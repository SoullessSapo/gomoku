
package domain;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Random;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
public abstract class Player implements Serializable{
	private String nombre;
    private String color;
    private int puntaje;
    private ArrayGomoku<Ficha> fichas;
    private ArrayList<Temporal> fichasEnTablero;
    private ArrayList<int[]> posiciones;
    private boolean repiteTurno = false;
    private int turnosConsecutivos = 0;
    private static final Random r = new Random();
    private Ficha ultimaFicha;
    /**
     * Constructor de la clase principal Gomoku
     */
    public Player(String name,String color) {
        puntaje = 0;
        fichas = new ArrayGomoku<>();
        fichasEnTablero = new ArrayList<>();
        posiciones = new ArrayList<>();
        setName(name);
        setColor(color);
    }
    /**
     * asignar un nombre al jugador
     */
    public void setName(String name) {
    	this.nombre = name;
    }
    /**
     * asignar un color al jugador
     */
    public void setColor(String color) {
    	this.color = color;
    }
    /**
     * Obtener las fichas del jugador
     * @return array de tipo Gomoku para obtener la lista de fichas.
     */
    public ArrayGomoku<Ficha> getFichas(){
        return fichas;
    }
    /**
     * Obtener las posiciones de las fichas del jugador.
     */
    public ArrayList<int[]> getPosiciones(){
        return posiciones;
    }
    /**
     * Obtener el nombre del jugador.
     */
    public String getName(){
        return nombre;
    }
    /**
     * Obtener puntaje del jugador.
     */
    public int getPuntaje(){
        return puntaje;
    }
    /**
     * Obtener puntaje del jugador.
     */
    public String getColor(){
        return color;
    }
    /**
     * Obtener la utlima ficha del jugador.
     */
    public Ficha getUltimaFicha() {
    	return ultimaFicha;
    }
    /**
     * Metodo para crear fichas al azar al iniciar el juego
     * @param numero de fichas especiales
     * @param cantidad total de fichas
     */
    public void asignarFichas(int numFichas,int cantidad) {
    	int contadorEspeciales = 0;
        for(int i=0;i<cantidad;i++) {
        	String tipoFicha = elegirFichaAzar(2);
        	if(contadorEspeciales < numFichas) {
        		asignarFicha(tipoFicha);
        		contadorEspeciales++;
        	}else {
        		asignarFicha("domain.NormalFicha");
        	}
        	
        }
        
    }
    /**
     * Metodo para elegir una ficha al azar
     * @param indice que indica hasta que tipo de fichas quiere elegir
     * @return nombre de la ficha elegida
     */
    public String elegirFichaAzar(int limite) {
    	String[] tiposFichas = {"domain.Temporal","domain.Pesada","domain.NormalFicha"};
        int index = r.nextInt(limite);
    	return tiposFichas[index];
    }
    /**
     * Metodo para asignar una ficha al jugador
     * @param nombre de la ficha a crear
     */
    public void asignarFicha(String tipo) {
    	try {
            Class<?> clase = Class.forName(tipo);
            Constructor<?> constructor = clase.getConstructor(String.class,Player.class);
            Object object = constructor.newInstance(color,this);
            Ficha ficha = (Ficha) object;
            fichas.add(ficha);
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
     * Metodo para seleccionar la ficha al azar que va a poner en el tablero
     */
    public Ficha añadirFicha() {
        avisarTemporales();
        int index;
        if(!repiteTurno) {
        	index = r.nextInt(fichas.size());
        }else {
        	turnosConsecutivos++;
        	if(turnosConsecutivos >= 2) {
        		reiniciarTurnosConsecutivos();
        	}else {
            	Gomoku.getInstance().incrementarTurno(1);
        	}
        	index = fichas.size()-1;
        }
        Ficha fichaSeleccionada = fichas.get(index);
        fichas.remove(index);
        ultimaFicha = fichaSeleccionada;
        return fichaSeleccionada;
    }
    /**
     * Metodo para reiniciar los turnos consecutivos del jugador
     */
    public void reiniciarTurnosConsecutivos() {
        turnosConsecutivos = 0;
        repiteTurno = false;
    }
    /**
     * Metodo para iniciar el iniciar el turno del jugador
     * @param posicion donde se va a colocar la ficha en el tablero
     */
    public void jugar(int[] posicion) {
    	Ficha ficha = añadirFicha();
    	ficha.colocarse(posicion);
    }
    /**
     * Metodo para asignar la posicion donde coloco la ficha el jugador
     * @param posicion x de la ficha colocada en el tablero
     * @param posicion y de la ficha colocada en el tablero
     * @param ficha que se coloco, si es temporal se añade a la lista de temporales
     */
    public void asignarPosicion(int posx,int posy,Ficha ficha){
        int[] posi = new int[]{posx,posy};
        posiciones.add(posi);
        if(ficha instanceof Temporal) {
        	fichasEnTablero.add((Temporal) ficha);
        }
    }
    /**
     * Metodo para eliminar la posicion de una ficha colocada
     * @param posicion x de la ficha colocada en el tablero
     * @param posicion y de la ficha colocada en el tablero
     */
    public void eliminarPosicion(int posx,int posy){
        int[] posi = new int[]{posx,posy};
        posiciones.remove(posi);  
    }
    /**
     * Metodo para sumar puntaje al jugador
     * @param cantidad de puntaje a sumar
     */
    public void sumarPuntaje(int num) {
    	puntaje += num;
    	if(puntaje % 1000 == 0) {
    		asignarFicha(elegirFichaAzar(3));
    	}
    }
    /**
     * Metodo para avisar a las temporales que se desaparezcan segun su condicion
     */
    public void avisarTemporales() {
    	for(int i = 0;i<fichasEnTablero.size();i++) {
    		fichasEnTablero.get(i).desaparecer();
    	}
    }
    /**
     * Metodo para dar una ficha nueva al jugador
     */
    public void darFicha() {
    	String fichaElegida = elegirFichaAzar(3);
    	if(fichaElegida.equals("domain.NormalFicha")) {
    		repiteTurno = true;
    	}
    	asignarFicha(fichaElegida);
    }
    /**
     * Metodo para saber si el jugador repite turno
     */
    public boolean getRepiteTurno() {
    	return repiteTurno;
    }
    /**
     * Metodo para jugar si es maquina
     */
    public abstract void jugar() throws GomokuException;
}
