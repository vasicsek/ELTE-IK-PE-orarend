package com.elte.tosz.logic.dbhandler;

import java.util.List;

/**
 *
 * @author Tóth Ákos
 * @param <E> Entity type
 */
public interface IEntity<E> {

        int hashcode();
        int getId();        
        boolean equals(Object object);        
        String toString();
    
    
}
