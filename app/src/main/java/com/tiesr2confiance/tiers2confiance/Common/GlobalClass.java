package com.tiesr2confiance.tiers2confiance.Common;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import com.tiesr2confiance.tiers2confiance.Models.ModelEthnicGroup;
import com.tiesr2confiance.tiers2confiance.Models.ModelEyeColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairLength;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelLanguage;
import com.tiesr2confiance.tiers2confiance.Models.ModelMaritalStatus;
import com.tiesr2confiance.tiers2confiance.Models.ModelOuiNon;
import com.tiesr2confiance.tiers2confiance.Models.ModelPersonality;
import com.tiesr2confiance.tiers2confiance.Models.ModelRoles;
import com.tiesr2confiance.tiers2confiance.Models.ModelSexualOrientation;
import com.tiesr2confiance.tiers2confiance.Models.ModelSmoker;
import com.tiesr2confiance.tiers2confiance.Models.ModelSports;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;

import java.util.ArrayList;
import java.util.Objects;

public class GlobalClass extends Application {
    private static final String TAG = "LOGAPP_GlobalClass";
    private static final String TAGAPP = "LOGAPP";
    private int loadedUserDataOK = 0;

    private FirebaseFirestore db;
    private FirebaseUser user;
    private String userId;
    private String userCountryLanguage;// = "EN";
    private String userNickName;
    private String userEmail;
    private long userRole;// = 1L;

    private ArrayList<ModelHobbies> arrayListHobbies = new ArrayList<>();
//    private ArrayList<ModelPersonality> arrayListPersonality = new ArrayList<>();
//private  ArrayList<ModelSports> arrayListSports = new ArrayList<>();

    private  ArrayList<ModelRoles> arrayListRoles = new ArrayList<>();
    private  ArrayList<ModelGenders> arrayListGenders = new ArrayList<>();


    private ArrayList<ModelLanguage> arrayListLanguage = new ArrayList<>();
    //TODO Alimentation des arraylists suivants
    private  ArrayList<ModelEthnicGroup> arrayListEthnicGroup = new ArrayList<>();
    private ArrayList<ModelEyeColor> arrayListEyeColors = new ArrayList<>();
    private  ArrayList<ModelHairColor> arrayListHairColor = new ArrayList<>();
    private  ArrayList<ModelHairLength> arrayListHairLength = new ArrayList<>();
    private  ArrayList<ModelMaritalStatus> arrayListMaritalStatus = new ArrayList<>();
    private ArrayList<ModelOuiNon> arrayListOuiNon = new ArrayList<>();
    private  ArrayList<ModelSexualOrientation> arrayList = new ArrayList<>();
    private  ArrayList<ModelSmoker> arrayListSmoker = new ArrayList<>();

    /************************* Constructors     ***************/
    public GlobalClass() {

    }

    public GlobalClass(int loadedUserDataOK, FirebaseFirestore db, FirebaseUser user, String userId, String userCountryLanguage, String userNickName, String userEmail, long userRole, ArrayList<ModelHobbies> arrayListHobbies, ArrayList<ModelRoles> arrayListRoles, ArrayList<ModelGenders> arrayListGenders, ArrayList<ModelLanguage> arrayListLanguage, ArrayList<ModelEthnicGroup> arrayListEthnicGroup, ArrayList<ModelEyeColor> arrayListEyeColors, ArrayList<ModelHairColor> arrayListHairColor, ArrayList<ModelHairLength> arrayListHairLength, ArrayList<ModelMaritalStatus> arrayListMaritalStatus, ArrayList<ModelOuiNon> arrayListOuiNon, ArrayList<ModelSexualOrientation> arrayList, ArrayList<ModelSmoker> arrayListSmoker) {
        this.loadedUserDataOK = loadedUserDataOK;
        this.db = db;
        this.user = user;
        this.userId = userId;
        this.userCountryLanguage = userCountryLanguage;
        this.userNickName = userNickName;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.arrayListHobbies = arrayListHobbies;
        this.arrayListRoles = arrayListRoles;
        this.arrayListGenders = arrayListGenders;
        this.arrayListLanguage = arrayListLanguage;
        this.arrayListEthnicGroup = arrayListEthnicGroup;
        this.arrayListEyeColors = arrayListEyeColors;
        this.arrayListHairColor = arrayListHairColor;
        this.arrayListHairLength = arrayListHairLength;
        this.arrayListMaritalStatus = arrayListMaritalStatus;
        this.arrayListOuiNon = arrayListOuiNon;
        this.arrayList = arrayList;
        this.arrayListSmoker = arrayListSmoker;
    }

