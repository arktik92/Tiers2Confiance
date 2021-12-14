package com.tiesr2confiance.tiers2confiance.chat;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_USERS_COLLECTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.NODE_CHATLIST;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.text.ParseException;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.Models.ModelChatlist;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;
import com.tiesr2confiance.tiers2confiance.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;

    private List<ModelUsers> mUsers; // Liste avec les informations des utilisateurs connectés
    private List<String> mUsersConnected; // Liste des utilisateurs avec lequels une session de chat est ouverte

    private FirebaseUser currentUser;

    // Avec Firestore
    private FirebaseFirestore db;
    private CollectionReference chatListCollectionRef;
    private CollectionReference userCollectionReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_chat_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        mUsers = new ArrayList<>();
        mUsersConnected = new ArrayList<>();


        // Avec Firestore
        // Init de Firestore
        db = FirebaseFirestore.getInstance();
        chatListCollectionRef = db.collection(NODE_CHATLIST);
        userCollectionReference = db.collection(KEY_USERS_COLLECTION);

//         Création de la liste des utilisateurs avec lesquels une session de chat est ouverte
        final DocumentReference chatListDocRef = chatListCollectionRef.document(currentUser.getUid());
        chatListDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null && value.exists()) {
                    mUsersConnected.clear();
                    ModelChatlist chatlist = value.toObject(ModelChatlist.class);
                    for (int i = 0; i < chatlist.getId().size(); i++) {
                        String user = chatlist.getId().get(i);
                        mUsersConnected.add(user);
                    }
                    try {
                        chatList();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

    private void chatList() throws ParseException {
        userCollectionReference
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mUsers.clear();
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            ModelUsers user = documentSnapshot.toObject(ModelUsers.class);
                            for (int i = 0; i < mUsersConnected.size(); i++) {
                                    if (documentSnapshot.getId().equals(mUsersConnected.get(i))) {
                                        mUsers.add(user);
                            }
                            }
                        }
                        userAdapter = new UserAdapter(getContext(), mUsers, true);
                        recyclerView.setAdapter(userAdapter);
                    }
                });
    }
}

