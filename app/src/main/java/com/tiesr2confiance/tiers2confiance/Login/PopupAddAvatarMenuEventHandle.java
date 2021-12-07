package com.tiesr2confiance.tiers2confiance.Login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;
import com.tiesr2confiance.tiers2confiance.R;


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
             //  System.out.println("takePicture");
               getImageLibrary();
           }else if(menuItem.getItemId() == R.id.takeCameraPicture){

             //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             // startActivityForResult(intent, 100);
             //  System.out.println("takeCameraPicture");
           }




        return false;
    }


public void getImageLibrary(){
        System.out.println(">> getImageLibrary");


    Log.d(TAG, "***** SelectPicture *******");

   final Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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

    final Intent intent = cameraIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
System.out.println("imageUri"+imageUri);
           // uploadPhoto();
        }
    }


}
