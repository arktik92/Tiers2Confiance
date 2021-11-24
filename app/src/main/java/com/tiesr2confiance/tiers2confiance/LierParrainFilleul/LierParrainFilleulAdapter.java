package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.Date;

public class LierParrainFilleulAdapter extends FirestoreRecyclerAdapter<ModelUsers, LierParrainFilleulAdapter.ItemViewHolder> {

    private static final String TAG = "Lier P/F ADAPTER :";
    // Le context
    private Context context;
    // Liste basée sur le model ModelUser
    private ArrayList<ModelUsers> usersArrayList;

    // Initialisation de la base de données, récupérations de la collection Users
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollectionRef = db.collection("users");;

    //Varaibles pour stocker l'utilisateur connecté
    private FirebaseUser currentUser;
    private DocumentReference useRef;

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

        String us_nickname = model.getUs_nickname();
        String us_city = model.getUs_city();
        Date us_birth_day = model.getUs_birth_date();
        Long us_role = model.getUs_role();
        String us_auth_uid = model.getUs_auth_uid();

        holder.tv_nickname.setText(us_nickname);
        holder.tv_city.setText(us_city);

        String str = String.format("%tc", us_birth_day);
        holder.tv_birth_day.setText(str);

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


        // Récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Association du listener au bouton Envoyer la demande (de parrainage, de filleul)
        holder.btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btn_request.setVisibility(View.GONE);
                usAuthUidRequested = us_auth_uid;

                useRef = usersCollectionRef.document(currentUser.getUid());
                useRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.e(TAG, "succes hehe");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "erreur hehe");
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
