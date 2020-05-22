package daos;

import interfaces.IDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelos.Parque;

public class ParqueDAO extends BaseDAO implements IDAO<String> {

	private final static String SELECT_POR_NOMBRE = "SELECT * FROM parques WHERE nombre_parque = ?";
	private final static String SELECT_TODOS_ID = "SELECT id_parque FROM parques";
	private final static String SELECT_NUM_PAR = "SELECT * FROM parques";
	private final static String INSERT_NUEVO = "INSERT INTO parques (id_parque, nombre_parque, extension, id_ciudad) VALUES(?, ?, ?, ?)";
	private final static String UPDATE = "UPDATE parques SET nombre_parque = ?, extension = ?, id_ciudad = ? WHERE nombre_parque = ?";
	private final static String BORRA_PARQUES = "DELETE FROM parques where id_parque = ?";
	private final static String SELECT_POR_CAD = "SELECT * FROM parques WHERE nombre_parque LIKE '%?%'";


	private static PreparedStatement ps;
	private static ResultSet rs;
	
	/**
	 * Se buscarán los parques que estén asociados a la ciudad introducida, se guardarán en una
	 * lista y se borrarán de la base de datos.
	 * 
	 * @param nombreCiudad el nombre de la ciudad a la que se van a borrar los parques.
	 * @return el número de parques que se han borrado.
	 */
	public static int borraParques(String nombreCiudad) {
		int numParquesIniciales = ParqueDAO.getNumParques();
		ArrayList<Parque> parquesAborrar = CiudadDAO.getParquesPorCiudad(nombreCiudad);

		parquesAborrar.forEach((object) -> {

			conectar();
			
			try {
				ps = conexion.prepareStatement(BORRA_PARQUES);
				ps.setInt(1, object.getId_parque());
				ps.executeQuery();

				ps.close();
			} catch (SQLException e) {
				System.out.println("Fallo en borraParques\n" + e.toString());
			} finally {
				try {
					conexion.close();
				} catch (SQLException e) {
					System.out.println("Fallo en el finally de borraParques\n" + e.toString());
				}
			}

		});
		
		return numParquesIniciales - getNumParques();
	}

