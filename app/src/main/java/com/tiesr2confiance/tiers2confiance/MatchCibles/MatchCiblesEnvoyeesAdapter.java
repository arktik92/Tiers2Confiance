package com.tiesr2confiance.tiers2confiance.MatchCibles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

public class MatchCiblesEnvoyeesAdapter extends FirestoreRecyclerAdapter<ModelUsers, MatchCiblesEnvoyeesAdapter.ItemViewHolder > {


    private static final String TAG = "Adapter Match Cibles Envoyees";


    // Variable locale
    private MatchCiblesEnvoyeesAdapter.OnItemClickListener mOnItemClickListener;

    /** #1 Interface **/
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    public void setOnItemCliclListener(MatchCiblesEnvoyeesAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MatchCiblesEnvoyeesAdapter(@NonNull FirestoreRecyclerOptions<ModelUsers> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchCiblesEnvoyeesAdapter.ItemViewHolder holder, int position, @NonNull ModelUsers model) {

       // Log.e(TAG, "onBindViewHolder: " + model.getUs_nickname() );

        // Récupération des attributs des utilisateurs de la liste
        String nickname_match = model.getUs_nickname();
        String city_match = model.getUs_city();
        String avatar_match = model.getUs_avatar();

        holder.tvNicknameMatch.setText(nickname_match);
        holder.tvCityMatch.setText(city_match);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        Context context = holder.ivPhotoProfilMatch.getContext();
        Glide.with(context)
                .load(avatar_match)
                .apply(options)
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPhotoProfilMatch);

    }

    @NonNull
    @Override
    public MatchCiblesEnvoyeesAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_match_cibles_item_recycler, parent, false);
        return new ItemViewHolder(view);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNicknameMatch, tvCityMatch;
        private ImageView ivPhotoProfilMatch;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNicknameMatch = itemView.findViewById(R.id.tv_nickname_match);
            tvCityMatch = itemView.findViewById(R.id.tv_city_match);
            ivPhotoProfilMatch = itemView.findViewById(R.id.iv_photo_profil_match);

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