    /************************* Setters     ***************/
    public void setLoadedUserDataOK(int loadedUserDataOK) {
        this.loadedUserDataOK = loadedUserDataOK;
    }

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

    public void setUserRole(long userRole) {
        this.userRole = userRole;
    }

    public void setArrayListHobbies(ArrayList<ModelHobbies> arrayListHobbies) {
        this.arrayListHobbies = arrayListHobbies;
    }

    public void setArrayListRoles(ArrayList<ModelRoles> arrayListRoles) {
        this.arrayListRoles = arrayListRoles;
    }

    public void setArrayListGenders(ArrayList<ModelGenders> arrayListGenders) {
        this.arrayListGenders = arrayListGenders;
    }

    public void setArrayListLanguage(ArrayList<ModelLanguage> arrayListLanguage) {
        this.arrayListLanguage = arrayListLanguage;
    }

    public void setArrayListEthnicGroup(ArrayList<ModelEthnicGroup> arrayListEthnicGroup) {
        this.arrayListEthnicGroup = arrayListEthnicGroup;
    }

    public void setArrayListEyeColors(ArrayList<ModelEyeColor> arrayListEyeColors) {
        this.arrayListEyeColors = arrayListEyeColors;
    }

    public void setArrayListHairColor(ArrayList<ModelHairColor> arrayListHairColor) {
        this.arrayListHairColor = arrayListHairColor;
    }

    public void setArrayListHairLength(ArrayList<ModelHairLength> arrayListHairLength) {
        this.arrayListHairLength = arrayListHairLength;
    }

    public void setArrayListMaritalStatus(ArrayList<ModelMaritalStatus> arrayListMaritalStatus) {
        this.arrayListMaritalStatus = arrayListMaritalStatus;
    }

    public void setArrayListOuiNon(ArrayList<ModelOuiNon> arrayListOuiNon) {
        this.arrayListOuiNon = arrayListOuiNon;
    }

    public void setArrayList(ArrayList<ModelSexualOrientation> arrayList) {
        this.arrayList = arrayList;
    }

    public void setArrayListSmoker(ArrayList<ModelSmoker> arrayListSmoker) {
        this.arrayListSmoker = arrayListSmoker;
    }

    /************************* Getters     ***************/
    public static String getTAG() {
        return TAG;
    }

    public static String getTAGAPP() {
        return TAGAPP;
    }

    public int getLoadedUserDataOK() {
        return loadedUserDataOK;
    }

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

    public long getUserRole() {
        return userRole;
    }

    public ArrayList<ModelHobbies> getArrayListHobbies() {
        return arrayListHobbies;
    }

    public ArrayList<ModelRoles> getArrayListRoles() {
        return arrayListRoles;
    }

    public ArrayList<ModelGenders> getArrayListGenders() {
        return arrayListGenders;
    }

    public ArrayList<ModelLanguage> getArrayListLanguage() {
        return arrayListLanguage;
    }

    public ArrayList<ModelEthnicGroup> getArrayListEthnicGroup() {
        return arrayListEthnicGroup;
    }

    public ArrayList<ModelEyeColor> getArrayListEyeColors() {
        return arrayListEyeColors;
    }

    public ArrayList<ModelHairColor> getArrayListHairColor() {
        return arrayListHairColor;
    }

    public ArrayList<ModelHairLength> getArrayListHairLength() {
        return arrayListHairLength;
    }

    public ArrayList<ModelMaritalStatus> getArrayListMaritalStatus() {
        return arrayListMaritalStatus;
    }

    public ArrayList<ModelOuiNon> getArrayListOuiNon() {
        return arrayListOuiNon;
    }

    public ArrayList<ModelSexualOrientation> getArrayList() {
        return arrayList;
    }

    public ArrayList<ModelSmoker> getArrayListSmoker() {
        return arrayListSmoker;
    }

