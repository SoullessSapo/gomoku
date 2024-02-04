/**
 * 
 */
package domain;

/**
 * 
 */
public class Golden extends Casilla {
	/**
     * Constructor de la clase NormalCasilla
     */
    public Golden(int posx, int posy) {
        super(posx,posy);
    }
    @Override
    /**
     * Metodo abstracto para colocar la ficha en la casilla.
     * @param ficha a colocar
     */
    public void colocarFicha(Ficha newFicha) {
       super.colocarFicha(newFicha);
       newFicha.darFicha();
        
    }

}
