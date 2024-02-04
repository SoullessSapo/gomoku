package domain;

import java.io.Serializable;

public class ModoNormal extends Juego {
	
	/**
     * Metodo abstracto para configurar el juego antes de que empiecen a colocar las fichas
     */
	public void configurarJuego(int numFichasEspeciales, int cantidadTotalFichas) {
		Gomoku.getInstance().getPlayer1().asignarFichas(numFichasEspeciales, cantidadTotalFichas);
		Gomoku.getInstance().getPlayer2().asignarFichas(numFichasEspeciales, cantidadTotalFichas);
	}
	/**
     * Metodo abstracto para terminar el juego normal
     * @param jugador del turno actual
     * @param tablero del juego
     */
	public boolean terminarJuego(Player p,Ficha[][] tablero) throws GomokuException {
		if(super.verificar5EnRaja(p.getPosiciones(), p.getColor(),tablero)) {
			Gomoku.getInstance().terminarJuego(p.getName());
			throw new GomokuException(GomokuException.FINISH_GAME);
		}else if(Gomoku.getInstance().tableroLleno()) {
			Gomoku.getInstance().terminarJuego(null);
			throw new GomokuException(GomokuException.FINISH_GAME);
		}
		return false;
	}
	public void iniciarJuego () {
		return;
	}
}
