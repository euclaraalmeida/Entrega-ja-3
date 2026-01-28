package requisito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import repositorio.CRUDRepositorio;
import repositorio.RepositorioEntrega;
import repositorio.RepositorioEntregador;
import repositorio.RepositorioPedido;

public class Fachada {
    
    private static RepositorioPedido PedidoRepositorio = new RepositorioPedido();
    private static RepositorioEntrega EntregaRepositorio = new RepositorioEntrega();
    private static RepositorioEntregador EntregadorRepositorio = new RepositorioEntregador();

    // -------------------------------------------------------------------------
    //  CRIAÇÃO 
    // -------------------------------------------------------------------------

    public static void CriarPedido(double valor, String descricao, String localizacao) throws Exception {
        PedidoRepositorio.conectar();
        try {
            CRUDRepositorio.begin(); 
            
            Pedido p = new Pedido(valor, descricao, localizacao);
            PedidoRepositorio.criar(p);
            
            CRUDRepositorio.commit(); 
        } catch (Exception e) {
            CRUDRepositorio.rollback(); 
            throw e;
        } finally {
            PedidoRepositorio.desconectar();
        }
    }

    public static void CriarEntrega(String data, String nomeEntregador) throws Exception {
        EntregaRepositorio.conectar();
        try {
            CRUDRepositorio.begin();

            try {
                LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                throw new Exception("Formato data invalido: " + data);
            }
            
            // Busca entregador pelo nome
            Entregador entregador = EntregadorRepositorio.ler(nomeEntregador);
            if (entregador == null) {
                throw new Exception("Entregador não encontrado: " + nomeEntregador);
            }

            Entrega e = new Entrega(data);
            e.setEntregador(entregador);
            entregador.adicionar(e);

            EntregaRepositorio.criar(e);          
            EntregadorRepositorio.atualizar(entregador); 
            
            CRUDRepositorio.commit();
        } catch (Exception e) {
            CRUDRepositorio.rollback();
            throw e;
        } finally {
            EntregaRepositorio.desconectar();
        }
    }
    
    public static void CriarEntregador(String nome) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            CRUDRepositorio.begin();

            Entregador existente = EntregadorRepositorio.ler(nome);
            if (existente != null) {
                throw new Exception("Erro: O entregador '" + nome + "' já está cadastrado."); 
            }

            Entregador e = new Entregador(nome);
            EntregadorRepositorio.criar(e);
            
