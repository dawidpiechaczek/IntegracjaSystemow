package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.database.interfaces.IEventDAOService;
import com.example.dawid.visitwroclove.model.EventDTO;

import java.util.List;

public class EventDAOImpl implements IEventDAOService {
    private static EventDAOImpl instance;

    public static EventDAOImpl getInstance(){
        if(instance == null){
            instance = new EventDAOImpl();
        }
        return instance;
    }

    protected static void setInstance(EventDAOImpl eventDAOImpl){
        instance = eventDAOImpl;
    }


    @Override
    public List<EventDTO> getFavourites() {
        return null;
    }

    @Override
    public void add(EventDTO entity) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public EventDTO getById(int id) {
        return null;
    }

    @Override
    public List<EventDTO> getAll() {
        return null;
    }
}
