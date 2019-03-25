package com.example.dawid.visitwroclove.database.interfaces;

import com.example.dawid.visitwroclove.model.EventDTO;

import java.util.List;

public interface IEventDAOService extends IEntityDAOService<EventDTO>{
    List<EventDTO> getFavourites();
}