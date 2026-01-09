package appconsole;

import requisito.Fachada;

public class Cadastrar {

    public Cadastrar() {
        try {
            System.out.println("Cadastrando dados...");

            // NÃO coloque begin/commit aqui! A Fachada já faz isso internamente.

            Fachada.CriarEntregador("João");
            Fachada.CriarEntregador("Maria");
            Fachada.CriarEntregador("Lucas");
            Fachada.CriarEntregador("Matheus");
            Fachada.CriarEntregador("Fausto");
            Fachada.CriarEntregador("Mariana");
            Fachada.CriarEntregador("Davi");
            Fachada.CriarEntregador("Arthur");
            Fachada.CriarEntregador("Clara");
            Fachada.CriarEntregador("Laura");

            Fachada.CriarEntrega("22/10/2025", "João");
            Fachada.CriarEntrega("23/10/2025", "João");
            Fachada.CriarEntrega("24/10/2025", "Maria");
            Fachada.CriarEntrega("25/10/2025", "Maria");
            Fachada.CriarEntrega("26/10/2025", "Maria");
            Fachada.CriarEntrega("27/10/2025", "Lucas");
            Fachada.CriarEntrega("28/10/2025", "Matheus");
            Fachada.CriarEntrega("29/10/2025", "Matheus");
            Fachada.CriarEntrega("30/10/2025", "Fausto");
            Fachada.CriarEntrega("31/10/2025", "Mariana");
            Fachada.CriarEntrega("01/11/2025", "Davi");
            Fachada.CriarEntrega("02/11/2025", "Davi");
            Fachada.CriarEntrega("03/11/2025", "Arthur");
            Fachada.CriarEntrega("04/11/2025", "Clara");
            Fachada.CriarEntrega("05/11/2025", "Laura");

            Fachada.CriarPedido(150.0, "Eletrônicos", "Porto Velho");
            Fachada.CriarPedido(89.9, "Roupas", "Ji-Paraná");
            Fachada.CriarPedido(230.5, "Livros", "Ariquemes");
            Fachada.CriarPedido(45.0, "Acessórios", "Cacoal");
            Fachada.CriarPedido(120.75, "Brinquedos", "Guajará-Mirim");
            Fachada.CriarPedido(310.4, "Eletrodomésticos", "Vilhena");
            Fachada.CriarPedido(67.8, "Cosméticos", "Jaru");
            Fachada.CriarPedido(540.0, "Móveis", "Rolim de Moura");
            Fachada.CriarPedido(22.5, "Alimentos", "Buritis");
            Fachada.CriarPedido(98.3, "Calçados", "Ouro Preto do Oeste");
            Fachada.CriarPedido(250.0, "Ferramentas", "Nova Mamoré");
            Fachada.CriarPedido(199.9, "Utilidades domésticas", "Alto Paraíso");

            // Associações
            Fachada.AddPedidoNaEntrega(1, 1); 
            Fachada.AddPedidoNaEntrega(2, 2); 
            Fachada.AddPedidoNaEntrega(3, 2); 
            Fachada.AddPedidoNaEntrega(4, 3);
            Fachada.AddPedidoNaEntrega(5, 4);
            Fachada.AddPedidoNaEntrega(6, 5);
            Fachada.AddPedidoNaEntrega(7, 6);
            Fachada.AddPedidoNaEntrega(8, 7);
            Fachada.AddPedidoNaEntrega(9, 8);
            Fachada.AddPedidoNaEntrega(10, 9);
            Fachada.AddPedidoNaEntrega(11, 10);
            Fachada.AddPedidoNaEntrega(12, 11);

            System.out.println("Cadastro realizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    public static void main(String[] args) {
        new Cadastrar();
    }
}