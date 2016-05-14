package com.elte.osz.logic.entities;

import com.elte.osz.logic.Building;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Konvertáló osztály, hogy a adatbázis building mezőjét(string) java Building
 * enummá alakítsa.
 *
 * @author RMUGLK
 */
@Converter(autoApply = true)
public class BuildingConverter implements AttributeConverter<Building, String> {

    @Override
    public String convertToDatabaseColumn(Building attribute) {
        return attribute.toString();
    }

    @Override
    public Building convertToEntityAttribute(String attribute) {
        for (Building b : Building.values()) {
            if (b.toString().toLowerCase().equals(attribute.toLowerCase())) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unkown building in enumeration:" + attribute);
    }
};
