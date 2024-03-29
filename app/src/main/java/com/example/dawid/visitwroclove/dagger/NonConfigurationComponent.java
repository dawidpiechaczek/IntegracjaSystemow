package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.presenter.RegistrationPresenter;
import com.example.dawid.visitwroclove.view.activity.DetailsActivity;
import com.example.dawid.visitwroclove.view.activity.EventsActivity;
import com.example.dawid.visitwroclove.view.activity.LoginActivity;
import com.example.dawid.visitwroclove.view.activity.MainPanelActivity;
import com.example.dawid.visitwroclove.view.activity.MapActivity;
import com.example.dawid.visitwroclove.view.activity.PlacesActivity;
import com.example.dawid.visitwroclove.view.activity.RegistrationActivity;
import com.example.dawid.visitwroclove.view.activity.RoutesListActivity;
import com.example.dawid.visitwroclove.view.activity.SplashScreenActivity;

import dagger.Subcomponent;

@Subcomponent
@NonConfigurationScope
public interface NonConfigurationComponent {
    void inject(MainPanelActivity activity);
    void inject(SplashScreenActivity activity);
    void inject(PlacesActivity activity);
    void inject(LoginActivity activity);
    void inject(EventsActivity activity);
    void inject(RegistrationActivity activity);
    void inject(DetailsActivity activity);
    void inject(MapActivity activity);
    void inject(RoutesListActivity activity);
}
