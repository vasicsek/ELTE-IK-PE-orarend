package com.elte.osz.logic.entities;

import com.elte.osz.logic.dbhandler.AbstractEntity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class TimetableHead extends AbstractEntity implements Serializable {

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false, nullable = false)
    @Basic
    private Timestamp created;

    @Column(unique = true, nullable = false)
    @Basic
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = TimetableItem.class)
    private List<TimetableItem> items;

    public TimetableHead() {
        super();
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
