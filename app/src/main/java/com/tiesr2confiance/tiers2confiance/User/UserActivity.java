package com.tiesr2confiance.tiers2confiance.User;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {


    private static final String TAG = "LOGAPP_UserActivity";

    /** Variable Firebase Auth **/
    private FirebaseUser user;
    private String userId, userAuthUID;
    private String collection;
    private String userNickName;
    private String userCountryLanguage = "";




    /** Variables Firestore **/
    private FirebaseFirestore db;
    private DocumentReference docRefUserConnected;
    public static ArrayList<ModelGenders> myArrayListGenders = new ArrayList<>();
    public static ArrayList<ModelHobbies> myArrayListHobbies = new ArrayList<>();

    ConstraintLayout userActivityConstraintLayout;
    LinearLayout     gendersLinearLayout;
    LinearLayout     hobbiesLinearLayout;
    LinearLayout     hobbiesLinearlayoutChkbox;

    RecyclerView    rvHobbies;

    private RadioGroup  radioGroupGenders;
    private RadioGroup  radioGroupHobbies;

    private TextView    tvUserNickName;
    private TextView    tvUserId;
    private TextView    tvUserAuthUID;

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initVariables();


        initComponents();
//        initDb();


//        getUserDataFromFirestore();

        Log.i(TAG, "onCreate userCountryLanguage : " + userCountryLanguage);
       // Util.waitfor(2000);

        GetData();
        //initGendersLayout(); // S'execute avant même que les données reviennent du serveur du coup impossible d'afficher les data
        //initHobbiesLayout(); // idem
    }



    public void initVariables(){
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        db                  = globalVariable.getDb();
        user                = globalVariable.getUser();
        userId              = globalVariable.getUserId();
        userNickName        = globalVariable.getUserNickName();
        userCountryLanguage = globalVariable.getUserCountryLanguage();

        Log.i(TAG, "userId : " + userId);
        Log.i(TAG, "userNickName : " + userNickName);
        Log.i(TAG, "userCountryLanguage : " + userCountryLanguage);

        Log.i(TAG, "-----------------------------");
        Log.i(TAG,"UserId : " + userId);

        myArrayListGenders = globalVariable.getArrayListGenders();
        Log.i(TAG, "------- myArrayListGenders.size() ---------" + myArrayListGenders.size());
//        Util.showSnackBar(userActivityConstraintLayout,"");
//        Util.showSnackBar(userActivityConstraintLayout,"Arrêt DEBUGG");
        Log.i(TAG, "------- END OF initVariables ---------");
        userCountryLanguage = globalVariable.getUserCountryLanguage();
        Log.i(TAG, "-----------------------------");
        Log.i(TAG,"UserId : " + userId);

    }


    /** Initialisation des composants **/
    public void initComponents() {

        //userId = user.getUid();

        Log.i(TAG, "initComponents : BEGIN");

        //userActivityLayout =  findViewById(R.id.user_Activity_Layout);
        userActivityConstraintLayout =  findViewById(R.id.user_Activity_Layout);

        /************** init des TextViews ***-******************/

        tvUserId                    = findViewById(R.id.tv_user_id);
        tvUserNickName              = findViewById(R.id.tv_user_nick_name);
//        tvUserAuthUID               = findViewById(R.id.tv_user_AuthUID);

        tvUserId.setText(userId);
        tvUserNickName.setText(userNickName);
//        tvUserAuthUID.setText(userId);

        /************** init des Views/Layouts CheckBox et Radiobuttons ***-******************/
        gendersLinearLayout         = findViewById(R.id.linear_layout_genders);
        hobbiesLinearLayout         = findViewById(R.id.linear_layout_hobbies);
        hobbiesLinearlayoutChkbox   = findViewById(R.id.linear_layout_chk_hobbies);
//        rvHobbies           = findViewById(R.id.rv_hobbies);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT
                        );
        params.setMargins(80, 0, 0, 40);


        radioGroupGenders = new RadioGroup(this);
        radioGroupGenders.setLayoutParams(params);

//        params.setMargins(30, 0, 0, 0);
        radioGroupHobbies = new RadioGroup(this);
        radioGroupHobbies.setLayoutParams(params);




