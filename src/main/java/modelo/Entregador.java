package modelo;

import java.util.ArrayList;
import java.util.List;


public class Entregador {
    private int id;
    private String nome;
    private List<Entrega> lista_entregas = new ArrayList<>();

    public Entregador(String nome) {
        this.nome = nome;
    }

 
	public void adicionar(Entrega e) {
        lista_entregas.add(e);
    }
    
    public void SetId(int idNovo) {
        this.id = idNovo;
   }
    public void remover(Entrega e) {
        lista_entregas.remove(e);
    }

    public String getNome() {
        return nome;
    }

    public List<Entrega> getListaEntregas() {
        return lista_entregas;
    }
    
	public Entrega getEntregador(int id) throws Exception{			
		for (Entrega e : lista_entregas) {
			if (e.getId() == id) {
				return e;
			}
		}

		throw new Exception("Entregador não encontrado");
	}
	


@Override
public String toString() {

	
	return "id: " + this.id + ","+ " nome: " + this.nome + "," + " Entregas: " + lista_entregas ;
}


public void setNome(String nome2) {
	this.nome = nome2;
}

}
