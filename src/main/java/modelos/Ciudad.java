package modelos;

/**
 *
 * Clase para recoger los datos de las ciudades
 * 
 * @author Raúl Arriaga Carmona
 */
public class Ciudad {
	
	private int id_ciudad;
	private String nombre_ciudad;
	private String ccaa;

	public Ciudad() {}

	public int getId_ciudad() {
		return id_ciudad;
	}

	public void setId_ciudad(int id_ciudad) {
		this.id_ciudad = id_ciudad;
	}

	public String getNombre_ciudad() {
		return nombre_ciudad;
	}

	public void setNombre_ciudad(String nombre_ciudad) {
		this.nombre_ciudad = nombre_ciudad;
	}

	public String getCcaa() {
		return ccaa;
	}

	public void setCcaa(String ccaa) {
		this.ccaa = ccaa;
	}
	
	@Override
	public String toString() {
		return nombre_ciudad + " (" + ccaa + ")";
	}
	
}
