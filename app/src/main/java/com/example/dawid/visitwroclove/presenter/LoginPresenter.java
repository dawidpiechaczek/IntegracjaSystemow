package com.example.dawid.visitwroclove.presenter;

import android.content.Context;
import android.util.Log;

import com.example.dawid.visitwroclove.model.LoggedUserDTO;
import com.example.dawid.visitwroclove.model.UserDTO;
import com.example.dawid.visitwroclove.model.UserInformationDTO;
import com.example.dawid.visitwroclove.service.VisitWroAPI;
import com.example.dawid.visitwroclove.view.activity.LoginView;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    private VisitWroAPI visitWroAPI;

    public void init(Context context) {
        visitWroAPI = VisitWroAPI.Factory.create(context);
    }


    public void login(UserDTO user) {
        visitWroAPI.getToken(user)
                .flatMap(new Function<LoggedUserDTO, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(LoggedUserDTO loggedUserDTO) throws Exception {
                        getView().saveTokenToSharedPrefs(loggedUserDTO.getAccessToken());
                        visitWroAPI = VisitWroAPI.Factory.createLogin(loggedUserDTO.getAccessToken());
                        return visitWroAPI.getUsersInformation(loggedUserDTO.getUserId());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        UserInformationDTO userInformationDTO = (UserInformationDTO) value;
                        String token = getView().getToken();
                        Log.d("LoginPresenter.onNext", "Token: " + token);
                        getView().savePremium(((UserInformationDTO) value).isPremium());
                        getView().saveEmail(((UserInformationDTO) value).getEmail());
                        getView().savePassword(((UserInformationDTO) value).getPassword());
                        getView().showLoadingScreen(token, userInformationDTO.getId());
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
