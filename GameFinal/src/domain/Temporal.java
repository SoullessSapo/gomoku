/**
 * 
 */
package domain;

public class Temporal extends Ficha{
	private int tiempo;
    public Temporal(String color,Player player) {
        super(color,player);
        tiempo = 1;
    }
    /**
     * Metodo para colocar la ficha en la casilla dada.
     */
    public void colocarse(int[] posicion) {
    	Gomoku.getInstance().colocarFicha(this,posicion);
    	player.sumarPuntaje(100);
    }
    public void desaparecer() {
    	if(tiempo == 3) {
    		Gomoku.getInstance().ponerFichaTablero(posicionx, posiciony, null);
    		player.eliminarPosicion(posicionx, posiciony);
    		
    	}else {
    		tiempo++;
    	}
    }

}
