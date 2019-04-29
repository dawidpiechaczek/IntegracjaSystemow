package com.example.dawid.visitwroclove.presenter;

import android.content.Context;
import android.util.Log;

import com.example.dawid.visitwroclove.model.LoggedUserDTO;
import com.example.dawid.visitwroclove.model.RegistrationDTO;
import com.example.dawid.visitwroclove.model.Response;
import com.example.dawid.visitwroclove.model.UserDTO;
import com.example.dawid.visitwroclove.model.UserInformationDTO;
import com.example.dawid.visitwroclove.service.VisitWroAPI;
import com.example.dawid.visitwroclove.utils.Validation;
import com.example.dawid.visitwroclove.view.activity.RegistrationView;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private VisitWroAPI randomUserAPI;

    public void init(Context context) {
        randomUserAPI = VisitWroAPI.Factory.create(context);
    }

    public void registration(final String email, final String passwordFirst, String passwordSecond) {
        if (!Validation.validate(email)) {
            getView().showError("Błędny adres email, przykładowy email: przyklad@gmail.com");
        } else if (passwordFirst.equals("") || passwordSecond.equals("")) {
            getView().showError("podaj hasła");
        } else if (!passwordFirst.equals(passwordSecond)) {
            getView().showError("podane hasła nie są takie same");
        } else {
            randomUserAPI.register(new RegistrationDTO(email, passwordFirst,false, 0))
                    .flatMap(new Function<Response, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Response response) throws Exception {
                            return randomUserAPI.getToken(new UserDTO(email, passwordFirst));
                        }
                    })
                    .flatMap(new Function<Object, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Object o) throws Exception {
                            LoggedUserDTO loggedUserDTO = (LoggedUserDTO) o;
                            getView().saveToken(((LoggedUserDTO) o).getAccessToken());
                            randomUserAPI = VisitWroAPI.Factory.createLogin(loggedUserDTO.getAccessToken());
                            return randomUserAPI.getUsersInformation(loggedUserDTO.getUserId());
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
                            getView().savePremium(((UserInformationDTO) value).isPremium());
                            getView().saveEmail(((UserInformationDTO) value).getEmail());
                            getView().savePassword(((UserInformationDTO) value).getPassword());
                            getView().saveId(((UserInformationDTO) value).getId());
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
}
