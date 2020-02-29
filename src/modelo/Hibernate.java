package modelo;

import actividad2.AccesoADatos;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hibernate implements AccesoADatos {

    public boolean escribir(String nombre, String posicion) {
        Jugador jugador = new Jugador();
        jugador.setNombreJugador(nombre);
        jugador.setPosicionJugador(posicion);
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        s.save(jugador);
        s.getTransaction().commit();
        s.close();
        sf.close();
        return true;
    }

    public Map<Integer, Jugador> leer() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Query query = s.createQuery("from Jugador");
        List<Jugador> list = query.list();
        s.getTransaction().commit();

        s.close();
        Map<Integer, Jugador> map = list.stream()
                .collect(Collectors.toMap(Jugador::getId, Jugador -> Jugador));
        return map;
    }

    public boolean eliminarUno(int id) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Jugador jugador = new Jugador();
        jugador.setId(id);
        s.beginTransaction();
        s.delete(jugador);
        s.getTransaction().commit();
        s.close();
        return true;
    }

    public Jugador buscarUno(int id) {
        Jugador jugador = null;
        try {
            SessionFactory sf = new Configuration().configure().buildSessionFactory();
            Session s = sf.openSession();
            s.beginTransaction();
            jugador = (Jugador) s.get(Jugador.class, id);
            s.getTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            return jugador;
        }
        return jugador;
    }

    @Override
    public boolean actualizarUno(int id, String nombre, String posicion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
