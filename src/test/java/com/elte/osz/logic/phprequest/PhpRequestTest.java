/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.phprequest;

import com.elte.osz.logic.entities.SemesterItem;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.jpa.jpql.parser.SubExpression;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dobos Árpád
 */
public class PhpRequestTest {
    
    public PhpRequestTest() {
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

    /**
     * Test of getSubject method, of class PhpRequest.
     */
    @Test
    public void testGetSubject() {
        System.out.println("getSubject");
        String code = "IP-08cPNY2EG";
        ArrayList<SemesterItem> sem = new ArrayList<SemesterItem>();
        PhpRequest instance = new PhpRequest();
        instance.downloadSubjectData(code);
        sem = instance.getSubjectInfo();
        assertTrue("Hibas tombmeret", sem.size() == 13);
        assertTrue("Hibas tanarnev", sem.get(1).getTeacher().getName().equals("Lázár Katalin Anna"));
        // System.out.println(sem.get(1).getSubject().getSubjectType());
        // System.out.println(sem.get(1).getRoom().getName());
        assertTrue("Hibas tipus", sem.get(12).getSubject().getSubjectType().equals("konzultáció"));
        //System.out.println(sem.size());
       // System.out.println(sem.get(1).getTeacher().getName());
        
       
        /*String code = "IKP-9169";
        String code2 = "IP-08EAN1E";
        PhpRequest instance = new PhpRequest();
        instance.downloadSubjectData(code);
        ArrayList<String> subjectData = new ArrayList<String>();
        subjectData = instance.getSubjectInfo();
        assertTrue("Hibas tomb meret", subjectData.size() == 5);
        assertTrue("Hibas elemet tett hozzas", subjectData.get(3).equals("gyakorlat"));
        assertTrue("Hibas a tanarnev", subjectData.get(4).equals("Daiki Tennó"));
        instance.downloadSubjectData(code2);
        assertTrue("Hibas tomb merete", subjectData.size() == 5);
       // System.out.println(subjectData.get(3));
        assertTrue("Hibas a targy tipusa", "előadás".equals(subjectData.get(3)));
        assertTrue("Hibas a tanarnev", subjectData.get(4).equals("Kovács Sándor"));*/
        
    }    
}
