package models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;

public class CategoriaDAO {
    static {
        try {

            Class.forName(config.Config.BD_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("### FALHA CRÍTICA: Driver JDBC do PostgreSQL não encontrado! ###");
            e.printStackTrace();
        }
    }

    //Busca categoria por id
    public static Categoria obterPorId(int idCategoria) {
        Categoria categoria = null;
        String sql = "SELECT id_categoria, nome FROM categoria WHERE id_categoria = ?;";
        
        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, idCategoria);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    categoria = new Categoria();
                    categoria.setId_cat(resultSet.getInt("id_categoria"));
                    categoria.setNome(resultSet.getString("nome"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao obter categoria por ID: " + ex.getMessage());
        }
        return categoria;
    }

    //inserir categoria
    public boolean inserir(String nome) {
        String sql = "INSERT INTO categoria (nome) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir categoria: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    //remover categoria por id
    public boolean remover(int idCategoria) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCategoria);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao remover categoria: " + ex.getMessage());
            // Detalhe: Se houver produtos usando esta FK, o erro será capturado aqui.
            return false;
        }
    }

    public boolean atualizar(int idCategoria, String novoNome) {
        String sql = "UPDATE categoria SET nome = ? WHERE id_categoria = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    
            preparedStatement.setString(1, novoNome);
            preparedStatement.setInt(2, idCategoria);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar categoria: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public int obterIdPorNome(String nome) {
        int idCategoria = -1;
        String sql = "SELECT id_categoria FROM categoria WHERE nome = ?;";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    idCategoria = resultSet.getInt("id_categoria");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao obter ID por nome: " + ex.getMessage());
        }
        return idCategoria; // Retorna o ID ou -1 se não encontrar
    }

    public List<Categoria> obterTodos() {
        List<Categoria> resultado = new ArrayList<>();
        String sql = "SELECT id_categoria, nome FROM categoria ORDER BY nome;";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Categoria categoria = new Categoria();

                // Mapeamento dos campos
                categoria.setId_cat(resultSet.getInt("id_categoria"));
                categoria.setNome(resultSet.getString("nome"));

                resultado.add(categoria);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar todas as categorias: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }
}
