package com.elte.osz.logic.entities;

import com.elte.osz.logic.dbhandler.BaseEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Tanár reprezentálása
 * @author RMUGLK
 */
@Entity
public class Teacher extends BaseEntity implements Serializable {

    @Column(nullable = false)
    @Basic
    private String name;

    public Teacher() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){        
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name);
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
        final Teacher other = (Teacher) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
}
