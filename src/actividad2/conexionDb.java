/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandrocruz
 */
public class conexionDb {
    private Connection conexion = null;
    private String username; 
    private String password;
    private String drul;
    DatosDb DatosDb;
    
    public Connection obtener()  {
        try {
            DatosDb = new DatosDb();
            username = DatosDb.getUsername();
            password = DatosDb.getPassword();
            drul = DatosDb.getDrul();
            if(conexion == null){
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                conexion = DriverManager.getConnection(drul, username,password);      
            }
        }
            catch(SQLException ex){
                System.out.println("Consejo: Revise los datos.La base de datos esta activa?");
                System.out.println("El error SQL es "+ex);
                System.exit(-1);
            }
            catch(ClassNotFoundException ex){
                System.out.println("Error classFound"+ex);
            }
            catch (IOException ex) {
                    
                }
        return conexion;
    }
    
    public void cerrar() throws SQLException {
        if(conexion != null)
            conexion.close();
    }
}
