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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class View_Profil extends AppCompatActivity {


    /***  Global Variables  ***/

    private static final String TAG = "View Profile";


    private static final String KEY_NAME = "us_first_name";
    private static final String KEY_CITY = "us_city";
    private static final String KEY_IMG = "us_img";
    private static final String KEY_IMG_AVATAR = "us_img_avatar";
    private static final String KEY_DESCRIPTION = "us_description";
    private static final String KEY_HOBBIES = "us_hobbies";


    private static final String KEY_HOBBIES_NAME = "ho_name";


    private TextView tvProfilName, tvDescription, tvProfilCity, tvHobbies;
    private ImageView ivProfil, ivProfilAvatarShape;

    /*** Hobbies ***/
    private TextView tvHobbiesname;


    private ArrayList<ModelUsers> itemArrayListHobbie;


    /*** ADapterHobbies ***/
    private AdapterHobbieItem adapterHobbieItem;
    private RecyclerView recyclerViewHobbies;


    /*** BDD ***/
    private FirebaseFirestore db;
    /**
     * ID Document
     **/
    private DocumentReference noteRef;
    /**
     * Collection
     **/
    private CollectionReference noteCollectionRef;


    public void init() {

        tvProfilName = findViewById(R.id.tvProfilName);
        tvProfilCity = findViewById(R.id.tvProfilCity);
        tvDescription = findViewById(R.id.tvDescription);

        tvHobbies = findViewById(R.id.tvHobbies);

        tvHobbiesname = findViewById(R.id.tvHobbiesName);


        itemArrayListHobbie = new ArrayList<>();


        /** Glide image **/

        ivProfil = findViewById(R.id.ivProfil);
        ivProfilAvatarShape = findViewById(R.id.ivProfilAvatarShape);

        /** BDD, Connexion FIreStore ***/
        db = FirebaseFirestore.getInstance();

        // NoteRef
        noteRef = db.document("user_test_profil/4coBi7nRA1Np1KGQpI1b");

        // Add Data
        noteCollectionRef = db.collection("user_test_profil");


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
                            Toast.makeText(View_Profil.this, "Any Document", Toast.LENGTH_SHORT).show();

                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(View_Profil.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


    }


    public void hobbies() {

        itemArrayListHobbie = new ArrayList<>();


        /** BDD, Connexion FIreStore ***/
        db = FirebaseFirestore.getInstance();

        // NoteRef
        noteRef = db.document("hobbies/0ll0YKNY37qcFbaWsa9W");

        // Add Data
        noteCollectionRef = db.collection("hobbies");


        /**** End - BDD Connexion Firestore *****/

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            String hobbiesname = documentSnapshot.getString(KEY_HOBBIES_NAME);

                            tvHobbiesname.setText(hobbiesname);

                            final String split_key = ";";
                            final String chaine = "1;3;5;9;12;";

                            String hobbies_list[] = chaine.split(split_key);

                            for (int i = 0; i <= hobbiesname.length(); i++) {

                                String hobbieID = documentSnapshot.getId();

                                TextView tv = new TextView(getApplicationContext());
                                tv.setText(hobbieID);
                                Toast.makeText(View_Profil.this, hobbiesname.length() + "////" + hobbieID, Toast.LENGTH_SHORT).show();

                                //itemArrayListHobbie.add(new ModelUsersHobbies(hobbieID));
                                //TODO boolean add = itemArrayListHobbie.add(new ModelUsers());
                            }

                            adapterHobbieItem = new AdapterHobbieItem(getApplicationContext(), itemArrayListHobbie); // il atteint le context et un ArrayList

                            /** Adapter recyclerView à l'adapter **/

                            recyclerViewHobbies.setAdapter(adapterHobbieItem);

                        } else {
                            Toast.makeText(View_Profil.this, "Any Hobbies Document", Toast.LENGTH_SHORT).show();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}