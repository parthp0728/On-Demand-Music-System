package com.greatlove.musicplay.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.greatlove.musicplay.R;
import com.greatlove.musicplay.activity.AdminActivity;
import com.greatlove.musicplay.activity.FolderSelectActivity;
import com.greatlove.musicplay.activity.LoginActivity;
import com.greatlove.musicplay.activity.ShowSongsActivity;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

import java.util.ArrayList;

/**
 * Created by sagar on 9/29/2019.
 */
public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    Context context;
    ArrayList<String> adminName;

    public AdminAdapter(Context context, ArrayList<String> adminName)
    {
        this.context = context;
        this.adminName = adminName;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.txtAdmin.setText(adminName.get(position));

        holder.cvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowSongsActivity.class);
                intent.putExtra("admin",holder.txtAdmin.getText().toString());
                AppMethods.setStringPreference((Activity) context, Constants.selectedAdmin,holder.txtAdmin.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAdmin;
        CardView cvAdmin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAdmin = itemView.findViewById(R.id.txtAdminName);
            cvAdmin = itemView.findViewById(R.id.card_AdminName);
        }
    }
}