//        rgGenders.addView(gendersLinearLayout);

        Log.i(TAG, "initComponents : END");

    }

    private void GetData() {
        getGendersDataFromFirestore(); //S'execute avant même que les données reviennent du serveur, notament le userCountryLanguage
        getHobbiesDataFromFirestore(); // idem

    }



    /** Initialisation des composants **/
//    public void initDb() {
//
//        Log.i(TAG, "initDb: BEGIN");
//
//        db      = FirebaseFirestore.getInstance();
//        user    = FirebaseAuth.getInstance().getCurrentUser();
//        userId  = user.getUid();
//
//        Log.i(TAG, "initDb: END");
//
//    }

    private void initArrays() {//onClickButton
        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        globalVariables.loadGendersDataFromFirestore();



        Log.i(TAG, "------- END OF initGlobalVariables ---------");
    }
    public void initRadioButtonsLayouts(View v) { //onClickButton
        Toast.makeText(getApplicationContext(), "userCountryLanguage " + userCountryLanguage, Toast.LENGTH_LONG).show();
        initGendersLayout(v);
        initHobbiesLayout(v);
        initArrays();

    }


    public void initHobbiesLayout(View v){ //onClickButton
    Log.i(TAG, "*** initHobbiesLayout ***" );

        hobbiesLinearLayout.addView(radioGroupHobbies);
//            rvHobbies.addView(radioGroupHobbies);

        radioGroupHobbies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String text = "Hobbie - you selected ";
                text += checkedId;
                Util.showSnackBar(userActivityConstraintLayout,text);

                Log.i(TAG, text);
            }
        });

    }

    public void getHobbiesDataFromFirestore() {

        Log.i(TAG, "getHobbiesDataFromFirestore: BEGIN");
        hobbiesLinearLayout = findViewById(R.id.linear_layout_hobbies);
//        rvHobbies = findViewById(R.id.rv_hobbies);
        myArrayListHobbies.clear();


        Query queryHobbies = db.collection("hobbies")
                .whereEqualTo("ho_country", userCountryLanguage);
                //.orderBy("ho_label");
                //.orderBy("ge_label", Query.Direction.DESCENDING);

        queryHobbies.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.i(TAG, "onSuccess: LIST EMPTY");

                } else {
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        if (documentSnapshot.exists()) {
                            String hobbiesDocId = documentSnapshot.getId();

                            Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
//                            DocumentReference documentReference1 = FirebaseFirestore.getInstance().document("some path");
                            DocumentReference docRefHobbie = db.document("hobbies/"+ hobbiesDocId);
                            docRefHobbie.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Log.i(TAG, "onSuccess ******** Hobbies :");

                                    ModelHobbies hobbie= documentSnapshot.toObject(ModelHobbies.class);
                                    String hoLabel = hobbie.getHo_label();
                                    Log.i(TAG, "onSuccess ******** hobbie : " + hobbie.getHo_id() + " " + hobbie.getHo_country() + " " + hobbie.getHo_label());
                                    myArrayListHobbies.add(hobbie);
                                    Log.i(TAG, "onSuccess ******** myArrayListHobbies : " + myArrayListGenders);
                                    Log.i(TAG, "********* myArrayListHobbies.size() *********** " + myArrayListHobbies.size());

                                    /*************** Gestion avec des Boutons Radio *******************/
                                    RadioButton radioButton = new RadioButton(getApplicationContext());
                                    radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                    radioButton.setText(hobbie.getHo_id() + " - " + hobbie.getHo_label());
                                    radioButton.setText(hobbie.ho_label);
                                    radioButton.setId(hobbie.getHo_id());
                                    radioGroupHobbies.addView(radioButton);

                                    /*************** Gestion avec des CheckBox *******************/
                                    CheckBox checkBox   = new CheckBox(getApplicationContext());
                                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                    checkBox.setText(hobbie.getHo_id() + " - " + hobbie.getHo_label());
                                    checkBox.setText(hobbie.ho_label);
                                    checkBox.setId(hobbie.getHo_id());
                                    hobbiesLinearlayoutChkbox.addView(checkBox);
//                                    if (hobbiesLinearlayoutChkbox != null) {
//                                        hobbiesLinearlayoutChkbox.addView(checkBox);
//                                    }

                                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Checkbox #" + checkBox.getId();
//                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                            Log.i(TAG, msg);
                                            Util.showSnackBar(userActivityConstraintLayout,msg);
                                        }
                                    });





                                }


                            });
                        }
                    }



                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting hobbies from FireStore!!!", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onFailure : Error getting hobbies from FireStore!!!");

                    }
                });





        Log.i(TAG, "getHobbiesDataFromFirestore: END");
    }


    public void initGendersLayout(View v){//onClickButton
        Log.i(TAG, "*** initGendersLayout ***" );
//        rgGenders       = findViewById(R.id.rg_Genders);
//        Util.waitfor(2000);
        gendersLinearLayout = findViewById(R.id.linear_layout_genders);
        gendersLinearLayout.addView(radioGroupGenders);

        radioGroupGenders.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String text = "Genders - you selected ";
                text += checkedId;
                //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                Util.showSnackBar(userActivityConstraintLayout,text);

                Log.i(TAG, text);
            }
        });

