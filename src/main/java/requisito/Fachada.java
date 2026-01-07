package requisito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
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
            PedidoRepositorio.begin();
            // O ID será gerado automaticamente pelo ControleID ao salvar
            Pedido p = new Pedido(valor, descricao, localizacao);
            PedidoRepositorio.criar(p);
            PedidoRepositorio.commit();
        } catch (Exception e) {
            PedidoRepositorio.rollback();
            throw e;
        } finally {
            PedidoRepositorio.desconectar();
        }
    }

    public static void CriarEntrega(String data, String nomeEntregador) throws Exception {
        EntregaRepositorio.conectar();
        try {
            EntregaRepositorio.begin();
            try {
                LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                throw new Exception("Formato data invalido: " + data);
            }
            
            Entregador entregador = EntregadorRepositorio.lerEntregador(nomeEntregador);
            if (entregador == null) {
                throw new Exception("Entregador não encontrado: " + nomeEntregador);
            }

            // O ID será gerado automaticamente
            Entrega e = new Entrega(data);
            e.setEntregador(entregador);
            entregador.adicionar(e);

            EntregaRepositorio.criar(e);
            EntregadorRepositorio.atualizar(entregador);
            
            EntregaRepositorio.commit();
        } catch (Exception e) {
            EntregaRepositorio.rollback();
            throw e;
        } finally {
            EntregaRepositorio.desconectar();
        }
    }
    
    public static void CriarEntregador(String nome) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            EntregadorRepositorio.begin();

            // 1. Verificação de duplicidade melhorada
            Entregador existente = EntregadorRepositorio.lerEntregador(nome);
            if (existente != null) {
                // Lança erro para o "front-end" ou console saber que falhou
                throw new Exception("Erro: O entregador '" + nome + "' já está cadastrado."); 
            }

            // 2. Criação
            Entregador e = new Entregador(nome);
            EntregadorRepositorio.criar(e);
            
            // 3. Confirmação
            EntregadorRepositorio.commit();
            System.out.println("Entregador criado com sucesso!"); // Log opcional

        } catch (Exception e) {
            // 4. Desfaz mudanças em caso de erro
            EntregadorRepositorio.rollback();
            throw e; // Repassa o erro para quem chamou tratar (ex: mostrar na tela)
        } finally {
            // 5. Sempre fecha a conexão
            EntregadorRepositorio.desconectar();
        }
    }

    // -------------------------------------------------------------------------
    //  ALTERAÇÃO 
    // -------------------------------------------------------------------------

    public static void removerEntregaDoEntregador(String nomeEntregador, int idEntrega) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            EntregadorRepositorio.begin();

            Entregador entregador = EntregadorRepositorio.lerEntregador(nomeEntregador);
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
            
            EntregadorRepositorio.commit();
        } catch (Exception e) {
            EntregadorRepositorio.rollback();
            throw e;
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
            PedidoRepositorio.begin();
            Pedido p = PedidoRepositorio.lerPedido(id);
            if (p == null) throw new Exception("Pedido inexistente ID: " + id);

            Entrega e = p.getEntrega();
            if (e != null) {
                e.remover(p);
                p.setEntrega(null);
                EntregaRepositorio.atualizar(e);
            }

            PedidoRepositorio.apagar(p);
            PedidoRepositorio.commit();
        } catch (Exception e) {
            PedidoRepositorio.rollback();
            throw e;
        } finally {
            PedidoRepositorio.desconectar();
        }
    }

    public static void excluirEntregador(String nome) throws Exception {
        EntregadorRepositorio.conectar();
        try {
            EntregadorRepositorio.begin();
            
            Entregador e = EntregadorRepositorio.lerEntregador(nome);
            if (e == null) throw new Exception("Entregador não encontrado: " + nome);

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
            EntregadorRepositorio.commit();
        } catch (Exception ex) {
            EntregadorRepositorio.rollback();
            throw ex;
        } finally {
            EntregadorRepositorio.desconectar();
        }
    }
    
    // -------------------------------------------------------------------------
    //  OUTRAS OPERAÇÕES
    // -------------------------------------------------------------------------

    public static void AddPedidoNaEntrega(int idPedido, int idEntrega) throws Exception {
        PedidoRepositorio.conectar(); 
        try {
            PedidoRepositorio.begin();
            
            Pedido p = PedidoRepositorio.lerPedido(idPedido);
            if (p == null) throw new Exception("Pedido não existe: " + idPedido);
            if (p.getEntrega() != null) throw new Exception("Pedido já tem entrega");

            Entrega e = EntregaRepositorio.lerEntrega(idEntrega);
            if (e == null) throw new Exception("Entrega não existe: " + idEntrega);
            
            if (e.getListaPedidos().size() >= 2) {
                throw new Exception("Regra violada: A entrega " + idEntrega + " já atingiu o limite de 2 pedidos.");
            }
            
            e.adicionar(p);
            p.setEntrega(e);

            PedidoRepositorio.atualizar(p);
            EntregaRepositorio.atualizar(e);
            
            PedidoRepositorio.commit();
        } catch (Exception ex) {
            PedidoRepositorio.rollback();
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
            List<Entregador> lista = EntregadorRepositorio.ListarEntregador();

            if (lista == null) {
                return new ArrayList<>(); 
            }

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
    public static List<Entrega> listarEntregas(){
        EntregaRepositorio.conectar();
        try { 
            List<Entrega> lista = EntregaRepositorio.ListarEntregas();
            for(Entrega e : lista) {
                if(e.getEntregador() != null) e.getEntregador().getNome();
                e.getListaPedidos().size();
            }
            return lista;
        } 
        finally { EntregaRepositorio.desconectar(); }
    }
    public static List<Pedido> listarPedidos(){
        PedidoRepositorio.conectar();
        try { return PedidoRepositorio.ListarPedidos(); } 
        finally { PedidoRepositorio.desconectar(); }
    }

    public static List<Entrega> consultarEntregasPorData(String data) {
        EntregaRepositorio.conectar();
        try { return EntregaRepositorio.EntregasNaData(data); } 
        finally { EntregaRepositorio.desconectar(); }
    }

    public static List<Entrega> consultarEntregadoresProdutivos(int n) {
        EntregadorRepositorio.conectar();
        try { return EntregaRepositorio.consultarEntregasComMaisDeNPedidos(n); } 
        finally { EntregadorRepositorio.desconectar(); }
    }

    public static List<Pedido> consultarPedidosPorEntregador(String nomeEntregador) {
        PedidoRepositorio.conectar();
        try { return PedidoRepositorio.PedidosPorEntregador(nomeEntregador); } 
        finally { PedidoRepositorio.desconectar(); }
    }
}
