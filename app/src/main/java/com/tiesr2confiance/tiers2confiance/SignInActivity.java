package com.tiesr2confiance.tiers2confiance;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    /** Variables Globales **/
    private static final String TAG = "Main Activity";
    private View baseView;

    /** Initialisation des composants **/
    public void init() {
        baseView = findViewById(R.id.mainLayoutSignIn);
    }

    /** Gestion du clic sur le bouton SIGN UP **/
    public void startSignUpActivity(View view) {
        Log.i(TAG, "startSignUpActivity: ");
        signUpActivity();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            //TODO intent a replacer

            // Intent vers l'activitée principale
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }
    }


    /** La variable du callback de retour du signLauncher **/
    private final ActivityResultLauncher<Intent> signLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignResult(result);
                }
            }
    );

    /** Méthode de gestion du retour callback **/

    private void onSignResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse reponse = result.getIdpResponse();
        if(result.getResultCode() == RESULT_OK) {
            ///Connecté
            Toast.makeText(SignInActivity.this, "Connecté", Toast.LENGTH_SHORT).show();
            /** #1 Gestion de l'affichage de l'activité de l'app **/
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        } else {
            ///pas connecté
            if(reponse == null) {
                Toast.makeText(SignInActivity.this, "Erreur de connection", Toast.LENGTH_SHORT).show();
            } else if(reponse.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                Toast.makeText(SignInActivity.this, "Pas de connection internet", Toast.LENGTH_SHORT).show();
            } else if (reponse.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                Toast.makeText(SignInActivity.this, "Erreur inconnue", Toast.LENGTH_SHORT).show();
            }


        }
    }
    private void signUpActivity() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
               // new AuthUI.IdpConfig.FacebookBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()

                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("https://google.fr", "https://yahoo.fr")
                .setIsSmartLockEnabled(true)
                .build();

        signLauncher.launch(signInIntent);
    }
    //reprendre config fb apres manifest
}