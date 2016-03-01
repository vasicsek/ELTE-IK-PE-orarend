package com.elte.osz.logic.dbhandler;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 *
 * @author Tóth Ákos
 * @param <E> Entity type
 */
public abstract class IEntity<E> implements Serializable {
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public IEntity() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/
    
    @Override    
    public boolean equals(Object other){       
        
        if ( other == this )
           return true;
        
       Field[] thisFields= this.getClass().getDeclaredFields();
       Field[] objectFields= other.getClass().getDeclaredFields();
        //System.out.println(this.getClass().getName() + " - " + other.getClass().getName());
       return Arrays.deepEquals(thisFields, objectFields);
              
    }
    @Override
    public  String toString(){
      /*  StringBuilder sb = new StringBuilder();
        Arrays.asList( this.getClass().getFields() ).forEach( (field) -> {
          sb.append(field.getName() + "="+ field.toString());
        });*/
        return /*sb.toString();*/ "IENTITY";
    }
    
}
