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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private Button bConfirm;
    private EditText eEmail, ePassword, eName;

    private FirebaseAuth bAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    /** Die RegisterActivity checkt ob bereits ein User besteht und speichert, falls noch nicht
     * vorhanden, sowohl in die Authentifikator-Datenbank als auch in die Realtime Database,
     * diesen ein */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Datenbank auswählen
        mDatabase = FirebaseDatabase.getInstance("https://thrifter-c4a4d-default-rtdb.europe-west1.firebasedatabase.app/");
        //Datenbankreferenz auswählen
        mRef = mDatabase.getReference().child("Users");
        //Authentifikator initialisieren
        bAuth = FirebaseAuth.getInstance();
        //Checken ob bereits ein bestehender User existiert
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;

                }

            }

        };


        //Button und Edittexts im Layout finden
        bConfirm = (Button) findViewById(R.id.confirm);
        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);
        eName = (EditText) findViewById(R.id.name);


        //Registrieren
        bConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String email = eEmail.getText().toString();
                final String password = ePassword.getText().toString();
                final String name = eName.getText().toString();

                //Account erstellen mit Email und Passwort. Die Daten kommen aus den EditTexts
                bAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this, "Fehler!", Toast.LENGTH_SHORT).show();

                        }
                        //Wenn erfolgreich, dann Accountid einspeichern in die Realtime Datenbank
                        else{

                            String currentID = bAuth.getCurrentUser().getUid();

                            mRef.child(currentID).setValue(currentID);

                        }

                    }

                });


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