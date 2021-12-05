package com.tiesr2confiance.tiers2confiance.MatchCibles;

import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulAdapter;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import org.w3c.dom.Text;

public class MatchCiblesAdapter extends FirestoreRecyclerAdapter<ModelUsers, MatchCiblesAdapter.ItemViewHolder > {


    private static final String TAG = "Adapter Match Cibles";


    // Variable locale
    private MatchCiblesAdapter.OnItemClickListener mOnItemClickListener;

    /** #1 Interface **/
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    public void setOnItemCliclListener(MatchCiblesAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MatchCiblesAdapter(@NonNull FirestoreRecyclerOptions<ModelUsers> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchCiblesAdapter.ItemViewHolder holder, int position, @NonNull ModelUsers model) {

       // Log.e(TAG, "onBindViewHolder: " + model.getUs_nickname() );

        // Récupération des attributs des utilisateurs de la liste
        String nickname_match = model.getUs_nickname();
        String city_match = model.getUs_city();
        String avatar_match = model.getUs_avatar();

        holder.tvNicknameMatch.setText(nickname_match);
        holder.tvCityMatch.setText(city_match);


    }

    @NonNull
    @Override
    public MatchCiblesAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_match_cibles_item_recycler, parent, false);
        return new ItemViewHolder(view);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNicknameMatch, tvCityMatch;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNicknameMatch = itemView.findViewById(R.id.tv_nickname_match);
            tvCityMatch = itemView.findViewById(R.id.tv_city_match);

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
