package com.tiesr2confiance.tiers2confiance.MatchCibles;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.Profil.ViewProfilFragment;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentMatchCiblesEnvoyeesBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class MatchCiblesEnvoyeesFragment extends Fragment {

    private static final String TAG = "Match Cibles ";
    private LinearLayout llRechercheDetails, llRechercheGloabale;
    private RecyclerView rvListCible;

    /** Champs de la recherche Globale **/
    private EditText ptCityGlobale;

    /** Champs de la recherche détaillée **/
    private SeekBar sbMax, sbMin;
    private TextView tvCurrentMin, tvCurrentMax;
    private Button btnSearchSingle;
    private EditText ptCodePostal;
    private RadioGroup radioGroupGenre;
    private RadioButton btnRadioGenre1, btnRadioGenre2, btnRadioGenre3;

    private FragmentMatchCiblesEnvoyeesBinding binding;

    ArrayList<String> critere = new ArrayList<>();

    private MatchCiblesEnvoyeesAdapter adapterUser;
    private Boolean usAlreadyLinked = true;


    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private Long usRole;

    String TypeSearch;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_cibles_envoyees, container, false);
        getDataMatchFromFirestore(view);
        binding = FragmentMatchCiblesEnvoyeesBinding.inflate(inflater, container, false);

        return view;
    }

    private void getDataMatchFromFirestore(View view) {

        critere.clear();
        critere.add("1");

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

                if ( TextUtils.isEmpty(contenuUser.getUs_nephews())) { usAlreadyLinked = false; }


                // On affiche la liste seulement si l'utilisateur n'est pas déjà lié avec un autre utilisateur
                if (usAlreadyLinked == false) {
                        Toast.makeText(getContext(), "Vous devez avoir un filleul pour accéder à ce menu", Toast.LENGTH_SHORT).show();
                }else {
                        // Parrain : On affiche les célibataires qu'on a déjà encore proposé à notre filleul, et qui ne sont pas encore matché
                        assert currentUser != null;
                        DocumentReference userNephew = usersCollectionRef.document(contenuUser.getUs_nephews());
                        userNephew.get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        ModelUsers contenunephewUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                        assert contenunephewUser != null;
                                        ArrayList<String> ListIn = new ArrayList<>();
                                        ListIn.add("1");
                                        try {
                                            ListIn.addAll(Arrays.asList(contenunephewUser.getUs_matchs_request_to().split(";")));
                                            displayPossibleMatchList(usRole, ListIn, view);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                }

        });
    }

    @SuppressLint("LongLogTag")
    private void displayPossibleMatchList(Long usRole, ArrayList listIn, View view) throws ParseException {
        Query query;

            query = db.collection("users")
                    .whereEqualTo("us_role", 1)
                    .whereIn("us_auth_uid",listIn);
            AfficherResultatQuery(query);
    }

    private void AfficherResultatQuery(Query query) {
        Query finalQuery = query;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                FirestoreRecyclerOptions<ModelUsers> users =
                        new FirestoreRecyclerOptions.Builder<ModelUsers>()
                                .setQuery(finalQuery, ModelUsers.class)
                                .build();
                adapterUser = new MatchCiblesEnvoyeesAdapter(users);
                rvListCible.setAdapter(adapterUser);
                adapterUser.startListening();

                adapterUser.setOnItemCliclListener(new MatchCiblesEnvoyeesAdapter.OnItemClickListener() {
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
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvListCible = view.findViewById(R.id.rv_list_cibles);
        rvListCible.setHasFixedSize(true);
        rvListCible.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}