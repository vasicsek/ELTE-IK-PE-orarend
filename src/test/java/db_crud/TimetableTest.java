/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_crud;

import com.elte.osz.logic.Utils;
import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.SemesterItem;
import com.elte.osz.logic.entities.Timetable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RMUGLK
 */
public class TimetableTest extends DBTest {
    
    
    private Timetable tt1;
    private Timetable tt2;
    private Timetable tt3;

    public Timetable getTt1() {
        return tt1;
    }

    public Timetable getTt2() {
        return tt2;
    }

    public Timetable getTt3() {
        return tt3;
    }
    private SemesterTable stt;
    public TimetableTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
     
    }
    
    @AfterClass
    public static void tearDownClass()  {
        
      
    }
    
    @Before
    public void setUp() {
        //kelenek szemeszter elemek
        logInfo("TimetableTest::Semeszter létrehozása!");
        stt = new SemesterTable();
        stt.createSemester();
        
    }
    
    @After
    public void tearDown() throws NonexistentEntityException {
          //takarítás, persze miután timetable-eket töröltük, különben SQLIntergrityContraintViolationException
        logInfo("TimetableTest::Semeszter törlése!");
        stt.deleteSemester();
        stt = null;
    }
    
    @Test
    public void testCRUD()throws NonexistentEntityException, Exception{         
       
        createTimetable();
        readTimetable();
        updateTimetable();
        readTimetable();
        deleteTimetable();
    }

    public void createTimetable(){

        tt1 = new Timetable();     
        logInfo("1. Üres órarend létrehozása");
        tt1.setName("2016 tavasz");
        Set<SemesterItem> classes= new TreeSet<SemesterItem>();
        tt1.setClasses(classes);
        tt1.setSemester(stt.getSem());
        ctrlTimetable.create(tt1);
        
        tt2 = new Timetable();     
        logInfo("2. Üres órarend létrehozása");
        tt2.setName("2016 ősz");              
        tt2.setSemester(stt.getSem());
        ctrlTimetable.create(tt2);        
        
        tt3 = new Timetable();     
        logInfo("3. Órarend létrehozása néhány semeszter elemmel(=tantárgy+időpont).");
        tt3.setName("2017 tavasz");        
        classes= new TreeSet<SemesterItem>();        
        attachRandomClasses(classes,stt.getSem());        
        tt3.setSemester(stt.getSem());
        tt3.setClasses(classes);
        logInfo("2017 tavaszhoz adunk szemeszter elemeket: "+classes.size()+" db-ot");
        ctrlTimetable.create(tt3);
    }
    public void readTimetable(){
        
        logInfo("Órarendek listázása...");
        List<Timetable> lsTt = ctrlTimetable.findTimetableEntities();
               
        for( int i = 0; i < lsTt.size(); ++i){
            logInfo(lsTt.get(i));
        }
    }
    public void updateTimetable() throws Exception{
        
        logInfo("1. Üres órarend szemeszter elem hozzárendelés");
        tt1.setName("2016 tavasz"+tt1.getClasses().size());        
        Set<SemesterItem> classes = new TreeSet<SemesterItem>();
        attachRandomClasses(classes,stt.getSem());
        logInfo("2016 tavasz kapott szemeszter elemeket: "+classes.size()+" db-ot");
        tt1.setClasses(classes);
        ctrlTimetable.edit(tt1);             
                
        
        logInfo("2. Üres órarendhez szemeszter elem hozzárendelés");
        tt2.setName("2016 ősz ÁTNEVEZVE");              
        classes = new TreeSet<SemesterItem>();
        attachRandomClasses(classes,stt.getSem());
        logInfo("2016 ősz kapott szemeszter elemeket: "+classes.size()+" db-ot");
        tt2.setClasses(classes);
        ctrlTimetable.edit(tt2);              
        /*
          Órák törlése, hozzáadása
        */
        logInfo("3. Órarend szemeszter eleminek eltávolítása(=tantárgy+időpont).");
        tt3.setName("2017 tavasz ÁTNEVEZVE");        
        classes = tt3.getClasses();
        Iterator<SemesterItem> it = classes.iterator();
        int rmcnt = 0;
        while(it.hasNext()){
            it.next();
            
            if ( Utils.getRandomInt(0, 10) % 2 == 0) {
                it.remove();
                rmcnt++;
            }
            
        }
        logInfo("REMOVED COUNT:"+rmcnt);
        
        attachRandomClasses(classes,stt.getSem());
        logInfo("2017 tavasz kapott szemeszter elemeket: "+classes.size()+" db-ot");
         ctrlTimetable.edit(tt3);
    }
    
    
    public void deleteTimetable() throws NonexistentEntityException{
        //takarítás, a teszt szemeszter is törlöm a tesztek után
        ctrlTimetable.destroy(tt1);
        ctrlTimetable.destroy(tt2);
        ctrlTimetable.destroy(tt3);
    }   
}
