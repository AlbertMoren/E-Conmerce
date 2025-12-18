package models.carrinho;

import models.usuario.Usuario;
import models.usuario.UsuarioDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;

public class CarrinhoDAO {
    private UsuarioDAO usuarioDao = new UsuarioDAO();

    // Cria um novo registro
    public boolean inserir(int idUsuario) { 
        String sql = "INSERT INTO carrinho (id_usuario) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idUsuario); 

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir carrinho: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    //Obeter todos os carrinhos 
    public List<Carrinho> obterTodos() {
        List<Carrinho> resultado = new ArrayList<>();
        String sql = "SELECT * FROM carrinho;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Carrinho carrinho = new Carrinho();
                
                carrinho.setIdCarrinho(resultSet.getInt("id_carrinho"));
                carrinho.setDataCriacao(resultSet.getTimestamp("data_criacao"));
                carrinho.setStatus(resultSet.getString("status"));

                int idUsuario = resultSet.getInt("id_usuario");
                Usuario usuario = usuarioDao.obterPorId(idUsuario);
                
                carrinho.setUsuario(usuario); 
                
                resultado.add(carrinho);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar carrinhos: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }

    //Obter carrinho por id de usuario
    public List<Carrinho> buscarPorIdUsuario(int idUsuario) {
        List<Carrinho> resultado = new ArrayList<>();
        String sql = "SELECT * FROM carrinho WHERE id_usuario = ? ORDER BY data_criacao DESC;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                Usuario usuarioComprador = usuarioDao.obterPorId(idUsuario);

                while (resultSet.next()) {
                    Carrinho carrinho = new Carrinho();
                    
                    carrinho.setIdCarrinho(resultSet.getInt("id_carrinho"));
                    carrinho.setDataCriacao(resultSet.getTimestamp("data_criacao"));
                    carrinho.setStatus(resultSet.getString("status"));

                    carrinho.setUsuario(usuarioComprador); 
                    resultado.add(carrinho);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar carrinho por usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }
    
    // Busca Carrinho por ID do Carrinho
    public Carrinho buscarPorIdCarrinho(int idCarrinho) {
        Carrinho carrinho = null;
        String sql = "SELECT * FROM carrinho WHERE id_carrinho = ?;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCarrinho);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                if (resultSet.next()) {
                    carrinho = new Carrinho();
                    
                    carrinho.setIdCarrinho(resultSet.getInt("id_carrinho"));
                    carrinho.setDataCriacao(resultSet.getTimestamp("data_criacao"));
                    carrinho.setStatus(resultSet.getString("status"));

                    int idUsuario = resultSet.getInt("id_usuario");
                    
                    Usuario usuario = usuarioDao.obterPorId(idUsuario);
                    
                    carrinho.setUsuario(usuario); 
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar carrinho por ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        return carrinho;
    }

    // remover carrinho por ID
    public boolean remover(int idCarrinho) {
        String sql = "DELETE FROM carrinho WHERE id_carrinho = ?;";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCarrinho);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao remover carrinho: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Atualizar Status do Carrinho (UPDATE) ---
    // A principal atualização no cabeçalho é mudar o status (ex: "aberto" -> "fechado")
    public boolean atualizarStatus(int idCarrinho, String novoStatus) {
        String sql = "UPDATE carrinho SET status = ? WHERE id_carrinho = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, novoStatus);
            preparedStatement.setInt(2, idCarrinho);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar status do carrinho: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}
