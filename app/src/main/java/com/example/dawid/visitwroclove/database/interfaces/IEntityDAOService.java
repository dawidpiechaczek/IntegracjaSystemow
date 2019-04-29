package com.example.dawid.visitwroclove.database.interfaces;

import java.util.List;

public interface IEntityDAOService<T> {
    void deleteById(final int id);
    T getById(final int id);
    List<T> getAll();
}
