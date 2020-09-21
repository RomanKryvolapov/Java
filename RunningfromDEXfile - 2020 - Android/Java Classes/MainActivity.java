package com.romankryvolapov.runningfromdexfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import java.net.URL;
import java.util.Map;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    // Values
    private final String LOG_TAG = "Status";
    private String dataDir;
    private int savedValuesCounter;
    private String[] savedURLs;
    // Buttons
    private Button buttonManual;
    private Button buttonRun;
    private Button buttonOpen;
    private Button buttonLoadField;
    private Button buttonUse;
    private Button buttonSave;
    private Button buttonDelete;
    private Button buttonCloseManual;
    // EditText
    private EditText editTextStringUrl;
    private EditText editTextStringFileName;
    private EditText editTextStringPackage;
    private EditText editTextStringClass;
    // TextView
    private TextView textViewField;
    private TextView textViewManual;
    public static TextView myTextView;
    // Spinner
    private Spinner spinnerMethods;
    private Spinner spinnerFields;
    private Spinner spinnerSavedURL;
    // Other
    public static ConstraintLayout myLayout;

    private Class<?> newClass;
    private Method[] newMethods;
    private Field[] newFields;

    private Dialog dialogManual;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

//        eraseAll();

        dataDir = getApplicationInfo().dataDir;
        myTextView = new TextView(this);
        myTextView.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        checkInternetPermittion(Manifest.permission.INTERNET);
        changeTopColor(R.color.colorPrimary);
        findAll();
        firstRun();
        addButtonListiners();
        addSavedURLtoSpinner();
        spinnerMethods.setAdapter(createSpinnerAdapter(new String[]{getString(R.string.app_field_no)}));
        spinnerMethods.setSelection(0);
        spinnerFields.setAdapter(createSpinnerAdapter(new String[]{getString(R.string.app_field_no)}));
        spinnerFields.setSelection(0);
//