	/**
	 * Se buscará en la base de datos todos los parques cuyo nombre contenga
	 * el patrón introducido y se devolverán en una lista.
	 * 
	 * @param cadena el patrón a buscar en los nombres de los parques.
	 * @return lista con los parques.
	 */
	public static ArrayList<Parque> getPorCadena(String cadena) {
		Parque parque;
		ArrayList<Parque> parques = new ArrayList<>();
		conectar();
		
		try {
			String cad = "SELECT * FROM parques WHERE nombre_parque LIKE '%"+ cadena + "%'";
			ps = conexion.prepareStatement(cad);
			//ps = conexion.prepareStatement(SELECT_POR_CAD);
			//ps.setString(1, cadena);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				parque = new Parque();
				
				parque.setId_parque(rs.getInt("id_parque"));
				parque.setNombre_parque(rs.getString("nombre_parque"));
				parque.setExtension(rs.getInt("extension"));
				parque.setId_ciudad(rs.getInt("id_ciudad"));
				
				parques.add(parque);
			}
			
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getPorCadena\n" + e.toString());
		}
		finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getPorCadena\n" + e.toString());
			}
		}
		return parques;
	}
	
	/**
	 * Se actualizarán los datos de un parque de la base de datos.
	 * 
	 * @param parqueViejo objeto de la clase Parque con los datos existentes.
	 * @param parqueNuevo objeto de la clase Parque con los nuevos datos.
	 * @return true o false dependiendo de si se ha actualizado el parque o no.
	 */
	public static boolean updateParque(Parque parqueViejo, Parque parqueNuevo) {
		boolean actualizado = false;
		conectar();
		
		try {
			ps = conexion.prepareStatement(UPDATE);
			ps.setString(1, parqueNuevo.getNombre_parque());
			ps.setInt(2, parqueNuevo.getExtension());
			ps.setInt(3, parqueNuevo.getId_ciudad());
			ps.setString(4, parqueViejo.getNombre_parque());
			
			rs = ps.executeQuery();
			
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en updateParque\n" + e.toString());
		}
		finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de updateParque\n" + e.toString());
			}
		}
		return actualizado;
	}
	
	/**
	 * Se buscará en la base de datos si existe un parque cuyo nombre coincida con
	 * el introducido.
	 * 
	 * @param nombreParque nombre del parque que se quiere consultar.
	 * @return objeto de la clase Parque encontrado.
	 */
	public static Parque getParque(String nombreParque){
		conectar();
		Parque parque = new Parque();
		
		try {
			ps = conexion.prepareStatement(SELECT_POR_NOMBRE);
			ps.setString(1, nombreParque);
			rs = ps.executeQuery();

			while (rs.next()) {
				parque.setNombre_parque(rs.getString("nombre_parque"));
				parque.setExtension(rs.getInt("extension"));
				parque.setId_ciudad(rs.getInt("id_ciudad"));
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getParque\n" + e.toString());
		}
		finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en finally de getParque\n" + e.toString());
			}
		}
		return parque;
	}
	
	/**
	 * Se insertará un nuevo parque en la base de datos.
	 * 
	 * @param parque objeto de la clase Parque con los datos del parque a insertar.
	 * @return 0 si el parque no se ha insertado. 1 si el parque ha sido añadido a la base de datos.
	 */
	public static int insertarParque(Parque parque) {
		int numParquesInsertados = getNumParques();
		conectar();

		try {
			ps = conexion.prepareStatement(INSERT_NUEVO);
			ps.setInt(1, parque.getId_parque());
			ps.setString(2, parque.getNombre_parque());
			ps.setInt(3, parque.getExtension());
			ps.setInt(4, parque.getId_ciudad());

			rs = ps.executeQuery();

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en insertarParque\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en finally de insertarParque\n" + e.toString());
			}
		}

		numParquesInsertados = getNumParques() - numParquesInsertados;
		return numParquesInsertados;
	}

	/**
	 * Se realizará un conteo de las filas que contiene la tabla parque en la base
	 * de datos para saber cuantos parques hay.
	 * 
	 * @return el número de parques existentes en la base de datos.
	 */
	public static int getNumParques() {
		conectar();
		int numParques = 0;

		try {
			ps = conexion.prepareStatement(SELECT_NUM_PAR);
			rs = ps.executeQuery();

			while (rs.next()) {
				numParques++;
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getNumParques\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException ex) {
				System.out.println("Fallo en el finally de getNumParques\n" + ex.toString());
			}
		}
		return numParques;
	}

	/**
	 * Se recorren las filas de la tabla de parques y se guardan los ID de cada uno
	 * en un array.
	 * 
	 * @return array de ID's de los parques de la base de datos.
	 */
	public static int[] getIDs() {
		int[] ids = new int[getNumParques()];
		conectar();
		int cont = 0;

		try {
			ps = conexion.prepareStatement(SELECT_TODOS_ID);
			rs = ps.executeQuery();

			while (rs.next()) {
				ids[cont] = rs.getInt("id_parque");
				cont++;
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getIDs\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getIDs\n" + e.toString());
			}
		}

		return ids;
	}

	/**
	 * Implementación del método de la interface IDAO.
	 * Se comprobará si existe el parque con el nombre introducido en la base de datos.
	 * 
	 * @param nombreParque nombre del parque a comprobar.
	 * @return true o false dependiendo de si existe o no.
	 */
	@Override
	public boolean getExiste(String nombreParque) {
		conectar();
		boolean existe = false;
		
		try {
			ps = conexion.prepareStatement(SELECT_POR_NOMBRE);
			ps.setString(1, nombreParque);
			rs = ps.executeQuery();

			if(rs.next()) {
				existe = true;
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en existeParque\n" + e.toString());
		}
		finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en finally de existeParque\n" + e.toString());
			}
		}
		return existe;
	}

}
