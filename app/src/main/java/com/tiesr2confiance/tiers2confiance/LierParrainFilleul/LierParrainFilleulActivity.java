package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.ViewProfil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LierParrainFilleulActivity extends AppCompatActivity {

    /** Variables globales **/
    private static final String TAG = "Lier Parrain Filleul :";
    SearchView svTextSearch;
    public int roleInverse;
    ArrayList<String> critere = new ArrayList<>();

    private RecyclerView recyclerView;
    private LierParrainFilleulAdapter adapterUser;

    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private Long usRole = 2L;
    private String usGodfatherRequestTo = "";
    private String usNephewsRequestTo = "";

    /** Initialisation des composants et affichage de la liste d'utilisateurs avec la recherche associée **/
    public void init() {
        recyclerView = findViewById(R.id.rvResultat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /** Récupération de la liste d'utilisateurs depuis la Firestore **/
    private void getDataFromFirestore() {

        // ici on determine le rôle de l'utilisateur connecté et on stock le rôle dans la variable usRole
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentUser != null;
        DocumentReference userConnected = usersCollectionRef.document(currentUser.getUid());

        userConnected.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                ModelUsers contenuUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                assert contenuUser != null;
                usRole = contenuUser.getUs_role();
                usGodfatherRequestTo = contenuUser.getUs_godfather_request_to();
                usNephewsRequestTo = contenuUser.getUs_nephews_request_to();
                ArrayList<String> GodfatherSepareted = new ArrayList<>(Arrays.asList(usGodfatherRequestTo.split(";")));
                ArrayList<String> NephewSepareted = new ArrayList<>(Arrays.asList(usNephewsRequestTo.split(";")));

                // Appel la fonction qui affiche la liste
                displayList(usRole, NephewSepareted, GodfatherSepareted );
                adapterUser.startListening();
            }
        });
    }

    public void displayList(Long role, ArrayList<String> NephewsList, ArrayList<String> GodfatherList ){

        //Ici on affiche la liste en fonction du rôle de l'utilisateur connecté
        // Si l'user connecté est un célibataire (il a un rôle us_role = 1), on veut donc afficher la liste des parrains disponibles
        critere.add("1");
       if (usRole.equals(1L)) {
            roleInverse = 2;
            setTitle(getString(R.string.Lier_pf_titre_filleul));
            critere = GodfatherList;
       } else {
           // Si l'user connecté est un parrain (il a un rôle us_role = 2), il cherche dans la liste des célibataires, qui n'ont pas déjà un parrain
            roleInverse = 1;
            setTitle(getString(R.string.Lier_pf_titre_parrain));
            critere = NephewsList;
       }
        /** Récupération de la collection Users dans Firestore **/
        Query query = db.collection("users")
                .whereEqualTo("us_role", roleInverse);
                //.whereNotIn("us_auth_uid", critere);
        FirestoreRecyclerOptions<ModelUsers> users =
                new FirestoreRecyclerOptions.Builder<ModelUsers>()
                        .setQuery(query, ModelUsers.class)
                        .build();

        Log.e(TAG, "Ca passe avec " + query.toString());

        adapterUser = new LierParrainFilleulAdapter(users);
        recyclerView.setAdapter(adapterUser);

        // Liaison des variables svTextSearch et lvResultat avec les éléments du graphique
        svTextSearch = findViewById(R.id.svTextSearch);

        // Actions à effectuer lorsque l'utilisateur tape du texte dans la barre de recherche
        svTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // adapterUser.stopListening();
                Query query = db.collection("users")
                        .whereEqualTo("us_role", roleInverse)
                        //.whereNotIn("us_auth_uid", critere) ici ça plante ??
                        .orderBy("us_nickname")
                        .startAt(newText)
                        .endAt(newText+"\uf8ff");

                FirestoreRecyclerOptions<ModelUsers> users =
                        new FirestoreRecyclerOptions.Builder<ModelUsers>()
                                .setQuery(query, ModelUsers.class)
                                .build();

                adapterUser = new LierParrainFilleulAdapter(users);
                recyclerView.setAdapter(adapterUser);
                adapterUser.startListening();
                return false;
            }
        });

        adapterUser.setOnItemCliclListener(new LierParrainFilleulAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot snapshot, int position) {
                snapshot.getReference();

                Intent intent = new Intent(LierParrainFilleulActivity.this, ViewProfil.class);
                intent.putExtra("IdUser", snapshot.getId());

                Log.e(TAG, "showProfil: " + snapshot.getId());
                startActivity(intent);
            }
        });
    }

    // ****************************************** CYCLE DE VIE ***********************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lier_parrain_filleul);
        init();
        getDataFromFirestore();
    }
}