package com.tiesr2confiance.tiers2confiance.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.Common.NoInternetActivity;
import com.tiesr2confiance.tiers2confiance.R;

public class LoginActivity extends AppCompatActivity {

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
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        // Vérification du remplissage des champs email et password
        if (email.equals("")) {
            etEmail.setError("Enter email");
        } else if (password.equals("")) {
            etPassword.setError("Enter password");
        } else {
            //vérification de la connection internet
            if (Util.connectionAvailable(this))
                // Si la connexion fonctionne
            {

                /** 5 Connexion à authenticator en utilisant les tools Firebase cf cours**/
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                   //TODO intent a replacer
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {

                                    Toast.makeText(LoginActivity.this,
                                            "Login failed" + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

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
}