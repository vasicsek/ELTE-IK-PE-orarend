/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.phprequest;

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
public class DataBaseOperationsTest {
    
    DataBaseOperations instance;
    
    public DataBaseOperationsTest() {
        instance = new DataBaseOperations();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //instance.deleteTestTable();
    }
    
    @After
    public void tearDown() {
       // instance.deleteTestTable();
    }

    /**
     * Test of searchSubjectSchedule method, of class DataBaseOperations.
     */
    @Test
    public void testSearchSubjectSchedule() {
        //System.out.println("searchSubjectScheduleTest");
        //DataBaseOperations instance = new DataBaseOperations();
        instance.searchSubjectSchedule();
        // TODO review the generated test code and remove the default call to fail.
    }
    
   
    @Test
    public void uploadSubjectTimeTest(){
        System.out.println("uploadSubjectTimeTest");
       // DataBaseOperations instance = new DataBaseOperations();
       instance.uploadSubjectTime("IP-08EAN1E");
       // System.out.println("StartTime: " + instance.startTime);
        //System.out.println(instance.day);
        assertTrue("Hibas subjectID", Integer.parseInt(instance.subjectID) == 209);
        assertTrue("Hibas teacherID", Integer.parseInt(instance.teacherID) == 134);
        assertTrue("Hibas roomID", Integer.parseInt(instance.roomID) == 68);
        assertTrue("Hibas startTime", instance.startTime.equals("17:45"));
        assertTrue("Hibas endTime", instance.endTime.equals("19:15"));
        assertTrue("Hibas day", instance.day.equals("Hétfo"));
        instance.uploadSubjectTime("IP-08EPNY1EG");
        assertTrue("Hibas subjectID", Integer.parseInt(instance.subjectID) == 585);
        assertTrue("Hibas teacherID", Integer.parseInt(instance.teacherID) == 320);
        assertTrue("Hibas startTime", instance.startTime.equals("19:30"));
        assertTrue("Hibas endTime", instance.endTime.equals("21:00"));
        assertTrue("Hibas day", instance.day.equals("Kedd"));
    }
    
    @Test
    public void updateSubjectDataTest(){
        System.out.println("updateSubjectDataTest");
        instance.updateSubjectData("1", "eloadás");
    }
    
   
}
