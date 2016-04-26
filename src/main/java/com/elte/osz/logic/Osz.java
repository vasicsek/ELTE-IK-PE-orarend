/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic;

/**
 *
 * @author Tóth Ákos 
 */
public class Osz {
    
    private OszDS ds;
    
    public Osz(){
        ds = new OszDS();
    }
    public void generate(boolean even_semester){
        
       
       
       return ;
    }
    
    public OszDS getDataSet(){
        return ds;
    }

    public void close() {
        // program bezárása
    }
}
