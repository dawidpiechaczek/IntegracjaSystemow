package com.example.dawid.visitwroclove.presenter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.CarouselAdapter;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.ReviewDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.model.SendPointDTO;
import com.example.dawid.visitwroclove.model.SendRouteDTO;
import com.example.dawid.visitwroclove.service.VisitWroAPI;
import com.example.dawid.visitwroclove.view.activity.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapPresenter extends BasePresenter<MapView> {
    private ObjectDAOImpl repoObjects;
    private EventDAOImpl repoEvents;
    private RouteDAOImpl repoRoutes;
    private List<ObjectDTO> buildings;
    private List<EventDTO> events;
    private RouteDTO routeDTO;
    private Context context;
    private RecyclerView recyclerView;
    private int currentMarkerId;
    private String currentMarkerTag;
    private VisitWroAPI visitWroAPI;

    public MapPresenter(Context context, ObjectDAOImpl repoObjects, EventDAOImpl repoEvents, RouteDAOImpl repoRoutes) {
        this.repoObjects = repoObjects;
        this.repoEvents = repoEvents;
        this.repoRoutes = repoRoutes;
        this.context = context;
    }

    public void init(Context context){
        visitWroAPI = VisitWroAPI.Factory.create(context);
    }

    public void initRepositories(int routeId) {
        buildings = repoObjects.getAll();
        events = repoEvents.getAll();
        if (routeId != -1)
            routeDTO = repoRoutes.getById(routeId);
    }

    public void setObjectsOnMap() {
        for (ObjectDTO o : buildings) {
            getView().addMarker(o, context.getString(R.string.places));
        }
    }

    public void setEventsOnMap() {
        for (EventDTO e : events) {
            getView().addMarker(e, context.getString(R.string.events));
        }
    }

    public void setRouteOnMap() {
        if (routeDTO != null && routeDTO.getPoints().size() > 1) {
            List<LatLng> list = generateListOfLatLng();
            GoogleDirection.withServerKey("AIzaSyCd96x4S9MNuOSaH9-uKmnKgto3wc_qV4E")
                    .from(routeDTO.getLatLngsList().get(0))
                    .to(routeDTO.getLatLngsList().get(routeDTO.getLatLngsList().size() - 1))
                    .transitMode(TransportMode.WALKING)
                    .waypoints(list)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            getView().positiveRouteCallback(direction);
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            //  getView().negativeRouteCallback();
                        }
                    });

            setCarouselToMap(routeDTO.getPoints());
            addRecyclerSettings();
            getView().setButtonVisibility(true);
        } else {
            if (routeDTO != null && routeDTO.getPoints().size() >= 1) {
                getView().setButtonNavigateVisibility(true);
            } else {
                getView().setButtonNavigateVisibility(false);
                getView().setButtonVisibility(false);
            }
        }
    }

    public List<BaseDTO> getBaseDtoList() {
        List<BaseDTO> list = new ArrayList<>();
        list.addAll(repoObjects.getAll());
        list.addAll(repoEvents.getAll());
        return list;
    }

    public BaseDTO getBaseDtoByName(String name) {
        List<BaseDTO> list = new ArrayList<>();
        list.addAll(repoObjects.getAll());
        list.addAll(repoEvents.getAll());
        for (BaseDTO baseDTO : list) {
            if (baseDTO.getName().equals(name)) {
                return baseDTO;
            }
        }
        return null;
    }

    private List<LatLng> generateListOfLatLng() {
        if (routeDTO.getPoints().size() == 2) {
            return new ArrayList<>();
        } else {
            return routeDTO.getLatLngsList().subList(1, routeDTO.getLatLngsList().size() - 1);
        }
    }

    private void addRecyclerSettings() {
        setLayoutManager();
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());
    }

    private void setCarouselToMap(List points) {
        CarouselAdapter adapter = new CarouselAdapter(context, repoObjects, repoEvents);
        adapter.setData(points);
        adapter.setOnClickListener(new CarouselAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                getView().setCameraPosition(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManager() {
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setMaxVisibleItems(3);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void addOwnRoute() {
        PointDTO point = new PointDTO();
        point.setObjectId(currentMarkerId);
        BaseDTO baseDTO;
        if (currentMarkerTag.equals(context.getString(R.string.events))) {
            baseDTO = repoEvents.getById(currentMarkerId);
            point.setEvent(true);
        } else {
            baseDTO = repoObjects.getById(currentMarkerId);
        }
        getView().setMarkerAdded(currentMarkerId);
        point.setLat(baseDTO.getAddress().getLat());
        point.setLng(baseDTO.getAddress().getLng());
        point.setDescription(baseDTO.getDescription());
        //cannot duplicate objects in route
        if (baseDTO.getAddress().getLat().equals(point.getLat()) && baseDTO.getAddress().getLng().equals(point.getLng())) {
            List<PointDTO> list = generateList();
            list.add(point);
            routeDTO.setPoints(list);

            setRouteOnMap();
        }
    }

    public void deleteFromRoute() {
        BaseDTO baseDTO;
        getView().setMarkerRemoved(currentMarkerId);
        if (currentMarkerTag.equals(context.getString(R.string.events))) {
            baseDTO = repoEvents.getById(currentMarkerId);
        } else {
            baseDTO = repoObjects.getById(currentMarkerId);
        }

        Iterator<PointDTO> i = routeDTO.getPoints().iterator();
        while (i.hasNext()) {
            PointDTO pointDTO = i.next();
            if (baseDTO.getAddress().getLat().equals(pointDTO.getLat()) && baseDTO.getAddress().getLng().equals(pointDTO.getLng())) {
                i.remove();
            }
        }

        getView().clearPolylines();
        setEventsOnMap();
        setObjectsOnMap();
        setRouteOnMap();
    }

    private List<PointDTO> generateList() {
        if (routeDTO != null) {
            return routeDTO.getPoints();
        } else {
            routeDTO = new RouteDTO();
            return new ArrayList<>();
        }
    }

    public void saveRoute(final int routeId, final String name, final String description, final String time, final String type, final int userId) {
        visitWroAPI.sendRoute(new SendRouteDTO(0, description, name, true, false, Double.parseDouble(time), type,userId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendRouteDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SendRouteDTO sendRouteDTO) {
                        if(routeId!=-1){
                            routeDTO.setId(routeId);
                        }
                        routeDTO.setName(name);
                        routeDTO.setDescription(description);
                        routeDTO.setLength(Double.parseDouble(time));
                        routeDTO.setType(type);
                        routeDTO.setMine(false);
                        routeDTO.setAmount(routeDTO.getPoints().size());
                        int superRouteId = repoRoutes.add(routeDTO);

                        for(PointDTO pointDTO: routeDTO.getPoints()){
                            visitWroAPI.sendRoutePoints(new SendPointDTO(0, pointDTO.getObjectId(), superRouteId))
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // getView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setMarkerIdAndTag(int id, String tag) {
        currentMarkerId = id;
        currentMarkerTag = tag;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RouteDTO getRoute() {
        return routeDTO;
    }
}