package com.greatlove.musicplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greatlove.musicplay.R;
import com.greatlove.musicplay.activity.FolderSongsActivity;
import com.greatlove.musicplay.activity.ShowAllSongsActivity;
import com.greatlove.musicplay.model.Songs;

import java.io.IOException;
import java.util.ArrayList;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.ViewHolder> {

    Context context;
    ArrayList<Songs> songs;


    public AllSongsAdapter(ShowAllSongsActivity activity, ArrayList<Songs> songs) {

        this.context = activity;
        this.songs = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_songs, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.songName.setText(songs.get(position).getName());
            holder.likes.setText(String.valueOf(songs.get(position).getLikes()));
            holder.duration.setText(songs.get(position).getTime());
            holder.foldername1.setText(songs.get(position).getFolderNamer());


    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView songName;
        TextView likes;
        TextView duration;
        TextView foldername1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            duration = itemView.findViewById(R.id.duration);
            songName = itemView.findViewById(R.id.text);
            likes = itemView.findViewById(R.id.num_like);
            foldername1 = itemView.findViewById(R.id.foldername1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ((ShowAllSongsActivity) context).playSong(songs.get(getAdapterPosition()),getAdapterPosition());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}
