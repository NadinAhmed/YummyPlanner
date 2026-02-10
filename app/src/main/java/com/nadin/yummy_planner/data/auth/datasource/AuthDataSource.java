package com.nadin.yummy_planner.data.auth.datasource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.data.auth.model.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthDataSource {
    private static final String PREFS_AUTH_SESSION = "auth_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_GUEST = "is_guest";

    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;
    private final SharedPreferences sharedPreferences;
    private final GoogleSignInClient googleSignInClient;

    public AuthDataSource(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Context applicationContext = context.getApplicationContext();
        sharedPreferences = applicationContext
                .getSharedPreferences(PREFS_AUTH_SESSION, Context.MODE_PRIVATE);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(applicationContext, googleSignInOptions);
    }

    public Single<User> login(String email, String password) {
        return Single.<User>create(emitter -> firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                String errorMessage = task.getException() != null
                                        ? task.getException().getMessage()
                                        : "Login failed";
                                emitter.onError(new Throwable(errorMessage));
                                return;
                            }

                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            if (currentUser == null) {
                                emitter.onError(new Throwable("Login failed"));
                                return;
                            }

                            firestore.collection("users")
                                    .document(currentUser.getUid())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        User user = new User(
                                                currentUser.getUid(),
                                                documentSnapshot.getString("name") != null ? documentSnapshot.getString("name") : "",
                                                documentSnapshot.getString("email") != null ? documentSnapshot.getString("email") : ""
                                        );
                                        persistLoginState(true);
                                        persistGuestState(false);
                                        emitter.onSuccess(user);
                                    })
                                    .addOnFailureListener(error -> emitter.onError(new Throwable(error.getMessage())));
                        }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<User> register(String name, String email, String password) {
        return Single.<User>create(emitter -> firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                String errorMessage = task.getException() != null
                                        ? task.getException().getMessage()
                                        : "Registration failed";
                                emitter.onError(new Throwable(errorMessage));
                                return;
                            }

                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            if (currentUser == null) {
                                emitter.onError(new Throwable("Registration failed"));
                                return;
                            }

                            User user = new User(currentUser.getUid(), name, email);
                            firestore.collection("users")
                                    .document(user.getId())
                                    .set(user)
                                    .addOnSuccessListener(unused -> {
                                        persistLoginState(true);
                                        persistGuestState(false);
                                        emitter.onSuccess(user);
                                    })
                                    .addOnFailureListener(error -> emitter.onError(new Throwable(error.getMessage())));
                        }))
                .subscribeOn(Schedulers.io());
    }

    public Intent getGoogleSignInIntent() {
        return googleSignInClient.getSignInIntent();
    }

    public Single<User> loginWithGoogle(Intent data) {
        return Single.<User>create(emitter -> {
                    try {
                        GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data)
                                .getResult(ApiException.class);
                        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                        firebaseAuth.signInWithCredential(credential)
                                .addOnCompleteListener(task -> {
                                    if (!task.isSuccessful()) {
                                        String errorMessage = task.getException() != null
                                                ? task.getException().getMessage()
                                                : "Google sign-in failed";
                                        emitter.onError(new Throwable(errorMessage));
                                        return;
                                    }

                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    if (firebaseUser == null) {
                                        emitter.onError(new Throwable("Google sign-in failed"));
                                        return;
                                    }

                                    User user = new User(
                                            firebaseUser.getUid(),
                                            firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "",
                                            firebaseUser.getEmail() != null ? firebaseUser.getEmail() : ""
                                    );

                                    firestore.collection("users")
                                            .document(user.getId())
                                            .set(user, SetOptions.merge())
                                            .addOnSuccessListener(unused -> {
                                                persistLoginState(true);
                                                persistGuestState(false);
                                                emitter.onSuccess(user);
                                            })
                                            .addOnFailureListener(error -> emitter.onError(new Throwable(error.getMessage())));
                                });
                    } catch (ApiException exception) {
                        emitter.onError(new Throwable(exception.getMessage()));
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public void logout() {
        firebaseAuth.signOut();
        googleSignInClient.signOut();
        persistLoginState(false);
        persistGuestState(false);
    }

    public void continueAsGuest() {
        firebaseAuth.signOut();
        googleSignInClient.signOut();
        persistLoginState(false);
        persistGuestState(true);
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public Single<String> getSignedInUserName() {
        return Single.<String>create(emitter -> {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser == null) {
                        emitter.onError(new Throwable("No signed-in user"));
                        return;
                    }

                    String displayName = currentUser.getDisplayName();
                    if (displayName != null && !displayName.trim().isEmpty()) {
                        emitter.onSuccess(displayName);
                        return;
                    }

                    firestore.collection("users")
                            .document(currentUser.getUid())
                            .get()
                            .addOnSuccessListener(documentSnapshot -> {
                                String name = documentSnapshot.getString("name");
                                if (name != null && !name.trim().isEmpty()) {
                                    emitter.onSuccess(name);
                                    return;
                                }

                                String email = currentUser.getEmail();
                                if (email != null && !email.trim().isEmpty()) {
                                    emitter.onSuccess(email);
                                } else {
                                    emitter.onSuccess("User");
                                }
                            })
                            .addOnFailureListener(error -> emitter.onError(new Throwable(error.getMessage())));
                })
                .subscribeOn(Schedulers.io());
    }

    public Completable logoutCompletable() {
        return Completable.fromAction(this::logout).subscribeOn(Schedulers.io());
    }

    public boolean isUserLoggedIn() {
        boolean isPersistedLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        return isPersistedLoggedIn && firebaseAuth.getCurrentUser() != null;
    }

    public boolean isGuestUser() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }

    public boolean hasActiveSession() {
        return isUserLoggedIn() || isGuestUser();
    }

    private void persistLoginState(boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    private void persistGuestState(boolean isGuest) {
        sharedPreferences.edit().putBoolean(KEY_IS_GUEST, isGuest).apply();
    }
}
