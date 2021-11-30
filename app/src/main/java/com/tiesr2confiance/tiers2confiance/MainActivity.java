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
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.ProfilFragment;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Variables globales
     **/

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


    public void launchLierParrainFilleulActivity(View v){
    //    Intent intent = new Intent(MainActivity.this, LierParrainFilleulFragment.class);
//        intent.putExtra("IdUser", snapshot.getId());
    //    startActivity(intent);


    }

    public void launchPendingRequestsActivity(View v){
      //  Intent intent = new Intent(MainActivity.this, PendingRequestsFragment.class);
//        intent.putExtra("IdUser", snapshot.getId());
     //   startActivity(intent);


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
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new ViewProfilFragment()).
                        commit();
                break;
            case R.id.nav_search_profil_PF:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new LierParrainFilleulFragment()).
                        commit();
                break;
            case R.id.nav_pending_request:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new PendingRequestsFragment()).
                        commit();
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }



}