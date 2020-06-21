package com.adityaagusw.examplelaravelcrudmvp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaagusw.examplelaravelcrudmvp.Model.Note;
import com.adityaagusw.examplelaravelcrudmvp.R;

import java.util.ArrayList;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.MyViewHolder> {

    private ArrayList<Note> noteArrayList;
    private Context context;

    public AdapterNote(Context context, ArrayList<Note> noteArrayList) {
        this.context = context;
        this.noteArrayList = noteArrayList;
    }

    public void setAdapterNote(ArrayList<Note> noteArrayList){
        this.noteArrayList = noteArrayList;
        notifyDataSetChanged();
    }

    //add for pagination here___________________________
    public void add(Note note){
        noteArrayList.add(note);
        notifyItemInserted(noteArrayList.size());
    }

    public void addAll(ArrayList<Note> notes) {
        for (Note n: notes){
            add(n);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = noteArrayList.get(position);

        holder.txtTitle.setText(note.getTitle());
        holder.txtContent.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle, txtContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitleItem);
            txtContent = itemView.findViewById(R.id.txtContentItem);

        }
    }
}
