/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * 
 * @author rulo
 * @param <O>
 * @param <S>
 */
public interface IDAO<O,S> {
	
	int consultarTodo();
	
	boolean getExiste(S s); 
	
}
