package com.example.dawid.visitwroclove.database.utils;

import com.example.dawid.visitwroclove.DAO.model.AddressEntity;
import com.example.dawid.visitwroclove.model.AddressDTO;

public class AddressAssembler {

    public static AddressDTO AddressDAOtoDTO(AddressEntity addressDAO) {
        AddressDTO newDTO = new AddressDTO();

        newDTO.setId(addressDAO.getId());
        newDTO.setCity(addressDAO.getCity());
        newDTO.setStreet(addressDAO.getStreet());
        newDTO.setHomeNumber(addressDAO.getHomeNumber());
        newDTO.setZipCode(addressDAO.getZipCode());
        newDTO.setLat(addressDAO.getLat());
        newDTO.setLng(addressDAO.getLng());

        return newDTO;
    }
}
