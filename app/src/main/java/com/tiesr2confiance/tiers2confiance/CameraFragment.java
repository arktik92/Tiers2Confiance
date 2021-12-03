package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.util.UUID;

public class CameraFragment extends AppCompatActivity {

    ImageView ivProfilImage;
    private Button btnAddPhotoGallery, btnAddCamera;

    public Uri imageUri, imageCameraUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private static final String TAG = "CAMERA";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAMERA_CAPTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ivProfilImage = findViewById(R.id.ivProfilImage);
        btnAddPhotoGallery = findViewById(R.id.btnAddPhotoGallery);
        btnAddCamera = findViewById(R.id.btnAddCamera);

        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();


        btnAddPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });


    }


    private void selectPicture() {

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

        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            uploadPhoto();
        }


        if (requestCode == REQUEST_IMAGE_CAMERA_CAPTURE) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("Data");
            ivProfilImage.setImageBitmap(bitmap);

            /*****/

            Intent phptoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File ImageFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");

            Log.d(TAG, "onActivityResult: " + ImageFolder);


            imageCameraUri = data.getData();

            Log.d(TAG, "onActivityResult: " + imageCameraUri);

            uploadCameraPhoto();


        }
    }


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


    /***** Send from camera ****/


    private void uploadCameraPhoto() {


        Log.d(TAG, "***** UploadPhoto ***** ");



        /****
        final ProgressDialog prDial = new ProgressDialog(this);

        Log.d(TAG, "***** ProgressDialog ***** ");

        prDial.setTitle("Uploading Image...");
        prDial.show();


        ***/

        final String randomKey = UUID.randomUUID().toString();

        // Create the reference to "images/mountain.jpg

        Log.d(TAG, "RandomKey: " + randomKey);




        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");



        //StorageReference riversRef = storageReference.child("images/" + randomKey);

        Log.d(TAG, "imagesFolder: " + imagesFolder);


        File f = new File(String.valueOf(imagesFolder));
        ivProfilImage.setImageURI(Uri.fromFile(f));

        Log.d(TAG, "Uri.fromFile(f): " + Uri.fromFile(f));

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);


        Log.d(TAG, "uploadImageToFireBase >> ");
        uploadImageToFireBase(f.getName(), contentUri);

    }


    public void uploadImageToFireBase(String name, Uri contentUri) {

        StorageReference image = storageReference.child("images/" + name);


        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        Log.d(TAG, "onSuccess: Uploaded Image URI is"+uri.toString());
                    }
                });
                Toast.makeText(CameraFragment.this, "Image is Uploaded", Toast.LENGTH_SHORT).show();
            }
        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CameraFragment.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //images/image.jpg


    public void getCameraPhoto(View view) {
        Log.d(TAG, "GET PHOTO STEP");


        Log.d(TAG, "getPhoto: ");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        // Request for camera runtime permission

        if (ContextCompat.checkSelfPermission(CameraFragment.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraFragment.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        btnAddCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);

            }
        });

    }


}