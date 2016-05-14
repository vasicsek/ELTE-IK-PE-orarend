/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.phprequest;

import java.util.ArrayList;
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
        String code = "IKP-9169";
        String code2 = "IP-08EAN1E";
        PhpRequest instance = new PhpRequest();
        instance.downloadSubjectData(code);
        // TODO review the generated test code and remove the default call to fail.
        ArrayList<String> subjectDate = new ArrayList<String>();
        subjectDate = instance.getSubjectInfo();
        assertTrue("Hibas tomb meret", subjectDate.size() == 5);
        assertTrue("Hibas elemet tett hozzas", subjectDate.get(3).equals("gyakorlat"));
        assertTrue("Hibas a tanarnev", subjectDate.get(4).equals("Daiki Tennó"));
        instance.downloadSubjectData(code2);
        assertTrue("Hibas tomb merete", subjectDate.size() == 5);
        assertTrue("Hibas a targy tipusa", subjectDate.get(3).equals("előadás"));
        assertTrue("Hibas a tanarnev", subjectDate.get(4).equals("Kovács Sándor"));
        
    }    
}