//        Map<String, ?> map = sharedPreferences.getAll();
//        for (Map.Entry<String, ?> entry : map.entrySet()) {
//            Log.d(LOG_TAG, "Key = " + entry.getKey() + " & Value = " + entry.getValue());
//        }

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

    private void eraseAll(){
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.apply();
    }

    private void addSavedURLtoSpinner() {
        if (sharedPreferences.contains("savedValuesCounter")) {
            savedValuesCounter = sharedPreferences.getInt("savedValuesCounter", 0);
            Log.d(LOG_TAG, "savedValuesCounter = " + savedValuesCounter);
            if (savedValuesCounter > 0) {
                savedURLs = new String[savedValuesCounter];
                for (int i = 0; i < savedValuesCounter; i++) {
                    savedURLs[i] = sharedPreferences.getString("stringUrl" + (i + 1), "");
                    Log.d(LOG_TAG, "SavedValues " + (i + 1) + " = " + savedURLs[i]);
                }

                spinnerSavedURL.setAdapter(createSpinnerAdapter(savedURLs));
                spinnerSavedURL.setSelection(savedValuesCounter - 1);
            } else {
                spinnerSavedURL.setAdapter(createSpinnerAdapter(new String[]{getString(R.string.app_field_no)}));
                spinnerSavedURL.setSelection(0);
                Log.d(LOG_TAG, "savedValuesCounter > 0 = false");
            }
        } else {
            spinnerSavedURL.setAdapter(createSpinnerAdapter(new String[]{getString(R.string.app_field_no)}));
            spinnerSavedURL.setSelection(0);
            Log.d(LOG_TAG, "sharedPreferences.contains(savedValuesCounter) = false");
        }
    }

    private ArrayAdapter createSpinnerAdapter(String[] values) {
        if (values != null && values.length > 0) {
            ArrayAdapter<String> adapterUrl = new ArrayAdapter<String>(
                    MainActivity.this, android.R.layout.simple_spinner_item, values);
            adapterUrl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return adapterUrl;
        } else {
            ArrayAdapter<String> adapterUrl = new ArrayAdapter<String>(
                    MainActivity.this, android.R.layout.simple_spinner_item, new String[]{getString(R.string.app_field_no)});
            adapterUrl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return adapterUrl;
        }
    }

    private void checkInternetPermittion(String permission) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            showToast(getString(R.string.app_no_inet) + "", Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, 0);
            Log.d(LOG_TAG, permission + " = false");
        } else {
            Log.d(LOG_TAG, permission + " = true");
        }
    }

    private void firstRun() {
        if (!sharedPreferences.contains("savedValuesCounter")) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putString("stringUrl1", "https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/");
            sharedPreferencesEditor.putString("stringFileName1", "Test.dex");
            sharedPreferencesEditor.putString("stringPackage1", "com.romankryvolapov.runningfromdexfile");
            sharedPreferencesEditor.putString("stringClass1", "Test");
            sharedPreferencesEditor.putInt("savedValuesCounter", 1);
            sharedPreferencesEditor.apply();
        }
    }

    private void runMethod(Class newClass, Method method) {
        if (newClass != null) {
            if (method != null) {
                try {
                    method.invoke(newClass.newInstance(), null);
                } catch (InvocationTargetException e) {
                    showToast(getString(R.string.app_server_code_error) + "\n" + e, Toast.LENGTH_LONG);
                    Log.d(LOG_TAG, "InvocationTargetException = " + e);
                    e.printStackTrace();
                } catch (Exception e) {
                    showToast(e + "", Toast.LENGTH_LONG);
                    Log.d(LOG_TAG, "Exception 4 = " + e);
                    e.printStackTrace();
                }
            } else showToast(getString(R.string.app_fields_not_correct), Toast.LENGTH_LONG);
        } else showToast(getString(R.string.app_fields_not_correct), Toast.LENGTH_LONG);
    }

    private void newDialogManual(String text) {
        if (text.length() > 0) {
            dialogManual = new Dialog(MainActivity.this);
            dialogManual.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogManual.setCancelable(false);
            dialogManual.setContentView(R.layout.manual);
            textViewManual = dialogManual.findViewById(R.id.textViewManual);
            textViewManual.setText(text);
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
        } else {
            showToast("Error", Toast.LENGTH_LONG);
            Log.d(LOG_TAG, "Error");
        }
    }

    private void useSavedURL() {
        if (sharedPreferences.contains("savedValuesCounter")) {
            savedValuesCounter = sharedPreferences.getInt("savedValuesCounter", 0);
            if (savedValuesCounter > 0) {
                int position = spinnerSavedURL.getSelectedItemPosition() + 1;
                editTextStringUrl.setText(sharedPreferences.getString("stringUrl" + position, ""));
                editTextStringFileName.setText(sharedPreferences.getString("stringFileName" + position, ""));;
                editTextStringPackage.setText(sharedPreferences.getString("stringPackage" + position, ""));;
                editTextStringClass.setText(sharedPreferences.getString("stringClass" + position, ""));;
            } else showToast(getString(R.string.app_field_error), Toast.LENGTH_SHORT);
        } else showToast(getString(R.string.app_field_error), Toast.LENGTH_SHORT);

    }

    private void deleteURL() {
        if (sharedPreferences.contains("savedValuesCounter")) {
            savedValuesCounter = sharedPreferences.getInt("savedValuesCounter", 0);
            if (savedValuesCounter > 0) {
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                for (int i = savedValuesCounter; i > spinnerSavedURL.getSelectedItemPosition() + 1; i--) {
                    sharedPreferencesEditor.putString("stringUrl" + (i - 1), sharedPreferences.getString("stringUrl" + i, ""));
                    sharedPreferencesEditor.putString("stringFileName" + (i - 1), sharedPreferences.getString("stringFileName" + i, ""));
                    sharedPreferencesEditor.putString("stringPackage" + (i - 1), sharedPreferences.getString("stringPackage" + i, ""));
                    sharedPreferencesEditor.putString("stringClass" + (i - 1), sharedPreferences.getString("stringClass" + i, ""));
                }

                sharedPreferencesEditor.remove("stringUrl" + savedValuesCounter);
                sharedPreferencesEditor.remove("stringFileName" + savedValuesCounter);
                sharedPreferencesEditor.remove("stringPackage" + savedValuesCounter);
                sharedPreferencesEditor.remove("stringClass" + savedValuesCounter);
                savedValuesCounter--;
                sharedPreferencesEditor.putInt("savedValuesCounter", savedValuesCounter);
                sharedPreferencesEditor.apply();
            } else showToast(getString(R.string.app_field_error), Toast.LENGTH_SHORT);
        } else showToast(getString(R.string.app_field_error), Toast.LENGTH_SHORT);
    }

    public static boolean checkFields(String stringUrl, String stringFileName, String
            stringPackage, String stringClass) {
        if (stringUrl != null & stringFileName != null & stringPackage != null & stringClass != null) {
            if (stringUrl.length() > 0 & stringFileName.length() > 0 & stringPackage.length() > 0 & stringClass.length() > 0) {
                return true;
            } else return false;
        } else return false;
    }


    private void saveURL(String stringUrl, String stringFileName, String stringPackage, String
            stringClass) {
        if (checkFields(stringUrl, stringFileName, stringPackage, stringClass)) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            if (sharedPreferences.contains("savedValuesCounter")) {
                savedValuesCounter = sharedPreferences.getInt("savedValuesCounter", 0);
                savedValuesCounter++;
            } else {
                savedValuesCounter = 1;
            }
            Log.d(LOG_TAG, "savedValuesCounter = " + savedValuesCounter);
            sharedPreferencesEditor.putInt("savedValuesCounter", savedValuesCounter);
            sharedPreferencesEditor.putString("stringUrl" + savedValuesCounter, stringUrl);
            sharedPreferencesEditor.putString("stringFileName" + savedValuesCounter, stringFileName);
            sharedPreferencesEditor.putString("stringPackage" + savedValuesCounter, stringPackage);
            sharedPreferencesEditor.putString("stringClass" + savedValuesCounter, stringClass);
            sharedPreferencesEditor.apply();
            Log.d(LOG_TAG, "Save ready");
            showToast(getString(R.string.app_save_ready) + "", Toast.LENGTH_SHORT);
        } else {
            Log.d(LOG_TAG, "Save error");
            showToast(getString(R.string.app_save_error) + "", Toast.LENGTH_SHORT);
        }
    }


    private void findAll() {
        buttonManual = findViewById(R.id.buttonManual);
        buttonRun = findViewById(R.id.buttonRun);
        buttonOpen = findViewById(R.id.buttonOpen);
        buttonLoadField = findViewById(R.id.buttonLoadField);
        editTextStringUrl = findViewById(R.id.editText1);
        editTextStringFileName = findViewById(R.id.editText2);
        editTextStringPackage = findViewById(R.id.editText3);
        editTextStringClass = findViewById(R.id.editText4);
        textViewField = findViewById(R.id.textViewField);
        myLayout = findViewById(R.id.myLayout);
        spinnerMethods = findViewById(R.id.spinnerMethods);
        spinnerFields = findViewById(R.id.spinnerFields);
        spinnerSavedURL = findViewById(R.id.spinnerSavedURL);
        buttonUse = findViewById(R.id.buttonUse);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);
    }

    private void addButtonListiners() {
        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogManual(getString(R.string.manual));
            }
        });
        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewField.setText("");
                openURL(editTextStringUrl.getText().toString(),
                        editTextStringFileName.getText().toString(),
                        editTextStringPackage.getText().toString(),
                        editTextStringClass.getText().toString()
                );
            }
        });
        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newClass != null & newMethods != null)
                    runMethod(newClass, newMethods[spinnerMethods.getSelectedItemPosition()]);
            }
        });
        buttonLoadField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSelectedFieldValueInTextView();
            }
        });
        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useSavedURL();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveURL(editTextStringUrl.getText().toString(), editTextStringFileName.getText().toString(), editTextStringPackage.getText().toString(), editTextStringClass.getText().toString());
                addSavedURLtoSpinner();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteURL();
                addSavedURLtoSpinner();
            }
        });
    }

    private void loadSelectedFieldValueInTextView() {
        if (spinnerFields != null & newFields != null && newFields.length > 0) {
            int position = spinnerFields.getSelectedItemPosition();
            try {
                textViewField.setText(newFields[position].get(newClass.newInstance()).toString());
            } catch (Exception e) {
                textViewField.setText("");
                showToast(e + "", Toast.LENGTH_LONG);
                Log.d(LOG_TAG, "Exception in loadSelectedFieldValueFromURL = " + e);
                e.printStackTrace();
            }
        }  else showToast(getString(R.string.app_field_error), Toast.LENGTH_SHORT);
    }

    private void showToast(String text, int length) {
        Toast toast = Toast.makeText(MainActivity.this, text, length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void openURL(String stringUrl, String stringFileName, String stringPackage, String
            stringClass) {
        if (MainActivity.checkFields(stringUrl, stringFileName, stringPackage, stringClass)) {
            DownloadTask downloadTask = new DownloadTask(stringUrl, stringFileName, stringPackage, stringClass);
            downloadTask.execute(null, null, null);
        } else {
            showToast(getString(R.string.app_fields_not_correct), Toast.LENGTH_SHORT);
        }
    }

    private void changeTopColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    public class DownloadTask extends AsyncTask<Void, Object, Object> {

        String stringUrl;
        String stringFileName;
        String stringPackage;
        String stringClass;
        String[] newMethodsString;
        String[] newFieldsString;

        private DownloadTask(String stringUrl, String stringFileName, String stringPackage, String stringClass) {
            this.stringUrl = stringUrl;
            this.stringFileName = stringFileName;
            this.stringPackage = stringPackage;
            this.stringClass = stringClass;
            Log.d(LOG_TAG, "stringUrl = " + stringUrl);
            Log.d(LOG_TAG, "stringFileName = " + stringUrl);
            Log.d(LOG_TAG, "stringPackage = " + stringUrl);
            Log.d(LOG_TAG, "stringClass = " + stringUrl);
        }


        @Override
        protected Object doInBackground(Void... params) {
            try {
                Log.d(LOG_TAG, "doInBackground start");
                InputStream inputStream = new URL(stringUrl + stringFileName).openStream();
                File dirInput = new File(dataDir);
                dirInput.mkdirs();
                File fileInput = new File(dirInput, stringFileName);
                if (fileInput.exists()) {
                    Log.d(LOG_TAG, "Old file delete");
                    fileInput.delete();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(fileInput);
                byte[] buffer = new byte[2048];
                int count;
                while ((count = inputStream.read(buffer, 0, 2048)) != -1) {
                    fileOutputStream.write(buffer, 0, count);
                }
                Log.d(LOG_TAG, "fileOutputStream = " + fileOutputStream.toString());
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                File fileOutput = new File(dataDir, stringFileName);
                File codeCacheDir = new File(getCacheDir() + File.separator + "codeCache");
                codeCacheDir.mkdirs();
                DexClassLoader dexClassLoader = new DexClassLoader(fileOutput.getAbsolutePath(), codeCacheDir.getAbsolutePath(), null, getClassLoader());
                newClass = dexClassLoader.loadClass(stringPackage + "." + stringClass);
                Log.d(LOG_TAG, "Class name = " + newClass.getName());
                if (newClass != null) {
                    newMethods = newClass.getDeclaredMethods();
                    if (newMethods != null && newMethods.length > 0) {
                        newMethodsString = new String[newMethods.length];
                        for (int i = 0; i < newMethods.length; i++) {
                            newMethods[i].setAccessible(true);
                            newMethodsString[i] = newMethods[i].getName();
                        }
                    }
                    newFields = newClass.getDeclaredFields();
                    if (newFields != null && newFields.length > 0) {
                        newFieldsString = new String[newFields.length];
                        for (int i = 0; i < newFields.length; i++) {
                            newFields[i].setAccessible(true);
                            newFieldsString[i] = newFields[i].getName();
                        }
                    }
                }

            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception in doInBackground = " + e);
                e.printStackTrace();
                return e;
            }

            return null;
        }

        @Override
        public void onPostExecute(Object obj) {
            if (obj == null) {
                if (newMethodsString.length > 0) {
                    spinnerMethods.setAdapter(createSpinnerAdapter(newMethodsString));
                    spinnerMethods.setSelection(0);
                    Log.d(LOG_TAG, "Methods count = " + newMethodsString.length);
                } else {
                    spinnerMethods.setAdapter(createSpinnerAdapter(new String[]{getString(R.string.app_field_no)}));
                    spinnerMethods.setSelection(0);
                    Log.d(LOG_TAG, "Methods count = 0");
                }
                if (newFieldsString.length > 0) {
                    spinnerFields.setAdapter(createSpinnerAdapter(newFieldsString));
                    spinnerFields.setSelection(0);
                    Log.d(LOG_TAG, "Fields count = " + newFieldsString.length);
                } else {
                    spinnerFields.setAdapter(createSpinnerAdapter(new String[]{getString(R.string.app_field_no)}));
                    spinnerFields.setSelection(0);
                    Log.d(LOG_TAG, "Fields count = 0");
                }
                Log.d(LOG_TAG, "Download Ready");
                showToast(getString(R.string.app_ready) + "", Toast.LENGTH_SHORT);
            } else {
                Log.d(LOG_TAG, "Download Error");
                showToast(getString(R.string.app_error) + "", Toast.LENGTH_LONG);
            }


        }
    }
}
