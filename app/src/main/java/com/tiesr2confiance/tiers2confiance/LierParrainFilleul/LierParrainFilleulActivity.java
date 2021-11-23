package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;

public class LierParrainFilleulActivity extends AppCompatActivity {

    /** Variables globales **/

    private static final String TAG = "Lier Parrain Filleul :";

    SearchView svTextSearch;
    RecyclerView rvResultat;

    private RecyclerView recyclerView;
    private LierParrainFilleulAdapterItem adapterUser;
    private ArrayList<ModelUsers> usersArrayList;

    //ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lier_parrain_filleul);
        init();
    }


    /** Initialisation des composants et affichage de la liste d'utilisateurs avec la recherche associée **/
    public void init() {

        recyclerView = findViewById(R.id.rvResultat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersArrayList = new ArrayList<>();


        //TODO ici, il faudra determiné le rôle de l'utilisateur connecté
        // Si l'user connecté est un filleul
        setTitle(getString(R.string.Lier_pf_titre_filleul));

        // Si l'user connecté est un parrain
        setTitle(getString(R.string.Lier_pf_titre_parrain));


        // Création d'une fausse liste pour l'exemple
        usersArrayList.add(new ModelUsers("Filleule 1", "testr"));
        usersArrayList.add(new ModelUsers("Filleule 2", "testr"));
        usersArrayList.add(new ModelUsers("Filleule 3", "testr"));
        usersArrayList.add(new ModelUsers("Filleule 4", "testr"));
        usersArrayList.add(new ModelUsers("Filleule 5", "testr"));


        // Liaison des variables svTextSearch et lvResultat avec les éléments du graphique
        svTextSearch = findViewById(R.id.svTextSearch);
        //lvResultat = findViewById(R.id.lvResultat);

        adapterUser = new LierParrainFilleulAdapterItem(LierParrainFilleulActivity.this, usersArrayList);
        recyclerView.setAdapter(adapterUser);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersArrayList );
        //lvResultat.setAdapter(adapter);
        //lvResultat.setVisibility(View.INVISIBLE);

        // Actions à effectuer lorsque l'utilisateur tape du texte dans la barre de recherche
        svTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               if (usersArrayList.contains(query)){
                   adapterUser.getFilter().filter(query);
                    //lvResultat.setVisibility(View.VISIBLE);
                }else{
                    // Sinon, on fait un toast
                    Toast.makeText(LierParrainFilleulActivity.this, "Aucun utilisateur trouvé avec ce pseudo",Toast.LENGTH_LONG).show();
                }
             return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterUser.getFilter().filter(newText);

                return false;
            }
        });



    }

}