package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.PGO.UserFragment;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.Login.LoginActivity;
import com.tiesr2confiance.tiers2confiance.MatchCibles.MatchCiblesFragment;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.Profil.ProfilFragment;
import com.tiesr2confiance.tiers2confiance.Profil.ViewProfilFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Main Activity : ";
    private static final String TAGAPP = "LOGAPP";
    /**
     * Variables globales
     **/
    /** Variable Firebase Auth **/


    /** Variables Firestore **/

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
    DocumentReference userConnected;

    /********* Gestion du Role **********/
    long role;


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

        GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        String userId       = globalVariables.getUserId();
        String userEmail    = globalVariables.getUserEmail();
        try {
            globalVariables.LoadUserDataFromFirestore();
            globalVariables.LoadGendersDataFromFirestore();
            globalVariables.LoadHobbiesDataFromFirestore();
        }
        catch (Exception e) {
            Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----userEmail "  + userEmail);        };

        globalVariables.DisplayAttributes();


        role = globalVariables.getUserRole();
        if (role == 0) {
            GetRoleFromFilePrefs();
        }

        Log.d(TAG, "MainActivity onCreate: USERROLE" + globalVariables.getUserRole() + globalVariables.getUserEmail());

       // role = 1L;

        if( role == 1L) {
            setContentView(R.layout.activity_main_celibataire);
        } else {
            setContentView(R.layout.activity_main_parrain);
        }

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

//        if(savedInstanceState == null){
//            addFragment();
//            // Force l'affichage du 1er fragment au démarrage
//            navigationView.setCheckedItem(R.id.nav_fragment_1);
//        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userConnected = usersCollectionRef.document(currentUser.getUid());

    }


    private void LoadUserData() {
        GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.i(TAGAPP, "******** CreationProfilActivity LoadUserDataFromFirestore START *************");
        globalVariables.LoadUserDataFromFirestore();
        Log.i(TAGAPP, "******** CreationProfilActivity LoadUserDataFromFirestore FINISH *************");

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



    private void addFragment() {
        fragmentManager = getSupportFragmentManager();
        // Commencer la discussion
        fragmentTransaction = fragmentManager.beginTransaction();
        // Appel du nouveau fragment
        CameraFragment cameraFragment = new CameraFragment();
        // Ajouter au container de fragment
        fragmentTransaction.add(R.id.fragment_container, new Fragment());
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
            case R.id.nav_photos:
                Intent intent = new Intent(MainActivity.this, CameraFragment.class);
                startActivity(intent);

                break;
            // MOI (Pseudo)
            case R.id.nav_profil:
                Bundle b = new Bundle();
                b.putString("idUser", userConnected.getId());
                Fragment fragment = new ViewProfilFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, fragment).
                        commit();
                break;

            // Chercher des célibataires pour mon filleul, ou consulter les propositions de match
            // reçues de mon parrain , ou de parrains d'autres célibataires
            case R.id.nav_cibles:
                b = new Bundle();
                b.putString("idUser", userConnected.getId());
                fragment = new MatchCiblesFragment();
                fragment.setArguments(b);
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, fragment).
                        commit();
                break;

            // Mon Parrain ou Mon filleul
            case R.id.nav_view_profil:
                userConnected.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        ModelUsers contenuUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                        assert contenuUser != null;
                        Long usRole = contenuUser.getUs_role();
                        if (usRole.equals(2L)){
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




/*****************************************************************************************************************/

private void GetRoleFromFilePrefs() {
    // On récupère la role de l'utilisateur dans SharedPreferences
    GlobalClass globalVariables = (GlobalClass) getApplicationContext();
    //        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapp.prefs", Context.MODE_PRIVATE);
    SharedPreferences sharedPreferences = getSharedPreferences(R.class.getPackage().getName()
            + ".prefs", Context.MODE_PRIVATE);


    // La vérifcation du boolean
    if (!sharedPreferences.getBoolean("isusersingle", true)) {
        globalVariables.setUserRole(1L);

        role = globalVariables.getUserRole();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // On place le boolean  isusersingle

        editor.putBoolean("isusersingle", true); //
        editor.commit();
    } else {
        globalVariables.setUserRole(2L);

        role = globalVariables.getUserRole();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // On place le boolean  isusersingle
        editor.putBoolean("isusersingle", false); //
        editor.commit();
    }




}



}