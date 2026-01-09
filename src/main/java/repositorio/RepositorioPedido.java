package repositorio;

import java.util.List;
import jakarta.persistence.TypedQuery;
import modelo.Pedido;
import util.Util;

public class RepositorioPedido extends CRUDRepositorio<Pedido> {

    // Método para ler pelo ID
    @Override
    public Pedido ler(Object chave) {
        return Util.getManager().find(Pedido.class, chave);
    }

    @Override
    public List<Pedido> listar() {
        return Util.getManager()
                .createQuery("SELECT p FROM Pedido p ORDER BY p.id", Pedido.class)
                .getResultList();
    }

    // Consulta específica
    public List<Pedido> PedidosPorEntregador(String nomeEntregador) {
        try {
            String jpql = "SELECT p FROM Pedido p WHERE p.entrega.entregador.nome = :nome";
            TypedQuery<Pedido> query = Util.getManager().createQuery(jpql, Pedido.class);
            query.setParameter("nome", nomeEntregador);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}