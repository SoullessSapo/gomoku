package domain;

public class NormalFicha extends Ficha {
	/**
     * Constructor de la clase NormalPiedra
     */
    public NormalFicha(String color,Player player) {
        super(color,player);
    }
    /**
     * Metodo para colocar la ficha en el tablero
     * @param int[] posicion donde colocar a la ficha
     */
    public void colocarse(int[] posicion) {
    	Gomoku.getInstance().colocarFicha(this,posicion);
    }
}
