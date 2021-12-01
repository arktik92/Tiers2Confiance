package com.tiesr2confiance.tiers2confiance;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CameraRecyclerAdapter extends RecyclerView.Adapter<CameraRecyclerAdapter.MyViewHolder> implements View.OnClickListener { //RecyclerView.Adapter<CameraRecyclerAdapter.MyViewHolder>

    Context context;
    ArrayList<String> mediaList;
    private CameraFragment binding;


    public CameraRecyclerAdapter(Context context, ArrayList<String> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
    }

@NonNull
@Override
    public CameraRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.gallery_item_image, parent, false);
    return new MyViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
      //  super.onBindViewHolder(holder, position, payloads);

        Uri imageUrl = Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-example-c7dcb.appspot.com/o/Medias%2F1624743661357.jpg?alt=media&token=e0283dcd-8b18-4c59-8a70-64f0eccb50c7");
        if(!imageUrl.equals(""))
            binding.imageProfil.setImageURI(Uri.parse(String.valueOf(imageUrl)));

    }



    public int getItemCount(){
    //return mediaList.size();
    return 1;
    }



    @Override
    public void onClick(View view) {

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView mediaContainer;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        mediaContainer = itemView.findViewById(R.id.mediaContainer);
    }

}




}
