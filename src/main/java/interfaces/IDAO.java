package interfaces;

/**
 * Interfaz de las clases DAO
 * 
 * @author rulo
 * 
 * @param <S> clase a definir 2
 */
public interface IDAO<S> {
	
	/**
	 * Todos los DAO deben implementar el método para comprobar si el objeto
	 * parametrizado existe en la base de datos
	 * 
	 * @param s Objeto de la clase S definida en la clase DAO
	 * @return true o false dependiendo de si el objeto existe
	 */
	boolean getExiste(S s);
	
}
