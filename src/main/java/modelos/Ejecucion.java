package modelos;

/**
 *
 * Clase para recoger los datos de las ejecuciones
 * 
 * @author Raúl Arriaga Carmona
 */
public class Ejecucion {
	
	private int id_actuacion;
	private int id_cuadrilla;

	public Ejecucion() {
	}

	public int getId_actuacion() {
		return id_actuacion;
	}

	public void setId_actuacion(int id_actuacion) {
		this.id_actuacion = id_actuacion;
	}

	public int getId_cuadrilla() {
		return id_cuadrilla;
	}

	public void setId_cuadrilla(int id_cuadrilla) {
		this.id_cuadrilla = id_cuadrilla;
	}
	
	@Override
	public String toString() {
		return "Pertenece a la acutación: " + id_actuacion;
	}
	
}
