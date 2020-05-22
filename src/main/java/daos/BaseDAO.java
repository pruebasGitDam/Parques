package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase padre de las conexiones a la base de datos
 * 
 * @author rulo
 */
public class BaseDAO {

	protected static Connection conexion = null;
	private final static String CADENA_CONEXION = "jdbc:oracle:thin:@localhost:1521/XE";
	private final static String USUARIO = "pruebas";
	private final static String PASSWRD = "pruebas";

	/**
	 * Método de conexión a la base de datos
	 * 
	 * @return objeto de la clase Connection listo para usar
	 */
	protected static Connection conectar() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWRD);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}

		return conexion;
	}
}
