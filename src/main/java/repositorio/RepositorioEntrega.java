package repositorio;

import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import util.Util;

public class RepositorioEntrega extends CRUDRepositorio<Entrega> {

    @Override
    public Entrega ler(Object chave) {
        return Util.getManager().find(Entrega.class, chave);
    }
    
   
    @Override
    public List<Entrega> listar() {
        return Util.getManager()
                .createQuery("SELECT e FROM Entrega e ORDER BY e.id", Entrega.class)
                .getResultList();
    }

    public List<Entrega> EntregasNaData(String data) {
        try {
            String jpql = "SELECT e FROM Entrega e WHERE e.data = :data";
            TypedQuery<Entrega> query = Util.getManager().createQuery(jpql, Entrega.class);
            query.setParameter("data", data);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Entrega> consultarEntregasComMaisDeNPedidos(int n) {
        try {
            String jpql = "SELECT e FROM Entrega e WHERE SIZE(e.lista_pedidos) > :n";
            TypedQuery<Entrega> query = Util.getManager().createQuery(jpql, Entrega.class);
            query.setParameter("n", n);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}