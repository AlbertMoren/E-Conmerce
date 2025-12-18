package models.produto;

import models.categoria.Categoria;

public class Produto {
    private int id_produto;
    private String descricao;
    private Double preco;
    private int quantidade;
    private Categoria categoria;
    private String foto;

    public Produto(){};

    public Produto(int id, String descricao, Double preco, int quantidade, Categoria categoria, String foto){
        this.id_produto = id;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.foto = foto;
    }

    public int getId_produto() {return id_produto; }
    public String getDescricao() {return descricao; }
    public Double getPreco() {return preco; }
    public int getQuantidade() {return quantidade;}
    public void setId_produto(int id_produto) {this.id_produto = id_produto;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setPreco(Double preco) {this.preco = preco;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    public Categoria getCategoria() {return categoria;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
    public String getFoto() {return foto;}
    public void setFoto(String foto) {this.foto = foto;}
}