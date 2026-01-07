package util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.TreeMap;


import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.query.Query;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import com.db4o.events.EventRegistry;
import com.db4o.events.EventRegistryFactory;

public class Util {
    
    // O manager continua sendo a única instância da conexão
    private static ObjectContainer manager = null; // Começa como null

    public static ObjectContainer conectarBanco() {
        if (manager == null || manager.ext().isClosed()) { 
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            
            config.common().messageLevel(0); 
            
            config.common().objectClass(Entregador.class).cascadeOnUpdate(true);
            config.common().objectClass(Entregador.class).cascadeOnActivate(true);
            config.common().objectClass(Entregador.class).cascadeOnDelete(false); 
            
            config.common().objectClass(Entrega.class).cascadeOnUpdate(true);
            config.common().objectClass(Entrega.class).cascadeOnActivate(true);
            config.common().objectClass(Entrega.class).cascadeOnDelete(false); 
            
            config.common().objectClass(Pedido.class).cascadeOnUpdate(true);
            config.common().objectClass(Pedido.class).cascadeOnActivate(true);
            config.common().objectClass(Pedido.class).cascadeOnDelete(false); 

            manager = Db4oEmbedded.openFile(config, "banco_entrega.db4o");
            ControleID.ativar(true, manager);
            
            
        }
        
        return manager;
    }
    
    public static void desconectar() {
        if (manager != null && !manager.ext().isClosed()) {
            manager.close();
            manager = null; 
        }
    }
    
    
    

	public static ObjectContainer getManager() {
		return manager;
	}

	public static String getIPservidor() {
		// TODO Auto-generated method stub
		return null;
	}
}
 

