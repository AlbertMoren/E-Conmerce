package models;

public class Produto {
    private int id_produto;
    private String descricao;
    private Double preco;
    private int quantidade;
    //ADicionar categoria(Albert)

    public Produto(){};

    public Produto(int id, String descricao, Double preco, int quantidade){
        this.id_produto = id;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getId_produto() {return id_produto; }
    public String getDescricao() {return descricao; }
    public Double getPreco() {return preco; }
    public int getQuantidade() {return quantidade;}
    public void setId_produto(int id_produto) {this.id_produto = id_produto;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setPreco(Double preco) {this.preco = preco;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
 
}
