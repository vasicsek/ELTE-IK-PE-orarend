package com.elte.osz.logic.dbhandler;

import java.util.List;

/**
 *
 * @author RMUGLK
 * @param <E> Entity type
 *
 */
public interface IController<E> {

    int getEntityCount();

    List<E> getEntites();

    E getEntityById(int id);

    void createEntity(E entity);

    void deleteEntity(int id);

    void updateEntity(E entity);
}
