package com.elte.osz.logic;
/**
 *
 * @author RMUGLK
 */
public class Osz {
    
    private final OszDS ds;
    
    public Osz(){
        ds = new OszDS();
    }
    public OszDS getDataSet(){
        return ds;
    }

    public void close() {
      ds.close();
    }
}
