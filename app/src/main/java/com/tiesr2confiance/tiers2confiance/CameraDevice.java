package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class CameraDevice extends AppCompatActivity {

    ImageView ivProfil;

    private static final String TAG = "CAMERADEVICE";
    Button btnSend;

    static final int REQUEST_IMAGE_CAPTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_device);

        ivProfil = findViewById(R.id.ivProfil);
        btnSend = findViewById(R.id.btnSend);


        checkPermissionDevice();
            getPhoto();

        }


        public void checkPermissionDevice(){


        if (ContextCompat.checkSelfPermission(CameraDevice.this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(CameraDevice.this, new String[]{
                        Manifest.permission.CAMERA
                }, REQUEST_IMAGE_CAPTURE);
            Log.d(TAG, "checkPermissionDevice: FALSE");

        }else{
            Log.d(TAG, "checkPermissionDevice: TRUE");

        }

    }


    public void getPhoto(){
        Log.d(TAG, "getPhoto: ");


        // Request for camera runtime permission

        if(ContextCompat.checkSelfPermission(CameraDevice.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( CameraDevice.this, new String [ ] {
                    Manifest.permission.CAMERA
            }, 100);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);

            }
        });






    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData()!= null){


            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivProfil.setImageBitmap(bitmap);


            /*****
            try{


                Log.d(TAG, "onActivityResult: GET DATA");
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivProfil.setImageBitmap(selectedImage);

            }

            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(CameraDevice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

***/
        }


    }
}


