package models;

public class Usuario {
    private int id_usuario;
    private String nome;
    private String email;
    private String senha;
    private String endereco;
    private Boolean administrador;

    public Usuario() {}

    public Usuario(int id, String nome, String endereco, String email, String senha) {
        this.id_usuario = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getEndereco() {return endereco; }
    public void setEndereco(String endereco) {this.endereco = endereco; }
    public Boolean getAdministrador() {return administrador; }
    public void setAdministrador(Boolean administrador) {this.administrador = administrador; }

    @Override
    public String toString() {
        return "Usuario [ID: " + id_usuario + ", Nome: " + nome + ", Email: " + email + ", Endereco: " + endereco  + ", Administrador: " + administrador + "]";
    }

}
