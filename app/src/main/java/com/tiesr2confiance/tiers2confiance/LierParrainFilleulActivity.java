package com.tiesr2confiance.tiers2confiance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class LierParrainFilleulActivity extends AppCompatActivity {

    SearchView svTextSearch;
    ListView lvResultat;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lier_parrain_filleul);

        // Si l'user connecté est un filleul
        setTitle(getString(R.string.Lier_pf_titre_filleul));

        // Si l'user connecté est un parrain
        setTitle(getString(R.string.Lier_pf_titre_parrain));


        // Création d'une fausse liste pour l'exemple
        list = new ArrayList<>();
        list.add("Filleul1");
        list.add("Filleul2");
        list.add("Filleul3");
        list.add("Filleul4");
        list.add("Filleul5");

        // Liaison des variables svTextSearch et lvResultat avec les éléments du graphique
        svTextSearch = findViewById(R.id.svTextSearch);
        lvResultat = findViewById(R.id.lvResultat);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list );
        lvResultat.setAdapter(adapter);

        // Actions à effectuer lorsque l'utilisateur tape du texte dans la barre de recherche
        svTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Si le résultat est trouvé, on l'affiche
                if (list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    // Sinon, on fait un toast
                    Toast.makeText(LierParrainFilleulActivity.this, "Aucun utilisateur trouvé avec ce pseudo",Toast.LENGTH_LONG).show();
                }
             return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



    }

}