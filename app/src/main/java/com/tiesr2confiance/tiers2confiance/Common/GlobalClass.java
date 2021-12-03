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

import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
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

    private  ArrayList<ModelGenders> arrayListGenders = new ArrayList<>();
    private ArrayList<ModelHobbies> arrayListHobbies = new ArrayList<>();

    /************************* Constructors     ***************/
    public GlobalClass() {

    }

    public GlobalClass(FirebaseFirestore db, FirebaseUser user, String userId, String userCountryLanguage, String userNickName, String userEmail, long userRole, ArrayList<ModelGenders> arrayListGenders, ArrayList<ModelHobbies> arrayListHobbies) {
        this.db = db;
        this.user = user;
        this.userId = userId;
        this.userCountryLanguage = userCountryLanguage;
        this.userNickName = userNickName;
        this.userEmail = userEmail;
        this.userRole = userRole;
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

    public void setUserRole(long userRole) {
        this.userRole = userRole;
    }

    public void setArrayListGenders(ArrayList<ModelGenders> arrayListGenders) {
        this.arrayListGenders = arrayListGenders;
    }

    public void setArrayListHobbies(ArrayList<ModelHobbies> arrayListHobbies) {
        this.arrayListHobbies = arrayListHobbies;
    }
/************************* Getters     ***************/
    public static String getTAG() {
        return TAG;
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

    public ArrayList<ModelGenders> getArrayListGenders() {
        return arrayListGenders;
    }

    public ArrayList<ModelHobbies> getArrayListHobbies() {
        return arrayListHobbies;
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
                    + arrayListGenders.get(i).getGe_id() + " "
                    + arrayListGenders.get(i).getGe_country() + " "
                    + arrayListGenders.get(i).getGe_label());
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
                    + arrayListHobbies.get(i).getHo_id() + " "
                    + arrayListHobbies.get(i).getHo_country() + " "
                    + arrayListHobbies.get(i).getHo_label());
        }
        Log.d(TAGAPP, "----------------");

    }
    /************************* Loaders     ***************/
    /************************************************************************************************/
    /************************************************************************************************/
    /************************************************************************************************/

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
/************************************************************************************************/
    /************************************************************************************************/
    public void LoadUserDataFromFirestore() {
//    public void LoadUserDataFromFirestore(UserDataCallback userDataCallback) {
        loadedUserDataOK    =   0;

        if (userId == null){
            try {
                db      = FirebaseFirestore.getInstance();
                user    = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null){
                    userId  = user.getUid();
                }

//                userNickName = ;
//                userEmail = "userEmail : Not Retrieved Yet From FS";
//                userCountryLanguage = "FR";

            }catch (Exception e) {
                Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore : CAN\'T LOAD db, user, userid-----" );
            }

            Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore : "+ userId +"-----" );

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

                        DisplayAttributes();

//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userId : " + userId);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userNickName : " + userNickName);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userCountryLanguage : " + userCountryLanguage);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userEmail : " + userEmail);

                        Log.i(TAG, "----- Update on getUserDataFromFirestore -----");

                        //                        Toast.makeText(getApplicationContext(), "USER FOUND in collection \"users\"", Toast.LENGTH_SHORT).show();

                }
            })

            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot myDocSnapshot) {
                    Log.d(TAG,"******************** LoadUserDataFromFirestore() addOnSuccessListener ********************");

                    if (myDocSnapshot.exists()) {

//                        userNickName    = myDocSnapshot.getString("us_nickname");// + " " + userId;
//                        userEmail       = myDocSnapshot.getString("us_email");// + " " + userId;
                        //  ModelUsers connectedUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class); // Méthode pour onComplete

                        ModelUsers connectedUser = myDocSnapshot.toObject(ModelUsers.class);
                        userNickName        = connectedUser.getUs_nickname();
                        userEmail           = connectedUser.getUs_email();
                        userCountryLanguage = connectedUser.getUs_country_lang();
                        userRole            = connectedUser.getUs_role();
                        loadedUserDataOK    =   1;

                        DisplayAttributes();

//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userId : " + userId);
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userNickName : " + userNickName);
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userCountryLanguage : " + userCountryLanguage);
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userEmail : " + userEmail);

                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess -----");

//                    Toast.makeText(getApplicationContext(),"USER FOUND in collection \"users\"", Toast.LENGTH_SHORT).show();

                    } else {

                        userNickName = "No USER FOUND";
//                    Toast.makeText(getApplicationContext(),"No Document found in collection \"users\" for this LOGIN/USER", Toast.LENGTH_SHORT).show();
                    }
                }
            })

            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadedUserDataOK    =   1;
                    Log.e(TAG, "LoadUserDataFromFirestore() onFailure: ");
                }
            });



