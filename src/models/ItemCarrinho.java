package models;

public class ItemCarrinho {
    private int idItem;
    private int quantidade;
    private BigDecimal precoUnitario;
    private Carrinho carrinho;
    private Produto produto;

    public ItemCarrinho() {}

    public ItemCarrinho(int idItem, Carrinho carrinho, Produto produto, int quantidade, BigDecimal precoUnitario) {
        this.idItem = idItem;
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getIdItem() { return idItem; }
    public void setIdItem(int idItem) { this.idItem = idItem; }

    public Carrinho getCarrinho() { return carrinho; }
    public void setCarrinho(Carrinho carrinho) { this.carrinho = carrinho; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}
