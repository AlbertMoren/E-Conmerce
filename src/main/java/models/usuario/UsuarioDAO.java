package models.usuario;

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
        String sql = "SELECT id_usuario, nome, email, endereco, senha, administrador FROM usuario";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(resultSet.getInt("id_usuario"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
                usuario.setEndereco(resultSet.getString("endereco"));
                usuario.setAdministrador(resultSet.getBoolean("administrador"));
                resultado.add(usuario);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar usuários: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }

    // obtem um usuario pelo id
    public Usuario obterPorId(int id) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, nome, email,endereco, senha, administrador FROM usuario WHERE id_usuario = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = new Usuario();
                    usuario.setId_usuario(resultSet.getInt("id_usuario"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setSenha(resultSet.getString("senha"));
                    usuario.setEndereco(resultSet.getString("endereco"));
                    usuario.setAdministrador(resultSet.getBoolean("administrador"));
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
        return usuario;
    }

    // obtem um usuario pelo email
    public Usuario obter(String email) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, nome, email,endereco, senha, administrador FROM usuario WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = new Usuario();
                    usuario.setId_usuario(resultSet.getInt("id_usuario"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setSenha(resultSet.getString("senha"));
                    usuario.setEndereco(resultSet.getString("endereco"));
                    usuario.setAdministrador(resultSet.getBoolean("administrador"));
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
    //Falta alterar para poder editar o endereço e outros campos(Albert)
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

    // atualiza um usuario com endereco
    public static boolean atualizar(String nome, String email, String endereco, String senha, String emailantigo) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, endereco = ?, senha = ? WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, endereco);
            preparedStatement.setString(4, senha);
            preparedStatement.setString(5, emailantigo);

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