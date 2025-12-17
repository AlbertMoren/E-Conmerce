package models;

public class Categoria {
    private int id_cat;
    private String nome;

    public Categoria(){}

    public Categoria(int id_cat, String nome){
        this.id_cat  = id_cat;
        this.nome = nome;
    }

    public int getId_cat() {return id_cat;}
    public void setId_cat(int id_cat) {this.id_cat = id_cat;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    
}
