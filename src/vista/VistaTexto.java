/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import modelo.Jugador;
import modelo.Mysql;

/**
 *
 * @author alejandrocruz
 */
public class VistaTexto {

    Scanner scan;
    private Controlador controlador;

    public VistaTexto(Controlador controlador) {
        this.controlador = controlador;
        scan = new Scanner(System.in);
    }

    public void mostrarmenu() {

        int numero;

        System.out.println("Seleccione una opcion:");
        System.out.println("1 Para trabajar con Archivos y Mysql");
        System.out.println("2 Para trabajar con MongoDb");
        System.out.println("3 Para trabajar con Hibernate");
        System.out.println("4 Para trabajar con Php");
        System.out.println("5 Para trabajar con nodejs");

        numero = scan.nextInt();
        switch (numero) {
            case 1:
                opcionElegida("Mysql");
                break;
            case 2:
                opcionElegida("MongoDb");
                break;
            case 3:
                opcionElegida("Hibernate");
                break;
            case 4:
                opcionElegida("Php");
                break;
            case 5:
                opcionElegida("nodejs");
                break;

        }

    }

    public void opcionElegida(String opcion) {
        Map<Integer, String> opcionesDb = controlador.devuelveOpciones(opcion + ".txt");
        recoreYPintaOpciones(opcionesDb);
        Map<Integer, String[]> opcionesNuevas = new HashMap<>();
        int totalOpciones = opcionesDb.size();
        int opcionesFijas = 6;
        int opcionesDeCopia = totalOpciones - opcionesFijas;
        System.out.println("Opciones" + opcionesDeCopia);
        for (int i = 1; i <= opcionesDeCopia; i++) {
            String[] arrayString = opcionesDb.get(opcionesFijas + i).split(" ");
            String[] arrayHashMap = {arrayString[3], arrayString[5]};
            opcionesNuevas.put(opcionesFijas + i, arrayHashMap);
        }
        System.out.println(totalOpciones);
        System.out.println("-->Introduce tu opcion");
        int numero = scan.nextInt();
        if (numero == 1) {
            System.out.println("Haz elegido mostrar tablas!");
            recoreYPinta(controlador.mostrarTablas(opcion));
        } else if (numero == 2) {
            System.out.println("Haz elegido insertar registros en la base de datos");
            System.out.println("Introduzca un nombre a insertar");
            scan.nextLine();
            String nombre = scan.nextLine();
            System.out.println("Introduzca una posicion a insertar");
            String posicion = scan.nextLine();
            controlador.escribirDato(nombre, posicion, opcion);
        } else if (numero == 3) {
            System.out.println("Haz elegido modificar un registro");
            System.out.println("");
            System.out.println("Introduzca el id del dato a actualizar");
            int id = scan.nextInt();
            System.out.println("Introduzca un nuevo nombre");
            scan.nextLine();
            String nombre = scan.nextLine();
            System.out.println("Introduzca una nueva posicion");
            String posicion = scan.nextLine();
            controlador.actualizarDato(opcion, id, posicion, nombre);
        } else if (numero == 4) {
            System.out.println("Haz elegido eliminar un registro");
            System.out.println("");
            System.out.println("Introduzca el id del dato a eliminar");
            int id = scan.nextInt();
            controlador.eliminar(opcion, id);
        } else if (numero == 5) {
            System.out.println("Haz elegido buscar un registro");
            System.out.println("");
            System.out.println("Introduzca el id del dato a buscar");
            int id = scan.nextInt();
            String resultado = controlador.buscar(opcion, id).toString();
            if (resultado.equals("null null")) {
                System.out.println("jugador no encontrado, disculpe las molestias");
            } else {
                System.out.println("El jugador es :" + resultado);
            }
        } else if (numero == 6) {
            System.out.println("Haz elegido eliminar todos registro");
            System.out.println("");
            controlador.eliminarTodos(opcion);
        } else if (numero > 6 && numero <= totalOpciones) {
            String[] desdeA = opcionesNuevas.get(numero);
            if (controlador.desdeA(desdeA[0], desdeA[1])) {
                System.out.println("Datos copiados con exito");
            } else {
                System.out.println("Error en la copia de datos");
            }
        }
    }

    public void recoreYPinta(Map<Integer, Jugador> Datos) {

        if (Datos.size() > 0) {
            Datos.forEach((Id, jugador) -> System.out.println(Id + " " + jugador));
        } else {
            System.out.println("El archivo/Base de datos esta vaciooo!!!");
        }
    }

    public void recoreYPintaOpciones(Map<Integer, String> datos) {

        if (datos.size() > 0) {
            datos.forEach((Id, texto) -> System.out.println(texto));
        } else {
            System.out.println("El archivo esta vaciooo!!!");
            System.exit(-1);
        }
    }

}
