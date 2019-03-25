package com.example.dawid.visitwroclove.database.utils;

public interface RealmTable {

    String ID = "id";

    interface ObjectDAO {
        String NAME = "name";
        String TYPE = "type";
        String RECOMMENDED = "recommended";
        String RANK = "rank";
        String REMOVED = "removed";
        String STATUS = "status";
        String FAVOURITE = "isFavourite";

        interface AddressDAO {
            String CITY = "address.city";
        }

    }

    interface EventDAO {
        String NAME = "name";
        String START_DATE = "date";
        String REMOVED = "removed";
        String STATUS = "status";
        String FAVOURITE = "isFavourite";
    }

    interface RouteDAO {
        String NAME = "name";
        String TYPE = "type";
        String REMOVED = "removed";
        String IS_MINE = "isMine";
    }
}