/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import actividad2.AccesoADatos;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Jugador;
import modelo.OpcionesDb;

/**
 *
 * @author alejandrocruz
 */
public class Controlador {

    OpcionesDb opcionesdb = new OpcionesDb();

    public Map<Integer, String> devuelveOpciones(String tipo) {
        return opcionesdb.leer(tipo);
    }

    public boolean escribirDato(String nombre, String posicion, String clase) {
        try {
            AccesoADatos metodoAcceso = (AccesoADatos) Class.forName("modelo." + clase).newInstance();
            return metodoAcceso.escribir(nombre, posicion);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Map<Integer, Jugador> mostrarTablas(String clase) {
        try {
            AccesoADatos metodoAcceso = (AccesoADatos) Class.forName("modelo." + clase).newInstance();
            return metodoAcceso.leer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashMap<Integer, Jugador>();
    }

    public boolean actualizarDato(String clase, int id, String posicion, String nombre) {
        try {
            AccesoADatos metodoAcceso = (AccesoADatos) Class.forName("modelo." + clase).newInstance();
            return metodoAcceso.actualizarUno(id, posicion, nombre);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public boolean eliminar(String clase, int id) {
        try {
            AccesoADatos metodoAcceso = (AccesoADatos) Class.forName("modelo." + clase).newInstance();
            return metodoAcceso.eliminarUno(id);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public boolean eliminarTodos(String clase) {
        try {
            AccesoADatos metodoAcceso = (AccesoADatos) Class.forName("modelo." + clase).newInstance();
            return metodoAcceso.eliminarTodos();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public Jugador buscar(String clase, int id) {
        try {
            AccesoADatos metodoAcceso = (AccesoADatos) Class.forName("modelo." + clase).newInstance();
            return metodoAcceso.buscar(id);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public boolean desdeA(String desde, String a) {
        try {
            AccesoADatos copiaDesde = (AccesoADatos) Class.forName("modelo." + desde).newInstance();
            AccesoADatos copiaA = (AccesoADatos) Class.forName("modelo." + a).newInstance();
            Map<Integer, Jugador> datosCopiados = copiaDesde.leer();
            for (Map.Entry<Integer, Jugador> entry : datosCopiados.entrySet()) {
                String nombre = entry.getValue().getNombreJugador();
                String posicion = entry.getValue().getPosicionJugador();
                copiaA.escribir(nombre, posicion);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InstantiationException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
