package presentation;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.util.ArrayList;

/**
 * 
 */
public class Log{
    public static String nombre="Halloween";
    
    
	    public static void record(Exception e) {
	
	    try {
	        Logger logger = Logger.getLogger(nombre);
	        logger.setUseParentHandlers(false);
	        FileHandler file = new FileHandler(nombre + ".log", true);
	        file.setFormatter(new SimpleFormatter());
	        logger.addHandler(file);
	
	        // Registrar la excepción en el archivo de registro
	        logger.log(Level.WARNING, "Advertencia: " + e.toString(), e);
	
	        // Mostrar un mensaje especial de alerta al usuario
	        System.err.println("Se ha producido la excepcion: " + e.getMessage());
	
	        // Continuar la ejecución (si es apropiado)
	        
	
	        file.close();
	    	} catch (Exception oe) {
	        // Si se produce un error al manejar la excepción, imprime la traza de la pila y registra en el archivo de registro (si es necesario).
	        oe.printStackTrace();
	    	}
	    }
}