    /************************* Displayers for Arrays  (in LOGCAT)   ***************/
    public void DisplayAttributes() {
        Log.d(TAGAPP, "DisplayAttributes()");

        Log.d(TAGAPP, "--------------------------------------------------------------------------");
        Log.d(TAGAPP, "------  Connected User Attributes      -----------------------------------");
        Log.d(TAGAPP, "-------------------- userId " + userId);
        Log.d(TAGAPP, "------- userCountryLanguage " + userCountryLanguage);
        Log.d(TAGAPP, "-------------- userNickName " + userNickName);
        Log.d(TAGAPP, "----------------- userEmail " + userEmail);
        Log.d(TAGAPP, "------------------ userRole " + userRole);
        Log.d(TAGAPP, "---------------------------------------------------------------------------");

    }
    public void DisplayGenders() {
        Log.d(TAGAPP, "DisplayGenders()");

        Log.d(TAGAPP, "------- ArrayListGenders ---------");
        Log.d(TAGAPP, "------- ArrayListGenders Size ---------" + arrayListGenders.size());
        Log.d(TAGAPP, "------- ArrayListGenders ---------" + arrayListGenders);
        Log.d(TAGAPP, "----------------");
        for (int i = 0; i < arrayListGenders.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListGenders.get(i).ge_id + " "
                    + arrayListGenders.get(i).ge_country + " "
                    + arrayListGenders.get(i).ge_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    public void DisplayHobbies() {
        Log.d(TAGAPP, "DisplayHobbies() ");

        Log.d(TAGAPP, "------- myArrayListHobbies ---------");
        Log.d(TAGAPP, "------- myArrayListHobbies Size---------" + arrayListHobbies.size());
        Log.d(TAGAPP, "------- myArrayListHobbies ---------" + arrayListHobbies);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListHobbies.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListHobbies.get(i).ho_id + " "
                    + arrayListHobbies.get(i).ho_country + " "
                    + arrayListHobbies.get(i).ho_label);
        }
        Log.d(TAGAPP, "----------------");

    }

    public void DisplayRoles() {
        Log.d(TAGAPP, "DisplayHobbies() ");

        Log.d(TAGAPP, "------- myArrayListRoles ---------");
        Log.d(TAGAPP, "------- myArrayListRoles Size---------" + arrayListRoles.size());
        Log.d(TAGAPP, "------- myArrayListRoles ---------" + arrayListRoles);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListRoles.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListRoles.get(i).ro_id + " "
                    + arrayListRoles.get(i).ro_country + " "
                    + arrayListRoles.get(i).ro_label  + " "
                    + arrayListRoles.get(i).ro_is_godfather
            );
        }
        Log.d(TAGAPP, "----------------");

    }

    public void DisplayLanguages() {
        Log.d(TAGAPP, "DisplayHobbies() ");

        Log.d(TAGAPP, "------- myArrayListLanguage ---------");
        Log.d(TAGAPP, "------- myArrayListLanguage Size---------" + arrayListLanguage.size());
        Log.d(TAGAPP, "------- myArrayListLanguage ---------" + arrayListLanguage);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListLanguage.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListLanguage.get(i).la_code + " "
                    + arrayListLanguage.get(i).la_label);
        }
        Log.d(TAGAPP, "----------------");
    }
    /************************* Loaders     ***************/
    /************************************************************************************************/
    /************************************************************************************************/
    /************************************************************************************************/
    public void LoadArraysDataFromFirestore() {
        LoadGendersDataFromFirestore();
        LoadHobbiesDataFromFirestore();
        LoadRolesDataFromFirestore();
        LoadLanguageDataFromFirestore();
    }


/************************************************************************************************/
/************************************************************************************************/
/********************* USER DATA                                    *****************************/
/************************************************************************************************/
    /************************************************************************************************/
    public void LoadUserDataFromFirestore() {
        Log.i(TAG, "----- GlobalClass getUserDataFromFirestore START -----");

//        loadedUserDataOK    =   0;


        try
        {
            db      = FirebaseFirestore.getInstance();
            user    = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null){
                userId  = user.getUid();
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore : CAN\'T LOAD db, user, userid-----" );
        }


        Log.i(TAG, "XXXXXXXX GlobalClass : LoadUserDataFromFirestore(): userId : " + userId);

        DocumentReference docRefUserConnected;

        try {
            docRefUserConnected = db.document("users/"+ userId); //bSfRUKasZ7PyHnew1jwqG6jksl03
            //docRefUserConnected = db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03");

            docRefUserConnected.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) { //asynchrone
                            Log.d(TAG,"******************** LoadUserDataFromFirestore() addOnCompleteListener ********************");

                            Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : " + userId + "-----");
                            ModelUsers connectedUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);

                            userNickName        = connectedUser.getUs_nickname();
                            userEmail           = connectedUser.getUs_email();
                            userCountryLanguage = connectedUser.getUs_country_lang();
                            userRole            = connectedUser.getUs_role();

                            loadedUserDataOK    =   1;

//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userId : " + userId);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userNickName : " + userNickName);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userCountryLanguage : " + userCountryLanguage);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userEmail : " + userEmail);

                            Log.i(TAG, "----- Update on getUserDataFromFirestore -----");

                            //                        Toast.makeText(getApplicationContext(), "USER FOUND in collection \"users\"", Toast.LENGTH_SHORT).show();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadedUserDataOK    =   1;
                            Log.e(TAG, "LoadUserDataFromFirestore() onFailure: ");
                        }
                    });


        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };



        DisplayAttributes();

        Log.i(TAG, "----- GlobalClass getUserDataFromFirestore END -----");
    } // END LoadUserDataFromFirestore()

