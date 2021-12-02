package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CameraFragment extends AppCompatActivity {

    private ImageView profilPic;
    private Button btnImportPhoto, btnImportCamera;

    public Uri imageUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private static final String TAG = "CAMERA *******";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        profilPic = findViewById(R.id.ivProfilImage);
        btnImportPhoto = findViewById(R.id.btnAddPhoto);
        btnImportCamera = findViewById(R.id.btnAddCamera);

        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();



        btnImportPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });


    }


    private void selectPicture(){

        Log.d(TAG, "***** SelectPicture *******");

        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

     //  Bundle camerabundle = new Bundle();

        cameraIntent.setType("image/*"); // image/jpg

        cameraIntent.putExtra("crop", true);
        cameraIntent.putExtra("scale", true);

        // Output image dim
        cameraIntent.putExtra("outputX", 256);
        cameraIntent.putExtra("outputY", 256);

        // Ratio
        cameraIntent.putExtra("aspectX", 1);
        cameraIntent.putExtra("aspectY", 1);

        cameraIntent.putExtra("return-data", true);

        cameraIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivityForResult(cameraIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!= null){
            imageUri = data.getData();

            uploadPhoto();
        }
    }


    private void uploadPhoto(){

        Log.d(TAG, "***** UploadPhoto ***** ");

        final ProgressDialog prDial = new ProgressDialog(this);

        Log.d(TAG, "***** ProgressDialog ***** ");

        prDial.setTitle("Uploading Image...");
        prDial.show();

        final String randomKey = UUID.randomUUID().toString();

        // Create the reference to "images/mountain.jpg

        Log.d(TAG, "RandomKey: " +randomKey);

        StorageReference riversRef = storageReference.child("images/"+randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        prDial.dismiss();
                        Log.d(TAG, "upload: SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        prDial.dismiss();
                        Log.d(TAG, "upload: FAILED");
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount() );
                        prDial.setMessage("Percentage:"+ (int) progressPercent +"%");
                    }
                });




    }


    public void getCameraPhoto(View view){
        Intent intent = new Intent(CameraFragment.this, CameraDeviceFragment.class);
        startActivity(intent);
        System.out.println("ENTER proccess getCameraPhoto >> ");
    }



}