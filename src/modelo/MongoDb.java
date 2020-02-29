/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import actividad2.AccesoADatos;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;

/**
 *
 * @author alejandrocruz
 */
public class MongoDb implements AccesoADatos {

    MongoClient mongo = new MongoClient("localhost", 27017);
    MongoDatabase database = mongo.getDatabase("jugadores");

    @Override
    public Map<Integer, Jugador> leer() {
        HashMap<Integer, Jugador> prueba = new HashMap();

        List<Document> depositos = database.getCollection("jugadores").find().into(new ArrayList<Document>());

        for (Document deposito : depositos) {
            Jugador jugador = new Jugador();
            jugador.setId(deposito.getInteger("id"));
            jugador.setNombreJugador(deposito.getString("nombre"));
            jugador.setPosicionJugador(deposito.getString("posicion"));
            prueba.put(jugador.getId(), jugador);
        }
        return prueba;
    }

    @Override
    public boolean escribir(String nombre, String posicion) {
        int UltimoId = leer().size() + 1;
        MongoCollection<Document> collection = database.getCollection("jugadores");
        Document document = new Document()
                .append("id", UltimoId)
                .append("nombre", nombre)
                .append("posicion", posicion);
        collection.insertOne(document);
        return true;
    }

    public Jugador buscar(String nombre) {
        MongoCollection<Document> collection = database.getCollection("jugadores");
        FindIterable<Document> iterDoc = collection.find(Filters.eq("nombre", nombre));
        Jugador jugador = new Jugador();
        for (Document document : iterDoc) {
            jugador.setId(document.getInteger("id"));
            jugador.setNombreJugador(document.getString("nombre"));
            jugador.setPosicionJugador(document.getString("posicion"));
        }
        return jugador;
    }

    public boolean actualizarUno(int id, String posicion, String nombre) {
        MongoCollection<Document> collection = database.getCollection("jugadores");
        collection.updateOne(Filters.eq("id", id), Updates.set("nombre", nombre));
        collection.updateOne(Filters.eq("id", id), Updates.set("posicion", posicion));
        return true;
    }

    public boolean eliminarUno(int id) {
        try {
            MongoCollection<Document> collection = database.getCollection("jugadores");
            collection.deleteOne(Filters.eq("id", id));
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public Jugador buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
