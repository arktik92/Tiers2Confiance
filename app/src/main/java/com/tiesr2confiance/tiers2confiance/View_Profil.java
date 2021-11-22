package com.tiesr2confiance.tiers2confiance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class View_Profil extends AppCompatActivity {


    /***  Global Variables  ***/

    private static final String TAG = "View Profile";

    private TextView tvName;
    private ImageView ivProfil;


    /*** BDD ***/
    private FirebaseFirestore db;
    /**
     * ID Document
     **/
    private DocumentReference noteRef;
    /**
     * Collection
     **/
    private CollectionReference noteCollectionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profil);

        init();
    }


    public void init() {

        tvName = findViewById(R.id.tvProfilName);
        ivProfil = findViewById(R.id.ivProfil);

        /** BDD ***/
        db = FirebaseFirestore.getInstance();

        // NoteRef
        noteRef = db.document("notes/Test_Profil");

        // Add Data
        noteCollectionRef = db.collection("notes");


    }

/**
    public void display_profil_data() {

        noteCollectionRef.get();

                QuerySnapshot listeSnapshots;

                        String mname = "";

                        for (QueryDocumentSnapshot documentSnapshot : listeSnapshots) {

                            /** For Each document (Snapshoot) of the list **/

                          /**  ModelItem contentProfil = documentSnapshot.toObject(ModelItem.class);

                            contentProfil.setDocumentId(documentSnapshot.getId());


                            String documentId = contentProfil.getDocumentId();
                            String profilName = contentProfil.getName();

                            mname += documentId + "\n Name: " + profilName;


                        }

                        tvName.setText(mname);

    }**/

}