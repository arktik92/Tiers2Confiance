package com.tiesr2confiance.tiers2confiance.Login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.net.InternetDomainName;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tiesr2confiance.tiers2confiance.CameraFragment;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.Profil.ViewProfilFragment;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.UUID;


public class PopupAddAvatarMenuEventHandle extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "POPUP MENU";
    public Uri imageUri, imageCameraUri;;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAMERA_CAPTURE = 100;
    private StorageReference storageReference;

    private Context context;

    public PopupAddAvatarMenuEventHandle(Context context) {
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {


           if(menuItem.getItemId() == R.id.takePicture){
               Log.d(TAG, "takePicture: ");
            // selectPicture();
           }else if(menuItem.getItemId() == R.id.takeCameraPicture){

               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              startActivityForResult(intent, 100);
               Log.d(TAG, "takeCameraPicture: ");
           }




        return false;
    }

    private void selectPicture(){

        Log.d(TAG, "***** SelectPicture *******");

        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivity(cameraIntent);
        /****
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        cameraIntent.putExtra("aspectX", 1);
        cameraIntent.putExtra("aspectY", 1);

        cameraIntent.putExtra("return-data", true);

        cameraIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

        **/

        Log.d(TAG, "***** END SelectPicture *******");

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Log.d(TAG, "onActivityResult: DATA ");
           // uploadPhoto();
        }


        if (requestCode == REQUEST_IMAGE_CAMERA_CAPTURE && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            ImageView imgAvatar = findViewById(R.id.imgAvatar);

            imgAvatar.setImageBitmap(bitmap);

            Log.d(TAG, "REQUEST_IMAGE_CAMERA_CAPTURE >> ");

            imageCameraUri =  data.getData(); // Bitmap  data.getExtras().get("Data");

            imgAvatar.setImageURI(imageCameraUri);

            //imageCameraUri = data.getData();

            Log.d(TAG, "imageCameraUri >> "+imageCameraUri);

            /**
             Bitmap bitmap = (Bitmap) data.getExtras().get("Data");
             ivProfilImage.setImageBitmap(bitmap);


             Intent phptoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

             File ImageFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");

             Log.d(TAG, "onActivityResult: " + ImageFolder);


             imageCameraUri = data.getData();

             Log.d(TAG, "onActivityResult: " + imageCameraUri);*/

         //   uploadCameraPhoto();


        }
    }

    private void uploadPhoto(){
        Log.d(TAG, "uploadPhoto: saasasasasasasasasasass");
    }
    
    
/***
    private void uploadPhoto() {

        Log.d(TAG, "***** UploadPhoto ***** ");

        final ProgressDialog prDial = new ProgressDialog(this);

        Log.d(TAG, "***** ProgressDialog ***** ");

        prDial.setTitle("Uploading Image...");
        prDial.show();

        final String randomKey = UUID.randomUUID().toString();

        // Create the reference to "images/mountain.jpg

        Log.d(TAG, "RandomKey: " + randomKey);


        StorageReference riversRef = storageReference.child("images/" + randomKey);

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
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        prDial.setMessage("Percentage:" + (int) progressPercent + "%");
                    }
                });


    }
    
    
    **/


    public void uploadCameraPhoto(View view) {
        Log.d(TAG, "GET PHOTO STEP");



        // Request for camera runtime permission

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_IMAGE_CAMERA_CAPTURE);
        }else{
            Log.d(TAG, "getPhoto: ");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA_CAPTURE);
        }


        /***
        btnAddCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);

            }
        });**/

    }



}
