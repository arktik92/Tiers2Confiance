package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class ViewProfil extends AppCompatActivity {


    /***  Global Variables  ***/

    private static final String TAG = "View Profile";


    private static final String KEY_NAME = "us_first_name";
    private static final String KEY_CITY = "us_city";
    private static final String KEY_IMG = "us_img";
    private static final String KEY_IMG_AVATAR = "us_img_avatar";
    private static final String KEY_DESCRIPTION = "us_presentation";
    private static final String KEY_HOBBIES = "us_hobbies";


    private static String KEY_FS_USER_ID = "4coBi7nRA1Np1KGQpI1b";
    private static final String KEY_FS_COLLECTION = "user_test_profil";
    private static final String KEY_FS_USER_HOBBIE = "user_test_profil_hobbies";

    private static final String KEY_HOBBIES_NAME = "ho_name";


    private TextView tvProfilName, tvDescription, tvProfilCity, tvHobbies;
    private ImageView ivProfil, ivProfilAvatarShape;

    /*** Hobbies ***/
    private TextView tvHobbiesname;


    private ArrayList<String> itemArrayListHobbieValues;
    private ArrayList<String> itemArrayListHobbieDocument;


    /*** ADapterHobbies ***/
    private AdapterHobbieItem adapterHobbieItem;
    private RecyclerView recyclerViewHobbies;


    /*** BDD ***/
    private FirebaseFirestore db, db_hobbies;
    /**
     * ID Document
     **/
    private DocumentReference noteRef, noteHobbies;
    /**
     * Collection
     **/
    private CollectionReference noteCollectionRef;

    String list_hobbies;
    String hobbies_list[];

    public void init() {

        tvProfilName = findViewById(R.id.tvProfilName);
        tvProfilCity = findViewById(R.id.tvProfilCity);
        tvDescription = findViewById(R.id.tvDescription);

        tvHobbies = findViewById(R.id.tvHobbies);

        tvHobbiesname = findViewById(R.id.tvHobbiesName);


        /** Glide image **/

        ivProfil = findViewById(R.id.ivProfil);
        ivProfilAvatarShape = findViewById(R.id.ivProfilAvatarShape);

        /** BDD, Connexion FIreStore ***/
        db = FirebaseFirestore.getInstance();

        // NoteRef
        noteRef = db.document(KEY_FS_COLLECTION + "/" + KEY_FS_USER_ID);

        // Add Data
        noteCollectionRef = db.collection(KEY_FS_COLLECTION);


        /**** End - BDD Connexion Firestore *****/

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profil);

        init();
        showProfil();
        hobbies();
    }


    public void showProfil() {

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("IdUser") != null) {
            KEY_FS_USER_ID = bundle.getString("IdUser");
            Log.d(TAG, "BundleGetString: "+ KEY_FS_USER_ID);
        }

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString(KEY_NAME);
                            String city = documentSnapshot.getString(KEY_CITY);

                            String imgurl = documentSnapshot.getString(KEY_IMG);
                            String imgUrl_avatar = documentSnapshot.getString(KEY_IMG_AVATAR);

                            String description = documentSnapshot.getString(KEY_DESCRIPTION);

                            String hobbies = documentSnapshot.getString(KEY_HOBBIES);


                            // Toast.makeText(View_Profil.this, imgUrl_avatar, Toast.LENGTH_LONG).show();

                            tvProfilCity.setText(city);
                            tvProfilName.setText(name);
                            tvDescription.setText(description);
                            tvHobbies.setText(hobbies);


                            /** Glide - Add Picture **/
                            Context context = getApplicationContext();


                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .error(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher);


                            Glide
                                    .with(context)
                                    .load(imgurl)
                                    .apply(options)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfil);


                            /** Loading Avatar **/


                            Glide
                                    .with(context)
                                    .load(imgUrl_avatar)
                                    .apply(options)
                                    .fitCenter()
                                    .circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfilAvatarShape);


                        } else {
                            Toast.makeText(ViewProfil.this, "Any Document", Toast.LENGTH_SHORT).show();

                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewProfil.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


    }


    public void hobbies() {

        /****   Collection hobbies ******/


        itemArrayListHobbieDocument = new ArrayList<>();
        itemArrayListHobbieValues = new ArrayList<>();

        /** BDD, Connexion FIreStore ***/


        db = FirebaseFirestore.getInstance();

        // NoteRef
        noteRef = db.document(KEY_FS_USER_HOBBIE + "/" + KEY_FS_USER_ID); /**** Hobbies -> Jardinier ****/



        // Add Data
        noteCollectionRef = db.collection(KEY_FS_USER_HOBBIE); //hobbies



        /** get specific item into the collection **/

        noteCollectionRef = db.collection("user_test_profil");
        noteCollectionRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        String split_key = ";";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                list_hobbies = document.getString("us_hobbies");
                                Log.d(TAG, " List Hobbies ID => " + list_hobbies); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}


                                String[] hobbies_list = list_hobbies.split(split_key);
                                Log.d(TAG, "hobbies list length: "+ hobbies_list.length);


                                /****/



    /*********/

    noteCollectionRef = db.collection("hobbies");
    noteCollectionRef
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String list_hobbies_item = document.getString("ho_label");
                            Log.d(TAG, " List Hobbies Item => " + list_hobbies_item); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}

                        //    String list_hobbies_item_id = document.getString("ho_id");
                          //  Log.d(TAG, " => Hobbies ID => " + list_hobbies_item_id); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}
                            ModelHobbies contenuHobbie = document.toObject(ModelHobbies.class);

                            String LlabelHobbie = contenuHobbie.ho_label;
                            Long idHobbie = contenuHobbie.ho_id;

                           // Long list_hobbies_item_id = Long.valueOf(Objects.requireNonNull(document.getString("ho_id")));
                           // Log.d(TAG, "list_hobbies_item_id:"+list_hobbies_item_id);

                            HashMap<Long, String> HashmapHobbie = new HashMap<Long, String>();

                            HashmapHobbie.put(idHobbie, LlabelHobbie);


                            /**** TODo ****///


                            Log.d(TAG, HashmapHobbie.keySet() + " => HashMap: " + HashmapHobbie.get(idHobbie));


                            ModelUsers contenuUser = document.toObject(ModelUsers.class);

                            String hobbies_list = contenuUser.us_hobbies;

                            noteCollectionRef = db.collection("user_test_profil");
                            noteCollectionRef
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<QuerySnapshot> task) {


                                                                   String split_key = ";";
                                                                   if (task.isSuccessful()) {
                                                                       for (QueryDocumentSnapshot document : task.getResult()) {

                                                                           list_hobbies = document.getString("us_hobbies");


                                                                    //       Log.d(TAG, "User Contener hobbies " + hobbies_list);

                                                                       }
                                                                   }
                                                               }
                                                           });



                            Log.d(TAG, " List Hobbies ID => " + list_hobbies); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}


                            String split_key =";";

                            String listHobbiesItem [] = null;

                            Object hobbie_item = 11;

                            listHobbiesItem = list_hobbies.split(split_key);


                            for (int i = 0; i < listHobbiesItem.length; i++) {

                                Log.d(TAG, "listhobbies: " + listHobbiesItem[0]);

                                Log.d(TAG, "hobbie_item: " + hobbie_item);

                                Log.d(TAG, "listhobbies: " + listHobbiesItem[0]);

                                Log.d(TAG, "toto: " + HashmapHobbie.get(11));
                            }

                            tvHobbiesname.setText(HashmapHobbie.get(11));


                          //  Log.d(TAG, "lH =>  " + listHobbiesItem.length);






                            /****
                            String listHobbies ="";

                            String item_hobbie ="";

                            for(int k = 0; k < listHobbiesItem.length; k++) {

                                Log.d(TAG, "HASH: " + HashmapHobbie.get(1));

                                Log.d(TAG, "Hobbies //////: " + listHobbiesItem[k]);

                                listHobbies = listHobbies +HashmapHobbie.get(listHobbiesItem[k])+"\n";

                              // item_hobbie =  item_hobbie + HashmapHobbie.get(listHobbiesItem[k]);

                                tvHobbiesname.setText(listHobbies);
                            }
                            ***/




                            /****


                            final String split_key = ";";


                            String hobbieslist [] = list_hobbies.split(split_key);



                            for(int i = 0; i <= list_hobbies.length(); i++){


                                Log.d(TAG, "Hobbies ID => " + hobbieslist[i]);
                                // tvHobbiesname.setText(hobbies_list[i]);
                            }


                            ****/




                        }



                    } else {

                        Log.d(TAG, "Task onComplete Hobbies Item Failure !!!");
                    }
                }
            });









                                /****
                                for (int i = 0; i <= hobbies_list.length; i++) {
                                    Log.d(TAG, " Hobbie " + i + " => " + hobbies_list[i]); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}
                                }


                                 ****/


                            }





                        } else {

                            Log.d(TAG, "Task onComplete Failure !!!");
                        }
                    }
                });



