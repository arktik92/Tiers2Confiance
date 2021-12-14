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
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.NODE_CHATLIST;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.NODE_CHATS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.tiesr2confiance.tiers2confiance.Login.CreationProfilActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelChat;
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

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ViewProfilFragment extends Fragment {

    public static final String TAG = "View Profile";
    private static final String TAGAPP = "LOGAPP";

    private static int REQUEST_IMAGE_CAPTURE = 1;
    private static int REQUEST_IMAGE_CAMERA_CAPTURE = 100;

    /** ---------------- DECLARATION DES VARIABLES ------------------------ **/

    /** Champs Commun **/
    private TextView tvUserAge, tvProfilName, tvPresentation, tvProfilCity;
    private ImageView ivProfilAvatarShape, ivGender;

    /** Champs Celibataire **/
    private TextView tvPersonality, tvSports,tvHobbies, tvBalance, tvEthnicGroup, tvEyeColor, tvHairColor, tvHairLength, tvSmoker, tvSexualOrient, tvMaritalStatus, tvShape;

     /** Champs Edit (crayon) **/
    private TextView tvPersonalityEdit, tvSportsEdit,tvHobbiesEdit, tvPresentationEdit, tvEthnicGroupEdit, tvEyeColorEdit, tvHairColorEdit, tvHairLengthEdit, tvSmokerEdit, tvSexualOrientEdit, tvMaritalStatusEdit, tvShapeEdit;
    private Button btnAddPhoto;

    /** Boutons Action   **/
    private Button btnPflCrediter, btnPflEnvoyer, btnLinkSupp, btnLinkRequest, btnLinkSuppTiers, btnLinkRequestTiers, btnUpdateProfil, btnAcceptNephew, btnAcceptGodfather, btnAcceptMatch ;

    private RecyclerView rvListPhotos;
    private ViewPhotosAdapter adapterPhotos;
    private LinearLayout
            llPhoto
            , llPresentation
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
            , llSmoker
            , llBalance;

    /*** BDD ***/
    private FirebaseFirestore db;

    /** ID Document To Displayed **/
    private String userId;
    private DocumentReference userDisplayed;

    /** ID Document Connected **/
    private FirebaseUser currentUser;
    private DocumentReference userConnected;
    private DocumentReference userNephew;

    // Chat
    private CollectionReference chatCollectionRef;
    private CollectionReference chatListCollectionRef;

    /** Photos **/
    StorageReference storageReference;
    public Uri imageUri, imageCameraUri;
    final String randomKey = UUID.randomUUID().toString();
    String fileName = randomKey + ".jpg";
    String imageType;

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
        storageReference = FirebaseStorage.getInstance().getReference();

        // currentUser, récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = db.collection(KEY_FS_COLLECTION).document(currentUser.getUid());

        // récupération de la collection CHAT
        chatCollectionRef = db.collection(NODE_CHATS);
        chatListCollectionRef = db.collection(NODE_CHATLIST);

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
        tvProfilCity = view.findViewById(R.id.tv_profil_city);
        tvPresentation = view.findViewById(R.id.tv_presentation);


        /** On instancie tous les éléments du layout **/
        llPhoto             = view.findViewById(R.id.ll_photo);
        llBalance           = view.findViewById(R.id.ll_balance);
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

        btnAddPhoto = view.findViewById(R.id.btn_add_photo);
        tvPersonalityEdit = view.findViewById(R.id.tv_personnality_edit);
        tvSportsEdit = view.findViewById(R.id.tv_sport_edit);
        tvHobbiesEdit = view.findViewById(R.id.tv_hobbies_edit);
        tvPresentationEdit = view.findViewById(R.id.tv_presentation_edit);
        tvEthnicGroupEdit= view.findViewById(R.id.tv_ethnic_edit);
        tvEyeColorEdit = view.findViewById(R.id.tv_eyes_color_edit);
        tvHairColorEdit = view.findViewById(R.id.tv_hair_color_edit);
        tvHairLengthEdit = view.findViewById(R.id.tv_hair_length_edit);
        tvSmokerEdit  = view.findViewById(R.id.tv_smoker_edit);
        tvSexualOrientEdit = view.findViewById(R.id.tv_sexual_orientation_edit);
        tvMaritalStatusEdit = view.findViewById(R.id.tv_marital_status_edit);
        tvShapeEdit = view.findViewById(R.id.tv_shape_edit);

        btnPflCrediter = view.findViewById(R.id.btn_pfl_crediter);
        btnPflEnvoyer = view.findViewById(R.id.btn_pfl_envoyer);
        btnAcceptMatch =view.findViewById(R.id.btn_accept_match);
        btnLinkSupp = view.findViewById(R.id.btn_link_supp);
        btnLinkRequest = view.findViewById(R.id.btn_link_request);
        btnLinkSuppTiers = view.findViewById(R.id.btn_link_supp_tier);
        btnLinkRequestTiers= view.findViewById(R.id.btn_link_request_tiers);
        btnAcceptNephew = view.findViewById(R.id.btn_accept_nephew);
        btnAcceptGodfather = view.findViewById(R.id.btn_accept_godfather);

        /** Par defaut, on masque toute la partie du profil CELIBATAIRE **/
        makeInvisible();
        /** Par defaut, on ne peut pas modifier les informations **/
        makeNotUpdate();

        // On initie les comosants à afficher
        InitComponents(view);



        /** ************************************   ACTIONS BOUTONS _  ROLE = PARRAIN ****************************************************** **/

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
                                            userConnected.update("us_nephews", userDisplayed.getId());
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

                                            if (contenuDisplayedUser.getUs_matchs_pending().contains(userConnected.getId())){
                                              userConnected.update("us_matchs_pending", contenuUser.getUs_matchs_pending() + userDisplayed.getId() +";" );
                                            } else {
                                                userConnected.update("us_matchs", contenuUser.getUs_matchs() + userDisplayed.getId() +";" );
                                                userDisplayed.update("us_matchs", contenuDisplayedUser.getUs_matchs() +  userConnected.getId() + ";");
                                                userDisplayed.update("us_matchs_pending", contenuDisplayedUser.getUs_matchs_pending().replace(userConnected.getId()+ ";", "") );

                                                // Upload du message dans la table Chats
                                                ModelChat newChat = new ModelChat(contenuUser.getUs_auth_uid(), userDisplayed.getId(), "message", false);
                                                long time = System.currentTimeMillis();
                                                String docId = String.valueOf(time);
                                                chatCollectionRef.document(docId).set(newChat)
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("TAG", "Error adding document", e);
                                                            }
                                                        });

                                                // Upload de la liaison des participants du chat en question dans Chatlist
                                                HashMap<String, Object> addUserToArrayMapConnected = new HashMap<>();
                                                HashMap<String, Object> addUserToArrayMapDisplayed = new HashMap<>();
                                                addUserToArrayMapDisplayed.put("id", FieldValue.arrayUnion(contenuUser.getUs_auth_uid()));
                                                addUserToArrayMapConnected.put("id", FieldValue.arrayUnion(userDisplayed.getId()));

                                                chatListCollectionRef
                                                        .document(currentUser.getUid())
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> taskConnected) {

                                                                chatListCollectionRef.document(userDisplayed.getId()).get()
                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> taskDisplayed) {
                                                                                // Création du chat dans chatList pour le utilisateur connecté
                                                                                if (!taskConnected.getResult().exists()) {
                                                                                    chatListCollectionRef.document(currentUser.getUid()).set(addUserToArrayMapConnected);
                                                                                } else {
                                                                                    chatListCollectionRef.document(currentUser.getUid()).update(addUserToArrayMapConnected);
                                                                                }
                                                                                // Création du chat dans chatList pour l'autre utilisateur
                                                                                if (!taskDisplayed.getResult().exists()) {
                                                                                    chatListCollectionRef.document(userDisplayed.getId()).set(addUserToArrayMapDisplayed);
                                                                                } else {
                                                                                    chatListCollectionRef.document(userDisplayed.getId()).update(addUserToArrayMapDisplayed);
                                                                                }
                                                                            }
                                                                        });
                                                            }
                                                        });


                                            }

                                            // userDisplayed.update("us_matchs_pending", contenuDisplayedUser.getUs_matchs_pending() +  userConnected.getId() + ";");
                                           // userDisplayed.update("us_matchs_request_to", contenuDisplayedUser.getUs_matchs_request_to().replace(userConnected.getId()+ ";", "") )
                                            // Création d'un objet pour envoyer sur la Database

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
    }


    // ************************************   INITIALISATION DES COMPOSANTS ET LE CLICK POUR MODIFICATION *****************************************************


    private void InitComponents(View v) {
        InitLlPresentation(v);
        InitivPhoto(v);
        InitLlHobbies(v);
        InitLlPersonality(v);
        InitLlSports(v);
        InitLlEthnicGroup(v);
        InitLlEyeColor(v);
        InitLlHairColor(v);
        InitLlHairLength(v);
        InitLlMaritalStatus(v);
        InitLlSexualOrientation(v);
        InitLlSmoker(v);
        InitLlShape(v);
        //TODO HasKids and Gender

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
        llPresentation.setClickable(false);
    }

    private void InitivPhoto(View v) {
        btnAddPhoto = v.findViewById(R.id.btn_add_photo);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               imageType = "imgAvatar";

               PopupMenu popMenu = new PopupMenu(getContext(), view);
               MenuInflater menuInflater = popMenu.getMenuInflater();

               // call Inflater Menu
               menuInflater.inflate(R.menu.menu_add_avatar,popMenu.getMenu());

               popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       int id = item.getItemId();

                       // En fonction du résultat, lancement de l'action appropriée

                       if (id == R.id.takeCameraPicture) {
                           getCameraPhotoNew();
                       } else if (id == R.id.takePicture) {
                           getImageLibrary();
                       }
                       return false;

                   }
               });
               // Show Popup menu
               popMenu.show();

           }
       });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadPhoto();
        }

        if (requestCode == REQUEST_IMAGE_CAMERA_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivProfilAvatarShape.setImageBitmap(bitmap);
            Log.d(TAG, "REQUEST_IMAGE_CAMERA_CAPTURE >> ");
            imageCameraUri = data.getData(); // Bitmap  data.getExtras().get("Data");
            ivProfilAvatarShape.setImageURI(imageCameraUri);
            //imageCameraUri = data.getData();
            Log.d(TAG, "imageCameraUri >> " + imageCameraUri);
            uploadCameraPhotoNew();
        }
    }

    private void uploadPhoto() {

        // currentUser, récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = db.collection(KEY_FS_COLLECTION).document(currentUser.getUid());
        Log.d(TAG, " UploadPhoto  ");
        final ProgressDialog prDial = new ProgressDialog(getContext());

        Log.d(TAG, " ProgressDialog  ");
        prDial.setTitle("Uploading Image...");
        prDial.show();

        // final String randomKey = UUID.randomUUID().toString();
        // Create the reference to "images/mountain.jpg
        Log.d(TAG, "RandomKey: " + randomKey);

        StorageReference riversRef = storageReference.child(currentUser.getUid() + "/" + randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        prDial.dismiss();
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(@NonNull Uri uri) {
                                imageUri = uri;
                                Log.d(TAG, "#####################################+" + uri);
                                uploadProfilFireBase(imageUri.toString());
                            }
                        });
                        ivProfilAvatarShape.setImageURI(imageUri);
                        Log.d(TAG, "upload: SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        prDial.dismiss();
                        Log.d(TAG, "upload: FAILED");
                    }
                })
                .addOnProgressListener(new com.google.firebase.storage.OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        prDial.setMessage("Percentage:" + (int) progressPercent + "%");
                    }
                });
    }


    private void uploadCameraPhotoNew() {

        final ProgressDialog prDial = new ProgressDialog(getContext());
        Log.d(TAG, "***** uploadCameraPhoto ***** ");
        prDial.setTitle("Uploading Image...");
        prDial.show();
        // Create a storage reference from our app
        StorageReference storageRef = storageReference.getStorage().getReference();
        // Create a reference to file
        // StorageReference mountainsRef = storageRef.child("toto.jpg");
        //Create a reference to "images/toto.jpg"
        // currentUser, récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = db.collection(KEY_FS_COLLECTION).document(currentUser.getUid());

        StorageReference mountainsImagesRef = storageRef.child(currentUser.getUid() + "/" + fileName);

        Log.d(TAG, "uploadCameraPhotoNew:GD " + mountainsImagesRef.getDownloadUrl());
        Log.d(TAG, "imageCameraUri: " + imageCameraUri);

        // while the file names are the same, the reference poinr to different ilfes
        //   mountainRef.getName().equals(mountainImagesRef.getName()); // true
        // mountainRef.getPath().equals(mountainImagesRef.getPath()); // false

        Toast.makeText(getContext(), "uploadCameraPhoto", Toast.LENGTH_SHORT).show();
        ivProfilAvatarShape.setDrawingCacheEnabled(true);
        ivProfilAvatarShape.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) ivProfilAvatarShape.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Handle Unsucessful uploads", Toast.LENGTH_SHORT).show();
                prDial.dismiss();
            }
        })

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "TaskSnapshot Successful", Toast.LENGTH_SHORT).show();
                        prDial.dismiss();

                        StorageReference riversRef = storageReference.child(currentUser.getUid() +"/" + fileName);

                        String getUid = FirebaseAuth.getInstance().getUid();

                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(@NonNull Uri uri) {
                                Log.d(TAG, "##########riversRef###################+" + uri);
                                imageUri = uri;
                                uploadProfilFireBase(imageUri.toString());
                            }
                        });
                    }
                })

                .addOnProgressListener(new com.google.firebase.storage.OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        prDial.setMessage("Percentage:" + (int) progressPercent + "%");

                    }
                });
    }

    //TODO
    /** FONCTION CAMERA, REDONDANCE AVEC CREATION PROFIL, A CENTRALISER  **/
    public void getCameraPhotoNew() {
        Log.d(TAG, "GET PHOTO STEP");

        // Request for camera runtime permission

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_IMAGE_CAMERA_CAPTURE);
        } else {
            Log.d(TAG, "getPhoto: ");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA_CAPTURE);
        }
    }
    public void getImageLibrary(){
        Log.d(TAG, "***** SelectPicture *******");

        final Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //  Bundle camerabundle = new Bundle();
        cameraIntent.setType("image/*"); // image/jpg

       /* cameraIntent.putExtra("crop", true);
        cameraIntent.putExtra("scale", true);
        // Output image dim
        cameraIntent.putExtra("outputX", 256);
        cameraIntent.putExtra("outputY", 256);
*/
        // Ratio
        cameraIntent.putExtra("aspectX", 1);
        cameraIntent.putExtra("aspectY", 1);
        cameraIntent.putExtra("return-data", true);
        cameraIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void uploadProfilFireBase(String fileUri) {

//        avatar = fileUri;
//        Log.d(TAG, "++++++++++++++ uploadProfilFireBase: " + avatar);
//
        // currentUser, récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = db.collection(KEY_FS_COLLECTION).document(currentUser.getUid());

        userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String label = null;
                if (imageType  == "imgAvatar"){
                    label = "us_avatar";
                }else{
                    label = "us_photos";
                }

                if (documentSnapshot.exists()) {
                    // Envoi de l'objet sur la Database
                    userDisplayed.update(label ,  fileUri)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Photo de profil modifiée", Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "Document Exist PHOTO profil crée");
                                    //  startActivity(new Intent(CreationProfilActivity.this, MainActivity.class));
                                    //     System.out.println("gs://tiers2confiance-21525.appspot.com/camera/"+fileUri);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Document Exist : Erreur dans la creation photo profil", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onFailure: ", e);
                                }
                            });
                } else {
                    // Envoi de l'objet sur la Database
                    userDisplayed.update(label ,  fileUri)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Photo de profil  créée", Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "Profil crée");
                                    StorageReference riversRef = storageReference.child("images/" + randomKey);
                                    //  startActivity(new Intent(CreationProfilActivity.this, MainActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Erreur dans la creation photo profil", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onFailure: ", e);
                                }
                            });
                }


            }
        });

    }


    private void InitLlHobbies(View v) {
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
        llHobbies.setClickable(false);
    }

    private void InitLlPersonality(View v) {
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
        llPersonality.setClickable(false);
    }

    private void InitLlSports(View v) {
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
        llSports.setClickable(false);
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
        llEthnicGroup.setClickable(false);
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
        llEyeColor.setClickable(false);
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
        llHairColor.setClickable(false);
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
        llHairLength.setClickable(false);
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
        llMaritalStatus.setClickable(false);
    }

    private void InitLlSexualOrientation(View v) {
        tvSexualOrient = v.findViewById(R.id.tv_sexual_orientation);
        llSexualOrientation   = v.findViewById(R.id.ll_sexual_orientation);

        llSexualOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id   =  String.valueOf(sexualOrientationId);
                upadtedField=   KEY_SEXUAL_ORIENTATION;

                Bundle data = new Bundle();
                data.putString("userattributesstring", Id);
                data.putString("userid", userId);
                data.putString("upadtedfield", upadtedField);


                Fragment myFragment = new UpdateSingleChoiceFragment();

                OpenFragment(v, data, myFragment);
            }
        });
        llSexualOrientation.setClickable(false);
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
        llSmoker.setClickable(false);
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
        llShape.setClickable(false);
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

                            String ethnie_val="--";
                            String colorEye_val="--";
                            String colorHair_val="--";
                            String lenghHair_val="--";
                            String sexualOrient_val="--";
                            String maritalStatus_val="--";
                            String smoker_val="--";
                            String shape_val="--";
                            balance = 0L;

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
                                Log.e(TAG, "Error on getting documentSnapshotDisplayed data (Il manque peut être un champ dasn le document USER) : ", e);
                            }


                            try {
                                /** ON AFFICHE LES INFORMATION COMMUNES **/
                                tvProfilCity.setText(city);
                                tvProfilName.setText(nickname);
                                // Le genre
                                if (genderUser.equals("1")) {
                                    ivGender.setImageResource(R.drawable.ic_male);
                                } else if (genderUser.equals("2")) {
                                    ivGender.setImageResource(R.drawable.ic_female);
                                } else {
                                    ivGender.setImageResource(R.drawable.ic_transgenre);
                                }
                                tvPresentation.setText(description);
                                /** L'avatar : Glide - Add Picture **/
                                Context context = getContext();
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.mipmap.ic_launcher)
                                        .placeholder(R.mipmap.ic_launcher);


                                //StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tiers2confiance-21525.appspot.com/41niZRNxf3S2OJI4YuJ338rTBFt2/7113af67-f70d-4d0f-83f8-9530af0219bb.jpg");

                               // Log.e(TAG, "onSuccess: " + gsReference);
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
                                if (role.equals(2L)) {
                                    // Si l'utilisateur connecté est un celib et donc...
                                    // si l'utilisateur à afficher est Tiers de confiance (parrain)...
                                } else {

                                    tvBalance.setText(balance.toString());
                                    ArrayList<String> imgPhotosList = new ArrayList<>();
                                    if (imgPhotos != "" & imgPhotos != ";") {
                                        imgPhotosList.addAll(Arrays.asList(imgPhotos.split(";")));
                                    }

                                    if (imgPhotosList.size() == 0) {
                                        llPhoto.setVisibility(View.GONE);
                                    } else {
                                        llPhoto.setVisibility(View.VISIBLE);
                                        adapterPhotos = new ViewPhotosAdapter(getContext(), imgPhotosList);
                                        rvListPhotos.setAdapter(adapterPhotos);
                                    }
                                    makeVisible();

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
                                    for (int i = 0; i < hobbiesListUser.length; i++) {
                                        for (int j = 0; j < ListHobbiesComplete.size(); j++) {
                                            String key = String.valueOf(ListHobbiesComplete.get(j).getHo_id());
                                            String hobbieLabel = ListHobbiesComplete.get(j).getHo_label();
                                            if (key.equals(hobbiesListUser[i])) {
                                                TextView tvAttribute = new TextView(getActivity().getApplicationContext());
                                                tvAttribute.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                tvAttribute.setText(hobbieLabel);
                                                llHobbies.addView(tvAttribute);
                                            }
                                        }
                                    }

                                    // PERSONALITY
                                    String[] personalityListUser = listPersonality.split(split_key);

                                    for (int i = 0; i < personalityListUser.length; i++) {
                                        for (int j = 0; j < ListPersonalityComplete.size(); j++) {
                                            String key = String.valueOf(ListPersonalityComplete.get(j).getPe_id());
                                            String personalityLabel = ListPersonalityComplete.get(j).getPe_label();
                                            if (key.equals(personalityListUser[i])) {
                                                TextView tvAttribute = new TextView(getActivity().getApplicationContext());
                                                tvAttribute.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                tvAttribute.setText(personalityLabel);
                                                llPersonality.addView(tvAttribute);
                                            }
                                        }
                                    }

                                    // SPORTS
                                    String[] sportsListUser = listSports.split(split_key);

                                    String sportsToDisplay = "-- ";
                                    for (int i = 0; i < sportsListUser.length; i++) {
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
                            }catch(Exception e){
                                Log.e(TAGAPP, "Problem when displaying data in profile : "+ currentUser.getUid(), e);
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
                                    // Si le user connecté est le même que le user à afficher (VOIR MON PROFIL) , on affiche rend clickable et on affiche les crayons
                                    if (documentSnapshotDisplayed.getId().equals(documentSnapshotConnected.getId()) ){
                                        makeUpdate();
                                        if (usRole.equals(2L)) {btnAddPhoto.setVisibility(View.GONE);}
                                        // Sinon on affiche d'autres boutons
                                    }else{
                                        // Si le user connecté est un Parrain, on affiche les boutons qui vont bien
                                        if (usRole.equals(2L)) {
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


    public void makeInvisible() {

        // Les boutons n'existent pas dans le Layout à l'initialisation, on les affiche seulement si necessaire
        btnPflCrediter.setVisibility(View.GONE);
        btnPflEnvoyer.setVisibility(View.GONE);
        btnAcceptMatch.setVisibility(View.GONE);
        btnLinkSupp.setVisibility(View.GONE);
        btnLinkRequest.setVisibility(View.GONE);
        btnLinkSuppTiers.setVisibility(View.GONE);
        btnLinkRequestTiers.setVisibility(View.GONE);
        btnAcceptNephew.setVisibility(View.GONE);
        btnAcceptGodfather.setVisibility(View.GONE);

        llPhoto.setVisibility(View.GONE);
        llBalance.setVisibility(View.GONE);
        llHobbies.setVisibility(View.GONE);
        llPersonality.setVisibility(View.GONE);
        llSports.setVisibility(View.GONE);
        llEthnicGroup.setVisibility(View.GONE);
        llShape.setVisibility(View.GONE);
        llEyeColor.setVisibility(View.GONE);
        llHairColor.setVisibility(View.GONE);
        llHairLength.setVisibility(View.GONE);
        llMaritalStatus.setVisibility(View.GONE);
        llSexualOrientation.setVisibility(View.GONE);
        llSmoker.setVisibility(View.GONE);

    }

    public void makeVisible(){
        llPhoto.setVisibility(View.VISIBLE);
        llBalance.setVisibility(View.VISIBLE);
        llHobbies.setVisibility(View.VISIBLE);
        llPersonality.setVisibility(View.VISIBLE);
        llSports.setVisibility(View.VISIBLE);
        llEthnicGroup.setVisibility(View.VISIBLE);
        llShape.setVisibility(View.VISIBLE);
        llEyeColor.setVisibility(View.VISIBLE);
        llHairColor.setVisibility(View.VISIBLE);
        llHairLength.setVisibility(View.VISIBLE);
        llMaritalStatus.setVisibility(View.VISIBLE);
        llSexualOrientation.setVisibility(View.VISIBLE);
        llSmoker.setVisibility(View.VISIBLE);
    }

    public void makeUpdate(){

        llPresentation.setClickable(true);
        llBalance.setClickable(true);
        llHobbies.setClickable(true);
        llPersonality.setClickable(true);
        llSports.setClickable(true);
        llEthnicGroup.setClickable(true);
        llShape.setClickable(true);
        llEyeColor.setClickable(true);
        llHairColor.setClickable(true);
        llHairLength.setClickable(true);
        llMaritalStatus.setClickable(true);
        llSexualOrientation.setClickable(true);
        llSmoker.setClickable(true);

        btnAddPhoto.setVisibility(View.VISIBLE);
        tvPersonalityEdit.setVisibility(View.VISIBLE);
        tvSportsEdit.setVisibility(View.VISIBLE);
        tvHobbiesEdit.setVisibility(View.VISIBLE);
        tvPresentationEdit.setVisibility(View.VISIBLE);
        tvEthnicGroupEdit.setVisibility(View.VISIBLE);
        tvEyeColorEdit.setVisibility(View.VISIBLE);
        tvHairColorEdit.setVisibility(View.VISIBLE);
        tvHairLengthEdit.setVisibility(View.VISIBLE);
        tvSmokerEdit.setVisibility(View.VISIBLE);
        tvSexualOrientEdit.setVisibility(View.VISIBLE);
        tvMaritalStatusEdit.setVisibility(View.VISIBLE);
        tvShapeEdit.setVisibility(View.VISIBLE);
    }

    public void makeNotUpdate(){

        llPresentation.setClickable(false);
        llHobbies.setClickable(false);
        llPersonality.setClickable(false);
        llSports.setClickable(false);
        llEthnicGroup.setClickable(false);
        llShape.setClickable(false);
        llEyeColor.setClickable(false);
        llHairColor.setClickable(false);
        llHairLength.setClickable(false);
        llMaritalStatus.setClickable(false);
        llSexualOrientation.setClickable(false);
        llSmoker.setClickable(false);

        btnAddPhoto.setVisibility(View.GONE);
        tvPersonalityEdit.setVisibility(View.GONE);
        tvSportsEdit.setVisibility(View.GONE);
        tvHobbiesEdit.setVisibility(View.GONE);
        tvPresentationEdit.setVisibility(View.GONE);
        tvEthnicGroupEdit.setVisibility(View.GONE);
        tvEyeColorEdit.setVisibility(View.GONE);
        tvHairColorEdit.setVisibility(View.GONE);
        tvHairLengthEdit.setVisibility(View.GONE);
        tvSmokerEdit.setVisibility(View.GONE);
        tvSexualOrientEdit.setVisibility(View.GONE);
        tvMaritalStatusEdit.setVisibility(View.GONE);
        tvShapeEdit.setVisibility(View.GONE);
    }
}
