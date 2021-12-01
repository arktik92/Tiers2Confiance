package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Models.ModelViewProfilItem;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentLierParrainFilleulBinding;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentProfilBinding;

public class ProfilFragment extends Fragment {


    private static final String TAG = "users_test_profil";
    /*** *Clé pour chaque champs de la BDD ***/

    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_emails = "email";

    /*** Global Variable ***/


    private EditText myName, myCity, myEmail;
    /*** Begin - BDD ***/
    /*** Create BDD ***/
    private FirebaseFirestore db;
    /*** ID Document ***/
    private DocumentReference noteRef;
    /*** Collection reference ***/
    private CollectionReference noteCollectionRef;
    private FragmentProfilBinding binding;

    /*** end - BDD ***/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        getDataIDUser(view);
        return binding.getRoot();
    }

    /** Initialisation des composants  **/
    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        myName = view.findViewById(R.id.etName);
        myCity = view.findViewById(R.id.etCIty);
        myEmail = view.findViewById(R.id.etEmail);
    }

    // Récupération de l'id de l'utilisateur connecté
    private void getDataIDUser(View view) {
        /* Init BDD */
        /*** BDD Connexion ***/
        db = FirebaseFirestore.getInstance();
        /*** db Location ***/
        noteRef = db.document("/notes/collection");
        /** Add Document into the collection with each different ID **/
        noteCollectionRef = db.collection("notes");
        /* End Init BDD */
    }


    public void updateNote(View v) {
        String mName = myName.getText().toString().trim();
        String mCity = myCity.getText().toString().trim();
        String mEmail = myEmail.getText().toString().trim();

        ModelViewProfilItem content = new ModelViewProfilItem(mName, mCity, mEmail);

        noteCollectionRef.add(content);

/*
        ModelViewProfilItem contentProfil = new ModelViewProfilItem(mName);

         noteCollectionRef.add(contentProfil)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Profil.this, "OnSuccess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profil.this, "OnFailure", Toast.LENGTH_SHORT).show();

                    }
                });
*/
    }

}