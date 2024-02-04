package domain;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;


public class Persistencia {


	public static void save(File archivo, Object objeto) throws GomokuException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(archivo))) {
            outputStream.writeObject(objeto);
   
            System.out.println("Objeto guardado con éxito.");
        } catch (Exception e) {
            throw new GomokuException("Error al guardar el objeto: " + e.getMessage());
        }
    }
	public static Gomoku open(File archivo) throws GomokuException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(archivo))) {
           
            return (Gomoku) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new GomokuException("Error al cargar el objeto: " + e.getMessage());
        }
    }

}