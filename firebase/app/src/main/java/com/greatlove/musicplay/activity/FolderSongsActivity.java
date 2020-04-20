package com.greatlove.musicplay.activity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greatlove.musicplay.Preference;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.adapter.FolderSongsAdapter;
import com.greatlove.musicplay.adapter.GridSpacingItemDecoration;
import com.greatlove.musicplay.model.AllSongs;
import com.greatlove.musicplay.model.CurrentSong;
import com.greatlove.musicplay.model.Songs;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

public class FolderSongsActivity extends AppCompatActivity {

    private static final String TAG = "FolderSongsActivity";
    private FolderSongsActivity activity;
    private String selectFolder,sTitle;
    private TextView folderName;
    private ImageView iv_back;
    private RecyclerView rcv_songs;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ArrayList<AllSongs> allSongs = new ArrayList<>();
    private ArrayList<String> allSongsKey = new ArrayList<>();
    private FolderSongsAdapter folderSongsAdapter;
    private MediaPlayer mediaPlayer;
    private ValueEventListener listener;
    private TextView text_noSongs,txtTitle;
    String adminEmail,adminData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_songs);

        activity = FolderSongsActivity.this;
        selectFolder = getIntent().getStringExtra("FolderName");

        adminEmail = AppMethods.getStringPreference(FolderSongsActivity.this, Constants.selectedAdmin);
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

        bind();
        init();

        Log.e(TAG, "onCreate: " + selectFolder);

    }

    private void init() {

        final ProgressDialog dialog = new ProgressDialog(FolderSongsActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(adminData);
        mDatabaseReference.child(selectFolder).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allSongs.clear();
                allSongsKey.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    allSongsKey.add(dataSnapshot1.getKey());
                    allSongs.add(dataSnapshot1.getValue(AllSongs.class));
                }

                if (allSongs.size() > 0) {
                    rcv_songs.setVisibility(View.VISIBLE);
                    text_noSongs.setVisibility(View.GONE);
                } else {
                    text_noSongs.setVisibility(View.VISIBLE);
                    rcv_songs.setVisibility(View.GONE);
                }

//                folderSongsAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(adminData);
        listener = mDatabaseReference.child(getResources().getString(R.string.db_cSong)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CurrentSong currentS = dataSnapshot.getValue(CurrentSong.class);
                    sTitle = currentS.sTitle;
                folderSongsAdapter = new FolderSongsAdapter(FolderSongsActivity.this, allSongs,sTitle);
                rcv_songs.setAdapter(folderSongsAdapter);

                folderSongsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Current Playing ::::::",databaseError.toString());
            }
        });


    }

    private void bind() {

        text_noSongs = findViewById(R.id.text_noSongs);

        folderName = findViewById(R.id.folderName);
        folderName.setText(selectFolder);

        iv_back = findViewById(R.id.ic_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rcv_songs = findViewById(R.id.rcv_songs);
        rcv_songs.addItemDecoration(new GridSpacingItemDecoration(1, 8, true));
        rcv_songs.setLayoutManager(new GridLayoutManager(this, 1));


    }

    public void playSong(final AllSongs songs) throws IOException {

        final ProgressDialog dialog = new ProgressDialog(FolderSongsActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(songs.getUrl());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                mp.start();
                folderName.setText(songs.getSong_name());

            }
        });
        mediaPlayer.prepareAsync();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void favouriteSong(AllSongs allSongs, final int adapterPosition) {


        AllSongs allSongs1 = new AllSongs();
        allSongs1.setUrl(allSongs.getUrl());
        allSongs1.setDiscription(allSongs.getDiscription());
        allSongs1.setFav(allSongs.getFav());
        allSongs1.setSong_name(allSongs.getSong_name());

        String fav_by = allSongs.getFav_by();
        Log.e(TAG, "favouriteSong: " + fav_by);
        String fav_by1 = null;
        if (fav_by.equals("")) {
            fav_by1 = fav_by.replace("", Preference.getUserId());
        } else if (fav_by.equals("null")) {
            fav_by1 = fav_by.replace("null", Preference.getUserId());
        } else if (fav_by.contains(Preference.getUserId())) {
            if (fav_by.contains("," + Preference.getUserId())) {
                fav_by1 = fav_by.replace("," + Preference.getUserId(), "");
            } else {
                fav_by1 = fav_by.replace(Preference.getUserId(), "");
            }
        } else {
            fav_by1 = fav_by + "," + Preference.getUserId();
        }
        allSongs1.setFav_by(fav_by1);

        mDatabaseReference.child(selectFolder).child(allSongsKey.get(adapterPosition)).setValue(allSongs1);
        final String finalFav_by = fav_by1;
        final Songs songs1 = new Songs();

        mDatabaseReference.child(getResources().getString(R.string.child_songs)).child(allSongsKey.get(adapterPosition)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Songs songs = dataSnapshot.getValue(Songs.class);
                int likes = songs.getLikes();

                if (finalFav_by.contains(Preference.getUserId())) {
                    likes = likes + 1;
                } else {
                    likes = likes - 1;
                }

                songs1.setUrl(songs.getUrl());
                songs1.setName(songs.getName());
                songs1.setLikes(likes);
                songs1.setFolderNamer(songs.getFolderNamer());

                mDatabaseReference.child(getResources().getString(R.string.child_songs)).child(allSongsKey.get(adapterPosition)).setValue(songs1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
