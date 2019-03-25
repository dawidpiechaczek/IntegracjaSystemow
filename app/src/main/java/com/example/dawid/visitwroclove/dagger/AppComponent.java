package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.application.MyApplication;

import dagger.Component;

/**
 * Created by Dawid on 23.07.2017.
 */

@Component(modules = {AppModule.class, DataBaseModule.class})
@AppScope
public interface AppComponent {

    NonConfigurationComponent nonConfigurationComponent();

    void inject(MyApplication myApplication);

}
