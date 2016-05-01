
package com.elte.osz.logic;



import com.elte.osz.logic.controllers.RoomJpaController;
import com.elte.osz.logic.controllers.SemesterItemJpaController;
import com.elte.osz.logic.controllers.SemesterJpaController;
import com.elte.osz.logic.controllers.SubjectJpaController;
import com.elte.osz.logic.controllers.TeacherJpaController;
import com.elte.osz.logic.controllers.TimetableJpaController;
import com.elte.osz.logic.dbhandler.DataSource;
import java.util.Map;


public class OszDS extends DataSource{
    
    public static final String PU = "puOsz";
    
    private final RoomJpaController ctrlRoom;
    private final SubjectJpaController ctrlSubject;
    private final TeacherJpaController ctrlTeacher;
    private final TimetableJpaController ctrlTimetable;
    private final SemesterJpaController ctrlSemester;
    private final SemesterItemJpaController ctrlSemesterItem;

    public SemesterItemJpaController getCtrlSemesterItem() {
        return ctrlSemesterItem;
    }

    public RoomJpaController getCtrlRoom() {
        return ctrlRoom;
    }

    public SubjectJpaController getCtrlSubject() {
        return ctrlSubject;
    }

    public TeacherJpaController getCtrlTeacher() {
        return ctrlTeacher;
    }

    public TimetableJpaController getCtrlTimetable() {
        return ctrlTimetable;
    }

    public SemesterJpaController getCtrlSemester() {
        return ctrlSemester;
    }
    public OszDS() {       
        super(PU);       
        
        ctrlRoom = new RoomJpaController(emf);
        ctrlSubject  = new SubjectJpaController(emf);
        ctrlTeacher = new TeacherJpaController(emf);
        ctrlTimetable= new TimetableJpaController(emf);
        ctrlSemester = new SemesterJpaController(emf);
        ctrlSemesterItem = new SemesterItemJpaController(emf);
    }
    
}
