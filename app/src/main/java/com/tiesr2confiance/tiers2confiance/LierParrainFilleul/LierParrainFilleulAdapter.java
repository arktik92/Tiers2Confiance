package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

public class LierParrainFilleulAdapter extends FirestoreRecyclerAdapter<ModelUsers, LierParrainFilleulAdapter.ItemViewHolder> {

    private static final String TAG = "Lier P/F ADAPTER :";
    private static final String TAGAPP = "LOGAPP";

    // Variable locale
    private OnItemClickListener mOnItemClickListener;

    /** #1 Interface **/
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    public void setOnItemCliclListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

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
                .inflate(R.layout.fragment_lier_parrain_filleul_item_recycler, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ModelUsers model) {

        // R??cup??ration des attributs des utilisateurs de la liste
        String nickname = model.getUs_nickname();
        String city = model.getUs_city();
        String avatar = model.getUs_avatar();
        long role = model.getUs_role();
        String godfather = model.getUs_godfather();
        String nephew = model.getUs_nephews();

        holder.tvNickname.setText(nickname);
        holder.tvCity.setText(city);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        Context context = holder.ivPhotoProfil.getContext();
        Glide.with(context)
                .load(avatar)
                .apply(options)
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPhotoProfil);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNickname, tvCity;
        public ImageView ivPhotoProfil;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNickname = itemView.findViewById(R.id.tv_nickname_match);
            tvCity = itemView.findViewById(R.id.tv_city_match);
            ivPhotoProfil = itemView.findViewById(R.id.iv_photo_profil_match);

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
