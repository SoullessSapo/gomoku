package presentation;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JOptionPane;
import domain.Gomoku;

public class Dialogos{
	/**
	 * dialogo para pedirle al usuario el tamaño del tablero
	 * @return int[] lista con el tamaño de las filas y columnas
	 */
	public static int[] pedirTamanioTablero() {
        String input = JOptionPane.showInputDialog(null, "Introduce el nuevo tamaño del tablero (dos enteros separados por espacio):", "Tamaño del Tablero", JOptionPane.QUESTION_MESSAGE);
        try {
            String[] partes = input.split("\\s+");
            if (partes.length == 2) {
                int[] tamanio = new int[2];
                tamanio[0] = Integer.parseInt(partes[0]);
                tamanio[1] = Integer.parseInt(partes[1]);
                return tamanio;
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce dos enteros separados por espacio.", "Error", JOptionPane.ERROR_MESSAGE);
            return pedirTamanioTablero();
        }
    }
	/**
	 * dialogo para pedirle el numero de fichas especiales
	 * @return int cantidad de fichas especiales
	 */
	public static int preguntarFicha(int tamanio) {
        JPanel panel = new JPanel();
        SpinnerModel model = new SpinnerNumberModel(0, 0, tamanio, 1);
        JSpinner spinner = new JSpinner(model);
        panel.add(spinner);

        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                panel,
                "Selecciona el número de fichas especiales",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        if (opcionSeleccionada == JOptionPane.OK_OPTION) {
            int valorSeleccionado = (int) spinner.getValue();
            return valorSeleccionado;
        }
        return 0;
    }
	/**
	 * dialogo para pedirle el numero de casillas especiales
	 * @return int cantidad de fichas especiales
	 */
	 public static int preguntarCasilla(int tamanio) {
	        JPanel panel = new JPanel();
	        SpinnerModel model = new SpinnerNumberModel(0, 0, tamanio, 1);
	        JSpinner spinner = new JSpinner(model);
	        panel.add(spinner);

	        int opcionSeleccionada = JOptionPane.showOptionDialog(
	                null,
	                panel,
	                "Selecciona el número de casillas especiales",
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                null);

	        if (opcionSeleccionada == JOptionPane.OK_OPTION) {
	            int valorSeleccionado = (int) spinner.getValue();
	            return valorSeleccionado;
	        }
	        return 0;
	    }

	
	
}
