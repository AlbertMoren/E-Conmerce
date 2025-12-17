package models;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class Venda {
    private int idVenda; 
    private Timestamp dataHora;
    private BigDecimal valorTotal;
    private Usuario usuario;
    private String status;

    public Venda(){}

    public Venda(int idVenda, Usuario usuario, Timestamp dataHora, BigDecimal valorTotal,String status) {
        this.idVenda = idVenda;
        this.usuario = usuario;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
        this.status = status;
    }
    
    public int getIdVenda() {return idVenda;}
    public void setIdVenda(int idVenda) {this.idVenda = idVenda;}
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario; }
    public Timestamp getDataHora() {return dataHora;}
    public void setDataHora(Timestamp dataHora) {this.dataHora = dataHora;}
    public BigDecimal getValorTotal() {return valorTotal;}
    public void setValorTotal(BigDecimal valorTotal) {this.valorTotal = valorTotal;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}

