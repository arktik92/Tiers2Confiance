package com.tiesr2confiance.tiers2confiance.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.Common.NoInternetActivity;
import com.tiesr2confiance.tiers2confiance.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String TAGAPP = "LOGAPP";

    /** Variables globales **/
    private TextInputEditText etEmail, etPassword;
    private String email,password;

    /** Méthode init **/
    public void init() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


        etPassword.setOnEditorActionListener(editorActionListener);
    }
    /** Gestion du bouton login **/
    public void btnLoginClick(android.view.View v) {
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();


        Log.i(TAGAPP, "1 LoginActivity : btnLoginClick addOnCompleteListener: user : " + globalVariables.getUser());
        Log.i(TAGAPP, "1 LoginActivity : btnLoginClick addOnCompleteListener: userId : " + globalVariables.getUserId());
        Log.i(TAGAPP, "1 LoginActivity : btnLoginClick addOnCompleteListener: UserEmail : " + globalVariables.getUserEmail());
        Log.i(TAGAPP, "1 LoginActivity : btnLoginClick addOnCompleteListener: userCountryLanguage : " + globalVariables.getUserCountryLanguage());
        Log.i(TAGAPP, "1 LoginActivity : btnLoginClick addOnCompleteListener: UserRole : " + globalVariables.getUserRole());


        // Vérification du remplissage des champs email et password
        if (email.equals("")) {
            etEmail.setError("Enter email");
        } else if (password.equals("")) {
            etPassword.setError("Enter password");
        } else {

            // On récupère la role de l'utilisateur dans SharedPreferences
            GetRoleFromFilePrefs();

            //vérification de la connection internet
            if (Util.connectionAvailable(this))
                // Si la connexion fonctionne
            {

                /** 5 Connexion à authenticator en utilisant les tools Firebase cf cours**/

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(@NonNull AuthResult authResult) {
                                Log.d(TAGAPP, ">>>>> firebaseAuth.signInWithEmailAndPassword addOnSuccessListener");
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Log.d(TAGAPP, ">>>>> firebaseAuth.signInWithEmailAndPassword addOnCompleteListener");
//                                    LoadUserData();
//
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {

                                    Toast.makeText(LoginActivity.this,
                                            "Login failed" + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: error in FirebaseAuth.signInWithEmailAndPassword");
                            }
                        });

                Log.d(TAGAPP, ">>>>> <<<<<<<<<<<");

            } else {
                startActivity(new Intent(LoginActivity.this, NoInternetActivity.class));
            }
        }
    }

    /** Gestion du click sur signUp **/
    public void tvSignupClick(android.view.View v) {

        startActivity(new Intent(this, SignInActivity.class));
    }

    //TODO Faire un reset password

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {

            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                    btnLoginClick(view);
            }
            return false;
        }
    };
    public void btnResetPasswordClick(View v) {
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }



    private void GetRoleFromFilePrefs() {
        // On récupère la role de l'utilisateur dans SharedPreferences
        GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        //        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapp.prefs", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences(R.class.getPackage().getName()
                + ".prefs", Context.MODE_PRIVATE);

        if(globalVariables.getUserRole() != 0L)
        {
            Long    userRole    = globalVariables.getUserRole();
            Boolean isUserSingle;
            isUserSingle = userRole != 2L;

            Log.d(TAGAPP, "SetRoleInFilePrefs userRole" + userRole);

//            sharedPreferences = context.getSharedPreferences(filePrefs, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // On place le boolean  isusersingle
            editor.putBoolean("isusersingle", isUserSingle); // est-ce un célib ?
            editor.commit();

        }else {
            // La vérifcation du boolean
            if (!sharedPreferences.getBoolean("isusersingle", true)) {
                globalVariables.setUserRole(1L);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // On place le boolean  isusersingle
                editor.putBoolean("isusersingle", true); //
                editor.commit();
            } else {
                globalVariables.setUserRole(2L);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // On place le boolean  isusersingle
                editor.putBoolean("isusersingle", false); //
                editor.commit();
            }
            ;
        }


    }








//
//
//    private void LoadUserData() {
//        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
////                                    Log.i(TAG, "LoginActivity : btnLoginClick addOnCompleteListener: UserEmail : " + globalVariables.getUserEmail());
////                                    Log.i(TAG, "LoginActivity : btnLoginClick addOnCompleteListener: userCountryLanguage : " + globalVariables.getUserCountryLanguage());
////                                    Log.i(TAG, "LoginActivity : btnLoginClick addOnCompleteListener: UserRole : " + globalVariables.getUserRole());
//
//        Log.i(TAG, "LoginActivity : onDestroy ");
//        FirebaseFirestore db;
//        FirebaseUser user;
//        String userId = "";
//
//        try {
//
//
//
//            db      = FirebaseFirestore.getInstance();
//            user    = FirebaseAuth.getInstance().getCurrentUser();
//
//            if (user != null){
//                userId  = user.getUid();
//            }
//            globalVariables.setUser(user);
//            globalVariables.setUserId(userId);
//            globalVariables.setUserEmail(email);
//            globalVariables.setUserCountryLanguage("XX"); // FR valeur par défaut avant de charger des données depuis la collection user / doc >> Userid
//            globalVariables.setUserRole(0); //Célibataire valeur par défaut avant de charger des données depuis la collection user / doc >> Userid
//
//            Log.i(TAGAPP, "LoginActivity : onDestroy : user : " + globalVariables.getUser());
//            Log.i(TAGAPP, "LoginActivity : onDestroy : userId : " + globalVariables.getUserId());
//
//            Log.i(TAGAPP, "LoginActivity : onDestroy : UserEmail : " + globalVariables.getUserEmail());
//            Log.i(TAGAPP, "LoginActivity : onDestroy : userCountryLanguage : " + globalVariables.getUserCountryLanguage());
//            Log.i(TAGAPP, "LoginActivity : onDestroy : UserRole : " + globalVariables.getUserRole());
//
//
//            globalVariables.LoadUserDataFromFirestore();
//            Log.i(TAGAPP, "******** LoginActivity LoadUserDataFromFirestore *************");
//
//            Log.i(TAGAPP, "LoginActivity : onDestroy : UserEmail : " + globalVariables.getUserEmail());
//            Log.i(TAGAPP, "LoginActivity : onDestroy : userCountryLanguage : " + globalVariables.getUserCountryLanguage());
//            Log.i(TAGAPP, "LoginActivity : onDestroy : UserRole : " + globalVariables.getUserRole());
//
//        }
//        catch (Exception e) {
//            Log.e(TAG, "----- LoginActivity : onDestroy error on userId: "+ userId +" -----" );
//            Log.e(TAG, "----- LoginActivity : onDestroy onComplete error on userId: "+ userId +" -----userEmail "  + email);
//        };
//
//    }








}