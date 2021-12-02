package com.tiesr2confiance.tiers2confiance;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_CITY;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_DESCRIPTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HOBBIES;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_IMG;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_IMG_AVATAR;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_NAME;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_ROLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentViewProfilBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewProfilFragment extends Fragment {

    public static final String TAG = "View Profile";

    private TextView tvProfilName, tvDescription, tvProfilCity, tvHobbies;
    private ImageView ivProfilAvatarShape;
    private Button btnPflCrediter, btnPflEnvoyer, btnLinkSupp, btnLinkRequest, btnLinkSuppTiers, btnLinkRequestTiers ;
    private LinearLayout llProfil;

    /*** BDD ***/
    private FirebaseFirestore db;
    private CollectionReference usersCollectionRef;
    /** ID Document **/
    private FirebaseUser currentUser;
    private DocumentReference noteRef;
    private DocumentReference userConnected;
    /** Collection **/
    private String KEY_FS_USER_ID;
    public final String KEY_FS_COLLECTION = "users";

    private Long usRole;
    private String usNephew;
    private String usGodfather;

    private static FirebaseUser user;
    private static String userId;
    private static String userNickName;
    private static String userCountryLanguage = "";
    private static String userEmail;


    String list_hobbies;
    public HashMap<Long, String> globalVarValue;

    private FragmentViewProfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //public String arg = getArguments().getString("");
        View view = inflater.inflate(R.layout.fragment_view_profil, container, false);

      //  Bundle bundle = getIntent().getExtras();
      //  if(bundle.getString("IdUser") != null) {
      //      KEY_FS_USER_ID = bundle.getString("IdUser");
      //      Log.d(TAG, "BundleGetString: "+ KEY_FS_USER_ID);
      //  }

        Bundle bundle = getArguments();
        String myStrings =bundle.getString("idUser");

        Log.e(TAG, "onCreateView: "+ myStrings);

        KEY_FS_USER_ID = "c0aS9xtlb1CFE51hQzRJ";
        KEY_FS_USER_ID = myStrings;

        getDataIDUser(view);
        showProfil();
        // Affiche les boutons en fonctions du context
        binding = FragmentViewProfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /** Initialisation des composants  **/
    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tvProfilName = view.findViewById(R.id.tvProfilName);
        tvProfilCity = view.findViewById(R.id.tvProfilCity);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvHobbies = view.findViewById(R.id.tvHobbies);

        llProfil = view.findViewById(R.id.ll_profil);
        llProfil.setVisibility(View.GONE);

        btnPflCrediter = view.findViewById(R.id.btn_pfl_crediter);
        btnPflEnvoyer = view.findViewById(R.id.btn_pfl_envoyer);
        btnLinkSupp = view.findViewById(R.id.btn_link_supp);
        btnLinkRequest = view.findViewById(R.id.btn_link_request);
        btnLinkSuppTiers = view.findViewById(R.id.btn_link_supp_tier);
        btnLinkRequestTiers= view.findViewById(R.id.btn_link_request_tiers);

        btnPflCrediter.setVisibility(View.GONE);
        btnPflEnvoyer.setVisibility(View.GONE);
        btnLinkSupp.setVisibility(View.GONE);
        btnLinkRequest.setVisibility(View.GONE);
        btnLinkSuppTiers.setVisibility(View.GONE);
        btnLinkRequestTiers.setVisibility(View.GONE);


        /** Glide image **/
        ivProfilAvatarShape = view.findViewById(R.id.ivProfilAvatarShape);

        // Redirige vers le fragment pour créditer son filleul
        btnPflCrediter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CreditFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Supprimer le lien Parrain/Filleul
        btnLinkSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération de l'utilisateur connecté
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                userConnected = usersCollectionRef.document(currentUser.getUid());

                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            noteRef.update("us_godfather", "" );
                            userConnected.update("us_nephews", "" );
                        }
                    }
                });

            }
        });

        // Le parrain demande à parrainer le célibataire
        btnLinkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération de l'utilisateur connecté
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                userConnected = usersCollectionRef.document(currentUser.getUid());

                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userConnected.update("us_nephews_request_to", contenuUser.getUs_nephews_request_to() + noteRef.getId()+  ";");
                            noteRef.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            ModelUsers celibUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                            assert celibUser != null;
                                            String usGodfatherRequestFrom = celibUser.getUs_godfather_request_from();
                                            noteRef.update("us_godfather_request_from", usGodfatherRequestFrom + userConnected.getId()+  ";");
                                        }
                                    });
                        }
                    }
                });

            }
        });


        // Supprimer le lien Parrain/Filleul
        btnLinkSuppTiers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération de l'utilisateur connecté
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                userConnected = usersCollectionRef.document(currentUser.getUid());

                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            noteRef.update("us_nephews", "" );
                            userConnected.update("us_godfather", "" );
                        }
                    }
                });

            }
        });

        // Le célibataire demande à être parrainé par un parrain
        btnLinkRequestTiers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération de l'utilisateur connecté
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                userConnected = usersCollectionRef.document(currentUser.getUid());

                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userConnected.update("us_godfather_request_to", contenuUser.getUs_godfather_request_to() + noteRef.getId()+  ";");
                            noteRef.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            ModelUsers celibUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                            assert celibUser != null;
                                            String usNephewsRequestFrom = celibUser.getUs_nephews_request_from();
                                            noteRef.update("us_nephews_request_from", usNephewsRequestFrom + userConnected.getId()+  ";");
                                        }
                                    });
                        }
                    }
                });

            }
        });


    }

    private void getDataIDUser(View view) {
        /** BDD, Connexion FIreStore ***/
        db = FirebaseFirestore.getInstance();
        // NoteRef, récupération de l'utilisateur connecté
        noteRef = db.document(KEY_FS_COLLECTION + "/" + KEY_FS_USER_ID);
        usersCollectionRef = db.collection("users");
    }

    public void showProfil() {

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString(KEY_NAME);
                            String city = documentSnapshot.getString(KEY_CITY);
                            String imgurl = documentSnapshot.getString(KEY_IMG);
                            String imgUrl_avatar = documentSnapshot.getString(KEY_IMG_AVATAR);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);
                            String hobbies = documentSnapshot.getString(KEY_HOBBIES);
                            Long role = documentSnapshot.getLong(KEY_ROLE);

                            tvProfilCity.setText(city);
                            tvProfilName.setText(name);
                            tvDescription.setText(description);
                            /** Glide - Add Picture **/
                            Context context = getContext();
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .error(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher);
                            /*
                            Glide
                                    .with(context)
                                    .load(imgurl)
                                    .apply(options)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfil);
                                 */
                            /** Loading Avatar **/
                            Glide
                                    .with(context)
                                    .load(imgUrl_avatar)
                                    .apply(options)
                                    .fitCenter()
                                    .circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfilAvatarShape);

                            if (role.equals(1L)){
                                llProfil.setVisibility(View.VISIBLE);
                            }

                            list_hobbies = documentSnapshot.getString("us_hobbies");
                            Log.d(TAG, " List Hobbies ID => " + list_hobbies); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}
                            String split_key = ";";
                            // Ici on a récupérer dans la variables hobbies_list la liste des hobbies de l'utilisateur
                            String[] hobbiesListUser = list_hobbies.split(split_key);

                            final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
                            ArrayList<ModelHobbies> ListHobbies = globalVariables.getArrayListHobbies();

                            int i;
                            String hobbies_display="--";
                            for (i=0; i< hobbiesListUser.length;i++) {

                                for (int j = 0; j < ListHobbies.size(); j++) {
                                    String key = String.valueOf(ListHobbies.get(j).getHo_id());
                                    String value = ListHobbies.get(j).getHo_label();
                                    if (key.equals(hobbiesListUser[i])) {
                                        Log.e(TAG, "onSuccess: " + "Clé: " + key + ", Valeur: " + value );
                                        hobbies_display += value + " -- ";
                                    }
                                }
                            }

                            tvHobbies.setText(hobbies_display);
                            // Ici on va chercher les labels correspondants à la liste d'ID récupérée, puis on les affiche
                            //ListsAttributs attrHobies = new ListsAttributs(FirebaseFirestore.getInstance(), "EN");
                            //attrHobies.getHobbiesDataFromFirestore();

                        } else {
                            Toast.makeText(getContext(), "Any Document", Toast.LENGTH_SHORT).show();
                        }


                        // Si le user connecté est un Parrain, on affiche les bouttons qui vont bien
                        currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        userConnected = usersCollectionRef.document(currentUser.getUid());

                        userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshotConnected) {
                                if (documentSnapshotConnected.exists()) {
                                    ModelUsers contenuUser = documentSnapshotConnected.toObject(ModelUsers.class);
                                    assert contenuUser != null;

                                    usRole = contenuUser.getUs_role();
                                    usNephew = contenuUser.getUs_nephews();
                                    usGodfather = contenuUser.getUs_godfather();
                                    if (usRole.equals(2L)) {
                                        Log.e(TAG, "onSuccess: " + usNephew );
                                        Log.e(TAG, "onSuccess: " + documentSnapshot.getId() );
                                        // Si le profil consulté est le filleul du parrain,
                                        if (usNephew.equals(documentSnapshot.getId())){
                                            //on peut créditer
                                            btnPflCrediter.setVisibility(View.VISIBLE);
                                            // on peut supprimer le lien de parrainage
                                            btnLinkSupp.setVisibility(View.VISIBLE);

                                        }else{
                                            // Si le profil consulté n'est pas le filleul du parrain
                                            //on peut envoyer faire un envoi du profil à son filleul (Proposition)
                                            if (TextUtils.isEmpty(usNephew)){
                                                // On peut demander à parrainer le célibataire si on n'a pas de filleul
                                                btnLinkRequest.setVisibility(View.VISIBLE);
                                            }else{
                                                // On peut envoyer le profil à son filleul si on en a un
                                                btnPflEnvoyer.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } else {
                                        // Si le profil consulté est le parrain du filleul
                                        if (usGodfather.equals(documentSnapshot.getId())){
                                            // on peut supprimer le lien de parrainage
                                            btnLinkSuppTiers.setVisibility(View.VISIBLE);
                                        }else{
                                            if (TextUtils.isEmpty(usGodfather)){
                                                // On fait une demande au parrain si on a pas de parrain
                                                btnLinkRequestTiers.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
