package models;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario() {}

    public Usuario(int id, String nome, string endereco, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getEndereco() {return endereco; }
    public void setEndereco(String endereco) {this.endereco = endereco; }

    @Override
    public String toString() {
        return "Usuario [ID: " + id + ", Nome: " + nome + ", Email: " + email + ", Endereco: " + endereco  + "]";
    }

}
