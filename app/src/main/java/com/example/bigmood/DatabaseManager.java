package com.example.bigmood;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;

public class DatabaseManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public final CollectionReference userCollectionReference = db.collection("Users");
    public final CollectionReference moodCollectionReference = db.collection("Moods");
    public final CollectionReference requestsCollectionReference = db.collection("FollowRequests");
    public final String SET_MOOD_TAG = "Set mood";
    public final String GET_MOOD_TAG = "Get mood";
    public final String SET_USER_TAG = "Set user";
    public final String GET_USER_TAG = "Get user";
    private User workingUser;
    private moodObject workingMood;

    public void addUser(User user) {
        //Possible errors: data fails to upload, invalid data maybe?
        // TODO: not tested
        Timestamp timestamp = new Timestamp(new Date());
        HashMap<String, Object> data = new HashMap<>();
        data.put("dateCreated", timestamp);
        data.put("userId", user.getUserId());
        data.put("displayName", user.getDisplayName());
        userCollectionReference
                .document(user.getUserId())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(SET_USER_TAG, "Data addition successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(SET_USER_TAG, "Data addition failed" + e.toString());
                    }
                });
    }

    public void addMood(User user, moodObject mood) {
        //Possible errors: data fails to upload, invalid data maybe?
        // TODO: not tested
    }

    public User getUser(String userId) {
        DocumentReference docRef = userCollectionReference.document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 workingUser = new User(documentSnapshot.getString("userId"), documentSnapshot.getString("displayName"));
                 workingUser.setProfilePictureUrl(documentSnapshot.getString("profileRef"));
                 workingUser.setDateCreated(documentSnapshot.getTimestamp("dateCreated"));
            }
        });
        return this.workingUser;
    }

    public moodObject getMoodById(String moodId) {
        // TODO: not tested
        DocumentReference docRef = moodCollectionReference.document(moodId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //moodObject moodObject = new moodObject(documentSnapshot.getString("userId"), documentSnapshot.getString("displayName"));
            }
        });
        return this.workingMood;
    }
}
