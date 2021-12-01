package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.TaskStackBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulActivity;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.User.UserActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainActivityConstraintLayout;

    /**  Var globales **/
    private static final String TAG = "LOGAPP_MainActivity";

    /** Variable Firebase Auth **/
    private FirebaseFirestore db;
    private static FirebaseUser user;
    private static String userId;
    private static String userNickName;
    private static String userCountryLanguage;
    private static String userEmail;

    public MainActivity() {
        super();
        //InitGlobalVariables();
        Log.e(TAG, "------- XXXX MainActivity constructor1 START ---------");
    }

    public MainActivity(int contentLayoutId) {
        super(contentLayoutId);
        //InitGlobalVariables();
        Log.e(TAG, "------- XXXX MainActivity constructor2 START ---------");
    }

    @Override
    public void setTheme(int resId) {
        super.setTheme(resId);
        Log.e(TAG, "------- XXXX MainActivity setTheme START ---------");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //InitArrays();
        Log.e(TAG, "------- XXXX MainActivity onPostCreate START ---------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        InitGlobalVariables(); // GlobalClass
        Log.e(TAG, "------- XXXX MainActivity onStart START ---------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "------- XXXX MainActivity onStop START ---------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "------- XXXX MainActivity onDestroy START ---------");
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
        Log.e(TAG, "------- XXXX MainActivity onCreateSupportNavigateUpTaskStack START ---------");
    }

    @Override
    public void onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onPrepareSupportNavigateUpTaskStack(builder);
        Log.e(TAG, "------- XXXX MainActivity onPrepareSupportNavigateUpTaskStack START ---------");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.e(TAG, "------- XXXX MainActivity onContentChanged START ---------");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "------- XXXX MainActivity onCreate START ---------");
        Log.i(TAG, "------- MainActivity onCreate START ---------");

//        InitGlobalVariables(); // GlobalClass
//        InitArrays();// GlobalClass

        Button btnInitVars1;
        btnInitVars1 = findViewById(R.id.btn_init_vars1);

        btnInitVars1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   InitGlobalVariablesStep1();
               }
           });

        Button btnInitVars2;
        btnInitVars2 = findViewById(R.id.btn_init_vars2);

        btnInitVars2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitGlobalVariablesStep2();
            }
        });

        Button btnInitArrays;
        btnInitArrays = findViewById(R.id.btn_init_arrays);
        btnInitArrays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitArrays();
            }
        });


        Button btnInitAll;
        btnInitAll = findViewById(R.id.btn_init_all);
        btnInitAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitGlobalVarsAndArrays();
            }

        });

        Button btnDisplayGendersArray;
        btnDisplayGendersArray = findViewById(R.id.btn_Display_Genders_Array);
        btnDisplayGendersArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final GlobalClass globalVariables = (GlobalClass) getApplicationContext();

                ArrayList<ModelGenders> arrayListGenders = globalVariables.getArrayListGenders();



                mainActivityConstraintLayout = findViewById(R.id.mainActivityConstraintLayout);
                String msg = "***** arrayListGenders.size() *********** " + arrayListGenders.size();
                Log.d(TAG, msg );
                msg = "***** arrayListGenders *********** " + arrayListGenders;
                Log.d(TAG, msg );
                Util.showSnackBar(mainActivityConstraintLayout,msg);
                Util.waitfor(500);

//                msg = "***** user *********** " + globalVariables.getUserCountryLanguage()+ "/" + globalVariables.getUserId();
//                Log.d(TAG, msg );
//                Util.showSnackBar(mainActivityConstraintLayout,msg);
//                Util.waitfor(500);


            };
        });

            Button btnDisplayHobbiesArray;
            btnDisplayHobbiesArray = findViewById(R.id.btn_Display_Hobbies_Array);
            btnDisplayHobbiesArray.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final GlobalClass globalVariables = (GlobalClass) getApplicationContext();

                    ArrayList<ModelHobbies> arrayListHobbies = globalVariables.getArrayListHobbies();



                    mainActivityConstraintLayout = findViewById(R.id.mainActivityConstraintLayout);
                    String msg = "***** arrayListHobbies.size() *********** " + arrayListHobbies.size();
                    Log.d(TAG, msg );
                    msg = "***** arrayListHobbies *********** " + arrayListHobbies;
                    Log.d(TAG, msg );
                    Util.showSnackBar(mainActivityConstraintLayout,msg);
                    Util.waitfor(500);

