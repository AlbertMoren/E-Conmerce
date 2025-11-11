package models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;




public class UsuarioDAO {


    static {
        try {

            Class.forName(config.Config.BD_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("### FALHA CRÍTICA: Driver JDBC do PostgreSQL não encontrado! ###");
            e.printStackTrace();
        }
    }




    // obtem os usuarios do banco
    public List<Usuario> obterTodos() {
        List<Usuario> resultado = new ArrayList<>();
        String sql = "SELECT id, nome, email, senha FROM usuario";


        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
                resultado.add(usuario);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar usuários: " + ex.getMessage());
            ex.printStackTrace();

        }
        return resultado;
    }

    // obtem um usuario pelo id
    public Usuario obter(int id) {
        Usuario usuario = null;
        String sql = "SELECT id, nome, email, senha FROM usuario WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = new Usuario();
                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setSenha(resultSet.getString("senha"));
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
        return usuario;
    }

    // insere um usuario
    public boolean inserir(String nome, String email, String senha) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas == 1; // Retorna true se 1 linha foi inserida

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean validarLogin(String email, String senha) {

        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            try(ResultSet rs = preparedStatement.executeQuery()){
                return rs.next();
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao validar login: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // atualiza um usuario
    public static boolean atualizar(String nome, String email, String senha, String emailantigo) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);
            preparedStatement.setString(4, emailantigo);

            return (preparedStatement.executeUpdate() == 1);

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // remove um usuario
    public static boolean remover(String email) {
        String sql = "DELETE FROM usuario WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            return (preparedStatement.executeUpdate() == 1);

        } catch (SQLException ex) {
            System.err.println("Erro ao remover usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}