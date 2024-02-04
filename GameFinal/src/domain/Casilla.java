package domain;

import java.io.Serializable;

public abstract class Casilla implements Serializable {
	protected int posicionx;

    protected int posiciony;

    private Ficha ficha;
    /**
     * Constructor de la clase Casilla
     */
    public Casilla(int posx, int posy) {
        posicionx = posx;
        posiciony = posy;
        ficha = null;
    }
    /**
     * Metodo abstracto para colocar la ficha en el tablero
     */
    public void colocarFicha(Ficha newFicha) {
        Gomoku.getInstance().ponerFichaTablero(posicionx,posiciony,newFicha);
    }
    /**
     * Metodo para obtener la ficha asociada.
     */
    public Ficha getFicha() {
        return ficha;
    }
    /**
     * Metodo para asignar la ficha.
     * @param ficha que fue puesta por el jugador.
     */
    public void setFicha(Ficha newficha) {
    	ficha = newficha;
    }

}

