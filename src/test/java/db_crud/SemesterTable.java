package db_crud;

import com.elte.osz.logic.Department;
import com.elte.osz.logic.Utils;
import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.Room;
import com.elte.osz.logic.entities.Semester;
import com.elte.osz.logic.entities.SemesterItem;
import com.elte.osz.logic.entities.Subject;
import com.elte.osz.logic.entities.Teacher;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SemesterTable extends DBTest{
    
    private final String semester_name ="2015/16 tavasz";
    private Semester sem;
    private Set<SemesterItem> lsSi;
    public Semester getSem() {
        return sem;
    }
    public SemesterTable() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCRUD() throws NonexistentEntityException, Exception {
        
        this.sem = null;
        
        logInfo("CREATING semester:"+semester_name);
        createSemester();
        logInfo("READING semester:"+semester_name);        
        readSemester();
        logInfo("UPDATING semester:"+semester_name);
        updateSemester();
        readSemester();    
        logInfo("DELETING semester:"+semester_name);
        deleteSemester();
        
    }

    
    public void createSemester()  {

        // A tantárgyak időpontjait fel akarjuk vinni adatbázisba
        // Ezért látrehozunk egy semeszter objektumot.      
        Semester semester = new Semester();
        semester.setName(semester_name);
        CriteriaBuilder cb =ctrlSubject.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Subject> q = cb.createQuery(Subject.class);        
        ParameterExpression<String> p = cb.parameter(String.class,"d");
        
        
        Root<Subject> c = q.from(Subject.class);
        q.select(c);
        q.where(cb.equal(c.get("department"), p));          
        
        //  A tantárgyak, amiknek az időpontjait tudjuk. (tegyük fel most az összes IK-s)       
        TypedQuery<Subject> tq = ctrlSubject.getEntityManager().createQuery(q);
        
        tq.setParameter("d", Department.IK);
        
        List<Subject> lsSubjects = tq.getResultList();

        //ebben tároljuk a semester tantárgy+időpontokat
        lsSi = new TreeSet<SemesterItem>();

        Iterator<Subject> itSubject = lsSubjects.iterator();

  
        while (itSubject.hasNext()) {

            Subject s = itSubject.next();
            Timestamp ts = Utils.getRandomTimeStamp();
            //Random terem és tanár keresése 
            Long id =  (Utils.getRandomLong(1, ctrlRoom.getRoomCount()) );
            
            Room room = ctrlRoom.getEntityManager().find(Room.class, id );
            id =  (Utils.getRandomLong(1, ctrlTeacher.getTeacherCount()));
            
            Teacher teacher = ctrlTeacher.getEntityManager().find(Teacher.class, id );

            //SemesterItem és subject kapcsolatában be van állítva a cascade=PERSIST ezért
            //egy subject-et lehet így is frissíteni a tulajdonságait
            //ajánlott szemeszter
            int iSem = Utils.getRandomInt(1, 6);
            //ajánlott szemeszter
            s.setSemester(iSem);

            SemesterItem si = new SemesterItem();
            si.setStartTime(ts.toString());
            si.setEndTime(new Timestamp(ts.getTime() + 2 * 3600000) .toString());
            si.setDay("Hétfő");
            si.setSubject(s);
            si.setTeacher(teacher);
            si.setRoom(room);
            lsSi.add(si);
        }

        //szemeszter időpontok összeasszociálása
        //mivel cascading merge,persit be van állítva ezért létrejönnek a megfelelő táblákban
        semester.setItems(lsSi);
        ctrlSemester.create(semester);
        
        this.sem = semester;
    }

    public void readSemester() {
        List<Semester> lsSemester = ctrlSemester.findSemesterEntities();  
        
        int index = lsSemester.indexOf(this.sem);        
        
        Assert.assertFalse("Szemeszter helyesen megjelent az adatbázisban!", index == -1 );        
        Assert.assertTrue("Szemeszter helyesen megjelent az adatbázisban!", sem.equals(lsSemester.get(index)));
        println(lsSi);
        logInfo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        for( SemesterItem si : lsSemester.get(index).getItems() )       {     
            logInfo(si);
           Assert.assertTrue("Szemeszter elemek helyesen megjelentek az adatbázisban!", lsSi.contains(si));
        }
            
    }
    
    public void updateSemester() throws Exception{
        //Mivel a cascadetype ALL ra van állítva a edit frissül minden a kapcsolódó semesteritem-ek és a subject is akár
        sem.setName(semester_name+" ÁTNEVEZVE ");
        
        List<SemesterItem> ls = sem.getItemsAsList();
        for ( int i = 0; i < ls.size(); ++i){
            ls.get(i).setEndTime(new Timestamp(Timestamp.valueOf("2015-01-01 00:00:00").getTime()).toString());
            ls.get(i).setDay("Hétfő");
            //Mivel a semesteritem-ben a cascadetype -ban a merge is szerepel, ezért meg fog változni a subject tábla is.
            ls.get(i).getSubject().setSubjectType("TESZT"+ls.size());
        }
        int rmcnt =Utils.getRandomInt(0, ls.size()-1);
        logInfo("REMOVING ITEM COUNT="+rmcnt);
        for( int i = 0; i < rmcnt ;++i)
            ls.remove(0);
        
        ctrlSemester.edit(sem);
        
   }
    
    public void deleteSemester() throws NonexistentEntityException{
        //Mivel a cascadetype ALL-ra van állítva a destroyal törlődnek a kapcsolódó semesteritem-ek is
        //sem.getId();
       ctrlSemester.destroy(sem);
    }

}
