package com.elte.osz.etc;

import com.elte.osz.logic.OszDS;
import com.elte.osz.logic.Utils;
import com.elte.osz.logic.controllers.RoomJpaController;
import com.elte.osz.logic.controllers.SubjectJpaController;
import com.elte.osz.logic.controllers.TeacherJpaController;
import com.elte.osz.logic.phprequest.DataBaseOperations;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
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
 * Adatbázist létrehozása, és adatokkal való feltöltése. Azt az adatbázist
 * használja, amely a persistence.xml-ben van megjelölve.
 *
 * @version 1.0
 * @author RMUGLK
 */
public class InitialDataTransform {

    private interface ElementFound {

        void elementFound(Element element);
    }
    private final String fpRoomsXml;
    private final String fpTeachersXml;
    private final String fpCoursesXml;
    private EntityManager em;
    private final DocumentBuilder db;
    private EntityManagerFactory emf;
    private SubjectJpaController ctrlSubject;
    private TeacherJpaController ctrlTeacher;
    private RoomJpaController ctrlRoom;

    /**
     * Inicializálja az objektumot. Át kell adni paraméterként a három xml
     * fájlt, amiből transform() függvény feltölti az adatbázist.
     * ParserConfigurationException-dob ha nem sikerült inicializálni az XML
     * parsert.
     *
     * @param fpCoursesXml Kurzusok xml fájl útvonala
     * @param fpRoomsXml Termek xml fájl útvonala
     * @param fpTeachersXml Oktatók xml fájl útvonala
     * @throws ParserConfigurationException Hiba az xml parser
     * inicializálásában.
     */
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

    /**
     * XML fájl beolvasása és értelmezése.
     *
     * @param fpXml XML fájl, amit be kell olvasni.
     * @param cb callback függvény, amelyen keresztül értesítés kapunk az éppen
     * aktuális elemről.
     * @throws SAXException xml fájl belső struktúrális hiba.
     * @throws IOException fájl elérés/olvasás hiba.
     */
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

