package com.elte.tosz.logic.entities;

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

@Entity(name = "Subject")
@Table(name = "Subject")

public class Subject implements Serializable {

    @Column(unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 0)
    @Basic
    private int maxTimeCount;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private String code;

    @Column(unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 0)
    @Basic
    private int estMemberCount;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private String name;

    @Id
    @OneToOne(optional = true, targetEntity = RoomGroup.class)
    private RoomGroup roomGroup;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private int semester;

    @Column(unique = false, updatable = false, insertable = true, nullable = false, length = 255, scale = 0, precision = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(optional = true, targetEntity = Room.class)
    private Room room;

    @Column(unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 0)
    @Basic
    private float estMemberRatio;

    public Subject() {

    }

    public int getMaxTimeCount() {
        return this.maxTimeCount;
    }

    public void setMaxTimeCount(int maxTimeCount) {
        this.maxTimeCount = maxTimeCount;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getEstMemberCount() {
        return this.estMemberCount;
    }

    public void setEstMemberCount(int estMemberCount) {
        this.estMemberCount = estMemberCount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomGroup getRoomGroup() {
        return this.roomGroup;
    }

    public void setRoomGroup(RoomGroup roomGroup) {
        this.roomGroup = roomGroup;
    }

    public int getSemester() {
        return this.semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public float getEstMemberRatio() {
        return this.estMemberRatio;
    }

    public void setEstMemberRatio(float estMemberRatio) {
        this.estMemberRatio = estMemberRatio;
    }
}