//        for(int i = 0; i <= myArrayListGenders.size(); i++){
//            Log.i(TAG, "onSuccess ******** gender ********  : " );
//            // tvHobbiesname.setText(hobbies_list[i]);
//        }

//        for (int num : myArrayListGenders) {
//            System.out.println(num);
//        }
//
//        Iterator iter = myArrayListGenders.iterator();
//        while (iter.hasNext()) {
//            System.out.println(iter.next());
//            Log.i(TAG, ">>>>> Iterator  " + iter.next());
//
//
//
//        }
//
//        Log.i(TAG, "Apres for iterator  " );

//        // Creates RadioButtons Dynamically
//        RadioButton radioButton1 = new RadioButton(this);
//        radioButton1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        radioButton1.setText(R.string.sex_male_text);
//        radioButton1.setId(11);
//
//        RadioButton radioButton2 = new RadioButton(this);
//        radioButton2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        radioButton2.setText(R.string.sex_female_text);
//        radioButton2.setId(12);

//        RadioGroup radioGroupGenders = new RadioGroup(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins(40, 0, 0, 0);
//        radioGroupGenders.setLayoutParams(params);

//        if (gendersLinearLayout != null) {
//            radioGroupGenders.addView(radioButton1);
//            radioGroupGenders.addView(radioButton2);
//            gendersLinearLayout.addView(radioGroupGenders);
//
//            radioGroupGenders.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    String text = "you selected";
//                    text += " " + getString((checkedId == 1) ? R.string.sex_male_text : R.string.sex_female_text);
//                    //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//                    Util.showSnackBar(userActivityConstraintLayout,text);
//
//                    Log.i(TAG, text);
//                }
//            });
//        }
    }

    public void getGendersDataFromFirestore() {

        Log.i(TAG, "getGendersDataFromFirestore: BEGIN");

        myArrayListGenders.clear();

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        userId = user.getUid();
//        db = FirebaseFirestore.getInstance();
        Query queryGenders = db.collection("genders")
                .whereEqualTo("ge_country", userCountryLanguage);
                //.whereNotIn("us_auth_uid", critere) ici ça plante ??
                //.orderBy("ge_label");
                //.orderBy("ge_label", Query.Direction.DESCENDING);
                //.orderBy("ge_label", Query.Direction.ASCENDING);

        queryGenders.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.i(TAG, "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        if (documentSnapshot.exists()) {
                            String genderDocId = documentSnapshot.getId();

                            Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
//                            DocumentReference documentReference1 = FirebaseFirestore.getInstance().document("some path");
                            DocumentReference docRefGender = db.document("genders/"+ genderDocId);
                            docRefGender.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    ModelGenders gender= documentSnapshot.toObject(ModelGenders.class);
                                    Log.i(TAG, "onSuccess ******** gender : " + gender.getGe_id() + " " + gender.getGe_country() + " " + gender.getGe_label());
                                    myArrayListGenders.add(gender);
                                    Log.i(TAG, "onSuccess ******** mArrayListGenders : " + myArrayListGenders);
                                    Log.i(TAG, "********* myArrayListGenders.size() *********** " + myArrayListGenders.size());
                                    RadioButton radioButton = new RadioButton(getApplicationContext());
                                    radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                                    radioButton.setText(gender.getGe_id() + " - " + gender.getGe_label());
                                    radioButton.setText(gender.getGe_label());
                                    radioButton.setId(gender.getGe_id());
                                    radioGroupGenders.addView(radioButton);
                                    radioGroupGenders.setOrientation(LinearLayout.HORIZONTAL);

                                }


                            });
                        }
                    }



                }
            }
        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error getting genders from FireStore!!!", Toast.LENGTH_LONG).show();
                                Log.e(TAG, "onFailure : Error getting genders from FireStore!!!");

                            }
        });





        Log.i(TAG, "getGendersDataFromFirestore: END");
    } // END getGendersDataFromFirestore()


