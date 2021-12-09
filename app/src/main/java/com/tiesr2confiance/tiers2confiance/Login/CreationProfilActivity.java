package com.tiesr2confiance.tiers2confiance.Login;

import static android.graphics.Color.TRANSPARENT;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_FS_COLLECTION;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.DocumentCollections;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tiesr2confiance.tiers2confiance.Common.PGO.UserFragment;
import com.tiesr2confiance.tiers2confiance.Crediter.CreditFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulFragment;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsFragment;
import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.Profil.ViewProfilFragment;
import com.tiesr2confiance.tiers2confiance.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CreationProfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /** Variables globales **/
    private static int REQUEST_IMAGE_CAPTURE = 1;
    private static int REQUEST_IMAGE_CAMERA_CAPTURE = 100;
    private static final String TAG = "CreationProfilActivity";
    private static final String TAGAPP = "LOGAPP";

    // Variable Widgets
    /** Variable Widgets **/
    private ImageView imgAvatar;
    public Uri imageUri, imageCameraUri;;
    private EditText etLastName, etFistName, etNickName, etCity, etZipCode;
    private TextView tvDateOfBirth;
    private RadioGroup radioGroupGenre;
    private RadioButton rbHomme, rbFemme;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    /** ID Document To Displayed **/
   // private String userId;
    private DocumentReference userDisplayed;


    /** ID Document Connected **/
    private FirebaseUser currentUser;
    private DocumentReference userConnected;



    /** Variable du code **/
    private Timestamp currentDate, registeredDate, timestamp;
    public  long role;
    private long  genre,  balance, sexualOrientation, maritalStatus, hasKids, height, shape, ethnicGroup,hairColor,
            hairLength, eyeColor, smoker;

    private String hobbies,
            lastName,
            firstName,
            nickName,
            dateOfBirth,
            zipCode,
            city,
            userId,
            userEmail,
            nephewsRequestTo,
            nephewsRequestfrom,
            nephews,
            godfatherRequestTo,
            godfatherRequestFrom,
            godfather,
            image,
            country,
            presentation,
            profession,
            personality,
            sports,
            urlImage,
            photos,
            uriPath,
            matchsRequestFrom,
            matchsRequestTo,
            matchs;

 private String avatar;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    /**
     * Variable Firebase Auth
     **/
    FirebaseUser user;

    /**
     * Variables Firestore
     **/
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private CollectionReference collectionReference;

    /**
     * Initialisation des composants
     **/
    public void init() {

        imgAvatar = findViewById(R.id.imgAvatar);
        etLastName = findViewById(R.id.et_creation_nom);
        etFistName = findViewById(R.id.et_creation_prenom);
        etNickName = findViewById(R.id.et_creation_pseudo);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        etZipCode = findViewById(R.id.et_creation_code_postal);
        etCity = findViewById(R.id.et_creation_ville);
        radioGroupGenre =findViewById(R.id.radio_group_genre);
        rbHomme = findViewById(R.id.rb_homme);
        rbFemme = findViewById(R.id.rb_femme);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);


        // Init des composants Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        db = FirebaseFirestore.getInstance();
        docRef = db.document("users/" + userId);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();
        role    =   globalVariables.getUserRole();

        Log.i(TAGAPP, "CreationProfilActivity onCreate role : " + role);

        // Définition de la contentView en fonction du rôle

        switch((int) role) {
            case 1:
                setContentView(R.layout.activity_creation_profil_celibataire);
                break;
            case 2:
                setContentView(R.layout.activity_creation_profil_parrain);
                break;
            default:
                setContentView(R.layout.activity_creation_profil_parrain);
                break;
        }

        // Rappel de la méthode init
        init();


        /** Méthode OnClickListener du Date Picker **/
        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal;
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreationProfilActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (DatePickerDialog.OnDateSetListener) mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateOfBirth = dayOfMonth + "-" + month + "-" + year;
                tvDateOfBirth.setText(dateOfBirth);
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = format.parse(dateOfBirth);
                    timestamp = new Timestamp(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
        // Inflate the menu; tjos adds items to the action bar if it is present

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    /** Méthode de création de l'utilisateur  **/
    public void creationUser(View view) {

        // Implémentation des variables qui vont etre envoyé sur la Database
        userEmail = user.getEmail().trim();
        city = etCity.getText().toString().trim();
        firstName = etFistName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();
        nickName = etNickName.getText().toString().trim();
        zipCode = etZipCode.getText().toString();
        nephewsRequestTo = "";
        nephewsRequestfrom = "";
        nephews = "";
        godfatherRequestTo = "";
        godfatherRequestFrom = "";
        image = "";
        godfather = "";
        hobbies = "";
        currentDate = Timestamp.now();
        balance = 0;
        country = "FR";
        presentation = "";
        sexualOrientation = 1;
        maritalStatus = 1;
        hasKids = 0;
        profession = "";
        height = 0;
        shape = 1;
        ethnicGroup = 1;
        hairColor = 1;
        hairLength = 1;
        eyeColor = 1;
        smoker = 1;
        personality = "";
        sports = "";

        photos = "";
        matchs = "";
        matchsRequestFrom = "";
        matchsRequestTo = "";

        // Méthode de la date de dernière connection
        if (registeredDate == null) {
            registeredDate = Timestamp.now();
        } else {
            registeredDate = registeredDate;
        }

        // Création d'un objet pour envoyer sur la Database
        Map<String, Object> userList = new HashMap<>();
        userList.put("us_auth_uid", userId);
        userList.put("us_nickname", nickName);
        userList.put("us_email", userEmail);
        userList.put("us_first_name", firstName);
        userList.put("us_last_name", lastName);
        userList.put("us_role", role);
        userList.put("us_balance", balance);
        userList.put("us_nephews", nephews);
        userList.put("us_nephews_request_from", nephewsRequestfrom);
        userList.put("us_nephews_request_to", nephewsRequestTo);
        userList.put("us_godfather", godfather);
        userList.put("us_godfather_request_from", godfatherRequestFrom);
        userList.put("us_godfather_request_to", godfatherRequestTo);
        userList.put("us_photos", photos);
        userList.put("us_birth_date", timestamp);
        userList.put("us_country_lang", country);
        userList.put("us_postal_code", Long.parseLong(zipCode));
        userList.put("us_city", city);
        userList.put("us_city_lowercase", city.toLowerCase());
        userList.put("us_presentation", presentation);
        userList.put("us_gender", genre);
        userList.put("us_sexual_orientation", sexualOrientation);
        userList.put("us_marital_status", maritalStatus);
        userList.put("us_has_kids", hasKids);
        userList.put("us_profession", profession);
        userList.put("us_height", height);
        userList.put("us_shape", shape);
        userList.put("us_ethnic_group", ethnicGroup);
        userList.put("us_hair_color", hairColor);
        userList.put("us_hair_length", hairLength);
        userList.put("us_eye_color", eyeColor);
        userList.put("us_smoker", smoker);
        userList.put("us_personality", personality);
        userList.put("us_sports", sports);
        userList.put("us_hobbies", hobbies);
        userList.put("us_avatar", avatar);
        userList.put("us_registered_date", registeredDate);
        userList.put("us_last_connexion_date", currentDate);
        userList.put("us_match_request_from", matchsRequestFrom);
        userList.put("us_match_request_to", matchsRequestTo);
        userList.put("us_match", matchs);



        // Envoi de l'objet sur la Database
        docRef.set(userList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreationProfilActivity.this, "Profil crée", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Profil crée");
                        startActivity(new Intent(CreationProfilActivity.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreationProfilActivity.this, "Erreur dans la creation du profil", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: ", e );
                    }
                });

    }


    public void radioButtonGender(View view) {

        boolean checked = ((RadioButton)view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_homme:
                if(checked)
                    genre = 1;
                break;
            case R.id.rb_femme:
                if(checked)
                    genre = 2;
                break;
        }

    }


    /**
     * upload picture
     **/

    public void showGetPhoto(View view) {
        Log.d(TAG, "showGetPhoto ");

        PopupMenu popMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popMenu.getMenuInflater();

        // call Inflater Menu
        menuInflater.inflate(R.menu.menu_add_avatar,popMenu.getMenu());

        // Add Menu Event
//         PopupAddAvatarMenuEventHandle popupAddAvatarMenuEventHandle = new PopupAddAvatarMenuEventHandle(getApplicationContext());
//        popMenu.setOnMenuItemClickListener(popupAddAvatarMenuEventHandle);

        popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                // En fonction du résultat, lancement de l'action appropriée
                if (id == R.id.takeCameraPicture) {
                    getCameraPhotoNew();
                } else if (id == R.id.takePicture) {
                    getImageLibrary();
                }
                return false;

            }
        });
        // Show Popup menu
        popMenu.show();




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
            imgAvatar.setImageBitmap(bitmap);

            Log.d(TAG, "REQUEST_IMAGE_CAMERA_CAPTURE >> ");

            imageCameraUri =  data.getData(); // Bitmap  data.getExtras().get("Data");

            imgAvatar.setImageURI(imageCameraUri);

            //imageCameraUri = data.getData();

            Log.d(TAG, "imageCameraUri >> "+imageCameraUri);


            uploadCameraPhotoNew();


        }
    }




    String fileName = "toto.jpg";

    private void uploadCameraPhotoNew() {


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

        Toast.makeText(CreationProfilActivity.this, "uploadCameraPhoto", Toast.LENGTH_SHORT).show();



        imgAvatar.setDrawingCacheEnabled(true);
        imgAvatar.buildDrawingCache();


        Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);


        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreationProfilActivity.this, "Handle Unsucessful uploads", Toast.LENGTH_SHORT).show();
                prDial.dismiss();
            }
        })

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CreationProfilActivity.this, "TaskSnapshot Successful", Toast.LENGTH_SHORT).show();
                        prDial.dismiss();
                        System.out.println("FILENAME DONE "+fileName);

                        uploadProfilFireBase(fileName);
                    }
                })

                .addOnProgressListener(new com.google.firebase.storage.OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        prDial.setMessage("Percentage:" + (int) progressPercent + "%");

                    }
                });



    }

