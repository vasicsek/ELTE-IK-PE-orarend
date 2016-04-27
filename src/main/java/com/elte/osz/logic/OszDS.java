
package com.elte.osz.logic;


import com.elte.osz.logic.controllers.RoomJpaController;
import com.elte.osz.logic.controllers.SubjectJpaController;
import com.elte.osz.logic.controllers.TeacherJpaController;
import com.elte.osz.logic.controllers.TimetableHeadJpaController;
import com.elte.osz.logic.controllers.TimetableItemJpaController;
import com.elte.osz.logic.dbhandler.DataSource;
import javax.persistence.Persistence;


public class OszDS extends DataSource{
    
    private RoomJpaController ctrlRoom;
    private SubjectJpaController ctrlSubject;
    private TeacherJpaController ctrlTeacher;
    private TimetableHeadJpaController ctrlTimetableHead;
    private TimetableItemJpaController ctrlTimetableItem;

    public RoomJpaController getCtrlRoom() {
        return ctrlRoom;
    }

    public SubjectJpaController getCtrlSubject() {
        return ctrlSubject;
    }

    public TeacherJpaController getCtrlTeacher() {
        return ctrlTeacher;
    }

    public TimetableHeadJpaController getCtrlTimetableHead() {
        return ctrlTimetableHead;
    }

    public TimetableItemJpaController getCtrlTimetableItem() {
        return ctrlTimetableItem;
    }
    
    
    public OszDS() {       
        super("puOsz");       
        
        
        
        ctrlRoom = new RoomJpaController(emf);
        ctrlSubject  = new SubjectJpaController(emf);
        ctrlTeacher = new TeacherJpaController(emf);
        ctrlTimetableHead = new TimetableHeadJpaController(emf);
        ctrlTimetableItem = new TimetableItemJpaController(emf);
    }
    
}
