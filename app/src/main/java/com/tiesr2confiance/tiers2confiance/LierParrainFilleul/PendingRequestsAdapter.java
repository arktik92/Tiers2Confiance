package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.Date;

public class PendingRequestsAdapter extends FirestoreRecyclerAdapter<ModelUsers, PendingRequestsAdapter.ItemViewHolder> {

    private static final String TAG = "Accepter P/F ADAPTER :";

    // Initialisation de la base de données, récupérations de la collection Users
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollectionRef = db.collection("users");

    //Variables pour stocker l'utilisateur connecté
    private FirebaseUser currentUser;
    private DocumentReference userConnected;
    private DocumentReference userPosition;

    // Variable locale
    private PendingRequestsAdapter.OnItemClickListener mOnItemClickListener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public  PendingRequestsAdapter(@NonNull FirestoreRecyclerOptions<ModelUsers> options) {
        super(options);
    }

    /** #1 Interface **/
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    public void setOnItemCliclListener(PendingRequestsAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ModelUsers model) {

        // Récupération des attributs des utilisateurs de la liste
        String us_nickname = model.getUs_nickname();
        String us_city = model.getUs_city();
        Date us_birth_day = model.getUs_birth_date();

        String us_godfather = model.getUs_godfather();
        String us_nephews = model.getUs_nephews();

        holder.tv_nickname_request.setText(us_nickname);
        holder.tv_city_request.setText(us_city);

        String str = String.format("%tc", us_birth_day);
        holder.tv_birth_day_request.setText(str);

        // Récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = usersCollectionRef.document(currentUser.getUid());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        Context context = holder.iv_photo_profil_request.getContext();
        // Fonctionne soit avec un contexte ou une vue (with), puis avec un load d'url,
        // puis le chargement des options précédemment crées, puis centrer l'image,
        // puis diskCacheStrategy
        // puis l'emplacement où mettre l'image, dans le holder crée.
        Glide.with(context)
                .load(R.mipmap.ic_launcher)
                .apply(options)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_photo_profil_request);

        // Association du listener au bouton Accepter la demande (de parrainage, de filleul)
        holder.btn_request_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPosition = usersCollectionRef.document(getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition()).getId());
                userConnected.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                                assert contenuUser != null;

                                // Si l'utilisateur connecté est un célibataire, il accepte la demande d'un parrain
                                if (contenuUser.getUs_role() == 1) {
                                    userConnected.update("us_godfather", userPosition.getId() );
                                    // Replace
                                    String ListDemands = contenuUser.getUs_godfather_request_from();
                                    String ListDemandsNew = ListDemands.replace(userPosition.getId()+ ";", "");
                                    userConnected.update("us_godfather_request_from", ListDemandsNew);

                                    userPosition.update("us_nephews",   us_nephews + userConnected.getId()+  ";");
                                    userPosition.update("us_nephews_request_to", "" ); // Replace
                                    Log.i(TAG, "LOGPGO Demande du parrain acceptée par le célibataire");
//                                    Log.e(TAG, "Demande du parrain acceptée par le célibataire");
                                }

                                // Si l'utilisateur connecté est un parrain, il cherche et demande à des célibataires de les parrainer
                                if (contenuUser.getUs_role() == 2) {
                                    userConnected.update("us_nephews", contenuUser.getUs_nephews_request_to() + userPosition.getId()+  ";");
                                    // Replace
                                    String ListDemands = contenuUser.getUs_nephews_request_from();
                                    String ListDemandsNew = ListDemands.replace(userPosition.getId() + ";", "");
                                    userConnected.update("us_nephews_request_from", ListDemandsNew );

                                    userPosition.update("us_godfather", userConnected.getId() );
                                    userPosition.update("us_godfather_request_to", "");
                                    Log.i(TAG, "LOGPGO Demande du célibataire acceptée par le parrain");
//                                    Log.e(TAG, "Demande du célibataire acceptée par le parrain");
                                }
                                holder.btn_request_request.setText("Demande acceptée");
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

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.activity_pending_requests_item_recycler, parent, false);
        return new PendingRequestsAdapter.ItemViewHolder(view);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nickname_request, tv_city_request, tv_birth_day_request;
        public ImageView iv_photo_profil_request;
        public Button btn_request_request;
        public CardView cv_profil_user_request;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nickname_request = itemView.findViewById(R.id.tv_nickname_request);
            tv_city_request = itemView.findViewById(R.id.tv_city_request);
            tv_birth_day_request = itemView.findViewById(R.id.tv_birth_day_request);
            iv_photo_profil_request = itemView.findViewById(R.id.iv_photo_profil_request);
            btn_request_request = itemView.findViewById(R.id.btn_request_request);
            cv_profil_user_request = itemView.findViewById(R.id.cv_profil_user_request);
        }
    }

}
