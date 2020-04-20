package com.greatlove.musicplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.greatlove.musicplay.Preference;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;

public class ProviderSelection extends AppCompatActivity {

    CardView btn_user, btn_admin;
    private FirebaseAuth auth;
    public static ProviderSelection activity;
    boolean isLogin;
    String lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_selection);

        activity = ProviderSelection.this;
        isLogin = AppMethods.getBooleanPreference(ProviderSelection.this,Constants.IsLogin);
        lName = AppMethods.getStringPreference(ProviderSelection.this,Constants.lName);

        btn_admin = findViewById(R.id.btn_admin);
        btn_user = findViewById(R.id.btn_user);
        auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            if (Preference.getUser_type().equalsIgnoreCase("1")) {
//                Intent intent = new Intent(ProviderSelection.this, AdminActivity.class);
//                startActivity(intent);
//                finish();
//            } else if (Preference.getUser_type().equalsIgnoreCase("2")) {
//                Intent intent = new Intent(ProviderSelection.this, FolderSelectActivity.class);
//                startActivity(intent);
//            }
//        }

        if (lName.equals("User") && isLogin)
        {
            Intent intent = new Intent(ProviderSelection.this, AdminActivity.class);
                startActivity(intent);
        } else if (lName.equals("Admin") && isLogin)
        {
            Intent intent = new Intent(ProviderSelection.this, FolderSelectActivity.class);
                startActivity(intent);
        }


        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                com.greatlove.musicplay.Preference.setUser_type("1");
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);

            }
        });

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                com.greatlove.musicplay.Preference.setUser_type("2");
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
