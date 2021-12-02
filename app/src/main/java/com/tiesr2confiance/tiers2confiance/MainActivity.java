package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.PGO.UserActivity;
import com.tiesr2confiance.tiers2confiance.Common.PGO.UserFragment;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.Login.LoginActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Main Activity : ";
    private static final String TAGAPP = "LOGAPP";
    /**
     * Variables globales
     **/
    /** Variable Firebase Auth **/
    private FirebaseUser user;
    private String userId, userAuthUID;
    private String collection;
    private String userNickName;
    private String userCountryLanguage;
    private String userEmail;




    /** Variables Firestore **/
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private DocumentReference docRefUserConnected;
    public static ArrayList<ModelGenders> myArrayListGenders = new ArrayList<>();
    public static ArrayList<ModelHobbies> myArrayListHobbies = new ArrayList<>();

    ConstraintLayout userActivityConstraintLayout;
    LinearLayout gendersLinearLayout;
    LinearLayout     hobbiesLinearLayout;
    LinearLayout     hobbiesLinearlayoutChkbox;

    RecyclerView rvHobbies;

    private RadioGroup radioGroupGenders;
    private RadioGroup  radioGroupHobbies;

    private TextView    tvUserNickName;
    private TextView    tvUserId;
    private TextView    tvCountryLanguage;
    private TextView    tvEmail;



    //********************************* FIREBASE AUTH
    private FirebaseAuth firebaseAuth;

    // ******************************** MENU
    Toolbar toolbar;
    DrawerLayout drawer_layout;

    // La gestion des fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // Gestion de la NavigationView
    private NavigationView navigationView;

    // Variable emplacement
    private static final String emplacement
            = MainActivity.class.getSimpleName();

    // ******************************** NAVIGATION
    private AppBarConfiguration appBarConfiguration;
    private ProfilFragment binding;

    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");

    @Override
    protected void onStart() {
        super.onStart();

        //     GlobalClass globalVariables = new GlobalClass();
        //     globalVariables.LoadUserDataFromFirestore();

        //        Log.i(TAG, globalVariables.getUser());
//        Log.i(TAG, globalVariables.getUserId());
//        Log.i(TAG, globalVariables.getUserEmail());
//        Log.i(TAG, globalVariables.getUserNickName());
//        Log.i(TAG, globalVariables.getUserCountryLanguage());
    }

    /**
     * Faire le lien entre les widgets et le design
     **/
    public void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_navigationView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Appel de la méthode d'initialisation de l'UI
        initUI();
        // Ajout du support pour la gestio nde la Toolbar
        setSupportActionBar(toolbar);
        // Ajout de la gestion des options d'accessibilité
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, // Le context de l'activité
                drawer_layout, // Le layout du MainActivity
                toolbar, // La toolbar
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        // Ajout d'un listener sur le bouton hamburger
        drawer_layout.addDrawerListener(toggle);
        // Synchro le bouton hamburger et le menu
        toggle.syncState();
        //
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            addFragment();
            // Force l'affichage du 1er fragment au démarrage
            navigationView.setCheckedItem(R.id.nav_fragment_1);
        }

        /*************************************************************/
        /*************************************************************/
        /**************************** PGO ****************************/
        /*************************************************************/
        /*************************************************************/

        Button btnInitVar1;
        btnInitVar1 = findViewById(R.id.btn_init_var1);
        btnInitVar1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   InitVariables();
                   InitComponents();
               }
           });

        Button btnInitVar2;
        btnInitVar2 = findViewById(R.id.btn_init_var2);
        btnInitVar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadGenders();
                LoadHobbies();
            }
        });


        Button btndisplaygenders;
        btndisplaygenders = findViewById(R.id.btn_display_genders);
        btndisplaygenders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayGenders();
            }
        });

        Button btndisplayhobbies;
        btndisplayhobbies = findViewById(R.id.btn_display_hobbies);
        btndisplayhobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayHobbies();
            }
        });



        GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        globalVariables.LoadUserDataFromFirestore();
        globalVariables.LoadGendersDataFromFirestore();
        globalVariables.LoadHobbiesDataFromFirestore();
//        LoadUserData();
//        LoadGenders();
//        LoadHobbies();

        InitVariables();
        InitComponents();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        globalVariables.LoadUserDataFromFirestore();
        globalVariables.LoadGendersDataFromFirestore();
        globalVariables.LoadHobbiesDataFromFirestore();
    }

    private void LoadUserData() {
        GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.i(TAGAPP, "******** CreationProfilActivity LoadUserDataFromFirestore START *************");
        globalVariables.LoadUserDataFromFirestore();
        Log.i(TAGAPP, "******** CreationProfilActivity LoadUserDataFromFirestore FINISH *************");

    }

    public void InitVariables() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.d(TAGAPP, "InitGlobalVariablesStep1");

