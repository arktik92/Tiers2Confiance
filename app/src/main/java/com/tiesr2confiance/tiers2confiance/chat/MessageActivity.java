package com.tiesr2confiance.tiers2confiance.chat;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_USERS_COLLECTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.NODE_CHATLIST;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.NODE_CHATS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.Models.ModelChat;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.grpc.Context;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "#######>>>>>";

    // Var des widgets
    private CircleImageView profile_image;
    private TextView username;
    private ImageButton btn_send;
    private EditText text_send;
    private RecyclerView recyclerView;
    private String idParticipantChat;

    // Var globales
    private MessageAdapter messageAdapter;
    private List<ModelChat> mchat;
    private Intent intent;
    private String CHANNEL_ID = "Messages notifications";

    // Var Firebase
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    private CollectionReference chatCollectionRef;
    private CollectionReference chatListCollectionRef;

    private DocumentReference userDocumentRef;
    private DocumentReference chatDocumentRef;
    private DocumentReference chatlistDocumentRef;


    // Initialisation des widgets
    private void init() {
        recyclerView = findViewById(R.id.recycler_view_message_activity);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
    }

    // Initialisation de FirebaseUser
    private void initFirebase() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        chatCollectionRef = db.collection(NODE_CHATS);
        chatListCollectionRef = db.collection(NODE_CHATLIST);

        userDocumentRef = db.collection(KEY_USERS_COLLECTION).document(currentUser.getUid());
        chatDocumentRef = db.collection(NODE_CHATS).document(currentUser.getUid());
        chatlistDocumentRef = db.collection(NODE_CHATLIST).document(currentUser.getUid());
    }

    // Gestion des clics sur les boutons
    private void btnSend() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = "";
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(currentUser.getUid(), idParticipantChat, msg);
                    sendNotification(currentUser.getUid(), msg, token);
                    createNotificationChannel();
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send an empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Initialisation des widgets
        init();
        // Initialisation de Firebase
        initFirebase();

        // Gestion de la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Gestion de la navigation de la toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        // Récupération de l'id du participant au chat via l'intent
        intent = getIntent();
        idParticipantChat = intent.getStringExtra("idParticipantChat");

        // Appel des clics sur les boutons
        btnSend();
        // Query pour le SnapshotListner
        Query query = db.collection(KEY_USERS_COLLECTION);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    ModelUsers user = Objects.requireNonNull(documentSnapshot.toObject(ModelUsers.class));
                    username.setText(user.getUs_nickname());

                    try{
                        user.getUs_avatar();
                        if (user.getUs_avatar().isEmpty()) {
                            Log.e(TAG, "icccccci: " + user.getUs_avatar() );
                            profile_image.setImageResource(R.mipmap.ic_launcher);
                        } else {
                            /** Loading Avatar **/
                            Log.e(TAG, "làààààààààààààà: " + user.getUs_avatar() );
                            Glide
                                    .with(getApplicationContext())
                                    .load(user.getUs_avatar())
                                    .fitCenter()
                                    .circleCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(profile_image);
                        }
                    } catch (Exception e){
                        Log.e(TAG, "onEvent: ERREUR "  );
                    }

                    readMessages(currentUser.getUid(), idParticipantChat, user.getUs_avatar());
                }
            }
        });
        seenMessage(idParticipantChat);
    }

    //TODO ###SAM Finir isSeen
    // Les messages on-ils été vu ?
    private void seenMessage(String userid) {
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        seenListener = reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    ChatModel chat = snapshot.getValue(ChatModel.class);
//                    if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)) {
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("isseen", true);
//                        snapshot.getRef().updateChildren(hashMap);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    // Envoi des messages
    private void sendMessage(String sender, final String receiver, String message) {

        // Upload du message dans la table Chats
        ModelChat newChat = new ModelChat(sender, receiver, message, false);
        long time = System.currentTimeMillis();
        String docId = String.valueOf(time);
        chatCollectionRef.document(docId).set(newChat)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

        // Upload de la liaison des participants du chat en question dans Chatlist
        HashMap<String, Object> addUserToArrayMap = new HashMap<>();
        addUserToArrayMap.put("id", FieldValue.arrayUnion(idParticipantChat));

        chatListCollectionRef
                .document(currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            chatListCollectionRef.document(currentUser.getUid()).set(addUserToArrayMap);
                        } else {
                            chatListCollectionRef.document(currentUser.getUid()).update(addUserToArrayMap);
                        }
                    }
                });
    }

    // Affichage des messages
    private void readMessages(final String myid, final String userid, final String imageurl) {
        mchat = new ArrayList<>();
        Query query = db.collection(NODE_CHATS);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mchat.clear();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    ModelChat chat = documentSnapshot.toObject(ModelChat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }
        });
    }

    // Création du contenu de la notification Push qui sera envoyée au destinataire
    private void sendNotification(String sender, String msg, String token){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MessageActivity.this,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .setContentTitle(sender)
                .setContentText(msg)
//                .setContentIntent(pendingIntent) // Ouverture de l'application sur la page du chat
                .setAutoCancel(true)
//                .setLargeIcon(bitmap) // Ajout de l'avatar de l'expéditeur
                .setColor(getResources().getColor(R.color.background))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // La priorité de la notification en général DEFAULT

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MessageActivity.this);
        notificationManagerCompat.notify(1, builder.build()); // ATTENTION l'id est UNIQUE pour chacune des notifications que vous allez créer
    }

    // 2 Pour afficher les notifications sur les versions supérieures à Android 8 // Api26+
    private void createNotificationChannel() {
        // On créé des Notification channel seulement pour les versions API 26 et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //O = Oreo 8.0
            String name = "Message notification";
            String description = "Message's channel notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Enregistrement de la channel avec le service d'android OS
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    // Pending Intent pour ouvrir l'activité message lors du clic sur la notification
    private void creaIntent() {
        Intent intent = new Intent(MessageActivity.this, MessageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                MessageActivity.this, 0, intent, 0);
    }

    // Gestion du statut de l'utilisateur
    private void status(String status) {
        userDocumentRef.update("status", status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("Online");
    }

    @Override
    protected void onStop() {
        super.onStop();
        status("offline");
    }
}