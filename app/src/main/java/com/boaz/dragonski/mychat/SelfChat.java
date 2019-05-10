package com.boaz.dragonski.mychat;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class SelfChat extends Application {

    public static final String APPLICATION_TAG = "SelfChat";
    public static final String MESSAGES_COLLECTION = "Messages";
    private Messages messages;

    @Override
    public void onCreate() {
        super.onCreate();

        final ArrayList<OneMessage> messageEntities = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(MESSAGES_COLLECTION);
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null){
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        OneMessage oneMessage = queryDocumentSnapshot.toObject(OneMessage.class);
                        messageEntities.add(oneMessage);
                    }
                    Collections.sort(messageEntities);
                    messages = new Messages(messageEntities);
                    start();
                }
            }
        });
    }

    public Messages getMessages() {
        return messages;
    }

    private void start(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