//        globalVariables.LoadUserDataFromFirestore();


        userId              =   globalVariables.getUserId();
        userNickName        =   globalVariables.getUserNickName();
        userCountryLanguage =   globalVariables.getUserCountryLanguage();
        userEmail           =   globalVariables.getUserEmail();

        Log.i(TAGAPP, "userId : " + userId);
        Log.i(TAGAPP, "userNickName : " + userNickName);
        Log.i(TAGAPP, "userCountryLanguage : " + userCountryLanguage);
        Log.i(TAGAPP, "userEmail : " + userEmail);
        Log.i(TAGAPP, "------- END OF initGlobalVariables ---------");
//        Toast.makeText(MainActivity.this,"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();

    }

    private void InitComponents() {
        tvUserId        = findViewById(R.id.tv_user_id);
        tvUserNickName  = findViewById(R.id.tv_nick_name);
        tvCountryLanguage= findViewById(R.id.tv_country_language);
        tvEmail        =   findViewById(R.id.tv_email);

        /************** init des TextViews ***-******************/
        tvUserId.setText(userId);
        tvUserNickName.setText(userNickName);
        tvCountryLanguage.setText(userCountryLanguage);
        tvEmail.setText(userEmail);

    }








    private void LoadGenders() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.d(TAGAPP, "LoadGenders()");

        globalVariables.LoadGendersDataFromFirestore();
    }


    private void LoadHobbies() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.d(TAGAPP, "LoadHobbies()");

        globalVariables.LoadHobbiesDataFromFirestore();
    }

    private void DisplayGenders() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.d(TAGAPP, "DisplayGenders()");

        myArrayListGenders  =   globalVariables.getArrayListGenders();

        Log.i(TAGAPP, "------- myArrayListGenders ---------" + myArrayListGenders.size());
        Log.i(TAGAPP, "------- myArrayListGenders ---------" + myArrayListGenders);
        Log.i(TAGAPP, "----------------");
        for (int i = 0; i < myArrayListGenders.size(); i++) {
            Log.i(TAGAPP, ">>"
                    + myArrayListGenders.get(i).getGe_id() + " "
                    + myArrayListGenders.get(i).getGe_country() + " "
                    + myArrayListGenders.get(i).getGe_label());
        }
        Log.i(TAGAPP, "----------------");
        Log.i(TAGAPP, "------- myArrayListGenders ---------");
        Toast.makeText(MainActivity.this,"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();

    }

    private void DisplayHobbies() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.d(TAGAPP, "DisplayHobbies() ");

        myArrayListHobbies  =   globalVariables.getArrayListHobbies();

        Log.i(TAGAPP, "------- myArrayListHobbies ---------" + myArrayListHobbies.size());
        Log.i(TAGAPP, "------- myArrayListHobbies ---------" + myArrayListHobbies);
        Log.i(TAGAPP, "----------------");


        Collections.sort(myArrayListHobbies, new Comparator<ModelHobbies>() {
            @Override
            public int compare(ModelHobbies u1, ModelHobbies u2) {
                return u2.getHo_label().compareTo(u1.getHo_label());
            }
        });


        for (int i = 0; i < myArrayListHobbies.size(); i++) {
            Log.i(TAGAPP, ">>"
                    + myArrayListHobbies.get(i).getHo_id() + " "
                    + myArrayListHobbies.get(i).getHo_country() + " "
                    + myArrayListHobbies.get(i).getHo_label());
        }
        Log.i(TAGAPP, "------- myArrayListHobbies ---------");
        Toast.makeText(MainActivity.this,"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();

    }















    public void InitComponents_OLD() {

        //userId = user.getUid();

        Log.i(TAG, "initComponents : BEGIN");

        //userActivityLayout =  findViewById(R.id.user_Activity_Layout);
        userActivityConstraintLayout =  findViewById(R.id.drawer_layout);

        /************** init des TextViews ***-******************/

        tvUserId                    = findViewById(R.id.tv_user_id);
//        tvUserNickName              = findViewById(R.id.tv_user_nick_name);
//        tvUserAuthUID               = findViewById(R.id.tv_user_AuthUID);

        tvUserId.setText(userId);
        tvUserNickName.setText(userNickName);
//        tvUserAuthUID.setText(userId);

        //        /************** init des Views/Layouts CheckBox et Radiobuttons ***-******************/
        //
        //        gendersLinearLayout         = findViewById(R.id.linear_layout_genders);
        //        hobbiesLinearLayout         = findViewById(R.id.linear_layout_hobbies);
        //        hobbiesLinearlayoutChkbox   = findViewById(R.id.linear_layout_chk_hobbies);
        //
        //
        ////        rvHobbies           = findViewById(R.id.rv_hobbies);
        //
        //        LinearLayout.LayoutParams params =
        //                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
        //                        , ViewGroup.LayoutParams.WRAP_CONTENT
        //                        );
        //        params.setMargins(80, 0, 0, 40);
        //
        //
        //        radioGroupGenders = new RadioGroup(this);
        //        radioGroupGenders.setLayoutParams(params);
        //
        ////        params.setMargins(30, 0, 0, 0);
        //        radioGroupHobbies = new RadioGroup(this);
        //        radioGroupHobbies.setLayoutParams(params);
        //
        //
        //
        //
        ////        rgGenders.addView(gendersLinearLayout);

        Log.i(TAG, "initComponents : END");

    }



    public void InitVariablesStep1(){
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.d(TAG,"InitGlobalVariablesStep1");

//        globalVariables.LoadUserDataFromFirestore();


        userId              =   globalVariables.getUserId();
        userNickName        =   globalVariables.getUserNickName();
        userCountryLanguage =   globalVariables.getUserCountryLanguage();
        String userEmail    =   globalVariables.getUserEmail();


        Log.i(TAG, "db : " + db);
        Log.i(TAG, "user : " + user);
        Log.i(TAG, "userId : " + userId);
        Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);
        Log.i(TAG, "------- END OF InitGlobalVariables ---------");
        Toast.makeText(MainActivity.this,"CONNECTED USER : " + userId , Toast.LENGTH_SHORT).show();
    }

    public void InitGlobalVariablesStep2(){
//        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
//
//        globalVariables.LoadUserDataFromFirestore(); // userNickName, userCountryLanguage
//
//        globalVariables.setUserNickName(userNickName);
//        globalVariables.setUserCountryLanguage(userCountryLanguage);
//        globalVariables.setUserEmail(userEmail);
//
//
//        Log.i(TAG, "userId : " + userId);
//        Log.i(TAG, "userNickName : " + userNickName);
//        Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);
//        Log.i(TAG, "userEmail : " + userEmail);
//        Log.i(TAG, "------- END OF InitGlobalVariables ---------");
//        Toast.makeText(MainActivity.this,"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();
    }



    private void addFragment() {
        fragmentManager = getSupportFragmentManager();
        // Commencer la discussion
        fragmentTransaction = fragmentManager.beginTransaction();
        // Appel du nouveau fragment
        CameraFragment cameraFragment = new CameraFragment();
        // Ajouter au container de fragment
        fragmentTransaction.add(R.id.fragment_container, cameraFragment);
        // Finalisation de la création du fragment
        fragmentTransaction.commit();



//        getSupportFragmentManager().
//                beginTransaction().
//                add(R.id.fragment_container, new Fragment_01()).
//                commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            // MOI (Pseudo)
            case R.id.nav_profil:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new ProfilFragment()).
                        commit();
                break;
            // Mon Parrain
            case R.id.nav_view_profil:

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                assert currentUser != null;
                DocumentReference userConnected = usersCollectionRef.document(currentUser.getUid());

                userConnected.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        ModelUsers contenuUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                        assert contenuUser != null;

                        Long usRole = contenuUser.getUs_role();
                        if (usRole.equals(2)){
                            if (TextUtils.isEmpty(contenuUser.getUs_nephews())){
                                getSupportFragmentManager().
                                        beginTransaction().
                                        replace(R.id.fragment_container, new LierParrainFilleulFragment()).
                                        commit();
                            }else{
                                Bundle b = new Bundle();
                                b.putString("idUser", contenuUser.getUs_nephews());
                                Fragment fragment = new ViewProfilFragment();
                                fragment.setArguments(b);
                                getSupportFragmentManager().
                                        beginTransaction().
                                        replace(R.id.fragment_container, fragment).
                                        commit();
                            }
                        }else{
                            if (TextUtils.isEmpty(contenuUser.getUs_godfather())){
                                getSupportFragmentManager().
                                        beginTransaction().
                                        replace(R.id.fragment_container, new LierParrainFilleulFragment()).
                                        commit();
                            }else{
                                Bundle b = new Bundle();
                                b.putString("idUser", contenuUser.getUs_godfather());
                                Fragment fragment = new ViewProfilFragment();
                                fragment.setArguments(b);
                                getSupportFragmentManager().
                                        beginTransaction().
                                        replace(R.id.fragment_container, fragment).
                                        commit();
                            }
                        }
                    }
                });

                break;
            case R.id.nav_search_profil_PF:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new LierParrainFilleulFragment()).
                        commit();
                break;

            case R.id.nav_crediter:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new CreditFragment()).
                        commit();
                break;

            case R.id.nav_pending_request:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new PendingRequestsFragment()).
                        commit();
                break;
            case R.id.nav_PGO:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new UserFragment()).
                        commit();
                break;
            case R.id.nav_deconnexion:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }


}