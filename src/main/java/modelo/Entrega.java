package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;


@Entity
public class Entrega {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String data;
    
    // Um entregador pode fazer varias entregas
    @ManyToOne
    private Entregador entregador;
    
    // um pedido pertence a uma entrega
    @OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Pedido> lista_pedidos = new ArrayList<>();

    public Entrega() {}
    
   
    public Entrega(String data) {
        this.data = data;	}
    

	public void adicionar(Pedido pedido) {
		pedido.setEntrega(this); // vincula o pedido a entrega 
        lista_pedidos.add(pedido);
    }

    public void remover(Pedido p) {
        lista_pedidos.remove(p);
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int idNovo) {
         this.id = idNovo;
    }

    public String getData() {
        return data;
    }

 
    
    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }
    
    public void setData(String d) {
        this.data = d;
    }
    
    public List<Pedido> getListaPedidos() {
        return lista_pedidos;
    }
    
    
    public Pedido localizarPedidoPeloId(int id) {			
        for (Pedido p : lista_pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    public Pedido getPedido(String descricao) {			
        for (Pedido p : lista_pedidos) {
            if (p.getDescricao().equals(descricao)) {
                return p;
            }
        }
        return null;
    }

    
   
	//toString
	@Override
	public String toString() {
		
			// lista temporaria comas descriçoes dos pedidos 
			ArrayList<String> descricaoPedidos = new ArrayList<String>();
			for(Pedido p : this.getListaPedidos()) {
				descricaoPedidos.add(p.getDescricao());
			}
			
			
	    // Verifica se o entregador existe se sim pega o nome se não - entrega sem entregador 
	    String nomeEntregador = (this.entregador != null) ? this.entregador.getNome() : "Sem entregador";
	    
	    return "Id:" + getId() +", "+ "Entrega [data=" + data + ", entregador=" + nomeEntregador + ","+"Pedidos:"+ descricaoPedidos+"]";
	}

}
