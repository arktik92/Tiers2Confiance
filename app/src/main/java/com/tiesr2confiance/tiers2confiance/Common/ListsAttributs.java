package com.tiesr2confiance.tiers2confiance.Common;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.tiesr2confiance.tiers2confiance.R;

import java.util.HashMap;

public class ListsAttributs extends Application {

    public HashMap<Long, String> globalVarValue;
    public static Resources res;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

    public HashMap<Long, String> getGlobalVarValue(){
        return globalVarValue;
    }

    public void setGlobalVarValue(HashMap<Long, String> globalVarValue) {

        globalVarValue.put((long)1, getContext().getString(R.string.ho_artisanat_text));
        globalVarValue.put((long)2, getContext().getString(R.string.ho_balades_text));
        globalVarValue.put((long)3, getContext().getString(R.string.ho_boites_text));
        globalVarValue.put((long)4, getContext().getString(R.string.ho_cafe_text));
        globalVarValue.put((long)5, getContext().getString(R.string.ho_charites_text));
        globalVarValue.put((long)6, getContext().getString(R.string.ho_clubs_text));
        globalVarValue.put((long)7, getContext().getString(R.string.ho_cuisiner_text));
        globalVarValue.put((long)8, getContext().getString(R.string.ho_déguster_text));
        globalVarValue.put((long)9, getContext().getString(R.string.ho_fairerencontres_text));
        globalVarValue.put((long)10, getContext().getString(R.string.ho_films_text));
        globalVarValue.put((long)11, getContext().getString(R.string.ho_jardiner_text));
        globalVarValue.put((long)12, getContext().getString(R.string.ho_jeuxcartes_text));
        globalVarValue.put((long)13, getContext().getString(R.string.ho_jeuxvideos_text));

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