package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.application.MyApplication;

import dagger.Component;

@Component(modules = {AppModule.class, DataBaseModule.class})
@AppScope
public interface AppComponent {

    NonConfigurationComponent nonConfigurationComponent();

    void inject(MyApplication myApplication);

}
