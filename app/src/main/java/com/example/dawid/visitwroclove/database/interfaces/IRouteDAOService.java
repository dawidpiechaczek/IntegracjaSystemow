package com.example.dawid.visitwroclove.database.interfaces;
import com.example.dawid.visitwroclove.model.RouteDTO;

import java.util.List;

public interface IRouteDAOService extends IEntityDAOService<RouteDTO> {
    RouteDTO getByIdLocal(int id_local);
    List<RouteDTO> getByType(final String type);
    List<RouteDTO> getMine();
    void updateLocal(RouteDTO r);
}
