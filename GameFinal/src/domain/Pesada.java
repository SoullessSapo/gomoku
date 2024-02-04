/**
 * 
 */
package domain;


public class Pesada extends Ficha {
	private boolean colocada = false;
    public Pesada(String color,Player player) {
        super(color,player);
    }
    /**
     * Metodo para colocar la ficha en la casilla dada.
     * @param int[] posicion donde se va a colocar en el tablero
     */
    public void colocarse(int[] posicion) {
	    	Gomoku.getInstance().colocarFicha(this,posicion);
		    player.sumarPuntaje(100);
	        int[] posicionesNorte = new int[]{posicionx-1,posiciony};
	        int[] posicionesEste = new int[]{posicionx,posiciony+1};
	        int[] posicionesNoreste = new int[]{posicionx-1,posiciony+1};
	        Gomoku.getInstance().colocarFicha(this,posicionesNorte);
	        Gomoku.getInstance().colocarFicha(this,posicionesEste);
	        Gomoku.getInstance().colocarFicha(this,posicionesNoreste);
    }
    
    
    

}
