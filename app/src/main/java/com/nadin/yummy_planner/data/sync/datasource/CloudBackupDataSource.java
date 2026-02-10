package com.nadin.yummy_planner.data.sync.datasource;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nadin.yummy_planner.data.meal.model.Meal;
import com.nadin.yummy_planner.data.meal.model.PlannerMeal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CloudBackupDataSource {
    private static final String USERS_COLLECTION = "users";
    private static final String APP_DATA_COLLECTION = "app_data";
    private static final String BACKUP_DOCUMENT = "backup";
    private static final String FAVOURITES_JSON_FIELD = "favoritesJson";
    private static final String PLANNER_JSON_FIELD = "plannerMealsJson";
    private static final String UPDATED_AT_FIELD = "updatedAt";

    private final FirebaseFirestore firestore;
    private final Gson gson;

    public CloudBackupDataSource() {
        this.firestore = FirebaseFirestore.getInstance();
        this.gson = new Gson();
    }

    public Completable uploadBackup(String userId, List<Meal> favourites, List<PlannerMeal> plannerMeals) {
        return Completable.create(emitter -> {
            Map<String, Object> backup = new HashMap<>();
            backup.put(FAVOURITES_JSON_FIELD, gson.toJson(favourites != null ? favourites : new ArrayList<>()));
            backup.put(PLANNER_JSON_FIELD, gson.toJson(plannerMeals != null ? plannerMeals : new ArrayList<>()));
            backup.put(UPDATED_AT_FIELD, FieldValue.serverTimestamp());

            firestore.collection(USERS_COLLECTION)
                    .document(userId)
                    .collection(APP_DATA_COLLECTION)
                    .document(BACKUP_DOCUMENT)
                    .set(backup)
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(error -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(error);
                        }
                    });
        });
    }

    public Single<BackupSnapshot> getBackup(String userId) {
        return Single.create(emitter -> firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(APP_DATA_COLLECTION)
                .document(BACKUP_DOCUMENT)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (!documentSnapshot.exists()) {
                        if (!emitter.isDisposed()) {
                            emitter.onSuccess(new BackupSnapshot(false, new ArrayList<>(), new ArrayList<>()));
                        }
                        return;
                    }

                    String favouritesJson = documentSnapshot.getString(FAVOURITES_JSON_FIELD);
                    String plannerJson = documentSnapshot.getString(PLANNER_JSON_FIELD);

                    Type favouritesType = new TypeToken<List<Meal>>() {}.getType();
                    Type plannerType = new TypeToken<List<PlannerMeal>>() {}.getType();

                    List<Meal> favourites = favouritesJson == null || favouritesJson.trim().isEmpty()
                            ? new ArrayList<>()
                            : gson.fromJson(favouritesJson, favouritesType);

                    List<PlannerMeal> plannerMeals = plannerJson == null || plannerJson.trim().isEmpty()
                            ? new ArrayList<>()
                            : gson.fromJson(plannerJson, plannerType);

                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(new BackupSnapshot(true, favourites, plannerMeals));
                    }
                })
                .addOnFailureListener(error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                }));
    }

    public static class BackupSnapshot {
        private final boolean exists;
        private final List<Meal> favourites;
        private final List<PlannerMeal> plannerMeals;

        public BackupSnapshot(boolean exists, List<Meal> favourites, List<PlannerMeal> plannerMeals) {
            this.exists = exists;
            this.favourites = favourites;
            this.plannerMeals = plannerMeals;
        }

        public boolean exists() {
            return exists;
        }

        public List<Meal> getFavourites() {
            return favourites;
        }

        public List<PlannerMeal> getPlannerMeals() {
            return plannerMeals;
        }
    }
}
