package models;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class Venda {
    private int idVenda; 
    private Timestamp dataHora;
    private BigDecimal valorTotal;
    private Usuario usuario;


    public Venda(){}

    public Venda(int idVenda, Usuario usuario, Timestamp dataHora, BigDecimal valorTotal) {
        this.idVenda = idVenda;
        this.usuario = usuario;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
    }
    
    public int getIdVenda() {return idVenda;}
    public void setIdVenda(int idVenda) {this.idVenda = idVenda;}
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario; }
    public Timestamp getDataHora() {return dataHora;}
    public void setDataHora(Timestamp dataHora) {this.dataHora = dataHora;}
    public BigDecimal getValorTotal() {return valorTotal;}
    public void setValorTotal(BigDecimal valorTotal) {this.valorTotal = valorTotal;}
}

