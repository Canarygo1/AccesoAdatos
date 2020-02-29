/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import actividad2.AccesoADatos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandrocruz
 */
public class Archivo implements AccesoADatos {

    File file = new File("fichero.txt");

    @Override
    public Map<Integer, Jugador> leer() {
        Map<Integer, Jugador> MapaResultados = new HashMap();

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String linea;
                //Se procede a recorrer el contenido del archivo(se guarda en un HashMap)
                while ((linea = reader.readLine()) != null) {
                    String[] arrayString = linea.split(";");
                    int id = Integer.valueOf(arrayString[0]);
                    Jugador jugador = new Jugador(arrayString[1], arrayString[2]);
                    jugador.setId(id);
                    MapaResultados.put(jugador.getId(), jugador);
                }
                reader.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error:En lectura del archivos" + ex);
            } catch (IOException ex) {
                System.out.println("Error:En lectura del archivos" + ex);
            }
        }
        return MapaResultados;
    }

    @Override
    public boolean escribir(String nombre, String posicion) {
        FileWriter fileWriter = null;
        //se escribe en el archivo
        try {
            Integer mayorId = 0;
            Map<Integer, Jugador> MapaResultados = leer();
            //Se recorre el HashMap de lectura para ver el ultimo id y ver la ultima posicion 
            for (Map.Entry<Integer, Jugador> entry : MapaResultados.entrySet()) {
                mayorId = entry.getKey();
                //Se comprueba que no existiera un dato igual.
                if (entry.getValue().getNombreJugador().equals(nombre) && entry.getValue().getPosicionJugador().equals(posicion)) {
                    return false;
                }
            }
            mayorId = mayorId + 1;
            //Se escribe en el fichero txt 
            fileWriter = new FileWriter(file, true);
            String contenido = mayorId + ";" + nombre + ";" + posicion;
            fileWriter.write(contenido + '\n');
            fileWriter.close();
            return true;
        } catch (IOException ex) {
            System.out.println("");
        }

        return false;
    }

    public boolean escribir(Map entrada) {
        Map<Integer, Jugador> MapaResultados = entrada;

        FileWriter fileWriter = null;
        //se escribe en el archivo
        try {
            fileWriter = new FileWriter(file);
            for (Map.Entry<Integer, Jugador> entry : MapaResultados.entrySet()) {
                System.out.println(entry.getValue());
                //Se escribe en el fichero txt 
                String contenido = entry.getValue().getId() + ";" + entry.getValue().getNombreJugador() + ";" + entry.getValue().getPosicionJugador();
                fileWriter.write(contenido + '\n');
            }
            fileWriter.close();
            return true;
        } catch (IOException ex) {
            System.out.println("");
        }

        return false;
    }

    public Jugador buscar(int id) {
        Map<Integer, Jugador> MapaResultados = leer();
        for (Map.Entry<Integer, Jugador> entry : MapaResultados.entrySet()) {
            //Se comprueba que no existiera un dato igual.
            if (entry.getValue().getId() == id) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean eliminarUno(int id) {
        Map<Integer, Jugador> MapaResultados = leer();
        Iterator<Integer> it = MapaResultados.keySet().iterator();
        while (it.hasNext()) {
            Integer bigDecimal = it.next();
            System.out.println(bigDecimal);
            if (id == bigDecimal) {
                System.out.println("Son iguales");
                it.remove();
            }
            escribir(MapaResultados);
        }

        return true;
    }

    public boolean actualizarUno(int id, String nombre, String posicion) {
        Map<Integer, Jugador> MapaResultados = leer();
        for (Map.Entry<Integer, Jugador> entry : MapaResultados.entrySet()) {
            if (entry.getValue().getId() == id) {
                entry.getValue().setNombreJugador(nombre);
                entry.getValue().setPosicionJugador(posicion);
                System.out.println(entry);
            }
            if (escribir(MapaResultados)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
