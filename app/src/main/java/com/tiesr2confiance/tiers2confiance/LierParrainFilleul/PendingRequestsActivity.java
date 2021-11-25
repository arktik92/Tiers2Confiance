package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PendingRequestsActivity extends AppCompatActivity {


    /* Décalaration des variables */
    private RecyclerView recyclerView;
    private PendingRequestsAdapter adapterUser;

    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private Long usRole = 2L;

    private String usGodfatherRequestFrom = "";
    private String usNephewsRequestFrom = "";

    public int roleInverse;
    ArrayList<String> critere = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);
        init();
        getDataFromFirestore();
    }

    /** Initialisation des composants  **/
    public void init() {
        recyclerView = findViewById(R.id.rvResultatDemand);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTitle(getString(R.string.accepter_demande));
    }

    /** Récupération de la liste des demandes  **/
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
                usGodfatherRequestFrom = contenuUser.getUs_godfather_request_from();
                usNephewsRequestFrom = contenuUser.getUs_nephews_request_from();

                ArrayList<String> GodfatherSepareted = new ArrayList<>(Arrays.asList(usGodfatherRequestFrom.split(";")));
                ArrayList<String> NephewSepareted = new ArrayList<>(Arrays.asList(usNephewsRequestFrom.split(";")));


                if (usRole.equals(1L)) {
                    roleInverse = 2;
                    critere = NephewSepareted;
                } else {
                    roleInverse = 1;
                    critere = GodfatherSepareted;
                }

                /** Récupération de la collection Users dans Firestore **/
                Query query = db.collection("users")
                        .whereEqualTo("us_role", roleInverse)
                        .whereIn("us_auth_uid", critere);
                FirestoreRecyclerOptions<ModelUsers> users =
                        new FirestoreRecyclerOptions.Builder<ModelUsers>()
                                .setQuery(query, ModelUsers.class)
                                .build();

                adapterUser = new PendingRequestsAdapter(users);
                recyclerView.setAdapter(adapterUser);
                adapterUser.startListening();
            }
        });

    }

}