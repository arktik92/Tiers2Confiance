package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LierParrainFilleulAdapter extends FirestoreRecyclerAdapter<ModelUsers, LierParrainFilleulAdapter.ItemViewHolder> {

    private static final String TAG = "Lier P/F ADAPTER :";
    // Le context
    private Context context;
    // Liste basée sur le model ModelUser
    private ArrayList<ModelUsers> usersArrayList;

    // Initialisation de la base de données, récupérations de la collection Users
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollectionRef = db.collection("users");

    //Varaibles pour stocker l'utilisateur connecté
    private FirebaseUser currentUser;
    private DocumentReference userConnected;
    private DocumentReference userPosition;

    //Varaibles pour stocker l'utilisateur cliqué (demandé)
    private String usAuthUidRequested;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LierParrainFilleulAdapter(@NonNull FirestoreRecyclerOptions<ModelUsers> options) {
        super(options);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.activity_lier_parrain_filleul_item_recycler, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ModelUsers model) {

        // Récupération des attributs des utilisateurs de la liste
        String us_nickname = model.getUs_nickname();
        String us_city = model.getUs_city();
        Date us_birth_day = model.getUs_birth_date();
        String us_godfather_request_from = model.getUs_godfather_request_from();
        String us_nephews_request_from = model.getUs_nephews_request_from();

        holder.tv_nickname.setText(us_nickname);
        holder.tv_city.setText(us_city);

        String str = String.format("%tc", us_birth_day);
        holder.tv_birth_day.setText(str);

        // Récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = usersCollectionRef.document(currentUser.getUid());

        //TODO Inserer la bonne image quand on aura récupérer l'url depuis le model
        // Utilisation de glide pour afficher les images,
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        Context context = holder.iv_photo_profil.getContext();
        // Fonctionne soit avec un contexte ou une vue (with), puis avec un load d'url,
        // puis le chargement des options précédemment crées, puis centrer l'image,
        // puis diskCacheStrategy
        // puis l'emplacement où mettre l'image, dans le holder crée.
        Glide.with(context)
                .load(R.mipmap.ic_launcher)
                .apply(options)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_photo_profil);

        // Association du listener au bouton Envoyer la demande (de parrainage, de filleul)
        holder.btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPosition = usersCollectionRef.document(getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition()).getId());
                userConnected.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                                assert contenuUser != null;

                                // Si l'utilisateur connecté est un célibataire, il cherche et demande à des parrains d'être leur filleul
                                if (contenuUser.getUs_role() == 1) {
                                    Log.e(TAG , "je suis un célibataire connecté, j'envoi une demande à un parrain");
                                    userConnected.update("us_godfather_request_to", contenuUser.getUs_godfather_request_to() + userPosition.getId() );
                                    userPosition.update("us_nephews_request_from",   us_nephews_request_from + userConnected.getId()+  ";");
                                }

                                // Si l'utilisateur connecté est un parrain, il cherche et demande à des célibataires de les parrainer
                                if (contenuUser.getUs_role() == 2) {
                                    Log.e(TAG , "je suis un parrain connecté, j'envoie une demande à un célibataire");
                                    userConnected.update("us_nephews_request_to", contenuUser.getUs_nephews_request_to() + userPosition.getId()+  ";");
                                    userPosition.update("us_godfather_request_from", us_godfather_request_from + userConnected.getId());
                                }
                                holder.btn_request.setText("Demande envoyée");
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "erreur" + currentUser.getUid());
                            }
                        });

            }
        });
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nickname, tv_city, tv_birth_day;
        public ImageView iv_photo_profil;
        public Button btn_request;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_birth_day = itemView.findViewById(R.id.tv_birth_day);
            iv_photo_profil = itemView.findViewById(R.id.iv_photo_profil);
            btn_request = itemView.findViewById(R.id.btn_request);

        }
    }



}