//                msg = "***** user *********** " + globalVariables.getUserCountryLanguage()+ "/" + globalVariables.getUserId();
//                Log.d(TAG, msg );
//                Util.showSnackBar(mainActivityConstraintLayout,msg);
//                Util.waitfor(500);


                }
            });



        Log.i(TAG, "------- MainActivity onCreate START ---------");
    }

    private void InitGlobalVarsAndArrays() {
        InitGlobalVariablesStep1();
        InitGlobalVariablesStep2();
        InitArrays();

    }


    private void InitArrays() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        globalVariables.LoadGendersDataFromFirestore();
        globalVariables.LoadHobbiesDataFromFirestore();


        Log.i(TAG, "------- Init Arrays ---------");
    }

    public void InitGlobalVariables(){
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();

        globalVariables.LoadUserDataFromFirestore();

//        InitGlobalVariablesStep1();
//        InitGlobalVariablesStep2();

    }


    public void InitGlobalVariablesStep1(){
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        Log.d(TAG,"InitGlobalVariablesStep1");


        db      = FirebaseFirestore.getInstance();
        user    = FirebaseAuth.getInstance().getCurrentUser();
        userId  = user.getUid();

        globalVariables.setDb(db);
        globalVariables.setUser(user);
        globalVariables.setUserId(userId);

        Log.i(TAG, "db : " + db);
        Log.i(TAG, "user : " + user);
        Log.i(TAG, "userId : " + userId);
        Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);
        Log.i(TAG, "------- END OF InitGlobalVariables ---------");
        Toast.makeText(MainActivity.this,"CONNECTED USER : " + userId , Toast.LENGTH_SHORT).show();
    }

    public void InitGlobalVariablesStep2(){
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();

        globalVariables.LoadUserDataFromFirestore(); // userNickName, userCountryLanguage

        globalVariables.setUserNickName(userNickName);
        globalVariables.setUserCountryLanguage(userCountryLanguage);
        globalVariables.setUserEmail(userEmail);


        Log.i(TAG, "userId : " + userId);
        Log.i(TAG, "userNickName : " + userNickName);
        Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);
        Log.i(TAG, "userEmail : " + userEmail);
        Log.i(TAG, "------- END OF InitGlobalVariables ---------");
        Toast.makeText(MainActivity.this,"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();
    }



                    //
                    //
                    //    public void SetUserDataFromFirestore() {
                    //        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
                    //        DocumentReference docRefUserConnected;
                    //
                    //        docRefUserConnected = db.document("users/"+ userId); //bSfRUKasZ7PyHnew1jwqG6jksl03
                    ////        docRefUserConnected = db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03"); //
                    //        docRefUserConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    //            @Override
                    //            public void onSuccess(DocumentSnapshot myDocSnapshot) {
                    //
                    //                if (myDocSnapshot.exists()) {
                    //
                    //                    userNickName    = myDocSnapshot.getString("us_nickname");// + " " + userId;
                    //                    userEmail       = myDocSnapshot.getString("us_email");// + " " + userId;
                    //
                    //                    ModelUsers connectedUser = myDocSnapshot.toObject(ModelUsers.class);
                    //                    userCountryLanguage     =   connectedUser.getUs_country_lang().toString().trim();
                    //
                    //
                    ////                    Mise à jour des variables ici car délai de latence dans l'init des variables.
                    //                    globalVariables.setUserNickName(userNickName);
                    //                    globalVariables.setUserCountryLanguage(userCountryLanguage);
                    //                    globalVariables.setUserEmail(userEmail);
                    //
                    //                    Log.i(TAG, "userId : " + userId);
                    //                    Log.i(TAG, "userNickName : " + userNickName);
                    //                    Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);
                    //                    Log.i(TAG, "userEmail : " + userEmail);
                    //
                    //                    Log.i(TAG, "----- Update on getUserDataFromFirestore -----");
                    //
                    //                    Toast.makeText(MainActivity.this,"USER FOUND in collection \"users\"", Toast.LENGTH_SHORT).show();
                    //
                    //                } else {
                    //
                    //                    userNickName = "No USER FOUND";
                    //                    Toast.makeText(MainActivity.this,"No Document found in collection \"users\" for this LOGIN/USER", Toast.LENGTH_SHORT).show();
                    //                }
                    //            }
                    //        });
                    //
                    //        Log.i(TAG, "----- END getUserDataFromFirestore -----");
                    //    } // END getUserDataFromFirestore()

    public void launchLierParrainFilleulActivity(View v){
        Intent intent = new Intent(MainActivity.this, LierParrainFilleulActivity.class);
//        intent.putExtra("IdUser", snapshot.getId());
        startActivity(intent);


    }

    public void launchPendingRequestsActivity(View v){
        Intent intent = new Intent(MainActivity.this, PendingRequestsActivity.class);
//        intent.putExtra("IdUser", snapshot.getId());
        startActivity(intent);


    }

    public void launchUserActivity(View v){
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
//        intent.putExtra("IdUser", snapshot.getId());

//        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
//        globalVariables.LoadGendersDataFromFirestore();

        startActivity(intent);


    }
}