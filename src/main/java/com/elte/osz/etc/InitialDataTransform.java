/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.etc;

import com.elte.osz.logic.OszDS;
import com.elte.osz.logic.controllers.RoomJpaController;
import com.elte.osz.logic.controllers.SubjectJpaController;
import com.elte.osz.logic.controllers.TeacherJpaController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Tóth Ákos
 */
public class InitialDataTransform {

    private interface ElementFound {

        void elementFound(Element element);
    }
    private final String fpRoomsXml;
    private final String fpTeachersXml;
    private final String fpCoursesXml;
    
    private final DocumentBuilder db;
    private EntityManagerFactory emf;
    private SubjectJpaController ctrlSubject;
    private TeacherJpaController ctrlTeacher;
    private RoomJpaController ctrlRoom;
    

    public InitialDataTransform(
            String fpCoursesXml,
            String fpRoomsXml,
            String fpTeachersXml
        
    ) throws ParserConfigurationException {
        this.fpCoursesXml = fpCoursesXml;
        this.fpRoomsXml = fpRoomsXml;
        this.fpTeachersXml = fpTeachersXml;
        this.db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        

    }

    private void parse(String fpXml, ElementFound cb) throws SAXException, IOException {

        Document doc = db.parse(new File(fpXml));
        doc.getDocumentElement().normalize();
        //mivel az összes adatbázis dump xml file ROW node-ban tárolja az adatot        
        NodeList lsNode = doc.getElementsByTagName("ROW");
        for (int i = 0; i < lsNode.getLength(); ++i) {

            Node node = lsNode.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                cb.elementFound((Element) node);
            }
        }
    }

    public void transform()
            throws FileNotFoundException, ParserConfigurationException,
            SAXException, IOException {
        //TODO ahogyan itt http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/          
        System.out.println("Adatbázis törlése és létrehozása..."); 
        Map map = new HashMap();
        map.put("javax.persistence.schema-generation.database.action", "drop-and-create");

        Persistence.generateSchema(OszDS.PU, map);
        emf = Persistence.createEntityManagerFactory(OszDS.PU);

        ctrlSubject = new SubjectJpaController(emf);
        ctrlTeacher = new TeacherJpaController(emf);
        ctrlRoom = new RoomJpaController(emf);
        System.out.println("SQL Insertek generálása xml fájlokból...");
        final String line = "INSERT INTO %s(%s) VALUES(%s)";
        System.out.println("Room tábla feltöltése adatokkal..." + fpRoomsXml);
        
        parse(fpRoomsXml, new ElementFound() {
            @Override
            public void elementFound(Element element) {
                                
                /*
                Példa:
                <terem_azonosito>4</terem_azonosito>
		<nev>0.83 Eötvös terem</nev>
		<meret>165</meret>
		<tipuskod>e</tipuskod>
		<epuletkod>aaa</epuletkod>
		<emelet>0</emelet>
		<kezelokod>KK</kezelokod>
                 */
                //tag nevek, amiknek a text-je kell
                final List<String> tags = Arrays.asList("nev", "epuletkod", "emelet");
                //indexek a "cols" listához, 
                //amik mutatják hogy melyik elemet kell sql stringé alakítani
                //ugyanolyan sorrendben mint ahogyan a cols listában jönnek
                final List<Integer> iesc = Arrays.asList(0, 1);
                String values = getValues(element, tags, iesc);
                String cmd = String.format(line, "\"ROOM\"", "\"NAME\",\"BUILDING\",\"FLOOR\"", values);
                EntityManager em = InitialDataTransform.this.ctrlRoom.getEntityManager();
                try {
                    em.getTransaction().begin();
                    em.createNativeQuery(cmd).executeUpdate();
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.close();
                    e.printStackTrace();
                    throw e;
                }
            }
        });

        System.out.println("Teacher tábla feltöltése adatokkal..." + fpTeachersXml);
        parse(fpTeachersXml, new ElementFound() {
            @Override
            public void elementFound(Element element) {
                /*
                Példa:
                <tanar_azonosito>10000</tanar_azonosito>
		<nev>Szentesi Árpád Dr.</nev>
                 */
                //tag nevek, amiknek a text-je kell
                final List<String> tags = Arrays.asList("nev");
                //indexek a "cols" listához, 
                //amik mutatják hogy melyik elemet kell sql stringé alakítani
                //ugyanolyan sorrendben mint ahogyan a cols listában jönnek
                final List<Integer> iesc = Arrays.asList(0);
                String values = getValues(element, tags, iesc);
                String cmd = String.format(line, "\"TEACHER\"", "\"NAME\"", values);

                EntityManager em = InitialDataTransform.this.ctrlTeacher.getEntityManager();

                try {
                    em.getTransaction().begin();
                    em.createNativeQuery(cmd).executeUpdate();
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.close();
                    e.printStackTrace();
                    throw e;
                }
            }
        });
        System.out.println("Subject tábla feltöltése adatokkal..." + fpCoursesXml);
        parse(fpCoursesXml, new ElementFound() {
            @Override
            public void elementFound(Element element) {
                /*
               Példa:
               <tanev_felev>2015-2016-2</tanev_felev>
		<kurzus_azonosito>BIO/3/2</kurzus_azonosito>
		<kurzuskod>BIO/3/2</kurzuskod>
		<oraszam_e>0</oraszam_e>
		<oraszam_g>0</oraszam_g>
		<oraszam_l>0</oraszam_l>
		<kurzusnev>Doktoranduszok beszámolói</kurzusnev>
		<kar>TTK</kar>
                 */

                //tag nevek, amiknek a text-je kell
                final List<String> tags = Arrays.asList("kurzuskod", "oraszam_e", "oraszam_g", "oraszam_l", "kurzusnev", "kar");
                //indexek a "cols" listához, 
                //amik mutatják hogy melyik elemet kell sql stringé alakítani
                //ugyanolyan sorrendben mint ahogyan a cols listában jönnek
                final List<Integer> iesc = Arrays.asList(0, 4, 5);
                String values = getValues(element, tags, iesc);
                final String cols = "\"CODE\",\"HOURS_PRESENTATION\","
                        + "\"HOURS_PRACTICAL\","
                        + "\"HOURS_NIGHTLY\","
                        + "\"NAME\",\"DEPARTMENT\"";

                String cmd = String.format(line, "\"SUBJECT\"", cols, values);
                EntityManager em = InitialDataTransform.this.ctrlSubject.getEntityManager();
                try {
                    em.getTransaction().begin();
                    em.createNativeQuery(cmd).executeUpdate();
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.close();
                    e.printStackTrace();
                    throw e;
                }

            }
        });
        
        emf.close();
        System.out.println("Kész!");

    }

    private String makeSqlString(String value) {
        if (value == null) {
            return "NULL";
        }

        Pattern p = Pattern.compile("'");
        Matcher m = p.matcher(value);
        StringBuffer sb = new StringBuffer();
        sb.append("'");
        while (m.find()) {
            m.appendReplacement(sb, "''");
        }
        m.appendTail(sb);
        sb.append("'");

        return sb.toString();
    }

    private String getElementTxt(Element element, String txt) {
        Node n = element.getElementsByTagName(txt).item(0);
        if (n == null) {
            return null;
        }

        return element.getElementsByTagName(txt).item(0).getTextContent();
    }

    private String getValues(Element element, List<String> names, List<Integer> indexedEsc) {

        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < names.size(); ++i) {
            if ((j < indexedEsc.size()) && (i == indexedEsc.get(j))) {
                sb.append(makeSqlString(getElementTxt(element, names.get(i))));
                ++j;
            } else {
                String str = getElementTxt(element, names.get(i));
                sb.append(str == null ? "NULL" : str);
            }
            if (i < names.size() - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    public static void main(String args[]) throws Exception {

        if (args.length == 3) {
            try {                              
                new InitialDataTransform(args[0], args[1], args[2]).transform();               
            } catch (Exception e) {
                System.err.println(">>> HIBA <<< :" + e.getLocalizedMessage());
                e.printStackTrace();
                throw e;
            }
        } else {

            throw new IllegalArgumentException("Rossz argumentum paraméterek az erőforrás generáláshoz!\nMegadott paraméterek száma:" + args.length);

        }

    }

}
