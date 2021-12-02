package com.tiesr2confiance.tiers2confiance.Profil;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_CITY;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_DESCRIPTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_FS_COLLECTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_GENDER;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HOBBIES;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_IMG;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_IMG_AVATAR;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_NAME;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_NICKNAME;
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
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentViewProfilBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewProfilFragment extends Fragment {

    public static final String TAG = "View Profile";

    private TextView tvProfilName, tvDescription, tvProfilCity, tvHobbies, tvRole;
    private ImageView ivProfilAvatarShape, ivGender;
    private Button btnPflCrediter, btnPflEnvoyer, btnLinkSupp, btnLinkRequest, btnLinkSuppTiers, btnLinkRequestTiers, btnUpdateProfil ;
    private LinearLayout llProfil;

    /*** BDD ***/
    private FirebaseFirestore db;

    /** ID Document To Displayed **/
    private String UserId;
    private DocumentReference userDisplayed;

    /** ID Document Connected **/
    private FirebaseUser currentUser;
    private DocumentReference userConnected;

    /** Variables **/
    private Long usRole;
    private String usNephew;
    private String usGodfather;
    private String list_hobbies;

    private FragmentViewProfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //public String arg = getArguments().getString("");
        View view = inflater.inflate(R.layout.fragment_view_profil, container, false);

        // Récupération de l'ID de l'utilisateur à afficher
        Bundle bundle = getArguments();
        String myStrings =bundle.getString("idUser");
        UserId = myStrings;

        // userDisplayed, récupération de l'utilisateur à afficher
        db = FirebaseFirestore.getInstance();
        userDisplayed = db.document(KEY_FS_COLLECTION + "/" + UserId);

        // currentUser, récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = db.collection(KEY_FS_COLLECTION).document(currentUser.getUid());

        // Affiche les élèments du profil en fonction du rôle et des liens entre l'utilisateur affiché, et l'utilisateur connecté
        // + Affiche les boutons necessaires
        showProfil();

        binding = FragmentViewProfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /** Initialisation des composants  **/
    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        tvProfilName = view.findViewById(R.id.tvProfilName);
        tvRole = view.findViewById(R.id.tvRole);
        tvProfilCity = view.findViewById(R.id.tvProfilCity);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvHobbies = view.findViewById(R.id.tvHobbies);
        ivGender = view.findViewById(R.id.ivGender);
        llProfil = view.findViewById(R.id.ll_profil);
        llProfil.setVisibility(View.GONE);

        btnPflCrediter = view.findViewById(R.id.btn_pfl_crediter);
        btnPflEnvoyer = view.findViewById(R.id.btn_pfl_envoyer);
        btnLinkSupp = view.findViewById(R.id.btn_link_supp);
        btnLinkRequest = view.findViewById(R.id.btn_link_request);
        btnLinkSuppTiers = view.findViewById(R.id.btn_link_supp_tier);
        btnLinkRequestTiers= view.findViewById(R.id.btn_link_request_tiers);
        btnUpdateProfil = view.findViewById(R.id.btn_update_profil);

        // Les boutons n'existe pas dans le Layout à l'initialisation, on les affiche seulement si necessaire
        btnPflCrediter.setVisibility(View.GONE);
        btnPflEnvoyer.setVisibility(View.GONE);
        btnLinkSupp.setVisibility(View.GONE);
        btnLinkRequest.setVisibility(View.GONE);
        btnLinkSuppTiers.setVisibility(View.GONE);
        btnLinkRequestTiers.setVisibility(View.GONE);
        btnUpdateProfil.setVisibility(View.GONE);

        /** Glide image **/
        ivProfilAvatarShape = view.findViewById(R.id.ivProfilAvatarShape);

        // ACTION BOUTON ROLE=PARRAIN : Redirige vers le fragment permettant de créditer son filleul
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

        // ACTION BOUTON ROLE=PARRAIN : Supprime le lien entre le parrain et le filleul
        btnLinkSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userDisplayed.update("us_godfather", "" );
                            userConnected.update("us_nephews", "" );
                        }
                    }
                });

            }
        });

        // ACTION BOUTON ROLE=PARRAIN : Le parrain demande à un célibataire (Parrain en recherche de filleul)
        btnLinkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userConnected.update("us_nephews_request_to", contenuUser.getUs_nephews_request_to() + userDisplayed.getId()+  ";");
                            userDisplayed.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            ModelUsers celibUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                            assert celibUser != null;
                                            String usGodfatherRequestFrom = celibUser.getUs_godfather_request_from();
                                            userDisplayed.update("us_godfather_request_from", usGodfatherRequestFrom + userConnected.getId()+  ";");
                                        }
                                    });
                        }
                    }
                });

            }
        });


        // ACTION BOUTON ROLE=CELIBATAIRE : Supprime le lien entre le parrain et le célibataire connecté
        btnLinkSuppTiers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userDisplayed.update("us_nephews", "" );
                            userConnected.update("us_godfather", "" );
                        }
                    }
                });

            }
        });

        // ACTION BOUTON ROLE=CELIBATAIRE : Le célibataire demande à être parrainé par un parrain
        btnLinkRequestTiers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userConnected.update("us_godfather_request_to", contenuUser.getUs_godfather_request_to() + userDisplayed.getId()+  ";");
                            userDisplayed.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            ModelUsers celibUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                            assert celibUser != null;
                                            String usNephewsRequestFrom = celibUser.getUs_nephews_request_from();
                                            userDisplayed.update("us_nephews_request_from", usNephewsRequestFrom + userConnected.getId()+  ";");
                                        }
                                    });
                        }
                    }
                });

            }
        });

        // ACTION BOUTON ROLE=PARRAIN OU CELIBATAIRE : Redirige vers la modification de son profil
        btnUpdateProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ProfilFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



    }

    public void showProfil() {

        userDisplayed.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshotDisplayed) {
                        if (documentSnapshotDisplayed.exists()) {
                            String nickname = documentSnapshotDisplayed.getString(KEY_NICKNAME);
                            String city = documentSnapshotDisplayed.getString(KEY_CITY);
                            String imgurl = documentSnapshotDisplayed.getString(KEY_IMG);
                            String imgUrl_avatar = documentSnapshotDisplayed.getString(KEY_IMG_AVATAR);
                            String description = documentSnapshotDisplayed.getString(KEY_DESCRIPTION);
                            String hobbies = documentSnapshotDisplayed.getString(KEY_HOBBIES);
                            Long role = documentSnapshotDisplayed.getLong(KEY_ROLE);

                            // Si l'utilisateur à afficher est un célibataire
                            if (role.equals(1L)){
                                tvRole.setText("Célib");
                                llProfil.setVisibility(View.VISIBLE);
                            }else{
                                // sinon, s'il est Tiers de confiance (parrain)
                                tvRole.setText("Tiers");
                            }

                            tvProfilCity.setText(city);
                            tvProfilName.setText(nickname);
                            tvDescription.setText(description);
                            /** Glide - Add Picture **/
                            Context context = getContext();
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .error(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher);

                            /** Loading Avatar **/
                            Glide
                                    .with(context)
                                    .load(imgUrl_avatar)
                                    .apply(options)
                                    .fitCenter()
                                    .circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfilAvatarShape);

                            // Ici, on récupère tous les attributs.caractériques de l'utilisateur à afficher (on récupère les ID des valeurs, qu'on va comparer avec les listes complètes chargées par la Class Globale)
                            String split_key = ";";
                            list_hobbies = documentSnapshotDisplayed.getString(KEY_HOBBIES);
                            String[] hobbiesListUser = list_hobbies.split(split_key);
                            String genderUser = String.valueOf(documentSnapshotDisplayed.getLong(KEY_GENDER));

                            // Appel de la classe global pour charger les listes d'attributs
                            final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
                            ArrayList<ModelHobbies> ListHobbiesComplete = globalVariables.getArrayListHobbies();
                            ArrayList<ModelGenders> ListGendersComplete = globalVariables.getArrayListGenders();

                            // HOBBIES VALEURS : Affichage des hobbies, comparaison de la liste des hobbies de l'utilisateur avec la liste complète chargée
                            int i;
                            String hobbiesToDisplay="-- ";
                            for (i=0; i< hobbiesListUser.length;i++) {
                                for (int j = 0; j < ListHobbiesComplete.size(); j++) {
                                    String key = String.valueOf(ListHobbiesComplete.get(j).getHo_id());
                                    String value = ListHobbiesComplete.get(j).getHo_label();
                                    if (key.equals(hobbiesListUser[i])) {
                                        hobbiesToDisplay += value + " -- ";
                                    }
                                }
                            }
                            tvHobbies.setText(hobbiesToDisplay);

                            // GENDERS VALEURS :  des hobbies, comparaison de la liste des hobbies de l'utilisateur avec la liste complète chargée
                            // En fait , pas besoin de récupérer le label du genre, puisqu'on affiche une icone en fonciton de l'id renseigné dans us_genre
                            /*
                            String genderToDisplay="";
                                for (int j = 0; j < ListGendersComplete.size(); j++) {
                                    String key = String.valueOf(ListGendersComplete.get(j).getGe_id());
                                    String value = ListGendersComplete.get(j).getGe_label();
                                    if (key.equals(genderUser)) {
                                        genderToDisplay = value;
                                    }
                                }
                            */

                                if (genderUser.equals("1")){
                                    ivGender.setImageResource(R.drawable.ic_female);
                                }else if (genderUser.equals("2")){
                                    ivGender.setImageResource(R.drawable.ic_male);
                                }else {
                                    ivGender.setImageResource(R.drawable.ic_transgenre);
                                }



                        } else {
                            Toast.makeText(getContext(), "Any Document", Toast.LENGTH_SHORT).show();
                        }


                        userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshotConnected) {
                                if (documentSnapshotConnected.exists()) {
                                    ModelUsers contenuUser = documentSnapshotConnected.toObject(ModelUsers.class);
                                    assert contenuUser != null;

                                    usRole = contenuUser.getUs_role();
                                    usNephew = contenuUser.getUs_nephews();
                                    usGodfather = contenuUser.getUs_godfather();
                                    // Si le user connecté est le même que le user à afficher (VOIR MON PROFIL) , on affiche le bouton Update simplement
                                    if (documentSnapshotDisplayed.getId().equals(documentSnapshotConnected.getId()) ){
                                        btnUpdateProfil.setVisibility(View.VISIBLE);
                                        // Sinon on affiche d'autres boutons
                                    }else{
                                        // Si le user connecté est un Parrain, on affiche les boutons qui vont bien
                                        if (usRole.equals(2L)) {
                                            Log.e(TAG, "onSuccess: " + usNephew );
                                            Log.e(TAG, "onSuccess: " + documentSnapshotDisplayed.getId() );
                                            // Si le profil consulté est le filleul du parrain,
                                            if (usNephew.equals(documentSnapshotDisplayed.getId())){
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
                                            // Si le user connecté est un Célibataire, on affiche les boutons qui vont bien
                                        } else {
                                            // Si le profil consulté est le parrain du filleul
                                            if (usGodfather.equals(documentSnapshotDisplayed.getId())){
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