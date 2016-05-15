/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.dbhandler;

import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DataSource {
    protected final EntityManagerFactory emf;

    public EntityManagerFactory getEmf() {
        return emf;
    }
    
    public DataSource(String name,Map map){
        emf = Persistence.createEntityManagerFactory(name,map);
        
    }
    public void close(){
        emf.close();
    }
    
    public DataSource(String name){
        emf = Persistence.createEntityManagerFactory(name);
        
    }
    
}
