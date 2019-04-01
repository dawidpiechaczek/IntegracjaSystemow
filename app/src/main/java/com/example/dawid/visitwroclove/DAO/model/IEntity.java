package com.example.dawid.visitwroclove.DAO.model;

import java.util.List;

public interface IEntity<T> {

    void add(T entity);
    void deleteById(final int id);
    T getById(final int id);
    List<T> getAll();

}
