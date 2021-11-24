package com.tiesr2confiance.tiers2confiance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class AdapterHobbieItem extends RecyclerView.Adapter<AdapterHobbieItem.ItemViewHolder> {

    private Context context; // point d'accroche
    private ArrayList<ModelUsers> itenArrayList; // rableau qui va comprendre les informations ncessaires

    public AdapterHobbieItem(Context context, ArrayList<ModelUsers> itenArrayList) {
        this.context = context;
        this.itenArrayList = itenArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Accrocher la view
        View view = LayoutInflater.from(context).inflate(R.layout.activity_profil_hobbie_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {


        ModelUsers currentItem = itenArrayList.get(position);

        // Récupérér les datas
        String hobbieName = currentItem.getUs_hobbies();

        holder.getItemId();
        Toast.makeText(context, ""+holder.getItemId(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return itenArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tvhobbieProfilItem;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvhobbieProfilItem = itemView.findViewById(R.id.tvhobbieProfilItem);
        }
    }


}
