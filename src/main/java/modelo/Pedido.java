package modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_20241370025")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private double valor;
    private String descricao;
    private String localizacao;
    
    // Muitos Pedidos para Uma Entrega
    @ManyToOne
    private Entrega entrega;
    
    

    public Pedido() {}    

    public Pedido(double valor, String descricao, String localizacao) {
        this.valor = valor;
        this.descricao = descricao;
        this.localizacao = localizacao;	
    }
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int idNovo) {
        this.id = idNovo;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }
   
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }
    
    // toString 
    @Override
    public String toString() {
        String infoEntrega = (this.entrega != null) ? String.valueOf(this.entrega.getId()) : "Sem entrega";
        
        return "Id: " + this.id + ", Pedido: " + this.descricao + 
               ", Localização: " + this.localizacao + ", Valor: " + this.valor + 
               ", ID Entrega: " + infoEntrega;
    }
}