package domain;

import java.io.Serializable;

public class Quicktime extends Juego {
    private long tiempoInicial;
    private Temporizador tiempoRestantePlayer1;
    private Temporizador tiempoRestantePlayer2;
    private boolean enEjecucion;
    
    public Quicktime() {
        this.enEjecucion = false;
    }
    /**
     * Metodo abstracto para iniciar el tiempo del juego
     */
    public void iniciarJuego() {
        if (!enEjecucion) {
            enEjecucion = true;

            Thread hilo = new Thread(() -> {
                while (enEjecucion && tiempoRestantePlayer1.getTiempoRestante() > 0
                        && tiempoRestantePlayer2.getTiempoRestante() > 0) {
                    try {
                        Thread.sleep(1000);
                        if (tiempoRestantePlayer1.getTiempoRestante() <= 0
                                || tiempoRestantePlayer2.getTiempoRestante() <= 0) {
                            try {
								terminarJuego(Gomoku.getInstance().getPlayer(),Gomoku.getInstance().getTablero());
							} catch (GomokuException e) {
							}
                            break;
                        }
                        intercambiarTurno();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            hilo.start();
        }

        tiempoRestantePlayer1.iniciar();
    }
    /**
     * Metodo para ir intercambiando de turno si el jugador ya coloco una ficha en el tablero y no se ha acabado el tiempo
     */
    private void intercambiarTurno() {
        if (tiempoRestantePlayer1.isEnEjecucion() && 
        		Gomoku.getInstance().getPlayer() != Gomoku.getInstance().getPlayer1()) {
        	tiempoRestantePlayer1.detener();
        	tiempoRestantePlayer2.iniciar();
        } else {
        	tiempoRestantePlayer2.detener();
            tiempoRestantePlayer1.iniciar();
        }
    }
    /**
     * Metodo para reiniciar el tiempo de los jugadores
     */
    public void reiniciar() {
        detener();
        this.tiempoRestantePlayer1.reiniciar();
        this.tiempoRestantePlayer2.reiniciar();
        iniciarJuego();
    }
    /**
     * Metodo detener el tiempo
     */
    public void detener() {
        enEjecucion = false;
    }
    /**
     * Metodo para obtener el temporizador del jugador 1
     */
    public Temporizador getTiempoRestantePlayer1() {
        return tiempoRestantePlayer1;
    }
    /**
     * Metodo para obtener el temporizador del jugador 2
     */
    public Temporizador getTiempoRestantePlayer2() {
        return tiempoRestantePlayer2;
    }
    /**
     * Metodo saber si esta en ejecucion
     */
    public boolean isEnEjecucion() {
        return enEjecucion;
    }
    /**
     * Metodo abstracto para configurar el juego antes de que empiecen a colocar las fichas
     */
	@Override
	public void configurarJuego(int numFichasEspeciales, int cantidadTotalFichas) throws GomokuException {
		Gomoku.getInstance().getPlayer1().asignarFichas(numFichasEspeciales, cantidadTotalFichas);
		Gomoku.getInstance().getPlayer2().asignarFichas(numFichasEspeciales, cantidadTotalFichas);		
	}
	/**
     * Metodo abstracto para terminar el juego quicktime
     * @param jugador del turno actual
     * @param tablero del juego
     */
	@Override
	public boolean terminarJuego(Player p,Ficha[][] tablero) throws GomokuException {
        detener();
        if(tiempoRestantePlayer1.getTiempoRestante() <= 0) {
        	Gomoku.getInstance().terminarJuego(Gomoku.getInstance().getPlayer2().getName());
        	throw new GomokuException(GomokuException.FINISH_GAME);
        }else if(tiempoRestantePlayer2.getTiempoRestante() <= 0) {
        	Gomoku.getInstance().terminarJuego(Gomoku.getInstance().getPlayer1().getName());
        	throw new GomokuException(GomokuException.FINISH_GAME);
        }
        else if (super.verificar5EnRaja(p.getPosiciones(), p.getColor(),tablero)){
            Gomoku.getInstance().terminarJuego(p.getName());
            throw new GomokuException(GomokuException.FINISH_GAME);
        }
        return false;
    }
	/**
     * Metodo para asignar el tiempo limite al juego
     * @param tiempo limite del juego
     */
	public void setTime(long tiempo) {
		this.tiempoInicial = tiempo;
        this.tiempoRestantePlayer1 = new Temporizador(tiempoInicial / 2);
        this.tiempoRestantePlayer2 = new Temporizador(tiempoInicial / 2);
		
	}

}




	

