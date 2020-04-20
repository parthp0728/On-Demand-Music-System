package com.greatlove.musicplay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.adapter.AdminAdapter;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView rcv_admin;
    private ImageView ic_back,imgLogout;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> adminName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getInit();
        getData();
    }

    private void getData() {
        final ProgressDialog dialog = new ProgressDialog(AdminActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        dialog.show();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(getResources().getString(R.string.admin));
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adminName.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    adminName.add(dataSnapshot1.getValue(String.class));
                }
                rcv_admin.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                AdminAdapter adminAdapter = new AdminAdapter(AdminActivity.this,adminName);
                rcv_admin.setAdapter(adminAdapter);
                dialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });



    }

    private void getInit() {
        ic_back = findViewById(R.id.ic_back);
        rcv_admin = findViewById(R.id.rcv_admin);
        imgLogout = findViewById(R.id.imgLogout);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogoutDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                openLogoutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openLogoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit").setMessage("Are you sure you want to Logout.?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AppMethods.setBooleanPreference(AdminActivity.this, Constants.IsLogin,false);
                startActivity(new Intent(AdminActivity.this, ProviderSelection.class));

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
