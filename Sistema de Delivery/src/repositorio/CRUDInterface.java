package repositorio;

import java.util.List;

public interface CRUDInterface<T> {
	public void criar(T objeto);
	public T atualizar(T objeto);
	public void deletar(T objeto);
	public T localizar(Object chave);
	public List<T> listar();
}