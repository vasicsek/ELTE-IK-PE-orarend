package com.elte.osz.logic.entities;

import com.elte.osz.logic.dbhandler.BaseEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.FieldResult;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;

/*** 
 * SemesterItem osztály a szemesterenkénti órákat reprezentálja, azaz 
 * tantárggyal, időponttal, teremmel, tanárral elátott óra.
 * @author RMUGLK
 */
@Entity
@SqlResultSetMapping(
        name = "SearchBySubjectMapping",
        entities = {
            @EntityResult(
                    entityClass = SemesterItem.class,
                    fields = {
                        @FieldResult(name = "id", column = "sid"),
                        @FieldResult(name = "startTime", column = "starttime"),
                        @FieldResult(name = "endTime", column = "sendtime"),
                        @FieldResult(name = "day", column = "day")

                    }
            )
        }
)
@NamedNativeQueries({
    @NamedNativeQuery(name = "searchBySubject", query
            = "SELECT t1.id as sid, endtime, starttime, day, "
            + "t2.id as subid, t2.name as subname, code, department, "
            + "hours_nightly, hours_practical, hours_presentation, semester, subjecttype, "
            + "t3.id as rid, building, floor, t3.name as rname, "
            + "t4.id as tid, t4.name as tname "
            + "FROM semesteritem t1, subject t2, room t3, teacher t4, semester_semesteritem t5 "
            + "WHERE t2.id = t1.subject_id "
            + "AND t3.id = t1.room_id "
            + "AND t4.id = t1.teacher_id "
            + "AND t5.items_id = t1.id "
            + "AND t5.semester_id = ? "
            + "AND LOWER(t2.\"NAME\") like LOWER('%' || ? || '%' ) "
            + "AND LOWER(t2.\"CODE\") like LOWER( '%'|| ? || '%' ) ",
            resultSetMapping = "SearchBySubjectMapping"
    ),
    @NamedNativeQuery(name = "searchBySubjectWithSemester", query
            = "SELECT t1.id as sid, endtime, starttime, day, "
            + "t2.id as subid, t2.name as subname, code, department, "
            + "hours_nightly, hours_practical, hours_presentation, semester, subjecttype, "
            + "t3.id as rid, building, floor, t3.name as rname, "
            + "t4.id as tid, t4.name as tname "
            + "FROM semesteritem t1, subject t2, room t3, teacher t4, semester_semesteritem t5 "
            + "WHERE t2.id = t1.subject_id "
            + "AND t3.id = t1.room_id "
            + "AND t4.id = t1.teacher_id "
            + "AND t5.items_id = t1.id "
            + "AND t5.semester_id = ? "
            + "AND LOWER(t2.\"NAME\") like LOWER('%' || ? || '%') "
            + "AND LOWER(t2.\"CODE\") like LOWER('%' || ? || '%') "
            + "AND t2.\"SEMESTER\"=? ",
            resultSetMapping = "SearchBySubjectMapping"
    ),
    @NamedNativeQuery(name = "searchBySubjectFull", query
            = "SELECT t1.id as sid, endtime, starttime, day, "
            + "t2.id as subid, t2.name as subname, code, department, "
            + "hours_nightly, hours_practical, hours_presentation, semester, subjecttype, "
            + "t3.id as rid, building, floor, t3.name as rname, "
            + "t4.id as tid, t4.name as tname "
            + "FROM semesteritem t1, subject t2, room t3, teacher t4, semester_semesteritem t5 "
            + "WHERE t2.id = t1.subject_id "
            + "AND t3.id = t1.room_id "
            + "AND t4.id = t1.teacher_id "
            + "AND t5.items_id = t1.id "
            + "AND t5.semester_id = ? "
            + "AND LOWER(t2.\"NAME\") like LOWER('%' || ? || '%') "
            + "AND LOWER(t2.\"CODE\") like LOWER('%' || ? || '%') "
            + "AND LOWER(t2.\"SUBJECTTYPE\") like LOWER('%' || ? || '%') "
            + "AND t2.\"SEMESTER\"=? ",
            resultSetMapping = "SearchBySubjectMapping"
    )

})
public class SemesterItem extends BaseEntity implements Serializable, Comparable {

   // @ManyToOne    
   // private Semester semester;

    /*public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    */
    @OneToOne(optional = true, targetEntity = Teacher.class)
    private Teacher teacher;

    @OneToOne(/*cascade = {CascadeType.MERGE, CascadeType.REFRESH},*/fetch=FetchType.EAGER, optional = false, targetEntity = Subject.class)
    private Subject subject;

    @Column(nullable = false)
    @Basic
    private String startTime;

    @Column(nullable = false)
    @Basic
    private String endTime;

    @Column(nullable = false)
    @Basic
    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @OneToOne(optional = true, targetEntity = Room.class)
    private Room room;

    public SemesterItem() {

    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "["+id+"](" + this.teacher + "@" + this.subject + "*" + getSubject().getSemester() + ", " + getSubject().getSubjectType() + "*" + " | Kezdés:" + this.startTime + " | Vége: " + this.endTime + ")";
    }

    @Override
    public int hashCode() {
        //return Objects.hash(id,startTime,endTime,teacher,room);
        //return Objects.hash(id);
        //System.out.println("SemesterItem::hashCode()");
        return Objects.hash(id, subject);
    }

    @Override
    public boolean equals(Object obj) {

        //System.out.println("SemesterItem::equals()");
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SemesterItem other = (SemesterItem) obj;
/*
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }*/
        if (!Objects.equals(this.teacher, other.teacher)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        if (!Objects.equals(this.room, other.room)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return hashCode() - o.hashCode();
    }

}
