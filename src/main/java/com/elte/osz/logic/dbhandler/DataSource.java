/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.dbhandler;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DataSource {
    protected EntityManagerFactory emf;
    
    public DataSource(String name){
        emf = Persistence.createEntityManagerFactory(name);
    }
    public void close(){
        emf.close();
    }
}
