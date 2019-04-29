package com.example.dawid.visitwroclove.view.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.MyWindowAdapter;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.presenter.MapPresenter;
import com.example.dawid.visitwroclove.utils.Constants;
import com.example.dawid.visitwroclove.utils.OnSaveFragmentCallback;
import com.example.dawid.visitwroclove.utils.OnSearchFragmentCallback;
import com.example.dawid.visitwroclove.utils.WindowListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dawid.visitwroclove.view.activity.MainPanelActivity.USER_ID;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapView, OnSaveFragmentCallback, OnSearchFragmentCallback, TextToSpeech.OnInitListener {
    @Inject
    ObjectDAOImpl mRepo;
    @Inject
    RouteDAOImpl mRepoRoute;
    @Inject
    EventDAOImpl mRepoEvents;
    @BindView(R.id.am_ll_container)
    ConstraintLayout container;
    @BindView(R.id.am_rv_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.am_btn_save)
    Button buttonSave;
    @BindView(R.id.am_btn_navigate)
    Button buttonNavigate;
    private MapPresenter presenter;
    private Map<Marker, Integer> markersId = new HashMap<>();
    public GoogleMap map;
    private int routeId = -1;
    private int objectId = -1;
    private String totalTime;
    private String objectType;
    private boolean ownRouteModeCreator;
    private boolean editedMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        getExtra();
        presenter = new MapPresenter(MapActivity.this, mRepo, mRepoEvents, mRepoRoute);
        presenter.initRepositories(routeId);
        presenter.setRecyclerView(recyclerView);
        initMap();
        presenter.init(this);
    }

    @Override
    public void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.detachView();
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        presenter.setObjectsOnMap();
        presenter.setEventsOnMap();
        presenter.setRouteOnMap();
        setMapListenersAndAdapters();
        setCameraPosition(-1);
        checkPermssions();
    }

    @OnClick(R.id.am_btn_navigate)
    public void onNavigateRoute() {
        StringBuilder waypoints = new StringBuilder();
        String startPoint = "";
        String destPoint = "";
        int inc = 0;
        for (PointDTO point : presenter.getRoute().getPoints()) {
            if (inc == 0) {
                startPoint = point.getLat() + "," + point.getLng();
            } else if (inc == presenter.getRoute().getPoints().size() - 1) {
                destPoint = point.getLat() + "," + point.getLng();
            } else {
                waypoints.append(point.getLat()).append(",").append(point.getLng());
            }

            inc++;
            if (inc < presenter.getRoute().getPoints().size()) {
                waypoints.append("%7C");
            }
        }
        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + startPoint + "&destination=" + destPoint + "&waypoints=" + waypoints.toString() + "&travelmode=driving&dir_action=navigate");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @OnClick(R.id.search_text_view)
    public void onSearchRoute() {
        showSearchDialog();
    }

    @OnClick(R.id.am_btn_save)
    public void onSaveRoute() {
        showSaveDialog();
    }

    private void showSearchDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("search_fragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        SearchDialogFragment newFragment = SearchDialogFragment.newInstance(this, presenter.getBaseDtoList());
        newFragment.show(ft, "search_fragment");
    }

    private void showSaveDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("save_fragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        SaveDialogFragment newFragment = SaveDialogFragment.newInstance(this, mRepoRoute.getById(routeId));
        newFragment.show(ft, "save_fragment");
    }

    private void checkPermssions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    private void setMapListenersAndAdapters() {
        MyWindowAdapter adapter = new MyWindowAdapter(MapActivity.this, mRepo, mRepoEvents, markersId);
        adapter.setCreatorMode(ownRouteModeCreator);
        WindowListener windowListener = new WindowListener(this, markersId);
        registerForContextMenu(container);
        map.setInfoWindowAdapter(adapter);
        map.setOnInfoWindowClickListener(windowListener);
        map.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                if (ownRouteModeCreator) {
                    openContextMenu(container);
                    presenter.setMarkerIdAndTag(markersId.get(marker), marker.getTag().toString());
                    marker.hideInfoWindow();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                presenter.addOwnRoute();
                return true;
            case R.id.delete:
                presenter.deleteFromRoute();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void addMarker(BaseDTO baseDTO, String tag) {
        LatLng latlng = new LatLng(Double.parseDouble(baseDTO.getAddress().getLat()), Double.parseDouble(baseDTO.getAddress().getLng()));
        Marker marker;
        if (tag.equals(getString(R.string.events))) {
            marker = map.addMarker(new MarkerOptions().position(latlng).title(baseDTO.getName()));
        } else {
            marker = map.addMarker(new MarkerOptions().position(latlng).title(baseDTO.getName()));
        }
        marker.setTag(tag);
        markersId.put(marker, baseDTO.getId());
    }

    @Override
    public void setCameraPosition(int position) {
        if (objectId != -1) {
            if (objectType.equals(Constants.ACTIVITY_VALUE_OBJECT)) {
                BaseDTO baseDTO = mRepo.getById(objectId);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(baseDTO.getAddress().getLat()), Double.parseDouble(baseDTO.getAddress().getLng())), 15));
            } else {
                BaseDTO baseDTO = mRepoEvents.getById(objectId);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(baseDTO.getAddress().getLat()), Double.parseDouble(baseDTO.getAddress().getLng())), 15));
            }
        }

        if (position != -1) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(presenter.getRoute().getPoints().get(position).getLat()), Double.parseDouble(presenter.getRoute().getPoints().get(position).getLng())), 15));
        }

        if (position == -1 && objectId == -1) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.109678, 17.031879), 15));
        }
    }

    @Override
    public void positiveRouteCallback(Direction direction) {
        if (direction.isOK()) {
            List<Leg> directionPositionList = direction.getRouteList().get(0).getLegList();
            int duration = 0;
            for (Leg leg : directionPositionList) {
                ArrayList<LatLng> latLngs = leg.getDirectionPoint();
                map.addPolyline(DirectionConverter.createPolyline(this, latLngs, 5, Color.RED));
                duration += Double.parseDouble(leg.getDistance().getValue());
            }
            totalTime = String.valueOf(duration / 1000.0);
        } else {
            Toast.makeText(this, getString(R.string.error_route), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void negativeRouteCallback() {
        Toast.makeText(this, getString(R.string.error_route), Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearPolylines() {
        map.clear();
    }

    @Override
    public void setButtonNavigateVisibility(boolean visible) {
        if (visible) {
            buttonNavigate.setVisibility(View.VISIBLE);
        } else {
            buttonNavigate.setVisibility(View.INVISIBLE);
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void setMarkerAdded(int markerId) {
        getKeyByValue(markersId, markerId).setIcon(BitmapDescriptorFactory.defaultMarker(210f));
    }

    @Override
    public void setMarkerRemoved(int markerId) {
        getKeyByValue(markersId, markerId).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    }

    @Override
    public void setButtonVisibility(boolean visible) {
        if ((visible && ownRouteModeCreator) || editedMode) {
            buttonSave.setVisibility(View.VISIBLE);
        } else {
            buttonSave.setVisibility(View.INVISIBLE);
        }
    }

    public void getExtra() {
        if (getIntent().getExtras() != null) {
            routeId = getIntent().getExtras().getInt("trasa", -1);
            if (getIntent().getExtras().getInt(Constants.ACTIVITY_VALUE_EVENT, -1) != -1) {
                objectType = Constants.ACTIVITY_VALUE_EVENT;
                objectId = getIntent().getExtras().getInt(Constants.ACTIVITY_VALUE_EVENT, -1);
            } else {
                objectType = Constants.ACTIVITY_VALUE_OBJECT;
                objectId = getIntent().getExtras().getInt(Constants.ACTIVITY_VALUE_OBJECT, -1);
            }
            ownRouteModeCreator = getIntent().getExtras().getBoolean("own_route_mode");
            if (!ownRouteModeCreator) {
                setButtonNavigateVisibility(true);
            }
            editedMode = getIntent().getExtras().getBoolean("edited_route");
            if (editedMode) {
                setButtonVisibility(true);
            }
        }
    }

    @Override
    public void onSave(String name, String description, String type) {
        presenter.saveRoute(routeId, name, description, totalTime, type, getSharedPreferences("token", MODE_PRIVATE ).getInt(USER_ID, 0));
        Toast.makeText(this, "Zapisano trasę", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInit(int i) {

    }

    @Override
    public void onSearch(String name) {
        BaseDTO baseDTO = presenter.getBaseDtoByName(name);
        if (baseDTO != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(baseDTO.getAddress().getLat()), Double.parseDouble(baseDTO.getAddress().getLng())), 15));
        } else {
            Toast.makeText(this, "Nie znaleziono", Toast.LENGTH_LONG).show();
        }
    }
}
