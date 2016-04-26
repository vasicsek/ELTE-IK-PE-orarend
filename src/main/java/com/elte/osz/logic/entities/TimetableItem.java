package com.elte.osz.logic.entities;



import com.elte.osz.logic.dbhandler.AbstractEntity;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TimetableItem extends AbstractEntity {
    
    @Column(nullable = false)
    @Basic
    private String teacher;
    
    @OneToOne(optional = false, targetEntity = Subject.class)
    private Subject subject;

    @Column(nullable = false)
    @Basic
    private Timestamp startTime;

    @Column(nullable = false)
    @Basic
    private Timestamp endTime;

    @Column(nullable = false)
    @Basic
    private String room;

    public TimetableItem() {
    }

    public String getTeacher() {
        return this.teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }    
}
