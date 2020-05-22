package modelos;

import daos.CiudadDAO;

/**
 *
 * Clase para recoger los datos de los parques
 * 
 * @author Raúl Arriaga Carmona
 */
public class Parque {
	
	private int id_parque;
	private String nombre_parque;
	private int extension;
	private int id_ciudad;

	public Parque() {
	}

	public int getId_parque() {
		return id_parque;
	}

	public void setId_parque(int id_parque) {
		this.id_parque = id_parque;
	}

	public String getNombre_parque() {
		return nombre_parque;
	}

	public void setNombre_parque(String nombre_parque) {
		this.nombre_parque = nombre_parque;
	}

	public int getExtension() {
		return extension;
	}

	public void setExtension(int extension) {
		this.extension = extension;
	}

	public int getId_ciudad() {
		return id_ciudad;
	}

	public void setId_ciudad(int id_ciudad) {
		this.id_ciudad = id_ciudad;
	}
	
	@Override
	public String toString() {
		return "El parque " + nombre_parque + " tiene una extensión de " + extension + " y está en " + CiudadDAO.getCiudadPorId(id_ciudad);
	}
	
}