package com.tiesr2confiance.tiers2confiance.LierParrainFilleul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiesr2confiance.tiers2confiance.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;

public class LierParrainFilleulAdapterItem extends RecyclerView.Adapter<LierParrainFilleulAdapterItem.ItemViewHolder> {

    private Context context;
    private ArrayList<ModelUsers> usersArrayList;

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

        holder.tv_nickname.setText(us_nickname);
        holder.tv_city.setText(us_city);

        //TODO Insérer Glide quand il faudra ajouter la photo, pour l'instant pas de gestion de imageUrl


    }

    @Override
    public int getItemCount( ) {
        return usersArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nickname, tv_city;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_city = itemView.findViewById(R.id.tv_city);
        }
    }
}
