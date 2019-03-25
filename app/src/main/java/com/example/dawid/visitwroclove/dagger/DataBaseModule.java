package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.DAO.implementation.AddressDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 23.07.2017.
 */
@Module
public class DataBaseModule {
    @Provides
    @AppScope
    ObjectDAOImpl provideObjectDao(){
        return ObjectDAOImpl.getInstance();
    }

    @Provides
    @AppScope
    EventDAOImpl provideEventDao(){ return EventDAOImpl.getInstance(); }

    @Provides
    @AppScope
    RouteDAOImpl provideRouteDao(){ return RouteDAOImpl.getInstance(); }

    @Provides
    @AppScope
    AddressDAOImpl provideAddressDao(){ return AddressDAOImpl.getInstance(); }
}
