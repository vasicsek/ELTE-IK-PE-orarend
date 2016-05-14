/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic.phprequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Dobos Árpád
 */
public class DataBaseOperations {
    private Connection connection;
    private Properties properties;
    private PhpRequest phprequest;
    
    private final String SQL_URL = "jdbc:derby://localhost:1527/osz";
    
    private final String GET_ALL_SUBJECTS_CODE = "SELECT CODE FROM SUBJET";
    private final String GET_SUBJECT_ID = "SELECT ID FROM SUBJECT WHERE CODE = (?)";
    private final String GET_TEACHER_ID = "SELECT ID FROM TEACHER WHERE NAME = (?)";
    private final String GET_ROOM_ID = "SELECT ID FROM ROOM WHERE NAME = (?)";
    
    private final String ADD_ELEMENT_TO_SEMESTERITEM = "INSERT INTO SEMESTERITEM (ENDTIME, STARTTIME, ROOM_ID, SUBJECT_ID,"
            + "TEACHER_ID) VALUES (?,?,?,?,?)";

    public DataBaseOperations() throws SQLException {
        properties = new Properties();
        properties.put("username", "osz");
        properties.put("password", "osz");
        phprequest = new PhpRequest();
        connection = DriverManager.getConnection(SQL_URL, properties);
    }
    
    
}
