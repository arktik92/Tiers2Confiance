package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

   // Bitmap photo;

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

       /* cameraIntent.putExtra("crop", true);
        cameraIntent.putExtra("scale", true);

        // Output image dim
        cameraIntent.putExtra("outputX", 256);
        cameraIntent.putExtra("outputY", 256);
*/
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


        if (requestCode == REQUEST_IMAGE_CAMERA_CAPTURE && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivProfilImage.setImageBitmap(bitmap);

            Log.d(TAG, "REQUEST_IMAGE_CAMERA_CAPTURE >> ");

            imageCameraUri =  data.getData(); // Bitmap  data.getExtras().get("Data");

            ivProfilImage.setImageURI(imageCameraUri);

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


    /***
    private void uploadCameraPhoto() {
        Toast.makeText(CameraFragment.this, "uploadCameraPhoto", Toast.LENGTH_SHORT).show();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        photo.compress(Bitmap.CompressFormat.JPEG,100, stream);

        byte[] b = stream.toByteArray();

        StorageReference storageReference =  FirebaseStorage.getInstance().getReference().child("documentImages").child("noplateImg");

        Toast.makeText(CameraFragment.this, "storageReference"+storageReference, Toast.LENGTH_SHORT).show();

        storageReference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloadUrI = taskSnapshot.getUploadSessionUri();

                Toast.makeText(CameraFragment.this, "downloadUrI"+downloadUrI, Toast.LENGTH_SHORT).show();

                Toast.makeText(CameraFragment.this, "uploaded", Toast.LENGTH_SHORT).show();
            }
        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CameraFragment.this, "FAiled", Toast.LENGTH_SHORT).show();
                    }
                });


    }

**/
    private void uploadCameraPhoto() {

        // Create a storage reference from our app
        StorageReference storageRef = storageReference.getStorage().getReference();

        // Create a reference to file
       // StorageReference mountainsRef = storageRef.child("toto.jpg");

        //Create a reference to "images/toto.jpg"
        StorageReference mountainsImagesRef = storageRef.child("imagess/toto.jpg");

        // while the file names are the same, the reference poinr to different ilfes
     //   mountainRef.getName().equals(mountainImagesRef.getName()); // true
       // mountainRef.getPath().equals(mountainImagesRef.getPath()); // false

        Toast.makeText(CameraFragment.this, "uploadCameraPhoto", Toast.LENGTH_SHORT).show();



        ivProfilImage.setDrawingCacheEnabled(true);
        ivProfilImage.buildDrawingCache();


        Bitmap bitmap = ((BitmapDrawable) ivProfilImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);


        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CameraFragment.this, "Handle Unsucessful uploads", Toast.LENGTH_SHORT).show();
            }
        })

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CameraFragment.this, "TaskSnapshot Successful", Toast.LENGTH_SHORT).show();
                    }
                });

        /***

        Log.d(TAG, "***** UploadPhoto ***** ");



        final String randomKey = UUID.randomUUID().toString();

        // Create the reference to "images/mountain.jpg

        Log.d(TAG, "RandomKey: " + randomKey);




        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "DCIM");

        Log.d(TAG, "uploadCameraPhoto: "+imagesFolder);



        final ProgressDialog prDial = new ProgressDialog(this);

        Log.d(TAG, "***** ProgressDialog ***** ");

        prDial.setTitle("Uploading Image...");
        prDial.show();

        // Create the reference to "images/mountain.jpg

        Log.d(TAG, "RandomKey: " + randomKey);


        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(imageCameraUri)
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





/**
        //StorageReference riversRef = storageReference.child("images/" + randomKey);

        Log.d(TAG, "imagesFolder: " + imagesFolder);


        File f = new File(String.valueOf(imagesFolder));
        ivProfilImage.setImageURI(Uri.fromFile(f));

        Log.d(TAG, "Uri.fromFile(f): " + Uri.fromFile(f));

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);


        Log.d(TAG, "uploadImageToFireBase >> ");**/
       // uploadImageToFireBase(f.getName(), contentUri);



    }


   /** public void uploadImageToFireBase(String name, Uri contentUri) {


        /*
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
    }*/

    //images/image.jpg


    public void getCameraPhoto(View view) {
        Log.d(TAG, "GET PHOTO STEP");



        // Request for camera runtime permission

        if (ContextCompat.checkSelfPermission(CameraFragment.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraFragment.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_IMAGE_CAMERA_CAPTURE);
        }else{
            Log.d(TAG, "getPhoto: ");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA_CAPTURE);
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