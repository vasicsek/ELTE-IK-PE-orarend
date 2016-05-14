package com.elte.osz.logic.entities;

import com.elte.osz.logic.dbhandler.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

/**
 * Szemeszter reprezentálása: Név + szemeszer elemek(SemesterItem)
 * @author RMUGLK
 */
@Entity
public class Semester extends BaseEntity implements Serializable {

    @Column( updatable = false, insertable = true, nullable = false)
    @Basic
    private Timestamp created;
    

    @Column(nullable = false)
    @Basic    
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = SemesterItem.class)
    private  Set<SemesterItem> items = new TreeSet<SemesterItem>();

    public Semester() {

    }
    
    @PrePersist
    void preInsert() {
        created = new Timestamp(new Date().getTime());
    }
    
    
    public Timestamp getCreated() {
        return this.created;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SemesterItem>  getItems(){
        return this.items;
    }
    public List<SemesterItem> getItemsAsList() {
        return new ArrayList(this.items);
    }
    
    public void setItems(Set<SemesterItem> items) {
        this.items = items;
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
        final Semester other = (Semester) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        
        return true;
    }
  
    
    
}
