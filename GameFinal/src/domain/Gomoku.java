package domain;
import java.lang.reflect.Constructor;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public class Gomoku implements Serializable,Observable{
	private transient Set<Observador> observadores;
	public Ficha[][] tablero;
    private int turno;
    private static Gomoku gomoku = null;
    private Casilla[][] casillas;
    private Player player1;
    private Player player2;
    private int row;
    private int column;
    private boolean win;
    private Juego modoJuego;
    private Ficha ultimaFicha;
    private String ganador = null;
    /**
     * Constructor de la clase principal Gomoku
     */
    public Gomoku() {
        win = false;
        observadores = new HashSet<>();
    }
    /**
     * Crea el tablero del juego.
     */
    public void crearJuego(int row, int column, int numCasillasEspeciales,Player player1, Player player2) {
    	this.row = row;
    	this.column = column;
    	casillas = new Casilla[row][column];
    	tablero = new Ficha[row][column];
    	this.player1 = player1;
    	this.player2 = player2;
    	elegirCasillaAzar(numCasillasEspeciales);
    }
    /**
     * configura el juego segun el tipo de juego.
     */
    public void iniciarJuego(Juego juego){
    	modoJuego = juego;
    	
    }
    /**
     * Asignar un juego.
     */
    public void setGomoku(Gomoku g) {
    	gomoku = g;
    }
    /**
     * Asignar un nuevo turno.
     */
    public void setTurno(int nuevoTurno) {
    	turno = nuevoTurno;
    }
    /**
     * Obtener el tablero
     */
    public Ficha[][] getTablero(){
        return tablero;
    }
    /**
     * Obtener el tamaño total del tablero
     */
    public int getTamanio() {
        return row*column;
    }
    /**
     * Obtener el tamaño de las filas.
     */
    public int getTamanioRow() {
        return row;
    }
    /**
     * Obtener el tamaño de las columnas.
     */
    public int getTamanioCol() {
        return column;
    }
    /**
     * Obtener el turno en el que se encuentra el juego.
     */
    public int turnoActual(){
        return turno;
    }
    /**
     * Obtener la ficha del tablero de fichas.
     * @param posicion x del tablero.
     * @param posicion y del tablero
     */
    public Ficha getFichaTablero(int x, int y) {
    	return tablero[x][y];
    }
    /**
     * Obtener el jugador segun el turno en el que esté.
     */
    public Player getPlayer(){
        if(turno%2 == 0){
            return player1;
        }
        return player2;
    }
    
    /**
     * Obtener el primer jugador.
     */
    public Player getPlayer1(){
        return player1;
    }
    /**
     * Obtener la utlima ficha colocada en el tablero.
     */
    public Ficha getUltimaFicha(){
        return ultimaFicha;
    }
    /**
     * Obtener el segundo jugador.
     */
    public Player getPlayer2(){
        return player2;
    }
    /**
     * Obtener el modo de juego
     */
    public Juego getJuego() {
    	return modoJuego;
    }
    /**
     * Asignar un casilla al tablero de casillas segun la posicion.
     * @param posicion x de la casilla
     * @param posicion y de la casilla
     */
    public void setCasilla(Casilla casilla, int posx,int posy) {
    	casillas[posx][posy] = casilla;
    }
    /**
     * Obtener la instacia del gomoku, patron singleton.
     */
    public static Gomoku getInstance() {
    	if(gomoku == null) {
    		gomoku = new Gomoku(); 
    	}
    	return gomoku;
    	}
 
 
    /**
     * Obtener las casillas del juego.
     */
    public Casilla[][] getCasillas(){
        return casillas;
    }
    /**
     * Obtener la variable que representa si hay ganador  o no.
     */
    public boolean getWin(){
        return win;
    }
    /**
     * obener la casilla del tablero de casillas
     * @param posicion x de la casilla
     * @param posicion y de la casilla
     */
    public Casilla getCasilla(int x, int y){
        return casillas[x][y];
    }
    /**
     * obtener el nombre del ganador.
     */
    public String getGanador() {
    	return ganador;
    }
    /**
     * obtener el oponente del turno actual
     */
    public String getOponente(String actual) {
    	if(actual.equals(player1.getName())) {
    		return player2.getName();
    	}else {
    		return player1.getName();
    	}
    }
    
    /**
     * Metodo para asignar casillas normales una vez se haya acabado de asignar las especiales
     */
    private void crearCasillas() {
        for(int i=0;i<row;i++) {
            for(int j=0;j<column;j++) {
            	if(casillas[i][j] == null) {
            		asignarCasilla("domain.NormalCasilla",i,j);
            	}
            }
        }
    }
    /**
     * Elegir las casillas al azar segun la cantidad de casillas especiales.
     * @param numero de casillas especiales.
     */
    public void elegirCasillaAzar(int cantidadCasillas) {
    	String[] tiposCasillas = new String[]{"domain.Mine","domain.Teleport","domain.Golden"};
	    Random r = new Random();
    	for(int i = 0;i<cantidadCasillas;i++) {
    	    int index = r.nextInt(3);
    	    int posx = r.nextInt(row);
    	    int posy = r.nextInt(column);
    	    asignarCasilla(tiposCasillas[index],posx,posy);
    	}
    	crearCasillas();
  
    }
    /**
     * Asignar una casilla segun el nombre del tipo de casilla.
     * @param numero de casillas especiales.
     */
    public void asignarCasilla(String nombreCasilla,int x, int y) {
    	try {
            Class<?> clase = Class.forName(nombreCasilla);
            Constructor<?> constructor = clase.getConstructor(int.class,int.class);
            Object object = constructor.newInstance(x,y);
            Casilla casilla = (Casilla) object;
            casillas[x][y] = casilla;
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }catch(IllegalAccessException e){
           System.out.println(e.getMessage());
        }catch(InstantiationException e){
            System.out.println(e.getMessage());
        }catch(InvocationTargetException e){
            System.out.println(e.getMessage());
        }catch(NoSuchMethodException e){
            System.out.println(e.getMessage());
        }
    }	
    /**
     * Metodo para poner la ficha en el tablero.
     * @param posicionx donde se va a ubicar la ficha.
     * @param posiciony donde se va a ubicar la ficha.
     * @param tipo de ficha que se va a colocar
     */
    public void ponerFichaTablero(int posx, int posy, Ficha ficha) {
        tablero[posx][posy] = ficha;
        casillas[posx][posy].setFicha(ficha);
        if(ficha!= null) {
        	ficha.setPosicion(posx, posy);
        }
    }
    /**
     * Colocar una ficha en la casilla segun la posicion
     * @param ficha a colocar.
     * @param posiciones de la casilla donde se va a colocar.
     */
    public void colocarFicha(Ficha ficha,int[] posiciones) {
    	int posx = posiciones[0];
        int posy = posiciones[1];
        if(posx>= 0 && posx < row && posy >= 0 && posy< column &&
        	tablero[posx][posy] == null) {
        	Casilla casilla = casillas[posx][posy];
        	casilla.colocarFicha(ficha);
        	ultimaFicha = ficha;
        }  
    }
    /**
     * Metodo para jugar el gomoku.
     * @param posiciones donde se va a ubicar la ficha.
     * @throws GomokuException indicando que ya terminó el juego
     */
    public void jugarTablero(int[] posiciones) throws GomokuException{
	    //imprimirCasilla();
	    Player jugadorActual = (turno % 2 == 0) ? player1 : player2;
	    jugadorActual.jugar(posiciones);
	    if(!modoJuego.terminarJuego(jugadorActual,tablero)) {
	    		turno++;
	    }
    	
    }
    /**
     * Metodo para hacer jugar a las maquinas
     * @throws GomokuException indicando que ya terminó el juego
     */
    public void jugar() throws GomokuException {
    	if(!player2.getClass().getName().equals("domain.Humano")) {
    		player2.jugar();
    	}
    }
    /**
     * Metodo para explotar las fichas vecinas a la posicion.
     * @param posicion x de la casilla.
     * @param posicion y de la casilla.
     */
    public void boom(int posx,int posy) {
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
    		int x = posicionesVecinos[i][0];
    		int y = posicionesVecinos[i][1];
    		if(x>= 0 && x < row && y>= 0 && y < column && tablero[x][y]!= null) {
    			if(!tablero[x][y].getPlayer().equals(player1)) {
    				player1.sumarPuntaje(100);
    			}else{
    				player2.sumarPuntaje(100);
    			}
    			tablero[x][y].boom();

    		}
    	}
    }
    /**
     * Metodo para verificar si el tablero está lleno.
     * @return booleano verificando si el tablero esta lleno.
     */
    public boolean tableroLleno() {
    	if(player1.getPosiciones().size() + player2.getPosiciones().size() >= row*column) {
    		return true;
    	}
    	return false;
    }
    /**
     * Metodo para incrementar el turno del jugador.
     * @param incremento del turno del jugador.
     */
    public void incrementarTurno(int incremento) {
    	turno += incremento;
    }
    /**
     * Metodo para eliminar el observador
     * @param observador a eliminar
     */
    public void delObserver(Observador o) {
    	observadores.remove(o);
    }
    /**
     * Metodo para añadir un observador
     * @param observador para añadir
     */
    public void addObserver(Observador o) {
    	observadores.add(o);
    }
    /**
     * Metodo para notificar al observador que algo sucedio en el gomoku, en este caso
     * la interfaz de juego.
     */
    public void notifyObservers() {
    	for(Observador o:observadores) {
    		o.finishGame();
    	}
    }
    /**
     * Metodo para terminar el juego
     * @param nombre del jugador ganador, si lo hay.
     */
    public void terminarJuego(String jugador) {
    	win = true;
    	ganador = jugador;
    	notifyObservers();
    }
    public void imprimirCasilla() {
    	for(int i = 0;i<row;i++) {
    		for(int j = 0;j<column;j++) {
    			System.out.println(casillas[i][j]);
    		}
    		System.out.println("");
    	}
    }
    public void crearObservador() {
    	observadores = new HashSet<>();
    }

}



