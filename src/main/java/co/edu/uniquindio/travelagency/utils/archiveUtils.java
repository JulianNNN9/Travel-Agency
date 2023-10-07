package co.edu.uniquindio.travelagency.utils;

import lombok.extern.java.Log;
import java.io.*;
import java.util.List;

@Log

public class archiveUtils {

    public static void serializerObjet(String ruta, Object objeto) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(ruta);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(objeto);

        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public static List<Object> deserializerObjet(String ruta) {
        try (FileInputStream fileInputStream = new FileInputStream(ruta);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){

            return (List<Object>) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }

        return null;
    }

}
