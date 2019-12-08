/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.cache;

import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author aminos
 */
public class ContextCache {

    private static HashMap<String, Object> cache;
    private Context context;
    private static ContextCache instance;

    private ContextCache() {
        cache = new HashMap<String, Object>();

        try {
            context = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
 
    public Object getProxy(String jndi) {
        Object object = null;
		if (cache.get(jndi) != null) {
			return cache.get(jndi);
		} else {
			try {
				object = context.lookup(jndi);
				if (object != null) {
					cache.put(jndi, object);
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return object;
    }
    
    	public static ContextCache getInstance() {
		if (instance == null)
			instance = new ContextCache();
		return instance;
	}
    
}
