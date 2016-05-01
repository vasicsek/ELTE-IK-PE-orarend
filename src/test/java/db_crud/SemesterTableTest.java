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
import java.util.ArrayList;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SemesterTableTest extends DBTest{
    
    private final String semester_name ="2015/16 tavasz";
    private Semester sem;

    public Semester getSem() {
        return sem;
    }
    public SemesterTableTest() {
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
        System.out.println("CREATING semester:"+semester_name);
        createSemester();
        System.out.println("READING semester:"+semester_name);        
        readSemester();
        System.out.println("UPDATING semester:"+semester_name);
        updateSemester();
        readSemester();    
        System.out.println("DELETING semester:"+semester_name);
        deleteSemester();
        
    }

    
    public void createSemester() {

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
        Set<SemesterItem> lsSi = new TreeSet<SemesterItem>();

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
            int iSem = Utils.getRandomInt(1, 6);
            
            s.setSemester(iSem);

            SemesterItem si = new SemesterItem();
            si.setStartTime(ts);

            si.setEndTime(new Timestamp(ts.getTime() + 2 * 3600000));
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
        
        for(Semester semester : lsSemester){
            
            System.out.println(semester);            
            for( SemesterItem si : semester.getItems()){
                System.out.println(si);
            }
        }
    }
    
    public void updateSemester() throws Exception{
        //Mivel a cascadetype ALL ra van állítva a edit frissül minden a kapcsolódó semesteritem-ek és a subject is akár
        sem.setName(semester_name+" ÁTNEVEZVE ");
        
        List<SemesterItem> ls = sem.getItemsAsList();
        for ( int i = 0; i < ls.size(); ++i){
            ls.get(i).setEndTime(new Timestamp(Timestamp.valueOf("2015-01-01 00:00:00").getTime()));
            //Mivel a semesteritem-ben a cascadetype -ban a merge is szerepel, ezért meg fog változni a subject tábla is.
            ls.get(i).getSubject().setSubjectType("TESZT"+ls.size());
        }
        int rmcnt =Utils.getRandomInt(0, ls.size()-1);
        System.out.println("REMOVING ITEM COUNT="+rmcnt);
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
