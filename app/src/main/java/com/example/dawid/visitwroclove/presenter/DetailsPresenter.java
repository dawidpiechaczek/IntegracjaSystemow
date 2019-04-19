package com.example.dawid.visitwroclove.presenter;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.utils.Constants;
import com.example.dawid.visitwroclove.view.activity.DetailsView;

public class DetailsPresenter extends BasePresenter<DetailsView> {
    private ObjectDAOImpl mRepoObjects;
    private EventDAOImpl mRepoEvents;
    private String activityType;
    private BaseDTO baseDTO;

    public DetailsPresenter(EventDAOImpl mRepoEvents, ObjectDAOImpl mRepoObjects) {
        this.mRepoObjects = mRepoObjects;
        this.mRepoEvents = mRepoEvents;
    }

    public void setLinearLayoutVisibility(String activityType, int itemId) {
        this.activityType = activityType;
        if (activityType.equals(Constants.ACTIVITY_VALUE_EVENT)) {
            baseDTO = mRepoEvents.getById(itemId);
            getView().setVisibility(true);
        } else {
            baseDTO = mRepoObjects.getById(itemId);
            getView().setVisibility(false);
        }
    }

    public String getActivityType() {
        return activityType;
    }

    public void addNewRate(double rate){
        baseDTO.setRank(rate);
        if (activityType.equals(Constants.ACTIVITY_VALUE_EVENT)) {
            mRepoEvents.add((EventDTO) baseDTO);
        } else {
            mRepoObjects.add((ObjectDTO) baseDTO);
        }
    }

    public void setFavourite(int isFavourite) {
        baseDTO.setFavourite(isFavourite);
        if (activityType.equals(Constants.ACTIVITY_VALUE_EVENT)) {
            mRepoEvents.add((EventDTO) baseDTO);
        } else {
            mRepoObjects.add((ObjectDTO) baseDTO);
        }
        getView().setFavourite(isFavourite);
    }

    public BaseDTO getBaseDTO(){
        return baseDTO;
    }
}

