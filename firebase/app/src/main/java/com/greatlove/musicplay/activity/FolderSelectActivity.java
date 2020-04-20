package com.greatlove.musicplay.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.adapter.RecyclerViewAdapter;
import com.greatlove.musicplay.model.AllSongs;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

import java.util.ArrayList;

public class FolderSelectActivity extends AppCompatActivity {

    private static final String TAG = "FolderSelectActivity";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> foldername = new ArrayList<>();
    private CardView btn_user, btn_show_all_songs;
    private String folder_name;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView rcv_folder;
    private ImageView ic_back;
    String adminName;
    ImageView imgLogout;
    String adminEmail,adminData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_select);

        adminEmail = AppMethods.getStringPreference(FolderSelectActivity.this, Constants.AdminName);
        if (adminEmail.equals(Constants.Subway))
        {
            adminData = "Subway";
        } else if (adminEmail.equals(Constants.Macdonals))
        {
            adminData = "Macdonals";
        } else if (adminEmail.equals(Constants.Dominoz))
        {
            adminData = "Dominoz";
        } else if (adminEmail.equals(Constants.Pizzahut))
        {
            adminData = "Pizzahut";
        }
        findView();
        init();




    }

    private void init() {

        final ProgressDialog dialog = new ProgressDialog(FolderSelectActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        dialog.show();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(adminData);

        mDatabaseReference.child(getResources().getString(R.string.child_folder)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foldername.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    foldername.add(dataSnapshot1.getValue(String.class));
                }
                dialog.dismiss();
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter(FolderSelectActivity.this, foldername);
        rcv_folder.setLayoutManager(new GridLayoutManager(this, 2));
        rcv_folder.setAdapter(recyclerViewAdapter);
    }

    private void findView() {

        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imgLogout = findViewById(R.id.imgLogout);

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogoutDialog();
            }
        });

        rcv_folder = findViewById(R.id.rcv_folder);

        btn_user = findViewById(R.id.btn_user);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FolderSelectActivity.this);
                builder.setTitle("Enter Folder Name");
                View viewInflated = LayoutInflater.from(FolderSelectActivity.this).inflate(R.layout.alert_dialog, null);
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        folder_name = input.getText().toString().trim();

                        ArrayList<AllSongs> allSongs = new ArrayList<>();

                        mDatabaseReference.child(getResources().getString(R.string.child_folder)).push().setValue(folder_name);

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });


    }


    private void openLogoutDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit").setMessage("Are you sure you want to Logout.?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AppMethods.setBooleanPreference(FolderSelectActivity.this, Constants.IsLogin,false);
                startActivity(new Intent(FolderSelectActivity.this, ProviderSelection.class));

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit").setMessage("Are you sure you want to exit.?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                finishAffinity();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
