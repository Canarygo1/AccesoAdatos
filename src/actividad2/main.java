/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad2;

import controlador.Controlador;
import modelo.Mysql;
import java.sql.SQLException;
import vista.VistaTexto;

/**
 *
 * @author alejandrocruz
 */
public class main {
    public static void main(String[] args)  {
        Controlador Controlador = new Controlador();
        VistaTexto VistaTexto = new VistaTexto(Controlador);
        VistaTexto.mostrarmenu();
    }
}
