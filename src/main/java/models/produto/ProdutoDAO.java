package models.produto;
import jakarta.servlet.http.Part;
import models.categoria.Categoria;
import models.categoria.CategoriaDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;

public class ProdutoDAO {
    static {
        try {

            Class.forName(config.Config.BD_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("### FALHA CRÍTICA: Driver JDBC do PostgreSQL não encontrado! ###");
            e.printStackTrace();
        }
    }
    private static final String UPLOAD_DIRETORIO = "/E-Conmerce/pictures";

    //LISTA TODOS OS PRODUTOS
    public static List<Produto> obterTodos() {
        List<Produto> resultado = new ArrayList<>();
        String sql = "SELECT p.*, p.id_categoria FROM produto p;";

        CategoriaDAO categoriaDao = new CategoriaDAO();

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId_produto(resultSet.getInt("id_produto"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setFoto(resultSet.getString("foto"));

                int idCategoria = resultSet.getInt("id_categoria");
                Categoria categoria = CategoriaDAO.obterPorId(idCategoria);

                produto.setCategoria(categoria);
                resultado.add(produto);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar usuários: " + ex.getMessage());
            ex.printStackTrace();

        }
        return resultado;
    }

    //lISTA UM UNICO PRODUTO
    public Produto obter(int id) {
        Produto produto = null;
        String sql = "SELECT p.*, p.id_categoria FROM produto p WHERE p.id_produto = ?;";
        CategoriaDAO categoriaDao = new CategoriaDAO();

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    produto = new Produto();
                    produto.setId_produto(resultSet.getInt("id_produto"));
                    produto.setDescricao(resultSet.getString("descricao"));
                    produto.setPreco(resultSet.getDouble("preco"));
                    produto.setQuantidade(resultSet.getInt("quantidade"));
                    produto.setFoto(resultSet.getString("foto"));

                    int idCategoria = resultSet.getInt("id_categoria");
                    Categoria categoria = categoriaDao.obterPorId(idCategoria);
                    produto.setCategoria(categoria);

                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter produto: " + ex.getMessage());
            ex.printStackTrace();
        }
        return produto;
    }

    public boolean inserir(String descricao, Double preco, Part foto, int quantidade, int id_categoria) {
        boolean sucesso = false;
        // 1. Ajuste os nomes das tabelas/colunas para bater com seu banco (Ex: produtos e id_produto)
        String sql = "INSERT INTO produto (descricao, preco, foto, quantidade, id_categoria) VALUES (?, ?, NULL, ?, ?)";

        // 2. Use o caminho absoluto que você confirmou no terminal (pwd)
        String UPLOAD_DIRETORIO = "/home/mslms/Projects/SMD/E-Conmerce/pictures/";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             // PONTO CHAVE: Statement.RETURN_GENERATED_KEYS adicionado aqui
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, descricao);
            preparedStatement.setDouble(2, preco);
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setInt(4, id_categoria);

            int rowsAffected = preparedStatement.executeUpdate();

            long produtoId = 0;
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    produtoId = resultSet.getLong(1);
                }
            }

            if (rowsAffected == 1 && produtoId > 0 && foto != null && foto.getSize() > 0) {
                // Criar nome: ID + extensão original
                String originalName = foto.getSubmittedFileName();
                String extensao = originalName.substring(originalName.lastIndexOf("."));
                String nomeFinalArquivo = produtoId + extensao;

                File arquivoDestino = new File(UPLOAD_DIRETORIO, nomeFinalArquivo);

                try (InputStream input = foto.getInputStream()) {
                    Files.copy(input, arquivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Atualiza o nome da foto no banco usando o ID correto
                String sqlUpdate = "UPDATE produto SET foto = ? WHERE id_produto = ?";
                try (PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdate)) {
                    stmtUpdate.setString(1, nomeFinalArquivo);
                    stmtUpdate.setLong(2, produtoId);
                    stmtUpdate.executeUpdate();
                }
                sucesso = true;
            } else if (rowsAffected == 1 && (foto == null || foto.getSize() == 0)) {
                sucesso = true; // Sucesso mesmo sem foto
            }

            if (sucesso) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (IOException | SQLException ex) {
            System.err.println("Erro ao inserir produto: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return sucesso;
    }

    //Atualizar produto
    public boolean atualizar(int id, String descricao, Double preco, int quantidade, int idCategoria){
        String sql = "UPDATE produto SET descricao = ?, preco = ?, quantidade = ?, id_categoria = ? WHERE id_produto = ?";

        try(Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, descricao);
            preparedStatement.setDouble(2, preco);
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setInt(4, idCategoria);

            preparedStatement.setInt(5, id);

            int linhasAfetadas = preparedStatement.executeUpdate();

            return linhasAfetadas == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar produto: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }

    }

    //REMOVER PRODUTO PELO id
    public static boolean remover(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() == 1);

        } catch (SQLException ex) {
            System.err.println("Erro ao remover produto: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

}
