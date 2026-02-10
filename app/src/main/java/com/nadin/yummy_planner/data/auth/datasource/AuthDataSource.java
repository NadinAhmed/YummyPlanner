package com.nadin.yummy_planner.data.auth.datasource;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nadin.yummy_planner.data.auth.model.User;
import com.nadin.yummy_planner.utils.AuthCallback;

public class AuthDataSource {
    private static final String PREFS_AUTH_SESSION = "auth_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;
    private final SharedPreferences sharedPreferences;

    public AuthDataSource(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(PREFS_AUTH_SESSION, Context.MODE_PRIVATE);
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
                                    persistLoginState(true);
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
                                .addOnSuccessListener(aVoid -> {
                                    persistLoginState(true);
                                    authCallback.onSuccess(user);
                                })
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
        persistLoginState(false);
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public boolean isUserLoggedIn() {
        boolean isPersistedLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        return isPersistedLoggedIn && firebaseAuth.getCurrentUser() != null;
    }

    private void persistLoginState(boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }
}
