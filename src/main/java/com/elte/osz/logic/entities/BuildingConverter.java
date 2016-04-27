/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.entities;

import com.elte.osz.logic.Building;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Tóth Ákos
 */
@Converter(autoApply=true)
public class BuildingConverter implements AttributeConverter<Building, String> {

    @Override
    public String convertToDatabaseColumn(Building attribute) {
         return attribute.toString();
    }

    @Override
    public Building convertToEntityAttribute(String attribute) {
       for( Building b : Building.values() ){
           if ( b.toString().equals(attribute) )
               return b;
       }
       
       throw new IllegalArgumentException("Unkown building in enumeration:"+attribute);
    }
    
};