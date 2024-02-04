package domain;

import java.io.Serializable;

public abstract class Maquina extends Player implements Serializable {

	public Maquina(String name, String color) {
		super(name,color);
		
	}
	/*
	 * Metodo para jugar en el tablero segun tipo de maquina
	 * @throws GomokuException indicando si ya termino el juego
	 */
	public abstract void jugar() throws GomokuException;
}