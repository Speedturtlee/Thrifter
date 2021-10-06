package com.example.thrifter.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thrifter.MainActivity;
import com.example.thrifter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    RecyclerView recyclerView;
    ItemCardAdapter itemAdapter;
    List<ListItem> listItemList;
    private Button bAdd, menuButton;

    /** */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        //Initialisieren des Authentifikators
        mAuth = FirebaseAuth.getInstance();
        //Eigene ID in String zum späteren Abgleich
        String userId = mAuth.getCurrentUser().getUid();
        //Ursprungsdatenbank
        mDatabase = FirebaseDatabase.getInstance("https://thrifter-c4a4d-default-rtdb.europe-west1.firebasedatabase.app/");
        //eigene Item Datenbank
        mRef = mDatabase.getReference().child("Users").child(userId);
        //Ursprungsbilderstorage
        mStorage=FirebaseStorage.getInstance();

        //Recyclerview erstellen bzw initialisieren
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Adapter und Karten erstellen und verknüpfen mit Recyclerview
        listItemList = new ArrayList<ListItem>();
        itemAdapter = new ItemCardAdapter(ListActivity.this, listItemList);
        recyclerView.setAdapter(itemAdapter);

        //Auslesen der Datenbank mit den dazugehörigen Items
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                //Daten aus Datenbank in Kartenelement übertragen
                ListItem listItem = snapshot.getValue(ListItem.class);
                //Karte der Liste hinzufügen
                listItemList.add(listItem);
                //Update
                itemAdapter.notifyDataSetChanged();

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
        menuButton = (Button) findViewById(R.id.returnButtonList);
        bAdd = (Button) findViewById(R.id.addB);


        //Ein Angebot erstellen per UploadActivity
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ListActivity.this, UploadActivity.class);
                startActivity(intent);
                return;

            }

        });


        //Zurück zur MainActivity
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                return;

            }

        });

    }

}