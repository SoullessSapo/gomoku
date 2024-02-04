package domain;
import java.util.Random;

public class Teleport extends Casilla {
	public Teleport(int posx, int posy) {
        super(posx,posy);
    }
	/**
     * Metodo abstracto para colocar la ficha en la casilla.
     * @param ficha a colocar
     */
    @Override
    public void colocarFicha(Ficha newFicha) {
    	if(newFicha != null) {
    		int posicionFichax;
            int posicionFichay;
    		Random r = new Random();
            do {
            	posicionFichax = r.nextInt(Gomoku.getInstance().getTamanioRow());
                posicionFichay = r.nextInt(Gomoku.getInstance().getTamanioCol());
            }while(Gomoku.getInstance().getFichaTablero(posicionFichax, posicionFichay) != null);
            Gomoku.getInstance().colocarFicha(newFicha, new int[]{posicionFichax,posicionFichay});
    	}
    }

}
