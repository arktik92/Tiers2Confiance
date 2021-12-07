package com.tiesr2confiance.tiers2confiance.Login;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;
import com.tiesr2confiance.tiers2confiance.R;

//TODO supprimer handle

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
//
//
//           if(menuItem.getItemId() == R.id.takePicture){
//               System.out.println("takePicture");
////               getImageLibrary();
//           }else if(menuItem.getItemId() == R.id.takeCameraPicture){
//
////               getCameraPhotoNew();
//           }
//
//
//
//
        return false;

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
