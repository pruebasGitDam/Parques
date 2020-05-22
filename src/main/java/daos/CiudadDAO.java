package daos;

import interfaces.IDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelos.Ciudad;
import modelos.Parque;

public class CiudadDAO extends BaseDAO implements IDAO<String> {

	private final static String SELECT = "SELECT id_ciudad FROM ciudades WHERE nombre_ciudad = ?";
	private final static String SELECT_POR_ID = "SELECT * FROM ciudades WHERE id_ciudad = ?";
	private final static String SELECT_POR_CIUDAD = "SELECT * FROM parques p INNER JOIN ciudades c ON p.id_ciudad = c.id_ciudad WHERE c.nombre_ciudad = ?";
	private final static String SELECT_POR_CCAA = "SELECT * FROM parques p INNER JOIN ciudades c ON p.id_ciudad = c.id_ciudad WHERE c.ccaa = ?";
	private final static String SELECT_POR_CIU_Y_EXT = "SELECT p.nombre_parque FROM parques p INNER JOIN ciudades c ON p.id_ciudad = c.id_ciudad WHERE c.nombre_ciudad = ? AND p.extension > ?";
	private final static String SELECT_CIU_POR_EXT =
			"SELECT c.id_ciudad, c.nombre_ciudad, c.ccaa, SUM(p.extension) FROM parques p INNER JOIN ciudades c ON p.id_ciudad = c.id_ciudad GROUP BY c.id_ciudad, c.nombre_ciudad, c.ccaa HAVING SUM(p.extension) > ?";

	private static PreparedStatement ps;
	private static ResultSet rs;

	/**
	 * Se buscarán en la base de datos todas las ciudades cuyos parques sumen una
	 * extensión introducida y se guardarán en una lista.
	 * 
	 * @param extensionTotal la extensión de todos los parques.
	 * @return lista con objetos de la clase Ciudad con las ciudades que cumplan el requisito.
	 */
	public static ArrayList<Ciudad> getCiuPorExt(int extensionTotal) {
		conectar();

		ArrayList<Ciudad> ciudades = new ArrayList<>();
		Ciudad ciudad;

		try {
			ps = conexion.prepareStatement(SELECT_CIU_POR_EXT);
			ps.setInt(1, extensionTotal);
			rs = ps.executeQuery();

			while (rs.next()) {
				ciudad = new Ciudad();

				ciudad.setId_ciudad(rs.getInt(1));
				ciudad.setNombre_ciudad(rs.getString(2));
				ciudad.setCcaa(rs.getString(3));

				ciudades.add(ciudad);
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getCiuPorExt\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getCiuPorExt\n" + e.toString());
			}
		}

		return ciudades;
	}

	/**
	 * Se buscarán los parques asociados a una ciudad en la base de datos cuyas extensiones sean mayor
	 * que la introducida y se guardarán en una lista.
	 * 
	 * @param nombreCiudad nombre de la ciudad asociada a los parques.
	 * @param extension extensión mínima que deben tener los parques.
	 * @return lista de objetos de la clase Parque con los que cumplan el requisito.
	 */
	public static ArrayList<Parque> getPorCiuYext(String nombreCiudad, int extension) {
		conectar();

		ArrayList<Parque> parques = new ArrayList<>();
		ArrayList<String> nombresParques = new ArrayList<>();
		Parque parque;

		try {
			ps = conexion.prepareStatement(SELECT_POR_CIU_Y_EXT);
			ps.setString(1, nombreCiudad);
			ps.setInt(2, extension);
			rs = ps.executeQuery();

			while (rs.next()) {
				nombresParques.add(rs.getString(1));
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getPorCiuYext\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getPorCiuYext\n" + e.toString());
			}
		}

		for (String park : nombresParques) {
			parque = new Parque();

			parque.setId_parque(ParqueDAO.getParque(park).getId_parque());
			parque.setNombre_parque(ParqueDAO.getParque(park).getNombre_parque());
			parque.setExtension(ParqueDAO.getParque(park).getExtension());
			parque.setId_ciudad(ParqueDAO.getParque(park).getId_ciudad());

			parques.add(parque);
		}

		return parques;
	}

