package com.example.dawid.visitwroclove.presenter;

import android.content.Context;
import android.util.Log;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.ReviewDTO;
import com.example.dawid.visitwroclove.service.VisitWroAPI;
import com.example.dawid.visitwroclove.utils.Constants;
import com.example.dawid.visitwroclove.view.activity.DetailsView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenter extends BasePresenter<DetailsView> {
    private ObjectDAOImpl mRepoObjects;
    private EventDAOImpl mRepoEvents;
    private String activityType;
    private BaseDTO baseDTO;
    private VisitWroAPI visitWroAPI;

    public void init(Context context) {
        visitWroAPI = VisitWroAPI.Factory.create(context);
    }

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

    public void addNewRate(int rate, int userId) {
        baseDTO.setRank(rate);
        visitWroAPI.sendReview(new ReviewDTO(rate, userId, baseDTO.getId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReviewDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReviewDTO user) {
                        // getView().showLoadingScreen(user.getAccessToken(), user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // getView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    public BaseDTO getBaseDTO() {
        return baseDTO;
    }
}

