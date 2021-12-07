package com.tiesr2confiance.tiers2confiance.Profil;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_BALANCE;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_CITY;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_DESCRIPTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_FS_COLLECTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_GENDER;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HOBBIES;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_AVATAR;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_NICKNAME;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_PHOTOS;
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
import android.widget.EditText;
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
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentViewProfilBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class ViewProfilFragment extends Fragment {

    public static final String TAG = "View Profile";

    private TextView tvProfilName, tvDescription, tvProfilCity, tvHobbies, tvRole, tvBalance;
    private ImageView ivProfilAvatarShape, ivGender;
    private Button btnPflCrediter, btnPflEnvoyer, btnLinkSupp, btnLinkRequest, btnLinkSuppTiers, btnLinkRequestTiers, btnUpdateProfil, btnAcceptNephew, btnAcceptGodfather, btnAcceptMatch ;
    private LinearLayout llProfil;
    private LinearLayout llHobbies;

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
    String imgPhoto;
    String imgUrlAvatar;
    String description;
    String  listHobbies;
    String genderUser;
    Long role;
    Long balance;


    private Long usRole;
    private String usNephew;
    private String usGodfather;
    private String usNephewRequestFrom;
    private String usGodfatherRequestFrom;
    private String usMatchsRequestFrom;
    private String usMatchsRequestTo;



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

        tvProfilName = view.findViewById(R.id.tvProfilName);
        tvRole = view.findViewById(R.id.tvRole);
        tvProfilCity = view.findViewById(R.id.tvProfilCity);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvHobbies = view.findViewById(R.id.tvHobbies);
        tvBalance = view.findViewById(R.id.tvBalance);
        ivGender = view.findViewById(R.id.ivGender);
        llProfil = view.findViewById(R.id.ll_profil);
        llProfil.setVisibility(View.GONE);
        llHobbies =view.findViewById(R.id.ll_hobbies);

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

        /** Glide image **/
        ivProfilAvatarShape = view.findViewById(R.id.ivProfilAvatarShape);

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

    }

    private void InitLlHobbies(View v) {
        tvHobbies = v.findViewById(R.id.tvHobbies);
        llHobbies =v.findViewById(R.id.ll_hobbies);

        llHobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hobbies =  listHobbies;

                Bundle data = new Bundle();
                data.putString("hobbies", listHobbies);//
                data.putString("userid", userId);// tvDescription.getText().toString());

                Fragment updatePresentationFragment = new UpdateHobbiesFragment();
                updatePresentationFragment.setArguments(data);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, updatePresentationFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    private void InitLlPresentation(View v) {

        LinearLayout    llPresentation;
        llPresentation = v.findViewById(R.id.ll_presentation);

        llPresentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String presentation =  tvDescription.getText().toString();

                Bundle data = new Bundle();
                data.putString("presentation", presentation);//
                data.putString("userid", userId);// tvDescription.getText().toString());

                Fragment updatePresentationFragment = new UpdatePresentationFragment();
                updatePresentationFragment.setArguments(data);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, updatePresentationFragment)
                            .addToBackStack(null)
                            .commit();



            }
        });
    }

    public void showProfil() {

        userDisplayed.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshotDisplayed) {
                        if (documentSnapshotDisplayed.exists()) {
                            nickname = documentSnapshotDisplayed.getString(KEY_NICKNAME);
                            city = documentSnapshotDisplayed.getString(KEY_CITY);
                            imgPhoto = documentSnapshotDisplayed.getString(KEY_PHOTOS);
                            imgUrlAvatar = documentSnapshotDisplayed.getString(KEY_AVATAR);
                            description = documentSnapshotDisplayed.getString(KEY_DESCRIPTION);
                            listHobbies = documentSnapshotDisplayed.getString(KEY_HOBBIES);
                            genderUser = String.valueOf(documentSnapshotDisplayed.getLong(KEY_GENDER));
                            role = documentSnapshotDisplayed.getLong(KEY_ROLE);
                            balance = documentSnapshotDisplayed.getLong(KEY_BALANCE);

                            // Si l'utilisateur à afficher est un célibataire
                            if (role.equals(1L)){
                                tvRole.setText("Célib");
                                llProfil.setVisibility(View.VISIBLE);
                            }else{
                                // sinon, s'il est Tiers de confiance (parrain)
                                tvRole.setText("Tiers");
                            }
                            tvBalance.setText(String.valueOf(balance));

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
                                    .load(imgUrlAvatar)
                                    .apply(options)
                                    .fitCenter()
                                    .circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfilAvatarShape);

                            // Ici, on récupère tous les attributs.caractériques de l'utilisateur à afficher (on récupère les ID des valeurs, qu'on va comparer avec les listes complètes chargées par la Class Globale)
                            String split_key = ";";
                            String[] hobbiesListUser = listHobbies.split(split_key);

                            // Appel de la classe global pour charger les listes d'attributs
                            final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
                            ArrayList<ModelGenders> ListGendersComplete = globalVariables.getArrayListGenders();
                            ArrayList<ModelHobbies> ListHobbiesComplete = globalVariables.getArrayListHobbies();


                            // HOBBIES VALEURS : Affichage des hobbies, comparaison de la liste des hobbies de l'utilisateur avec la liste complète chargée
                            int i;
                            String hobbiesToDisplay="-- ";
                            for (i=0; i< hobbiesListUser.length;i++) {
                                for (int j = 0; j < ListHobbiesComplete.size(); j++) {
                                    String key = String.valueOf(ListHobbiesComplete.get(j).getHo_id());
                                    String value = ListHobbiesComplete.get(j).getHo_label();
                                    String hobbieLabel = ListHobbiesComplete.get(j).getHo_label();
                                    if (key.equals(hobbiesListUser[i])) {
                                        //hobbiesToDisplay += value + " -- ";
                                        TextView tvHobby = new TextView(getActivity().getApplicationContext());
                                        tvHobby.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tvHobby.setText(hobbieLabel);
                                        llHobbies.addView(tvHobby);
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
                                    ivGender.setImageResource(R.drawable.ic_male);
                                }else if (genderUser.equals("2")){
                                    ivGender.setImageResource(R.drawable.ic_female);
                                }else {
                                    ivGender.setImageResource(R.drawable.ic_transgenre);
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

                                    usRole = contenuUser.getUs_role();
                                    usNephew = contenuUser.getUs_nephews();
                                    usNephewRequestFrom = contenuUser.getUs_nephews_request_from();
                                    usGodfather = contenuUser.getUs_godfather();
                                    usMatchsRequestFrom = contenuUser.getUs_matchs_request_from();
                                    usMatchsRequestTo = contenuUser.getUs_matchs_request_to();
                                    usGodfatherRequestFrom = contenuUser.getUs_godfather_request_from();
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