	/**
	 * Método para recuperar el nombre de una ciudad por su id.
	 *
	 * @param id el id de la ciudad a consultar.
	 * @return objeto de la clase Ciudad con los datos de la ciudad encontrada.
	 */
	public static Ciudad getCiudadPorId(int id) {
		conectar();

		Ciudad ciudad = new Ciudad();
		try {
			ps = conexion.prepareStatement(SELECT_POR_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				ciudad.setId_ciudad(rs.getInt(1));
				ciudad.setNombre_ciudad(rs.getString(2));
				ciudad.setCcaa(rs.getString(3));
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getNombreCiudad\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getNombreCiudad\n" + e.toString());
			}
		}

		return ciudad;
	}

	/**
	 * Se buscarán en la abse de datos todos los parques asociados a una ciudad y se guardarán
	 * en una lista.
	 * 
	 * @param nombreCiudad el nombre de la ciudad de la que se quieren buscar los parques.
	 * @return lista de objetos de la clase Parque con los que están asociados a la ciudad.
	 */
	public static ArrayList<Parque> getParquesPorCiudad(String nombreCiudad) {
		conectar();

		ArrayList<Parque> parques = new ArrayList<>();
		Parque parque;

		try {
			ps = conexion.prepareStatement(SELECT_POR_CIUDAD);
			ps.setString(1, nombreCiudad);
			rs = ps.executeQuery();

			while (rs.next()) {
				parque = new Parque();

				int id_parque = rs.getInt(1);
				String nombre_parque = rs.getString(2);
				int extension = rs.getInt(3);
				int id_ciudad = rs.getInt(4);

				parque.setId_parque(id_parque);
				parque.setNombre_parque(nombre_parque);
				parque.setExtension(extension);
				parque.setId_ciudad(id_ciudad);

				parques.add(parque);
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Fallo en getParquesPorCiudad\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException ex) {
				System.out.println("Fallo en cierre de conexión de getParquesPorCiudad\n" + ex.toString());
			}
		}

		return parques;
	}

	/**
	 * Se buscarán en la abse de datos todos los parques asociados a una CCAA y se guardarán
	 * en una lista.
	 * 
	 * @param CCAA el nombre de la CCAA de la que se quieren buscar los parques.
	 * @return lista de objetos de la clase Parque con los que están asociados a la CCAA.
	 */
	public static ArrayList<Parque> getParquesPorCCAA(String CCAA) {
		conectar();

		ArrayList<Parque> parques = new ArrayList<>();
		Parque parque;

		try {
			ps = conexion.prepareStatement(SELECT_POR_CCAA);
			ps.setString(1, CCAA);
			rs = ps.executeQuery();

			while (rs.next()) {
				parque = new Parque();

				int id_parque = rs.getInt(1);
				String nombre_parque = rs.getString(2);
				int extension = rs.getInt(3);
				int id_ciudad = rs.getInt(4);

				parque.setId_parque(id_parque);
				parque.setNombre_parque(nombre_parque);
				parque.setExtension(extension);
				parque.setId_ciudad(id_ciudad);

				parques.add(parque);
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Fallo en getParquesPorCCAA\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException ex) {
				System.out.println("Fallo en el finally de getParquesPorCCAA\n" + ex.toString());
			}
		}

		return parques;
	}

	/**
	 * Se buscará en la base de datos si existe una ciudad asociada al nombre introducido.
	 * 
	 * @param nombreCiudad nombre de la ciudad de la que se buscará el ID.
	 * @return ID de la ciudad.
	 */
	public int getIdCiudad(String nombreCiudad) {
		conectar();
		int idCiudad = 0;
		try {
			ps = conexion.prepareStatement(SELECT);
			ps.setString(1, nombreCiudad);
			rs = ps.executeQuery();

			while (rs.next()) {
				idCiudad = rs.getInt("id_ciudad");
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en getIdCiudad\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en el finally de getIdCiudad\n" + e.toString());
			}
		}

		return idCiudad;
	}

	/**
	 * Implementación del método de la interface IDAO.
	 * Se comprobará si existe la ciudad con el nombre introducido en la base de datos.
	 * 
	 * @param nombreCiudad nombre de la ciudad a comprobar.
	 * @return true o false dependiendo de si existe o no.
	 */
	@Override
	public boolean getExiste(String nombreCiudad) {
		conectar();
		boolean existe = false;

		try {
			ps = conexion.prepareStatement(SELECT);
			ps.setString(1, nombreCiudad);
			rs = ps.executeQuery();

			if (rs.next()) {
				existe = true;
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Fallo en existeCiudad\n" + e.toString());
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("Fallo en finally de existeCiudad\n" + e.toString());
			}
		}
		return existe;
	}

}
