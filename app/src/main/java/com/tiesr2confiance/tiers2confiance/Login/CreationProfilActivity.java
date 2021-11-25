package com.tiesr2confiance.tiers2confiance.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreationProfilActivity extends AppCompatActivity {

    /** Variables globales **/
    private static final String TAG = "CreationProfilActivity";
    private EditText etLastName, etFistName, etNickName, etDateOfBirth, etZipCode, etCity;
    private String lastName,firstName,nickName, dateOfBirth,zipCode,city, userId, userEmail,timeStamp,currentTime;
    private RadioGroup rgRadioGroup;
    private RadioButton rbHomme, rbFemme;

    /** Variable Firebase Auth **/
    FirebaseUser user;

    /** Variables Firestore **/
        private FirebaseFirestore db;
        private DocumentReference docRef;

    /** Initialisation des composants **/
    public void init() {
        etLastName = findViewById(R.id.et_creation_nom);
        etFistName = findViewById(R.id.et_creation_prenom);
        etNickName = findViewById(R.id.et_creation_pseudo);
        etDateOfBirth = findViewById(R.id.et_creation_date_de_naissance);
        etZipCode = findViewById(R.id.et_creation_code_postal);
        etCity = findViewById(R.id.et_creation_ville);
        rgRadioGroup =findViewById(R.id.rg_radio_group);
        rbHomme = findViewById(R.id.rb_homme);
        rbFemme = findViewById(R.id.rb_femme);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        db = FirebaseFirestore.getInstance();
        docRef = db.document("users/"+ userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_profil);

    init();

    }

    public void creationUser(View view) {
        userEmail = user.getEmail();
        dateOfBirth = etDateOfBirth.getText().toString().trim();
        city = etCity.getText().toString().trim();
        firstName = etFistName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();
        nickName = etNickName.getText().toString().trim();
        zipCode = etZipCode.getText().toString();
        currentTime = new SimpleDateFormat("dd/MM/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        if(timeStamp == null) {
            timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        } else {
            timeStamp = timeStamp;
        }

        Map<String, Object> userList = new HashMap<>();
        userList.put("us_auth_id", userId);
        userList.put("us_email", userEmail);
        userList.put("us_birth_date", dateOfBirth);
        userList.put("us_city", city);
        userList.put("us_first_name", firstName);
        userList.put("us_last_name", lastName);
        userList.put("us_postal_code", zipCode);
        userList.put("us_nickname", nickName);
        userList.put("us_registered_date", timeStamp);
        userList.put("us_last_connection_date", currentTime);

        docRef.set(userList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreationProfilActivity.this, "Profil crée", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Profil crée");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreationProfilActivity.this, "Erreur dans la creation du profil", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: ", e );
                    }
                });

    }
}