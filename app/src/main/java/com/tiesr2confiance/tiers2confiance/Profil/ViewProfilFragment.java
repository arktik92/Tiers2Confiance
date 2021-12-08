package com.tiesr2confiance.tiers2confiance.Profil;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_BALANCE;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_CITY;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_DESCRIPTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_ETHNIE;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_EYE_COLOR;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_FS_COLLECTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_GENDER;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HAIR_COLOR;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HAIR_LENGTH;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HOBBIES;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_AVATAR;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_MARITAL_STATUS;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_NICKNAME;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_PERSONALITY;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_PHOTOS;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_ROLE;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SEXUAL_ORIENTATION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SHAPE;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SMOKE;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SPORTS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.tiesr2confiance.tiers2confiance.Models.ModelEthnicGroup;
import com.tiesr2confiance.tiers2confiance.Models.ModelEyeColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairLength;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelMaritalStatus;
import com.tiesr2confiance.tiers2confiance.Models.ModelPersonality;
import com.tiesr2confiance.tiers2confiance.Models.ModelSexualOrientation;
import com.tiesr2confiance.tiers2confiance.Models.ModelShapes;
import com.tiesr2confiance.tiers2confiance.Models.ModelSmoker;
import com.tiesr2confiance.tiers2confiance.Models.ModelSports;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentViewProfilBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class ViewProfilFragment extends Fragment {

    public static final String TAG = "View Profile";
    private static final String TAGAPP = "LOGAPP";

    private TextView tvUserAge, tvProfilName, tvRole, tvPresentation, tvProfilCity, tvPersonality, tvSports;
    private TextView tvHobbies, tvBalance, tvEthnicGroup, tvEyeColor, tvHairColor, tvHairLength, tvSmoker, tvSexualOrient, tvMaritalStatus, tvShape;
    private ImageView ivProfilAvatarShape, ivGender;
    private Button btnPflCrediter, btnPflEnvoyer, btnLinkSupp, btnLinkRequest, btnLinkSuppTiers, btnLinkRequestTiers, btnUpdateProfil, btnAcceptNephew, btnAcceptGodfather, btnAcceptMatch ;
    private LinearLayout llProfil;
    private RecyclerView rvListPhotos;
    private ViewPhotosAdapter adapterPhotos;
    private LinearLayout
            llPresentation
            , llHobbies
            , llPersonality
            , llSports
            , llEthnicGroup
            , llShape
            , llEyeColor
            , llHairColor
            , llHairLength
            , llMaritalStatus
            , llSexualOrientation
            , llSmoker;



    /*** BDD ***/
    private FirebaseFirestore db;

    /** ID Document To Displayed **/
    private String userId;
    private DocumentReference userDisplayed;

    /** ID Document Connected **/
    private FirebaseUser currentUser;
    private DocumentReference userConnected;
    private DocumentReference userNephew;

    /** Variables **/
    String nickname;
    String city;
    String imgPhotos;
    String imgUrlAvatar;
    String description;
    String listHobbies;
    String listPersonality;
    String listSports;
    String genderUser;

    Long   role;
    Long   balance;

    long ethnicGroupId;
    long eyeColorId;
    long hairColorId;
    long hairLengthId;
    long sexualOrientationId;
    long maritalStatusId;
    long smokerId;
    long shapeId;


    private Long   usRole;
    private String usNephew;
    private String usGodfather;
    private String usNephewRequestFrom;
    private String usGodfatherRequestFrom;
    private String usMatchsRequestFrom;
    private String usMatchsRequestTo;

    String upadtedField;
    String userAge;



    private FragmentViewProfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //public String arg = getArguments().getString("");
        View view = inflater.inflate(R.layout.fragment_view_profil, container, false);

        // Récupération de l'ID de l'utilisateur à afficher
        Bundle bundle = getArguments();
        String myStrings =bundle.getString("idUser");
        userId = myStrings;



        // userDisplayed, récupération de l'utilisateur à afficher
        db = FirebaseFirestore.getInstance();
        userDisplayed = db.document(KEY_FS_COLLECTION + "/" + userId);

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

        ivProfilAvatarShape = view.findViewById(R.id.iv_profil_avatar_shape);
        tvProfilName = view.findViewById(R.id.tv_profil_name);
        tvUserAge   =view.findViewById(R.id.tv_User_Age);
        ivGender = view.findViewById(R.id.iv_gender);
        tvRole = view.findViewById(R.id.tv_role_display);
        tvProfilCity = view.findViewById(R.id.tv_profil_city);
        tvPresentation = view.findViewById(R.id.tv_presentation);
        tvPersonality = view.findViewById(R.id.tv_personnality);
        tvSports = view.findViewById(R.id.tv_sport);


        // Par defaut, on masque toute la partie du profil CELIBATAIRE, On affiche seulement si le rôle est 1.
        llProfil = view.findViewById(R.id.ll_profil);
        llProfil.setVisibility(View.GONE);
        llHobbies           = view.findViewById(R.id.ll_hobbies);
        llPresentation      = view.findViewById(R.id.ll_presentation);
        llPersonality       = view.findViewById(R.id.ll_personality);
        llSports            = view.findViewById(R.id.ll_sports);
        llEthnicGroup       = view.findViewById(R.id.ll_ethnic_group);
        llShape             = view.findViewById(R.id.ll_shape );
        llEyeColor          = view.findViewById(R.id.ll_eye_color);
        llHairColor         = view.findViewById(R.id.ll_hair_color);
        llHairLength        = view.findViewById(R.id.ll_hair_length);
        llMaritalStatus     = view.findViewById(R.id.ll_marital_status);
        llSexualOrientation = view.findViewById(R.id.ll_sexual_orientation);
        llSmoker            = view.findViewById(R.id.ll_smoker);

        rvListPhotos = view.findViewById(R.id.rv_list_photos);
        rvListPhotos.setVisibility(view.GONE);
        rvListPhotos.setHasFixedSize(true);
        rvListPhotos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        tvBalance = view.findViewById(R.id.tv_balance);
        tvEthnicGroup = view.findViewById(R.id.tv_ethnic_group);
        tvEyeColor = view.findViewById(R.id.tv_eyes_color);
        tvHairColor = view.findViewById(R.id.tv_hair_color);
        tvHairLength = view.findViewById(R.id.tv_hair_length);
        tvSmoker= view.findViewById(R.id.tv_smoker);
        tvSexualOrient = view.findViewById(R.id.tv_sexual_orientation);
        tvShape= view.findViewById(R.id.tv_shape);
        tvMaritalStatus = view.findViewById(R.id.tv_marital_status);




        btnPflCrediter = view.findViewById(R.id.btn_pfl_crediter);
        btnPflEnvoyer = view.findViewById(R.id.btn_pfl_envoyer);
        btnAcceptMatch =view.findViewById(R.id.btn_accept_match);
        btnLinkSupp = view.findViewById(R.id.btn_link_supp);
        btnLinkRequest = view.findViewById(R.id.btn_link_request);
        btnLinkSuppTiers = view.findViewById(R.id.btn_link_supp_tier);
        btnLinkRequestTiers= view.findViewById(R.id.btn_link_request_tiers);
        btnUpdateProfil = view.findViewById(R.id.btn_update_profil);
        btnAcceptNephew = view.findViewById(R.id.btn_accept_nephew);
        btnAcceptGodfather = view.findViewById(R.id.btn_accept_godfather);

        // Les boutons n'existent pas dans le Layout à l'initialisation, on les affiche seulement si necessaire
        btnPflCrediter.setVisibility(View.GONE);
        btnPflEnvoyer.setVisibility(View.GONE);
        btnAcceptMatch.setVisibility(View.GONE);
        btnLinkSupp.setVisibility(View.GONE);
        btnLinkRequest.setVisibility(View.GONE);
        btnLinkSuppTiers.setVisibility(View.GONE);
        btnLinkRequestTiers.setVisibility(View.GONE);
        btnUpdateProfil.setVisibility(View.GONE);
        btnAcceptNephew.setVisibility(View.GONE);
        btnAcceptGodfather.setVisibility(View.GONE);


        // ************************************   ACTIONS BOUTONS _  ROLE = PARRAIN *****************************************************

        // Le parrain connecté est redirigé vers le fragment permettant de créditer son filleul
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


        // Le parrain connecté supprime le lien avec son filleul
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
                btnLinkSupp.setText("Lien supprimé");
                btnLinkSupp.setEnabled(false);
            }
        });

        // Le parrain connecté envoie ce profil de célibataire à son filleul
        btnPflEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    ModelUsers contenuUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                    assert contenuUser != null;
                                    // userNephew, récupération du filleul du parrain connecté
                                    userNephew = db.document(KEY_FS_COLLECTION + "/" + contenuUser.getUs_nephews());
                                    userNephew.get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()  {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            ModelUsers nephewUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                            assert nephewUser != null;
                                            String usMatchRequestTo = nephewUser.getUs_matchs_request_to();
                                            userDisplayed.get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            ModelUsers celibUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                                            assert celibUser != null;
                                                            String usMatchRequestFrom = celibUser.getUs_matchs_request_from();
                                                            // Ici on ajoute les IDs respectifs des deux célibataires à matcher dans la list des demandes (From ou To selon si le célibataire est la cible ou le filleul d parrain connecté)
                                                            userDisplayed.update("us_matchs_request_from", usMatchRequestFrom + userNephew.getId()+  ";");
                                                            userNephew.update("us_matchs_request_to", usMatchRequestTo + userDisplayed.getId()+  ";");
                                                        }
                                                    });
                                        }
                                    });
                                    btnPflEnvoyer.setText("Demande de match envoyée");
                                    btnPflEnvoyer.setEnabled(false);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: " + "Error Connected User" );
                            }
                        });
            }
        });

        // Le parrain connecté demande à un célibataire d'être son parrain (Parrain en recherche de filleul)
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
                btnLinkRequest.setText("Demande envoyée");
                btnLinkRequest.setEnabled(false);
            }
        });


        // Le parrain connecté accepte d'être le parrain du célibataire qui lui a demandé
        btnAcceptNephew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userConnected.update("us_godfather_request_to", contenuUser.getUs_godfather_request_to() + userDisplayed.getId()+  ";");
                            userDisplayed.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            userConnected.update("us_nephews", contenuUser.getUs_nephews_request_to() + userDisplayed.getId()+  ";");
                                            // Replace
                                            String ListDemands = contenuUser.getUs_nephews_request_from();
                                            String ListDemandsNew = ListDemands.replace(userDisplayed.getId() + ";", "");
                                            userConnected.update("us_nephews_request_from", ListDemandsNew );

                                            userDisplayed.update("us_godfather", userConnected.getId() );
                                            userDisplayed.update("us_godfather_request_to", "");
                                            Log.i(TAG, "LOGPGO Demande du célibataire acceptée par le parrain");
