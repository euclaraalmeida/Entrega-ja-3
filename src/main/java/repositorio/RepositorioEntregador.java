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
            
            // Transforma ela num objeto Entregador
            // Entrega (retorna) esse objeto
            return query.getSingleResult(); // ctz que vai retornar apenas 1 , 
            // se puder ter mais de 1 entregador isso muda , perguntar tbm se essa busca sera pelo nome mesmo ou pelo id agora?
        } catch (NoResultException e) {
            return null; 
        }
    }

    @Override
    public Entregador ler(Object chave) {
        return ler((String) chave);
    }
    
    @Override
    public List<Entregador> listar() {
        return Util.getManager()
                   .createQuery("SELECT e FROM Entregador e ORDER BY e.id", Entregador.class)
                   .getResultList();
    }
}