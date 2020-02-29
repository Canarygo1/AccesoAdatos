/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandrocruz
 */
public class DatosDb {
    private String username;
    private String password;
    private String drul;


    public DatosDb() throws IOException {
        lecturadeDatos();
    }
    public void lecturadeDatos(){
          Properties p = new Properties();
        try {
            p.load(new FileInputStream("config.ini"));
            username = p.getProperty("username");
            password = p.getProperty("password");
            drul = p.getProperty("drul");

        } catch (FileNotFoundException ex) {
            System.out.println("Error"+ex);
        } catch (IOException ex) {
            Logger.getLogger(DatosDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDrul() {
        return drul;
    }   
}