//    public void getUserDataFromFirestore() {
//
//        Log.i(TAG, "getUserDataFromFirestore : BEGIN");
//
////        user = FirebaseAuth.getInstance().getCurrentUser();
////        userId = user.getUid();
////        db = FirebaseFirestore.getInstance();
////        Query query = db.collection("Genders");
//
//
////                .whereEqualTo("ge_country", "FR");
////        collection : db.collection("Genders").getId().toString();
//        docRefUserConnected = db.document("users/"+ userId); //xZwCEqfYK8OriViGFzok
//        //docRefUserConnected = db.document("users/"+ "xZwCEqfYK8OriViGFzok"); //xZwCEqfYK8OriViGFzok
//       // userNickName = documentSnapshot.getString("us_nick_name");
//
//
//
//        docRefUserConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot myDocSnapshot) {
//
//                if (myDocSnapshot.exists()) {
//
//                    userNickName = "Connected user : " + myDocSnapshot.getString("us_nickname");// + " " + userId;
//
//
//                    ModelUsers connectedUser = myDocSnapshot.toObject(ModelUsers.class);
//                    userNickName    = "Connected user (" + connectedUser.getUs_country_lang() + ") : " + connectedUser.getUs_nickname();
//                    userAuthUID     =  "Connected userAuthIUD : " + connectedUser.getUs_auth_uid();
//                    userCountryLanguage     =   connectedUser.getUs_country_lang().toString().trim();
////                    userCountryLanguage     = "FR";
//                    Log.i(TAG, "onSuccess userCountryLanguage : " + userCountryLanguage);
//
////                    Snackbar.make(userActivityConstraintLayout, "USER FOUND !!!!!! " + userId, Snackbar.LENGTH_SHORT).show();
////                    Snackbar.make(userActivityConstraintLayout, "USER FOUND !!!!!! " + userNickName, Snackbar.LENGTH_SHORT).show();
//                    Util.showSnackBar(userActivityConstraintLayout,"USER FOUND !!!!!! " + userId);
////                    Util.waitfor(500);
//                    Util.showSnackBar(userActivityConstraintLayout,"USER FOUND !!!!!! " + userNickName);
////                    Util.waitfor(500);
//                    Util.showSnackBar(userActivityConstraintLayout,"USER FOUND !!!!!! " + userAuthUID);
//
//                    Log.i(TAG, "userId : " + userId);
//                    Log.i(TAG, "userNickName : " + userNickName);
//                    Log.i(TAG, "userAuthUID : " + userAuthUID);
//
//                    //Toast.makeText(UserActivity.this, "USER FOUND !!!!!!" + userId, Toast.LENGTH_SHORT).show();
//                    //Toast.makeText(UserActivity.this, "", Toast.LENGTH_SHORT).show();
//                } else {
//                    //Toast.makeText(UserActivity.this, "No USER Document for this id", Toast.LENGTH_SHORT).show();
//
//                    userNickName = "No USER FOUND";
//                        //"No Document for this id", Toast.LENGTH_SHORT).show;
//                    Snackbar.make(userActivityConstraintLayout, "No USER Document for this id", Snackbar.LENGTH_SHORT).show();
//                }
//
//                tvUserId.setText(userId);
//                tvUserNickName.setText(userNickName);
//                tvUserAuthUID.setText(userAuthUID);
//                //rgGenders.add
//            }
//        });
//
////        Log.i(TAG, "*** userId : " + userId);
////        Log.i(TAG, "*** userNickName : " + userNickName);
////        Log.i(TAG, "*** userAuthUID : " + userAuthUID);
//
//
//
//        Log.i(TAG, "getUserDataFromFirestore : END");
//
//
//
//
//    } // END getUserDataFromFirestore()


} // End Class UserActivity