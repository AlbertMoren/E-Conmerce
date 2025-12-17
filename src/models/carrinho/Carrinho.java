package models;
import java.sql.Timestamp;

public class Carrinho {
    private int idCarrinho;
    private Usuario usuario;
    private Timestamp dataCriacao;
    private String status;

    public Carrinho() {}

    public Carrinho(int idCarrinho, Usuario usuario, Timestamp dataCriacao, String status) {
        this.idCarrinho = idCarrinho;
        this.usuario = usuario;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    public int getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(int idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
