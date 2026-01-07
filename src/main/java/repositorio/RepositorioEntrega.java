package repositorio;
import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;
import modelo.Entrega;
import modelo.Pedido;
import util.Util;

public class RepositorioEntrega extends CRUDRepositorio<Entrega> {

	public  Entrega lerEntrega(Object chave) {
		Integer id = (Integer) chave;
		Query q = Util.getManager().query();
		q.constrain(Entrega.class);
		q.descend("id").constrain(id);
		List<Entrega> resultado = q.execute();
		if (resultado.size() > 0)
			return resultado.getFirst();
		else
			return null;
	}
	
	// quais entregas na data x
	
	public List<Entrega> EntregasNaData(Object chave) {
		String data = (String) chave;
		Query q = Util.getManager().query();
		q.constrain(Entrega.class);
		q.descend("data").constrain(data);
		List<Entrega> resultado = q.execute();
		if (resultado.size()>0) {
	           return new ArrayList<>(resultado); 
		}else {
	           return null; 
		}
	}
	
	
	public List<Entrega> ListarEntregas() {

    System.out.println("-------Lista de Entregas--------");
    Query q2 = Util.getManager().query(); 
    q2.constrain(Entrega.class); 
    List<Entrega> resultados2 = q2.execute();

    if (resultados2.isEmpty()) {
        System.out.println("Nenhuma entrega cadastrada.");
    } else {
        for (Entrega e : resultados2) {
            System.out.println(e); 
        }}
    return new ArrayList<>(resultados2); 
    }

	//quais os entregas com mais de N pedidos
	static class Filtro implements Evaluation {
        private int n;

        public Filtro(int n) {
            this.n = n;
        }

        public void evaluate(Candidate candidate) {
            Entrega e = (Entrega) candidate.getObject();
            
            if (e.getListaPedidos().size() > n) {
                candidate.include(true);
            } else {
                candidate.include(false);
            }
        }
    }

    public List<Entrega> consultarEntregasComMaisDeNPedidos(int N) {
        Query q = Util.getManager().query();
        q.constrain(Entrega.class);
        q.constrain(new Filtro(N)); 

        List<Entrega> resultados = q.execute();

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma entrega com mais de " + N + " pedidos encontrada.");
            return null;
        } else {
            System.out.println("Entregas com mais de " + N + " pedidos:");
            for (Entrega e : resultados) {
                System.out.println(e);
            }
            return new ArrayList<>(resultados);
        }
    }
    
	@Override
	public Entrega ler(Object chave) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
