package models;

import java.util.List;
import java.util.ArrayList;

public class Categoria {

    private int id_categoria;
    private String nome;
    private List<Produto> produtos;

    public Categoria() {
        this.produtos = new ArrayList<>();
    }

    public Categoria(int id_categoria, String nome) {
        this.id_categoria = id_categoria;
        this.nome = nome;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto p) {
        this.produtos.add(p);
    }
    public List<Produto> getProdutos() {
        return this.produtos;
    }
    public int getId_categoria() {
        return this.id_categoria;
    }
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}