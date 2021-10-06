package com.example.thrifter.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thrifter.MainActivity;
import com.example.thrifter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private Button bLogin, bRegister;
    private EditText eEmail, ePassword;
    private FirebaseAuth bAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    /** Die WelcomeActivity checkt ob ein bestehender User besteht bzw das Gerät noch einen
     * AuthentifikatorCode hat. Falls dieser vorhanden ist wird man direkt zur MainActivity
     * übergeleitet. Falls nicht kann man sich entweder einloggen oder einen neuen Account
     * erstellen */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //Initialisieren des Authentifikators
        bAuth = FirebaseAuth.getInstance();
        //Checken ob bereits ein bestehender User existiert, falls ja Auto-Login zur Mainactivity
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){

                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;

                }

            }

        };


        //Button und Edittexts im Layout finden
        bRegister = (Button) findViewById(R.id.registration);
        bLogin = (Button) findViewById(R.id.login);
        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);


        //Login
        bLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String email = eEmail.getText().toString();
                final String password = ePassword.getText().toString();

                //Abgleich mit der AuthentifikatorDatenbank und bei Datenabgleich -> einloggen
                bAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(WelcomeActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){

                            Toast.makeText(WelcomeActivity.this, "Fehler!", Toast.LENGTH_SHORT).show();

                        }

                    }

                });

            }

        });


        //Überleiten zur RegisterActivity um Account zu erstellen
        bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent1);
                return;

            }

        });

    }


    //Beim Start der Activity wird der Listener hinzugefügt um zu checken, ob bereits der User besteht
    @Override
    protected void onStart() {

        super.onStart();
        bAuth.addAuthStateListener(firebaseAuthStateListener);

    }


    //Beim Stop der Activity wird dieser wieder entfernt
    @Override
    protected void onStop() {

        super.onStop();
        bAuth.removeAuthStateListener(firebaseAuthStateListener);

    }

}