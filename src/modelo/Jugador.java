/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author alejandrocruz
 */
public class Jugador {

    private int Id;
    private String nombreJugador = "";
    private String posicionJugador = "";

    Jugador(String nombreJugador, String posicionJugador) {
        this.nombreJugador = nombreJugador;
        this.posicionJugador = posicionJugador;
    }

    Jugador() {
    }

    public int getId() {
        return Id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getPosicionJugador() {
        return posicionJugador;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    @Override
    public String toString() {
        return (nombreJugador + " " + posicionJugador);
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setPosicionJugador(String posicionJugador) {
        this.posicionJugador = posicionJugador;
    }
}
