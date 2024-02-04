package domain;

public class Miedosa extends Maquina{
	private int[] posiciones;
	/**
	 * Constructor de la clase hija Agresiva
	 */
    public Miedosa(String name, String color) {
        super(name, color);
    }
    /**
	 *Metodo para elegir la ficha y jugar en el tablero
	 *@throws GomokuException indicando que ya acabo el juego
	 */
    @Override
    public void jugar() throws GomokuException {
    	if(Gomoku.getInstance().getWin() == false) {
        int dimension = Gomoku.getInstance().getTamanioCol();
        int posx = Gomoku.getInstance().getUltimaFicha().posicionx;
        int posy = Gomoku.getInstance().getUltimaFicha().posiciony;
        
        if (posx >= dimension / 2 && posy >= dimension / 2) {
            posx = dimension- posx - 1 ;
            posy =dimension- posy - 1 ;
            System.out.println("1");
            posiciones = new int[]{posx, posy};
            System.out.println(posiciones[0] + " " + posiciones[1]);

        } else if (posx >= dimension / 2 && posy < dimension / 2) {
            posx = dimension- posx - 1;
            posy  = dimension - posy - 1;
            System.out.println("2");
            posiciones = new int[]{posx, posy};
        } else if (posx < dimension / 2 && posy >= dimension / 2) {
            posx = dimension- posx - 1;
            posy = dimension - posy - 1;
            System.out.println("3");
            posiciones = new int[]{posx, posy};
        } else if (posx < dimension / 2 && posy < dimension / 2) {
            posx = dimension  - posx - 1;
            posy = dimension - posy - 1;
            System.out.println("4");
            posiciones = new int[]{posx, posy};
        }
        if (Gomoku.getInstance().getTablero()[posx][posy] == null) {
        	System.out.println(posiciones[0] + " " + posiciones[1]);
        Gomoku.getInstance().jugarTablero(posiciones);
        }
        else {
        	Gomoku.getInstance().jugarTablero(verificar(posiciones));
        }
    	}
    }
    /**
	 *Metodo verificar que la posicion no se pase de los limites del tablero
	 *@param int[] posiciones donde se va a posicionar
	 */
	public int[] verificar(int[] posiciones) {
		Ficha[][] tablero = Gomoku.getInstance().getTablero();
		boolean sePuede = false;
		int[] donde = null;
		int suma = 1;
		int i = posiciones[0];
		int j = posiciones[1];
		while (!sePuede ) {
			if (tablero[i+suma][j] == null && i+suma < tablero.length) {
					donde = new int[] {i+suma,j};
					sePuede = true;
				}
			if (tablero[i-suma][j] == null && i-suma >= 0) {
				donde = new int[] {i-suma,j};
				sePuede = true;
			}
			if (tablero[i+suma][j+suma] == null && i + suma < tablero.length && j + suma < tablero.length) {
				donde = new int[] {i+suma,j+suma};
				sePuede = true;
			}
			if (tablero[i-suma][j-suma] == null && i-suma >= 0 && j-suma >= 0) {
				donde = new int[] {i-suma,j-suma};
				sePuede = true;
			}
			if (tablero[i][j+suma] == null && j + suma < tablero.length) {
				donde = new int[] {i,j+suma};
				sePuede = true;
			}
			if (tablero[i][j-suma] == null && j - suma >= 0) {
				donde = new int[] {i,j-suma};
				sePuede = true;
			}
			suma += 1;
		}
		return donde;
	}
}
