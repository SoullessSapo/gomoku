/**
 * 
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Juego implements Serializable {
	
	
	public abstract void configurarJuego(int numFichasEspeciales, int cantidadTotalFichas) throws GomokuException;
	public abstract boolean terminarJuego(Player p,Ficha[][] tablero) throws GomokuException;
	 public abstract void iniciarJuego ();
	 /**
	    * Metodo para verificar si hay 5 en raya.
	    * @param posiciones de las fichas del jugador.
	    * @param color del jugador actual.
	    * @param tablero del juego
	    * @return booleano verificando si hay 5 en raya.
	     */
	    public boolean verificar5EnRaja(ArrayList<int[]> posiciones,String color,Ficha[][] tablero) {
	    	ArrayList<int[]> ordenadoFila = MergeSort2D.mergeSort(posiciones, 0);
	    	ArrayList<int[]> ordenadoColumna = MergeSort2D.mergeSort(posiciones, 1);
	    	ArrayList<int[]> ordenadoDiagonal1 = MergeSort2D.mergeSort(posiciones, 2);
	    	ArrayList<int[]> ordenadoDiagonal2 = MergeSort2D.mergeSort(posiciones, 3);
	    	if(verificarFila(ordenadoFila,color,tablero)) {
	    		return true;	
	    	}else if(verificarColumna(ordenadoColumna,color,tablero)) {
	    		return true;	
	    	}else if(verificarDiagonal1(ordenadoDiagonal1,color,tablero)) {
	    		return true;
	    	}else if(verificarDiagonal2(ordenadoDiagonal2,color,tablero)) {
	    		return true;
	    	}	 
	    	return false;
	        
	    }
	    /**
	     * Metodo para verificar si hay 5 en raya por columnas.
	     * @param posiciones de las fichas del jugador.
	     * @param color del jugador actual.
	     * @param tablero del juego
	     * @return booleano verificando si hay 5 en raya.
	     */
	    public boolean verificarColumna(ArrayList<int[]> posiciones,String color,Ficha[][] tablero) {
	    	int i = 0;
	    	int cont;
	    	do {
	    		int posx = posiciones.get(i)[0];
	        	int posy = posiciones.get(i)[1];
	        	cont = 0;
	        	while(posx>= 0 && posx < tablero.length && tablero[posx][posy] != null && tablero[posx][posy].getColor().equals(color) && cont < 5) {
	    	    	cont++;
	    	    	posx++;
	    	    }
	        	i++;
	    	}while(cont < 5 && i < posiciones.size());
	    	return cont >= 5;
	    }
	    /**
	     * Metodo para verificar si hay 5 en raya por filas.
	     * @param posiciones de las fichas del jugador.
	     * @param color del jugador actual.
	     * @param tablero del juego
	     * @return booleano verificando si hay 5 en raya.
	     */
	    public boolean verificarFila(ArrayList<int[]> posiciones,String color,Ficha[][] tablero) {
	    	int i = 0;
	    	int cont;
	    	do {
		    	int posx = posiciones.get(i)[0];
		    	int posy = posiciones.get(i)[1];
		    	cont = 0;
			    while(posy>= 0 && posy < tablero[0].length && tablero[posx][posy] != null && tablero[posx][posy].getColor().equals(color) && cont < 5) {
			    	cont++;
			    	posy++;
			    }
			    i++;
	    	}while(cont < 5 && i < posiciones.size());
	    	return cont >= 5;
	    }
	    /**
	     * Metodo para verificar si hay 5 en raya por diagonal principal.
	     * @param posiciones de las fichas del jugador.
	     * @param color del jugador actual.
	     * @param tablero del juego
	     * @return booleano verificando si hay 5 en raya.
	     */
	    public boolean verificarDiagonal1(ArrayList<int[]> posiciones,String color,Ficha[][] tablero) {
	    	int i = 0;
	    	int cont;
	    	do {
		    	int posx = posiciones.get(i)[0];
		    	int posy = posiciones.get(i)[1];
		    	cont = 0;
		    	while(posy>= 0 && posy < tablero[0].length && posx < tablero.length && posx >= 0 && tablero[posx][posy] != null && tablero[posx][posy].getColor().equals(color) && cont < 5) {
		    		cont++;
		    		posx++;
		    		posy++;
		    	}
		    	i++;
	    	}while(cont < 5 && i < posiciones.size());
	    	
	    	return cont >= 5;
	    }
	    /**
	     * Metodo para verificar si hay 5 en raya por diagonal secundaria.
	     * @param posiciones de las fichas del jugador.
	     * @param color del jugador actual.
	     * @param tablero del juego
	     * @return booleano verificando si hay 5 en raya.
	     */
	    public boolean verificarDiagonal2(ArrayList<int[]> posiciones,String color,Ficha[][] tablero) {
	    	int i = 0;
	    	int cont;
	    	do {
	    		int posx = posiciones.get(i)[0];
		    	int posy = posiciones.get(i)[1];
		    	cont = 0;
		    	while(posx >= 0 && posx < tablero.length && posy >= 0 && posy < tablero[0].length
		    			&& tablero[posx][posy] != null && tablero[posx][posy].getColor().equals(color) && cont < 5) {
		    		cont++;
		    		posx++;
		    		posy--;
		    	}
		    	i++;
	    	}while(cont < 5 && i < posiciones.size());
	    	return cont >= 5;
	    }
}