            CRUDRepositorio.commit();
        } catch (Exception e) {
            CRUDRepositorio.rollback();
            throw e;
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }

    // -------------------------------------------------------------------------
    //  ALTERAÇÃO 
    // -------------------------------------------------------------------------

    public static void removerEntregaDoEntregador(String nomeEntregador, int idEntrega) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            CRUDRepositorio.begin();

            Entregador entregador = EntregadorRepositorio.ler(nomeEntregador);
            if (entregador == null) throw new Exception("Entregador não encontrado: " + nomeEntregador);

            Entrega entregaParaRemover = null;
            for (Entrega e : entregador.getListaEntregas()) {
                if (e.getId() == idEntrega) {
                    entregaParaRemover = e;
                    break;
                }
            }

            if (entregaParaRemover == null) {
                throw new Exception("O entregador " + nomeEntregador + " não possui a entrega ID: " + idEntrega);
            }

            entregador.remover(entregaParaRemover);
            entregaParaRemover.setEntregador(null);

            EntregadorRepositorio.atualizar(entregador);
            EntregaRepositorio.atualizar(entregaParaRemover);
            
            CRUDRepositorio.commit();
        } catch (Exception e) {
            CRUDRepositorio.rollback();
            throw e;
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }
    
    

    public static void alterarEntregador(Entregador e) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            CRUDRepositorio.begin();
            EntregadorRepositorio.atualizar(e);
            CRUDRepositorio.commit();
        } catch (Exception ex) {
            CRUDRepositorio.rollback();
            throw ex;
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }

    // -------------------------------------------------------------------------
    //  EXCLUSÃO
    // -------------------------------------------------------------------------

    public static void apagarPedido(int id) throws Exception {
        PedidoRepositorio.conectar();
        try {
            CRUDRepositorio.begin();
            
            Pedido p = PedidoRepositorio.ler(id);
            if (p == null) throw new Exception("Pedido inexistente ID: " + id);

            Entrega e = p.getEntrega();
            if (e != null) {
                e.remover(p);
                p.setEntrega(null);
                EntregaRepositorio.atualizar(e);
            }

            PedidoRepositorio.apagar(p);
            CRUDRepositorio.commit();
        } catch (Exception e) {
            CRUDRepositorio.rollback();
            throw e;
        } finally {
            PedidoRepositorio.desconectar();
        }
    }

    public static void excluirEntregador(String nome) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            CRUDRepositorio.begin();
            
            Entregador e = EntregadorRepositorio.ler(nome);
            if (e == null) throw new Exception("Entregador não encontrado: " + nome);

            // Desvincula todas as entregas antes de apagar o entregador
            if (!e.getListaEntregas().isEmpty()) {
                List<Entrega> entregas = new ArrayList<>(e.getListaEntregas());
                for (Entrega ent : entregas) {
                    ent.setEntregador(null); 
                    EntregaRepositorio.atualizar(ent);
                }
                e.getListaEntregas().clear();
                EntregadorRepositorio.atualizar(e);
            }

            EntregadorRepositorio.apagar(e);
            CRUDRepositorio.commit();
        } catch (Exception ex) {
            CRUDRepositorio.rollback();
            throw ex;
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }
    
    // -------------------------------------------------------------------------
    //  ADICIONAR PEDIDO NA ENTREGA (Vinculo)
    // -------------------------------------------------------------------------

    public static void AddPedidoNaEntrega(int idPedido, int idEntrega) throws Exception {
        PedidoRepositorio.conectar(); 
        try {
            CRUDRepositorio.begin();
            
            Pedido p = PedidoRepositorio.ler(idPedido);
            if (p == null) throw new Exception("Pedido não existe: " + idPedido);
            if (p.getEntrega() != null) throw new Exception("Pedido já tem entrega");

            Entrega e = EntregaRepositorio.ler(idEntrega);
            if (e == null) throw new Exception("Entrega não existe: " + idEntrega);
            
            // Máximo 2 pedidos
            if (e.getListaPedidos().size() >= 2) {
                throw new Exception("Regra violada: A entrega " + idEntrega + " já atingiu o limite de 2 pedidos.");
            }
            
            // Vincula dos dois lados 
            e.adicionar(p);
            
            // Atualiza ambos no banco
            PedidoRepositorio.atualizar(p);
            EntregaRepositorio.atualizar(e);
            
            CRUDRepositorio.commit();
        } catch (Exception ex) {
            CRUDRepositorio.rollback();
            throw ex;
        } finally {
            PedidoRepositorio.desconectar();
        }
    }

    // -------------------------------------------------------------------------
    //  LISTAGENS E CONSULTAS
    // -------------------------------------------------------------------------

    public static List<Entregador> listarEntregador() {
        EntregadorRepositorio.conectar();
        try {
            List<Entregador> lista = EntregadorRepositorio.listar();
            
            if (lista == null) return new ArrayList<>();

            for (Entregador e : lista) {
                e.getListaEntregas().size(); 
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return new ArrayList<>(); 
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }

    public static List<Entrega> listarEntregas() {
        EntregaRepositorio.conectar();
        try { 
            List<Entrega> lista = EntregaRepositorio.listar();
            if (lista == null) return new ArrayList<>();

            for(Entrega e : lista) {
                if(e.getEntregador() != null) e.getEntregador().getNome();
                e.getListaPedidos().size();
            }
            return lista;
        } 
        finally { 
            EntregaRepositorio.desconectar(); 
        }
    }

    public static List<Pedido> listarPedidos() {
        PedidoRepositorio.conectar();
        try { 
            return PedidoRepositorio.listar(); 
        } 
        finally { 
            PedidoRepositorio.desconectar(); 
        }
    }
    
    public static Entregador localizarEntregador(String nome) {
        EntregadorRepositorio.conectar();
        try {
            Entregador e = EntregadorRepositorio.ler(nome);
            if (e != null) {
                e.getListaEntregas().size(); 
            }
            return e;
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }
    
    public static void alterarFotoEntregador(String nome, byte[] foto) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            CRUDRepositorio.begin();
            Entregador ent = EntregadorRepositorio.ler(nome);
            if (ent == null) throw new Exception("Entregador não encontrado");
            
            ent.setFoto(foto); 
            EntregadorRepositorio.atualizar(ent); 
            
            CRUDRepositorio.commit();
        } catch (Exception ex) {
            CRUDRepositorio.rollback();
            throw ex;
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }
    // Consultas específicas
    public static List<Entrega> consultarEntregasPorData(String data) {
        EntregaRepositorio.conectar();
        try { 
            return EntregaRepositorio.EntregasNaData(data); 
        } 
        finally { 
            EntregaRepositorio.desconectar(); 
        }
    }

    public static List<Entrega> consultarEntregadoresProdutivos(int n) {
        EntregaRepositorio.conectar();
        try { 
            
            return EntregaRepositorio.consultarEntregasComMaisDeNPedidos(n); 
        } 
        finally { 
            EntregaRepositorio.desconectar(); 
        }
    }

    public static List<Pedido> consultarPedidosPorEntregador(String nomeEntregador) {
        PedidoRepositorio.conectar();
        try { 
            return PedidoRepositorio.PedidosPorEntregador(nomeEntregador); 
        } 
        finally { 
            PedidoRepositorio.desconectar(); 
        }
    }
}