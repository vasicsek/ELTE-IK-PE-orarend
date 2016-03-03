package com.elte.osz.logic.entities;

//
// This file was generated by the JPA Modeler
//

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "ProposedTimeItem")
@Table(name = "ProposedTimeItem")

public class ProposedTimeItem implements Serializable {

    @Column(nullable = false)
    @Basic
    private int hour;

    @Column(nullable = false)
    @Basic
    private int len;

    @Id
    @OneToOne(optional = false, targetEntity = Subject.class)
    private Subject subject;

    @Column(updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Basic
    private int day;

    @Id
    @OneToOne(optional = false, targetEntity = Room.class)
    private Room room;

    public ProposedTimeItem() {

    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getLen() {
        return this.len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
