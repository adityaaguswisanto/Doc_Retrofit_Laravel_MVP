package com.adityaagusw.examplelaravelcrudmvp.Activity.Lihat.Mahasiswa;

import com.adityaagusw.examplelaravelcrudmvp.Model.Mahasiswa;

import java.util.ArrayList;

public interface LihatMhsView {

    void loadingProgress(boolean state);

    void onGetResult(ArrayList<Mahasiswa> mahasiswa);

    void onErrorLoading(String message);

}
