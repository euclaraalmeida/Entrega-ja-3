/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persist�ncia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package repositorio;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import util.Util;
import util.ControleID;

public abstract class CRUDRepositorio<T> {
	// opera��es CRUD para persistir qualquer tipo de objeto T

	public void conectar() {
		Util.conectarBanco();
	}

	public void desconectar() {
		Util.desconectar();
	}

	public void criar(T objeto) {
		Util.getManager().store(objeto);
	}

	public void atualizar(T objeto) {
		Util.getManager().store(objeto);
	}

	public abstract T ler(Object chave);

	public void apagar(T objeto) {
		Util.getManager().delete(objeto);
	}

	public List<T> listar() {
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		Query q = Util.getManager().query();
		q.constrain(type);
		List<T> resultado = q.execute();
		return new ArrayList<>(resultado);
	}

	public void begin() {
		// nao faz nada, pois o db4o inicia uma transa��o automaticamente
	}

	public void commit() {
		Util.getManager().commit();
	}

	public void rollback() {
		Util.getManager().rollback();
	}

	public void resetarID(int valorInicial) {
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		ControleID.resetarRegistroID(type, valorInicial);
	}
}
