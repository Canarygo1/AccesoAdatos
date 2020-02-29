/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import actividad2.AccesoADatos;
import actividad2.conexionDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandrocruz
 */
public class Mysql implements AccesoADatos {

    private String resultadoConsulta;
    conexionDb configuracion = new conexionDb();

    public Map<Integer, Jugador> leer() {
        try {
            //Se crea el mapa y se realiza la consulta con prepareStatement
            Map<Integer, Jugador> MapaResultados = new HashMap<>();
            Connection conexion = configuracion.obtener();
            PreparedStatement ps = conexion.prepareStatement("Select * from jugadores");
            ResultSet resultado;
            resultado = ps.executeQuery();
            //Se rellena el haspMap con objectos.
            while (resultado.next()) {
                Jugador Jugador = new Jugador(resultado.getString(2), resultado.getString(3));
                Jugador.setId(resultado.getInt(1));
                MapaResultados.put(resultado.getInt(1), Jugador);
            }
            //Se cierra la conexion a la base de datos.
            ps.close();
            return MapaResultados;

        } catch (SQLException ex) {
            System.out.println("Error:" + ex);
        }
        return null;
    }

    //Se realiza funcion boolean para controlar si la escritura a sido correcta.
    public boolean escribir(String nombre, String posicion) {
        try {
            //Se realiza creacion del objeto posterior escritura.
            Map<Integer, Jugador> MapaResultados = leer();
            Jugador Jugador = new Jugador(nombre, posicion);
            Connection conexion = configuracion.obtener();
            for (Map.Entry<Integer, Jugador> entry : MapaResultados.entrySet()) {
                if (entry.getValue().getNombreJugador().equals(nombre) && entry.getValue().getPosicionJugador().equals(posicion)) {
                    return false;
                }
            }
            PreparedStatement ps = conexion.prepareStatement("insert into jugadores (nombre,posicion) values(?,?)");
            ps.setString(1, Jugador.getNombreJugador());
            ps.setString(2, Jugador.getPosicionJugador());
            int registros = ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error:" + ex);
        }
        return false;
    }

    public Jugador buscar(int id) {
        try {
            Connection conexion = configuracion.obtener();
            PreparedStatement ps = conexion.prepareStatement("select nombre,posicion from jugadores where id_jugador = (?)");
            ps.setInt(1, id);
            ResultSet resultado;
            resultado = ps.executeQuery();
            resultado.next();
            Jugador Jugador = new Jugador(resultado.getString(1), resultado.getString(2));
            return Jugador;

        } catch (SQLException ex) {
            return null;
        }
    }

    public boolean eliminarUno(int id) {
        try {
            Connection conexion = configuracion.obtener();
            PreparedStatement ps = conexion.prepareStatement("delete from jugadores where id_jugador = (?)");
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean actualizarUno(int id, String nombre, String posicion) {
        try {
            Connection conexion = configuracion.obtener();
            PreparedStatement ps = conexion.prepareStatement("update jugadores set nombre = (?) , posicion = (?) where id_jugador = (?)");
            ps.setString(1, nombre);
            ps.setString(2, posicion);
            ps.setInt(3, id);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    @Override
    public boolean eliminarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
