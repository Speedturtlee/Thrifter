package com.example.thrifter.Thrift;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.thrifter.List.ListItem;
import com.example.thrifter.Thrift.ThriftAdapter;
import com.example.thrifter.MainActivity;
import com.example.thrifter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class ThriftActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference otherUsersDB;
    private DatabaseReference myUsersDb;
    private DatabaseReference initUsersDb;

    private Button returnMenu;
    private Button likeButton;
    private Button dislikeButton;
    private RecyclerView recyclerThrift;

    private ThriftAdapter thriftAdapter;

    List<ThriftCards> rowItems;
    ArrayList<String> idListe;

    /** Die ThriftActivity hat die Aufgabe, einen zufälligen User auszuwählen und dessen Angebote
     * anzuzeigen. Drückt man auf Like wird dieser in der ChatActivity hinzugefügt und bei Dislike
     * wird dieser nicht mehr in der Zukunft angezeigt. Die Anzeige der Angebote ist über eine
     * Recyclerview */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrift);

        //Initialisieren des Authentifikators
        mAuth = FirebaseAuth.getInstance();
        //Eigene ID in String zum späteren Abgleich
        String currentUId = mAuth.getCurrentUser().getUid();
        //Ursprungsdatenbank
        initUsersDb = FirebaseDatabase.getInstance("https://thrifter-c4a4d-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users");
        //eigene Item Datenbank
        myUsersDb = initUsersDb.child(currentUId);
        //andere Datenbank
        otherUsersDB = initUsersDb.child(getOtherCards());

        //Recyclerview erstellen bzw initialisieren
        recyclerThrift = findViewById(R.id.thriftlist);
        recyclerThrift.setHasFixedSize(true);
        recyclerThrift.setLayoutManager(new LinearLayoutManager(this));

        //Adapter und Karten erstellen und verknüpfen mit Recyclerview
        rowItems = new ArrayList<ThriftCards>();
        thriftAdapter = new ThriftAdapter(this, rowItems);
        recyclerThrift.setAdapter(thriftAdapter);


        //Auslesen der Datenbank ---> eigentlich otherUsersDb
        myUsersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                //Daten aus Datenbank in Kartenelement übertragen
                ThriftCards thriftCards = snapshot.getValue(ThriftCards.class);
                //Karte der Liste hinzufügen
                rowItems.add(thriftCards);
                //Update
                thriftAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }

        });


        //Buttons initialisieren
        dislikeButton = (Button) findViewById(R.id.dislike_button);
        likeButton = (Button) findViewById(R.id.like_button);
        returnMenu = (Button) findViewById(R.id.returnMenuSwipe);


        //Button um in die MainActivity zurückzukommen
        returnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(ThriftActivity.this, MainActivity.class);
                startActivity(intent);
                return;

            }

        });

    }


    //Liest die Datenbank der User aus und speichert die IDs in einer Liste um somit auf die anderen
    //Datenbanken zugreifen zu können
    public String getOtherCards() {

        idListe = new ArrayList<String>();

        initUsersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                //Speichern des Datenbankeintrags in einen Stringwert
                String tempIds = snapshot.getValue(String.class);
                //Hinzufügen zur Liste
                idListe.add(tempIds);

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //einen zufälligen Eintrag aus der Liste als String wiedergeben
        int max = idListe.size();
        int random = new Random().nextInt(max);
        String id = idListe.get(random);

        return id;

    }

}