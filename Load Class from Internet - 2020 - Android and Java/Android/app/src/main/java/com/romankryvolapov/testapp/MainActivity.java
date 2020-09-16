package com.romankryvolapov.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private static String dataDir;
    private static final String LOG_TAG = "Status";
    private static final String fileName = "java.dex";
    private static final String url = "https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/Old%202016/java.dex";
    private static final String className = "TestUtil";
//    private static final String className = "IntegratorHelper";
    private PackageManager packageManager;
    private DownloadTask downloadTask;
    private File dexFile;
    private File codeCacheDir;
    private DexClassLoader dexClassLoader;
    private Class newClass;
    private File dir;
    private Method methods[];
    private Field fields[];
    TextView textView;


    StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        packageManager = this.getPackageManager();

        stringBuilder = new StringBuilder();
        textView = findViewById(R.id.textView);

        changeTopColor();
        checkPermittion();

        stringBuilder.append("Приложение Viber на телефоне:\n");
        stringBuilder.append(isPackageInstalled("com.viber.voip") + "\n");
        stringBuilder.append("Приложение Facebook Messenger на телефоне:\n");
        stringBuilder.append(isPackageInstalled("com.facebook.orca") + "\n");
        stringBuilder.append("Приложение Facebook на телефоне:\n");
        stringBuilder.append(isPackageInstalled("com.facebook.katana") + "\n");
        stringBuilder.append("Приложение Telegram на телефоне:\n");
        stringBuilder.append(isPackageInstalled("org.telegram.messenger") + "\n");
        stringBuilder.append("Приложение Youtube на телефоне:\n");
        stringBuilder.append(isPackageInstalled("com.google.android.youtube") + "\n");







        loadClassFromInternet();


    }

    private boolean isPackageInstalled(String packageName) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    void checkPermittion() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            stringBuilder.append("Доступ в интернет нет\n");
            Log.d(LOG_TAG, "Permission.INTERNET = false");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 0);
        } else {
            stringBuilder.append("Доступа в интернет есть\n");
            Log.d(LOG_TAG, "Permission.INTERNET = true");
        }
    }

    void changeTopColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    void loadClassFromInternet() {
        dataDir = getApplicationInfo().dataDir;
        downloadTask = new DownloadTask(this, dataDir);
        downloadTask.execute(null, null, null);
    }

    void afterDownload() {
        try {
            dexFile = new File(dataDir, fileName);
            codeCacheDir = new File(getCacheDir() + File.separator + "codeCache");
            codeCacheDir.mkdirs();
            dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), codeCacheDir.getAbsolutePath(), null, getClassLoader());
            newClass = dexClassLoader.loadClass(className);
            methods = newClass.getDeclaredMethods();
            stringBuilder.append("Название класса\n");
            stringBuilder.append(className + "\n");
            stringBuilder.append("Количество методов в классе\n");
            stringBuilder.append(methods.length + "\n");
            Log.d(LOG_TAG, "Methods Count = " + methods.length);
            fields = newClass.getClass().getDeclaredFields();
            stringBuilder.append("Количество полей в классе\n");
            stringBuilder.append(fields.length + "\n");
            Log.d(LOG_TAG, "Fields Count = " + fields.length);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "Exception = " + e);
        }
        textView.setText(stringBuilder.toString());
    }

    public class DownloadTask extends AsyncTask<Void, Object, Object> {


        private WeakReference<MainActivity> activityRef;
        String dataDir;

        public DownloadTask(MainActivity activity, String dataDir) {
            activityRef = new WeakReference<MainActivity>(activity);
            this.dataDir = dataDir;
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
                InputStream inputStream = new URL(url).openStream();
                dir = new File(dataDir);
                dir.mkdirs();
                dexFile = new File(dir, "java.dex");
                if (dexFile.exists()) {
                    dexFile.delete();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(dexFile);
                byte[] buffer = new byte[2048];
                int count;
                while ((count = inputStream.read(buffer, 0, 2048)) != -1) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                Log.d(LOG_TAG, "IOException = " + e);
                e.printStackTrace();
                return e;
            }

            return null;
        }

        @Override
        public void onPostExecute(Object obj) {
            MainActivity activity = activityRef.get();
            if (activity != null) {
                if (obj == null) {
                    stringBuilder.append("DEX файл загружен\n");
                    Log.d(LOG_TAG, "Download Ready");
                    afterDownload();
                } else {
                    stringBuilder.append("DEX файл не загружен\n");
                    Log.d(LOG_TAG, "Download Error");
                }

            }
        }
    }
}
