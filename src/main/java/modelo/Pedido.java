package modelo;

public class Pedido {
    private int id;
    private double valor;
    private String descricao;
    private Entrega entrega;
    private String localizacao;


    public Pedido(double valor, String descricao, String localizacao) {
        this.valor = valor;
        this.descricao = descricao;
        this.localizacao = localizacao;	}
    
    
    public double getValor() {
        return valor;
    }

    
	public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }
    
    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
   
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
   
    
    public int getId() {
        return id;
    }
    
    public void SetId(int idNovo) {
        this.id = idNovo;
   }
    
    public String getDescricao() {
        return descricao;
    }
    

  //tostring
  		@Override
  		public String toString() {
  			return "id: " + this.id + ", "+ " Pedido: " + this.descricao + ", "+ "Localização "+":"+ this.localizacao + " Valor: " + this.valor + ", "+ " Entrega: " + (this.entrega != null ? this.entrega.getId() : "Sem entrega");
  		}




		
  		
  	
		
  	}

