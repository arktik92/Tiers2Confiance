package com.tiesr2confiance.tiers2confiance;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentFirstBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class CameraFragment extends Fragment {

    ImageView imageProfil;
    LinearLayout llMediaContainer;

    private FragmentFirstBinding binding;


    private FirebaseFirestore db;
    private CollectionReference mediaList;
    private FirebaseStorage storage;
    StorageReference mediaFolder;

    ArrayList<Uri> listLocalImageFile = new ArrayList<>();


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

        initUI(view);
        initFireBase();


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {

                System.out.println("Fragment");


                openGallery(view);


                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);



                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("PERMISSION GRANTED");
                } else {

                    System.out.println("NO PERMISSION GRANTED");
                }

            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initUI(View view){
        imageProfil = view.findViewById(R.id.ivProfil);
        llMediaContainer = view.findViewById(R.id.llMediaContainer);
    }


    public void initFireBase(){
        // go inside the storage;
        storage = FirebaseStorage.getInstance();
        // Go to document
        mediaFolder = storage.getReference().child("Médias");

        db = FirebaseFirestore.getInstance();
        mediaList = db.collection("MyMédia");

    }


    public void openGallery(View view) {


        /***

if((listLocalImageFile.size() != 0)){
    final ProgressDialog progressDialog = new ProgressDialog(getContext()); // this
    progressDialog.setMessage("Uploadled 0/"+listLocalImageFile.size());
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setCancelable(false);
    progressDialog.show();

    final StorageReference storageReference = storage.getReference();

    for(int i =0; i < listLocalImageFile.size();i++){

    }




}

         **/



        System.out.print("OpenGallery");


        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

     //   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

       // if(Manifest.permission.WRITE_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //}

        intent.setType("image/*"); // image/jpg

        intent.putExtra("crop", true);
        intent.putExtra("scale", true);

        // Output image dim
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);

        // Ratio
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("return-data", true);

        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivity(intent);


    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void getCamera(View view) {


        System.out.print("*** GEt CAMERA FUNCTION ****");


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
try{
    startActivity(takePictureIntent);
}
catch(ActivityNotFoundException e){
    System.out.println("Error => "+e);
}

    }


    public static boolean checkPermission(Context context) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            System.out.println("ACCESS_COARSE_LOCATION => ACCES PERMISSION GRANTED ");

        }

        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            System.out.println("WRITE_EXTERNAL_STORAGE => ACCES PERMISSION GRANTED ");
        }

return false;
    }


    /**
     * END CAMERA
     ****/


    public void CaptureFromCamera(View view) {

        System.out.println("get photo");

        /*
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // ACTION_IMAGE_CAPTURE_SECURE
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", File.createTempFile("tmp",".jpg", new File("C:/"))));
            startActivity(intent);
        }
        catch (IOException e){
            System.out.println("error catch => "+e);
        }


         */

    }


}


