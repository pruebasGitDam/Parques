package modelos;

/**
 *
 * Clase para recoger los datos de las cuadrillas
 * 
 * @author Raúl Arriaga Carmona
 */
public class Cuadrilla {
	
	private int id_cuadrilla;
	private String nombre_cuadrilla;
	private int id_jefe;
	
	public Cuadrilla() {}

	public int getId_cuadrilla() {
		return id_cuadrilla;
	}

	public void setId_cuadrilla(int id_cuadrilla) {
		this.id_cuadrilla = id_cuadrilla;
	}

	public String getNombre_cuadrilla() {
		return nombre_cuadrilla;
	}

	public void setNombre_cuadrilla(String nombre_cuadrilla) {
		this.nombre_cuadrilla = nombre_cuadrilla;
	}

	public int getId_jefe() {
		return id_jefe;
	}

	public void setId_jefe(int id_jefe) {
		this.id_jefe = id_jefe;
	}
	
	@Override
	public String toString() {
		return "La cuadrilla se llama: " + nombre_cuadrilla;
	}
	
}
