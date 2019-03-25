package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.view.activity.MainPanelActivity;
import com.example.dawid.visitwroclove.view.activity.SplashScreenActivity;

import dagger.Subcomponent;

@Subcomponent
@NonConfigurationScope
public interface NonConfigurationComponent {
    void inject(MainPanelActivity activity);
    void inject(SplashScreenActivity activity);
}
