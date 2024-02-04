/**
 * 
 */
package domain;

/**
 * 
 */
public class Mine extends Casilla {
	/**
     * Constructor de la clase Mine
     */
    public Mine(int posx,int posy) {
        super(posx,posy);
    }
    @Override
    /**
     * Metodo abstracto para colocar la ficha en la casilla y eliminar a los vecinos
     * @param ficha a colocar en el tablero
     */
    public void colocarFicha(Ficha newFicha) {
        super.colocarFicha(newFicha);
        Gomoku.getInstance().boom(posicionx,posiciony);
        
    }

}
