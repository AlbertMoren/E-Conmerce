package models.produto;
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

    //iNSERIR UM PRODUTO
    public boolean inserir(String descricao, Double preco, int quantidade, int id_categoria) {
        String sql = "INSERT INTO produto (descricao, preco, quantidade, id_categoria) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, descricao);
            preparedStatement.setDouble(2, preco);
            preparedStatement.setInt(3, quantidade);
            
            preparedStatement.setInt(4, id_categoria);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas == 1; 

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir produto: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
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
