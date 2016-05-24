package com.elte.osz.logic.entities;

import com.elte.osz.logic.dbhandler.BaseEntity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

/**
 * Órarend reprezentálása
 *
 * @author RMUGLK
 */
@Entity
public class Timetable extends BaseEntity implements Serializable {
    /**
    * Időbélyeg az órarend elkészülésének időpontjáról.
    */
    @Column(updatable = false, insertable = true, nullable = false)
    @Basic
    private Timestamp created;
    /**
     * Szemester elemek: azaz tantárgy, időpont, terem, tanár összerendelése.
     * {@link SemesterItem}
     */
    @OneToMany(targetEntity = SemesterItem.class)
    private Set<SemesterItem> classes = new TreeSet<>();
    /**
    * Az órarendhez tartozó szemeszter. Pl "2015-16-2"-es szemeszter stb
    */
    @OneToOne(targetEntity = Semester.class)
    private Semester semester;
    /**
     * {@link Timetable#semester}
     */    
    public Semester getSemester() {
        return semester;
    }
    /**
     * {@link Timetable#semester}
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    
    /**
     * Órarend neve.
     */    
    @Column(nullable = false)
    @Basic
    private String name;

    public Timetable() {

    }

    public Timetable(String name) {
        this.name = name;
    }
  
    public Timetable(String name, List<SemesterItem> lsSi) {
        this.name = name;
        setClassesAsList(lsSi);
    }
    /**
     * {@link Timetable#created}
     */
    public Timestamp getCreated() {
        return this.created;
    }

    public ArrayList<SemesterItem> getClassesAsList() {
        ArrayList result = new ArrayList<>();
        result.addAll(this.classes);
        return result;
    }
  /**
     * {@link Timetable#classes}
     */
    public Set<SemesterItem> getClasses() {
        return this.classes;
    }
  /**
     * {@link Timetable#classes}
     */
    public void setClasses(Set<SemesterItem> classes) {
        this.classes = classes;
    }
  /**
     * {@link Timetable#classes}
     */
    public void setClassesAsList(List<SemesterItem> classes) {
        this.classes = new TreeSet(classes);
    } 
      /**
     * {@link Timetable#name}
     */
    public String getName() {
        return this.name;
    }
  /**
     * {@link Timetable#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.name).append("[");
        SemesterItem si = null;
        Iterator<SemesterItem> it = classes.iterator();

        while (it.hasNext()) {
            si = it.next();
            sb.append(si);
            if (it.hasNext()) {
                sb.append(",\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        System.out.println("!!! TIMETABLE HASHCODE !!!");
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("!!! TIMETABLE EQUALS !!!");
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Timetable other = (Timetable) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        return true;
    }

    @PrePersist
    void preInsert() {
        created = new Timestamp(new Date().getTime());
    }

}
