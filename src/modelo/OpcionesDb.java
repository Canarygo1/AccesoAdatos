/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alejandrocruz
 */
public class OpcionesDb {
        public Map<Integer,String> leer(String tipo){
            File file = new File(tipo);
        Map<Integer,String> MapaResultados = new HashMap();
        
        if(file.exists()){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String linea;
                int contador = 0;
                //Se procede a recorrer el contenido del archivo(se guarda en un HashMap)
                    while((linea = reader.readLine()) != null) {
                        contador++;    
                        MapaResultados.put(contador,linea);
                    }
                    reader.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error:En lectura del archivos"+ex);
            } catch (IOException ex) {
                System.out.println("Error:En lectura del archivos"+ex);
            }
        }  
            System.out.println(MapaResultados.toString());
            return MapaResultados;
       }
}
