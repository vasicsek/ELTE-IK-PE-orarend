/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_crud;

import com.elte.osz.logic.controllers.exceptions.NonexistentEntityException;
import com.elte.osz.logic.entities.SemesterItem;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Tóth Ákos
 */
public class SemesterItemSearches extends DBTest {
    
    private static SemesterTableTest stt;
    
    public SemesterItemSearches() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //kelenek szemeszter elemek
        System.out.println("SemesterItemSearches::Semeszter létrehozása!");
        stt = new SemesterTableTest();
        stt.createSemester();
        //stt.readSemester();
    }
    @AfterClass
    public static void tearDownClass() throws NonexistentEntityException {
             
        System.out.println("SemesterItemSearches::Semeszter törlése!");
        stt.deleteSemester();
        stt = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
   
   private void doSearchWithSemesterNr(String subjectName, String subjectCode, int semesterNr){
      System.out.println("Keresés tantárgy név, kód és ajánlott szemeszter  alapján: \"" + subjectName + "\", \"" + subjectCode + "\", " + semesterNr);
      List<SemesterItem> ls = ctrlSemesterItem.searchBySubjectWithSemesterNr(stt.getSem().getId(),subjectName, subjectCode, semesterNr);
      System.out.println("Találatok száma:"+ ls.size());
      ls.forEach((element)->{       
           System.out.println(element.getSubject() );                 
      });
      System.out.println("-------------------------------------------------");
   }
   private void doSearch(String subjectName, String subjectCode){
      System.out.println("Keresés tantárgy név és kód alapján: \"" + subjectName + "\", \"" + subjectCode + "\"");
      List<SemesterItem> ls = ctrlSemesterItem.searchBySubject(stt.getSem().getId(),subjectName, subjectCode);
      System.out.println("Találatok száma "+ ls.size());
      println(ls);
      
      System.out.println("-------------------------------------------------");
   }
   private void doSearchFull(String subjectName, String subjectCode, String subjectType, int semesterNr ){
      System.out.println("\"Teljes\" keresés...: \"" + subjectName + "\", \"" + subjectCode + "\", " + "\""+ subjectType +"\", " + semesterNr);
      List<SemesterItem> ls = ctrlSemesterItem.searchBySubjectFull(stt.getSem().getId(),subjectName, subjectCode, subjectType, semesterNr);
      System.out.println("Találatok száma "+ ls.size());
      println(ls);
      
      System.out.println("-------------------------------------------------");       
   }
   
   @Test
   public void searchSemesters(){
       System.out.println("Első féléves tantárgyak kilistázása...");
       doSearchWithSemesterNr("", "",1);
       System.out.println("Keresés névre...:");
       doSearch("diszkrét","");
       System.out.println("Keresés kódra..:");
       doSearch("", "IKP");
       System.out.println("Keresés névre és kódra..:");
       doSearch("rendszer", "EA");  
       System.out.println("Keresés:");
       doSearchFull("a", "IK", "",1);
   }
}
