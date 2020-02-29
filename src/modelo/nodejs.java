/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import actividad2.AccesoADatos;
import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import sun.misc.IOUtils;

/**
 *
 * @author alejandrocruz
 */
public class nodejs implements AccesoADatos {

    @Override
    public Map<Integer, Jugador> leer() {
        try {
            String json = readUrl("http://localhost:3000/Jugadores/");
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
            String query = "http://localhost:3000/Jugadores";
            JugadorJson jugador = new JugadorJson(nombre, posicion);
            Gson gson = new Gson();
            String json = gson.toJson(jugador);

            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            String results = s.hasNext() ? s.next() : "";

            in.close();
            conn.disconnect();
            if (results.equals("ok")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminarUno(int id) {
        try {
            URL url = new URL("http://localhost:3000/Jugadores/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
        } catch (ProtocolException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public Jugador buscarUno(int id) {
        try {
            String json = readUrl("http://localhost:3000/Jugadores/" + id);
            Gson gson = new Gson();
            JsonReader.JugadorJson page = gson.fromJson(json, JsonReader.JugadorJson.class);
            Jugador jugador = new Jugador(page.nombre, page.posicion);
            if (page.error != null) {
                System.out.println(page.error);
                return null;

            } else {
                return jugador;
            }
        } catch (IOException ex) {
            Logger.getLogger(Php.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Php.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean actualizarUno(int id, String nombre, String posicion) {
        try {
            String query = "http://localhost:3000/Jugadores/" + id;
            JugadorJson jugador = new JugadorJson(nombre, posicion);
            Gson gson = new Gson();
            String json = gson.toJson(jugador);

            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            String results = s.hasNext() ? s.next() : "";

            in.close();
            conn.disconnect();
            if (results.equals("ok")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Jugador buscar(int id) {
        try {
            String json = readUrl("http://localhost:3000/Jugadores/" + id);
            Gson gson = new Gson();
            JsonReader.JugadorJson page = gson.fromJson(json, JsonReader.JugadorJson.class);
            Jugador jugador = new Jugador(page.nombre, page.posicion);

            return jugador;

        } catch (IOException ex) {
            Logger.getLogger(Php.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Php.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean eliminarTodos() {
        try {
            URL url = new URL("http://localhost:3000/deleteAll/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
        } catch (ProtocolException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(nodejs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
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

        JugadorJson jugadorAnnadir;

        public Page(JugadorJson jugadorJson) {
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
