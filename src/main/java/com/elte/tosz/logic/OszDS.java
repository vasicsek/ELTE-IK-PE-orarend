/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.tosz.logic;

import com.elte.tosz.controllers.ProposedTimeItemJpaController;
import com.elte.tosz.controllers.ProposedTimeJpaController;
import com.elte.tosz.controllers.RoomJpaController;
import com.elte.tosz.controllers.SubjectJpaController;
import com.elte.tosz.controllers.SyllabusItemJpaController;
import com.elte.tosz.controllers.SyllabusJpaController;
import com.elte.tosz.logic.dbhandler.DataSource;
import com.elte.tosz.logic.entities.ProposedTime;
import com.elte.tosz.logic.entities.ProposedTimeItem;

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
        super("puTosz");
        ctrlRoom = new RoomJpaController(emf);    
        ctrlSyllabus = new SyllabusJpaController(emf);
        ctrlSyllabusItem = new SyllabusItemJpaController(emf);
        ctrlSubject = new SubjectJpaController(emf);
        ctrlProposed = new ProposedTimeJpaController(emf);
        ctrlProposedItem = new ProposedTimeItemJpaController(emf);     
    }
    
}
