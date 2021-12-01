package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tiesr2confiance.tiers2confiance.CameraRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class CameraShowFragment extends AppCompatActivity {

    /**
     * Variables globales
     **/
    RecyclerView recycler;
    ImageView imageView;
    private CameraRecyclerAdapter adapter;

    ArrayList<String> mediasUri;

    /**
     * Variables Firebase
     **/
    private FirebaseFirestore db;
    private CollectionReference mediaList;
    private StorageReference fileStorage;

    /**
     * Méthode initUI pour lier les widgets au code
     **/
    private void initUI() {
        recycler = findViewById(R.id.recylcer);
        imageView = findViewById(R.id.imageView);
    }

    /**
     * 3 Méthode pour l'initialisation des composants Firebase
     **/
    public void initFirebase() {
        // Instance pour le storage des images
        fileStorage = FirebaseStorage.getInstance().getReference();
        // Instance pour Firestore
        db = FirebaseFirestore.getInstance();
        // Assignation de la collection
        mediaList = db.collection("mediaList");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_media);

        /** Appel des méthodes **/
        initUI();
        initFirebase();
        getDataFromFireBase();
        addDataToRecycler();
    }

    private void getDataFromFireBase(){
        DocumentReference docRef = db.collection("mediaList").document("zG1FooDMCLjGZMXNRZqO");
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
//                                mediasUri = documentSnapshot.getData();
                                Log.i("TAG", "onComplete: " + documentSnapshot.getData());
                            } else {
                                Log.i("TAG", "No such document");
                            }
                        } else {
                            Log.i("TAG", "get failed with ", task.getException());
                        }
                    }
                });

        // Méthode pour récupérer tous les documents de la collection mediaList
//        db.collection("mediaList")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.i("TAG", document.getId() + "=>" + document.getData());
//                            }
//                        } else {
//                            Log.d("TAG", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
    }



    private void addDataToRecycler(){
        adapter = new CameraRecyclerAdapter(this, mediasUri);
        recycler.setAdapter(adapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(mLayoutManager);
    }


}
