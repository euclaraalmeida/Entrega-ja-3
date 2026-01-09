package repositorio;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import util.Util;

public class RepositorioEntrega extends CRUDRepositorio<Entrega> {

	public  Entrega lerEntrega(Object chave) {
		try {
			
		String jpql = "SELECT e FROM Entrega e WHERE e.id = :id";					
					
		TypedQuery<Entrega> query = Util.getManager().createQuery(jpql, Entrega.class);
					
		 query.setParameter("id",chave);
			
		return query.getSingleResult();
			
			
		}
		catch (jakarta.persistence.NoResultException e) {
	        return null;
	    }
		}
		
	
	
	// quais entregas na data x
	
	public List<Entrega> EntregasNaData(Object data) {
		try {	
			String jpql = "SELECT e FROM Entrega e WHERE e.data = :data";					
			
			TypedQuery<Entrega> query = Util.getManager().createQuery(jpql, Entrega.class);
						
			 query.setParameter("data",data);
				
			return query.getResultList();
			
		}
		
		catch (jakarta.persistence.NoResultException e) {
	        return null;
	    }
		
	}
	
	


	//quais os entregas com mais de N pedidos
    public List<Entrega> consultarEntregasComMaisDeNPedidos(int N) {
    	   String jpql = "SELECT e FROM Entrega e WHERE SIZE(e.lista_pedidos) > :n";
		    
		    TypedQuery<Entrega> query = Util.getManager().createQuery(jpql, Entrega.class);
		    query.setParameter("n", N);
		    
		    return query.getResultList();
    }
    
	@Override
    public Entrega ler(Object chave) { 
        return ler((String) chave);
    }

	@Override
	public List<Entrega> listar() {
		  return Util.getManager()
                  .createQuery("SELECT e FROM Entrega e ORDER BY e.id", Entrega.class)
                  .getResultList();
   }
	}
    
	

