package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.enums.Categories;
import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.utils.Constants;
import com.example.dawid.visitwroclove.utils.FontManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dawid.visitwroclove.utils.Constants.BUS_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.EXTRA_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.WEATHER_WEB_VIEW;


public class MainPanelActivity extends BaseActivity {

    private String mLog = MainPanelActivity.class.getName();
    @BindView(R.id.tv_map)
    TextView mpa_tv_map;
    @BindView(R.id.tv_events)TextView mpa_tv_events;
    @BindView(R.id.tv_places)TextView mpa_tv_places;
    @BindView(R.id.tv_tracks)TextView mpa_tv_tracks;
    @BindView(R.id.tv_bus)TextView mpa_tv_buses;
    @BindView(R.id.tv_weather)TextView mpa_tv_weather;
    @Inject
    public ObjectDAOImpl mRepo;
    @Inject
    public EventDAOImpl mRepoEvent;
    @Inject
    public RouteDAOImpl mRepoRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        getComponent().inject(this);
        ButterKnife.bind(this);
        Log.d(mLog, "MainPanelActivity.onCreate()");
        setPermissions();
        setIcons();
        scripts();
    }

    private void setIcons() {
        mpa_tv_places.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_map.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_events.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_tracks.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_weather.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_buses.setTypeface(FontManager.getIcons(MainPanelActivity.this));
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(mLog, "MainPanelActivity.onResume()");
    }

    @OnClick(R.id.ll_places)
    public void showPlacesActivity() {
        Intent intent = new Intent(getApplicationContext(), PlacesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_events)
    public void showEventsActivity() {
     //   Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
      //  startActivity(intent);
    }

    private void changeLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        Intent refresh = new Intent(MainPanelActivity.this, MainPanelActivity.class);
        startActivity(refresh);
        finish();
    }
    private void scripts() {
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.setId(10);
        objectDTO.setFavourite(1);
        objectDTO.setName("Ratusz Wrocławski");
        objectDTO.setDescription("Budynek gdzie mieści się starostwo miasta Wrocław.");
        objectDTO.setPhone("42 455 34 43");
        objectDTO.setType("gastronomia");
        objectDTO.setAddressId(20);
        objectDTO.setImage("https://upload.wikimedia.org/wikipedia/commons/5/52/2017_Ratusz_Staromiejski_we_Wroc%C5%82awiu_01.jpg");
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(20);
        addressDTO.setCity("Wrocław");
        addressDTO.setStreet("Rynek");
        addressDTO.setHomeNumber("19");
        addressDTO.setLat("51.109678");
        addressDTO.setLng("17.031879");
        objectDTO.setAddress(addressDTO);
        mRepo.add(objectDTO);

        ObjectDTO objectDTO1 = new ObjectDTO();
        objectDTO1.setId(11);
        objectDTO1.setFavourite(1);
        objectDTO1.setName("Hala Stulecia");
        objectDTO1.setDescription("Hala Stulecia (inna funkcjonująca nazwa: Hala Ludowa), to duży obiekt widowiskowo-sportowy położony w Parku Szczytnickim we Wrocławiu. Wzniesiony w latach 1911–1913 według projektu architektonicznego Maxa Berga. W 2006 roku Hala została wpisana na Listę Światowego Dziedzictwa Kulturalnego i Przyrodniczego UNESCO! ");
        objectDTO1.setPhone("42 455 34 43");
        objectDTO1.setType("zwiedzanie");
        objectDTO1.setAddressId(22);
        objectDTO1.setImage("https://upload.wikimedia.org/wikipedia/commons/0/06/Wroc%C5%82aw_-_Jahrhunderthalle1.jpg");
        AddressDTO addressDTO1 = new AddressDTO();
        addressDTO1.setId(22);
        addressDTO1.setCity("Wrocław");
        addressDTO1.setZipCode("50-200");
        addressDTO1.setStreet("Biskupin");
        addressDTO1.setHomeNumber("14");
        addressDTO1.setLat("51.106512");
        addressDTO1.setLng("17.077010");
        objectDTO1.setAddress(addressDTO1);
        mRepo.add(objectDTO1);

        ObjectDTO objectDTO2 = new ObjectDTO();
        objectDTO2.setId(12);
        objectDTO2.setFavourite(1);
        objectDTO2.setName("ZOO");
        objectDTO2.setAddressId(23);
        objectDTO2.setDescription("Odbywają się w niej liczbne wydarzenia kulturalne.");
        objectDTO2.setPhone("42 455 34 43");
        objectDTO2.setType("zwiedzanie");
        objectDTO2.setImage("http://www.zoo.wroclaw.pl/zdc/wp-content/uploads/2016/09/logo-ZOO-WROCLAW.jpg");
        AddressDTO addressDTO2 = new AddressDTO();
        addressDTO2.setId(23);
        addressDTO2.setCity("Wrocław");
        addressDTO2.setStreet("Biskupin");
        addressDTO2.setHomeNumber("14");
        addressDTO2.setLat("51.107586");
        addressDTO2.setLng("17.078028");
        objectDTO2.setAddress(addressDTO2);
        mRepo.add(objectDTO2);

        ObjectDTO objectDTO3 = new ObjectDTO();
        objectDTO3.setId(13);
        objectDTO3.setFavourite(0);
        objectDTO3.setAddressId(24);
        objectDTO3.setName("Galeria Dominikańska");
        objectDTO3.setDescription("Odbywają się w niej liczbne wydarzenia kulturalne.");
        objectDTO3.setPhone("42 455 34 43");
        objectDTO3.setType("building");
        objectDTO3.setImage("http://galeria-dominikanska.pl/files/gallery/fotom-2196.jpg");
        AddressDTO addressDTO3 = new AddressDTO();
        addressDTO3.setId(24);
        addressDTO3.setCity("Wrocław");
        addressDTO3.setStreet("Biskupin");
        addressDTO3.setHomeNumber("14");
        addressDTO3.setLat("51.106171");
        addressDTO3.setLng("17.068014");
        objectDTO3.setAddress(addressDTO3);
        mRepo.add(objectDTO3);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(14);
        eventDTO.setFavourite(1);
        eventDTO.setAddressId(25);
        eventDTO.setName("Wyścigi konne");
        eventDTO.setDescription("Budynek gdzie mieści się starostwo miasta Wrocław.");
        eventDTO.setType("event");
        eventDTO.setImage("http://www.iceis.pl/wyscigi/konne/wyscigi-konne_partynice,400px.jpg");
        AddressDTO addressDTO4 = new AddressDTO();
        addressDTO4.setId(25);
        addressDTO4.setCity("Wrocław");
        addressDTO4.setStreet("Rynek");
        addressDTO4.setHomeNumber("19");
        addressDTO4.setLat("51.109678");
        addressDTO4.setLng("17.031879");
        eventDTO.setAddress(addressDTO4);
        mRepoEvent.add(eventDTO);

        PointDTO pointDTO = new PointDTO();
        pointDTO.setLat(addressDTO.getLat());
        pointDTO.setLng(addressDTO.getLng());
        pointDTO.setObjectId(10);
        pointDTO.setRouteId(0);
        PointDTO pointDTO1 = new PointDTO();
        pointDTO1.setLat(addressDTO1.getLat());
        pointDTO1.setLng(addressDTO1.getLng());
        pointDTO1.setObjectId(11);
        pointDTO1.setRouteId(0);
        PointDTO pointDTO2 = new PointDTO();
        pointDTO2.setLat(addressDTO2.getLat());
        pointDTO2.setLng(addressDTO2.getLng());
        pointDTO2.setObjectId(12);
        pointDTO2.setRouteId(0);
        PointDTO pointDTO3 = new PointDTO();
        pointDTO3.setLat(addressDTO3.getLat());
        pointDTO3.setLng(addressDTO3.getLng());
        pointDTO3.setObjectId(13);
        pointDTO3.setRouteId(1);

        List<PointDTO> list = new ArrayList<>();
        list.add(pointDTO);
        list.add(pointDTO1);
        list.add(pointDTO2);
        List<PointDTO>list1 = new ArrayList<>();
        list1.add(pointDTO3);
        list1.add(pointDTO);

        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setGlobalId(1);
        routeDTO.setName("Ciekawa trasa");
        routeDTO.setDescription("Opis bardzo ciekawej trasy");
        routeDTO.setLength(2.17);
        routeDTO.setAmount(3);
        routeDTO.setType(Categories.FOREST.getValue());
        routeDTO.setPoints(list);
        RouteDTO routeDTO1 = new RouteDTO();
        routeDTO1.setGlobalId(2);
        routeDTO1.setName("Spacerek");
        routeDTO1.setDescription("Idealna trasa na krótki spacerek");
        routeDTO1.setLength(4.56);
        routeDTO1.setAmount(2);
        routeDTO1.setType(Categories.WATER.getValue());
        routeDTO1.setPoints(list1);
        RouteDTO routeDTO2 = new RouteDTO();
        routeDTO2.setGlobalId(3);
        routeDTO2.setAmount(3);
        routeDTO2.setName("Moja trasa");
        routeDTO2.setDescription("Moja ulubiona trasa");
        routeDTO2.setLength(1.15);
        routeDTO2.setType(Categories.FAVOURITE.getValue());
        routeDTO2.setPoints(list);

        mRepoRoutes.add(routeDTO);
        mRepoRoutes.add(routeDTO1);
        mRepoRoutes.add(routeDTO2);
    }
}


