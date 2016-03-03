/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic;

import com.elte.osz.logic.entities.ProposedTime;
import com.elte.osz.logic.entities.Syllabus;
import java.util.List;

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
        
       List<Syllabus> syls = ds.getCtrlSyllabus().findSyllabusEntities();
       
       return ;
    }
    
    public OszDS getDataSet(){
        return ds;
    }
}
