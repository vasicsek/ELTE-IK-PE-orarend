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
 * Szemeszter elemeket kereső függvények tesztelése(SQL lekérdezések)
 * @author RMUGLK
 */
public class SemesterItemSearches extends DBTest {
    
    private static SemesterTable stt;
    
    public SemesterItemSearches() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //kelenek szemeszter elemek
        logInfo("SemesterItemSearches::Semeszter létrehozása!");
        stt = new SemesterTable();
        stt.createSemester();
        //stt.readSemester();
    }
    @AfterClass
    public static void tearDownClass() throws NonexistentEntityException {
             
        logInfo("SemesterItemSearches::Semeszter törlése!");
        stt.deleteSemester();
        stt = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
   /**
    * Keresés névre, kódra, ajánlott félévvel.
    * @param subjectName
    * @param subjectCode
    * @param semesterNr  ajánlot félév
    */
   private void doSearchWithSemesterNr(String subjectName, String subjectCode, int semesterNr){
      logInfo("Keresés tantárgy név, kód és ajánlott szemeszter  alapján: \"" + subjectName + "\", \"" + subjectCode + "\", " + semesterNr);
      List<SemesterItem> ls = ctrlSemesterItem.searchBySubjectWithSemesterNr(stt.getSem().getId(),subjectName, subjectCode, semesterNr);
      logInfo("Találatok száma:"+ ls.size());
      ls.forEach((element)->{       
           logInfo(element.getSubject() );                 
      });
      logInfo("-------------------------------------------------");
   }
   private void doSearch(String subjectName, String subjectCode){
      logInfo("Keresés tantárgy név és kód alapján: \"" + subjectName + "\", \"" + subjectCode + "\"");
      List<SemesterItem> ls = ctrlSemesterItem.searchBySubject(stt.getSem().getId(),subjectName, subjectCode);
      logInfo("Találatok száma "+ ls.size());
      println(ls);
      
      logInfo("-------------------------------------------------");
   }
   private void doSearchFull(String subjectName, String subjectCode, String subjectType, int semesterNr ){
      logInfo("\"Teljes\" keresés...: \"" + subjectName + "\", \"" + subjectCode + "\", " + "\""+ subjectType +"\", " + semesterNr);
      List<SemesterItem> ls = ctrlSemesterItem.searchBySubjectFull(stt.getSem().getId(),subjectName, subjectCode, subjectType, semesterNr);
      logInfo("Találatok száma "+ ls.size());
      println(ls);
      
      logInfo("-------------------------------------------------");       
   }
   
   @Test
   public void searchSemesters(){
       logInfo("Első féléves tantárgyak kilistázása...");
       doSearchWithSemesterNr("", "",1);
       logInfo("Keresés névre...:");
       doSearch("diszkrét","");
       logInfo("Keresés kódra..:");
       doSearch("", "IKP");
       logInfo("Keresés névre és kódra..:");
       doSearch("rendszer", "EA");  
       logInfo("Keresés:");
       doSearchFull("a", "IK", "",1);
   }
}
