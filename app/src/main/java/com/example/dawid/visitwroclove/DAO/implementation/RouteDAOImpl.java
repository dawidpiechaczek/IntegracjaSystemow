package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.database.interfaces.IRouteDAOService;
import com.example.dawid.visitwroclove.model.RouteDTO;

import java.util.List;

public class RouteDAOImpl implements IRouteDAOService {

    private static RouteDAOImpl instance;

    public static RouteDAOImpl getInstance() {
        if (instance == null) {
            instance = new RouteDAOImpl();
        }
        return instance;
    }

    private static final String TAG = RouteDAOImpl.class.getName();


    @Override
    public RouteDTO getByIdLocal(int id_local) {
        return null;
    }

    @Override
    public List<RouteDTO> getByType(String type) {
        return null;
    }

    @Override
    public List<RouteDTO> getMine() {
        return null;
    }

    @Override
    public void updateLocal(RouteDTO r) {

    }

    @Override
    public void add(RouteDTO entity) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public RouteDTO getById(int id) {
        return null;
    }

    @Override
    public List<RouteDTO> getAll() {
        return null;
    }
}
