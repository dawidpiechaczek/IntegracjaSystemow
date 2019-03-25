package com.example.dawid.visitwroclove.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 23.07.2017.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    Context provideAppContext(){
        return context;
    }
}
