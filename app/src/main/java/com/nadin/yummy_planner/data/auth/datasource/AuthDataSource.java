package com.nadin.yummy_planner.data.auth.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nadin.yummy_planner.data.auth.model.User;
import com.nadin.yummy_planner.utils.AuthCallback;

public class AuthDataSource {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public AuthDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void login(
            String email,
            String password,
            AuthCallback authCallback
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        firestore.collection("users")
                                .document(firebaseAuth.getCurrentUser().getUid())
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    String name = documentSnapshot.getString("name");
                                    String userEmail = documentSnapshot.getString("email");
                                    User user = new User(
                                            firebaseAuth.getCurrentUser().getUid(),
                                            name != null ? name : "",
                                            userEmail != null ? userEmail : ""
                                    );
                                    authCallback.onSuccess(user);
                                })
                                .addOnFailureListener(e -> authCallback.onError(e.getMessage()));
                    } else {
                        authCallback.onError(
                                task.getException() != null
                                        ? task.getException().getMessage()
                                        : "Login failed"
                        );
                    }
                });
    }

    public void register(
            String name,
            String email,
            String password,
            AuthCallback authCallback
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(
                                firebaseAuth.getCurrentUser().getUid(),
                                name,
                                email
                        );
                        firestore.collection("users")
                                .document(user.getId())
                                .set(user)
                                .addOnSuccessListener(aVoid -> authCallback.onSuccess(user))
                                .addOnFailureListener(e -> authCallback.onError(e.getMessage()));
                    } else {
                        authCallback.onError(
                                task.getException() != null
                                        ? task.getException().getMessage()
                                        : "Registration failed"
                        );
                    }
                });
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
