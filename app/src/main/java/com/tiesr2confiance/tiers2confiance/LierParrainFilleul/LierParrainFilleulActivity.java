package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tiesr2confiance.tiers2confiance.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.SignInActivity;

import java.util.ArrayList;

public class LierParrainFilleulActivity extends AppCompatActivity {

    /** Variables globales **/
    private static final String TAG = "Lier Parrain Filleul :";
    SearchView svTextSearch;
    RecyclerView rvResultat;

    private RecyclerView recyclerView;
    private LierParrainFilleulAdapter adapterUser;

    /** Var Firebase **/
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    /** Initialisation des composants et affichage de la liste d'utilisateurs avec la recherche associée **/
    public void init() {

        recyclerView = findViewById(R.id.rvResultat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    /** Récupération de la liste d'utilisateurs depuis la Firestore **/
    private void getDataFromFirestore() {

        //TODO ici, il faudra determiné le rôle de l'utilisateur connecté
        // Si l'user connecté est un filleul
        setTitle(getString(R.string.Lier_pf_titre_filleul));
        // Si l'user connecté est un parrain
        setTitle(getString(R.string.Lier_pf_titre_parrain));


        /** Récupération de la collection Users dans Firestore **/
        Query query = db.collection("users");
        FirestoreRecyclerOptions<ModelUsers> users =
                new FirestoreRecyclerOptions.Builder<ModelUsers>()
                        .setQuery(query, ModelUsers.class)
                        .build();

        adapterUser = new LierParrainFilleulAdapter(users);
        recyclerView.setAdapter(adapterUser);
        //lvResultat.setVisibility(View.INVISIBLE);

        // Liaison des variables svTextSearch et lvResultat avec les éléments du graphique
        svTextSearch = findViewById(R.id.svTextSearch);


        // Actions à effectuer lorsque l'utilisateur tape du texte dans la barre de recherche
        svTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //      if (users.contains(query)){
                //          adapterUser.getFilter().filter(query);
                //        }else{
                // Sinon, on fait un toast
                //             Toast.makeText(LierParrainFilleulActivity.this, "Aucun utilisateur trouvé avec ce pseudo",Toast.LENGTH_LONG).show();
                //         }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterUser.getFilter().filter(newText);

                return false;
            }
        });

    }


    // CYCLE DE VIE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lier_parrain_filleul);
        init();

        getDataFromFirestore();
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(LierParrainFilleulActivity.this, SignInActivity.class ));
        } else {
            adapterUser.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterUser.stopListening();
    }


}