//    public void clickCameraButton() {
//        btnAddPhotoCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getCameraPhotoNew();
//            }
//        });
//    }

    public void getCameraPhotoNew() {
        Log.d(TAG, "GET PHOTO STEP");



        // Request for camera runtime permission

        if (ContextCompat.checkSelfPermission(CreationProfilActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreationProfilActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_IMAGE_CAMERA_CAPTURE);
        }else{
            Log.d(TAG, "getPhoto: ");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA_CAPTURE);
        }
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

        cameraIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }
    private void uploadPhoto() {

        Log.d(TAG, " UploadPhoto  ");

        final ProgressDialog prDial = new ProgressDialog(this);

        Log.d(TAG, " ProgressDialog  ");

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
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(@NonNull Uri uri) {
                                Log.d(TAG, "#####################################+" + uri);
//                                imageUri = uri;
                            }
                        });

                        imgAvatar.setImageURI(imageUri);

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
                .addOnProgressListener(new com.google.firebase.storage.OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        prDial.setMessage("Percentage:" + (int) progressPercent + "%");
                    }
                });
    }

    private void uploadCameraPhoto() {


        final ProgressDialog prDial = new ProgressDialog(this);

        Log.d(TAG, " uploadCameraPhoto  ");

        prDial.setTitle("Uploading Image...");
        prDial.show();


        // Create a storage reference from our app
        StorageReference storageRef = storageReference.getStorage().getReference();

        // Create a reference to file
        // StorageReference mountainsRef = storageRef.child("toto.jpg");

        //Create a reference to "images/toto.jpg"
        StorageReference mountainsImagesRef = storageRef.child("camera/"+fileName);

       urlImage = "gs://tiers2confiance-21525.appspot.com/images/" + fileName;
        uriPath = Uri.parse(urlImage).toString();


        // while the file names are the same, the reference poinr to different ilfes
        //   mountainRef.getName().equals(mountainImagesRef.getName()); // true
        // mountainRef.getPath().equals(mountainImagesRef.getPath()); // false

        Toast.makeText(CreationProfilActivity.this, "uploadCameraPhoto", Toast.LENGTH_SHORT).show();
        imgAvatar.setDrawingCacheEnabled(true);
        imgAvatar.buildDrawingCache();


        Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);


        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreationProfilActivity.this, "Handle Unsucessful uploads", Toast.LENGTH_SHORT).show();
                prDial.dismiss();
            }
        })

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CreationProfilActivity.this, "TaskSnapshot Successful", Toast.LENGTH_SHORT).show();
                        prDial.dismiss();
                        Log.d(TAG, "FILENAME DONE "+fileName);
                        uploadProfilFireBase(fileName);
                    }
                })
                .addOnProgressListener(new com.google.firebase.storage.OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        prDial.setMessage("Percentage:" + (int) progressPercent + "%");
                    }
                });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.takePicture:
               // getImageLibrary();
                uploadPhoto();

                break;

            case R.id.takeCameraPicture:
