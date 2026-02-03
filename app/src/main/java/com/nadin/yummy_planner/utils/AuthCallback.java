package com.nadin.yummy_planner.utils;

import com.nadin.yummy_planner.models.User;

public interface AuthCallback {
    void onSuccess(User user);
    void onError(String message);
}
