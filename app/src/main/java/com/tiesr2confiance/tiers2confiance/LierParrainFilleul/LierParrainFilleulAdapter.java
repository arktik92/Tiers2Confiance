package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.Date;

public class LierParrainFilleulAdapter extends FirestoreRecyclerAdapter<ModelUsers, LierParrainFilleulAdapter.ItemViewHolder> implements Filterable {

    private static final String TAG = "Lier P/F ADAPTER :";
    private Context context;
    private ArrayList<ModelUsers> usersArrayList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Two data sources, the original data and filtered data
    private ArrayList<ModelUsers> originalData;
    private ArrayList<ModelUsers> filteredData;

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
    }


    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = originalData;
                    results.count = originalData.size();
                }
                else
                {
                    ArrayList<ModelUsers> filterResultsData = new ArrayList<>();

                    for(ModelUsers data : originalData)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison,Filleule 1
                        // so you'll need to fill out this conditional
                        if(data.us_nickname == "Filleule 1")
                        {
                            Log.e(TAG, "ca passe Condition");
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                    Log.e(TAG, "ca passe avec " + results.count);
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredData = (ArrayList<ModelUsers>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nickname, tv_city, tv_birth_day;
        public ImageView iv_photo_profil;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_birth_day = itemView.findViewById(R.id.tv_birth_day);
            iv_photo_profil = itemView.findViewById(R.id.iv_photo_profil);

        }
    }



}
