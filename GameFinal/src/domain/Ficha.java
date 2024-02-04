package domain;
import java.io.Serializable;

public abstract class Ficha implements Serializable {
	
	    protected String color;

	    protected int posicionx;

	    protected int posiciony;

	    protected Casilla casilla;
	    protected Player player;

	    /**
	     * Constructor de la clase Ficha.
	     */
	    public Ficha(String color,Player player) {
	        casilla = null;
	        this.color = color;
	        this.player = player;
	        posicionx = -1;
	        posiciony = -1;
	    
	    }
	    /**
	     * Metodo abstracto para colocar la ficha enla casilla dada.
	     */
	    public abstract void colocarse(int[] posicion);
	    
	    /**
	     * Metodo para asiganar la posicion de la ficha.
	     */
	    public void setPosicion(int x, int y) {
	        posicionx = x;
	        posiciony = y;
	        asignarPosicion();
	    }
	    /**
	     * Metodo para obtener la casilla.
	     */
	    public Casilla getCasilla(){
	        return casilla;
	    }
	    /**
	     * Metodo para obtener el color de la ficha.
	     */
	    public String getColor(){
	        return color;
	    }
	    /**
	     * Metodo para asignar la posicion de la ficha en la lista de posiciones del jugador asociado.
	     */
	    public void asignarPosicion(){
	        player.asignarPosicion(posicionx,posiciony,this);
	    }
	    /**
	     * Metodo para obtener el jugador asociado.
	     */
	    public Player getPlayer() {
	    	return player;
	    }
	    
	    /**
	     * Metodo para explotar la ficha y quitarle puntos a su jugador asociado.
	     */
	    public void boom() {
	    	player.sumarPuntaje(-50);
	    	player.eliminarPosicion(posicionx, posiciony);
	    	Gomoku.getInstance().ponerFichaTablero(posicionx,posiciony,null);
	    }
	    /**
	     * Metodo para dar una ficha extra a su jugador asociado.
	     */
	    public void darFicha() {
	    	player.darFicha();
	    }
	    

}

