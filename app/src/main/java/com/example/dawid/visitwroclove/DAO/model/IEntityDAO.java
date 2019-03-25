package com.example.dawid.visitwroclove.DAO.model;

import java.util.List;

/**
 * Created by Dawid on 11.07.2017.
 */

public interface IEntityDAO<T> {

    void add(T entity);
    void deleteById(final int id);
    T getById(final int id);
    List<T> getAll();

}
