/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.phprequest;

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
    private ArrayList<String> SubjectInfo;

    public PhpRequest() {
        SubjectInfo = new ArrayList<String>();
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
            Iterator<Element> it2 = it.next().select("td").iterator();
            it2.next();
            it2.next();
            String time = it2.next().text();
            String lab = it2.next().text();
            it2.next();
            it2.next();
            String type = it2.next().text();
            for(int i = 0; i < 4; ++i)it2.next();
            String teacher = it2.next().text();
            System.out.println(time);
            System.out.println(lab);
            System.out.println(type);
            System.out.println(teacher); 
            SubjectInfo.add(code);
            SubjectInfo.add(time);
            SubjectInfo.add(lab);
            SubjectInfo.add(type);
            SubjectInfo.add(teacher);
        } catch (IOException ex) {
            Logger.getLogger(PhpRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getSubjectInfo() {
        return SubjectInfo;
    }
}
