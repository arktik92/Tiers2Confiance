package com.tiesr2confiance.tiers2confiance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulActivity;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.User.UserActivity;

public class MainActivity extends AppCompatActivity {


    /**  Var globales **/
    private static final String TAG = "LOGAPP_MainActivity";

    /** Variable Firebase Auth **/
    private FirebaseFirestore db;
    private static FirebaseUser user;
    private static String userId;
    private static String userNickName;
    private static String userCountryLanguage = "";
    private static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGlobalVariables();

        //initArrays();

        Log.i(TAG, "------- END OF onCreate ---------");
    }

    private void initArrays() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        globalVariables.loadGendersDataFromFirestore();



        Log.i(TAG, "------- END OF initGlobalVariables ---------");
    }

    public void initGlobalVariables(){
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();


        db      = FirebaseFirestore.getInstance();
        user    = FirebaseAuth.getInstance().getCurrentUser();
        userId  = user.getUid();

        globalVariables.setDb(db);
        globalVariables.setUser(user);
        globalVariables.setUserId(userId);

        getUserDataFromFirestore(); // userNickName, userCountryLanguage


        globalVariables.setUserNickName(userNickName);
        globalVariables.setUserCountryLanguage(userCountryLanguage);
        globalVariables.setUserEmail(userEmail);

        Log.i(TAG, "userId : " + userId);
        Log.i(TAG, "userNickName : " + userNickName);
        Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);
        Log.i(TAG, "userEmail : " + userEmail);
        Log.i(TAG, "------- END OF initGlobalVariables ---------");
    }


    public void getUserDataFromFirestore() {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        DocumentReference docRefUserConnected;

//        docRefUserConnected = db.document("users/"+ userId); //bSfRUKasZ7PyHnew1jwqG6jksl03
        docRefUserConnected = db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03"); //
        docRefUserConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot myDocSnapshot) {

                if (myDocSnapshot.exists()) {

                    userNickName    = myDocSnapshot.getString("us_nickname");// + " " + userId;
                    userEmail       = myDocSnapshot.getString("us_email");// + " " + userId;

                    ModelUsers connectedUser = myDocSnapshot.toObject(ModelUsers.class);
                    userCountryLanguage     =   connectedUser.getUs_country_lang().toString().trim();


//                    Mise à jour des variables ici car délai de latence dans l'init des variables.
                    globalVariables.setUserNickName(userNickName);
                    globalVariables.setUserCountryLanguage(userCountryLanguage);
                    globalVariables.setUserEmail(userEmail);

                    Log.i(TAG, "userId : " + userId);
                    Log.i(TAG, "userNickName : " + userNickName);
                    Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);
                    Log.i(TAG, "userEmail : " + userEmail);

                    Log.i(TAG, "----- Update on getUserDataFromFirestore -----");

                    Toast.makeText(MainActivity.this,"USER FOUND in collection \"users\"", Toast.LENGTH_SHORT).show();

                } else {

                    userNickName = "No USER FOUND";
                    Toast.makeText(MainActivity.this,"No Document found in collection \"users\" for this LOGIN/USER", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.i(TAG, "----- END getUserDataFromFirestore -----");
    } // END getUserDataFromFirestore()

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

        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        globalVariables.loadGendersDataFromFirestore();

        startActivity(intent);


    }
}