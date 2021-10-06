package com.example.thrifter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.thrifter.Chat.ChatActivity;
import com.example.thrifter.List.ListActivity;
import com.example.thrifter.Login.WelcomeActivity;
import com.example.thrifter.Thrift.ThriftActivity;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    private Button bSettings, bChat, bList, bLogout;
    private ImageView bExplore;

    private FirebaseAuth mAuth;

    /** Die MainActivity hat die Aufgabe in die verschiedenen Menüs bzw Activities
     *  überzuleiten */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialisieren des Authentifikators
        mAuth = FirebaseAuth.getInstance();


        //Buttons
        bSettings = (Button) findViewById(R.id.settings);
        bChat = (Button) findViewById(R.id.chat);
        bList = (Button) findViewById(R.id.list);
        bLogout = (Button) findViewById(R.id.logout_button);
        bExplore = (ImageView) findViewById(R.id.explore_Button);


        //Einstellungen (Bisher nur logout enthalten)
        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSettings();

            }
        });


        //Button zum überleiten zur ChatActivity
        bChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                return;

            }
        });


        //Button zum anzeigen der eigenen Angebotsliste
        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                return;

            }
        });


        //Button per Imageview zum überleiten für die Suche nach Kleidung
        bExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explore Page öffnen
                Intent intent = new Intent(MainActivity.this, ThriftActivity.class);
                startActivity(intent);
                return;

            }

        });

    }


    //Anzeige der Einstellungen per Dialog
    public void showSettings() {

        Dialog settingsDia = new Dialog(this);
        settingsDia.setContentView(R.layout.settings_dialog);
        settingsDia.show();

    }

    //Ausloggen
    public void logoutUser(View view) {

        mAuth.signOut();
        Intent intent = new Intent (MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
        return;


    }

}