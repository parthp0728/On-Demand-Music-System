package com.greatlove.musicplay;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.greatlove.musicplay.activity.ShowAllSongsActivity;
import com.greatlove.musicplay.model.AllSongs;
import com.greatlove.musicplay.model.Songs;
import com.greatlove.musicplay.model.common;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText editTextTitle;
    TextView textViewImage;
    ProgressBar progressBar;
    Uri audioUri;
    StorageReference mStorageRef;
    DatabaseReference referenceSongs;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    StorageTask mUploadTask;
    private String folderName;
    private String fileName;
    private String fileUrl;
    private String time;
    private String catName;
    String adminEmail,adminData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        folderName = common.folder_name;
        catName = getIntent().getStringExtra(common.catName);

        adminEmail = AppMethods.getStringPreference(MainActivity.this, Constants.AdminName);
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

        editTextTitle = (EditText) findViewById(R.id.songTitle);
        textViewImage = (TextView) findViewById(R.id.txtViewSongsFileSelected);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        referenceSongs = FirebaseDatabase.getInstance().getReference().child(adminData);
        mStorageRef = FirebaseStorage.getInstance().getReference().child(adminData);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowAllSongsActivity.class);
                intent.putExtra(common.catName,catName);
                startActivity(intent);
            }
        });


    }


    public void openAudioFile(View v) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(i, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null) {
            audioUri = data.getData();
            String fileName = getFileName(audioUri);

            Context context;
            MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, audioUri);
            long millis = mediaPlayer.getDuration();
            String duratiion = String.format("%d min : %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) millis),
                    TimeUnit.MILLISECONDS.toSeconds((long) millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    millis)));
            time = duratiion;
            textViewImage.setText(fileName);

        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }

        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        return result;
    }

    public void uploadAudioToFirebase(View v) {
        if (textViewImage.getText().toString().equals("No File Selected")) {
            Toast.makeText(getApplicationContext(), "Please Select song", Toast.LENGTH_LONG).show();
        } else {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(getApplicationContext(), "song upload is already in progress", Toast.LENGTH_LONG).show();
            } else {
                uploadFile();
            }
        }
    }

    private void uploadFile() {
        if (audioUri != null) {
            String durationTxt;
            Toast.makeText(getApplicationContext(), "Uploading please wait...", Toast.LENGTH_LONG).show();

            progressBar.setVisibility(View.VISIBLE);
            final StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(audioUri));
            int durationInMills = findSongDuration(audioUri);
            if (durationInMills == 0) {
                durationTxt = "NA";
            }

            durationTxt = getDurationFromMilli(durationInMills);
            final String finalDurationTxt = durationTxt;
            mUploadTask = storageReference.putFile(audioUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    fileUrl = uri.toString();
                                    Log.e("success", "onSuccess: " + fileUrl);

                                    if (fileUrl != null) {
                                        String songKey = referenceSongs.child(adminData).push().getKey();
                                        Songs songs = new Songs();
                                        songs.setName(textViewImage.getText().toString());
                                        songs.setUrl(fileUrl);
                                        songs.setLikes(0);
                                        songs.setTime(time);
                                        songs.setFolderNamer(folderName);

                                        referenceSongs.child(getResources().getString(R.string.child_songs)).child(songKey).setValue(songs);

                                        AllSongs allSongs = new AllSongs();
                                        allSongs.setDiscription("null");
                                        allSongs.setFav("null");
                                        allSongs.setFav_by("null");
                                        allSongs.setSong_name(textViewImage.getText().toString());
                                        allSongs.setUrl(fileUrl);
                                        allSongs.setTime(time);
                                        allSongs.setCategoty(catName);
                                        referenceSongs.child(folderName).child(songKey).setValue(allSongs);

                                        Toast.makeText(getApplicationContext(), "Upload file successfull", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "No file Selected For Upload", Toast.LENGTH_LONG).show();
        }
    }

    private String getDurationFromMilli(int durationInMills) {
        Date date = new Date(durationInMills);
        SimpleDateFormat simple = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String myTime = simple.format(date);
        return myTime;
    }

    private int findSongDuration(Uri audioUri) {
        int timeInMillisec = 0;

        try {
            MediaMetadataRetriever retriver = new MediaMetadataRetriever();
            retriver.setDataSource(this, audioUri);
            String time = retriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            timeInMillisec = Integer.parseInt(time);

            retriver.release();
            return timeInMillisec;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getFileExtension(Uri audioUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(audioUri));
    }

    public void openSongsActivity(View v) {
//        Intent i = new Intent(MainActivity.this, ShowSongsActivity.class);
//        startActivity(i);
    }
}
