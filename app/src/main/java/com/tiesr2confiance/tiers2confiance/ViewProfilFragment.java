package com.tiesr2confiance.tiers2confiance;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_CITY;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_DESCRIPTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HOBBIES;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_IMG;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_IMG_AVATAR;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.tiesr2confiance.tiers2confiance.Common.ListsAttributs;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentViewProfilBinding;

import java.util.HashMap;
import java.util.Map;

public class ViewProfilFragment extends Fragment {

    public static final String TAG = "View Profile";

    private TextView tvProfilName, tvDescription, tvProfilCity, tvHobbies;
    private ImageView ivProfilAvatarShape;

    /*** BDD ***/
    private FirebaseFirestore db;
    /** ID Document **/
    private DocumentReference noteRef;
    /** Collection **/
    private String KEY_FS_USER_ID = "c0aS9xtlb1CFE51hQzRJ";
    public final String KEY_FS_COLLECTION = "users";


    String list_hobbies;
    public HashMap<Long, String> globalVarValue;

    private FragmentViewProfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        public String arg = getArguments().getString("");
        View view = inflater.inflate(R.layout.fragment_view_profil, container, false);

      //  Bundle bundle = getIntent().getExtras();
      //  if(bundle.getString("IdUser") != null) {
      //      KEY_FS_USER_ID = bundle.getString("IdUser");
      //      Log.d(TAG, "BundleGetString: "+ KEY_FS_USER_ID);
      //  }

        getDataIDUser(view);
        showProfil();
        binding = FragmentViewProfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /** Initialisation des composants  **/
    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tvProfilName = view.findViewById(R.id.tvProfilName);
        tvProfilCity = view.findViewById(R.id.tvProfilCity);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvHobbies = view.findViewById(R.id.tvHobbies);

        /** Glide image **/
        ivProfilAvatarShape = view.findViewById(R.id.ivProfilAvatarShape);
    }

    private void getDataIDUser(View view) {
        /** BDD, Connexion FIreStore ***/
        db = FirebaseFirestore.getInstance();
        // NoteRef, récupération de l'utilisateur connecté
        noteRef = db.document(KEY_FS_COLLECTION + "/" + KEY_FS_USER_ID);
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

                            tvProfilCity.setText(city);
                            tvProfilName.setText(name);
                            tvDescription.setText(description);
                            /** Glide - Add Picture **/
                            Context context = getContext();
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .error(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher);
                            /*
                            Glide
                                    .with(context)
                                    .load(imgurl)
                                    .apply(options)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfil);
                                 */
                            /** Loading Avatar **/
                            Glide
                                    .with(context)
                                    .load(imgUrl_avatar)
                                    .apply(options)
                                    .fitCenter()
                                    .circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivProfilAvatarShape);

                            list_hobbies = documentSnapshot.getString("us_hobbies");
                            Log.d(TAG, " List Hobbies ID => " + list_hobbies); //  Ie19kQdquBcoGypUpyWS => {ho_label=Jardiner}
                            String split_key = ";";
                            // Ici on a récupérer dans la variables hobbies_list la liste des hobbies de l'utilisateur
                            String[] hobbies_list = list_hobbies.split(split_key);

//                            ListsAttributs listHobbiesVar = new ListsAttributs();
//                            listHobbiesVar.setGlobalVarValue( globalVarValue);
//                            globalVarValue = listHobbiesVar.getGlobalVarValue();

                            globalVarValue = new HashMap<Long, String>();
                            globalVarValue.put((long)1, getContext().getString(R.string.ho_artisanat_text));
                            globalVarValue.put((long)2, getString(R.string.ho_balades_text));
                            globalVarValue.put((long)3, getString(R.string.ho_boites_text));
                            globalVarValue.put((long)4, getString(R.string.ho_cafe_text));
                            globalVarValue.put((long)5, getString(R.string.ho_charites_text));
                            globalVarValue.put((long)6, getString(R.string.ho_clubs_text));
                            globalVarValue.put((long)7, getString(R.string.ho_cuisiner_text));
                            globalVarValue.put((long)8, getString(R.string.ho_déguster_text));
                            globalVarValue.put((long)9, getString(R.string.ho_fairerencontres_text));
                            globalVarValue.put((long)10, getString(R.string.ho_films_text));
                            globalVarValue.put((long)11, getString(R.string.ho_jardiner_text));
                            globalVarValue.put((long)12, getString(R.string.ho_jeuxcartes_text));
                            globalVarValue.put((long)13, getString(R.string.ho_jeuxvideos_text));

                            Log.e(TAG, "onSuccess SIZE: " + globalVarValue.size() );

                            int i;
                            String hobbies_display="--";
                            for (i=0; i< hobbies_list.length;i++) {
                                for (Map.Entry<Long, String> entry : globalVarValue.entrySet()) {
                                    String key = entry.getKey().toString();
                                    String value = entry.getValue();
                                    if (key.equals(hobbies_list[i])) {
                                        Log.e(TAG, "onSuccess: " + "Clé: " + key + ", Valeur: " + value );

                                        hobbies_display += value + " -- ";
                                    }
                                }
                            }

                            tvHobbies.setText(hobbies_display);
                            // Ici on va chercher les labels correspondants à la liste d'ID récupérée, puis on les affiche
                            //ListsAttributs attrHobies = new ListsAttributs(FirebaseFirestore.getInstance(), "EN");
                            //attrHobies.getHobbiesDataFromFirestore();

                        } else {
                            Toast.makeText(getContext(), "Any Document", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
