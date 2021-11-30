package com.tiesr2confiance.tiers2confiance;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentFirstBinding;



public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

              System.out.println("Fragment");


                openGallery();

              //  getCamera();
             
              //  NavHostFragment.findNavController(FirstFragment.this)
               //         .navigate(R.id.action_FirstFragment_to_SecondFragment);

              //  Intent intent = new Intent(getContext(), LierParrainFilleulFragment.class);
//        intent.putExtra("IdUser", snapshot.getId());
             //   startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





    public void openGallery(){

System.out.print("OpenGallery");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

       // Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*"); // image/jpg

        intent.putExtra("crop", true);
        intent.putExtra("scale", true);

        /** Output image dim **/
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);

        /** Ratio **/
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("return-data", true);

        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivity(intent);

    }




    /*
    public void getCamera(){
    // START CAMERA

    static final int RESQUEST_IMAGE_CAPTURE =1;

    private void dispatchTakePictureIntent(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try(
                startActivityForResult(takePictureIntent, RESQUEST_IMAGE_CAPTURE);
                )
            catch(ActivityNotFoundException e){
            System.out.print("camera error "+ e);
        }



    }

    }*/




    /** END CAMERA ****/

}