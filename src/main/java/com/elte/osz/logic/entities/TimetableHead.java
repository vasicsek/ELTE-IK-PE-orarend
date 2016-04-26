package com.elte.osz.logic.entities;



import com.elte.osz.logic.dbhandler.AbstractEntity;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class TimetableHead extends AbstractEntity {   
    
    @Column(updatable = false, nullable = false)
    @Basic
    private Timestamp created;

    @Column(nullable = false)
    @Basic
    private String name;

    @OneToMany(targetEntity = TimetableItem.class)
    private List<TimetableItem> items;

    public TimetableHead() {

    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimetableItem> getItems() {
        return this.items;
    }

    public void setItems(List<TimetableItem> items) {
        this.items = items;
    }

 
}
