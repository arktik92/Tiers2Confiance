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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;


/*****/


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

    String fileName = "toto.jpg";

    private void uploadCameraPhoto() {


        final ProgressDialog prDial = new ProgressDialog(this);

        Log.d(TAG, "***** uploadCameraPhoto ***** ");

        prDial.setTitle("Uploading Image...");
        prDial.show();


        // Create a storage reference from our app
        StorageReference storageRef = storageReference.getStorage().getReference();

        // Create a reference to file
       // StorageReference mountainsRef = storageRef.child("toto.jpg");

        //Create a reference to "images/toto.jpg"
        StorageReference mountainsImagesRef = storageRef.child("camera/"+fileName);



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
                prDial.dismiss();
            }
        })

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CameraFragment.this, "TaskSnapshot Successful", Toast.LENGTH_SHORT).show();
                  prDial.dismiss();
                        Log.d(TAG, "FILENAME DONE "+fileName);
                  uploadProfilFireBase(new File(fileName));
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

private static final String US_AVATAR ="us_avatar";


    private FirebaseFirestore db;
    private DocumentReference Documentref;
    private  CollectionReference collectionReference;

    public void uploadProfilFireBase(File file){



    /**** Upload the picture on the FireBase ***/

    Log.d(TAG, "UPLOADPROFIL FILE : "+file);

/** BDD connexion */

db = FirebaseFirestore.getInstance();
        Documentref = db.document("users");

        Documentref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(CameraFragment.this, "Erreur lors du chargement", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, error.toString());
                    return; // pour pas que l'application plante
                }

                if(value.exists()){
                    String us_avatar = value.getString("us_avatar");
                    Log.d(TAG, "Value exist: ");
                }



            }
        });


                /***
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {

                        if(documentSnapshot.exists()){
                            String us_avatar = documentSnapshot.getString("us_avatar");
                            Log.d(TAG, "onSuccess: us_avatar "+us_avatar);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: us_avatar");
                    }
                });

**/

}

}