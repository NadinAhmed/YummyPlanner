package com.nadin.yummy_planner.data.sync;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.nadin.yummy_planner.data.auth.datasource.AuthDataSource;
import com.nadin.yummy_planner.data.meal.MealRepo;
import com.nadin.yummy_planner.data.sync.datasource.CloudBackupDataSource;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BackupRestoreRepository {
    private final AuthDataSource authDataSource;
    private final MealRepo mealRepo;
    private final CloudBackupDataSource cloudBackupDataSource;

    public BackupRestoreRepository(Context context) {
        this.authDataSource = new AuthDataSource(context);
        this.mealRepo = new MealRepo(context);
        this.cloudBackupDataSource = new CloudBackupDataSource();
    }

    public Completable backupCurrentUserData() {
        return Single.fromCallable(() -> {
                    FirebaseUser currentUser = authDataSource.getCurrentUser();
                    if (currentUser == null) {
                        throw new IllegalStateException("No signed-in user");
                    }
                    return currentUser.getUid();
                })
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(userId -> cloudBackupDataSource.uploadBackup(
                        userId,
                        mealRepo.getAllFavMealsSync(),
                        mealRepo.getAllPlannerMealsSync()
                ));
    }

    public Completable backupThenClearCurrentUserData() {
        return Single.fromCallable(() -> {
                    FirebaseUser currentUser = authDataSource.getCurrentUser();
                    if (currentUser == null) {
                        throw new IllegalStateException("No signed-in user");
                    }
                    return currentUser.getUid();
                })
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(userId -> cloudBackupDataSource.uploadBackup(
                                userId,
                                mealRepo.getAllFavMealsSync(),
                                mealRepo.getAllPlannerMealsSync()
                        )
                        .andThen(Completable.fromAction(mealRepo::clearAllLocalData)
                                .subscribeOn(Schedulers.io())));
    }

    public Single<Boolean> restoreCurrentUserData() {
        return Single.fromCallable(() -> {
                    FirebaseUser currentUser = authDataSource.getCurrentUser();
                    if (currentUser == null) {
                        throw new IllegalStateException("No signed-in user");
                    }
                    return currentUser.getUid();
                })
                .subscribeOn(Schedulers.io())
                .flatMap(userId -> cloudBackupDataSource.getBackup(userId)
                        .flatMap(snapshot -> {
                            if (!snapshot.exists()) {
                                return Single.just(false);
                            }

                            return Completable.fromAction(() -> mealRepo.replaceAllLocalData(
                                            snapshot.getFavourites(),
                                            snapshot.getPlannerMeals()
                                    ))
                                    .subscribeOn(Schedulers.io())
                                    .andThen(Single.just(true));
                        }));
    }
}
