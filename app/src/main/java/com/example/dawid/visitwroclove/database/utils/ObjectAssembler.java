package com.example.dawid.visitwroclove.database.utils;

import com.example.dawid.visitwroclove.DAO.model.ObjectEntity;
import com.example.dawid.visitwroclove.model.ObjectDTO;

public class ObjectAssembler {
    public static ObjectDTO DAOtoDTO(ObjectEntity oDAO) {
        ObjectDTO oDTO = null;

        if (oDAO != null) {
            oDTO = new ObjectDTO();
            oDTO.setId(oDAO.getId());
            oDTO.setType(oDAO.getType());
            oDTO.setName(oDAO.getName());
            oDTO.setDescription(oDAO.getDescription());
            oDTO.setPhone(oDAO.getPhone());
            oDTO.setAddressId(oDAO.getAddressId());
            oDTO.setAddress(AddressAssembler.AddressDAOtoDTO(oDAO.getAddress()));
            oDTO.setThumb(oDAO.getThumb());
            oDTO.setImage(oDAO.getImage());
            oDTO.setRemoved(oDAO.isRemoved());
            oDTO.setFavourite(oDAO.isFavourite());
        }

        return oDTO;
    }


}
