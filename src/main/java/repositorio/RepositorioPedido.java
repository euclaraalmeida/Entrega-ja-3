package repositorio;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import util.Util;

public class RepositorioPedido  extends CRUDRepositorio<Pedido>{

	
	public Entrega lerPedido(Object chave) {
		try {
		
		String jpql = "SELECT e FROM Pedido e WHERE e.id = :id";					
					
		TypedQuery<Entrega> query = Util.getManager().createQuery(jpql, Entrega.class);
					
		 query.setParameter("id",chave);
			
		return query.getSingleResult();
			
			
		}
		catch (jakarta.persistence.NoResultException e) {
	        return null;
	    }
	
	}
	
	// pedidos entregues por X entregador 
	public List<Pedido> PedidosPorEntregador(String nomeEntregador){
	    String jpql = "SELECT p FROM Pedido p WHERE p.entrega.entregador.nome = :nome";
	    
	    TypedQuery<Pedido> query = Util.getManager().createQuery(jpql, Pedido.class);
	    query.setParameter("nome", nomeEntregador);
	    
	    return query.getResultList();		 
	}
	

	@Override
    public Pedido ler(Object chave) { 
        return ler((String) chave); // conferir se aqui é string mesmo ja que a pesquisa é feita pelo id 
    }



	@Override
	public List<Pedido> listar() {
		return Util.getManager().createQuery("Select e from Pedido e ORDER BY e.id = :id", Pedido.class).getResultList();
	}
}
