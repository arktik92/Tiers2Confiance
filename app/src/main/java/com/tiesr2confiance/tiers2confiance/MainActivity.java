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

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;

import java.util.HashMap;

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


    // Les listes de valeur des listes déroulantes
    public HashMap<Long, String> HashmapHobbie = new HashMap<Long, String>();

    /**
     * Faire le lien entre les widgets et le design
     **/
    public void initUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_navigationView);
    }


    /**
     * Créer les listes de valeurs
     **/
    public void ListsCreate() {

        HashmapHobbie.put((long)1, getString(R.string.ho_artisanat_text));
        HashmapHobbie.put((long)2, getString(R.string.ho_balades_text));
        HashmapHobbie.put((long)3, getString(R.string.ho_boites_text));
        HashmapHobbie.put((long)4, getString(R.string.ho_cafe_text));
        HashmapHobbie.put((long)5, getString(R.string.ho_charites_text));
        HashmapHobbie.put((long)6, getString(R.string.ho_clubs_text));
        HashmapHobbie.put((long)7, getString(R.string.ho_cuisiner_text));
        HashmapHobbie.put((long)8, getString(R.string.ho_déguster_text));
        HashmapHobbie.put((long)9, getString(R.string.ho_fairerencontres_text));
        HashmapHobbie.put((long)10, getString(R.string.ho_films_text));
        HashmapHobbie.put((long)11, getString(R.string.ho_jardiner_text));
        HashmapHobbie.put((long)12, getString(R.string.ho_jeuxcartes_text));
        HashmapHobbie.put((long)13, getString(R.string.ho_jeuxvideos_text));

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