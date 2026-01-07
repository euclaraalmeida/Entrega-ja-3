package appconsole;

import java.util.List;
import requisito.Fachada;
import modelo.Entregador;
import modelo.Entrega;
import modelo.Pedido;

public class Listar {

    public Listar() {
        listar();
    }

    public void listar() {
        try {
            System.out.println("------Lista de Entregadores---");
            List<Entregador> entregadores = Fachada.listarEntregador();
            if (entregadores.isEmpty()) System.out.println("Nenhum entregador.");
            for (Entregador e : entregadores) System.out.println(e);

            System.out.println("\n-------Lista de Entregas--------");
            List<Entrega> entregas = Fachada.listarEntregas();
            if (entregas.isEmpty()) System.out.println("Nenhuma entrega.");
            for (Entrega e : entregas) System.out.println(e);

            System.out.println("\n-------Lista de Pedidos--------");
            List<Pedido> pedidos = Fachada.listarPedidos();
            if (pedidos.isEmpty()) System.out.println("Nenhum pedido.");
            for (Pedido p : pedidos) System.out.println(p);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Listar();
    }
}