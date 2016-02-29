/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.tosz.logic;

import com.elte.tosz.logic.entities.ProposedTime;
import java.util.List;

/**
 *
 * @author cauchy
 */
public class Osz {
    
    private OszDS ds;
    
    public Osz(){
        ds = new OszDS();
    }
    public List<ProposedTime> generate(){
       return null;
    }
    
    public OszDS getDataSet(){
        return ds;
    }
}
