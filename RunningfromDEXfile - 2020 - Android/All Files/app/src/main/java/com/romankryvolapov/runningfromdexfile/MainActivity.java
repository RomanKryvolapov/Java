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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {


    private final String LOG_TAG = "Status";
    private String dataDir;
    private Button buttonManual;
    private Button buttonRun;
    private Button buttonOpen;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private TextView textViewField;
    private Button buttonCloseManual;
    private TextView textViewManual;
    private Dialog dialogManual;
    private Spinner spinnerMethods;
    private Spinner spinnerFields;
    private String stringUrl = "";
    private String stringFileName = "";
    private String stringPackage = "";
    private String stringClass = "";
    private Class<?> newClass;
    private Method[] newMethods;
    private Field[] newFields;
    private String[] newMethodsString;
    private String[] newFieldsString;
    private Button buttonLoadField;

    public static TextView myTextView;
    public static ConstraintLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAll();
        addButtonListiners();
        changeTopColor();
        checkPermittion();
        dataDir = getApplicationInfo().dataDir;
        myTextView = new TextView(this);
        myTextView.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
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
            showToast(getString(R.string.app_no_inet) + "", Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 0);
            Log.d(LOG_TAG, "Permission.INTERNET = false");
        } else {
            Log.d(LOG_TAG, "Permission.INTERNET = true");
        }
    }

    private void test() {
        stringUrl = "https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/Old%202016/";
        stringFileName = "Test.dex";
        stringPackage = "com.romankryvolapov.runningfromdexfile";
        stringClass = "Test";
    }

    private void run() {
        if (newClass != null) {
            if (newMethods != null && newMethods.length > 0) {
                try {
                    Log.d(LOG_TAG, "newMethods[spinnerMethods] name = " + newMethods[spinnerMethods.getSelectedItemPosition()].getName());
                    newMethods[spinnerMethods.getSelectedItemPosition()].invoke(newClass.newInstance(), null);
                } catch (InvocationTargetException e) {
                    showToast(getString(R.string.app_server_code_error) + "\n" + e, Toast.LENGTH_LONG);
                    Log.d(LOG_TAG, "InvocationTargetException = " + e);
                    e.printStackTrace();
                } catch (Exception e) {
                    showToast(e + "", Toast.LENGTH_LONG);
                    Log.d(LOG_TAG, "Exception 4 = " + e);
                    e.printStackTrace();
                }
            }
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
        buttonOpen = findViewById(R.id.buttonOpen);
        buttonLoadField = findViewById(R.id.buttonLoadField);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        textViewField = findViewById(R.id.textViewField);
        myLayout = findViewById(R.id.myLayout);
        spinnerMethods = findViewById(R.id.spinnerMethods);
        spinnerFields = findViewById(R.id.spinnerFields);

    }

    private void addButtonListiners() {
        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogManual();
            }
        });
        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURL();
            }
        });
        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run();
            }
        });
        buttonLoadField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadfield();
            }
        });
    }

    private void loadfield() {
        textViewField.setText("");
        if (newFields != null && newFields.length > 0) {
            try {
                textViewField.setText("");
                String value = newFields[spinnerFields.getSelectedItemPosition()].get(newClass.newInstance()).toString();
                textViewField.setText(value);
            } catch (Exception e) {
                showToast(e + "", Toast.LENGTH_SHORT);
                Log.d(LOG_TAG, "Exception 6 = " + e);
                e.printStackTrace();
            }


        }
//                textViewField.setText();


    }

    private void showToast(String text, int legnth) {
        Toast toast = Toast.makeText(MainActivity.this, text, legnth);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    void afterDownload() {
        try {
            File file = new File(dataDir, stringFileName);
            File codeCacheDir = new File(getCacheDir() + File.separator + "codeCache");
            codeCacheDir.mkdirs();
            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), codeCacheDir.getAbsolutePath(), null, getClassLoader());
            newClass = dexClassLoader.loadClass(stringPackage + "." + stringClass);
            if (newClass != null) {
                Log.d(LOG_TAG, "newClass name = " + newClass.getName());
                newMethods = newClass.getDeclaredMethods();
                if (newMethods != null && newMethods.length > 0) {
                    newMethodsString = new String[newMethods.length];
                    for (int i = 0; i < newMethods.length; i++) {
                        Log.d(LOG_TAG, "newMethods[i] name = " + newMethods[i].getName());
                        newMethods[i].setAccessible(true);
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
                        newFields[i].setAccessible(true);
                        newFieldsString[i] = newFields[i].getName();
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newFieldsString);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFields.setAdapter(adapter2);
                    spinnerFields.setSelection(0);
                }
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception 2 = " + e);
            showToast(e + "", Toast.LENGTH_LONG);
            e.printStackTrace();
        }

    }

    private void openURL() {
        textViewField.setText("");
        stringUrl = editText1.getText().toString();
        stringFileName = editText2.getText().toString();
        stringPackage = editText3.getText().toString();
        stringClass = editText4.getText().toString();
//        test();
        if (stringUrl != null & stringFileName != null & stringPackage != null & stringClass != null) {
            if (stringUrl.length() > 0 & stringFileName.length() > 0 & stringPackage.length() > 0 & stringClass.length() > 0) {
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(null, null, null);
            } else {
                showToast(getString(R.string.app_fields_not_correct), Toast.LENGTH_LONG);
            }
        } else {
            showToast(getString(R.string.app_fields_not_correct), Toast.LENGTH_LONG);
        }
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
                showToast(getString(R.string.app_ready) + "", Toast.LENGTH_SHORT);
                Log.d(LOG_TAG, "Download Ready");
                afterDownload();
            } else {
                showToast(getString(R.string.app_error) + "", Toast.LENGTH_LONG);
                Log.d(LOG_TAG, "Download Error");
            }


        }
    }
}