/**

 final String split_key = ";";
 final String chaine  = "1;3;5;9;12";


 String hobbies_list[] = chaine.split(split_key);



 for(int i = 0; i <= hobbiesname.length(); i++){

 // tvHobbiesname.setText(hobbies_list[i]);
 }
 **/






        /** End **/


        noteCollectionRef = db.collection(KEY_FS_USER_HOBBIE); //hobbies


        /*** get all the collection ***/

        noteCollectionRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {


                                        Log.d(TAG, document.getId() + " => " + document.getData()); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}


                                        Log.d(TAG, " Adding getID => " + document.getId()); // Ie19kQdquBcoGypUpyWS


                                        itemArrayListHobbieDocument.add(document.getId());

                                        Log.d(TAG, " Adding getData => " + document.getData());
                                        itemArrayListHobbieValues.add(String.valueOf(document.getData()));


                                        String Value = document.getString("ho_label"); // Jardinier
                                        Log.d(TAG, " Value getString => " + Value);


                                        Log.d(TAG, " Total Data => " + itemArrayListHobbieDocument.size());


                                        Log.d(TAG, "******** LIST Document ID *********\n");

                                        for (String item : itemArrayListHobbieDocument) {
                                            Log.d(TAG, " Document ID => " + item);


                                            int position = itemArrayListHobbieDocument.indexOf(item);

                                            /**** Indexof ***/
                                            Log.d(TAG, " Document " + item + " => " + position);

                                            Log.d(TAG, " Data [" + item + "] => " + itemArrayListHobbieValues.get(position));

                                            Log.d(TAG, " Document " + item + " ! " + itemArrayListHobbieValues.get(position)); //lastIndexOf("ho_label")); //toString("no_label")); ---> false/true  contains("ho_label")


                                            String Value2 = document.getString("ho_label");

                                            Log.d(TAG, " Value " + Value2); //lastIndexOf("ho_label")); //toString("no_label")); ---> false/true  contains("ho_label")


                                            tvHobbiesname.setText(Value2);

                                        }


                                        // String Document_id ="Ie19kQdquBcoGypUpyWS"; //8r84uynmKS7rorCaFOJJ


                                        /****/


                                        /*****/
                                    }


                                } else {
                                    Log.d(TAG, "Error gettings document:", task.getException());
                                }
                            }
                        });


        Log.i(TAG, "size =>" + itemArrayListHobbieDocument.size());


        /**** End - BDD Connexion Firestore *****/

        noteRef.get()
                .

                        addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if (documentSnapshot.exists()) {

                                    String hobbiesname = documentSnapshot.getString("users");

                                    Log.d(TAG, "BDD ===> " + hobbiesname);


                                    /***
                                     for(int i = 0; i <= hobbiesname.length(); i++ ){
                                     Log.i(TAG, "ID Collection --->"+noteCollectionRef.getId());
                                     Log.i(TAG, "ID Snapshoot ---->" + documentSnapshot.getId());

                                     }
                                     ***/


                            /*
                            for (int i = 0; i <= hobbiesname.length(); i++) {

                                String hobbieID = documentSnapshot.getId();

                                TextView tv = new TextView(getApplicationContext());
                                tv.setText(hobbieID);
                                Toast.makeText(View_Profil.this, hobbiesname.length() + "////" + hobbieID, Toast.LENGTH_SHORT).show();

                                //itemArrayListHobbie.add(new ModelUsersHobbies(hobbieID));



                                Toast.makeText(View_Profil.this, "ID" + hobbieID, Toast.LENGTH_SHORT).show();


                                //itemArrayListHobbie.add(new ModelUsers(hobbieID));
                            }*/

                                    // adapterHobbieItem = new AdapterHobbieItem(getApplicationContext(), itemArrayListHobbie); // il atteint le context et un ArrayList

                                    /** Adapter recyclerView à l'adapter **/

                                    //recyclerViewHobbies.setAdapter(adapterHobbieItem);

                                } else {
                                    Toast.makeText(ViewProfil.this, "Any Hobbies Document", Toast.LENGTH_SHORT).show();

                                }

                            }
                        })
                .

                        addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
    }

}