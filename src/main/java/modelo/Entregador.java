package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Entregador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    // mudei para o id agora ser a pk
	@Column(unique = true) // ainda vai ser unico de acordo com as regras de negocio da etapa 2? 
    private String nome;
    
    // Um entregador tem variás entregas
    @OneToMany(mappedBy = "entregador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Entrega> lista_entregas = new ArrayList<>();

    public Entregador() {}
    
    
    public Entregador(String nome) {
        this.nome = nome;
    }

 
	public void adicionar(Entrega e) {
		e.setEntregador(this); // vinculando dos dois lados
        lista_entregas.add(e);
    }
    
    public void setId(int idNovo) {
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
    
	public Entrega getEntrega(int id) throws Exception{			
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
