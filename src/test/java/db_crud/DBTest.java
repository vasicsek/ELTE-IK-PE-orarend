/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_crud;

import com.elte.osz.logic.OszDS;
import com.elte.osz.logic.Utils;
import com.elte.osz.logic.controllers.RoomJpaController;
import com.elte.osz.logic.controllers.SemesterItemJpaController;
import com.elte.osz.logic.controllers.SemesterJpaController;
import com.elte.osz.logic.controllers.SubjectJpaController;
import com.elte.osz.logic.controllers.TeacherJpaController;
import com.elte.osz.logic.controllers.TimetableJpaController;
import com.elte.osz.logic.entities.Semester;
import com.elte.osz.logic.entities.SemesterItem;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Tóth Ákos
 */
public class DBTest {

    public DBTest() {
        ds = new OszDS();
        this.ctrlTeacher = ds.getCtrlTeacher();
        this.ctrlRoom = ds.getCtrlRoom();
        this.ctrlSubject = ds.getCtrlSubject();
        this.ctrlSemester = ds.getCtrlSemester();
        this.ctrlTimetable = ds.getCtrlTimetable();
        this.ctrlSemesterItem = ds.getCtrlSemesterItem();

    }
    protected final OszDS ds;
    protected final SemesterJpaController ctrlSemester;
    protected final SubjectJpaController ctrlSubject;
    protected final RoomJpaController ctrlRoom;
    protected final TeacherJpaController ctrlTeacher;
    protected final TimetableJpaController ctrlTimetable;
    protected final SemesterItemJpaController ctrlSemesterItem;
    
    protected void println(Set<SemesterItem> sis ){
       sis.forEach( (si)->{
           System.out.println(si);
       });
   }
    protected void println(List<SemesterItem> sis ){
       sis.forEach( (si)->{
           System.out.println(si);
       });
   }
    protected void attachRandomClasses(Set<SemesterItem> classes, Semester semester){
        final int cnt = Utils.getRandomInt(1, 5);
        List<SemesterItem> ssi = semester.getItemsAsList();
        
        for ( int i = 0; i < cnt;++i ){            
            classes.add( ssi.get( Utils.getRandomInt(0, ssi.size()-1) ) );                       
            
        }
    }
}
