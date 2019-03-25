package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.DAO.model.AddressDAO;
import com.example.dawid.visitwroclove.DAO.model.IEntityDAO;
import com.example.dawid.visitwroclove.model.AddressDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Dawid on 11.07.2017.
 */

public class AddressDAOImpl implements IEntityDAO<AddressDTO> {
    private static AddressDAOImpl instance;

    public static AddressDAOImpl getInstance() {
        if (instance == null) {
            instance = new AddressDAOImpl();
        }
        return instance;
    }

    @Override
    public void add(AddressDTO entity) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public AddressDTO getById(int id) {
        return null;
    }

    @Override
    public List<AddressDTO> getAll() {
        return null;
    }
}
