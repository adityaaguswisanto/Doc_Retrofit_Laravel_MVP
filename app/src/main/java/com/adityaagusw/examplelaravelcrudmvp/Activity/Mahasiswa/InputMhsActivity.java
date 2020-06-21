package com.adityaagusw.examplelaravelcrudmvp.Activity.Mahasiswa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.adityaagusw.examplelaravelcrudmvp.R;
import com.adityaagusw.examplelaravelcrudmvp.Storage.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;

public class InputMhsActivity extends AppCompatActivity implements InputMhsView, View.OnClickListener {

    private ImageView ivFoto;
    private EditText edtNim, edtNama;
    private Button btnSubmit;
    private InputMhsPresenter presenter;
    private ProgressDialog progressDialog;

    private File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mhs);

        ivFoto = findViewById(R.id.ivFoto);
        edtNim = findViewById(R.id.edtNimMhs);
        edtNama = findViewById(R.id.edtNamaMhs);
        btnSubmit = findViewById(R.id.btnPostMhs);

        presenter = new InputMhsPresenter(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please waiting....");

        ivFoto.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        permission();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFoto:
                EasyImage.openChooserWithGallery(InputMhsActivity.this, "Pilih", 3);
                break;
            case R.id.btnPostMhs:

                MultipartBody.Part fotoPart = null;

                if (imgFile != null) {
                    File file = new File(String.valueOf(imgFile));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                    fotoPart = MultipartBody.Part.createFormData("foto", file.getName(), requestBody);
                }

                RequestBody nim = RequestBody.create(MediaType.parse("text/plain"), edtNim.getText().toString().trim());
                RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), edtNama.getText().toString().trim());

                presenter.postMahasiswa("Bearer " + SharedPrefManager.getInstance(this).getUser().getApi_token(), nim, nama, fotoPart);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        presenter.easyImage(requestCode, resultCode, data, this);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri uri = result.getUri();

                Glide.with(getApplicationContext())
                        .load(new File(Objects.requireNonNull(uri.getPath())))
                        .into(ivFoto);

                imgFile = new File(uri.getPath());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception exception = result.getError();
                Toast.makeText(this, "Error : " + exception.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onRequestAccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void permission(){
        if (ContextCompat.checkSelfPermission(InputMhsActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(InputMhsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Kasih Hak Aksess dulu cok", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(InputMhsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
}