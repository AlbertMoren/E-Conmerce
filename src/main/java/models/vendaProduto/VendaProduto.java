package models.vendaProduto;

import java.math.BigDecimal;

import models.produto.Produto;
import models.venda.Venda;

public class VendaProduto {
    private Venda venda;
    private Produto produto;
    
    private int quantidade;
    private BigDecimal precoUnitario;

    public VendaProduto() {}

    public VendaProduto(Venda venda, Produto produto, int quantidade, BigDecimal precoUnitario) {
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

}
