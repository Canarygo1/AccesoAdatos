/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import actividad2.AccesoADatos;
import actividad2.ApiRequests;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandrocruz
 */
public class Php implements AccesoADatos {

    @Override
    public Map<Integer, Jugador> leer() {
        try {
            String json = readUrl("http://localhost:8888/BaloncestoJSONServer/leeJugadores.php");
            Gson gson = new Gson();
            JsonReader.Page page = gson.fromJson(json, JsonReader.Page.class);
            System.out.println(page.estado);
            int contador = 0;

            HashMap<Integer, modelo.Jugador> prueba = new HashMap();
            for (JsonReader.JugadorJson item : page.jugadores) {
                Jugador jugador = new Jugador(item.nombre, item.posicion);
                prueba.put(contador, jugador);
                contador++;

            }
            return prueba;

        } catch (IOException ex) {
            Logger.getLogger(Php.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Php.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean escribir(String nombre, String posicion) {
        try {
            URL url = new URL("http://localhost:8888/BaloncestoJSONServer/escribirJugador.php");
            Gson gson = new Gson();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            JugadorJson jugadorJson = new JugadorJson(nombre, posicion);
            Page page = new Page("add", jugadorJson);

            String jsonInputString = gson.toJson(page);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(pruebaInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean eliminarUno(int id) {
        throw new UnsupportedOperationException("No implementado todaviaa.");
    }

    @Override
    public boolean actualizarUno(int id, String nombre, String posicion) {
        throw new UnsupportedOperationException("No implementado todaviaa.");
    }

    @Override
    public Jugador buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static class JugadorJson {

        public JugadorJson(String nombre, String posicion) {
            this.nombre = nombre;
            this.posicion = posicion;
        }

        String nombre;
        String posicion;
    }

    static class Page {

        String peticion;
        JugadorJson jugadorAnnadir;

        public Page(String peticion, JugadorJson jugadorJson) {
            this.peticion = peticion;
            this.jugadorAnnadir = jugadorJson;
        }

    }

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
}
