/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad2;

import java.util.Map;
import modelo.Jugador;

/**
 *
 * @author alejandrocruz
 */
public interface AccesoADatos {

    public Map<Integer, Jugador> leer();

    public boolean escribir(String nombre, String posicion);

    public boolean eliminarUno(int id);

    public boolean actualizarUno(int id, String nombre, String posicion);

    public Jugador buscar(int id);

    public boolean eliminarTodos();
}
