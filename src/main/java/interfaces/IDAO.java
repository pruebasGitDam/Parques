package interfaces;

/**
 * Interfaz
 * 
 * @author rulo
 * 
 * @param <O>
 * @param <S>
 */
public interface IDAO<O,S> {
	
	int consultarTodo();
	
	boolean getExiste(S s); 
	
}
