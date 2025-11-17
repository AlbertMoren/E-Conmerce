package models;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;


public class VendaDAO {
    static {
        try {

            Class.forName(config.Config.BD_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("### FALHA CRÍTICA: Driver JDBC do PostgreSQL não encontrado! ###");
            e.printStackTrace();
        }
    }
    private UsuarioDAO usuarioDao = new UsuarioDAO();

    //INSERIR VENDA
    public boolean inserir(int id_usuario, BigDecimal valor_total,String Status) {
        String sql = "INSERT INTO venda (id_usuario, valor_total, status) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id_usuario);
            preparedStatement.setBigDecimal(2, valor_total);
            preparedStatement.setString(3, Status);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir venda: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    //obter todas as vendas
    public List<Venda> obterTodos() {
        List<Venda> resultado = new ArrayList<>();
        String sql = "SELECT * FROM venda;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Venda venda = new Venda();
                
                venda.setIdVenda(resultSet.getInt("id_venda"));
                venda.setDataHora(resultSet.getTimestamp("data_hora"));
                venda.setValorTotal(resultSet.getBigDecimal("valor_total"));
                venda.setStatus(resultSet.getString("status"));

                int idUsuario = resultSet.getInt("id_usuario");
                
                Usuario usuario = usuarioDao.obterPorId(idUsuario); 
                venda.setUsuario(usuario); 
                
                resultado.add(venda);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar vendas: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }

    //Obter venda por ID usuario
    public List<Venda> obterVendaPorUsuario(int idUsuario) {
        List<Venda> resultado = new ArrayList<>();
        String sql = "SELECT * FROM venda WHERE id_usuario = ?;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setInt(1, idUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                Usuario usuarioComprador = usuarioDao.obterPorId(idUsuario);

                while (resultSet.next()) {
                    Venda venda = new Venda();
                    
                    venda.setIdVenda(resultSet.getInt("id_venda"));
                    venda.setDataHora(resultSet.getTimestamp("data_hora"));
                    venda.setValorTotal(resultSet.getBigDecimal("valor_total"));
                    venda.setStatus(resultSet.getString("status"));

                    venda.setUsuario(usuarioComprador); 
                    resultado.add(venda);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar vendas por usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }

    //Obter venda por ID venda
    public Venda obterPorId(int idVenda) {
        String sql = "SELECT * FROM venda WHERE id_venda = ?;";
        Venda venda = null;

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idVenda);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    venda = new Venda();

                    venda.setIdVenda(resultSet.getInt("id_venda"));
                    venda.setDataHora(resultSet.getTimestamp("data_hora"));
                    venda.setValorTotal(resultSet.getBigDecimal("valor_total"));
                    venda.setStatus(resultSet.getString("status"));

                    int idUsuario = resultSet.getInt("id_usuario");
                    Usuario usuario = usuarioDao.obterPorId(idUsuario);
                    venda.setUsuario(usuario);
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter venda por ID: " + ex.getMessage());
            ex.printStackTrace();
        }

        return venda;
    }

    //atualiza venda
    public boolean atualizar(Venda venda) {
        String sql = "UPDATE venda SET id_usuario = ?, valor_total = ?, status = ? WHERE id_venda = ?"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, venda.getUsuario().getId_usuario());
            preparedStatement.setBigDecimal(2, venda.getValorTotal());
            preparedStatement.setString(3, venda.getStatus()); 
            preparedStatement.setInt(4, venda.getIdVenda()); 

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar venda: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    //Atualiza o status da venda
    public boolean atualizarStatus(int idVenda, String novoStatus) {
        String sql = "UPDATE venda SET status = ? WHERE id_venda = ?"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, novoStatus);
            preparedStatement.setInt(2, idVenda);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar status da venda: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    //Remover a venda por id da venda
    public boolean remover(int idVenda) {
        String sql = "DELETE FROM venda WHERE id_venda = ?"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idVenda);

            return preparedStatement.executeUpdate() == 1; 

        } catch (SQLException ex) {
            System.err.println("Erro ao remover venda: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}


