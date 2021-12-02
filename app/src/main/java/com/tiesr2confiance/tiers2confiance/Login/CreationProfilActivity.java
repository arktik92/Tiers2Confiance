package com.tiesr2confiance.tiers2confiance.Login;

import static android.graphics.Color.TRANSPARENT;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreationProfilActivity extends AppCompatActivity {

    /** Variables globales **/



    private static final String TAG = "CreationProfilActivity";

    private EditText etLastName, etFistName, etNickName, etCity, etZipCode;
    private TextView tvDateOfBirth;
    private String hobbies,lastName,firstName,nickName, dateOfBirth, zipCode,city, userId, userEmail, nephewsRequestTo, nephewsRequestfrom, nephews, godfatherRequestTo, godfatherRequestFrom, godfather, image;
    public static long role, genre;
    private RadioGroup radioGroupGenre;
    private RadioButton rbHomme, rbFemme;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Timestamp currentDate, registeredDate, timestamp;

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
        radioGroupGenre =findViewById(R.id.radio_group_genre);


        rbHomme = findViewById(R.id.rb_homme);
        rbFemme = findViewById(R.id.rb_femme);

        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);



        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        db = FirebaseFirestore.getInstance();
        docRef = db.document("users/"+ userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(role == 1) {
            setContentView(R.layout.activity_creation_profil_celibataire);
        } else {
            setContentView(R.layout.activity_creation_profil_parrain);
        }


        init();





        /** Méthode OnClickListener du Date Picker **/
        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal;
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreationProfilActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (DatePickerDialog.OnDateSetListener) mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                 dateOfBirth = dayOfMonth + "-" + month + "-" + year;
                tvDateOfBirth.setText(dateOfBirth);
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = format.parse(dateOfBirth);
                    timestamp = new Timestamp(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void creationUser(View view) {
        userEmail = user.getEmail().trim();
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
        hobbies = "";
        currentDate = Timestamp.now();
        if(registeredDate == null) {
            registeredDate = Timestamp.now();
        } else {
            registeredDate = registeredDate;
        }

        Map<String, Object> userList = new HashMap<>();
        userList.put("us_auth_uid", userId);
        userList.put("us_email", userEmail);
        userList.put("us_birth_date", timestamp);
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
        userList.put("us_gender", genre);
        userList.put("us_hobbies", hobbies);



        docRef.set(userList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreationProfilActivity.this, "Profil crée", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Profil crée");
                        startActivity(new Intent(CreationProfilActivity.this, MainActivity.class));
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


    public void radioButtonGender(View view) {

        boolean checked = ((RadioButton)view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_homme:
                if(checked)
                    genre = 1;
                break;
            case R.id.rb_femme:
                if(checked)
                    genre = 2;
                break;
        }

    }


}