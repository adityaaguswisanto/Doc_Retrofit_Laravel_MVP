package com.adityaagusw.examplelaravelcrudmvp.Activity.Mahasiswa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.adityaagusw.examplelaravelcrudmvp.Activity.Note.InputNoteView;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiClient;
import com.adityaagusw.examplelaravelcrudmvp.Api.ApiInterface;
import com.adityaagusw.examplelaravelcrudmvp.Model.Mahasiswa;
import com.adityaagusw.examplelaravelcrudmvp.Model.MahasiswaResponse;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputMhsPresenter {

    private InputMhsView view;
    private ApiInterface apiInterface;

    public InputMhsPresenter(InputMhsView view) {
        this.view = view;
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    void postMahasiswa(String token, RequestBody nim, RequestBody nama, MultipartBody.Part foto){
        view.showProgress();
        Call<MahasiswaResponse<Mahasiswa>> call = apiInterface.createMahasiswa(token, nim, nama, foto);

        call.enqueue(new Callback<MahasiswaResponse<Mahasiswa>>() {
            @Override
            public void onResponse(@NonNull Call<MahasiswaResponse<Mahasiswa>> call, @NonNull Response<MahasiswaResponse<Mahasiswa>> response) {

                view.hideProgress();

                if (response.isSuccessful()){

                    MahasiswaResponse<Mahasiswa> mahasiswa = response.body();

                    assert mahasiswa != null;
                    if (!mahasiswa.isValue()){
                        view.onRequestAccess(mahasiswa.getMessage());
                    }else{
                        view.onRequestError(mahasiswa.getMessage());
                    }

                }else{
                    view.onRequestError("Terjadi Kesalahan");
                }

            }

            @Override
            public void onFailure(@NonNull Call<MahasiswaResponse<Mahasiswa>> call,@NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getMessage());
            }
        });
    }

    void easyImage(int requestCode, int resultCode, Intent data, Context context){
        EasyImage.handleActivityResult(requestCode, resultCode, data, (Activity) context, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                CropImage.activity(Uri.fromFile(imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setFixAspectRatio(true)
                        .start((Activity) context);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                view.onRequestError("Error : " + e.getMessage());
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                view.onRequestError("Tidak Jadi");
            }
        });

    }


}
