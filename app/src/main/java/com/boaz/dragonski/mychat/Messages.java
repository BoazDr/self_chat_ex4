package com.boaz.dragonski.mychat;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Messages {
    private ArrayList<OneMessage> messageList;
    private CollectionReference messagesCollection;
    public static final String KEY_FOR_MESSAGES = "messages";

    public Messages(ArrayList<OneMessage> messageList) {
        this.messageList = messageList;
        this.messagesCollection = FirebaseFirestore.getInstance().collection(SelfChat.MESSAGES_COLLECTION);
    }

    public ArrayList<OneMessage> getMessageList() {
        return messageList;
    }

    public void removeMessage(OneMessage oneMessage) {
        messageList.remove(oneMessage);
        messagesCollection.document(oneMessage.getMsgId()).delete();
    }

    public void addMessage(final OneMessage oneMessage) {
        messageList.add(oneMessage);
        messagesCollection.document(oneMessage.getMsgId()).set(oneMessage);
    }

    public void saveJsonToBundle(Bundle bundle){
        Gson gson = new Gson();
        String json = gson.toJson(messageList);
        bundle.putString(KEY_FOR_MESSAGES, json);
    }

    public void restoreJsonFromBundle(Bundle bundle){
        Gson gson = new Gson();
        String json = bundle.getString(KEY_FOR_MESSAGES);
        messageList = gson.fromJson(json, new TypeToken<ArrayList<OneMessage>>(){}.getType());
    }

}
