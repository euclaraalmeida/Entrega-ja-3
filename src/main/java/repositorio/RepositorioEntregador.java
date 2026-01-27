package repositorio;

import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Entregador;
import util.Util;

public class RepositorioEntregador extends CRUDRepositorio<Entregador> {
	
    public Entregador ler(String nome) {
        try {
        	
            String jpql = "SELECT e FROM Entregador e WHERE e.nome = :n";
            
            TypedQuery<Entregador> query = Util.getManager().createQuery(jpql, Entregador.class);
            
            query.setParameter("n", nome);
            
            return query.getSingleResult(); 
        } catch (NoResultException e) {
            return null; 
        }
    }

	@Override
	public Entregador ler(Object chave) {
		return Util.getManager().find(Entregador.class, chave);
	}
    
    @Override
    public List<Entregador> listar() {
        return Util.getManager()
                   .createQuery("SELECT e FROM Entregador e ORDER BY e.id", Entregador.class)
                   .getResultList();
    }
}