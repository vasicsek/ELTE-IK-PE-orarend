package com.elte.osz.logic.entities;


import com.elte.osz.logic.dbhandler.AbstractEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Subject extends AbstractEntity {
    
    
    @Column(unique = true, nullable = false)
    @Basic
    private String code;

    @Column(nullable = false)
    @Basic
    private String name;

    @Column(nullable = false)
    @Basic
    private int semester;

    @Basic
    private String subjectType;

    public Subject() {

    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return this.semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    public String getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

   
}
