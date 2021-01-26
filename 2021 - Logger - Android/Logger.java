package com.company;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import androidx.room.Database;
import org.json.JSONObject;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Logger {

    @Getter
    private static final String TAG = "Logger";
    @Getter
    private static final int connectTimeout = 10;

    public static void sendLog() {
        new ObservableCreate<Boolean>
                (emitter -> {
                    List<LogExample> logList = Database.getInstance(TAG).logExampleDao().getListForSend(false);
                    if (logList != null && logList.size() > 0) {
                        for (LogExample log : logList) {
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("...", "...");

                            jsonObject.put("className", log.getClassName());
                            jsonObject.put("methodName", log.getMethodName());
                            jsonObject.put("message", log.getMessage());
                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
                            Request request = new Request.Builder()
                                    .url("...")
                                    .post(body)
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-type", "application/json")
                                    .build();
                            Response response = buildHttpClient().newCall(request).execute();
                            int responseCode = response.code();
                            String responseString = response.body().string();
                            Log.d(getTAG(), "sendLog responseCode = " + responseCode + " responseString = " + responseString);
                            if (responseCode == 200) {
                                Log.d(getTAG(), "Логи отправлены на сервер");
                                Database.getInstance(TAG).logExampleDao().update(log);
                            } else {
                                Log.e(getTAG(), "Логи не отправлены на сервер, ответ сервера не 200");
                            }
                        }
                    }
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(getTAG(), "sendLog");
                    }

                    @Override
                    public void onNext(@NonNull Boolean b) {
                        Log.d(getTAG(), "sendLog onComplete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(getTAG(), "sendLog onError " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(getTAG(), "sendLog onComplete");
                    }
                });
    }


    public static void saveLog(String className, String methodName, String message, Context context) {
        Log.d(getTAG(), "saveLogAndExit");
        new ObservableCreate<Boolean>
                (emitter -> {
                    LogExample log = new LogExample(className, methodName, message);
                    Database.getInstance(TAG).logExampleDao().insert(log);
                    PackageManager packageManager = context.getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                    ComponentName componentName = intent.getComponent();
                    Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                    context.startActivity(mainIntent);
                    Runtime.getRuntime().exit(0);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(getTAG(), "saveLogAndExit " + className + " " + methodName + " " + message);
                    }

                    @Override
                    public void onNext(@NonNull Boolean b) {
                        Log.d(getTAG(), "saveLogAndExit onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(getTAG(), "saveLogAndExit onError " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(getTAG(), "saveLogAndExit onComplete");
                    }
                });
    }

    public static void saveLog(String className, String methodName, String message) {
        new ObservableCreate<View>
                (emitter -> {
                    LogExample log = new LogExample(className, methodName, message);
                    Database.getInstance(TAG).logExampleDao().insert(log);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<View>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(getTAG(), "saveLog " + className + " " + methodName + " " + message);
                    }

                    @Override
                    public void onNext(@NonNull View view) {
                        Log.d(getTAG(), "saveLog onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(getTAG(), "saveLog onError " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(getTAG(), "saveLog onComplete");
                    }
                });
    }

    private static OkHttpClient buildHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(connectTimeout, TimeUnit.SECONDS)
                .build();
    }
}
