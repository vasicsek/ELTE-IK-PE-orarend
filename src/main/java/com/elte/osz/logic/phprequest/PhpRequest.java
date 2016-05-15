/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.phprequest;

import com.elte.osz.logic.entities.Room;
import com.elte.osz.logic.entities.SemesterItem;
import com.elte.osz.logic.entities.Subject;
import com.elte.osz.logic.entities.Teacher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Dobos Árpád
 */
public class PhpRequest {
    private ArrayList<SemesterItem> SubjectInfo;

    public PhpRequest() {
        SubjectInfo = new ArrayList<SemesterItem>();
    }
    
    /**
     *
     * @param code
     */
    public void downloadSubjectData(String code){
        SubjectInfo.clear();
        try {
            Document dok = Jsoup.connect("http://to.ttk.elte.hu/test.php").data("melyik","kodalapjan").data("felev","2015-2016-2")
                    .data("limit","1000").data("targykod",code).post();
            Element element = dok.select("table").first();
            Iterator<Element> it = element.select("tr").iterator();
            it.next();
            int j = 0;
            while(it.hasNext()){
                Iterator<Element> it2 = it.next().select("td").iterator();
                if(it2.next().text().equals("Nincs ilyen adat.")){}
                else{
                    SubjectInfo.add(new SemesterItem());
                    it2.next();
                    String time = it2.next().text();
                    String lab = it2.next().text();
                    it2.next();
                    it2.next();
                    String type = it2.next().text();
                    //System.out.println(type);
                    if(!type.equals("gyakorlat") && !type.equals("konzultáció")) type = "előadás";
                    for(int i = 0; i < 4; ++i)it2.next();
                    String teacher = it2.next().text();
                    //add time
                    String[] times = time.split(" |-");
                    if(times.length < 2){
                        SubjectInfo.get(j).setDay("nincs megadva");
                        SubjectInfo.get(j).setStartTime("");
                        SubjectInfo.get(j).setEndTime("");
                    } else{
                        SubjectInfo.get(j).setDay(times[0]);
                        SubjectInfo.get(j).setStartTime(times[1]);
                        SubjectInfo.get(j).setEndTime(times[2]);
                    }
                    //add teacher
                    Teacher t = new Teacher();
                    t.setName(teacher);
                    SubjectInfo.get(j).setTeacher(t);
                    //add room
                    Room r = new Room();
                    r.setName(lab);
                    SubjectInfo.get(j).setRoom(r);
                    //add subject
                    Subject s = new Subject();
                    s.setCode(code);
                    s.setSubjectType(type);
                    SubjectInfo.get(j).setSubject(s);
                   /* SubjectInfo.add(code);
                    SubjectInfo.add(time);
                    SubjectInfo.add(lab);
                    SubjectInfo.add(type);
                    SubjectInfo.add(teacher);*/
                }
                ++j;
                //System.out.println(j);
            }
        } catch (IOException ex) {
            Logger.getLogger(PhpRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<SemesterItem> getSubjectInfo() {
        return SubjectInfo;
    }
}
