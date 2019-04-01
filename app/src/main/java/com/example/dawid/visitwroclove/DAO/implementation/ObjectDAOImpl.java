package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.DAO.model.AddressEntity;
import com.example.dawid.visitwroclove.DAO.model.ObjectEntity;
import com.example.dawid.visitwroclove.database.enums.Removed;
import com.example.dawid.visitwroclove.database.enums.Status;
import com.example.dawid.visitwroclove.database.interfaces.IObjectDAOService;
import com.example.dawid.visitwroclove.database.utils.ObjectAssembler;
import com.example.dawid.visitwroclove.database.utils.RealmTable;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class ObjectDAOImpl implements IObjectDAOService {
    private static ObjectDAOImpl instance;

    public static ObjectDAOImpl getInstance(){
        if(instance == null){
            instance = new ObjectDAOImpl();
        }
        return instance;
    }

    protected static void setInstance(ObjectDAOImpl objectDAOImpl){
        instance = objectDAOImpl;
    }


    @Override
    public void add(ObjectDTO entity) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        ObjectEntity objectDAO = new ObjectEntity();
        AddressEntity aDAO = new AddressEntity();

        aDAO.setId(entity.getAddress().getId());
        aDAO.setCity(entity.getAddress().getCity());
        aDAO.setStreet(entity.getAddress().getStreet());
        aDAO.setHomeNumber(entity.getAddress().getHomeNumber());
        aDAO.setZipCode(entity.getAddress().getZipCode());
        aDAO.setLat(entity.getAddress().getLat());
        aDAO.setLng(entity.getAddress().getLng());

        objectDAO.setId(entity.getId());
        objectDAO.setRank(Math.round(1 + (5 - 1) * new Random().nextDouble()));
        objectDAO.setType(entity.getType());
        objectDAO.setName(entity.getName());
        objectDAO.setDescription(entity.getDescription());
        objectDAO.setPhone(entity.getPhone());
        objectDAO.setAddressId(entity.getAddressId());
        objectDAO.setAddress(aDAO);
        objectDAO.setThumb(entity.getThumb());
        objectDAO.setImage(entity.getImage());
        objectDAO.setRemoved(entity.isRemoved());
        objectDAO.setFavourite(entity.isFavourite());

        realm.copyToRealmOrUpdate(objectDAO);

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteById(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        ObjectEntity e = realm.where(ObjectEntity.class).equalTo(RealmTable.ID, id).findFirst();
        if (e != null) {
            e.deleteFromRealm();
        }

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public ObjectDTO getById(int id) {
        Realm realm = Realm.getDefaultInstance();
        ObjectEntity eDAO = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ID, id)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                //       .equalTo(RealmTable.ObjectDAO.STATUS, Status.PUBLISH.getValue())
                .findFirst();

        ObjectDTO eDTO = null;

        if (eDAO != null) {
            eDTO = ObjectAssembler.DAOtoDTO(eDAO);
        }
        realm.close();
        return eDTO;
    }

    @Override
    public List<ObjectDTO> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ObjectEntity> query = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue());
        RealmResults<ObjectEntity> results = query.findAll().sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);
        //RealmResults<EventDAO> results = query.findAll().sort(RealmTable.Report.ID, Sort.DESCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
            list.add(oDTO);
        }

        realm.close();

        return list;
    }

    // =============================================================================================

    @Override
    public List<ObjectDTO> getByName(String name) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ObjectEntity> results = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo(RealmTable.ObjectDAO.STATUS, Status.PUBLISH.getValue())
                .contains(RealmTable.ObjectDAO.NAME, name, Case.INSENSITIVE).findAll().sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
                list.add(oDTO);
            }
        }

        realm.close();
        return list;
    }

    @Override
    public List<ObjectDTO> getByType(String type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ObjectEntity> results = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                //          .equalTo(RealmTable.ObjectDAO.STATUS, Status.PUBLISH.getValue())
                .equalTo(RealmTable.ObjectDAO.TYPE, type).findAll().sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
                list.add(oDTO);
            }
        }
        realm.close();
        return list;
    }

    @Override
    public List<ObjectDTO> getRecommended(int mark) {
        //TODO add mark service
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ObjectEntity> results = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo(RealmTable.ObjectDAO.STATUS, Status.PUBLISH.getValue())
                .equalTo(RealmTable.ObjectDAO.RECOMMENDED, 1).findAll().sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
                list.add(oDTO);
            }
        }

        realm.close();
        return list;
    }

    @Override
    public List<ObjectDTO> getFavourites() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ObjectEntity> results = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                //  .equalTo(RealmTable.ObjectDAO.STATUS, Status.PUBLISH.getValue())
                .equalTo(RealmTable.ObjectDAO.FAVOURITE, 1).findAll().sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
                list.add(oDTO);
            }
        }

        realm.close();
        return list;
    }

    public List<ObjectDTO> sortByNameDesc() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ObjectEntity> query = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue());
        RealmResults<ObjectEntity> results = query.findAll().sort(RealmTable.ObjectDAO.NAME, Sort.DESCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
            list.add(oDTO);
        }

        realm.close();

        return list;
    }

    public List<ObjectDTO> sortByNameAsc() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ObjectEntity> query = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue());
        RealmResults<ObjectEntity> results = query.findAll().sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
            list.add(oDTO);
        }

        realm.close();

        return list;
    }

    public List<ObjectDTO> sortByRankDesc() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ObjectEntity> query = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue());
        RealmResults<ObjectEntity> results = query.findAll().sort(RealmTable.ObjectDAO.RANK, Sort.DESCENDING);
        //RealmResults<EventDAO> results = query.findAll().sort(RealmTable.Report.ID, Sort.DESCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
            list.add(oDTO);
        }

        realm.close();

        return list;
    }

    public List<ObjectDTO> sortByRankAsc() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ObjectEntity> query = realm.where(ObjectEntity.class)
                .equalTo(RealmTable.ObjectDAO.REMOVED, Removed.NOT_REMOVED.getValue());
        RealmResults<ObjectEntity> results = query.findAll().sort(RealmTable.ObjectDAO.RANK, Sort.ASCENDING);
        //RealmResults<EventDAO> results = query.findAll().sort(RealmTable.Report.ID, Sort.DESCENDING);

        List<ObjectDTO> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            ObjectDTO oDTO = ObjectAssembler.DAOtoDTO(results.get(i));
            list.add(oDTO);
        }

        realm.close();

        return list;
    }

}

