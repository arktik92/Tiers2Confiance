package com.tiesr2confiance.tiers2confiance.Login;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Window;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.core.View;
import com.tiesr2confiance.tiers2confiance.Common.NoInternetActivity;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.R;


import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    /** Variables Globales **/
    private static final String TAG = "Main Activity";
    private TextInputEditText etPseudo, etEmail, etPassword, etConfirmPassword;
    private String pseudo, email, password, confirmPassword;

    /** Variable Firebase **/
    FirebaseUser user;

    /** Initialisation des composants **/
    public void init() {
        etPseudo = findViewById(R.id.etPseudo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    public void btnSignupClick(android.view.View view) {
        pseudo = etPseudo.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmPassword = etConfirmPassword.getText().toString().trim();

        // Les vérifications
        // Si les cases sont vides
        if (pseudo.equals("")) {
            etPseudo.setError("Enter pseudo");
        } else if (email.equals("")) {
            etEmail.setError("Enter Email");
        } else if (password.equals("")) {
            etPassword.setError("Enter password");
        } else if (confirmPassword.equals("")) {
            etConfirmPassword.setError("Confirm password");
        }
        // Les patterns pour vérifier si il s'agit bien d'un email
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etPseudo.setError("enter correct email");
        }
        // Vérification password identique
        else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("password mismatch");
        }

        else {
            //vérification de la connection internet
            if (Util.connectionAvailable(this))
            {
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, password)

                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    user = FirebaseAuth.getInstance().getCurrentUser();
                                    startActivity(new Intent(SignInActivity.this,CreationProfilActivity.class));

                                } else {
                                    Toast.makeText(SignInActivity.this,
                                            "erreur login",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                startActivity(new Intent(SignInActivity.this, NoInternetActivity.class));
            }
        }
    }


}