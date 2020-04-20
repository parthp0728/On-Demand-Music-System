package com.greatlove.musicplay.activity;

import android.media.MediaPlayer;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.greatlove.musicplay.adapter.AllSongsAdapter;
import com.greatlove.musicplay.adapter.GridSpacingItemDecoration;
import com.greatlove.musicplay.model.Current;
import com.greatlove.musicplay.model.CurrentSong;
import com.greatlove.musicplay.model.Songs;
import com.greatlove.musicplay.model.SongsPos;
import com.greatlove.musicplay.model.common;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowAllSongsActivity extends AppCompatActivity {

    private static final String TAG = "ShowAllSongsActivity";
    private ShowAllSongsActivity activity;
    TextView txtTitle;
    private ImageView iv_back,imgPlay,imgPause,imgNext,imgPrevious;
    LinearLayout llControls;
    private RecyclerView recyclerView;
    private AllSongsAdapter allSongsAdapter;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ArrayList<Songs> songs = new ArrayList<>();
    private ArrayList<Songs> sortedSongs = new ArrayList<>();
    private List<SongsPos> songsPos = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    String catName,adminEmail,adminData;
    int p;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_songs);

        activity = ShowAllSongsActivity.this;
        catName = getIntent().getStringExtra(common.catName);

        adminEmail = AppMethods.getStringPreference(ShowAllSongsActivity.this, Constants.AdminName);
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


        init();
        bind();


    }

    private void init() {

/*        if (mediaPlayer.isPlaying())
        {
            imgPlay.setVisibility(View.GONE);
            imgPause.setVisibility(View.VISIBLE);
        } else {
            imgPause.setVisibility(View.GONE);
            imgPlay.setVisibility(View.VISIBLE);
        }*/

        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        imgPlay = findViewById(R.id.imgPlay);
        imgPause = findViewById(R.id.imgPause);
        llControls = findViewById(R.id.llContorls);
        txtTitle = findViewById(R.id.txtTitle);

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if ((sortedSongs.size() - 1) > p)
                    {
                        playSong(sortedSongs.get(p+1),p+1);
                    } else {
                        Toast.makeText(activity, "No More Song", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                if (!mediaPlayer.isPlaying())
                {
                    mediaPlayer.start();
                }
            }
        });

        imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPlay.setVisibility(View.VISIBLE);
                imgPause.setVisibility(View.GONE);
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(adminData);
        mDatabaseReference.child(getResources().getString(R.string.child_songs)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                songs.clear();
                sortedSongs.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    songs.add(dataSnapshot1.getValue(Songs.class));
                }

                Collections.sort(songs, Collections.<Songs>reverseOrder());

                for (int i = 0;i < songs.size();i++)
                {
                    Songs ss = songs.get(i);
                    if (ss.getFolderNamer().equals(catName))
                    {
                        sortedSongs.add(ss);
                    }
                }

                allSongsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void bind() {

        iv_back = findViewById(R.id.ic_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.rcv_songs);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 8, true));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        allSongsAdapter = new AllSongsAdapter(activity, sortedSongs);
        recyclerView.setAdapter(allSongsAdapter);

    }

    public void playSong(final Songs songs, int position) throws IOException {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        p = position;

         name = songs.getName().toString();
         txtTitle.setText(name);


        if (mediaPlayer != null) {
            mediaPlayer.stop();
            Current current = new Current("");
            CurrentSong currentSong = new CurrentSong("");
            setData(current, currentSong);
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(songs.getUrl());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                mp.start();
                Current current = new Current(catName);
                CurrentSong currentSong = new CurrentSong(name);
                setData(current,currentSong);
                imgPause.setVisibility(View.VISIBLE);
                imgPlay.setVisibility(View.GONE);
                llControls.setVisibility(View.VISIBLE);
            }
        });
        mediaPlayer.prepareAsync();
    }

    private void setData(Current current, CurrentSong name) {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(adminData);
//        String userId=  mDatabaseReference.push().getKey();
//        mDatabaseReference.child(userId).setValue(current);
        mDatabaseReference.child(getResources().getString(R.string.db_current)).setValue(current);
        mDatabaseReference.child(getResources().getString(R.string.db_cSong)).setValue(name);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            if (mediaPlayer != null)
            mediaPlayer.stop();
    }
}
