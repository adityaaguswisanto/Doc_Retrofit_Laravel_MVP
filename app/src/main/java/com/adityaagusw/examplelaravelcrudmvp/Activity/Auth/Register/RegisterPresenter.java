package com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Register;

import android.content.Context;

import androidx.annotation.NonNull;

import com.adityaagusw.examplelaravelcrudmvp.Activity.Auth.Login.LoginView;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiClient;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiInterface;
import com.adityaagusw.examplelaravelcrudmvp.Model.User;
import com.adityaagusw.examplelaravelcrudmvp.Model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {

    private RegisterView view;
    private ApiInterface apiInterface;
    private Context context;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    void registerUser(String name, String email, String password) {

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            view.onRequestError("Field Is Empty");
        } else {
            view.showProgress();

            Call<UserResponse<User>> call = apiInterface.registUser(name, email, password);
            call.enqueue(new Callback<UserResponse<User>>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse<User>> call, @NonNull Response<UserResponse<User>> response) {
                    view.hideProgress();
                    if (response.isSuccessful()) {
                        UserResponse<User> userUserResponse = response.body();

                        assert userUserResponse != null;
                        if (!userUserResponse.isValue()) {
                            view.onRequestAccess(userUserResponse.getMessage());
                        } else {
                            view.onRequestError(userUserResponse.getMessage());
                        }

                    } else {
                        view.onRequestError("Is Wrong...!");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<UserResponse<User>> call, @NonNull Throwable t) {
                    view.hideProgress();
                    view.onRequestError(t.getMessage());
                }
            });
        }

    }
}
