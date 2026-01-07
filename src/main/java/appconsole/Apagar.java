package appconsole;

import requisito.Fachada;

public class Apagar {

    public Apagar() {
        try {
            System.out.println("Iniciando exclusão...");

 
            Fachada.excluirEntregador("Davi");
            
            System.out.println("Entregador 'Davi' excluído com sucesso (e entregas tratadas).");

        } catch (Exception e) {
            System.out.println("Erro ao apagar: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new Apagar();
    }
}
