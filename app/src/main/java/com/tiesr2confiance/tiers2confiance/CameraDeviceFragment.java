package com.tiesr2confiance.tiers2confiance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CameraDeviceFragment extends AppCompatActivity {

    /*** var global ****/


    private static final String TAG = "* CAMERA DEVICE * ";

    private static final int REQUEST_ID_IMAGE_CAPTURE = 1888;
    static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;


    public Uri imageUri;

    private ImageView imageView;

    private  Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_device_fragment);


        imageView = findViewById(R.id.ivAvatar);

        checkCamera(CameraDeviceFragment.this);



     //  if (checkAndRequestPermissions(CameraDeviceFragment.this))
            getPhoto(CameraDeviceFragment.this);




    }


    public boolean checkCamera(Context context){

        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Log.d(TAG, "checkCamera: this device has camera");
            return true;

        }else{
            // this device has no camera

            Log.d(TAG, "checkCamera: this device has no camera");
            return false;
        }

    }

    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtStorePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int CameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

        List<String> listPermissionsNeeded = new ArrayList<>();


        /* Check Camera Permission */
        if (CameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.CAMERA);
            Log.d(TAG, "checkAndRequestPermissions: Missing CAMERA PERMISSION");
        }

        if (WExtStorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.d(TAG, "checkAndRequestPermissions: Missing WRITE_EXTERNAL_STORAGE PERMISSION");
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);

            Log.d(TAG, "checkAndRequestPermissions FALSE : Missing PERMISSION ");

            return false;
        }
        Log.d(TAG, "checkAndRequestPermissions TRUE : Missing PERMISSION ");
        return true;


    }


    /** handle your permission result **/

    public void setRequestPermissionsResult(int requestCode, String[] permissions, int [] grantResults){

        switch (requestCode){
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if(ContextCompat.checkSelfPermission(CameraDeviceFragment.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Flagup Requires Acces to Camera", Toast.LENGTH_SHORT).show();
                }else if(ContextCompat.checkSelfPermission(CameraDeviceFragment.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Flagup Requires Acces to Your Storage", Toast.LENGTH_SHORT).show();
                }else{
                    getPhoto(CameraDeviceFragment.this);
                }
                break;
        }

    }


    /***

    public void getPhoto() {

        ivAvatar = findViewById(R.id.ivAvatar);

        Intent cameraImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ImageView imageView;

        try {
            Log.d(TAG, "Activity CameraImage =>");

            cameraImage.setType("image/*"); // image/jpg

            cameraImage.putExtra("crop", true);
            cameraImage.putExtra("scale", true);

            // Output image dim
            cameraImage.putExtra("outputX", 256);
            cameraImage.putExtra("outputY", 256);

            // Ratio
            cameraImage.putExtra("aspectX", 1);
            cameraImage.putExtra("aspectY", 1);

            cameraImage.putExtra("return-data", true);

            cameraImage.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

            startActivityForResult(cameraImage, 1);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "Activity Album Exception: " + e);
        }

    }

     ***/

    private void getPhoto(Context context){

        Log.d(TAG, "***** GetPhoto ****** ");



        final CharSequence[] optionMenu ={"Take a photo", "Choose From Gallery", "Exit"};

        /*** create a MenuOption Array ***/

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // set the items in builder

        builder.setItems(optionMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionMenu[i].equals("Take photo")){
                    // Open the camera and get the photo

                    try{     Log.d(TAG, "onClick: TAKE PICTURE");



                        /****
                        Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takepicture, REQUEST_ID_IMAGE_CAPTURE);


                        ***/
// Camera Intent
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE,"New Picture");
                        Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);


                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePicture, REQUEST_ID_IMAGE_CAPTURE);


                    }
                    catch (ActivityNotFoundException e){
                        Log.d(TAG, "TAKE PICTURE ActivityNotFoundException " +e);
                    }



                }
                else if(optionMenu[i].equals("Choose from Gallery")){
                    // Choose from the external Storaga

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    pickPhoto.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                    startActivityForResult(pickPhoto, REQUEST_ID_IMAGE_CAPTURE);


                    Log.d(TAG, "onClick: GALLERY EXTRA_LOCAL_ONLY");
                }else if(optionMenu[i].equals("Exit")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ID_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData()!= null){
            imageUri = data.getData();

            Log.d(TAG, "REQUEST_ID_IMAGE_CAPTURE: "+ REQUEST_ID_IMAGE_CAPTURE);
            uploadPhoto();

        }




        /***
         if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
         Bundle extras = data.getExtras();
         Bitmap imageBitmap = (Bitmap) extras.get("data");

         // imageView.setImageBitmap(imageBitmap);
         }
         ***/



        /****

        if (requestCode != RESULT_CANCELED) {
            switch (requestCode) {

                case 0:
                    if (resultCode == RESULT_OK && data != null) {

                        Bundle extras = data.getExtras();
                        Bitmap selectedImage = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(selectedImage);

                        Log.d(TAG, "onActivityResult: data selectImage");
                    }

                    break;

                case 1:
                   // if (resultCode == RESULT_OK && data != null) {

                    if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor =
                                    getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }

                    Log.d(TAG, "onActivityResult: data selectImage");

            }
        }


        ****/

    }



    private void uploadPhoto() {

        Log.d(TAG, "***** UploadPhoto ***** ");
    }



}