package com.greatlove.musicplay.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.adapter.GridSpacingItemDecoration;
import com.greatlove.musicplay.model.Current;
import com.greatlove.musicplay.model.CurrentSong;
import com.greatlove.musicplay.model.UploadSong;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowSongsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    List<UploadSong> mUpload;
    SongsAdapter adapter;
    DatabaseReference databaseReference;
    private ArrayList<String> foldername = new ArrayList<>();
    MediaPlayer mediaPlayer;
    ValueEventListener valueEventListener;
    ValueEventListener vv;
    String playingCat;
    private ImageView ic_back;
    String adminEmail,adminData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        adminEmail = AppMethods.getStringPreference(ShowSongsActivity.this, Constants.selectedAdmin);
        if (adminEmail.equals("Subway"))
        {
            adminData = "Subway";
        } else if (adminEmail.equals("Macdonals"))
        {
            adminData = "Macdonals";
        } else if (adminEmail.equals("Dominoz"))
        {
            adminData = "Dominoz";
        } else if (adminEmail.equals("Pizzahut"))
        {
            adminData = "Pizzahut";
        }



        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBarShowSongs);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));
        recyclerView.setLayoutManager(new GridLayoutManager(ShowSongsActivity.this, 2));
        mUpload = new ArrayList<>();

        adapter = new SongsAdapter(ShowSongsActivity.this, foldername,playingCat, "");
        recyclerView.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance().getReference(adminData);
        valueEventListener = databaseReference.child(getResources().getString(R.string.child_folder)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUpload.clear();

                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    foldername.add(dss.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShowSongsActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference(adminData);
        valueEventListener = databaseReference.child(getResources().getString(R.string.db_current)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Current current = dataSnapshot.getValue(Current.class);
                playingCat = current.catName;
                adapter = new SongsAdapter(ShowSongsActivity.this, foldername,playingCat, "");
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Current Playing ::::::",databaseError.toString());
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }

    public void playSong(List<UploadSong> arrayListSongs, int adapterPosition) throws IOException {
        UploadSong uploadSong = arrayListSongs.get(adapterPosition);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(uploadSong.getSongLink());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.prepareAsync();
    }

    @Override
    public void onBackPressed() {

       super.onBackPressed();
    }
}
