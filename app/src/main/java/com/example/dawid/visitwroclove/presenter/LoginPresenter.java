package com.example.dawid.visitwroclove.presenter;

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

    final VisitWroAPI randomUserAPI = VisitWroAPI.Factory.create();

    public void login(UserDTO user) {
        randomUserAPI.getToken(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoggedUserDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoggedUserDTO user) {
                        Log.d("LoginPresenter.onNext", "Token: " + user.getAccessToken());
                        getView().showLoadingScreen();
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
