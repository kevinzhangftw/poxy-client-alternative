package com.poxy.eric.natureparker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.poxy.eric.natureparker.Networking.Badge;
import com.poxy.eric.natureparker.Networking.BadgeCallback;
import com.poxy.eric.natureparker.Networking.Cred;
import com.poxy.eric.natureparker.Networking.PoxyServer;
import com.poxy.eric.natureparker.Networking.UserState;

/**
 * Created by Jonathan on 9/3/2017.
 * This activity handles user registration and credential storage for future sign-in attempts.
 */

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtPasswordConfirm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectFormElements();
    }

    /**
     * Connect our form functionality to the UI elements
     */
    private void connectFormElements(){
        txtEmail = (EditText)findViewById(R.id.etEmail);
        txtPassword = (EditText)findViewById(R.id.etPassword);
        txtPasswordConfirm = (EditText)findViewById(R.id.etPasswordConfirm);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    /**
     * Preforms the registration function for the user by making a call to the server.
     */
    private void register(){
        //Create a new credential and attempt to register it.
        PoxyServer.register(new Cred(txtEmail.getText().toString(), txtPassword.getText().toString(), txtPasswordConfirm.getText().toString()),
                registrationCallback());
    }

    /**
     * This handles a registration attempt.
     * @return  a callback function, which will give a success or fail.
     */
    private BadgeCallback registrationCallback(){
        return new BadgeCallback() {
            @Override
            public void completion(boolean success, Badge badge) {
                if (success){
                    UserState.setBadge(badge, getApplicationContext());
                    startActivity(new Intent(getBaseContext(), MapsActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(getBaseContext(), "Registration failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
