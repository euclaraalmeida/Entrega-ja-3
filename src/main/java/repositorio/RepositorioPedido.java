package repositorio;
import java.util.ArrayList;
import java.util.List;
import com.db4o.query.Query;

import modelo.Entregador;
import modelo.Pedido;
import util.Util;

public class RepositorioPedido  extends CRUDRepositorio<Pedido>{

	
	public Pedido lerPedido(Object chave) {
		Integer id = (Integer) chave;
		Query q = Util.getManager().query();
		q.constrain(Pedido.class);
		q.descend("id").constrain(id);
		List<Pedido> resultado = q.execute();
		if (resultado.size() > 0){
			return resultado.getFirst();
		}else {
			return null;
		}
				
	}
	
	
	// pedidos entre por X entregador
	public List<Pedido> PedidosPorEntregador(Object chave){
		String nome = (String) chave;
		Query q2 = Util.getManager().query();
		q2.constrain(Pedido.class);
		q2.descend("entrega")
		  .descend("entregador")
		  .descend("nome")
		  .constrain(nome);
		
		List<Pedido> resultados2 = q2.execute();
		
		if (resultados2.size()>0) {
			System.out.println("Pedidos do entregador " + chave + ":");
		    return new ArrayList<>(resultados2); 
		}else {
		return null;
		}
	}
	

	public List<Pedido> ListarPedidos(){

	    Query q3 = Util.getManager().query(); 
	    q3.constrain(Pedido.class); 
	    List<Pedido> resultados3 = q3.execute(); 
	
	    if (resultados3.isEmpty()) {
	        System.out.println("Nenhum pedido cadastrado.");
	    } else {
	        for (Pedido p : resultados3) {
	            System.out.println(p); 
	        }
	    }
	    return new ArrayList<>(resultados3); 
}

	@Override
	public Pedido ler(Object chave) {
		// TODO Auto-generated method stub
		return null;
	}
}
