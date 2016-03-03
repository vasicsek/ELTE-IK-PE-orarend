/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic;

import com.elte.osz.logic.controllers.ProposedTimeItemJpaController;
import com.elte.osz.logic.controllers.ProposedTimeJpaController;
import com.elte.osz.logic.controllers.RoomJpaController;
import com.elte.osz.logic.controllers.SubjectJpaController;
import com.elte.osz.logic.controllers.SyllabusItemJpaController;
import com.elte.osz.logic.controllers.SyllabusJpaController;
import com.elte.osz.logic.dbhandler.DataSource;
import com.elte.osz.logic.entities.ProposedTime;
import com.elte.osz.logic.entities.ProposedTimeItem;

/**
 *
 * @author Tóth Ákos
 */
public class OszDS extends DataSource{

    private RoomJpaController ctrlRoom;    
    private SyllabusJpaController ctrlSyllabus;
    private SyllabusItemJpaController ctrlSyllabusItem;
    private SubjectJpaController ctrlSubject;
    private ProposedTimeJpaController  ctrlProposed;
    private ProposedTimeItemJpaController ctrlProposedItem;
    
    public RoomJpaController getCtrlRoom() {
        return ctrlRoom;
    }

    public SyllabusJpaController getCtrlSyllabus() {
        return ctrlSyllabus;
    }

    public SyllabusItemJpaController getCtrlSyllabusItem() {
        return ctrlSyllabusItem;
    }

    public SubjectJpaController getCtrlSubject() {
        return ctrlSubject;
    }

    public ProposedTimeJpaController getCtrlProposed() {
        return ctrlProposed;
    }

    public ProposedTimeItemJpaController getCtrlProposedItem() {
        return ctrlProposedItem;
    }
    
    public OszDS() {
        //super("puTosz");
        super("puOsz");
        ctrlRoom = new RoomJpaController(emf);    
        ctrlSyllabus = new SyllabusJpaController(emf);
        ctrlSyllabusItem = new SyllabusItemJpaController(emf);
        ctrlSubject = new SubjectJpaController(emf);
        ctrlProposed = new ProposedTimeJpaController(emf);
        ctrlProposedItem = new ProposedTimeItemJpaController(emf);     
    }
    
}
