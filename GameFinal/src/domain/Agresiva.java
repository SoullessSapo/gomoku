package domain;
import java.util.Random;
import java.util.ArrayList;
import java.util.Random;
/**
 * Constructor de la clase hija Agresiva
 */
public class Agresiva extends Maquina {
	public Agresiva(String name,String color) {
		super(name,color);
	}
	/**
	 *Metodo para elegir la ficha y jugar en el tablero
	 *@throws GomokuException indicando que ya acabo el juego
	 */
	@Override
	public void jugar() throws GomokuException {
	    Ficha[][] tablero = Gomoku.getInstance().getTablero();
	    Player jugadorOtro = Gomoku.getInstance().getPlayer1();
	    String color = jugadorOtro.getColor();
	    ArrayList<int[]> posicionesOtro = jugadorOtro.getPosiciones();
	    int[] posicion = mirarMejorPosicion(posicionesOtro,tablero,color);
	    Gomoku.getInstance().jugarTablero(posicion);
	}
	/**
	 *Metodo para mirar la mejor posicion a poner segun las posiciones del otro jugador
	 *@param ArrayList<int[]> posiciones de las fichas en el tablero del jugador
	 *@param Ficha[][] tablero del juego
	 *@param color del oponente
	 *@throws GomokuException indicando que ya acabo el juego
	 */
	private int[] mirarMejorPosicion(ArrayList<int[]> posiciones,Ficha[][] tablero,String color) {
		int x=0;
		int y = 0;
		int cont = 1;
		do {
			int posx=posiciones.get(posiciones.size()-cont)[0];
			int posy=posiciones.get(posiciones.size()-cont)[1];
			double mayor =Double.NEGATIVE_INFINITY;
			int[][] posicionesVecinos = {
	    			{posx,posy-1},
	    			{posx,posy+1},
	    			{posx-1,posy},
	    			{posx+1,posy},
	    			{posx-1,posy-1},
	    			{posx-1,posy+1},
	    			{posx+1,posy-1},
	    			{posx+1,posy+1}
	    	};
			for(int i = 0;i<posicionesVecinos.length;i++) {
				if(posicionesVecinos[i][0]>=0 && posicionesVecinos[i][0]<tablero.length && posicionesVecinos[i][1]>= 0 && posicionesVecinos[i][1]<tablero[0].length) {
				int[] adelante = contarDelante(posicionesVecinos[i],tablero,color);
				int[] atras = contarAtras(posicionesVecinos[i],tablero,color);
				int[] arriba = contarArriba(posicionesVecinos[i],tablero,color);
				int[] abajo = contarAbajo(posicionesVecinos[i],tablero,color);
				int[] dArriba = contarDiagonalArriba(posicionesVecinos[i],tablero,color);
				int[] dAbajo = contarDiagonalAbajo(posicionesVecinos[i],tablero,color);
				int[] d2Arriba = contarDiagonal2Arriba(posicionesVecinos[i],tablero,color);
				int[] d2Abajo = contarDiagonal2Abajo(posicionesVecinos[i],tablero,color);
				if(adelante[0]>mayor) {
					mayor = adelante[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]+1] == null&&posicionesVecinos[i][1]+1<tablero.length){
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1]+1;
					}else if(adelante[1]>=0&& adelante[1]<tablero.length && adelante[2]>=0 && adelante[2]<tablero.length){
						x = adelante[1];
						y = adelante[2];
					}
				}
				if(atras[0]>mayor) {
					mayor = atras[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]-1] == null&&posicionesVecinos[i][1]-1>0){
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1]-1;
					}else if(atras[1]>=0&&atras[1]<tablero.length && atras[2]>=0&& atras[2]<tablero.length){
						x = atras[1];
						y = atras[2];
					}
				}
				if(arriba[0]>mayor) {
					mayor = arriba[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]-1][posicionesVecinos[i][1]] == null && posicionesVecinos[i][0]-1>0){
						x = posicionesVecinos[i][0]-1;
						y = posicionesVecinos[i][1];
					}else if(arriba[1]>=0&& arriba[1]<tablero.length && arriba[2]>= 0 && arriba[2]<tablero.length){
						x = arriba[1];
						y = arriba[2];
					}
				}
				if(abajo[0]>mayor) {
					mayor = abajo[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]+1][posicionesVecinos[i][1]] == null && posicionesVecinos[i][0]+1<tablero.length){
						x = posicionesVecinos[i][0]+1;
						y = posicionesVecinos[i][1];
					}else if(abajo[1]>=0&&abajo[1]<tablero.length&&abajo[2]>=0 && abajo[2]<tablero.length){
						x = abajo[1];
						y = abajo[2];
					}
				}
				if(dArriba[0]>mayor) {
					mayor = dArriba[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]-1][posicionesVecinos[i][1]-1] == null && posicionesVecinos[i][0]-1>0 && posicionesVecinos[i][1]-1>0){
						x = posicionesVecinos[i][0]-1;
						y = posicionesVecinos[i][1]-1;
					}else if(dArriba[1]>=0&& dArriba[1]<tablero.length && dArriba[2]>=0&&dArriba[2]<tablero.length){
						x = dArriba[1];
						y = dArriba[2];
					}
				}
				if(dAbajo[0]>mayor) {
					mayor = dAbajo[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]+1][posicionesVecinos[i][1]+1]== null && posicionesVecinos[i][0]+1<tablero.length &&  posicionesVecinos[i][1]+1<tablero.length) {
						x = posicionesVecinos[i][0]+1;
						y = posicionesVecinos[i][1]+1;
					}else if(dAbajo[1]>=0&&dAbajo[1]<tablero.length && dAbajo[2]>=0 && dAbajo[2]<tablero.length){
						x = dAbajo[1];
						y = dAbajo[2];
					}
				}
				if(d2Arriba[0]>mayor) {
					mayor = d2Arriba[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]-1][posicionesVecinos[i][1]+1] == null && posicionesVecinos[i][0]-1>0 && posicionesVecinos[i][1]+1 <tablero.length ) {
						x = posicionesVecinos[i][0]-1;
						y = posicionesVecinos[i][1]+1;
					}else if(d2Arriba[1]>=0 && d2Arriba[1]<tablero.length && d2Arriba[2]>=0 && d2Arriba[2]<tablero.length){
						x = d2Arriba[1];
						y = d2Arriba[2];
					}
				}
				if(d2Abajo[0]>mayor) {
					mayor = d2Abajo[0];
					if(tablero[posicionesVecinos[i][0]][posicionesVecinos[i][1]] == null) {
						x = posicionesVecinos[i][0];
						y = posicionesVecinos[i][1];
					}else if(tablero[posicionesVecinos[i][0]+1][ posicionesVecinos[i][1]-1] == null && posicionesVecinos[i][0]+1<tablero.length &&
					posicionesVecinos[i][1]-1>0){
						x = posicionesVecinos[i][0]+1;
						y = posicionesVecinos[i][1]-1;
					}else if(d2Abajo[1]>=0&&d2Abajo[1]<tablero.length && d2Abajo[2] >=0 && d2Abajo[2]<tablero.length){
						x = d2Abajo[1];
						y = d2Abajo[2];
					}
				}
			}
				
			}
			System.out.println(x+" "+y);
			cont++;
		}while(tablero[x][y] != null && cont <posiciones.size());
		if(cont>=posiciones.size()) {
			int[] posicionFinal = encontrarPosicionVaciaCercana(posiciones.get(posiciones.size()-1),tablero);
			x= posicionFinal[0];
			y = posicionFinal[1];
		}
		return new int[] {x,y};
	}
	/**
	 *Metodo para encontrar la posicion vacia mas cercana a su ultima posicion
	 *@param int[] ultima posicicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return int[] posicion a colocar en el tablero
	 */
	private int[] encontrarPosicionVaciaCercana(int[] lastPosicion,Ficha[][] tablero) {
		int posx = lastPosicion[0];
		int posy = lastPosicion[1];
		Random r = new Random();
		int[][] vecinos = {
    			{posx,posy-1},
    			{posx,posy+1},
    			{posx-1,posy},
    			{posx+1,posy},
    			{posx-1,posy-1},
    			{posx-1,posy+1},
    			{posx+1,posy-1},
    			{posx+1,posy+1}
    	};
		int y = 0;
		int x = 0;
		int[] posicion;
		do{
			posicion = vecinos[r.nextInt(vecinos.length)];
			x = posicion[0];
			y = posicion[1];
			
		}while(x<0 && x>=tablero.length && y< 0 && y>=tablero[0].length && tablero[x][y] != null);
		return posicion;
	}
	/**
	 *Metodo para contar hacia adelante dada la posicion de la ficha
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarDelante(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x>= 0 && x<tablero.length && y >= 0 && y <tablero[0].length-1 && tablero[x][y+1] != null && tablero[x][y+1].getColor().equals(color)) {
			y++;
			cont++;
		}
		return new int[] {cont,x,y+1};
	}
	/**
	 *Metodo para contar hacia atras dada la posicion de la ficha
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarAtras(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x>= 0 && x<tablero.length && y > 0 && y <tablero[0].length && tablero[x][y-1] != null && tablero[x][y-1].getColor().equals(color)) {
			y--;
			cont++;
		}
		return new int[] {cont,x,y-1};
	}
	/**
	 *Metodo para contar hacia arriba dada la posicion de la ficha
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarArriba(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x> 0 && x<tablero.length-1 && y >= 0 && y <tablero[0].length && tablero[x-1][y] != null && tablero[x-1][y].getColor().equals(color)) {
			x--;
			cont++;
		}
		return new int[] {cont,x-1,y};
	}
	/**
	 *Metodo para contar hacia abajo dada la posicion de la ficha
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarAbajo(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x>= 0 && x<tablero.length-1 && y >= 0 && y <tablero[0].length && tablero[x+1][y] != null && tablero[x+1][y].getColor().equals(color)) {
			x++;
			cont++;
		}
		return new int[] {cont,x+1,y};
	}
	/**
	 *Metodo para contar diagonal principal arriba del tablero
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarDiagonalArriba(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x> 0 && x<tablero.length && y > 0 && y <tablero[0].length && tablero[x-1][y-1] != null && tablero[x-1][y-1].getColor().equals(color)) {
			x--;
			y--;
			cont++;
		}
		return new int[] {cont,x-1,y-1};
	}
	/**
	 *Metodo para contar diagonal principal abajo del tablero
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarDiagonalAbajo(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x>= 0 && x<tablero.length-1 && y >= 0 && y <tablero[0].length-1 && tablero[x+1][y+1] != null && tablero[x+1][y+1].getColor().equals(color)) {
			x++;
			y++;
			cont++;
		}
		return new int[] {cont,x+1,y+1};
	}
	/**
	 *Metodo para contar diagonal secundaria arriba del tablero
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarDiagonal2Arriba(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x> 0 && x<tablero.length && y >= 0 && y <tablero[0].length-1 && tablero[x-1][y+1] != null && tablero[x-1][y+1].getColor().equals(color)) {
			x--;
			y++;
			cont++;
		}
		return new int[] {cont,x-1,y+1};
	}
	/**
	 *Metodo para contar diagonal secundaria abajo del tablero
	 *@param int[] posicion del oponente
	 *@param Ficha[][] tablero del juego
	 *@return color del oponente
	 */
	private int[] contarDiagonal2Abajo(int[] posiciones,Ficha[][] tablero,String color) {
		int x = posiciones[0];
		int y = posiciones[1];
		int cont = 1;
		while(x>= 0 && x<tablero.length-1 && y > 0 && y <tablero[0].length && tablero[x+1][y-1] != null && tablero[x+1][y-1].getColor().equals(color)) {
			x++;
			y--;
			cont++;
		}
		return new int[] {cont,x+1,y-1};
	}
	


}
