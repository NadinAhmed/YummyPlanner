package com.nadin.yummy_planner.utils;

import com.nadin.yummy_planner.data.auth.model.User;

public interface AuthCallback {
    void onSuccess(User user);
    void onError(String message);
}
