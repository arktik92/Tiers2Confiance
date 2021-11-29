package com.tiesr2confiance.tiers2confiance.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;
import com.tiesr2confiance.tiers2confiance.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreationProfilActivity extends AppCompatActivity {

    /** Variables globales **/
    private static final String TAG = "CreationProfilActivity";
    private EditText etLastName, etFistName, etNickName, etZipCode, etCity;
    private TextView tvDateOfBirth;
    private String lastName,firstName,nickName, dateOfBirth,zipCode,city, userId, userEmail, nephewsRequestTo, nephewsRequestfrom, nephews, godfatherRequestTo, godfatherRequestFrom, godfather, image;
    private long role;
    private RadioGroup rgRadioGroup;
    private RadioButton rbHomme, rbFemme;
    private FrameLayout datePicker;
    private Timestamp currentDate, registeredDate;



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
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        etZipCode = findViewById(R.id.et_creation_code_postal);
        etCity = findViewById(R.id.et_creation_ville);
        rgRadioGroup =findViewById(R.id.rg_radio_group);
        rbHomme = findViewById(R.id.rb_homme);
        rbFemme = findViewById(R.id.rb_femme);
        datePicker = findViewById(R.id.picker);

        role = 1;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void creationUser(View view) {



        userEmail = user.getEmail();
        //dateOfBirth = etDateOfBirth.getText().toString().trim();
        city = etCity.getText().toString().trim();
        firstName = etFistName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();
        nickName = etNickName.getText().toString().trim();
        zipCode = etZipCode.getText().toString();
        nephewsRequestTo = "";
        nephewsRequestfrom = "";
        nephews = "";
        godfatherRequestTo = "";
        godfatherRequestFrom= "";
        image = "";
        godfather = "";
        currentDate = Timestamp.now();
        if(registeredDate == null) {
            registeredDate = Timestamp.now();
        } else {
            registeredDate = registeredDate;
        }

        Map<String, Object> userList = new HashMap<>();
        userList.put("us_auth_uid", userId);
        userList.put("us_email", userEmail);
        userList.put("us_birth_date", dateOfBirth);
        userList.put("us_city", city);
        userList.put("us_first_name", firstName);
        userList.put("us_last_name", lastName);
        userList.put("us_postal_code", Long.parseLong(zipCode));
        userList.put("us_nickname", nickName);
        userList.put("us_registered_date", registeredDate);
        userList.put("us_last_connexion_date", currentDate);
        userList.put("us_nephews_request_to",nephewsRequestTo);
        userList.put("us_nephews_request_from",nephewsRequestfrom);
        userList.put("us_godfather_request_to",godfatherRequestTo);
        userList.put("us_godfather_request_from", godfatherRequestFrom);
        userList.put("us_img",image);
        userList.put("us_nephews", nephews);
        userList.put("us_godfather", godfather);
        userList.put("us_role", role);
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
    /** Methode onClick datePicker **/
    public void onClickEtDateOfBirth(View view) {
        datePicker.setVisibility(View.VISIBLE);
    }
}