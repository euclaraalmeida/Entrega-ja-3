package repositorio;
import java.util.ArrayList;
import java.util.List;


import modelo.Entregador;
import modelo.Pedido;
import util.Util;

public class RepositorioEntregador  extends CRUDRepositorio<Entregador>{
	
	
	public Entregador lerEntregador(Object chave) {
		String nome = (String) chave;
		Query q = Util.getManager().query();
		q.constrain(Entregador.class);
		q.descend("nome").constrain(nome);
		List<Entregador> resultado = q.execute();
		if(resultado.size()>0) {
			return resultado.getFirst();
		}else {
			return null;
		}
	
	}
	    
   	
   	// Listar 
       public List<Entregador> ListarEntregador() {
           System.out.println("------Lista de Entregadores---");
           Query q1 = Util.getManager().query(); 
           q1.constrain(Entregador.class);
           List<Entregador> resultados1 = q1.execute(); 

           if (resultados1.isEmpty()) {
               System.out.println("Nenhum entregador cadastrado.");
           } else {
               for (Entregador e : resultados1) {
                   System.out.println(e); 
               }
           }
           
           return new ArrayList<>(resultados1); 
       }
	   @Override
	   public Entregador ler(Object chave) {
		// TODO Auto-generated method stub
		return null;
	   }



       }
       
	       
       



	
	
       



