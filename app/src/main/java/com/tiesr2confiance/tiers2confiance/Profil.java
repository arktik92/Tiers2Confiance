package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profil extends AppCompatActivity {


    private static final String TAG = "users_test_profil";
    /*** *Clé pour chaque champs de la BDD ***/

    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_emails = "email";

    /*** Global Variable ***/


    private EditText myName, myCity, myEmail;
    /*** Begin - BDD ***/
    /*** Create BDD ***/
    private FirebaseFirestore db;
    /*** ID Document ***/
    private DocumentReference noteRef;
    /*** Collection reference ***/
    private CollectionReference noteCollectionRef;


    /*** end - BDD ***/


    private void init() {


        myName = findViewById(R.id.etName);
        myCity = findViewById(R.id.etCIty);
        myEmail = findViewById(R.id.etEmail);

        /* Init BDD */
        /*** BDD Connexion ***/
        db = FirebaseFirestore.getInstance();

        /*** db Location ***/
        noteRef = db.document("/notes/collection");

        /** Add Document into the collection with each different ID **/
        noteCollectionRef = db.collection("notes");

        /* End Init BDD */


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        init();

    }


    public void updateNote(View v) {
        String mName = myName.getText().toString().trim();
        String mCity = myCity.getText().toString().trim();
        String mEmail = myEmail.getText().toString().trim();

        ModelViewProfilItem content = new ModelViewProfilItem(mName, mCity, mEmail);



        noteCollectionRef.add(content);




/*
        ModelViewProfilItem contentProfil = new ModelViewProfilItem(mName);

         noteCollectionRef.add(contentProfil)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Profil.this, "OnSuccess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profil.this, "OnFailure", Toast.LENGTH_SHORT).show();

                    }
                });
*/

    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}