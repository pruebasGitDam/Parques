package modelos;

/**
 *
 * Clase para recoger los datos de las actuaciones
 * 
 * @author Raúl Arriaga Carmona
 */
public class Actuacion {
	
	private int id_actuacion;
	private String fecha;
	private int horas_duracion;
	private String tarea;
	private int id_parque;

	public Actuacion() {
	}

	public int getId_actuacion() {
		return id_actuacion;
	}

	public void setId_actuacion(int id_actuacion) {
		this.id_actuacion = id_actuacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getHoras_duracion() {
		return horas_duracion;
	}

	public void setHoras_duracion(int horas_duracion) {
		this.horas_duracion = horas_duracion;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public int getId_parque() {
		return id_parque;
	}

	public void setId_parque(int id_parque) {
		this.id_parque = id_parque;
	}
	
	@Override
	public String toString() {
		return "La actuación consiste en: " + tarea;
	}
	
}
