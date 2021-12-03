package com.tiesr2confiance.tiers2confiance.Login;

import static android.graphics.Color.TRANSPARENT;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
    private static final String TAGAPP = "LOGAPP";

    private static final String filePrefs = R.class.getPackage().getName() + ".prefs";

    // Variable Widgets
    private EditText etLastName, etFistName, etNickName, etCity, etZipCode;
    private TextView tvDateOfBirth;
    private RadioGroup radioGroupGenre;
    private RadioButton rbHomme, rbFemme;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //Variable du code
    private Timestamp currentDate, registeredDate, timestamp;
    public  long role;
    private long  genre,  balance, sexualOrientation, maritalStatus, hasKids, height, shape, ethnicGroup,hairColor,
            hairLength, eyeColor, smoker;
    private String hobbies,lastName,firstName,nickName, dateOfBirth, zipCode,city, userId,
            userEmail, nephewsRequestTo, nephewsRequestfrom, nephews, godfatherRequestTo,
            godfatherRequestFrom, godfather, image, avatar, country,presentation,profession, personality, sports, photos;


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


        // Init des composants Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        db = FirebaseFirestore.getInstance();
        docRef = db.document("users/"+ userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        role    =   globalVariables.getUserRole();

        Log.i(TAGAPP, "CreationProfilActivity onCreate role : " + role);

        // Définition de la contentView en fonction du rôle

        switch((int) role) {
            case 1:
                setContentView(R.layout.activity_creation_profil_celibataire);
                break;
            case 2:
                setContentView(R.layout.activity_creation_profil_parrain);
                break;
            default:
                setContentView(R.layout.activity_creation_profil_parrain);
                break;
        }

//
//        if((int) role == 1) {
//            setContentView(R.layout.activity_creation_profil_celibataire);
//        } else {
//            setContentView(R.layout.activity_creation_profil_parrain);
//        }

        // Rappel de la méthode init
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
    /** Méthode de création de l'utilisateur  **/
    public void creationUser(View view) {

        // Implémentation des variables qui vont etre envoyé sur la Database
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
        balance = 0;
        country = "FR";
        presentation = "";
        sexualOrientation = 1;
        maritalStatus = 1;
        hasKids = 0;
        profession = "";
        height = 0;
        shape = 1;
        ethnicGroup = 1;
        hairColor = 1;
        hairLength = 1;
        eyeColor = 1;
        smoker = 1;
        personality = "";
        sports = "";
        avatar = "";
        photos = "";


        // Méthode de la date de dernière connection
        if(registeredDate == null) {
            registeredDate = Timestamp.now();
        } else {
            registeredDate = registeredDate;
        }

        // Création d'un objet pour envoyer sur la Database
        Map<String, Object> userList = new HashMap<>();
        userList.put("us_auth_uid", userId);
        userList.put("us_nickname", nickName);
        userList.put("us_email", userEmail);
        userList.put("us_first_name", firstName);
        userList.put("us_last_name", lastName);
        userList.put("us_role", role);
        userList.put("us_balance", balance);
        userList.put("us_nephews", nephews);
        userList.put("us_nephews_request_from",nephewsRequestfrom);
        userList.put("us_nephews_request_to",nephewsRequestTo);
        userList.put("us_godfather", godfather);
        userList.put("us_godfather_request_from", godfatherRequestFrom);
        userList.put("us_godfather_request_to",godfatherRequestTo);
        userList.put("us_photos", photos);
        userList.put("us_birth_date", timestamp);
        userList.put("us_country_lang", country);
        userList.put("us_postal_code", Long.parseLong(zipCode));
        userList.put("us_city", city);
        userList.put("us_presentation", presentation);
        userList.put("us_gender", genre);
        userList.put("us_sexual_orientation", sexualOrientation);
        userList.put("us_marital_status", maritalStatus);
        userList.put("us_has_kids", hasKids);
        userList.put("us_profession", profession);
        userList.put("us_height", height);
        userList.put("us_shape", shape);
        userList.put("us_ethnic_group", ethnicGroup);
        userList.put("us_hair_color", hairColor);
        userList.put("us_hair_length", hairLength);
        userList.put("us_eye_color", eyeColor);
        userList.put("us_smoker", smoker);
        userList.put("us_personality", personality);
        userList.put("us_sports", sports);
        userList.put("us_hobbies", hobbies);
        userList.put("us_avatar", avatar);
        userList.put("us_registered_date", registeredDate);
        userList.put("us_last_connexion_date", currentDate);
        userList.put("us_image", image);















        // Envoi de l'objet sur la Database
        docRef.set(userList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        SetRoleInFilePrefs();

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




    private void SetRoleInFilePrefs() {
        // Création ou mise à jour des préférences en local
        GlobalClass globalVariables = (GlobalClass) getApplicationContext();

        Context context     = getApplicationContext();
        Long    userRole    = globalVariables.getUserRole();
        Boolean isUserSingle;
        isUserSingle = userRole != 2L;

        Log.d(TAGAPP, "SetRoleInFilePrefs userRole" + userRole);

        SharedPreferences sharedPreferences = context.getSharedPreferences(filePrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // On place le boolean  isusersingle
        editor.putBoolean("isusersingle", isUserSingle); // est-ce un célib ?
        editor.commit();

    }





}
