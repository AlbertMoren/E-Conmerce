package models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;

public class produtosDAO {
    static {
        try {

            Class.forName(config.Config.BD_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("### FALHA CRÍTICA: Driver JDBC do PostgreSQL não encontrado! ###");
            e.printStackTrace();
        }
    }

    //LISTA TODOS OS PRODUTOS
    public List<Produto> obterTodos() {
        List<Produto> resultado = new ArrayList<>();
        String sql = "SELECT * FROM produto;";


        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                resultado.add(produto);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar usuários: " + ex.getMessage());
            ex.printStackTrace();

        }
        return resultado;
    }

    //lISTA UM UNICO PRODUTO
    public Produtos obter(int id) {
        Produto produto = null;
        String sql = "SELECT * FROM produtos WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    produto = new Produtos();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("descricao"));
                    produto.setEmail(resultSet.getString("preco"));
                    produto.setSenha(resultSet.getString("quantidade"));
                    resultado.add(produto);
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
        return produto;
    }

    //iNSERIR UM PRODUTO
    public boolean inserir(String descricao, Double preco, int quantidade) {
        String sql = "INSERT INTO produto (descricao, preco, quantidade) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, descricao);
            preparedStatement.setDouble(2, preco);
            preparedStatement.setInt(3, quantidade);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas == 1; 

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    //Atualizar produto
    public boolean atualizar(int id, String descricao, Double preco, int quantidade){
        Boolean sucesso = false;
        String_sql = "UPDATE produtos SET descricao = ?, preco = ?, quantidade = ? WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, produto.getDescricao());
            preparedStatement.setDouble(2, produto.getPreco());
            preparedStatement.setInt(3, produto.getQuantidade());
            preparedStatement.setInt(4, produto.getId());

            sucesso = (preparedStatement.executeUpdate() == 1);

            return sucesso;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar produto: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }

    }
    

    //REMOVER PRODUTO PELO id
    public static boolean remover(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate() == 1);

        } catch (SQLException ex) {
            System.err.println("Erro ao remover usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

}
