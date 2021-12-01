package com.tiesr2confiance.tiers2confiance;

import static com.tiesr2confiance.tiers2confiance.Common.ListsAttributs.getContext;

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

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.Login.LoginActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Main Activity : ";
    /**
     * Variables globales
     **/

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
            case R.id.nav_deconnexion:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }


}