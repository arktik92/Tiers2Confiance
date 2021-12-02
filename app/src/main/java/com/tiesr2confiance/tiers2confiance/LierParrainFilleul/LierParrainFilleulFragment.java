package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.ViewProfilFragment;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentLierParrainFilleulBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LierParrainFilleulFragment extends Fragment {

    /** Variables globales **/
    private static final String TAG = "Lier Parrain Filleul :";


    public int roleInverse;
    ArrayList<String> critere = new ArrayList<>();

    private SearchView svTextSearch;
    private RecyclerView rvResultat;
    private LierParrainFilleulAdapter adapterUser;
    private Boolean usAlreadyLinked = true;

    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private Long usRole;
    private String usGodfatherRequestTo = "";
    private String usNephewsRequestTo = "";

    private FragmentLierParrainFilleulBinding binding;

    // ****************************************** CYCLE DE VIE ***********************************************
  //  @Override
  //  protected void onCreate(Bundle savedInstanceState) {
  //      super.onCreate(savedInstanceState);
  //      setContentView(R.layout.activity_lier_parrain_filleul);
  //      init();
  //      getDataFromFirestore();
  //  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_lier_parrain_filleul, container, false);
        getDataFromFirestore(view);
        binding = FragmentLierParrainFilleulBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /** Initialisation des composants et affichage de la liste d'utilisateurs avec la recherche associée **/
    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rvResultat = view.findViewById(R.id.rvResultat);
        rvResultat.setHasFixedSize(true);
        rvResultat.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /** Récupération de la liste d'utilisateurs depuis la Firestore **/
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
                usGodfatherRequestTo = contenuUser.getUs_godfather_request_to();
                usNephewsRequestTo = contenuUser.getUs_nephews_request_to();
                ArrayList<String> GodfatherSepareted = new ArrayList<>(Arrays.asList(usGodfatherRequestTo.split(";")));
                ArrayList<String> NephewSepareted = new ArrayList<>(Arrays.asList(usNephewsRequestTo.split(";")));

                if (usRole.equals(1L)) {
                    if ( TextUtils.isEmpty(contenuUser.getUs_godfather())) { usAlreadyLinked = false; }
                } else {
                    if ( TextUtils.isEmpty(contenuUser.getUs_nephews())) { usAlreadyLinked = false; }
                }

                // On affiche la liste seulement si l'utilisateur n'est pas déjà lié avec un autre utilisateur
                if (usAlreadyLinked == true) {
                    if (usRole.equals(1L)){
                        Toast.makeText(getContext(), "Vous avez déjà un parrain", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Vous avez déjà un filleul", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    // Appel la fonction qui affiche la liste
                    displayList(usRole, NephewSepareted, GodfatherSepareted, view);
                    adapterUser.startListening();
                }
            }
        });
    }

    public void displayList(Long role, ArrayList<String> NephewsList, ArrayList<String> GodfatherList, View view ){

        //Ici on affiche la liste en fonction du rôle de l'utilisateur connecté
        // Si l'user connecté est un célibataire (il a un rôle us_role = 1), on veut donc afficher la liste des parrains disponibles
        critere.add("1");
       if (usRole.equals(1L)) {
            roleInverse = 2;
            critere = GodfatherList;
       } else {
           // Si l'user connecté est un parrain (il a un rôle us_role = 2), il cherche dans la liste des célibataires, qui n'ont pas déjà un parrain
            roleInverse = 1;
            critere = NephewsList;
       }
        /** Récupération de la collection Users dans Firestore **/
        Query query = db.collection("users")
                .whereEqualTo("us_role", roleInverse)
                .whereNotIn("us_auth_uid", critere);
        FirestoreRecyclerOptions<ModelUsers> users =
                new FirestoreRecyclerOptions.Builder<ModelUsers>()
                        .setQuery(query, ModelUsers.class)
                        .build();
        adapterUser = new LierParrainFilleulAdapter(users);
        rvResultat.setAdapter(adapterUser);

        // Liaison des variables svTextSearch et lvResultat avec les éléments du graphique
        svTextSearch = view.findViewById(R.id.svTextSearch);

        // Actions à effectuer lorsque l'utilisateur tape du texte dans la barre de recherche
        svTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Log.e(TAG, "onComplete: HHHHsubmit "  );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onComplete: HHHH "  );
               // adapterUser.stopListening();
                Query query = db.collection("users")
                        .whereEqualTo("us_role", roleInverse)
                        //.whereNotIn("us_auth_uid", critere) ici ça plante ??
                        .orderBy("us_nickname")
                        .startAt(newText)
                        .endAt(newText+"\uf8ff");

                Log.e(TAG, "onQueryTextChange: " + "TESSS" );

                FirestoreRecyclerOptions<ModelUsers> users =
                        new FirestoreRecyclerOptions.Builder<ModelUsers>()
                                .setQuery(query, ModelUsers.class)
                                .build();

                adapterUser = new LierParrainFilleulAdapter(users);
                rvResultat.setAdapter(adapterUser);
                adapterUser.startListening();
                return false;
            }
        });

        adapterUser.setOnItemCliclListener(new LierParrainFilleulAdapter.OnItemClickListener() {
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