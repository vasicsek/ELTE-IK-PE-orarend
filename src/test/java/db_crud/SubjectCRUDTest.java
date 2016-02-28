/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_crud;

import com.elte.tosz.controllers.SubjectJpaController;
import com.elte.tosz.controllers.exceptions.NonexistentEntityException;
import com.elte.tosz.logic.OszDS;
import com.elte.tosz.logic.entities.Room;
import com.elte.tosz.logic.entities.Subject;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cauchy
 */
public class SubjectCRUDTest {
    
    private final OszDS ds;
    private final SubjectJpaController ctrlSubject ;
    private Subject subject;
    
    public SubjectCRUDTest() {
        ds = new OszDS();     
        ctrlSubject = ds.getCtrlSubject();        
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
    public void crudTest() throws NonexistentEntityException, Exception{        
       
       subjectCreation();
       subjectRead();
       subjectUpdate();
       subjectDelete();
    }   
    
    public void subjectCreation() {
        
        Subject s = new Subject();
        s.setCode("IP-08cSZHE");
        s.setEstMemberCount(150);
        s.setEstMemberRatio((float) 0.5);
        s.setMaxTimeCount(1);       
        s.setName("Számítógépes hálózatok GY. (BSc,08,A)");
        
        ctrlSubject.create(s);
        
        List<Subject> subjects = ctrlSubject.findSubjectEntities();
        int index = subjects.indexOf(s);
        System.out.println("Found index:"+index);
        assert( index > -1 );
        this.subject = s;         
        
    }
    
    public void subjectRead(){
        List<Subject> subjects = ctrlSubject.findSubjectEntities();
        subjects.forEach((subject) -> {
            System.out.println(subject);
        } );
        
        assert(!subjects.isEmpty());        
    }
    
    public void subjectUpdate() throws Exception{
        subject.setEstMemberRatio((float) 0.9);
        ctrlSubject.edit(subject);
        List<Subject> subjects = ctrlSubject.findSubjectEntities();
        System.out.println("Subjects array length:" + subjects.size());
        int index = subjects.indexOf(subject);
        System.out.println("Found index:"+index);
        assert( index > -1 );
    }
    public void subjectDelete() throws NonexistentEntityException{
        System.out.println(subject.toString());
        assert(subject != null);
        ctrlSubject.destroy(subject.getId());        
        subject = null;
    }
}
