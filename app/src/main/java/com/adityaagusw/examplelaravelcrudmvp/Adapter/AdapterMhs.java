package com.adityaagusw.examplelaravelcrudmvp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaagusw.examplelaravelcrudmvp.Model.Mahasiswa;
import com.adityaagusw.examplelaravelcrudmvp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;

public class AdapterMhs extends RecyclerView.Adapter<AdapterMhs.MyViewHolder> {

    private Context context;
    private ArrayList<Mahasiswa> mahasiswaArrayList;

    public AdapterMhs(Context context, ArrayList<Mahasiswa> mahasiswaArrayList) {
        this.context = context;
        this.mahasiswaArrayList = mahasiswaArrayList;
    }

    public void setAdapterMhs(ArrayList<Mahasiswa> mahasiswaArrayList){
        this.mahasiswaArrayList = mahasiswaArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mhs, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mahasiswa mahasiswa = mahasiswaArrayList.get(position);
        String url = "http://192.168.137.1:8000/foto/" + mahasiswa.getFoto();

        Glide.with(holder.itemView)
                .load(url)
                .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.ivFoto);

        holder.txtNim.setText(mahasiswa.getNim());
        holder.txtNama.setText(mahasiswa.getNama());

    }

    @Override
    public int getItemCount() {
        return mahasiswaArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivFoto;
        private TextView txtNim, txtNama;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.ivFotoItem);
            txtNim = itemView.findViewById(R.id.txtNimItem);
            txtNama = itemView.findViewById(R.id.txtNamaItem);

        }
    }
}
