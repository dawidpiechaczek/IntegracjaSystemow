package com.example.dawid.visitwroclove.presenter;

import android.util.Log;

import com.example.dawid.visitwroclove.model.LoggedUserDTO;
import com.example.dawid.visitwroclove.model.RegistrationDTO;
import com.example.dawid.visitwroclove.model.Response;
import com.example.dawid.visitwroclove.model.UserDTO;
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

    final VisitWroAPI randomUserAPI = VisitWroAPI.Factory.create(getView().getContext());

    public void registration(final String email, final String passwordFirst, String passwordSecond) {
        if (!Validation.validate(email)) {
            getView().showError("Błędny adres email, przykładowy email: przyklad@gmail.com");
        } else if (passwordFirst.equals("") || passwordSecond.equals("")) {
            getView().showError("podaj hasła");
        } else if (!passwordFirst.equals(passwordSecond)) {
            getView().showError("podane hasła nie są takie same");
        } else {
            randomUserAPI.register(new RegistrationDTO(email, passwordFirst))
                    .flatMap(new Function<Response, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Response response) throws Exception {
                            return randomUserAPI.getToken(new UserDTO(email, passwordFirst));
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
                            LoggedUserDTO user = (LoggedUserDTO) value;
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
}
