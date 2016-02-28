/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.tosz.logic.dbhandler;

import java.util.List;

/**
 *
 * @author Tóth Ákos
 * @param <E> Entity type
 * 
 */
public interface IController<E> {
    int getEntityCount();
    List<E> getEntites();
    E  getEntityById(int id);
    void createEntity(E entity);
    void deleteEntity(int id);
    void updateEntity(E entity);
}