//            docRefUserConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot myDocSnapshot) {
//
//                    if (myDocSnapshot.exists()) {
//
////                        userNickName    = myDocSnapshot.getString("us_nickname");// + " " + userId;
////                        userEmail       = myDocSnapshot.getString("us_email");// + " " + userId;
//
//                        ModelUsers connectedUser = myDocSnapshot.toObject(ModelUsers.class);
//                        userNickName        = connectedUser.getUs_nickname();
//                        userEmail           = connectedUser.getUs_email();
//                        userCountryLanguage = connectedUser.getUs_country_lang();
//                        userRole            = connectedUser.getUs_role();
//
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userId : " + userId);
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userNickName : " + userNickName);
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userCountryLanguage : " + userCountryLanguage);
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess userEmail : " + userEmail);
//
//                        Log.i(TAG, "----- Update on getUserDataFromFirestore addOnSuccessListener onSuccess -----");
//
////                    Toast.makeText(getApplicationContext(),"USER FOUND in collection \"users\"", Toast.LENGTH_SHORT).show();
//
//                    } else {
//
//                        userNickName = "No USER FOUND";
////                    Toast.makeText(getApplicationContext(),"No Document found in collection \"users\" for this LOGIN/USER", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });



        }
        catch (Exception e) {
                Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----" );
                Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userNickName "  + userNickName);
                Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
                Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };




        Log.i(TAG, "----- END getUserDataFromFirestore -----");
    } // END LoadUserDataFromFirestore()

/************************************************************************************************/
/************************************************************************************************/
    public void LoadGendersDataFromFirestore() {
        loadedUserDataOK    =   0;
        Log.i(TAG, "START ----- GlobalClass : LoadGendersDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
//                || (userCountryLanguage == "FR")
                || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            db      = FirebaseFirestore.getInstance();
//            user    = FirebaseAuth.getInstance().getCurrentUser();
//            userId  = user.getUid();
//            userNickName = "userNickName : Not Retrieved Yet from FS";
//            userEmail = "userEmail : Not Retrieved Yet From FS";
//            userCountryLanguage = "FR";
            LoadUserDataFromFirestore();

//            while (loadedUserDataOK    ==   0){
//                Util.waitfor(500);
//            }


            Log.i(TAG, "----- GlobalClass : LoadGendersDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }

        Log.i(TAG, "loadGendersDataFromFirestore: BEGIN");
        ArrayList<ModelGenders> myArrayListGenders = new ArrayList<>();
        Log.i(TAG, "loadGendersDataFromFirestore: BEGIN userCountryLanguage >>>>" + userCountryLanguage);

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
    //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// mArrayListGenders : " + myArrayListGenders);
    //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// myArrayListGenders.size() *********** " + myArrayListGenders.size());

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

        Log.i(TAG, "**** INIT GlobalClass ***** arrayListGenders : " + arrayListGenders);
        Log.i(TAG, "**** INIT GlobalClass ***** arrayListGenders.size() *********** " + arrayListGenders.size());

        Log.i(TAG, "loadGendersDataFromFirestore: END");

    } // END loadGendersDataFromFirestore()




    public void LoadHobbiesDataFromFirestore() {
        Log.i(TAG, "START ----- GlobalClass : LoadHobbiesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
//                        || (userCountryLanguage == "FR")
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            db      = FirebaseFirestore.getInstance();
//            user    = FirebaseAuth.getInstance().getCurrentUser();
//            userId  = user.getUid();
//            userNickName = "userNickName : Not Retrieved Yet from FS";
//            userEmail = "userEmail : Not Retrieved Yet From FS";
//            userCountryLanguage = "FR";
            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }

        Log.i(TAG, "loadHobbiesDataFromFirestore: BEGIN");
        ArrayList<ModelHobbies> myArrayListHobbies = new ArrayList<>();
        Log.i(TAG, "loadHobbiesDataFromFirestore: BEGIN userCountryLanguage >>>>" + userCountryLanguage);

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

//        Log.i(TAG, "**** INIT GlobalClass ***** mArrayListhobbies : " + myArrayListhobbies);
//        Log.i(TAG, "**** INIT GlobalClass ***** myArrayListhobbies.size() *********** " + myArrayListhobbies.size());

        arrayListHobbies = myArrayListHobbies;

//        Util.waitfor(2000);

        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHobbies : " + arrayListHobbies);
        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHobbies.size() *********** " + arrayListHobbies.size());

        Log.i(TAG, "loadHobbiesDataFromFirestore: END");

    } // END loadhobbiesDataFromFirestore()








    /************************* Cycle of Life     ***************/
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

} // END OF CLASS
