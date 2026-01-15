package models.venda;

import models.carrinho.CarrinhoItem;
import models.usuario.Usuario;
import models.usuario.UsuarioDAO;

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

    private Integer inserirRetornandoId(Connection connection, int idUsuario, BigDecimal valorTotal, String status) throws SQLException {
        String sql = "INSERT INTO venda (id_usuario, valor_total, status) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setBigDecimal(2, valorTotal);
            preparedStatement.setString(3, status);

            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                return null;
            }

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return null;
    }

    public Integer inserirComItens(int idUsuario, BigDecimal valorTotal, String status, List<CarrinhoItem> itens) {
        String sqlItem = "INSERT INTO venda_produto (id_venda, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA)) {
            connection.setAutoCommit(false);

            Integer idVenda = inserirRetornandoId(connection, idUsuario, valorTotal, status);
            if (idVenda == null) {
                connection.rollback();
                return null;
            }

            try (PreparedStatement stmtItem = connection.prepareStatement(sqlItem)) {
                for (CarrinhoItem item : itens) {
                    if (item == null || item.getProduto() == null) {
                        continue;
                    }
                    stmtItem.setInt(1, idVenda);
                    stmtItem.setInt(2, item.getProduto().getId_produto());
                    stmtItem.setInt(3, item.getQuantidade());
                    stmtItem.setBigDecimal(4, BigDecimal.valueOf(item.getProduto().getPreco()));
                    stmtItem.addBatch();
                }
                stmtItem.executeBatch();
            }

            connection.commit();
            return idVenda;

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir venda com itens: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

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

    public boolean removerComItens(int idVenda) {
        String sqlRemoverItens = "DELETE FROM venda_produto WHERE id_venda = ?";
        String sqlRemoverVenda = "DELETE FROM venda WHERE id_venda = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA)) {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtItens = connection.prepareStatement(sqlRemoverItens);
                 PreparedStatement stmtVenda = connection.prepareStatement(sqlRemoverVenda)) {

                stmtItens.setInt(1, idVenda);
                stmtItens.executeUpdate();

                stmtVenda.setInt(1, idVenda);
                int rows = stmtVenda.executeUpdate();

                if (rows == 1) {
                    connection.commit();
                    return true;
                }

                connection.rollback();
                return false;
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao remover venda com itens: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}