//                getCameraPhotoNew();
                String fileName = "toto.jpg";
                uploadCameraPhoto();

                break;

        }


        return true;
    }


/****/


    public void uploadProfilFireBase(String fileUri){

        avatar = fileUri;
        Log.d(TAG, "++++++++++++++ uploadProfilFireBase: " + avatar);

        System.out.println("FireBase >> "+ fileUri);

        // currentUser, récupération de l'utilisateur connecté
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userConnected = db.collection(KEY_FS_COLLECTION).document(currentUser.getUid());

        System.out.println("currentUser"+currentUser);
        System.out.println("userConnected"+userConnected);


        userConnected.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    System.out.println("Document Snapshot exist");


                    // Création d'un objet pour envoyer sur la Database
                    Map<String, Object> userList = new HashMap<>();
                    userList.put("us_avatar", "gs://tiers2confiance-21525.appspot.com/camera/"+fileUri);

                    // Envoi de l'objet sur la Database
                    docRef.set(userList)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CreationProfilActivity.this, "Document Exist : Profil Photo crée", Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "Document Exist PHOTO profil crée");
                                  //  startActivity(new Intent(CreationProfilActivity.this, MainActivity.class));

                                    System.out.println("gs://tiers2confiance-21525.appspot.com/camera/"+fileUri);



                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreationProfilActivity.this, "Document Exist : Erreur dans la creation photo profil", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onFailure: ", e );
                                }
                            });



                    /****

                    ModelUsers contenuUser = documentSnapshot.toObject(ModelUsers.class);
                    assert contenuUser != null;
                    userConnected.update("us_nephews_request_to", contenuUser.getUs_nephews_request_to() + userDisplayed.getId()+  ";");
                    userDisplayed.get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    ModelUsers celibUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
                                    assert celibUser != null;
                                    String usGodfatherRequestFrom = celibUser.getUs_godfather_request_from();
                                    userDisplayed.update("us_godfather_request_from", usGodfatherRequestFrom + userConnected.getId()+  ";");
                                }
                            });



                    ***/
                }else{

                    System.out.println("Document Snapshot doesn't exist");

                    // Création d'un objet pour envoyer sur la Database
                    Map<String, Object> userList = new HashMap<>();
                    userList.put("us_avatar", avatar);

                    // Envoi de l'objet sur la Database
                    docRef.set(userList)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CreationProfilActivity.this, "Profil Photo crée", Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "Profil crée");
                                    Log.i(TAG, "Avatar"+avatar);
                                   startActivity(new Intent(CreationProfilActivity.this, MainActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreationProfilActivity.this, "Erreur dans la creation photo profil", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onFailure: ", e );
                                }
                            });



                }





            }
        });



/****
        // Init des composants Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        db = FirebaseFirestore.getInstance();
        docRef = db.document("users/" + userId);
        storageReference = FirebaseStorage.getInstance().getReference();

        ***/

    }

}