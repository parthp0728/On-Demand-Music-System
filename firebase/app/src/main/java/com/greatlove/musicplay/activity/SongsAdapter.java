package com.greatlove.musicplay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greatlove.musicplay.R;

import java.util.List;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsAdapterViewHolder> {

    Context context;
    List<String> folderName;
    String playingCat;
    String sTitle;

    public SongsAdapter(Context context, List<String> arrayListSongs, String playingCat, String sTitle) {
        this.context = context;
        this.folderName = arrayListSongs;
        this.playingCat = playingCat;
        this.sTitle = sTitle;
    }

    @NonNull
    @Override
    public SongsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, viewGroup, false);
        return new SongsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapterViewHolder holder, int i) {

        holder.text_name.setText(folderName.get(i).toString());

            if (folderName.get(i).equals(playingCat))
            {
                holder.text_playing.setVisibility(View.VISIBLE);
            } else {
                holder.text_playing.setVisibility(View.GONE);
            }

            if (playingCat == null ||playingCat.isEmpty() || playingCat.equals(""))
            {
                holder.text_playing.setVisibility(View.GONE);
            }



    }

    @Override
    public int getItemCount() {
        return folderName.size();
    }

    public class SongsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text_name,text_playing;

        public SongsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            Resources resources = context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            itemView.getLayoutParams().height = displayMetrics.widthPixels / 2;

            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_playing = itemView.findViewById(R.id.text_playing);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(context,FolderSongsActivity.class);
            intent.putExtra("FolderName", folderName.get(getAdapterPosition()));
            intent.putExtra("song",sTitle);
            context.startActivity(intent);
        }
    }
}