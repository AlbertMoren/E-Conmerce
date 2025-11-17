package models;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;

public class VendaProdutoDAO {
    private VendaDAO vendaDao = new VendaDAO();
    private ProdutoDAO produtoDao = new ProdutoDAO();

    // Inserir Item Vendido
    // Este método é chamado para cada item quando uma venda é finalizada.
    public boolean inserir(int idVenda, int idProduto, int quantidade, BigDecimal precoUnitario) { 
        String sql = "INSERT INTO venda_produto (id_venda, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idVenda);
            preparedStatement.setInt(2, idProduto);
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setBigDecimal(4, precoUnitario);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir item vendido: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Obter Itens por ID da Venda 
    // Método essencial para detalhar o conteúdo de uma venda específica.
    public List<VendaProduto> obterItensPorVenda(int idVenda) {
        List<VendaProduto> resultado = new ArrayList<>();
        String sql = "SELECT * FROM venda_produto WHERE id_venda = ?;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idVenda);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                Venda vendaCabecalho = vendaDao.obterPorId(idVenda);

                while (resultSet.next()) {
                    VendaProduto item = new VendaProduto();
                    
                    item.setQuantidade(resultSet.getInt("quantidade"));
                    item.setPrecoUnitario(resultSet.getBigDecimal("preco_unitario"));

                    item.setVenda(vendaCabecalho); 
                    
                    int idProduto = resultSet.getInt("id_produto");
                    Produto produto = produtoDao.obter(idProduto);
                    item.setProduto(produto); 
                    
                    resultado.add(item);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar detalhes da venda: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }


    public VendaProduto obterPorVendaEProduto(int idVenda, int idProduto) {
    VendaProduto item = null;
    String sql = "SELECT * FROM venda_produto WHERE id_venda = ? AND id_produto = ?;"; 

    try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, idVenda);
        preparedStatement.setInt(2, idProduto);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                item = new VendaProduto();
                
                item.setQuantidade(resultSet.getInt("quantidade"));
                item.setPrecoUnitario(resultSet.getBigDecimal("preco_unitario"));

                Venda venda = vendaDao.obterPorId(idVenda);
                item.setVenda(venda); 
                
                Produto produto = produtoDao.obter(idProduto);
                item.setProduto(produto);
            }
        }
    } catch (SQLException ex) {
        System.err.println("Erro ao obter item por venda e produto: " + ex.getMessage());
    }
    return item;
}
}
