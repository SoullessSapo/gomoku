package presentation;
import domain.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class DialogosModoJuego {
	/**
	 * dialogo para pedirle al usuario el tipo de juego
	 * @return String modo de juego
	 */
	public static String preguntarModoJuego() {
		String[] opciones = { "Normal", "Piedras limitadas", "QuickTime"};

        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione un modo de juego",
                "Selección de Modo de Juego", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones,
                opciones[0]);

        if (seleccion == -1) {
            return null;
        }

        return opciones[seleccion];
	}
	/**
	 * dialogo para crear el tipo de juego y preguntar si, es el caso, la cantidad de tiempo/piedras limitadas
	 * @return Juego tipo de juego 
	 */
	public static Juego crearModoJuego() {
		String tipoJuego = preguntarModoJuego();
		Juego juego;
		if(tipoJuego != null) {
			juego = Factory.getJuego(tipoJuego);
			if(tipoJuego == "QuickTime"){
				long tiempo = preguntarTiempo();
				Quicktime juegoTiempo = (Quicktime) juego;
				juegoTiempo.setTime(tiempo * 60 * 1000);
				return juegoTiempo;
			}
		}else {
			juego = Factory.getJuego("Normal");
		}
		return juego;
	}
	/**
	 * dialogo para preguntar la cantidad de piedras limitadas en caso de ser el modo de juego "piedras limitadas"
	 * @return int cantidad de pidras a jugar
	 */
	public static int preguntarCantidadPiedras(int tamanio) {
        JPanel panel = new JPanel();
        SpinnerModel model = new SpinnerNumberModel(1, 0, tamanio, 1);
        JSpinner spinner = new JSpinner(model);
        panel.add(spinner);

        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                panel,
                "Selecciona el número de fichas limitadas",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        if (opcionSeleccionada == JOptionPane.OK_OPTION) {
            int valorSeleccionado = (int) spinner.getValue();
            return valorSeleccionado;
        }
        return 1;
    }
	/**
	 * dialogo para preguntar el tiempo limite del modo quicktime
	 * @return int tiempo limite del juego
	 */
	public static int preguntarTiempo() {
        JPanel panel = new JPanel();
        SpinnerModel model = new SpinnerNumberModel(1, 0, 60, 1);
        JSpinner spinner = new JSpinner(model);
        panel.add(spinner);

        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                panel,
                "Selecciona el tiempo de partida",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        if (opcionSeleccionada == JOptionPane.OK_OPTION) {
            int valorSeleccionado = (int) spinner.getValue();
            return valorSeleccionado;
        }
        return 1;
    }

}