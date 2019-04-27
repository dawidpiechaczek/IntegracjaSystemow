package com.example.dawid.visitwroclove.presenter;

import android.content.Context;
import android.util.Log;

import com.example.dawid.visitwroclove.model.LoggedUserDTO;
import com.example.dawid.visitwroclove.model.UserDTO;
import com.example.dawid.visitwroclove.service.VisitWroAPI;
import com.example.dawid.visitwroclove.view.activity.LoginView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    private VisitWroAPI visitWroAPI;

    public void init(Context context) {
        visitWroAPI = VisitWroAPI.Factory.create(context);
    }


    public void login(UserDTO user) {
        visitWroAPI.getToken(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoggedUserDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoggedUserDTO user) {
                        Log.d("LoginPresenter.onNext", "Token: " + user.getAccessToken());
                        getView().showLoadingScreen(user.getAccessToken(), user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("LoginPresenter.onNext", "Message: " + e.getMessage());
                        getView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