/************************************************************************************************/
/************************************************************************************************/
/********************* GENDERS                                      *****************************/
/************************************************************************************************/
    /************************************************************************************************/
    public void LoadGendersDataFromFirestore() {
        Log.i(TAG, "GlobalClass loadGendersDataFromFirestore: START");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            LoadUserDataFromFirestore();

        }

        ArrayList<ModelGenders> myArrayListGenders = new ArrayList<>();

        try
        {
            Query queryGenders = db.collection("genders")
                    .whereEqualTo("ge_country", userCountryLanguage);

            queryGenders.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Genders onSuccess but LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String genderDocId = documentSnapshot.getId();

                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefGender = db.document("genders/"+ genderDocId);
                                        docRefGender.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelGenders gender= documentSnapshot.toObject(ModelGenders.class);
                                                Log.i(TAG, "onSuccess ******** gender : " + gender.getGe_id() + " " + gender.getGe_country() + " " + gender.getGe_label());
                                                myArrayListGenders.add(gender);

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
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };

        arrayListGenders = myArrayListGenders;

//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListGenders : " + arrayListGenders);
//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListGenders.size() *********** " + arrayListGenders.size());

        Log.i(TAG, "GlobalClass loadGendersDataFromFirestore: END");

    } // END loadGendersDataFromFirestore()







    public void LoadHobbiesDataFromFirestore() {
        Log.i(TAG, "START ----- GlobalClass : LoadRolesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");

        Log.i(TAG, "GlobalClass LoadHobbiesDataFromFirestore: START");
        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
//                        || (userCountryLanguage == "FR")
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            Log.i(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }

        ArrayList<ModelHobbies> myArrayListHobbies = new ArrayList<>();

        try
        {
            Query queryHobbies = db.collection("hobbies")
                    .whereEqualTo("ho_country", userCountryLanguage);

            queryHobbies.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Hobbies onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String hobbieDocId = documentSnapshot.getId();

                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefhobbie = db.document("hobbies/"+ hobbieDocId);
                                        docRefhobbie.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelHobbies hobbie= documentSnapshot.toObject(ModelHobbies.class);
                                                Log.i(TAG, "onSuccess ******** hobby : " + hobbie.getHo_id() + " " + hobbie.getHo_country() + " " + hobbie.getHo_label());
                                                myArrayListHobbies.add(hobbie);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// mArrayListhobbies : " + myArrayListhobbies);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// myArrayListhobbies.size() *********** " + myArrayListhobbies.size());

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
                            Toast.makeText(getApplicationContext(), "Error getting hobbies from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting hobbies from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };

        arrayListHobbies = myArrayListHobbies;


        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHobbies : " + arrayListHobbies);
        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHobbies.size() *********** " + arrayListHobbies.size());

        Log.i(TAG, "GlobalClass loadHobbiesDataFromFirestore: END");

    } // END loadhobbiesDataFromFirestore()



/************************************************************************************************/
/************************************************************************************************/
/********************* Roles                                      *****************************/
/************************************************************************************************/
    /************************************************************************************************/



    public void LoadRolesDataFromFirestore() {
        Log.i(TAG, "START ----- GlobalClass : LoadRolesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadRolesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }

        Log.i(TAG, "loadRolesDataFromFirestore: BEGIN");
        ArrayList<ModelRoles> myArrayListRoles = new ArrayList<>();
        Log.i(TAG, "loadRolesDataFromFirestore: BEGIN userCountryLanguage >>>>" + userCountryLanguage);

        try
        {
            Query queryRoles = db.collection("roles")
                    .whereEqualTo("ro_country", userCountryLanguage);

            queryRoles.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Roles onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String rolesDocId = documentSnapshot.getId();

                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefRoles = db.document("roles/"+ rolesDocId);
                                        docRefRoles.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelRoles roles = documentSnapshot.toObject(ModelRoles.class);
                                                Log.i(TAG, "onSuccess ******** hobby : " + roles.getRo_id() + " " + roles.getRo_country() + " " + roles.getRo_label());
                                                myArrayListRoles.add(roles);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// mArrayListRoles : " + myArrayListRoles);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// myArrayListRoles.size() *********** " + myArrayListRoles.size());

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
                            Toast.makeText(getApplicationContext(), "Error getting Roles from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Roles from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };

        arrayListRoles = myArrayListRoles;

//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListRoles : " + arrayListRoles);
//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListRoles.size() *********** " + arrayListRoles.size());

        Log.i(TAG, "loadRolesDataFromFirestore: END");

    } // END loadRolesDataFromFirestore()



/************************************************************************************************/
/************************************************************************************************/
/********************* Language                                        *****************************/
/************************************************************************************************/
    /************************************************************************************************/



    public void LoadLanguageDataFromFirestore() {
        Log.i(TAG, "START ----- GlobalClass : LoadRolesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadRolesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }

        Log.i(TAG, "loadLanguageDataFromFirestore: BEGIN");
        ArrayList<ModelLanguage> myArrayListLanguage = new ArrayList<>();
        Log.i(TAG, "loadLanguageDataFromFirestore: BEGIN userCountryLanguage >>>>" + userCountryLanguage);

        try
        {
            Query queryLanguage = db.collection("roles")
                    .whereEqualTo("ro_country", userCountryLanguage);

            queryLanguage.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Roles onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String languagesDocId = documentSnapshot.getId();

                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefRoles = db.document("roles/"+ languagesDocId);
                                        docRefRoles.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelLanguage language = documentSnapshot.toObject(ModelLanguage.class);
                                                Log.i(TAG, "onSuccess ******** hobby : " + language.getLa_code() + " " + language.getLa_label() );
                                                myArrayListLanguage.add(language);

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
                            Toast.makeText(getApplicationContext(), "Error getting Language from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Language from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListLanguage = myArrayListLanguage;

//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListLanguage : " + arrayListLanguage);
//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListLanguage.size() *********** " + arrayListLanguage.size());

        Log.i(TAG, "loadLanguageDataFromFirestore: END");

    } // END loadLanguageDataFromFirestore()



/************************************************************************************************/
/************************************************************************************************/
/*********************        EthnicGroup                               *****************************/
/************************************************************************************************/
/************************************************************************************************/








/************************************************************************************************/
/************************************************************************************************/
/*********************         Eye Color                              *****************************/
/************************************************************************************************/
/************************************************************************************************/















/************************************************************************************************/
/************************************************************************************************/
/*********************             Gender                          *****************************/
/************************************************************************************************/
/************************************************************************************************/


















/************************************************************************************************/
/************************************************************************************************/
/*********************              HairColor                         *****************************/
/************************************************************************************************/
/************************************************************************************************/

























/************************************************************************************************/
/************************************************************************************************/
/*********************                HairLength                       *****************************/
/************************************************************************************************/
/************************************************************************************************/































/************************************************************************************************/
/************************************************************************************************/
/*********************                 Oui Non                      *****************************/
/************************************************************************************************/
/************************************************************************************************/





























/************************************************************************************************/
/************************************************************************************************/
/*********************                  Marital Status                     *****************************/
/************************************************************************************************/
/************************************************************************************************/






























/************************************************************************************************/
/************************************************************************************************/
/*********************               Personality                        *****************************/
/************************************************************************************************/
/************************************************************************************************/
































/************************************************************************************************/
/************************************************************************************************/
/*********************          SexualOrientation                             *****************************/
/************************************************************************************************/
/************************************************************************************************/














/************************************************************************************************/
/************************************************************************************************/
/*********************           shape                            *****************************/
/************************************************************************************************/
/************************************************************************************************/
















/************************************************************************************************/
/************************************************************************************************/
/*********************           Smoker                            *****************************/
/************************************************************************************************/
/************************************************************************************************/















/************************************************************************************************/
/************************************************************************************************/
/*********************           Sports                            *****************************/
/************************************************************************************************/
/************************************************************************************************/














/************************************************************************************************/
/************************************************************************************************/
/*************************                     Cycle of Life                      ***************/
/************************************************************************************************/
    /************************************************************************************************/






    @Override
    public void onCreate() {
        super.onCreate();

//        for (int i = 0; i < 10; i++) {
//            if (
//                    ((userCountryLanguage == "") ? null : userCountryLanguage) == null
//                            || userCountryLanguage == "FR"
//                            || ((userId == "") ? null : userCountryLanguage) == null
//            ) {
//                db = FirebaseFirestore.getInstance();
//                user = FirebaseAuth.getInstance().getCurrentUser();
//                userId = user.getUid();
//                LoadUserDataFromFirestore();
//                //userCountryLanguage = "FR";
//                Log.i(TAG, "----- GlobalClass : LoadGendersDataFromFirestore userCountryLanguage : " + userCountryLanguage + "-----");
//            } else {
//                break;
//            }
//        }

//        LoadUserDataFromFirestore();
//        LoadUserDataFromFirestore();
//        LoadUserDataFromFirestore();
//        LoadGendersDataFromFirestore();
//        LoadHobbiesDataFromFirestore();
    }





    /*****************************************************************************************************/
    public void LoadUserDataWithNoCallback() {
        final String[] msg = {"Sans Callback : pas de message"};
        db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            msg[0] =   "SansCallback User Trouvé";
                        } else {
                            msg[0] =   "SansCallback User n'existe pas";
                        }
                    }
                });
        Log.w(TAGAPP, msg[0]);
    }



    /************************************************************************************************/
    /************************************************************************************************/

    interface UserDataCallback {
        void userExist(boolean exist);
    }


    public void LoadUserData(UserDataCallback userDataCallback) {

        db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            userDataCallback.userExist(true);

                        } else {
                            userDataCallback.userExist(false);
                        }
                    }
                });

    }

    public void LoadUserDataWithCallBack(){

        LoadUserData(new UserDataCallback() {
            @Override
            public void userExist(boolean exist) {
                String msg = "Avec Callback : pas de message";
//                Log.d(TAG, "CallBack");
                if (exist) {
                    msg =   "TASK avec CallBack : User exists";
                    Log.d(TAGAPP, msg);
                } else {
                    msg =   "Task CallBack : User does not exist";
                    Log.d(TAGAPP, msg);
                }
            }
        });

    }


