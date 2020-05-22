package utils;

import java.util.Arrays;

public class AutoID {

	/**
	 * Método que genera un id adaptado a un conjunto.
	 * 
	 * @param arrayID array con los id's a los que hay que adaptar el nuevo
	 * @return id adaptado
	 */
	public static int getNewId(int[] arrayID) {
		int idBuscado = 0;
		Arrays.sort(arrayID);
		if (arrayID.length > 1) {
			for (int i = 0; i < arrayID.length - 1; i++) {
				if ((arrayID[i]+1) != arrayID[i+1]) {
					idBuscado = arrayID[i] + 1;
				}
			}
		} else if (arrayID.length == 1) {
			idBuscado = arrayID[0] + 1;
		} else if (arrayID.length == 0) {
			idBuscado = 1;
		}
		return idBuscado;
	}

}
