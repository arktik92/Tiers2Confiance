package com.tiesr2confiance.tiers2confiance;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentFirstBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CameraFragment extends Fragment {


    /* Camera Setup*/
public static final String EXTRA_INFO="default";



    ImageView imageProfil, mediaContainer;
    LinearLayout llMediaContainer;

    private FragmentFirstBinding binding;


    /**
     * Variable FireBase
     **/
    private FirebaseFirestore db;
    private CollectionReference mediaList;
    private FirebaseStorage storage;
    StorageReference mediaFolder;

    ArrayList<Uri> listLocalImageFile = new ArrayList<>();
    ArrayList<String> savedImageUrl = new ArrayList<>();

    int counter;
    int i = 0;


    private FragmentActivity myContext;

    private static final String TAG = "CAMERA FRAGMENT";

    /**
     * Variable pour les URI des medias
     **/
    Uri localMediaUri, serverMediaUri;

    /**
     * Variable FireBase
     **/



/*
    public void StartActivityFromFragment(Fragment fragment, Intent intent, int code){

              getActivity().startActivityFromFragment(fragment, intent, code);
              if(code == 101){
                  localMediaUri = intent.getData();
                  // Ajout dans le array();
                  addMediaToLinear();

              }
    }


    */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

        /** TODO Camera */
        //return inflater.inflate(R.layout.fragment_camera, container, talse);

    }


    public boolean checkPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            System.out.println("PERMISSION GRANTED");
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            return true;
        } else {

            System.out.println("NO PERMISSION GRANTED");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
            return false;
        }

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        initFireBase();


        if (checkPermission()) {
            addMediaToLinear();
        }


        /** Camera **/

        /*******/


        /*
        int getImage = registerForActivityResult(
                ActivityResultContracts.GetContent(),
                ActivityResultCallback{
            binding.imageView.setImageURI(it);
        }
    )

        */

        /****/


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                /** Ajout de vérification de la permission de parcourir les dossiers du terminal
                 Avant tout chose, il faut ajouter la permission dans le Manifest */


                System.out.println("Fragment");


                openGallery(view);


            }


        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initUI(View view) {
        imageProfil = view.findViewById(R.id.ivProfil);
        llMediaContainer = view.findViewById(R.id.llMediaContainer);
    }


    public void initFireBase() {
        // go inside the storage;
        storage = FirebaseStorage.getInstance();
        // Go to document
        mediaFolder = storage.getReference().child("Médias");

        db = FirebaseFirestore.getInstance();
        mediaList = db.collection("MyMédia");

    }

    public String getFileExtension(Uri uri) {

        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uri));
        return type;
    }


    private void addMediaToLinear() {

        listLocalImageFile.add(localMediaUri);
        if (listLocalImageFile.size() != 0) {
            mediaContainer = new ImageView(myContext.getApplicationContext());
// Params de la taille
            mediaContainer.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            mediaContainer.setPadding(4, 4, 4, 4);
            // TODO Ajouter le clic sur 'limage pour la modifier ou supprimer en appuyant sur l'ID;
            // mediaContainer.setId();

            llMediaContainer.addView(mediaContainer);


            /* Glide image Loader */
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_add_a_photo_24)
                    .placeholder(R.drawable.ic_add_a_photo_24);

            //Glide.with(getActivity().getApplicationContext())
            Glide.with(getParentFragment())
                    .load(listLocalImageFile.get(1))
                    .apply(options)
                    .centerCrop()
                    //  .getDiskCacheStrategy(DiskCacheStrategy.ALL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mediaContainer);
            i++;
        }
    }


    private void saveImageDatatoFireBase(final DialogFragment dialogFragment) { //  ProgressDialog profressDialog
        //  progressDialog.setMessage("Savind uploaded image ....");
        dialogFragment.show(myContext.getSupportFragmentManager(), "Saving Uploaded image ....");
        Map<String, Object> dataMap = new HashMap<>();
        // Below line of code will put your images list as an array in firestore
        dataMap.put("images", savedImageUrl);

        mediaList.add(dataMap)
                .addOnSuccessListener(new OnSuccessListener<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        //  progressDialog.dismiss();
                        dialogFragment.dismiss();

                        //  Log.d(TAG, "Images uploaded and saved successfully");

                        dialogFragment.show(myContext.getSupportFragmentManager(), "Images uploaded and saved successfully");


//                    Toast.makeText(CameraFragment.this, "Images uploaded and saved successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  progressDialog.dismiss();
                        dialogFragment.dismiss();

                        //Log.d(TAG, "Images uploaded but we couldn't save them to the database.");

                        dialogFragment.show(myContext.getSupportFragmentManager(), "Images uploaded but we couldn't save them to the database.");


                        //Toast.makeText(CameraFragment.this, "Images uploaded but we couldn't save them to the database.", Toast.LENGTH_SHORT).show();

                    }
                });

    }


    public void openGallery(View view) {

        final DialogFragment dialogFragment = new DialogFragment();

        if ((listLocalImageFile.size() != 0)) {
            // final ProgressDialog progressDialog = new ProgressDialog(getContext()); // this

            dialogFragment.show(myContext.getSupportFragmentManager(), "Uploadled 0/" + listLocalImageFile.size());
            // progressDialog.setMessage("Uploadled 0/" + listLocalImageFile.size());

            dialogFragment.getDialog().setCancelable(false);
            // progressDialog.setCancelable(false);


            //progressDialog.setCanceledOnTouchOutside(false);
            dialogFragment.getDialog().setCanceledOnTouchOutside(false);


            //progressDialog.show();
            dialogFragment.getDialog().show();

            final StorageReference storageReference = storage.getReference();

            for (int i = 0; i < listLocalImageFile.size(); i++) {
                final int index = 1;
                StorageReference fileReference = mediaFolder.child(System.currentTimeMillis()
                        + "." + getFileExtension(listLocalImageFile.get(1)));


                fileReference.putFile(listLocalImageFile.get(i))
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    fileReference.getDownloadUrl()
                                            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {

                                                    counter++;

                                                    //progressDialog.setCancelMessage("Uploaded" + counter + "/" + listLocalImageFile.size());

                                                    // dialogFragment.getDialog().setCancelMessage("Uploaded" + counter + "/" + listLocalImageFile.size());
                                                    dialogFragment.show(myContext.getSupportFragmentManager(), "Uploaded" + counter + "/" + listLocalImageFile.size());
                                                    dialogFragment.getDialog().setCancelMessage(null);

                                                    if (task.isSuccessful()) {
                                                        savedImageUrl.add(task.getResult().toString());
                                                    } else {
                                                        // this is ti delete the image if the download url is not complete

                                                        storageReference.child("UserImages/").child(listLocalImageFile.get(index).toString()).delete();

                                                        dialogFragment.show(myContext.getSupportFragmentManager(), "Coudn't save");
                                                        //     Toast.makeText(CameraFragment.this, "Coudn't save", Toast.LENGTH_SHORT).show();

                                                    }

                                                    if (counter == listLocalImageFile.size()) {
                                                        //saveImageDatatoFireBase(progressDialog);
                                                        saveImageDatatoFireBase(dialogFragment);
                                                    }
                                                }
                                            });
                                } else {

                                    //    progressDialog.setMessage("Uploaded" + counter + "/" + listLocalImageFile.size());

                                    dialogFragment.show(myContext.getSupportFragmentManager(), "Uploaded" + counter + "/" + listLocalImageFile.size());
                                    counter++;

                                    //Toast.makeText(CameraFragment.this, "couldn't upload" + listLocalImageFile.get(index).toString(), Toast.LENGTH_SHORT).show();
                                    dialogFragment.show(myContext.getSupportFragmentManager(), "couldn't upload" + listLocalImageFile.get(index).toString());

                                }
                            }
                        });
            }
        } else {

            dialogFragment.show(myContext.getSupportFragmentManager(), "Please add some images first");

            //Toast.makeText(this, "Please add some images first", Toast.LENGTH_SHORT).show();
        }
    }


/*********/


/*

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
*/

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