/************************************************************************************************/
/************************************************************************************************/
/************************************************************************************************/
/************************************************************************************************/
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//public void LoadUserDataFromFirestore_OK() {
////    public void LoadUserDataFromFirestore(UserDataCallback userDataCallback) {
//
//    if (userId == null){
//        try {
//            db      = FirebaseFirestore.getInstance();
//            user    = FirebaseAuth.getInstance().getCurrentUser();
//
//            if (user != null){
//                userId  = user.getUid();
//            }
//
////                userNickName = ;
////                userEmail = "userEmail : Not Retrieved Yet From FS";
////                userCountryLanguage = "FR";
//
//        }catch (Exception e) {
//            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore : CAN\'T LOAD db, user, userid-----" );
//        }
//
//        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore : "+ userId +"-----" );
//
//    }
//
//    Log.i(TAG, "XXXXXXXX GlobalClass : LoadUserDataFromFirestore(): userId : " + userId);
//
//    DocumentReference docRefUserConnected;
//
//    try {
//        docRefUserConnected = db.document("users/"+ userId); //bSfRUKasZ7PyHnew1jwqG6jksl03
//        //docRefUserConnected = db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03");
//
//        docRefUserConnected.get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) { //asynchrone
//                        ModelUsers connectedUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
//
//                        userNickName        = connectedUser.getUs_nickname();
//                        userEmail           = connectedUser.getUs_email();
//                        userCountryLanguage = connectedUser.getUs_country_lang();
//                        userRole            = connectedUser.getUs_role();
//
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "LoadUserDataFromFirestore() onFailure: ");
//                    }
//                });
//
//
//
//    }
//    catch (Exception e) {
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----" );
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userNickName "  + userNickName);
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
//    };
//
//
//
//
//    Log.i(TAG, "----- END getUserDataFromFirestore -----");
//} // END LoadUserDataFromFirestore()
//



/************************************************************************************************/
/************************************************************************************************/
/********************* Hobbies                                      *****************************/
/************************************************************************************************/
/************************************************************************************************/





} // END OF CLASS
