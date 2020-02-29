/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

/**
 *
 * @author alejandrocruz
 */
public class pruebaInsert {

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

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8888/BaloncestoJSONServer/escribirJugador.php");
        Gson gson = new Gson();

        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            JugadorJson jugadorJson = new JugadorJson("joselito","manolin");
            Page page = new Page("add",jugadorJson);
            
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
                System.out.println(response.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(pruebaInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
