package modelos;

/**
 *
 * Clase para recoger los datos de los jardineros
 * 
 * @author Raúl Arriaga Carmona
 */
public class Jardinero {
	
	private int id_jardinero;
	private String nombre_jardinero;
	private int id_jefe;
	private int id_cuadrilla;

	public Jardinero() {
	}

	public int getId_jardinero() {
		return id_jardinero;
	}

	public void setId_jardinero(int id_jardinero) {
		this.id_jardinero = id_jardinero;
	}

	public String getNombre_jardinero() {
		return nombre_jardinero;
	}

	public void setNombre_jardinero(String nombre_jardinero) {
		this.nombre_jardinero = nombre_jardinero;
	}

	public int getId_jefe() {
		return id_jefe;
	}

	public void setId_jefe(int id_jefe) {
		this.id_jefe = id_jefe;
	}

	public int getId_cuadrilla() {
		return id_cuadrilla;
	}

	public void setId_cuadrilla(int id_cuadrilla) {
		this.id_cuadrilla = id_cuadrilla;
	}
	
	@Override
	public String toString() {
		return "El nombre del jardinero es: " + nombre_jardinero;
	}
	
}
