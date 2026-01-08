package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Util {
    
    private static EntityManagerFactory factory;
    private static EntityManager manager;

    public static void conectar() {
        // Apenas garante que o manager existe
        getManager();
    }

    public static EntityManager getManager() {
        if (manager == null || !manager.isOpen()) { 
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory("entrega-ja-pu");
            }
            manager = factory.createEntityManager();
        }
        return manager;
    }
    
    public static void desconectar() {
        if (manager != null && manager.isOpen()) {
            manager.close();
            manager = null;
        }
        if (factory != null && factory.isOpen()) {
            factory.close();
            factory = null;
        }
    }
}