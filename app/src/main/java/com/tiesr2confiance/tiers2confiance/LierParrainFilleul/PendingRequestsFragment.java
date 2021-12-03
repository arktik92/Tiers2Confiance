package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.tiesr2confiance.tiers2confiance.Profil.ViewProfilFragment;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentPendingRequestsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class PendingRequestsFragment extends Fragment {


    /* Décalaration des variables */
    private RecyclerView recyclerView;
    private PendingRequestsAdapter adapterUser;

    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private Long usRole = 2L;
    private Boolean usAlreadyLinked = true;

    private String usGodfatherRequestFrom = "";
    private String usNephewsRequestFrom = "";

    public int roleInverse;
    ArrayList<String> critere = new ArrayList<>();

    private FragmentPendingRequestsBinding binding;

    /**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);
        init();
        getDataFromFirestore();
    }
     **/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pending_requests, container, false);
        getDataFromFirestore(view);
        binding = FragmentPendingRequestsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /** Initialisation des composants  **/
    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rvResultatDemand);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    /** Récupération de la liste des demandes  **/
    private void getDataFromFirestore(View view) {

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
                    if ( TextUtils.isEmpty(contenuUser.getUs_godfather())) { usAlreadyLinked = false; }
                    critere = GodfatherSepareted;
                } else {
                    roleInverse = 1;
                    critere = NephewSepareted;
                    if ( TextUtils.isEmpty(contenuUser.getUs_nephews())) { usAlreadyLinked = false; }
                }

                // On affiche la liste seulement si l'utilisateur n'est pas déjà lié avec un autre utilisateur
                if (usAlreadyLinked == true) {
                    if (usRole.equals(1L)){
                        Toast.makeText(getContext(), "Vous avez déjà un parrain", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Vous avez déjà un filleul", Toast.LENGTH_SHORT).show();
                    }
                }else{
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

                    adapterUser.setOnItemCliclListener(new PendingRequestsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DocumentSnapshot snapshot, int position) {

                            String idUser = snapshot.getId();
                            Bundle b = new Bundle();
                            b.putString("idUser", idUser);
                            Fragment fragment = new ViewProfilFragment();
                            fragment.setArguments(b);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }
                    });
                }

            }
        });

    }

}