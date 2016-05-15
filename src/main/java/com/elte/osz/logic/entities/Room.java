package com.elte.osz.logic.entities;

import com.elte.osz.logic.Building;
import com.elte.osz.logic.dbhandler.BaseEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Terem reprezentálása
 * @author RMUGLK
 */
@Entity
public class Room extends BaseEntity implements Serializable {

    @Column(nullable = false)
    @Basic
    private String name;

    @Column(nullable = false)
    @Basic
    private byte floor;

    @Basic
    @Enumerated(EnumType.STRING)
    @Convert(converter = BuildingConverter.class)
    private Building building;

    public Room() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getFloor() {
        return this.floor;
    }

    public void setFloor(byte floor) {
        this.floor = floor;
    }
    public void setFloor(int floor) {
        this.floor = (byte)floor;
    }

    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public int hashCode() {
       
        return Objects.hash(id,name,building,floor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.floor != other.floor) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.building != other.building) {
            return false;
        }
        return true;
    }
    
}
