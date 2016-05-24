package com.elte.osz.logic.entities;

import com.elte.osz.logic.Department;
import com.elte.osz.logic.dbhandler.BaseEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.eclipse.persistence.annotations.Index;

/**
 * Tantárgyak reprezentálása, időpont, tanár, terem stb nélkül.
 * @author RMUGLK
 */
@Entity
public class Subject extends BaseEntity implements Serializable {
    /**
    * Tantárgy kódja
    */
    @Column(nullable = false)
    @Basic
    @Index
    private String code;
    /**
     * Tantárgy neve
     */
    @Column(nullable = false)
    @Basic
    @Index
    private String name;
    /**
     * Ajánlott féléve a tantárgynak (tipikusan 1-6 szám)
     */
    @Index
    @Basic
    private byte semester;
    /**
     * Gyakorlati órák száma
     */
    @Basic
    private byte hours_practical;
    /**
     * Esti órák száma
     */
    @Basic
    private byte hours_nightly;

    /**
     * Kar(IK,TTK, stb.)
     */
    @Index
    @Column(nullable = false)
    @Basic    
    @Enumerated(EnumType.STRING)
    private Department department;

    /**
     * Előadás órák száma
     */
    @Basic
    private byte hours_presentation;
    /**
     * Tantárgy típus(előadás,gyakorlat stb.)
     */
    @Index
    @Basic
    private String subjectType;

    public Subject() {

    }
    /**
     * {@link Subject#code}
     */
    public String getCode() {
        return this.code;
    }
    /**
     * {@link Subject#code}
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * {@link Subject#name}
     */
    public String getName() {
        return this.name;
    }
    /**
     * {@link Subject#name}
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * {@link Subject#semester}
     */
    public byte getSemester() {
        return this.semester;
    }
    /**
     * {@link Subject#semester}
     */
    public void setSemester(int semester){
        this.semester = (byte) semester;
    }
    /**
     * {@link Subject#semester}
     */
    public void setSemester(byte semester) {
        this.semester = semester;
    }
    /**
     * {@link Subject#hours_practical}
     */
    public byte getHours_practical() {
        return this.hours_practical;
    }
    /**
     * {@link Subject#hours_practical}
     */
    public void setHours_practical(byte hours_practical) {
        this.hours_practical = hours_practical;
    }
    /**
     * {@link Subject#hours_practical}
     */
    public void setHours_practical(int hours_practical) {
        this.hours_practical = (byte) hours_practical;
    }
    /**
     * {@link Subject#hours_nightly}
     */
    public byte getHours_nightly() {
        return this.hours_nightly;
    }
    /**
     * {@link Subject#hours_nightly}
     */
    public void setHours_nightly(byte hours_nightly) {
        this.hours_nightly = hours_nightly;
    }
    /**
     * {@link Subject#hours_nightly}
     */  
    public void setHours_nightly(int hours_nightly) {
       this.hours_nightly = (byte) hours_nightly;
    }
    /**
     * {@link Subject#department}
     */
    public Department getDepartment() {
        return this.department;
    }
    /**
     * {@link Subject#department}
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
    /**
     * {@link Subject#hours_presentation}
     */
    public byte getHours_presentation() {
        return this.hours_presentation;
    }
    /**
     * {@link Subject#hours_presentation}
     */
    public void setHours_presentation(byte hours_presentation) {
        this.hours_presentation = hours_presentation;
    }
    /**
     * {@link Subject#hours_presentation}
     */    
    public void setHours_presentation(int hours_presentation) {
        this.hours_presentation = (byte) hours_presentation;
    }
    /**
     * {@link Subject#subjecType}
     */
    public String getSubjectType() {
        return this.subjectType;
    }
    /**
     * {@link Subject#subjecType}
     */
    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }
      @Override
    public String toString(){        
        return "["+this.id + "] "+ this.name + "["+ code +"]";
    }

    @Override
    public int hashCode() {
        
       // System.out.println("Subject::hashCode()");
       //return Objects.hash(id,code,name,department,semester,subjectType,hours_nightly,hours_practical,hours_presentation);
       return  Objects.hash(id,code,name);
       //return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
     //   System.out.println("Subject::equals()");
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subject other = (Subject) obj;
        if (this.semester != other.semester) {
            return false;
        }
        if (this.hours_practical != other.hours_practical) {
            return false;
        }
        if (this.hours_nightly != other.hours_nightly) {
            return false;
        }
        if (this.hours_presentation != other.hours_presentation) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.subjectType, other.subjectType)) {
            return false;
        }
        if (this.department != other.department) {
            return false;
        }
        return true;
    }
    
     
}
