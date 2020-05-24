package utils;

import daos.CiudadDAO;
import daos.ParqueDAO;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import modelos.Parque;

public class Principal {

	public static void main(String[] args) {
		menuPrincipal();
	}

	/**
	 * Desde aquí se muestra el menú interacción con el usuario.
	 */
	private static void menuPrincipal() {
		int opcion;
		boolean opcionValida = false;

		System.out.println(
				"\n\n[#] Selecciona una opción de las siguientes:\n\n"
				+ "[1] Listar los parques de una ciudad.\n"
				+ "[2] Listar los parques de una CCAA.\n"
				+ "[3] Añadir un parque.\n"
				+ "[4] Actualizar un parque.\n"
				+ "[5] Seleccionar parques por cadena.\n"
				+ "[6] Consultar parques por ciudad y extensión.\n"
				+ "[7] Borrar parques de una ciudad.\n"
				+ "[8] Consultar ciudades por la extensión de sus parques.\n"
				+ "[9] Salir");

		do {
			opcion = lecturaNum();

			if (opcion > 0 && opcion < 10) {
				opcionValida = true;
			} else {
				System.out.println("La opción elejida no es válida, vuelve a seleccionarla.");
			}

		} while (!opcionValida);

		if (opcion < 9) {
			opciones(opcion);
		} else {
			System.out.println("Hasta pronto");
		}

	}

	/**
	 * Según la opción elegida en el menú principal, se ejecuta un case u otro.
	 * 
	 * @param opcion la opción elegida en el menú principal.
	 */
	private static void opciones(int opcion) {
		ArrayList<Parque> parques;

		switch (opcion) {

			case 1:
				System.out.println("Introduce el nombre de la ciudad");

				parques = CiudadDAO.getParquesPorCiudad(lecturaCad());

				imprimeArrays(parques);
				break;

			case 2:
				System.out.println("Introduce el nombre de la CCAA");

				parques = CiudadDAO.getParquesPorCCAA(lecturaCad());

				imprimeArrays(parques);
				break;

			case 3:
				CiudadDAO cdao = new CiudadDAO();
				String nombreCiudad;
				
				System.out.println("Introduce el nombre de la ciudad");
				nombreCiudad = lecturaCad();
				
				if (cdao.getExiste(nombreCiudad)) {
					Parque parqueNuevo = new Parque();
					
					System.out.println("Introduce el nombre del nuevo parque");
					parqueNuevo.setNombre_parque(lecturaCad());
					System.out.println("Introduce la extension del nuevo parque");
					parqueNuevo.setExtension(lecturaNum());
					
					parqueNuevo.setId_parque(AutoID.getNewId(ParqueDAO.getIDs()));
					parqueNuevo.setId_ciudad(cdao.getIdCiudad(nombreCiudad));
					
					if (ParqueDAO.insertarParque(parqueNuevo) != 0) {
						System.out.println("El nuevo parque insertado con éxito");
					} else {
						System.out.println("No se ha podido insertar el nuevo parque");
					}	
				} else {
					System.out.println("La ciudad no se encuentra en la base de datos.");
				}
				break;

			case 4:
				ParqueDAO parqueDAO = new ParqueDAO();
				CiudadDAO ciudadDAO = new CiudadDAO();
				
				System.out.println("Introduce el nombre del parque");
				String nombreParque = lecturaCad();
				
				if (parqueDAO.getExiste(nombreParque)) {
					Parque nuevo = new Parque();
					Parque viejo = ParqueDAO.getParque(nombreParque);
					System.out.println(viejo.toString());
					
					String nuevoNombre;
					String nuevaCiudad;
					int nuevaExtension;
					
					System.out.println("Introduce el nuevo nombre del parque");
					nuevoNombre = lecturaCad();
					System.out.println("Introduce el nombre de la ciudad donde está");
					nuevaCiudad = lecturaCad();
					System.out.println("Introduce la nueva extensión que tiene");
					nuevaExtension = lecturaNum();
					
					if (ciudadDAO.getExiste(nuevaCiudad)) {
						nuevo.setNombre_parque(nuevoNombre);
						nuevo.setId_ciudad(ciudadDAO.getIdCiudad(nuevaCiudad));
						nuevo.setExtension(nuevaExtension);
						
						ParqueDAO.updateParque(viejo, nuevo);
						
						if (parqueDAO.getExiste(nuevoNombre)) {
							System.out.println("El parque ha sido actualizado");
						} else {
							System.out.println("La actualización ha fallado");
						}
						
					} else {
						System.out.println("La ciudad no se encuentra en la base de datos");
					}
					
				} else {
					System.out.println("El parque no se encuentra en la base de datos");
				}
				break;

			case 5:
				System.out.println("Introduce la cadena");
				String cadena = lecturaCad();
				cadena = "%" + cadena + "%";

				parques = ParqueDAO.getPorCadena(cadena);
				imprimeArrays(parques);
				break;

			case 6:
				System.out.println("Introduce el nombre de la ciudad");
				String ciudad = lecturaCad();
				System.out.println("Introduce la extensión");
				int extension = lecturaNum();
				
				imprimeArrays(CiudadDAO.getPorCiuYext(ciudad, extension));
				break;

			case 7:
				System.out.println("Introduce el nombre de la ciudad");
				int borrados = ParqueDAO.borraParques(lecturaCad());
				System.out.println("Se han borrado " + borrados + " parques");
				break;

			case 8:
				System.out.println("Introduce la extensión");
				imprimeArrays(CiudadDAO.getCiuPorExt(lecturaNum()));
				break;

		}

		menuPrincipal();

	}

	/**
	 * Método interno para realizar de forma controlada las capturas de números.
	 * 
	 * @return el entero introducido por teclado.
	 */
	private static int lecturaNum() {
		boolean valido = false;
		Scanner lector = new Scanner(System.in);
		int numPedido = 0;

		do {
			try {
				numPedido = lector.nextInt();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("¡Opss! Algo ha fallado.");
			}
		} while (!valido && numPedido != 0);

		return numPedido;
	}

	/**
	 * Método interno para realizar de forma controlada las capturas de cadenas.
	 * 
	 * @return la cadena introducida por teclado.
	 */
	private static String lecturaCad() {
		boolean valido = false;
		Scanner lector = new Scanner(System.in);
		String cadPedida = "";

		do {
			try {
				cadPedida = lector.nextLine();
				valido = true;
			} catch (InputMismatchException e) {
				System.out.println("¡Opss! Algo ha fallado.");
			}
		} while (!valido && !cadPedida.equals(""));

		return cadPedida;
	}

	/**
	 * Método interno para imprimir los datos de los objetos que contiene una lista.
	 * 
	 * @param arrayList lista con los objetos a imprimir.
	 */
	private static void imprimeArrays(ArrayList arrayList) {
		if (arrayList.isEmpty()) {
			System.out.println("No hay datos que mostrar");
		} else {
			arrayList.forEach((object) -> {
				System.out.println(object.toString());
			});
		}
	}

}
