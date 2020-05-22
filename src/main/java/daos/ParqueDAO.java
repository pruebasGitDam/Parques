package daos;

import interfaces.IDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelos.Parque;

/**
 *
 * @author rulo
 */
public class ParqueDAO extends BaseDAO implements IDAO<Parque, String> {

	private final static String SELECT_POR_NOMBRE = "SELECT * FROM parques WHERE nombre_parque = ?";
	private final static String SELECT_TODOS_ID = "SELECT id_parque FROM parques";
	private final static String SELECT_NUM_PAR = "SELECT * FROM parques";
	private final static String INSERT_NUEVO = "INSERT INTO parques (id_parque, nombre_parque, extension, id_ciudad) VALUES(?, ?, ?, ?)";
	private final static String UPDATE = "UPDATE parques SET nombre_parque = ?, extension = ?, id_ciudad = ? WHERE nombre_parque = ?";
	private final static String SELECT_POR_CAD = "SELECT * FROM parques WHERE nombre_parque LIKE '%?%'";
	private final static String COMMIT = "COMMIT;";
	
	private static PreparedStatement ps;
	private static ResultSet rs;

	public static ArrayList<Parque> getPorCadena(String cadena) {
		Parque parque;
		ArrayList<Parque> parques = new ArrayList<>();
		conectar();
		
		try {
			String cad = "SELECT * FROM parques WHERE nombre_parque LIKE '%"+ cadena + "%'";
			//String cad = "SELECT * FROM parques WHERE nombre_parque LIKE '%"+ cadena + "%'";
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

	@Override
	public int consultarTodo() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean getExiste(String nombreParque) {
		return existeParque(nombreParque);
	}
	
	private boolean existeParque(String nombreParque) {
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
