package com.romankryvolapov.runningfromdexfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

public class MainActivity extends AppCompatActivity {

    private static String dataDir;
    private static final String LOG_TAG = "Status";

    private Button buttonManual;
    private Button buttonRun;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    Button buttonCloseManual;

    TextView textViewManual;

    private Dialog dialogManual;

    private Spinner spinnerMethods;
    private Spinner spinnerFields;

    private static String stringUrl = "";
    private static String stringFileName = "";
    private static String stringPackage = "";
    private static String stringClass = "";
    private static String stringMethod = "";

    public ConstraintLayout myLayout;
    private Class<?> newClass;
    //    private Method newMethod;
    private Method[] newMethods;
    private Field[] newFields;
    private String[] newMethodsString;
    private String[] newFieldsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDir = getApplicationInfo().dataDir;
        findAll();
        addButtonListiners();
        changeTopColor();
        checkPermittion();
        test();
    }

//    private boolean isPackageInstalled(String packageName) {
//        try {
//            PackageManager packageManager = this.getPackageManager();
//            packageManager.getPackageInfo(packageName, 0);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }

    void checkPermittion() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            showToast(getString(R.string.app_no_inet) + "");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 0);
            Log.d(LOG_TAG, "Permission.INTERNET = false");
        } else {
            Log.d(LOG_TAG, "Permission.INTERNET = true");
        }
    }

    private void test() {
        try {
            stringUrl = "https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/Old%202016/";
            stringFileName = "Test.dex";
            stringPackage = "com.romankryvolapov.runningfromdexfile";
            stringClass = "Test";
            stringMethod = "test";
            run();
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception 3 = " + e);
        }

    }

    private void newDialogManual() {
//        displays instructions for use, at the moment it works in English or Russian
//        depending on device language
        dialogManual = new Dialog(MainActivity.this);
        dialogManual.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogManual.setCancelable(false);
        dialogManual.setContentView(R.layout.manual);
        textViewManual = dialogManual.findViewById(R.id.textViewManual);
        textViewManual.setText(getText(R.string.manual));
        buttonCloseManual = dialogManual.findViewById(R.id.buttonCloseManual);
        buttonCloseManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogManual.dismiss();
            }
        });
        dialogManual.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialogManual.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialogManual.show();
    }

    private void findAll() {
        buttonManual = findViewById(R.id.buttonManual);
        buttonRun = findViewById(R.id.buttonRun);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        myLayout = findViewById(R.id.myLayout);
        spinnerMethods = findViewById(R.id.spinnerMethods);
        spinnerFields = findViewById(R.id.spinnerFields);
    }


    private void addButtonListiners() {
        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run();
            }
        });
        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogManual();
            }
        });
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    void afterDownload() {
        try {
            File file = new File(dataDir, stringFileName);
            Log.d(LOG_TAG, "File = " + file);
            File codeCacheDir = new File(getCacheDir() + File.separator + "codeCache");
            codeCacheDir.mkdirs();
            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), codeCacheDir.getAbsolutePath(), null, getClassLoader());
            newClass = dexClassLoader.loadClass(stringPackage + "." + stringClass);
            newMethods = newClass.getDeclaredMethods();
            if (newMethods != null && newMethods.length > 0) {
                newMethodsString = new String[newMethods.length];
                for (int i = 0; i < newMethods.length; i++) {
                    newMethodsString[i] = newMethods[i].getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newMethodsString);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMethods.setAdapter(adapter);
                spinnerMethods.setSelection(0);
            }

            newFields = newClass.getDeclaredFields();
            if (newFields != null && newFields.length > 0) {
                newFieldsString = new String[newFields.length];
                for (int i = 0; i < newFields.length; i++) {
                    newFieldsString[i] = newFields[i].getName();
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newFieldsString);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFields.setAdapter(adapter2);
                spinnerFields.setSelection(0);
            }


        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception 2 = " + e);
            showToast(e + "");
            e.printStackTrace();
        }

    }

    private void run() {
//        text1 = editText1.getText().toString();
//        text2 = editText2.getText().toString();
//        text3 = editText3.getText().toString();
//        text4 = editText4.getText().toString();
//        text5 = editText5.getText().toString();
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(null, null, null);
    }

    private void changeTopColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    public class DownloadTask extends AsyncTask<Void, Object, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            try {
                InputStream inputStream = new URL(stringUrl + stringFileName).openStream();
                File dir = new File(dataDir);
                dir.mkdirs();
                File file = new File(dir, stringFileName);
                Log.d(LOG_TAG, "File = " + file);
                if (file.exists()) {
                    Log.d(LOG_TAG, "Old file delete");
                    file.delete();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[2048];
                int count;
                while ((count = inputStream.read(buffer, 0, 2048)) != -1) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                Log.d(LOG_TAG, "AsyncTask Finish");
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception 1 = " + e);
                e.printStackTrace();
                return e;
            }

            return null;
        }

        @Override
        public void onPostExecute(Object obj) {
            if (obj == null) {
                showToast(getString(R.string.app_ready) + "");
                Log.d(LOG_TAG, "Download Ready");
                afterDownload();
            } else {
                showToast(getString(R.string.app_error) + "");
                Log.d(LOG_TAG, "Download Error");
            }


        }
    }
}
