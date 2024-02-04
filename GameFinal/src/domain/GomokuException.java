/**
 * 
 */
package domain;

/**
 * 
 */
public class GomokuException extends Exception{
	
	public static final String LIMIT_TOKEN_ERROR = "El numero de fichas debe ser menor al tamaño del tablero.";
	public static final String REPITE_TURNO = "El jugador repite turno";
	public static final String PLAYER_NO_EXIST = "El tipo de jugador no existe";

	public static final String GAME_NO_EXIST = "El tipo de juego no existe";
	public static final String FINISH_GAME = "Fin del juego.";
	public GomokuException(String message){
			super(message);
		}

}