    private void execQuery(EntityManager em, String cmd) {
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

    /**
     * Törli az adatbázist ha létezett, és létrehozza újból, majd feltölti
     * adatokkal. IOException kapunk ha a konstruktorban megadott xml fájlokat
     * nem tudja beolvasni, illetve SAXExcception ha XML parse hiba van.
     *
     * @throws SAXException XML fájl struktúrális, belső hiba.
     * @throws IOException Fájl elérés hiba
     */
    public void transform()
            throws
            SAXException, IOException {
        //TODO ahogyan itt http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/          
        Utils.logger.log(Level.INFO, "Adatbázis törlése és létrehozása...");
        Map map = new HashMap();
        map.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        //Optimalizálás
        //http://java-persistence-performance.blogspot.hu/2011/06/how-to-improve-jpa-performance-by-1825.html
        map.put("eclipselink.jdbc.batch-writing", "JDBC");
        map.put("eclipselink.jdbc.batch-writing.size", "1000");
        map.put("eclipselink.jdbc.cache-statements", "true");
        //disable caching for batch insert (caching only improves reads, so only adds overhead for inserts)
        map.put("eclipselink.cache.shared.default", "false");
        //Avoids the cost of flushing on every query execution.        
        map.put("eclipselink.persistence-context.flush-mode", "commit");
        //Avoids some logging overhead
        map.put("eclipselink.logging.level", "off");
        //avoid cost of persist on commit
        map.put("eclipselink.persistence-context.persist-on-commit", "false");

        Persistence.generateSchema(OszDS.PU, map);
        emf = Persistence.createEntityManagerFactory(OszDS.PU);

        ctrlSubject = new SubjectJpaController(emf);
        ctrlTeacher = new TeacherJpaController(emf);
        ctrlRoom = new RoomJpaController(emf);
        Utils.logger.log(Level.INFO, "SQL Insertek generálása xml fájlokból...");
        final String line = "INSERT INTO %s(%s) VALUES(%s)";

        Utils.logger.log(Level.INFO, "Room tábla feltöltése adatokkal..." + fpRoomsXml);
        em = InitialDataTransform.this.ctrlRoom.getEntityManager();
        em.getTransaction().begin();

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
                em.createNativeQuery(cmd).executeUpdate();
            }
        });

        em.getTransaction().commit();

        em = InitialDataTransform.this.ctrlTeacher.getEntityManager();
        em.getTransaction().begin();
        Utils.logger.log(Level.INFO, "Teacher tábla feltöltése adatokkal..." + fpTeachersXml);
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
                em.createNativeQuery(cmd).executeUpdate();
            }
        });
        em.getTransaction().commit();

        em = InitialDataTransform.this.ctrlSubject.getEntityManager();
        em.getTransaction().begin();
        Utils.logger.log(Level.INFO, "Subject tábla feltöltése adatokkal..." + fpCoursesXml);
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
                em.createNativeQuery(cmd).executeUpdate();

            }
        });

        em.getTransaction().commit();

        emf.close();
        Utils.logger.log(Level.INFO, "Kész!");

    }

    /**
     * Szöveget esc-pel hogy sql adatbázis utasításaiban szereplhessen.(dátumra
     * is)
     *
     * @param value Szöveg amit esc-pelni szeretnék
     * @return sql szöveg, ha value értéke null akkor 'NULL'-t kapunk.
     */
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

    /**
     * XML element-ből tag érték kinyerése szövegként.
     *
     * @param element amelynek gyereke a txt nevű tag
     * @param txt a tag neve, amelynek értékét szeretnénk
     * @return tag értéke, lehet null.
     */
    private String getElementTxt(Element element, String txt) {
        Node n = element.getElementsByTagName(txt).item(0);
        if (n == null) {
            return null;
        }

        return element.getElementsByTagName(txt).item(0).getTextContent();
    }

    /**
     * XML elemből kiszedit az names nevű tagek értékeit, majd olyan formájú
     * szöveget hoz létre amely egy SQL-es insert into kifejezés values részébe
     * való.
     *
     * @param element XML elem
     * @param names tag nevek a element-n belül.
     * @param indexedEsc Azok az indexek az előző names paraméterből, amelyeket
     * esc-pelni kell(mert sql string vag dátum), a növekvő sorrend fontos!
     * @return Esc-pelt szöveg, SQL-es insert into kifejezés values részé.
     * Például: * {@code
     * <row>
     * <tanev_felev>2015-2016-2</tanev_felev>
     * <kurzus_azonosito>BIO/3/2</kurzus_azonosito>
     * <kurzuskod>BIO/3/2</kurzuskod>
     * <oraszam_e>0</oraszam_e>
     * <oraszam_g>0</oraszam_g>
     * <oraszam_l>0</oraszam_l>
     * <kurzusnev>Doktoranduszok beszámolói</kurzusnev>
     * <kar>TTK</kar>
     * </row>
     * Ekkor ha element  objektum tartalmazza <row> tagek közötti xml-t.
     * names tömb legyen [kurzuskod,kurzusnev], és a indexedEsc=[0,1] akkor
     * a visszatérési érték: 'BIO/3/2','Doktoranduszok beszámolói'
     * }
     */
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

    private void loadSemesterItems() {
        Utils.logger.log(Level.INFO, "Jelenlegi szemeszter adatok gyűjtése a TTK TO-ról!");
        new DataBaseOperations().searchSubjectSchedule();
        Utils.logger.log(Level.INFO, "Kész!");
    }

    private void dumpTables(String dir) throws SQLException {
        final Connection connection = DriverManager.getConnection(DataBaseOperations.SQL_URL, DataBaseOperations.properties);

        connection.setAutoCommit(false);

        PreparedStatement ps = connection.prepareStatement(
                "CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE (?,?,?,?,?,?)");

        ps.setString(1, null);
        ps.setString(2, "ROOM");
        ps.setString(3, dir + "room.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);

        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SUBJECT");
        ps.setString(3, dir + "subject.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);

        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "TEACHER");
        ps.setString(3, dir + "teacher.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);

        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SEMESTER");
        ps.setString(3, dir + "semester.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);

        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SEMESTERITEM");
        ps.setString(3, dir + "semesteritem.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);

        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SEMESTER_SEMESTERITEM");
        ps.setString(3, dir + "semester_semesteritem.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);

        ps.addBatch();

        Utils.logger.log(Level.INFO, "DUMPok készítése...");
        ps.executeBatch();

        connection.commit();

        connection.close();
    }

    
    private static void loadDump(String dir) throws SQLException{
        
        
        final Connection connection = DriverManager.getConnection(DataBaseOperations.SQL_URL, DataBaseOperations.properties);
    
        connection.setAutoCommit(false);

        PreparedStatement ps = connection.prepareStatement(
                "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (?,?,?,?,?,?,?)");

        ps.setString(1, null);
        ps.setString(2, "ROOM");
        ps.setString(3, dir + "room.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setInt(7, 0);

        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SUBJECT");
        ps.setString(3, dir + "subject.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setInt(7, 0);
        
        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "TEACHER");
        ps.setString(3, dir + "teacher.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setInt(7, 0);
        
        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SEMESTER");
        ps.setString(3, dir + "semester.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setInt(7, 0);
        
        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SEMESTERITEM");
        ps.setString(3, dir + "semesteritem.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setInt(7, 0);
        
        ps.addBatch();

        ps.setString(1, null);
        ps.setString(2, "SEMESTER_SEMESTERITEM");
        ps.setString(3, dir + "semester_semesteritem.tbl");
        ps.setString(4, null);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setInt(7, 0);
        
        ps.addBatch();

        Utils.logger.log(Level.INFO, "DUMPok feltöltése...");
        ps.executeBatch();

        connection.commit();

        connection.close();
        
        
       /* CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'TEACHER','/tmp/teacher.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'ROOM','/tmp/room.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SUBJECT','/tmp/subject.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SEMESTER','/tmp/semester.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SEMESTERITEM','/tmp/semesteritem.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SEMESTER_SEMESTERITEM','/tmp/semester_semesteritem.tbl',null,null,null,0);*/

    }
    /**
     * Program, amely létrehoz egy InitialDataTransform típusú objektumot és
     * meghívja a transform() függvényét. Program argumentumként kötelező
     * megadni a három xml fájlt, a következő sorrendben: kurzusok, termek
     * oktatok.
     *
     * @param args program argumentumok
     * @throws java.lang.Exception Hiba tovább dobása.
     */
    public static void main(String args[]) throws Exception {

        File fTeacher = new File("src/main/resources/teacher.tbl");
        File fRoom = new File("src/main/resources/room.tbl");
        File fSubject = new File("src/main/resources/subject.tbl");
        File fSemester = new File("src/main/resources/semester.tbl");
        File fSemesterItem = new File("src/main/resources/semesteritem.tbl");
        File fSemester_SemesterItem = new File("src/main/resources/semester_semesteritem.tbl");
        
        String dir = fTeacher.getParentFile().getAbsolutePath() + File.separator;

        if (dir == null) {
            throw new Exception("Unable to get parent directory: " + fTeacher.getAbsolutePath());
        }
        if (args.length == 3) {
            try {
                

                if (fTeacher.exists()) {
                    assert (fTeacher.delete());
                }

                if (fRoom.exists()) {
                    assert (fRoom.delete());
                }

                if (fSubject.exists()) {
                    assert (fSubject.delete());
                }

                if (fSemester.exists()) {
                    assert (fSemester.delete());
                }

                if (fSemesterItem.exists()) {
                    assert (fSemesterItem.delete());
                }

                if (fSemester_SemesterItem.exists()) {
                    assert (fSemester_SemesterItem.delete());
                }

                InitialDataTransform idt = new InitialDataTransform(args[0], args[1], args[2]);
                idt.transform();
                idt.loadSemesterItems();
                idt.dumpTables(dir);

            } catch (Exception e) {
                System.err.println(">>> HIBA <<< :" + e.getLocalizedMessage());
                e.printStackTrace();
                throw e;
            }
        } else if (fRoom.exists()
                && fSemester.exists()
                && fSemesterItem.exists()
                && fSemester_SemesterItem.exists()
                && fSubject.exists()
                && fTeacher.exists()) {
            
            Utils.logger.log(Level.INFO,"Adatbázis újraépítése meglévő adatok alapján!");
            
            Map map = new HashMap();
            map.put("javax.persistence.schema-generation.database.action", "drop-and-create");
            //disable caching for batch insert (caching only improves reads, so only adds overhead for inserts)
            map.put("eclipselink.cache.shared.default", "false");
            //Avoids some logging overhead
            map.put("eclipselink.logging.level", "off");            
                        
            Persistence.generateSchema(OszDS.PU, map);
            loadDump(dir);
            
            Utils.logger.log(Level.INFO,"Kész!");
            
        } 

    }

}
