package domain;


import java.util.ArrayList;

public class Experta extends Maquina {
	/**
	 * Constructor de la clase hija Agresiva
	 */
	public Experta(String name, String color) {
        super(name, color);
    }
	/**
	 *Metodo para elegir la ficha y jugar en el tablero
	 *@throws GomokuException indicando que ya acabo el juego
	 */
    @Override
    public void jugar() throws GomokuException {
        Ficha[][] tablero = Gomoku.getInstance().getTablero();
        int[] mejorJugada = minimax(tablero, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        Gomoku.getInstance().jugarTablero(mejorJugada);
    }
    /**
	 *Metodo para mirar la mejor jugada usando el algoritmo minimax
	 *@param Ficha[][] tablero del juego
	 *@param profundidad a recorrer
	 *@param maximo valor
	 *@param minimo valor
	 *@param booleano indicando si es maximo o no 
	 *@return int[] posiciones donde colocarse en el juego
	 */
    private int[] minimax(Ficha[][] tablero, int profundidad, int alfa, int beta, boolean esMaximizador) {
        ArrayList<int[]> posicionesDisponibles = obtenerPosicionesDisponibles(tablero);

        if (profundidad == 0 || posicionesDisponibles.isEmpty()) {
            return evaluarPosicion(tablero);
        }

        int mejorValor = esMaximizador ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] mejorJugada = posicionesDisponibles.get(0);

        for (int[] jugada : posicionesDisponibles) {
            int fila = jugada[0];
            int columna = jugada[1];

            Ficha fichaSimulada = new NormalFicha(getColor(), this);
            tablero[fila][columna] = fichaSimulada;
            getPosiciones().add(jugada);

            int[] resultado = minimax(tablero, profundidad - 1, alfa, beta, !esMaximizador);
            tablero[fila][columna] = null;
            getPosiciones().remove(getPosiciones().size() - 1);

            if (esMaximizador) {
                if (resultado[1] > mejorValor) {
                    mejorValor = resultado[1];
                    mejorJugada = jugada;
                }
                alfa = Math.max(alfa, mejorValor);
            } else {
                if (resultado[1] < mejorValor) {
                    mejorValor = resultado[1];
                    mejorJugada = jugada;
                }
                beta = Math.min(beta, mejorValor);
            }
            if (beta <= alfa) {
                break;
            }
        }

        return mejorJugada;
    }
    /**
	 *Metodo para obtener las posiciones donde no se han colocado fichas
	 *@param Ficha[][] tablero del juego
	 *@return ArrayList<int[]> posiciones disponibles del tablero
	 */
    private ArrayList<int[]> obtenerPosicionesDisponibles(Ficha[][] tablero) {
        ArrayList<int[]> posiciones = new ArrayList<>();
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == null) {
                    posiciones.add(new int[]{i, j});
                }
            }
        }
        return posiciones;
    }
    /**
	 *Metodo para evaluar una posicion en el tablero
	 *@return int[] posicion donde se podria colocar
	 */
    private int[] evaluarPosicion(Ficha[][] tablero) {
        int mejorValor = Integer.MIN_VALUE;
        int[] mejorJugada = new int[]{-1, -1};
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length - 4; j++) {
                int valor = evaluarLinea(tablero[i][j], tablero[i][j + 1], tablero[i][j + 2], tablero[i][j + 3], tablero[i][j + 4]);
                if (valor > mejorValor) {
                    mejorValor = valor;
                    mejorJugada = new int[]{i, j + 2};
                }
            }
        }

        for (int i = 0; i < tablero.length - 4; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                int valor = evaluarLinea(tablero[i][j], tablero[i + 1][j], tablero[i + 2][j], tablero[i + 3][j], tablero[i + 4][j]);
                if (valor > mejorValor) {
                    mejorValor = valor;
                    mejorJugada = new int[]{i + 2, j};
                }
            }
        }
        for (int i = 0; i < tablero.length - 4; i++) {
            for (int j = 0; j < tablero[0].length - 4; j++) {
                int valor = evaluarLinea(tablero[i][j], tablero[i + 1][j + 1], tablero[i + 2][j + 2], tablero[i + 3][j + 3], tablero[i + 4][j + 4]);
                if (valor > mejorValor) {
                    mejorValor = valor;
                    mejorJugada = new int[]{i + 2, j + 2};
                }
            }
        }
        for (int i = 0; i < tablero.length - 4; i++) {
            for (int j = 4; j < tablero[0].length; j++) {
                int valor = evaluarLinea(tablero[i][j], tablero[i + 1][j - 1], tablero[i + 2][j - 2], tablero[i + 3][j - 3], tablero[i + 4][j - 4]);
                if (valor > mejorValor) {
                    mejorValor = valor;
                    mejorJugada = new int[]{i + 2, j - 2};
                }
            }
        }

        return new int[]{mejorJugada[0], mejorJugada[1], mejorValor};
    }
    /**
	 *Metodo para evaluar una linea seguida en el juego
	 *@return int distancia del oponente y el jugador
	 */
    private int evaluarLinea(Ficha f1, Ficha f2, Ficha f3, Ficha f4, Ficha f5) {
        int valorPropio = evaluarFicha(f1) + evaluarFicha(f2) + evaluarFicha(f3) + evaluarFicha(f4) + evaluarFicha(f5);
        int valorOponente = evaluarFichaOponente(f1) + evaluarFichaOponente(f2) + evaluarFichaOponente(f3) + evaluarFichaOponente(f4) + evaluarFichaOponente(f5);
        return valorPropio - valorOponente;
    }
    /**
	 *Metodo para evaluar una ficha a que jugador de pertenece
	 *@param ficha a evaluar
	 */
    private int evaluarFicha(Ficha ficha) {
        if (ficha == null) {
            return 0;
        }
        return ficha.getColor().equals(getColor()) ? 1 : 0;
    }
    /**
	 *Metodo para evaluar la ficha el oponente
	 *@param ficha a evaluar
	 */
    private int evaluarFichaOponente(Ficha ficha) {
        if (ficha == null) {
            return 0;
        }
        return ficha.getColor().equals(getColor()) ? 0 : 1;
    }

}


