package com.tiesr2confiance.tiers2confiance.Common;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListsAttributs extends Application {


    // Les listes de valeur des listes déroulantes
    public HashMap<Long, String> HashmapHobbie = new HashMap<Long, String>();


    public HashMap<Long, String> getGlobalVarValue() {
        return HashmapHobbie;
    }

    public void setGlobalVarValue(HashMap<Long, String> HashmapHobbie) {
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



    /**  TEST CREATION LIST GLOBALE DEPUIS FIRESTORE => GALEEEEEEEERE
     *
     *
    private static final String TAG = "ListAttributs :";
    public static ArrayList<ModelGenders> myArrayListGenders;
    public static ArrayList<ModelHobbies> myArrayListHobbies;


    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String userCountryLanguage = "EN";

    public ListsAttributs(FirebaseFirestore db, String userCountryLanguage) {
        this.db = db;
        this.userCountryLanguage = userCountryLanguage;
    }

    public ArrayList getHobbiesDataFromFirestore() {

        myArrayListHobbies = new ArrayList<>();

        Query queryHobbies = db.collection("hobbies")
                .whereEqualTo("ho_country", userCountryLanguage);

        queryHobbies.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.i(TAG, "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        if (documentSnapshot.exists()) {
                            String hobbiesDocId = documentSnapshot.getId();
                            ModelHobbies hobbie= documentSnapshot.toObject(ModelHobbies.class);
                            DocumentReference docRefHobbie = db.document("hobbies/"+ hobbiesDocId);
                            myArrayListHobbies.add(hobbie);
                            Log.e(TAG, "onSuccess EN DEDAN: TAILLE " + myArrayListHobbies.size() );
                        }

                        }
                    }
                }
        });
        return myArrayListHobbies;
    }
            **/


}