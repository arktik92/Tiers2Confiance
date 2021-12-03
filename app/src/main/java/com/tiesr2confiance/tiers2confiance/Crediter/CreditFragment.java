package com.tiesr2confiance.tiers2confiance.Crediter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentCrediterBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CreditFragment extends Fragment {

    /** Var Design **/
    private EditText etCredit;
    private Button btncredit;

    /** Variables globales **/
    private static final String TAG = "Crediter :";

    /** Var Firebase **/
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollectionRef = db.collection("users");
    private DocumentReference userConnected;
    private DocumentReference userFilleul;
    private Long usSolde;
    private Long usSoldeNew;
    private Long usRole;
    private String usNephews;

    private FragmentCrediterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_crediter, container, false);
        binding = FragmentCrediterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /** Initialisation des composants et affichage de la liste d'utilisateurs avec la recherche associée **/
    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etCredit = view.findViewById(R.id.et_credit);
        btncredit = view.findViewById(R.id.btn_crediter);

        btncredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ici on determine le rôle de l'utilisateur connecté et on stock le rôle dans la variable usRole
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                assert currentUser != null;
                userConnected = usersCollectionRef.document(currentUser.getUid());

                /** Lors du clic sur le bouton CREDITER dans fragment_crediter.xml, Récupération du User à créditer et créditer le solde  **/
                userConnected.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (TextUtils.isEmpty(etCredit.getText().toString())){
                        Toast.makeText(getContext(), "Merci de saisir un montant à créditer", Toast.LENGTH_SHORT).show();
                    }else{
                        ModelUsers contenuUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                        assert contenuUser != null;
                        usRole = contenuUser.getUs_role();

                        if (usRole.equals(1L)) {
                            // je suis célibataire => Créditer mon compte
                            usSolde = contenuUser.getUs_balance() ;
                            usSoldeNew = usSolde + Long.parseLong(etCredit.getText().toString()) ;
                            // Je crédite le célibataire connecté
                            userConnected.update("us_balance", usSoldeNew);
                        } else {
                            // je suis parrain => Créditer mon filleul
                            usNephews = contenuUser.getUs_nephews();
                            Log.e(TAG, "onComplete: " + contenuUser.getUs_nephews() );
                            if (TextUtils.isEmpty(usNephews) == false){
                                userFilleul = usersCollectionRef.document(usNephews);
                                userFilleul.get()
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Le filleul n'a pas été trouvé", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                                // Je crédite le célibataire
                                                Log.e(TAG, "onSuccess: " + userFilleul.getId() );
                                                ModelUsers contenuFilleul = documentSnapshot.toObject(ModelUsers.class);
                                                assert contenuUser != null;
                                                usSolde = contenuFilleul.getUs_balance();
                                                usSoldeNew = usSolde + Long.parseLong(etCredit.getText().toString()) ;
                                                userFilleul.update("us_balance", usSoldeNew);
                                                Toast.makeText(getContext(), "Solde crédité, merci!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }else{
                                Toast.makeText(getContext(), "Il faut avoir un filleul pour créditer son compte", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
            }
        });

    }


}


