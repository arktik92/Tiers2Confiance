package com.tiesr2confiance.tiers2confiance.Profil;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;

public class ViewPhotosAdapter extends RecyclerView.Adapter<ViewPhotosAdapter.MyViewHolder> implements View.OnClickListener{

    private static final String TAG = "ViewPhotoAdaper";
    Context context;
    ArrayList<String> photosList;

    public ViewPhotosAdapter(Context context, ArrayList<String> photosList) {
        this.context = context;
        this.photosList = photosList;
    }

    @NonNull
    @Override
    public ViewPhotosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_view_profil_item_recycler, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPhotosAdapter.MyViewHolder holder, int position) {

        Uri imageUri = Uri.parse(photosList.get(position));
        Glide.with(context)
                .load(imageUri)
                .apply(new RequestOptions().override(1000,1000))
                .into(holder.mediaContainer);
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mediaContainer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaContainer = itemView.findViewById(R.id.mediaContainer);
        }
    }
}
