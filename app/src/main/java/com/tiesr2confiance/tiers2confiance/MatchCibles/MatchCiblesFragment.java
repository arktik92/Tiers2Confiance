package com.tiesr2confiance.tiers2confiance.MatchCibles;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_FS_COLLECTION;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
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
import com.tiesr2confiance.tiers2confiance.Profil.ViewProfilFragment;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentMatchCiblesBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchCiblesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchCiblesFragment extends Fragment {

    private static final String TAG = "Match Cibles Fragment - ";
    private RecyclerView rvListCible;
    private FragmentMatchCiblesBinding binding;

    ArrayList<String> critere = new ArrayList<>();

    private MatchCiblesAdapter adapterUser;
    private Boolean usAlreadyLinked = true;
    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private Long usRole;


    public MatchCiblesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MatchCiblesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchCiblesFragment newInstance() {
        MatchCiblesFragment fragment = new MatchCiblesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_cibles, container, false);
        getDataMatchFromFirestore(view);
        binding = FragmentMatchCiblesBinding.inflate(inflater, container, false);
        return view;
    }

    private void getDataMatchFromFirestore(View view) {

        // ici on determine le rôle de l'utilisateur connecté et on stock le rôle dans la variable usRole
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentUser != null;
        DocumentReference userConnected = usersCollectionRef.document(currentUser.getUid());

        userConnected.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                ModelUsers contenuUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                assert contenuUser != null;
                usRole = contenuUser.getUs_role();

                if (usRole.equals(1L)) {
                    if ( TextUtils.isEmpty(contenuUser.getUs_godfather())) { usAlreadyLinked = false; }
                } else {
                    if ( TextUtils.isEmpty(contenuUser.getUs_nephews())) { usAlreadyLinked = false; }
                }

                // On affiche la liste seulement si l'utilisateur n'est pas déjà lié avec un autre utilisateur
                if (usAlreadyLinked == false) {
                    if (usRole.equals(1L)){
                        Toast.makeText(getContext(), "Vous devez avoir un parrain pour accéder à ce menu", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Vous devez avoir un filleul pour accéder à ce menu", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    // Appel la fonction qui affiche la liste

                    if (usRole.equals(1L)) {
                       // Filleul : On affiche les célibataires proposés par son parrain, ou les célibataires d'un autre parrain dont on est la cible
                        ArrayList<String> ListIn = new ArrayList<>();
                        ArrayList<String> ListNotIn = new ArrayList<>();
                        ListIn.addAll(Arrays.asList(contenuUser.getUs_matchs_request_to().split(";")));
                        ListIn.addAll(Arrays.asList(contenuUser.getUs_matchs_request_from().split(";")));
                        displayPossibleMatchList(usRole, ListIn, ListNotIn, view);
                        adapterUser.startListening();
                    } else {
                        // Parrain : On affiche les célibataires qu'on n'a pas encore proposé à notre filleul
                        assert currentUser != null;
                        DocumentReference userNephew = usersCollectionRef.document(contenuUser.getUs_nephews());
                        userNephew.get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        ModelUsers contenunephewUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                        assert contenunephewUser != null;
                                        ArrayList<String> ListIn = new ArrayList<>();
                                        ArrayList<String> ListNotIn = new ArrayList<>();
                                        ListNotIn.addAll(Arrays.asList(contenunephewUser.us_matchs_request_to.split(";")));
                                        displayPossibleMatchList(usRole, ListIn, ListNotIn, view);
                                        adapterUser.startListening();
                                    }
                                });
                    }
                }
            }
        });


    }

    @SuppressLint("LongLogTag")
    private void displayPossibleMatchList(Long usRole, ArrayList listIn, ArrayList listNotIn, View view) {

        critere.add("1");
        Query query = db.collection("users");
        //Célibataire
        if (usRole.equals(1L)) {
            critere.addAll(listIn);
            query = db.collection("users")
                    .whereEqualTo("us_role", 1)
                    .whereIn("us_auth_uid", critere);
        } else {
        //Parrain
            critere.addAll(listNotIn);
            query = db.collection("users")
                    .whereEqualTo("us_role", 1)
                    .whereNotIn("us_auth_uid", critere);
        }

        FirestoreRecyclerOptions<ModelUsers> users =
                new FirestoreRecyclerOptions.Builder<ModelUsers>()
                        .setQuery(query, ModelUsers.class)
                        .build();

        adapterUser = new MatchCiblesAdapter(users);
        rvListCible.setAdapter(adapterUser);
        adapterUser.startListening();


        adapterUser.setOnItemCliclListener(new MatchCiblesAdapter.OnItemClickListener() {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvListCible = view.findViewById(R.id.rv_list_cibles);
        rvListCible.setHasFixedSize(true);
        rvListCible.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}