//                                    Log.e(TAG, "Demande du célibataire acceptée par le parrain");
                                        }
                                    });
                        }
                    }
                });
                btnAcceptNephew.setText("Lien accepté");
                btnAcceptNephew.setEnabled(false);
            }
        });

        // ************************************   ACTIONS BOUTONS _  ROLE = CELIBATAIRE *****************************************************

        // Le célibataire connecté supprime le lien avec son parrain
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
                btnLinkSuppTiers.setText("Lien supprimé");
                btnLinkSuppTiers.setEnabled(false);
            }
        });

        // Le célibataire accepte un match proposé par son parrain, ou reçu d'un autre parrain
        btnAcceptMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userConnected.update("us_matchs_request_to", contenuUser.getUs_matchs_request_to().replace(userDisplayed.getId() + ";", ""));
                            userConnected.update("us_matchs_request_from", contenuUser.getUs_matchs_request_from().replace(userDisplayed.getId() + ";", ""));
                            userDisplayed.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            ModelUsers contenuDisplayedUser = documentSnapshot.toObject(ModelUsers.class);
                                            assert contenuDisplayedUser != null;
                                            userConnected.update("us_matchs", contenuUser.getUs_matchs() + userDisplayed.getId() +";" );
                                            userDisplayed.update("us_matchs", contenuDisplayedUser.getUs_matchs() +  userConnected.getId() + ";");
                                            userDisplayed.update("us_matchs_request_to", contenuDisplayedUser.getUs_matchs_request_to().replace(userConnected.getId()+ ";", "") );
                                            userDisplayed.update("us_matchs_request_from", contenuDisplayedUser.getUs_matchs_request_from().replace(userConnected.getId()+ ";", "") );
                                        }
                                    });
                        }
                    }
                });
                btnAcceptMatch.setText("Match accepté");
                btnAcceptMatch.setEnabled(false);
            }
        });

        // Le célibataire connecté demande à être parrainé par l'utilisateur du profil
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
                btnLinkRequestTiers.setText("Demande envoyée");
                btnLinkRequestTiers.setEnabled(false);
            }
        });

        // Le célibataire connecté accepte d'être le filleul du tier qui lui a demandé
        btnAcceptGodfather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                            assert contenuUser != null;
                            userConnected.update("us_godfather_request_to", contenuUser.getUs_godfather_request_to().replace(userDisplayed.getId() + ";", ""));
                            userDisplayed.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            userConnected.update("us_godfather", userDisplayed.getId() );
                                            // Replace
                                            String ListDemands = contenuUser.getUs_godfather_request_from();
                                            String ListDemandsNew = ListDemands.replace(userDisplayed.getId()+ ";", "");
                                            userConnected.update("us_godfather_request_from", ListDemandsNew);

                                            userDisplayed.update("us_nephews",   userConnected.getId());
                                            userDisplayed.update("us_nephews_request_to", "" ); // Replace
                                            Log.i(TAG, "LOGPGO Demande du parrain acceptée par le célibataire");
                                        }
                                    });
                        }
                    }
                });
                btnAcceptGodfather.setText("Lien accepté");
                btnAcceptGodfather.setEnabled(false);
            }
        });


        // ************************************   ACTIONS BOUTONS _  LES DEUX ROLES = CELIBATAIRE OU PARRAIN *****************************************************

        // L'utilisateur connecté est redirigé vers la modification de son profil
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

        InitComponents(view);
    }

    private void InitComponents(View v) {
        InitLlPresentation(v);
        InitLlHobbies(v);
        InitLlPersonality(v);
        InitLlSports(v);
        InitLlEthnicGroup(v);
        InitLlEyeColor(v);
        InitLlHairColor(v);
        InitLlHairLength(v);
        InitLlMaritalStatus(v);



        InitLlSmoker(v);
        InitLlShape(v);

    }


    private void InitLlPresentation(View v) {
        tvPresentation = v.findViewById(R.id.tv_presentation);
        llPresentation = v.findViewById(R.id.ll_presentation);

        llPresentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String presentation =  tvPresentation.getText().toString();

                Bundle data = new Bundle();
                data.putString("presentation", presentation);
                data.putString("userid", userId);

                Fragment myFragment = new UpdatePresentationFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llPresentation.setClickable(false);
    }


    private void InitLlHobbies(View v) {
        tvHobbies = v.findViewById(R.id.tvHobbies);
        llHobbies =v.findViewById(R.id.ll_hobbies);

        llHobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userattributesstring =  listHobbies;
                upadtedField                =   KEY_HOBBIES;

                Bundle data = new Bundle();
                data.putString("userattributesstring", userattributesstring);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);

                Fragment  myFragment= new UpdateMultiChoiceFragment();
                OpenFragment(v, data, myFragment);
            }
        });
    }

    private void InitLlPersonality(View v) {
        tvPersonality = v.findViewById(R.id.tv_personnality);
        llPersonality =v.findViewById(R.id.ll_personality);

        llPersonality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userattributesstring =  listPersonality;
                upadtedField                =   KEY_PERSONALITY;

                Bundle data = new Bundle();
                data.putString("userattributesstring", userattributesstring);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);

                Fragment  myFragment= new UpdateMultiChoiceFragment();
                OpenFragment(v, data, myFragment);
            }
        });
    }


    private void InitLlSports(View v) {
        tvSports = v.findViewById(R.id.tv_sport);
        llSports = v.findViewById(R.id.ll_sports);

        llSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userattributesstring =  listSports;
                upadtedField                =   KEY_SPORTS;

                Bundle data = new Bundle();
                data.putString("userattributesstring", userattributesstring);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);

                Fragment  myFragment= new UpdateMultiChoiceFragment();
                OpenFragment(v, data, myFragment);
            }
        });
    }





    private void InitLlEthnicGroup(View v) {
        tvEthnicGroup   = v.findViewById(R.id.tv_ethnic_group);
        llEthnicGroup   = v.findViewById(R.id.ll_ethnic_group);

        llEthnicGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id =  String.valueOf(ethnicGroupId);
                upadtedField                =   KEY_ETHNIE;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llEthnicGroup.setClickable(false);
    }

    private void InitLlEyeColor(View v) {
        tvEyeColor = v.findViewById(R.id.tv_eyes_color);
        llEyeColor   = v.findViewById(R.id.ll_eye_color);

        llEyeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id   =  String.valueOf(eyeColorId);
                upadtedField=   KEY_EYE_COLOR;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llEthnicGroup.setClickable(false);
    }


    private void InitLlHairColor(View v) {
        tvHairColor = v.findViewById(R.id.tv_hair_color);
        llHairColor   = v.findViewById(R.id.ll_hair_color);

        llHairColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id   =  String.valueOf(hairColorId);
                upadtedField=   KEY_HAIR_COLOR;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llEthnicGroup.setClickable(false);
    }


    private void InitLlHairLength(View v) {
        tvHairLength = v.findViewById(R.id.tv_hair_length);
        llHairLength   = v.findViewById(R.id.ll_hair_length);

        llHairLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id   =  String.valueOf(hairLengthId);
                upadtedField=   KEY_HAIR_LENGTH;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llEthnicGroup.setClickable(false);
    }

    private void InitLlMaritalStatus(View v) {
        tvMaritalStatus = v.findViewById(R.id.tv_marital_status);
        llMaritalStatus   = v.findViewById(R.id.ll_marital_status);

        llMaritalStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id   =  String.valueOf(maritalStatusId);
                upadtedField=   KEY_MARITAL_STATUS;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llEthnicGroup.setClickable(false);
    }





    private void InitLlSmoker(View v) {
        tvSmoker   = v.findViewById(R.id.tv_smoker);
        llSmoker   = v.findViewById(R.id.ll_smoker);

        llSmoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id   =  String.valueOf(smokerId);
                upadtedField=   KEY_SMOKE;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llEthnicGroup.setClickable(false);
    }


    private void InitLlShape(View v) {
        tvShape   = v.findViewById(R.id.tv_shape);
        llShape   = v.findViewById(R.id.ll_shape);

        llShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id   =  String.valueOf(shapeId);
                upadtedField=   KEY_SHAPE;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
//        llEthnicGroup.setClickable(false);
    }

    private void OpenFragment(View v, Bundle dataBundle, Fragment myFragment) {

        myFragment.setArguments(dataBundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment)
                .addToBackStack(null)
                .commit();
    }



    public void showProfil() {

        userDisplayed.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshotDisplayed) {
                        if (documentSnapshotDisplayed.exists()) {

                            try {
                                ModelUsers currentUser = documentSnapshotDisplayed.toObject(ModelUsers.class);
                                Date userDateOfBirth    = currentUser.getUs_birth_date();

                                String ageStr   =   String.valueOf(currentUser.getUs_age()) + " " + getString(R.string.lbl_ans);
                                tvUserAge.setText(ageStr);

                                // Variables communes
                                nickname = documentSnapshotDisplayed.getString(KEY_NICKNAME);
                                city = documentSnapshotDisplayed.getString(KEY_CITY);
                                imgUrlAvatar = documentSnapshotDisplayed.getString(KEY_AVATAR);
                                description = documentSnapshotDisplayed.getString(KEY_DESCRIPTION);
                                genderUser = String.valueOf(documentSnapshotDisplayed.getLong(KEY_GENDER));
                                role = documentSnapshotDisplayed.getLong(KEY_ROLE);


                                // Variables spéciales célibataires
                                imgPhotos = documentSnapshotDisplayed.getString(KEY_PHOTOS);
                                listHobbies = documentSnapshotDisplayed.getString(KEY_HOBBIES);
                                listPersonality= documentSnapshotDisplayed.getString(KEY_PERSONALITY);
                                listSports = documentSnapshotDisplayed.getString(KEY_SPORTS);

                                ethnicGroupId = documentSnapshotDisplayed.getLong(KEY_ETHNIE);
                                eyeColorId = documentSnapshotDisplayed.getLong(KEY_EYE_COLOR);
                                hairColorId = documentSnapshotDisplayed.getLong(KEY_HAIR_COLOR);
                                hairLengthId = documentSnapshotDisplayed.getLong(KEY_HAIR_LENGTH);
                                sexualOrientationId = documentSnapshotDisplayed.getLong(KEY_SEXUAL_ORIENTATION);
                                maritalStatusId = documentSnapshotDisplayed.getLong(KEY_MARITAL_STATUS);
                                smokerId = documentSnapshotDisplayed.getLong(KEY_SMOKE);
                                shapeId = documentSnapshotDisplayed.getLong(KEY_SHAPE);
                                balance = documentSnapshotDisplayed.getLong(KEY_BALANCE);
                            }catch(Exception e){
                                Log.e(TAG, "Error on getting documentSnapshotDisplayed data : ", e);
                            }

                            String ethnie_val="--";
                            String colorEye_val="--";
                            String colorHair_val="--";
                            String lenghHair_val="--";
                            String sexualOrient_val="--";
                            String maritalStatus_val="--";
                            String smoker_val="--";
                            String shape_val="--";
                            balance = 0L;



                            /** ON AFFICHE LES INFORMATION COMMUNES **/
                            tvProfilCity.setText(city);
                            tvProfilName.setText(nickname);
                            // Le genre
                            if (genderUser.equals("1")){
                                ivGender.setImageResource(R.drawable.ic_male);
                            }else if (genderUser.equals("2")){
                                ivGender.setImageResource(R.drawable.ic_female);
                            }else {
                                ivGender.setImageResource(R.drawable.ic_transgenre);
                            }
                            tvPresentation.setText(description);
                            /** L'avatar : Glide - Add Picture **/
                            Context context = getContext();
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .error(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher);
                            /** Loading Avatar **/
                            Glide
                                    .with(context)
                                    .load(imgUrlAvatar)
                                    .apply(options)
                                    .fitCenter()
                                    .circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfilAvatarShape);


                            /** ON DIFFERENCIE SELON LE ROLE **/
                            if (role.equals(2L)){
                                // Si l'utilisateur connecté est un celib et donc...
                                // si l'utilisateur à afficher est Tiers de confiance (parrain)...

//                                tvRole.setText("Tiers");
                                tvRole.setText(getString(R.string.lbl_tiers));
                            }else{
                                ArrayList<String> imgPhotosList = new ArrayList<>();

                                if (imgPhotos != "" & imgPhotos != ";"){
                                    imgPhotosList.addAll(Arrays.asList(imgPhotos.split(";")));
                                }

                                if(imgPhotosList.size() == 0){
                                    rvListPhotos.setVisibility(View.GONE);

                                }else{
                                    rvListPhotos.setVisibility(View.VISIBLE);
                                    adapterPhotos = new ViewPhotosAdapter(getContext(), imgPhotosList);
                                    rvListPhotos.setAdapter(adapterPhotos);
                                }



                                //tvRole.setText("Célib");
                                tvRole.setText(getString(R.string.lbl_celibataire));
                                llProfil.setVisibility(View.VISIBLE);
                                tvBalance.setText(String.valueOf(balance));
                                // Ici, on récupère tous les attributs.caractériques de l'utilisateur à afficher (on récupère les ID des valeurs, qu'on va comparer avec les listes complètes chargées par la Class Globale)
                                String split_key = ";";
                                String[] hobbiesListUser = listHobbies.split(split_key);

                                // Appel de la classe global pour charger les listes d'attributs
                                final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
                                ArrayList<ModelHobbies> ListHobbiesComplete = globalVariables.getArrayListHobbies();
                                ArrayList<ModelPersonality> ListPersonalityComplete = globalVariables.getArrayListPersonnality();
                                ArrayList<ModelSports> ListSportsComplete = globalVariables.getArrayListSports();
                                ArrayList<ModelEthnicGroup> ListEthnicComplete = globalVariables.getArrayListEthnicGroup();
                                ArrayList<ModelEyeColor> ListEyeColorComplete = globalVariables.getArrayListEyeColors();
                                ArrayList<ModelHairColor> ListHairColorComplete = globalVariables.getArrayListHairColor();
                                ArrayList<ModelHairLength> ListHairLengthComplete = globalVariables.getArrayListHairLength();
                                ArrayList<ModelMaritalStatus> ListMaritalStatusComplete = globalVariables.getArrayListMaritalStatus();
                                ArrayList<ModelSexualOrientation> ListSexualOrientationComplete = globalVariables.getArrayListSexualOrientation();
                                ArrayList<ModelSmoker> ListSmokerComplete = globalVariables.getArrayListSmoker();
                                ArrayList<ModelShapes> ListShapeComplete = globalVariables.getArrayListShapes();


                                // HOBBIES VALEURS : Affichage des hobbies, comparaison de la liste des hobbies de l'utilisateur avec la liste complète chargée

                                String hobbiesToDisplay="-- ";
                                for (int i=0; i< hobbiesListUser.length;i++) {
                                    for (int j = 0; j < ListHobbiesComplete.size(); j++) {
                                        String key = String.valueOf(ListHobbiesComplete.get(j).getHo_id());
                                        String value = ListHobbiesComplete.get(j).getHo_label();
                                        String hobbieLabel = ListHobbiesComplete.get(j).getHo_label();
                                        if (key.equals(hobbiesListUser[i])) {
                                            hobbiesToDisplay += value + " -- ";
                                            TextView tvAttribute = new TextView(getActivity().getApplicationContext());
                                            tvAttribute.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            tvAttribute.setText(hobbieLabel);
                                            llHobbies.addView(tvAttribute);
                                        }
                                    }
                                }
                                tvHobbies.setVisibility(View.GONE);
                                tvHobbies.setText(hobbiesToDisplay);



                                // PERSONALITY
                                String[] personalityListUser = listPersonality.split(split_key);

                                String personalityToDisplay="-- ";
                                for (int i=0; i< personalityListUser.length;i++) {
                                    for (int j = 0; j < ListPersonalityComplete.size(); j++) {
                                        String key = String.valueOf(ListPersonalityComplete.get(j).getPe_id());
                                        String value = ListPersonalityComplete.get(j).getPe_label();
                                        String personalityLabel = ListPersonalityComplete.get(j).getPe_label();
                                        if (key.equals(personalityListUser[i])) {
                                            personalityToDisplay += value + " -- ";
                                            TextView tvAttribute = new TextView(getActivity().getApplicationContext());
                                            tvAttribute.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            tvAttribute.setText(personalityLabel);
                                            llPersonality.addView(tvAttribute);
                                        }
                                    }
                                }
                                tvPersonality.setVisibility(View.GONE);
                                tvPersonality.setText(personalityToDisplay);



                                // SPORTS
                                String[] sportsListUser = listSports.split(split_key);

                                String sportsToDisplay="-- ";
                                for (int i=0; i< sportsListUser.length;i++) {
                                    for (int j = 0; j < ListSportsComplete.size(); j++) {
                                        String key = String.valueOf(ListSportsComplete.get(j).getSp_id());
                                        String value = ListSportsComplete.get(j).getSp_label();
                                        String sportLabel = ListSportsComplete.get(j).getSp_label();
                                        if (key.equals(sportsListUser[i])) {
                                            sportsToDisplay += value + " -- ";
                                            TextView tvAttribute = new TextView(getActivity().getApplicationContext());
                                            tvAttribute.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            tvAttribute.setText(sportLabel);
                                            llSports.addView(tvAttribute);
                                        }
                                    }
                                }
                                tvSports.setVisibility(View.GONE);
                                tvSports.setText(sportsToDisplay);





                                // ETHNIC : Affichage de l'ethnie, comparaison de la valeur de l'utilisateur avec la liste complète chargée
                                for (int j = 0; j < ListEthnicComplete.size(); j++) {
                                    long key = ListEthnicComplete.get(j).getEt_id();
                                    String value = ListEthnicComplete.get(j).getEt_label();
                                    if (key == ethnicGroupId) {
                                        ethnie_val = value;
                                    }
                                }
                                tvEthnicGroup.setText(ethnie_val);

                                // COULEUR YEUX : Affichage de de la couleur des yeux avec la liste complète chargée
                                for (int j = 0; j < ListEyeColorComplete.size(); j++) {
                                    long key = ListEyeColorComplete.get(j).getEy_id();
                                    String value = ListEyeColorComplete.get(j).getEy_label();
                                    if (key == eyeColorId) {
                                        colorEye_val = value;
                                    }
                                }
                                tvEyeColor.setText(colorEye_val);

                                // COULEUR CHEVEUX : Affichage de la couleur des cheveux avec la liste complète chargée
                                for (int j = 0; j < ListHairColorComplete.size(); j++) {
                                    long key = ListHairColorComplete.get(j).getHc_id();
                                    String value = ListHairColorComplete.get(j).getHc_label();
                                    if (key == hairColorId) {
                                        colorHair_val = value;
                                    }
                                }
                                tvHairColor.setText(colorHair_val);

                                // LONGUEUR CHEVEUX : Affichage de la longeur des cheveux comparaison de la valeur de l'utilisateur avec la liste complète chargée
                                for (int j = 0; j < ListHairLengthComplete.size(); j++) {
                                    long key = ListHairLengthComplete.get(j).getHl_id();
                                    String value = ListHairLengthComplete.get(j).getHl_label();
                                    if (key == hairLengthId) {
                                        lenghHair_val = value;
                                    }
                                }
                                tvHairLength.setText(lenghHair_val);

                                // STATUS MARITAL : Affichage du statut marital de l'utilisateur avec la liste complète chargée
                                for (int j = 0; j < ListMaritalStatusComplete.size(); j++) {
                                    long key = ListMaritalStatusComplete.get(j).getMa_id();
                                    String value = ListMaritalStatusComplete.get(j).getMa_label();
                                    if (key == maritalStatusId) {
                                        maritalStatus_val = value;
                                    }
                                }
                                tvMaritalStatus.setText(maritalStatus_val);

                                // ORIENTATION SEXUEL : Affichage de l'orientation sexuelle de l'utilisateur avec la liste complète chargée
                                for (int j = 0; j < ListSexualOrientationComplete.size(); j++) {
                                    long key = ListSexualOrientationComplete.get(j).getSe_id();
                                    String value = ListSexualOrientationComplete.get(j).getSe_label();
                                    if (key == sexualOrientationId) {
                                        sexualOrient_val = value;
                                    }
                                }
                                tvSexualOrient.setText(sexualOrient_val);

                                // FUMEUR : Affichage du fumeur, comparaison de la valeur de l'utilisateur avec la liste complète chargée
                                for (int j = 0; j < ListSmokerComplete.size(); j++) {
                                    long key = ListSmokerComplete.get(j).getSm_id();
                                    String value = ListSmokerComplete.get(j).getSm_label();
                                    if (key == smokerId) {
                                        smoker_val = value;
                                    }
                                }
                                tvSmoker.setText(smoker_val);

                                // SILHOUETTE : Affichage de la silhouette, comparaison de la valeur de l'utilisateur avec la liste complète chargée
                                for (int j = 0; j < ListShapeComplete.size(); j++) {
                                    long key = ListShapeComplete.get(j).getSh_id();
                                    String value = ListShapeComplete.get(j).getSh_label();
                                    if (key == shapeId) {
                                        shape_val = value;
                                    }
                                }
                                tvShape.setText(shape_val);
                            }

                        } else {
                            Toast.makeText(getContext(), "Any Document", Toast.LENGTH_SHORT).show();
                        }


                        // ************************   QUELS SONT LES BOUTONS AFFICHES EN FONCTION DU CONTEXTE ?????? *************************

                        userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshotConnected) {
                                if (documentSnapshotConnected.exists()) {
                                    ModelUsers contenuUser = documentSnapshotConnected.toObject(ModelUsers.class);
                                    assert contenuUser != null;

                                    usRole                  = contenuUser.getUs_role();
                                    usNephew                = contenuUser.getUs_nephews();
                                    usNephewRequestFrom     = contenuUser.getUs_nephews_request_from();
                                    usGodfather             = contenuUser.getUs_godfather();
                                    usMatchsRequestFrom     = contenuUser.getUs_matchs_request_from();
                                    usMatchsRequestTo       = contenuUser.getUs_matchs_request_to();
                                    usGodfatherRequestFrom  = contenuUser.getUs_godfather_request_from();
                                    // Si le user connecté est le même que le user à afficher (VOIR MON PROFIL) , on affiche le bouton Update simplement
                                    if (documentSnapshotDisplayed.getId().equals(documentSnapshotConnected.getId()) ){



                                        //btnUpdateProfil.setVisibility(View.VISIBLE);
                                        llPresentation.setClickable(true);


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
                                                    if (usNephewRequestFrom.indexOf(documentSnapshotDisplayed.getId()) == -1 ){
                                                        // Sinon on peut demander au célibataire de le parrainer si on n'a pas de filleul
                                                        btnLinkRequest.setVisibility(View.VISIBLE);
                                                    } else{
                                                        // On peut accepter la demande du célibataire
                                                        btnAcceptNephew.setVisibility(View.VISIBLE);
                                                    }
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
                                            }else if (TextUtils.isEmpty(usGodfather) == false){
                                                Log.e(TAG, "onSuccess: false" + usMatchsRequestFrom + " et " + usMatchsRequestTo);
                                                if (usMatchsRequestFrom.indexOf(documentSnapshotDisplayed.getId()) >= 0 || usMatchsRequestTo.indexOf(documentSnapshotDisplayed.getId()) >= 0 ){
                                                    btnAcceptMatch.setVisibility(View.VISIBLE);
                                                }
                                            }
                                                else{
                                                    if (usGodfatherRequestFrom.indexOf(documentSnapshotDisplayed.getId())== -1){
                                                        // Sinon on peur faire une demande au parrain si on a pas de parrain
                                                        btnLinkRequestTiers.setVisibility(View.VISIBLE);
                                                    }else {
                                                        // On peut accepter la demande du parrain
                                                        btnAcceptGodfather.setVisibility(View.VISIBLE);
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
