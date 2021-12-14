package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public PendingRequestsAdapter(@NonNull FirestoreRecyclerOptions<ModelUsers> options, FirebaseUser currentUser) {
        super(options);
        this.currentUser = currentUser;
    }


    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ModelUsers model) {

        // Récupération des attributs des utilisateurs de la liste
        String us_nickname = model.getUs_nickname();
        String us_city = model.getUs_city();
        String avatar_match = model.getUs_avatar();

        holder.tv_nickname_request.setText(us_nickname);
        holder.tv_city_request.setText(us_city);

        //String str = String.format("%tc", us_birth_day);

        // Récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = usersCollectionRef.document(currentUser.getUid());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        Context context = holder.iv_photo_profil_request.getContext();
        Glide.with(context)
                .load(avatar_match)
                .apply(options)
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_photo_profil_request);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_pending_requests_item_recycler, parent, false);
        return new PendingRequestsAdapter.ItemViewHolder(view);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nickname_request, tv_city_request;
        public ImageView iv_photo_profil_request;
        public CardView cv_profil_user_request;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nickname_request = itemView.findViewById(R.id.tv_nickname_request);
            tv_city_request = itemView.findViewById(R.id.tv_city_request);
            iv_photo_profil_request = itemView.findViewById(R.id.iv_photo_profil_request);
            cv_profil_user_request = itemView.findViewById(R.id.cv_profil_user_request);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && mOnItemClickListener != null){
                        DocumentSnapshot userSnapshot = getSnapshots().getSnapshot(position);
                        mOnItemClickListener.onItemClick(userSnapshot, position);
                    }
                }
            });
        }
    }

}
