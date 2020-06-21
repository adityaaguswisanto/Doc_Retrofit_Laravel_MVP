package com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Login;

public interface LoginView {

    void showProgress();

    void hideProgress();

    void onRequestAccess(String message);

    void onRequestError(String message);

}
