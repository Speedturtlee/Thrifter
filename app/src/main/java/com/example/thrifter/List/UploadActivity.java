package com.example.thrifter.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thrifter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    ImageView imageView;
    EditText editArticleName, editArticleDesc, editArticlePrice;
    Button confirmButton;
    private static final int GalleryCode = 1;
    Uri imageUrl=null;
    ProgressDialog progressDialog;

    /** Die UploadActivity erstellt Angebote und speichert auch das zugehörige Artikelbild in
     * einem Storage ab. Das Angebot wird dann in der Realtime Database abgespeichert mit der
     * dazugehörigen BildURL um in der Listactivity das Angebot angezeigt zu bekommen */

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView=findViewById(R.id.picture_select);
        editArticleName=findViewById(R.id.article_name);
        editArticleDesc=findViewById(R.id.article_description);
        editArticlePrice=findViewById(R.id.article_price);
        confirmButton=findViewById(R.id.upload_button);

        //Initialisieren des Authentifikators
        mAuth = FirebaseAuth.getInstance();
        //Eigene ID in String
        String userId = mAuth.getCurrentUser().getUid();
        //Ursprungsdatenbank
        mDatabase = FirebaseDatabase.getInstance("https://thrifter-c4a4d-default-rtdb.europe-west1.firebasedatabase.app/");
        //eigene Item Datenbank
        mRef = mDatabase.getReference().child("Users").child(userId);
        //Ursprungsbilderstorage
        mStorage=FirebaseStorage.getInstance();
        //Prograssdialog für das hochladen
        progressDialog = new ProgressDialog(this);


        //Hier wird der ImageSelecter aufgerufen um ein Foto auszuwählen
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GalleryCode);

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //Hier wird das Bild zur Vorschau in der Activity eingelesen und angezeigt
        if(requestCode==GalleryCode && resultCode == RESULT_OK) {

            imageUrl=data.getData();
            imageView.setImageURI(imageUrl);

        }

        //Button zum erstellen des Angebots
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String an = editArticleName.getText().toString().trim();
                String ad = editArticleDesc.getText().toString().trim();
                String ap = editArticlePrice.getText().toString().trim();
                //Nur wenn alles ausgefüllt ist findet das Hochladen statt
                if(!(an.isEmpty() && ad.isEmpty() && ap.isEmpty() && imageUrl != null)){

                    //Hier wird ein ProgressDialog erstellt
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();
                    //Hier wird der Path erstellt, in dem das Bild in den Storage gespeichert wird
                    StorageReference filePath = mStorage.getReference().child("uploads").child(imageUrl.getLastPathSegment());
                    filePath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Auslesen der BildURL per Task zum anschließenden abspeichern in der
                            //Datenbank
                            Task<Uri>downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {

                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    String t = task.getResult().toString();

                                    //Durch .push() wird das Item mit einem Zeitstempel versehen
                                    //und eingespeichert
                                    DatabaseReference newItem= mRef.push();
                                    //Hier werden die Daten des Items in die Datenbank gespeichert
                                    newItem.child("Name").setValue(an);
                                    newItem.child("Description").setValue(ad);
                                    newItem.child("Price").setValue(ap);
                                    newItem.child("Image").setValue(task.getResult().toString());
                                    //Beenden des Progressdialogs
                                    progressDialog.dismiss();
                                    //Nach Abschluss -> Rückkehr zur ListActivity
                                    Intent intent = new Intent(UploadActivity.this, ListActivity.class);
                                    startActivity(intent);

                                }

                            });

                        }

                    });

                }

            }

        });

    }

}