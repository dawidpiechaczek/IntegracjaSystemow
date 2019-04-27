package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.DAO.model.PointEntity;
import com.example.dawid.visitwroclove.DAO.model.RouteEntity;
import com.example.dawid.visitwroclove.database.enums.Removed;
import com.example.dawid.visitwroclove.database.interfaces.IRouteDAOService;
import com.example.dawid.visitwroclove.database.utils.RealmTable;
import com.example.dawid.visitwroclove.database.utils.RouteAssembler;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

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
    public void add(RouteDTO entity) {
        int globalId;
        RouteDTO existingRoute = getById(entity.getId());

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        if (existingRoute != null) {
            globalId = existingRoute.getId();
        } else {
            if (realm.where(RouteEntity.class).max("id") != null) {
                globalId = realm.where(RouteEntity.class).max("id").intValue();
                globalId++;
            } else {
                globalId = 1;
            }
        }

        RouteEntity r = new RouteEntity();
        RealmList<PointEntity> points = new RealmList<>();

        r.setId(globalId);

        //int primaryKeyValue = new AtomicLong(realm.where(RouteDAO.class).max("id").longValue());
        int primaryKey;
        if (realm.where(RouteEntity.class).max("id_local") != null) {
            if (realm.where(RouteEntity.class).max("id_local").intValue() < 1) {
                primaryKey = 1;
            } else {
                primaryKey = realm.where(RouteEntity.class).max("id_local").intValue();
                primaryKey++;
            }
        } else {
            primaryKey = 1;
        }

        if (entity.isMine()) {
            r.setId(-1);
            r.setId_local(primaryKey);
        } else {
            r.setId(entity.getId());
            r.setId_local(-1);
        }

        Number currentIdNum = realm.where(RouteEntity.class).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }

        r.setId(globalId);
        //r.setId(primaryKey);
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        r.setLength(entity.getLength());
        r.setType(entity.getType());
        r.setAmount(entity.getAmount());

        for (int i = 0; i < entity.getPoints().size(); i++) {
            PointEntity p = new PointEntity();
            if (entity.getPoints().get(i).getId() != null)
                p.setId(entity.getPoints().get(i).getId());
            else
                p.setId(globalId + "." + i);
            p.setRouteId(entity.getPoints().get(i).getRouteId());
            p.setObjectId(entity.getPoints().get(i).getObjectId());
            p.setLat(entity.getPoints().get(i).getLat());
            p.setLng(entity.getPoints().get(i).getLng());
            p.setDescription(entity.getPoints().get(i).getDescription());
            p.setEvent(entity.getPoints().get(i).isEvent());

            points.add(p);
        }
        r.setPoints(points);
        r.setRemoved(entity.getRemoved());

        r.setMine(entity.isMine());

        realm.copyToRealmOrUpdate(r);

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteById(int id_local) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RouteEntity e = realm.where(RouteEntity.class).equalTo("id_local", id_local).findFirst();
        if (e != null) {
            e.setRemoved(Removed.REMOVED.getValue());
        }

        realm.commitTransaction();
        realm.close();

    }

    @Override
    public RouteDTO getById(int id) {
        Realm realm = Realm.getDefaultInstance();
        RouteEntity rDAO = realm.where(RouteEntity.class)
                //   .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo("id", id).findFirst();

        RouteDTO r = null;

        if (rDAO != null) {
            r = new RouteDTO();
            r.setId(rDAO.getId());
            r.setLength(rDAO.getLength());
            r.setAmount(rDAO.getAmount());
            r.setDescription(rDAO.getDescription());
            r.setName(rDAO.getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int i = 0; i < rDAO.getPoints().size(); i++) {
                PointDTO p = new PointDTO();
                p.setId(rDAO.getPoints().get(i).getId());
                p.setRouteId(rDAO.getPoints().get(i).getRouteId());
                p.setObjectId(rDAO.getPoints().get(i).getObjectId());
                p.setLat(rDAO.getPoints().get(i).getLat());
                p.setLng(rDAO.getPoints().get(i).getLng());
                p.setDescription(rDAO.getPoints().get(i).getDescription());
                p.setEvent(rDAO.getPoints().get(i).isEvent());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(rDAO.getId_local());
            r.setRemoved(rDAO.getRemoved());
        }

        realm.close();
        return r;
    }

    @Override
    public List<RouteDTO> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<RouteEntity> query = realm.where(RouteEntity.class)
                .equalTo(RealmTable.RouteDAO.IS_MINE, false);
        RealmResults<RouteEntity> results = query.findAll().sort(RealmTable.RouteDAO.NAME, Sort.ASCENDING);

        List<RouteDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            RouteDTO r = new RouteDTO();
            r.setId(results.get(i).getId());
            r.setLength(results.get(i).getLength());
            r.setAmount(results.get(i).getAmount());
            r.setDescription(results.get(i).getDescription());
            r.setName(results.get(i).getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int j = 0; j < results.get(i).getPoints().size(); j++) {
                PointDTO p = new PointDTO();

                p.setId(results.get(i).getPoints().get(j).getId());
                p.setRouteId(results.get(i).getPoints().get(j).getRouteId());
                p.setObjectId(results.get(i).getPoints().get(j).getObjectId());
                p.setLat(results.get(i).getPoints().get(j).getLat());
                p.setLng(results.get(i).getPoints().get(j).getLng());
                p.setDescription(results.get(i).getPoints().get(j).getDescription());
                p.setEvent(results.get(i).getPoints().get(j).isEvent());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(results.get(i).getId_local());
            r.setRemoved(results.get(i).getRemoved());

            list.add(r);
        }

        realm.close();
        return list;
    }

    public List<RouteDTO> getAllMine() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<RouteEntity> query = realm.where(RouteEntity.class)
                .equalTo(RealmTable.RouteDAO.IS_MINE, true);
        RealmResults<RouteEntity> results = query.findAll().sort(RealmTable.RouteDAO.NAME, Sort.ASCENDING);

        List<RouteDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            RouteDTO r = new RouteDTO();
            r.setId(results.get(i).getId());
            r.setLength(results.get(i).getLength());
            r.setAmount(results.get(i).getAmount());
            r.setDescription(results.get(i).getDescription());
            r.setName(results.get(i).getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int j = 0; j < results.get(i).getPoints().size(); j++) {
                PointDTO p = new PointDTO();

                p.setId(results.get(i).getPoints().get(j).getId());
                p.setRouteId(results.get(i).getPoints().get(j).getRouteId());
                p.setObjectId(results.get(i).getPoints().get(j).getObjectId());
                p.setLat(results.get(i).getPoints().get(j).getLat());
                p.setLng(results.get(i).getPoints().get(j).getLng());
                p.setDescription(results.get(i).getPoints().get(j).getDescription());
                p.setEvent(results.get(i).getPoints().get(j).isEvent());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(results.get(i).getId_local());
            r.setRemoved(results.get(i).getRemoved());

            list.add(r);
        }

        realm.close();
        return list;
    }

    // =============================================================================================

    @Override
    public RouteDTO getByIdLocal(int id_local) {
        Realm realm = Realm.getDefaultInstance();
        RouteEntity rDAO = realm.where(RouteEntity.class)
                .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo("id_local", id_local).findFirst();

        RouteDTO r = null;

        if (rDAO != null) {
            r = new RouteDTO();
            r.setId(rDAO.getId());
            r.setLength(rDAO.getLength());
            r.setAmount(rDAO.getAmount());
            r.setDescription(rDAO.getDescription());
            r.setName(rDAO.getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int i = 0; i < rDAO.getPoints().size(); i++) {
                PointDTO p = new PointDTO();
                p.setRouteId(rDAO.getPoints().get(i).getRouteId());
                p.setObjectId(rDAO.getPoints().get(i).getObjectId());
                p.setLat(rDAO.getPoints().get(i).getLat());
                p.setLng(rDAO.getPoints().get(i).getLng());
                p.setDescription(rDAO.getPoints().get(i).getDescription());
                p.setEvent(rDAO.getPoints().get(i).isEvent());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(rDAO.getId_local());
            r.setRemoved(rDAO.getRemoved());
        }

        realm.close();
        return r;
    }

    @Override
    public List<RouteDTO> getByType(String type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RouteEntity> results = realm.where(RouteEntity.class)
                .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                //          .equalTo(RealmTable.ObjectDAO.STATUS, Status.PUBLISH.getValue())
                .equalTo(RealmTable.RouteDAO.TYPE, type).findAll().sort(RealmTable.RouteDAO.NAME, Sort.ASCENDING);

        List<RouteDTO> list = new ArrayList<>();
        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                RouteDTO oDTO = RouteAssembler.DAOtoDTO(results.get(i));
                list.add(oDTO);
            }
        }
        realm.close();
        return list;
    }

    @Override
    public List<RouteDTO> getMine() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<RouteEntity> query = realm.where(RouteEntity.class)
                .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo("isMine", true);
        RealmResults<RouteEntity> results = query.findAll().sort(RealmTable.RouteDAO.NAME, Sort.ASCENDING);

        List<RouteDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            RouteDTO r = new RouteDTO();
            r.setId(results.get(i).getId());
            r.setLength(results.get(i).getLength());
            r.setAmount(results.get(i).getAmount());
            r.setDescription(results.get(i).getDescription());
            r.setName(results.get(i).getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int j = 0; j < results.get(i).getPoints().size(); j++) {
                PointDTO p = new PointDTO();

                p.setRouteId(results.get(i).getPoints().get(j).getRouteId());
                p.setObjectId(results.get(i).getPoints().get(j).getObjectId());
                p.setLat(results.get(i).getPoints().get(j).getLat());
                p.setLng(results.get(i).getPoints().get(j).getLng());
                p.setDescription(results.get(i).getPoints().get(j).getDescription());
                p.setEvent(results.get(i).getPoints().get(j).isEvent());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(results.get(i).getId_local());
            r.setMine(results.get(i).isMine());

            list.add(r);
        }

        realm.close();
        return list;
    }

    @Override
    public void updateLocal(RouteDTO r) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RouteEntity fromDbDAO = realm.where(RouteEntity.class).equalTo("id_local", r.getId_local()).findFirst();

        RouteEntity rDAO = new RouteEntity();
        if (fromDbDAO != null) {
            rDAO.setId(fromDbDAO.getId());
            rDAO.setName(r.getName());
            rDAO.setDescription(r.getDescription());
            rDAO.setLength(r.getLength());
            rDAO.setAmount(r.getAmount());

            RealmList<PointEntity> points = new RealmList<>();
            for (int i = 0; i < r.getPoints().size(); i++) {
                PointEntity p = new PointEntity();
                p.setId(fromDbDAO.getId() + "." + i);
                p.setRouteId(r.getPoints().get(i).getRouteId());
                p.setObjectId(r.getPoints().get(i).getObjectId());
                p.setLat(r.getPoints().get(i).getLat());
                p.setLng(r.getPoints().get(i).getLng());
                p.setDescription(r.getPoints().get(i).getDescription());
                p.setEvent(r.getPoints().get(i).isEvent());

                points.add(p);
            }
            rDAO.setPoints(points);

            rDAO.setId_local(r.getId_local());
            rDAO.setMine(true);
        }

        realm.copyToRealmOrUpdate(rDAO);

        realm.commitTransaction();
        realm.close();
    }

    public List<RouteDTO> filter(String nameOfRoute, String distanceFrom, String distanceFor, String pointsFrom, String pointsFor, boolean isMine) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<RouteEntity> query = realm.where(RouteEntity.class).equalTo(RealmTable.RouteDAO.IS_MINE, isMine);
        if (!nameOfRoute.equals("")) {
            query.contains("name", nameOfRoute);
        }
        if (!distanceFor.equals("") && !distanceFrom.equals("")) {
            query.between("length", Double.parseDouble(distanceFrom), Double.parseDouble(distanceFor));
        } else if(distanceFor.equals("") && !distanceFrom.equals("")){
            query.between("length", Double.parseDouble(distanceFrom), 100);
        }
        if (!pointsFor.equals("") && !pointsFrom.equals("")) {
            query.between("amount", Integer.parseInt(pointsFrom), Integer.parseInt(pointsFor));
        }
        // .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue());
        RealmResults<RouteEntity> results = query.findAll();

        List<RouteDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            RouteDTO r = new RouteDTO();
            r.setId(results.get(i).getId());
            r.setLength(results.get(i).getLength());
            r.setAmount(results.get(i).getAmount());
            r.setDescription(results.get(i).getDescription());
            r.setName(results.get(i).getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int j = 0; j < results.get(i).getPoints().size(); j++) {
                PointDTO p = new PointDTO();

                p.setId(results.get(i).getPoints().get(j).getId());
                p.setRouteId(results.get(i).getPoints().get(j).getRouteId());
                p.setObjectId(results.get(i).getPoints().get(j).getObjectId());
                p.setLat(results.get(i).getPoints().get(j).getLat());
                p.setLng(results.get(i).getPoints().get(j).getLng());
                p.setDescription(results.get(i).getPoints().get(j).getDescription());
                p.setEvent(results.get(i).getPoints().get(j).isEvent());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(results.get(i).getId_local());
            r.setRemoved(results.get(i).getRemoved());

            list.add(r);
        }

        realm.close();
        return list;
    }

}
