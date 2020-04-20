package com.greatlove.musicplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.greatlove.musicplay.MainActivity;
import com.greatlove.musicplay.Preference;
import com.greatlove.musicplay.R;
import com.greatlove.musicplay.utils.AppMethods;
import com.greatlove.musicplay.utils.Constants;


public class LoginActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup;
    TextView btnReset;
    CardView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
       /* if (Preference.getUser_type().equalsIgnoreCase("2")) {
            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }*/

        // set the view now
        setContentView(R.layout.activity_login);


        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);

        if (Preference.getUser_type().equalsIgnoreCase("2")) {

            btnReset.setVisibility(View.GONE);
            btnSignup.setVisibility(View.GONE);

        }

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();



                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                Preference.setUserId(task.getResult().getUser().getUid());

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    if (Preference.getUser_type().equalsIgnoreCase("1")) {
                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                        AppMethods.setBooleanPreference(LoginActivity.this,Constants.IsLogin,true);
                                        AppMethods.setStringPreference(LoginActivity.this,Constants.lName,"User");

                                        if (!ProviderSelection.activity.isFinishing() && ProviderSelection.activity != null) {
                                            ProviderSelection.activity.finish();
                                        }
                                        finish();

                                    } else if (Preference.getUser_type().equalsIgnoreCase("2")) {
                                        Intent intent = new Intent(LoginActivity.this, FolderSelectActivity.class);
                                        startActivity(intent);

                                        AppMethods.setStringPreference(LoginActivity.this, Constants.AdminName,email);
                                        AppMethods.setBooleanPreference(LoginActivity.this,Constants.IsLogin,true);
                                        AppMethods.setStringPreference(LoginActivity.this,Constants.lName,"Admin");

                                        if (!ProviderSelection.activity.isFinishing() && ProviderSelection.activity != null) {
                                            ProviderSelection.activity.finish();
                                        }

                                        finish();
                                    }
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
