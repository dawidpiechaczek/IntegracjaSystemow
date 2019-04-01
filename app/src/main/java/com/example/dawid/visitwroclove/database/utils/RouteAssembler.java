package com.example.dawid.visitwroclove.database.utils;

import com.example.dawid.visitwroclove.DAO.model.PointEntity;
import com.example.dawid.visitwroclove.DAO.model.RouteEntity;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;

import java.util.ArrayList;
import java.util.List;

public class RouteAssembler {
    public static RouteDTO DAOtoDTO(RouteEntity oDAO) {
        RouteDTO oDTO = null;

        if (oDAO != null) {
            oDTO = new RouteDTO();
            oDTO.setId(oDAO.getId());
            oDTO.setType(oDAO.getType());
            oDTO.setName(oDAO.getName());
            oDTO.setDescription(oDAO.getDescription());
            oDTO.setLength(oDAO.getLength());
            oDTO.setGlobalId(oDAO.getGlobalId());
            List<PointDTO> pointDTOList = new ArrayList<>();
            for(PointEntity pointDAO : oDAO.getPoints() ){
                pointDTOList.add(DAOtoDTO(pointDAO));
            }
            oDTO.setPoints(pointDTOList);
            oDTO.setRemoved(oDAO.getRemoved());
        }

        return oDTO;
    }

    private static PointDTO DAOtoDTO(PointEntity oDAO){
        PointDTO oDTO = null;
        if (oDAO != null) {
            oDTO = new PointDTO();
            oDTO.setId(oDAO.getId());
            oDTO.setLat(oDAO.getLat());
            oDTO.setLng(oDAO.getLng());
            oDTO.setDescription(oDAO.getDescription());
            oDTO.setObjectId(oDAO.getObjectId());
            oDTO.setRouteId(oDAO.getRouteId());
            oDTO.setEvent(oDAO.isEvent());
        }

        return oDTO;
    }
}
