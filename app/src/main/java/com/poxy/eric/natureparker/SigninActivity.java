package com.poxy.eric.natureparker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.poxy.eric.natureparker.Networking.Badge;
import com.poxy.eric.natureparker.Networking.BadgeCallback;
import com.poxy.eric.natureparker.Networking.LoginCred;
import com.poxy.eric.natureparker.Networking.PoxyServer;
import com.poxy.eric.natureparker.Networking.UserState;

/**
 * This activity handles sign-in attempts.
 */

public class SigninActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private Button registerButton;
    private Button signInButton;
    private EditText txtEmail;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        connectFormElements();
    }

    /**
     * Attach the form functionality to the front end UI elements.
     */
    private void connectFormElements(){
        txtEmail = (EditText)findViewById(R.id.etEmail);
        txtPassword = (EditText)findViewById(R.id.etPassword);
        signInButton = (Button)findViewById(R.id.btnSignIn);
        registerButton = (Button)findViewById(R.id.btnRegister);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        /*imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent parkListintent = new Intent(getBaseContext(), ParkDetailActivity.class);
                startActivity(parkListintent);
            }
        });
        */
    }

    /**
     * This handles sign-in attempts.
     */
    private void signIn(){
        PoxyServer.login(new LoginCred(txtEmail.getText().toString(), txtPassword.getText().toString()), loginCallback());
    }

    /**
     * This function handles the login callback function sent to the sign-in function.
     * @return
     */
    private BadgeCallback loginCallback(){
        return new BadgeCallback() {
            @Override
            public void completion(boolean success, Badge badge) {
                if (success) {
                    UserState.setBadge(badge, getApplicationContext());
                    Intent mapIntent = new Intent(getBaseContext(), MapsActivity.class);
                    startActivity(mapIntent);
                    finish();
                }
                else{
                    Toast.makeText(getBaseContext(), "Login failed, please try again", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

}