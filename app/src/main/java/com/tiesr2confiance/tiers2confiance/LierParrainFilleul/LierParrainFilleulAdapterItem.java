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
import com.tiesr2confiance.tiers2confiance.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class LierParrainFilleulAdapterItem extends RecyclerView.Adapter<LierParrainFilleulAdapterItem.ItemViewHolder> implements Filterable {

    private static final String TAG = "Lier P/F ADAPTER :";
    private Context context;
    private ArrayList<ModelUsers> usersArrayList;


    //Two data sources, the original data and filtered data
    private ArrayList<HashMap<String, String>> originalData;
    private ArrayList<HashMap<String, String>> filteredData;

    public LierParrainFilleulAdapterItem(Context context, ArrayList<ModelUsers> usersArrayList){
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.activity_lier_parrain_filleul_item_recycler, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ModelUsers currentUser = usersArrayList.get(position);

        String us_nickname = currentUser.getUs_nickname();
        String us_city = currentUser.getUs_city();

        //Log.e(TAG, us_nickname);

        holder.tv_nickname.setText(us_nickname);
        holder.tv_city.setText(us_city);

        //TODO Inserer la bonne image quand on aura récupérer l'url depuis le model
        // Utilisation de glide pour afficher les images,
        RequestOptions options = new RequestOptions().centerCrop().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher);
        Context context = holder.iv_photo_profil.getContext();
        // Fonctionne soit avec un contexte ou une vue (with), puis avec un load d'url,
        // puis le chargement des options précédemment crées, puis centrer l'image,
        // puis diskCacheStrategy
        // puis l'emplacement où mettre l'image, dans le holder crée.
        Glide.with(context).load(R.mipmap.ic_launcher).apply(options).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_photo_profil);
    }

    @Override
    public int getItemCount( ) {
        return usersArrayList.size();
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
                    ArrayList<HashMap<String,String>> filterResultsData = new ArrayList<HashMap<String,String>>();

                    Log.e(TAG,"ca passe AVANT");
                    for(HashMap<String,String> data : originalData)
                    {

                        Log.e(TAG,"ca passe boucle FOR");
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison,Filleule 1
                        // so you'll need to fill out this conditional
                        if(1==1)
                        {
                            Log.e(TAG,"ca passe BOUCLE");
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredData = (ArrayList<HashMap<String,String>>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nickname, tv_city;
        public ImageView iv_photo_profil;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_city = itemView.findViewById(R.id.tv_city);
            iv_photo_profil = itemView.findViewById(R.id.iv_photo_profil);
        }
    }
}
