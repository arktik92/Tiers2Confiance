package com.tiesr2confiance.tiers2confiance.MatchCibles;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.tiesr2confiance.tiers2confiance.databinding.FragmentMatchCiblesBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class MatchCiblesFragment extends Fragment {

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

    private FragmentMatchCiblesBinding binding;

    ArrayList<String> critere = new ArrayList<>();

    private MatchCiblesAdapter adapterUser;
    private Boolean usAlreadyLinked = true;

    /** Critère de recherche **/
    private String CityCritereGlobale;
    private long codePostalCritere;
    private long ageMinCritere;
    private long ageMaxCritere;
    private long genreCritere;

    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private Long usRole;

    String TypeSearch;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Récupération de l'ID de l'utilisateur à afficher
        Bundle bundle = getArguments();
        TypeSearch = bundle.getString("typeSearch");

        Log.e(TAG," TTEEE" + TypeSearch );

        View view = inflater.inflate(R.layout.fragment_match_cibles, container, false);
        getDataMatchFromFirestore(view);
        binding = FragmentMatchCiblesBinding.inflate(inflater, container, false);

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

                    // Appel la fonction qui affiche la liste avec les paramètres : displayPossibleMatchList
                    if (usRole.equals(1L)) {
                       // Filleul : On affiche les célibataires proposés par son parrain, ou les célibataires d'un autre parrain dont on est la cible
                        ArrayList<String> ListIn = new ArrayList<>();
                        ArrayList<String> ListNotIn = new ArrayList<>();
                        ListIn.addAll(Arrays.asList(contenuUser.getUs_matchs_request_to().split(";")));
                        ListIn.addAll(Arrays.asList(contenuUser.getUs_matchs_request_from().split(";")));
                        try {
                            displayPossibleMatchList(usRole, ListIn, ListNotIn, view);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Parrain : On affiche les célibataires qu'on n'a pas encore proposé à notre filleul, et qui ne sont pas encore matché
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
                                        ListNotIn.addAll(Arrays.asList(contenunephewUser.us_matchs.split(";")));
                                        try {
                                            displayPossibleMatchList(usRole, ListIn, ListNotIn, view);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void displayPossibleMatchList(Long usRole, ArrayList listIn, ArrayList listNotIn, View view) throws ParseException {
        Query query;

        /** Recherche globale **/
        // Récupération des attributs indiqués dans la recherche
        if (!String.valueOf(ptCityGlobale.getText()).equals("")){
            CityCritereGlobale = String.valueOf(ptCityGlobale.getText());
        }

        /** Recherche détaillée **/
        // Récupération des attributs indiqué dans la recherche
        if (!String.valueOf(ptCodePostal.getText()).equals("")){
            codePostalCritere = Long.parseLong(String.valueOf(ptCodePostal.getText()));
        }
        ageMinCritere = sbMin.getProgress();
        ageMaxCritere = sbMax.getProgress();
        int choix = radioGroupGenre.getCheckedRadioButtonId();
        if (choix == -1 ){
          // Pas de choix
            genreCritere = -1;
        }else{
            genreCritere = choix;
        }
        Calendar today =  Calendar.getInstance();
        long Min =  today.get(Calendar.YEAR) - ageMinCritere -1;
        long Max =  today.get(Calendar.YEAR) - ageMaxCritere -1;
        String critereAgeMin =  today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.MONTH) + "/" + Min;
        String critereAgeMax =  today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.MONTH) + "/" + Max;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dateMax = format.parse(critereAgeMax);
        Date dateMin = format.parse(critereAgeMin);


        if (TypeSearch == "globale"){
            query = db.collection("users").whereEqualTo("us_role", 1);
            if (!String.valueOf(ptCityGlobale.getText()).equals("")){
                query = query
                        .orderBy("us_city_lowercase")
                        .startAt(CityCritereGlobale.toLowerCase())
                        .endAt(CityCritereGlobale.toLowerCase()+"\uf8ff");
            }
            AfficherResultatQuery(query);
        }else{
            //Célibataire
            if (usRole.equals(1L)) {
                critere.clear();
                critere.add("1");
                critere.addAll(listIn);
                query = db.collection("users")
                        .whereEqualTo("us_role", 1)
                        .whereEqualTo("us_gender", genreCritere)
                        .whereLessThan("us_birth_date",dateMin )
                        .whereGreaterThan("us_birth_date", dateMax)
                        .whereIn("us_auth_uid", critere);
                if (!String.valueOf(ptCodePostal.getText()).equals("")){
                    query = query.whereEqualTo("us_postal_code", codePostalCritere);
                }
                AfficherResultatQuery(query);
            } else {
                //Parrain
                //critere.addAll(listNotIn);
                query = db.collection("users")
                        .whereEqualTo("us_role", 1)
                        .whereEqualTo("us_gender", genreCritere)
                        .whereLessThan("us_birth_date",dateMin )
                        .whereGreaterThan("us_birth_date", dateMax);
                //.whereNotIn("us_auth_uid", critere);
                if (!String.valueOf(ptCodePostal.getText()).equals("")){
                    query = query
                            //.orderBy("us_postal_code")
                            .whereEqualTo("us_postal_code", codePostalCritere);
                    //.startAt(codePostalCritere)
                    //.endAt(codePostalCritere+"\uf8ff");
                }
                AfficherResultatQuery(query);
            }
        }
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
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** On instancie tous les éléments du layout **/
        llRechercheDetails = view.findViewById(R.id.ll_recherche_details);
        llRechercheGloabale = view.findViewById(R.id.ll_recherche_globale);
        rvListCible = view.findViewById(R.id.rv_list_cibles);
        rvListCible.setHasFixedSize(true);
        rvListCible.setLayoutManager(new LinearLayoutManager(getContext()));

        // Recherche globale
        ptCityGlobale = view.findViewById(R.id.pt_ville_globale);

        // Recherche détaillée
        sbMin = view.findViewById(R.id.sb_min);
        tvCurrentMin = view.findViewById(R.id.tv_min);
        sbMax = view.findViewById(R.id.sb_max);
        tvCurrentMax = view.findViewById(R.id.tv_max);
        btnSearchSingle = view.findViewById(R.id.btn_search_single);
        ptCodePostal = view.findViewById(R.id.pt_code_postal);
        radioGroupGenre = view.findViewById(R.id.btn_radio_genre);
        btnRadioGenre1 = view.findViewById(R.id.btn_radio_genre_1);
        btnRadioGenre2 = view.findViewById(R.id.btn_radio_genre_2);
        btnRadioGenre3 = view.findViewById(R.id.btn_radio_genre_3);

        btnRadioGenre1.setId((int) 1);
        btnRadioGenre1.setChecked(true);
        btnRadioGenre2.setId((int) 2);
        btnRadioGenre3.setId((int) 3);

        sbMin.setMin((int) 18);
        sbMin.setMax((int) 99);
        sbMin.setProgress((int) 18);
        tvCurrentMin.setText(String.valueOf(18));

        sbMax.setMin((int)18);
        sbMax.setMax((int) 99);
        sbMax.setProgress((int) 99);
        tvCurrentMax.setText(String.valueOf(99));

        /** On affiche l'espace de recherche en fonction du menu choisi par l'utilisateur **/

        if (TypeSearch == "details"){
            makeVisibleRechercheDetails();
            makeInvisibleRechercheGlobale();
        }else {
            makeVisibleRechercheGlobale();
            makeInvisibleRechercheDetails();
        }

        /**  Recherche Globale sur code postal **/

        ptCityGlobale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getDataMatchFromFirestore(view);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**  Recherche Détaillé sur code postal COMPLET, l'âge et le genre **/

        // Click sur le bouton GO pour le code postal
        btnSearchSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataMatchFromFirestore(view);
            }
        });

        sbMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCurrentMin.setText(String.valueOf(sbMin.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                getDataMatchFromFirestore(view);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCurrentMax.setText(String.valueOf(sbMax.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                getDataMatchFromFirestore(view);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        radioGroupGenre.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getDataMatchFromFirestore(view);
            }
        });


    }

    private void makeInvisibleRechercheGlobale() {
        llRechercheGloabale.setVisibility(View.GONE);
    }

    private void makeInvisibleRechercheDetails() {
        llRechercheDetails.setVisibility(View.GONE);
    }

    private void makeVisibleRechercheGlobale() {
        llRechercheGloabale.setVisibility(View.VISIBLE);
    }

    private void makeVisibleRechercheDetails() {
        llRechercheDetails.setVisibility(View.VISIBLE);
    }

}