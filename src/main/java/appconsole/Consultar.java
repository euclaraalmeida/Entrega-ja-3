package appconsole;

import java.util.List;
import requisito.Fachada;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;

public class Consultar {

    public Consultar() {
        consultar();
    }

    public void consultar() {
        System.out.println("------ Iniciando Consultas via Fachada ------");

        // 1. Entregas na data 23/10/2025
        System.out.println("\n--- 1. Entregas na data 23/10/2025 ---");
        List<Entrega> entregasData = Fachada.consultarEntregasPorData("23/10/2025");
        if (entregasData != null) {
            for (Entrega e : entregasData) {
                System.out.println(e);
            }
        } else {
            System.out.println("Nenhuma entrega nesta data.");
        }

        // 2. Pedidos entregues por "Maria"
        System.out.println("\n--- 2. Pedidos entregues por 'Maria' ---");
        List<Pedido> pedidosMaria = Fachada.consultarPedidosPorEntregador("Maria");
        if (pedidosMaria != null) {
            for (Pedido p : pedidosMaria) {
                System.out.println(p);
            }
        } else {
            System.out.println("Nenhum pedido encontrado para Maria.");
        }

        // 3. Entregadores com mais de N entregas
        int N = 2;
        System.out.println("\n--- 3. Entregadores com mais de " + N + " entregas ---");
        List<Entrega> produtivos = Fachada.consultarEntregadoresProdutivos(N);
        if (produtivos != null) {
            for (Entrega e : produtivos) {
                System.out.println(e);
            }
        } else {
            System.out.println("Nenhum entregador acima da meta.");
        }
    }

    public static void main(String[] args) {
        new Consultar();
    }
}
