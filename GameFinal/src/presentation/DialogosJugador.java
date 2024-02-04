package presentation;

import javax.swing.JOptionPane;

import domain.Player;


public class DialogosJugador {
	/**
	 * dialogo para pedirle al usuario el modo de juego
	 * @return String modo de juego
	 */
	public static String preguntarJugadores() {
		String[] opciones = { "Jugador vs Jugador","Jugador vs Maquina"};

        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione un modo de juego",
                "Selección de Modo de Juego", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones,
                opciones[0]);

        if (seleccion == -1) {
            return null;
        }

        return opciones[seleccion];
	}
	/**
	 * dialogo para crear el modo de juego y preguntar si, es el caso, los tipos de maquinas
	 * @return Player[] los dos tipos de jugadores
	 */
	public static Player[] crearModoJuego(String tipoJuego) {
		Player jugador1;
		Player jugador2;
		if(tipoJuego == "Jugador vs Jugador") {
			jugador1 = Factory.getPlayer("Humano");
			jugador2 = Factory.getPlayer("Humano");
		}else {
			jugador1 = Factory.getPlayer("Humano");
			String[] opciones = {"Agresiva","Miedosa","Experta"};
	        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione el tipo de Maquina",
	                "Selección de Modo de Juego", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones,
	                opciones[0]);
	        jugador2 = Factory.getPlayer(opciones[seleccion]);
		}
		return new Player[] {jugador1,jugador2};
	}
	/**
	 * dialogo para solicitar los nombres de los jugadores
	 * @return String[] nombre de los jugadores
	 */
	public static String[] solicitarNombres(GomokuGUI interfaz) {
        String nombreJugador1 = JOptionPane.showInputDialog(interfaz, "Ingrese el nombre del Jugador 1:");
        String nombreJugador2 = JOptionPane.showInputDialog(interfaz, "Ingrese el nombre del Jugador 2:");
        return new String[]{nombreJugador1,nombreJugador2};
    }
	/**
	 * dialogo para solicitar el color de los jugadores
	 * @return String color del primer jugador
	 */
	public static String preguntarColor(GomokuGUI interfaz,String name1) {
		String colorElegido;
        Object[] opciones = {"Blanco", "Negro"};
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                interfaz,
                name1+" Selecciona un color de ficha",
                "Seleccionar Color de Ficha",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        if (opcionSeleccionada == 0) {
            colorElegido = "White"; 
        } else {
            colorElegido = "Black";  
        }
		return colorElegido;
    }
	
}
