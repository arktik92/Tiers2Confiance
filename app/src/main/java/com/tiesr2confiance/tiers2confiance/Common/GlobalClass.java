package com.tiesr2confiance.tiers2confiance.Common;

import android.app.Application;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;

import java.util.ArrayList;

public class GlobalClass extends Application {
    private static final String TAG = "LOGAPP_GlobalClass";

    private FirebaseFirestore db;
    private FirebaseUser user;
    private String userId;
    private String userCountryLanguage;
    private String userNickName;
    private String userEmail;

    private  ArrayList<ModelGenders> arrayListGenders = new ArrayList<>();
    private ArrayList<ModelHobbies> arrayListHobbies = new ArrayList<>();

    /************************* Constructors     ***************/
    public GlobalClass() {

    }

    public GlobalClass(FirebaseFirestore db, FirebaseUser user, String userId, String userCountryLanguage, String userNickName, String userEmail, ArrayList<ModelGenders> arrayListGenders, ArrayList<ModelHobbies> arrayListHobbies) {
        this.db = db;
        this.user = user;
        this.userId = userId;
        this.userCountryLanguage = userCountryLanguage;
        this.userNickName = userNickName;
        this.userEmail = userEmail;
        this.arrayListGenders = arrayListGenders;
        this.arrayListHobbies = arrayListHobbies;
    }

    /************************* Setters     ***************/

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserCountryLanguage(String userCountryLanguage) {
        this.userCountryLanguage = userCountryLanguage;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setArrayListGenders(ArrayList<ModelGenders> arrayListGenders) {
        this.arrayListGenders = arrayListGenders;
    }

    public void setArrayListHobbies(ArrayList<ModelHobbies> arrayListHobbies) {
        this.arrayListHobbies = arrayListHobbies;
    }

    /************************* Getters     ***************/

    public FirebaseFirestore getDb() {
        return db;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserCountryLanguage() {
        return userCountryLanguage;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ArrayList<ModelGenders> getArrayListGenders() {
        return arrayListGenders;
    }

    public ArrayList<ModelHobbies> getArrayListHobbies() {
        return arrayListHobbies;
    }

    /************************* Loaders     ***************/

    public void loadGendersDataFromFirestore() {

        Log.i(TAG, "loadGendersDataFromFirestore: BEGIN");
        ArrayList<ModelGenders> myArrayListGenders = new ArrayList<>();
        Log.i(TAG, "loadGendersDataFromFirestore: BEGIN userCountryLanguage >>>>" + userCountryLanguage);
        userCountryLanguage = "EN";

         myArrayListGenders.clear();

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        userId = user.getUid();
//        db = FirebaseFirestore.getInstance();
        Query queryGenders = db.collection("genders")
                .whereEqualTo("ge_country", userCountryLanguage);
        //.whereNotIn("us_auth_uid", critere) ici ça plante ??
        //.orderBy("ge_label");
        //.orderBy("ge_label", Query.Direction.DESCENDING);
        //.orderBy("ge_label", Query.Direction.ASCENDING);

        queryGenders.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.i(TAG, "onSuccess: LIST EMPTY");

                } else {
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        if (documentSnapshot.exists()) {
                            String genderDocId = documentSnapshot.getId();

                            Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
//                            DocumentReference documentReference1 = FirebaseFirestore.getInstance().document("some path");
                            DocumentReference docRefGender = db.document("genders/"+ genderDocId);
                            docRefGender.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    ModelGenders gender= documentSnapshot.toObject(ModelGenders.class);
                                    Log.i(TAG, "onSuccess ******** gender : " + gender.getGe_id() + " " + gender.getGe_country() + " " + gender.getGe_label());
                                    myArrayListGenders.add(gender);
                                    Log.i(TAG, "onSuccess XXXXX VarGlobale XXXXXX////// mArrayListGenders : " + myArrayListGenders);
                                    Log.i(TAG, "XXXXXX VarGlobale XXXXX////// myArrayListGenders.size() *********** " + myArrayListGenders.size());

                                }


                            });
                        }
                    }



                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting genders from FireStore!!!", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onFailure : Error getting genders from FireStore!!!");

                    }
                });


        Log.i(TAG, "onSuccess **** VarGlobal e**** mArrayListGenders : " + myArrayListGenders);
        Log.i(TAG, "**** VarGlobale ***** myArrayListGenders.size() *********** " + myArrayListGenders.size());

        arrayListGenders = myArrayListGenders;

        Log.i(TAG, "loadGendersDataFromFirestore: END");
    } // END loadGendersDataFromFirestore()
}
