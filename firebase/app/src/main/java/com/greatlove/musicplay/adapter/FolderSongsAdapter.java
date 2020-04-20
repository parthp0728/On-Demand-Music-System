package com.greatlove.musicplay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greatlove.musicplay.Preference;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.activity.FolderSongsActivity;
import com.greatlove.musicplay.model.AllSongs;

import java.io.IOException;
import java.util.ArrayList;

public class FolderSongsAdapter extends RecyclerView.Adapter<FolderSongsAdapter.ViewHolder> {

    Context context;
    ArrayList<AllSongs> allSongs = new ArrayList<>();
    String sTitle;

    public FolderSongsAdapter(FolderSongsActivity folderSongsActivity, ArrayList<AllSongs> allSongs, String sTitle) {

        this.allSongs = allSongs;
        this.context = folderSongsActivity;
        this.sTitle = sTitle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_songs, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (!TextUtils.isEmpty(sTitle))
        {
            if (sTitle.equals(allSongs.get(position).getSong_name()))
            {
                holder.sPlaying.setText("current playing");
            } else {
                holder.sPlaying.setVisibility(View.GONE);
            }
        } else {
            holder.sPlaying.setVisibility(View.GONE);
        }



        holder.songName.setText(allSongs.get(position).getSong_name());

        holder.length.setText(allSongs.get(position).getTime());
        String string = allSongs.get(position).getFav_by();
        if (string.contains(Preference.getUserId())) {
            holder.iv_favourite.setImageResource(R.drawable.favourite);
        } else {
            holder.iv_favourite.setImageResource(R.drawable.un_favourite);
        }

    }

    @Override
    public int getItemCount() {
        return allSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songName, length,sPlaying;
        ImageView iv_favourite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.songName);
            length = itemView.findViewById(R.id.length);
            iv_favourite = itemView.findViewById(R.id.iv_favourite);
            sPlaying = itemView.findViewById(R.id.sPlaying);

            iv_favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((FolderSongsActivity) context).favouriteSong(allSongs.get(getAdapterPosition()), getAdapterPosition());

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ((FolderSongsActivity) context).playSong(allSongs.get(getAdapterPosition()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }


    }
}
