package com.company;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class MyExceptionHandler extends MainActivity implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CheckConnection";


    @androidx.annotation.NonNull
    @lombok.NonNull
    @Getter
    @Setter
    private Context context;

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable e) {
        Log.e(TAG, "uncaughtException " + e);
        Logger.saveLog("CustomExceptionHandler", "uncaughtException", e.getMessage(), getContext());
    }
}
