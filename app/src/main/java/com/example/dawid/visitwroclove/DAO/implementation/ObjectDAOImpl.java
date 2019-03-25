package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.database.interfaces.IObjectDAOService;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import java.util.List;

public class ObjectDAOImpl implements IObjectDAOService {
    private static ObjectDAOImpl instance;

    public static ObjectDAOImpl getInstance(){
        if(instance == null){
            instance = new ObjectDAOImpl();
        }
        return instance;
    }

    protected static void setInstance(ObjectDAOImpl objectDAOImpl){
        instance = objectDAOImpl;
    }

    @Override
    public List<ObjectDTO> getByName(String name) {
        return null;
    }

    @Override
    public List<ObjectDTO> getByType(String type) {
        return null;
    }

    @Override
    public List<ObjectDTO> getRecommended(int mark) {
        return null;
    }

    @Override
    public List<ObjectDTO> getFavourites() {
        return null;
    }

    @Override
    public void add(ObjectDTO entity) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ObjectDTO getById(int id) {
        return null;
    }

    @Override
    public List<ObjectDTO> getAll() {
        return null;
    }
}

