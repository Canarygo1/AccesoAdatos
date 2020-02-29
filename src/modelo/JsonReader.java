/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Jugador;
import org.json.simple.ItemList;

public class JsonReader {

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

    }

    static class JugadorJson {

        String id_jugador;
        String nombre;
        String posicion;
        String error;

        public String getError() {
            return error;
        }

    }

    static class Page {

        String estado;
        List<JugadorJson> jugadores;
    }

    public static void main(String[] args) throws Exception {

        String json = readUrl("http://localhost:8888/BaloncestoJSONServer/leeJugadores.php");

        Gson gson = new Gson();
        Page page = gson.fromJson(json, Page.class);

        System.out.println(page.estado);
        HashMap<Integer, modelo.Jugador> prueba = new HashMap();
        for (JugadorJson item : page.jugadores) {
            Jugador jugador = new Jugador(item.nombre, item.posicion);
            prueba.put(0, jugador);
            System.out.println("    " + jugador.getNombreJugador() + " " + jugador.getPosicionJugador());
        }
    }
}
