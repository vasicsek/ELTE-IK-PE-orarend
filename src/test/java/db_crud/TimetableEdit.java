/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_crud;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.SemesterItem;
import com.elte.osz.logic.entities.Timetable;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RMUGLK
 */
public class TimetableEdit extends DBTest {

    private Timetable tt;
    private SemesterTableTest stt;
    public TimetableEdit() {

    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
         logInfo("TimetableEdit::Semeszter létrehozása!");
        stt = new SemesterTableTest();
        stt.createSemester();
    }

    @After
    public void tearDown() throws NonexistentEntityException {

         //takarítás, persze miután timetable-eket töröltük, különben SQLIntergrityContraintViolationException
        logInfo("TimetableEdit::Semeszter törlése!");
        stt.deleteSemester();
        stt = null;
    }

    @Test
    public void editTimetable() throws Exception {
        
        tt = new Timetable();     
        logInfo("Órarend létrehozása...");
        tt.setName("2016 tavasz ÓRARENDEM");        
        logInfo("Órák felvétele...");
        attachRandomClasses(tt.getClasses(),stt.getSem());                        
        tt.setSemester(stt.getSem());                
        ctrlTimetable.create(tt);
        logInfo(tt.getName()+" órái:");
        println(tt.getClasses());
        logInfo("Meglévő órarendet szeretnék változtatni.");
        Set<SemesterItem> ssi = tt.getClasses();
        
        logInfo("Kiválasztom az órát és hozzáadom.");
        List<SemesterItem> ls = ctrlSemesterItem.searchBySubject(tt.getSemester().getId(), "diszkrét matematika 2", "EA");        
        //Set adatstruktúra miatt nem kell nézni hogy ugyanaz már benne van e 
        ssi.addAll(ls);
        logInfo("Perzisztálás...");
        ctrlTimetable.edit(tt);
        
        logInfo(tt.getName()+" órái:");
        println(tt.getClasses());
        logInfo("Kész!");
        logInfo("Takarítás...");
        ctrlTimetable.destroy(tt);
    }

}
