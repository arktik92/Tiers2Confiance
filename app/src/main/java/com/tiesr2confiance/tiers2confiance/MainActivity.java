package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.Login.LoginActivity;

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
        FirstFragment fragment_01 = new FirstFragment();
        // Ajouter au container de fragment
        fragmentTransaction.add(R.id.fragment_container, fragment_01);
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
            case R.id.nav_profil_filleul:

                // envoyer les variable sur un autre fragment.

//                String test = "toto";
//                Bundle b = new Bundle();
//                b.putString("message", test);
//                Fragment fragment = new Fragment();
//                fragment.setArguments(b);
//                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.replace(R.id.the_fragg,dv);
//                ft.commit();

                // recuperer sur un autre fragment
//                public class Frag2 extends Fragment {
//                    public View onCreateView(LayoutInflater inflater,
//                                             ViewGroup containerObject,
//                                             Bundle savedInstanceState){
//                        //here is your arguments
//                        Bundle bundle=getArguments();
//
//                        //here is your list array
//                        String[] myStrings=bundle.getStringArray("elist");
//                    }
//                }

                 getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new ViewProfilFragment()).
                        commit();
                break;
            case R.id.nav_search_profil_PF:
            case R.id.nav_search_profil_FP:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new LierParrainFilleulFragment()).
                        commit();
                break;

            case R.id.nav_crediter:
            case R.id.nav_crediter_from_celib:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new CreditFragment()).
                        commit();
                break;

            case R.id.nav_pending_request:
            case R.id.nav_pending_request_godfather:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new PendingRequestsFragment()).
                        commit();
                break;
            case R.id.nav_deconnexion_celib:
            case R.id.nav_deconnexion_parrain:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }


}