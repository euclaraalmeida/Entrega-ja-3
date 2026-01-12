package appconsole;

import requisito.Fachada;

public class Alterar {

    public Alterar() {
        try {
            System.out.println("Iniciando alteração...");
            

            Fachada.removerEntregaDoEntregador("Maria", 3);
            // ta removendo entrega, isso ta certo?
            
            System.out.println("Relacionamento removido com sucesso via Fachada.");

        } catch (Exception e) {
            System.out.println("Erro ao alterar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Alterar();
    }
}
