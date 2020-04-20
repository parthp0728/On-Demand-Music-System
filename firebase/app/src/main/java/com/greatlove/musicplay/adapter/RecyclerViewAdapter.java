package com.greatlove.musicplay.adapter;

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

import com.greatlove.musicplay.MainActivity;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.activity.FolderSelectActivity;
import com.greatlove.musicplay.model.common;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> foldername;

    public RecyclerViewAdapter(FolderSelectActivity folderSelectActivity, ArrayList<String> foldername) {

        this.context = folderSelectActivity;
        this.foldername = foldername;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textView.setText(foldername.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                common.folder_name = foldername.get(position).toString();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(common.catName,foldername.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return foldername.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_name);

            Resources resources = context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            itemView.getLayoutParams().height = displayMetrics.widthPixels / 2;

        }
    }
}
