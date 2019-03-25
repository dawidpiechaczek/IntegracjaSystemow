package com.example.dawid.visitwroclove.database.interfaces;

import com.example.dawid.visitwroclove.model.ObjectDTO;

import java.util.List;


public interface IObjectDAOService extends IEntityDAOService<ObjectDTO> {
    List<ObjectDTO> getByName(final String name);
    List<ObjectDTO> getByType(final String type);
    List<ObjectDTO> getRecommended(final int mark); //podaje się ocenę od jakiej się chce polecane
    List<ObjectDTO> getFavourites();
}
