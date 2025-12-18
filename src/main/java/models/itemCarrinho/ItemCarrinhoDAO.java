package models.itemCarrinho;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;

public class ItemCarrinhoDAO {
    private CarrinhoDAO carrinhoDao = new CarrinhoDAO();
    private ProdutoDAO produtoDao = new ProdutoDAO();

    //Adiciona produtos ao item carrinho
    public boolean inserir(int idCarrinho, int idProduto, int quantidade, double precoUnitario) {
        String sql = "INSERT INTO item_carrinho (id_carrinho, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCarrinho);
            preparedStatement.setInt(2, idProduto);
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setBigDecimal(4, BigDecimal.valueOf(precoUnitario));

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir item no carrinho: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    //obter itens carrinho por ID_carrinho
    public List<ItemCarrinho> obterItensPorCarrinho(int idCarrinho) {
        List<ItemCarrinho> resultado = new ArrayList<>();
        String sql = "SELECT * FROM item_carrinho WHERE id_carrinho = ?;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCarrinho);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                Carrinho carrinhoCabecalho = carrinhoDao.buscarPorIdCarrinho(idCarrinho);

                while (resultSet.next()) {
                    ItemCarrinho item = new ItemCarrinho();

                    item.setIdItem(resultSet.getInt("id_item"));
                    item.setQuantidade(resultSet.getInt("quantidade"));
                    item.setPrecoUnitario(resultSet.getBigDecimal("preco_unitario"));

                    item.setCarrinho(carrinhoCabecalho); 
                
                    int idProduto = resultSet.getInt("id_produto");
                    Produto produto = produtoDao.obter(idProduto); 
                    item.setProduto(produto); 
                    
                    resultado.add(item);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar itens do carrinho: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }

    // Consultar ItemCarrinho por ID do Usuário
    public List<ItemCarrinho> obterItensPorUsuario(int idUsuario) {
        List<ItemCarrinho> resultado = new ArrayList<>();
        // Query com JOIN para ligar o item ao carrinho e o carrinho ao usuário
        String sql = "SELECT ic.* FROM item_carrinho ic JOIN carrinho c ON ic.id_carrinho = c.id_carrinho WHERE c.id_usuario = ?;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ItemCarrinho item = new ItemCarrinho();
                    
                    item.setIdItem(resultSet.getInt("id_item"));
                    item.setQuantidade(resultSet.getInt("quantidade"));
                    item.setPrecoUnitario(resultSet.getBigDecimal("preco_unitario"));

                    int idCarrinho = resultSet.getInt("id_carrinho");
                    Carrinho carrinho = carrinhoDao.buscarPorIdCarrinho(idCarrinho); 
                    item.setCarrinho(carrinho); 
                    
                    int idProduto = resultSet.getInt("id_produto");
                    Produto produto = produtoDao.obter(idProduto);
                    item.setProduto(produto); 
                    
                    resultado.add(item);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar itens por usuário: " + ex.getMessage());
        }
        return resultado;
    }

    // Atualizar ItemCarrinho
    public boolean atualizarQuantidade(int idItem, int novaQuantidade) {
        String sql = "UPDATE item_carrinho SET quantidade = ? WHERE id_item = ?;";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, novaQuantidade);
            preparedStatement.setInt(2, idItem);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar a quantidade do item: " + ex.getMessage());
            return false;
        }
    }

    // Remover ItemCarrinho
    public boolean remover(int idItem) {
        String sql = "DELETE FROM item_carrinho WHERE id_item = ?;";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idItem);
            
            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            System.err.println("Erro ao remover item: " + ex.getMessage());
            return false;
        }
    }

    // --- Busca Item por ID do Carrinho E ID do Produto ---
    public ItemCarrinho buscarItemPorCarrinhoEProduto(int idCarrinho, int idProduto) {
        ItemCarrinho item = null;
        // Seleciona o item que corresponde ao carrinho e ao produto
        String sql = "SELECT * FROM item_carrinho WHERE id_carrinho = ? AND id_produto = ?;"; 

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCarrinho);
            preparedStatement.setInt(2, idProduto);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                item = new ItemCarrinho();
                
                item.setIdItem(resultSet.getInt("id_item"));
                item.setQuantidade(resultSet.getInt("quantidade"));
                item.setPrecoUnitario(resultSet.getBigDecimal("preco_unitario"));

                Carrinho carrinho = carrinhoDao.buscarPorIdCarrinho(idCarrinho); 
                item.setCarrinho(carrinho); 
                
                Produto produto = produtoDao.obter(idProduto);
                item.setProduto(produto); 
            }
        }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar item por carrinho e produto: " + ex.getMessage());
        }
    return item;
    }
}
