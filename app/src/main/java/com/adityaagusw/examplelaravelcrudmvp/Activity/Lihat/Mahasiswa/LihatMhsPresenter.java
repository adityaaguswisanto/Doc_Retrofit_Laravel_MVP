package com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Mahasiswa;

import androidx.annotation.NonNull;

import com.adityaagusw.examplelaravelcrudmvp.Api.ApiClient;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiInterface;
import com.adityaagusw.examplelaravelcrudmvp.Model.GetMhs;
import com.adityaagusw.examplelaravelcrudmvp.Model.Mahasiswa;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatMhsPresenter {

    private LihatMhsView view;
    private ApiInterface apiInterface;

    public LihatMhsPresenter(LihatMhsView view) {
        this.view = view;
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    void readMhs(String token){
        view.loadingProgress(true);

        Call<GetMhs<Mahasiswa>> call = apiInterface.getMahasiswa(token);
        call.enqueue(new Callback<GetMhs<Mahasiswa>>() {
            @Override
            public void onResponse(@NonNull Call<GetMhs<Mahasiswa>> call, @NonNull Response<GetMhs<Mahasiswa>> response) {
                view.loadingProgress(false);
                if (response.isSuccessful()){

                    GetMhs<Mahasiswa> getMhs = response.body();

                    assert getMhs != null;
                    if (!getMhs.isValue()){

                        assert response.body() != null;
                        ArrayList<Mahasiswa> list = response.body().getData();

                        if (list.isEmpty()){
                            view.onErrorLoading("Data Kosong !");
                        }else{
                            view.onGetResult(list);
                        }

                    }

                }else{
                    view.onErrorLoading("Terjadi Kesalahan");
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetMhs<Mahasiswa>> call,@NonNull Throwable t) {
                view.loadingProgress(false);
                view.onErrorLoading(t.getMessage());
            }
        });

    }

}
