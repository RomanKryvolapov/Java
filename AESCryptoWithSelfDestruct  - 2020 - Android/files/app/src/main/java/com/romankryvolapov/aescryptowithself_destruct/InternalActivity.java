package com.romankryvolapov.aescryptowithself_destruct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class InternalActivity extends AppCompatActivity {

    private final String LOG_TAG = "Status";
    private Dialog dialogEditText;
    private Dialog dialogKey;
    private Dialog dialogManual;
    private Button buttonSeeManual;
    private Button buttonSendKeys;
    private TextView textViewManual;
    private Button buttonDialogKeyCancel;
    private Button buttonCloseManual;
    private Button buttonDialogKeySave;
    private TextView textViewDialogKey;
    private Button buttonEraseAll1;
    private Button buttonEraseAll2;
    private Button buttonEraseAll3;
    private Button buttonDelKey;
    private Button buttonGenerateKey;
    private Button buttonAddKeyToBase;
    private Button buttonCopyPublicKey;
    private Button buttonPastePublicKey;
    private Button buttonDeletePublicKey;
    private Button buttonCopyPrivateKey;
    private Button buttonPastePrivateKey;
    private Button buttonDeletePrivateKey;
    private Button buttonEncryptionText;
    private Button buttonDecryptionText;
    private Button buttonSendText;
    private Button buttonCopyOriginalText;
    private Button buttonPasteOriginalText;
    private Button buttonDeleteOriginalText;
    private Button buttonCopyEncryptedText;
    private Button buttonPasteEncryptedText;
    private Button buttonDeleteEncryptedText;
    private Button buttonDialogEditTextNo;
    private Button buttonDialogEditTextYes;
    private Button buttonUse;
    private Button buttonDialogCopyKey;
    private Button buttonDialogPasteKey;
    private Button buttonDialogDeleteKey;
    private Spinner spinnerKeys;
    private Spinner spinnerAlgorithm;
    private TextView textViewKey1;
    private TextView textViewKey2;
    private TextView textField1;
    private TextView textField2;
    private String text1 = "";
    private String text2 = "";
    private String pass = "";
    private String keyString = "";
    private SharedPreferences sharedPreferences;
    private EditText textDialogEditText;
    private Boolean EraseAll1 = false;
    private Boolean EraseAll2 = false;
    private Boolean EraseAll3 = false;
    private int substring = 30;
    private int substring2 = 14;
    private int totalKeys = 0;
    private ArrayList<String> keys = new ArrayList<>();
    private String publicKeyString = "";
    private String privateKeyString = "";
    private int spinnerAlgorithmPosition = 0;
    private int spinnerAlgorithmPositionOnStart = 0;
    private String receivedText;

    private String[] algorithms = new String[]{
            "RSA NoPadding 4096 bit",
            "RSA NoPadding 3072 bit",
            "RSA NoPadding 2048 bit",
            "RSA NoPadding 1024 bit",
            "RSA NoPadding 512 bit",
            "RSA None PKCS1 4096 bit",
            "RSA None PKCS1 3072 bit",
            "RSA None PKCS1 2048 bit",
            "RSA None PKCS1 1024 bit",
            "RSA None PKCS1 512 bit",
            "RSA ECB PKCS1 4096 bit",
            "RSA ECB PKCS1 3072 bit",
            "RSA ECB PKCS1 2048 bit",
            "RSA ECB PKCS1 1024 bit",
            "RSA ECB PKCS1 512 bit",
            "RSA None OAEP SHA-1 MGF 4096 bit",
            "RSA None OAEP SHA-1 MGF1 3072 bit",
            "RSA None OAEP SHA-1 MGF1 2048 bit",
            "RSA None OAEP SHA-1 MGF1 1024 bit",
            "RSA None OAEP SHA-1 MGF1 512 bit",
            "RSA ECB OAEP SHA-1 MGF1 4096 bit",
            "RSA ECB OAE SHA-1 MGF1 3072 bit",
            "RSA ECB OAEP SHA-1 MGF1 2048 bit",
            "RSA ECB OAEP SHA-1 MGF1 1024 bit",
            "RSA ECB OAEP SHA-1 MGF1 512 bit",
            "RSA None OAEP SHA-256 MGF1 4096 bit",
            "RSA None OAEP SHA-256 MGF1 3072 bit",
            "RSA None OAEP SHA-256 MGF1 2048 bit",
            "RSA None OAEP SHA-256 MGF1 1024 bit",
            "RSA None OAEP SHA-256 MGF1 512 bit",
            "RSA ECB OAEP SHA-256 MGF1 4096 bit",
            "RSA ECB OAEP SHA-256 MGF1 3072 bit",
            "RSA ECB OAEP SHA-256 MGF1 2048 bit",
            "RSA ECB OAEP SHA-256 MGF1 1024 bit",
            "RSA ECB OAEP SHA-256 MGF1 512 bit",
            "AES GCM NoPadding 256 bit",
            "AES CBC NoPadding 256 bit",
            "AES ECB NoPadding 256 bit",
            "AES CBC PKCS5Padding 256 bit",
            "AES ECB PKCS5Padding 256 bit",
            "AES GCM NoPadding 128 bit",
            "AES CBC NoPadding 128 bit",
            "AES ECB NoPadding 128 bit",
            "AES CBC PKCS5Padding 128 bit",
            "AES ECB PKCS5Padding 128 bit"
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);
        setStatusBarColor();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pass = bundle.getString("pass");
            receivedText = bundle.getString("receivedText");
        }


        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("spinnerAlgorithmPositionOnStart")) {
            spinnerAlgorithmPositionOnStart = sharedPreferences.getInt("spinnerAlgorithmPositionOnStart", 0);
        }


        findAllElements();
        addListiners();
        arrayAdapterAlgorithm();
//        if (sharedPreferences.contains("totalKeys")) {
//            totalKeys = sharedPreferences.getInt("totalKeys", 0);
//            if (totalKeys > 0) {
//                textViewKey1.setText(getString(R.string.wait));
//                textViewKey2.setText(getString(R.string.wait));
//            }
//        }
        GetAllKeysFromSharedPreferences getAllKeysFromSharedPreferences = new GetAllKeysFromSharedPreferences();
        getAllKeysFromSharedPreferences.execute();
    }

    private void findAllElements() {
        buttonEraseAll1 = findViewById(R.id.buttonEraseAll1);
        buttonEraseAll2 = findViewById(R.id.buttonEraseAll2);
        buttonEraseAll3 = findViewById(R.id.buttonEraseAll3);
        buttonDelKey = findViewById(R.id.buttonDelKey);
        buttonGenerateKey = findViewById(R.id.buttonGenerateKey);
        buttonAddKeyToBase = findViewById(R.id.buttonAddKeyToBase);
        buttonCopyPublicKey = findViewById(R.id.buttonCopyPublicKey);
        buttonPastePublicKey = findViewById(R.id.buttonPastePublicKey);
        buttonDeletePublicKey = findViewById(R.id.buttonDeletePublicKey);
        buttonCopyPrivateKey = findViewById(R.id.buttonCopyPrivateKey);
        buttonPastePrivateKey = findViewById(R.id.buttonPastePrivateKey);
        buttonDeletePrivateKey = findViewById(R.id.buttonDeletePrivateKey);
        buttonEncryptionText = findViewById(R.id.buttonEncryptionText);
        buttonDecryptionText = findViewById(R.id.buttonDecryptionText);
        buttonSendText = findViewById(R.id.buttonSendText);
        buttonCopyOriginalText = findViewById(R.id.buttonCopyOriginalText);
        buttonPasteOriginalText = findViewById(R.id.buttonPasteOriginalText);
        buttonDeleteOriginalText = findViewById(R.id.buttonDeleteOriginalText);
        buttonCopyEncryptedText = findViewById(R.id.buttonCopyEncryptedText);
        buttonPasteEncryptedText = findViewById(R.id.buttonPasteEncryptedText);
        buttonDeleteEncryptedText = findViewById(R.id.buttonDeleteEncryptedText);
        buttonUse = findViewById(R.id.buttonUse);
        spinnerKeys = findViewById(R.id.spinnerKeys);
        spinnerAlgorithm = findViewById(R.id.spinnerAlgorithm);
        textViewKey1 = findViewById(R.id.textViewKey1);
        textViewKey2 = findViewById(R.id.textViewKey2);
        textField1 = findViewById(R.id.textField1);
        textField2 = findViewById(R.id.textField2);
        buttonSeeManual = findViewById(R.id.buttonSeeManual);
        buttonSendKeys = findViewById(R.id.buttonSendKeys);
    }

    private void addListiners() {

        textField1.setMovementMethod(new ScrollingMovementMethod());
        textField2.setMovementMethod(new ScrollingMovementMethod());

        textField1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogEditText();
            }
        });
        textViewKey1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogKey(false);
            }
        });
        textViewKey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogKey(true);
            }
        });
        buttonEraseAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseAll1 = true;
                buttonEraseAll1.setText("OK");
                buttonEraseAll();
            }
        });
        buttonEraseAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseAll2 = true;
                buttonEraseAll2.setText("OK");
                buttonEraseAll();
            }
        });
        buttonEraseAll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseAll3 = true;
                buttonEraseAll3.setText("OK");
                buttonEraseAll();
            }
        });
        buttonDeletePublicKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicKeyString = "";
                textViewKey1.setText(publicKeyString);
            }
        });
        buttonDeletePrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privateKeyString = "";
                textViewKey2.setText(privateKeyString);
            }
        });
        buttonCopyPublicKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publicKeyString != null)
                    if (publicKeyString.length() > 0)
                        copyToClipboard(publicKeyString);
            }
        });
        buttonCopyPrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (privateKeyString != null)
                    if (privateKeyString.length() > 0)
                        copyToClipboard(privateKeyString);
            }
        });
        buttonGenerateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateKey();
            }
        });
        buttonEncryptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptionText();
            }
        });
        buttonDecryptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decryptionText();
            }
        });
        buttonPastePublicKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = pasteFromClipboard();
                if (key != null)
                    if (key.length() > 0) {
                        publicKeyString = key;
                        textViewKey1.setText(substringer(publicKeyString, substring, true));
                    } else {
                        publicKeyString = "";
                        textViewKey1.setText("");
                    }
            }
        });
        buttonPastePrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = pasteFromClipboard();
                if (key != null)
                    if (key.length() > 0) {
                        privateKeyString = key;
                        textViewKey2.setText(substringer(privateKeyString, substring, true));
                    } else {
                        privateKeyString = "";
                        textViewKey2.setText("");
                    }
            }
        });
        buttonCopyOriginalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = textField1.getText().toString();
                if (text1 != null)
                    if (text1.length() > 0) {
                        copyToClipboard(text1);
                    }
            }
        });
        buttonCopyEncryptedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text2 != null)
                    if (text2.length() > 0) {
                        copyToClipboard(text2);
                    }
            }
        });
        buttonPasteOriginalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = pasteFromClipboard();
                if (text1 != null)
                    if (text1.length() > 0) {
                        textField1.setText(text1);
                    } else {
                        text1 = "";
                        textField1.setText(text1);
                    }
            }
        });
        buttonPasteEncryptedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2 = pasteFromClipboard();
                if (text2 != null)
                    if (text2.length() > 0) {
                        textField2.setText(text2);
                    } else {
                        text2 = "";
                        textField2.setText(text2);
                    }
            }
        });
        buttonDeleteOriginalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = "";
                textField1.setText(text1);
            }
        });
        buttonDeleteEncryptedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2 = "";
                textField2.setText(text2);
            }
        });
        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keys.size() > 0) {
                    publicKeyString = keys.get(spinnerKeys.getSelectedItemPosition() * 3);
                    privateKeyString = keys.get(spinnerKeys.getSelectedItemPosition() * 3 + 1);
                    textViewKey1.setText(substringer(publicKeyString, substring, true));
                    textViewKey2.setText(substringer(privateKeyString, substring, true));
                    String temp = keys.get(spinnerKeys.getSelectedItemPosition() * 3 + 2);
                    for (int i = 0; i < algorithms.length; i++) {
                        if (temp.equals(algorithms[i])) {
                            spinnerAlgorithm.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });
        buttonAddKeyToBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publicKeyString != null && privateKeyString != null)
                    if (publicKeyString.length() > 0 || privateKeyString.length() > 0) {
                        textViewKey1.setText(getString(R.string.wait));
                        textViewKey2.setText(getString(R.string.wait));
                        addCurrentKeyToHashMap();
                        AddAllKeysToSharedPreferences addAllKeysToSharedPreferences = new AddAllKeysToSharedPreferences();
                        addAllKeysToSharedPreferences.execute();
                    }

            }
        });
        buttonDelKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keys.size() > 0) {
                    textViewKey1.setText(getString(R.string.wait));
                    textViewKey2.setText(getString(R.string.wait));
                    totalKeys--;
                    keys.remove(spinnerKeys.getSelectedItemPosition() * 3 + 2);
                    keys.remove(spinnerKeys.getSelectedItemPosition() * 3 + 1);
                    keys.remove(spinnerKeys.getSelectedItemPosition() * 3);
                    AddAllKeysToSharedPreferences addAllKeysToSharedPreferences = new AddAllKeysToSharedPreferences();
                    addAllKeysToSharedPreferences.execute();
                }
            }
        });
        buttonSeeManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogManual();
            }
        });
        buttonSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
            }
        });
        buttonSendKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKey();
            }
        });
        if (receivedText != null)
            if (receivedText.length() > 0) {
                text1 = receivedText;
                textField1.setText(text1);
            }
    }

    private String substringer(String text, int substringSize, boolean addDots) {
        StringBuilder stringBuilder = new StringBuilder();
        if (text != null)
            if (text.length() > substringSize && addDots) {
                stringBuilder.append(text.substring(0, substringSize));
                stringBuilder.append(" ...");
                return stringBuilder.toString();
            } else if (text.length() > substringSize && !addDots) {
                stringBuilder.append(text.substring(0, substringSize));
                return stringBuilder.toString();
            } else {
                stringBuilder.append(text);
                return stringBuilder.toString();
            }
        return "";
    }

    private void buttonEraseAll() {
        if (EraseAll1 && EraseAll2 && EraseAll3) {
            SharedPreferences.Editor sharedPreferenceseditor = sharedPreferences.edit();
            sharedPreferenceseditor.clear();
            sharedPreferenceseditor.apply();
            this.finish();
        }
    }

    private void arrayAdapterAlgorithm() {
        String[] algorithmsWithItemNumber = new String[algorithms.length];
        for (int i = 0; i < algorithms.length; i++) {
            algorithmsWithItemNumber[i] = (i + 1) + ". " + algorithms[i];
        }
        ArrayAdapter<String> arrayAdapterAlgorithm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, algorithmsWithItemNumber) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.WHITE);
                ((TextView) view).setGravity(Gravity.CENTER);
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                return view;
            }
        };
        spinnerAlgorithm.setAdapter(arrayAdapterAlgorithm);
        spinnerAlgorithm.setSelection(spinnerAlgorithmPositionOnStart);
        spinnerAlgorithm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSpinnerAlgorithmPositionOnStart(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void arrayAdapterKeys() {

        String[] keyWithItemNumber = new String[totalKeys];
        int temp = 0;
        String a = "";
        String b = "";
        for (int i = 0; i < totalKeys; i++) {
            a = substringer(keys.get(temp), substring2, false);
            temp++;
            b = substringer(keys.get(temp), substring2, false);
            temp++;
            temp++;
            keyWithItemNumber[i] = "" + (i + 1) + ". " + getText(R.string.key1) + " " + a + "... " + getText(R.string.key2) + " " + b + "...";

        }
//        Log.d(LOG_TAG, "keyWithItemNumber size = " + keyWithItemNumber.length);
        ArrayAdapter<String> arrayAdapterKeys = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, keyWithItemNumber) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.WHITE);
                ((TextView) view).setGravity(Gravity.CENTER);
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                return view;
            }
        };
        spinnerKeys.setAdapter(arrayAdapterKeys);
        spinnerKeys.setSelection(totalKeys - 1);


    }

    private void sendText() {
        if (text2 != null)
            if (text2.length() > 0) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, text2);
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
    }

    private void sendKey() {
        StringBuilder text = new StringBuilder();
        text.append(getText(R.string.send_text_1));
        text.append("\n");
        if (spinnerAlgorithm.getSelectedItemPosition() >= 35) {
            text.append(getText(R.string.send_text_3));
        } else {
            text.append(getText(R.string.send_text_4));
        }
        text.append("\n");
        text.append(getText(R.string.send_text_5));
        text.append("\n");
        text.append(algorithms[spinnerAlgorithm.getSelectedItemPosition()]);
        text.append("\n");
        text.append(getText(R.string.send_text_2));
        text.append("\n");
        if (spinnerAlgorithm.getSelectedItemPosition() >= 35) {
            text.append(privateKeyString);
        } else {
            text.append(publicKeyString);
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, text.toString());
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void addCurrentKeyToHashMap() {
        spinnerAlgorithmPosition = spinnerAlgorithm.getSelectedItemPosition();
        totalKeys++;
        keys.add(publicKeyString);
        keys.add(privateKeyString);
        keys.add(algorithms[spinnerAlgorithmPosition]);
        Log.d(LOG_TAG, "keys size = " + keys.size());
    }

    private void newDialogEditText() {
        dialogEditText = new Dialog(InternalActivity.this);
        dialogEditText.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEditText.setCancelable(false);
        dialogEditText.setContentView(R.layout.editfield1);

        textDialogEditText = dialogEditText.findViewById(R.id.dialogEditField1text);
        textDialogEditText.setText(text1);

        buttonDialogEditTextNo = dialogEditText.findViewById(R.id.buttonDialogEditField1No);
        buttonDialogEditTextYes = dialogEditText.findViewById(R.id.buttonDialogEditField1Yes);
        buttonDialogEditTextNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditText.dismiss();
            }
        });
        buttonDialogEditTextYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = textDialogEditText.getText().toString();
                textField1.setText(text1);
                dialogEditText.dismiss();
            }
        });
        dialogEditText.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialogEditText.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialogEditText.show();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(textDialogEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        textDialogEditText.requestFocus();
    }

    private void setSpinnerAlgorithmPositionOnStart(int position) {
        SharedPreferences.Editor sharedPreferenceseditor = sharedPreferences.edit();
        sharedPreferenceseditor.putInt("spinnerAlgorithmPositionOnStart", position);
        sharedPreferenceseditor.apply();
        sharedPreferenceseditor = null;
    }

    private void newDialogManual() {
        dialogManual = new Dialog(InternalActivity.this);
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

    private void newDialogKey(final boolean isPrivate) {
        if (isPrivate)
            keyString = privateKeyString;
        else
            keyString = publicKeyString;
        dialogKey = new Dialog(InternalActivity.this);
        dialogKey.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogKey.setCancelable(false);
        dialogKey.setContentView(R.layout.key);

        textViewDialogKey = dialogKey.findViewById(R.id.textViewManual);
        if (keyString != null)
            if (keyString.length() > 0)
                textViewDialogKey.setText(keyString);

        buttonDialogKeyCancel = dialogKey.findViewById(R.id.buttonCloseManual);
        buttonDialogKeyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogKey.dismiss();
            }
        });
        buttonDialogKeySave = dialogKey.findViewById(R.id.buttonDialogKeySave);
        buttonDialogKeySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPrivate) {
                    privateKeyString = substringer(keyString, substring, true);
                } else {
                    publicKeyString = substringer(keyString, substring, true);
                }
                dialogKey.dismiss();
            }
        });
        buttonDialogPasteKey = dialogKey.findViewById(R.id.buttonDialogPasteKey);
        buttonDialogPasteKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyString = pasteFromClipboard();
                textViewDialogKey.setText(keyString);
            }
        });
        buttonDialogDeleteKey = dialogKey.findViewById(R.id.buttonDialogDeleteKey);
        buttonDialogDeleteKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyString = "";
                textViewDialogKey.setText(keyString);
            }
        });
        buttonDialogCopyKey = dialogKey.findViewById(R.id.buttonDialogCopyKey);
        buttonDialogCopyKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(keyString);
            }
        });
        dialogKey.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialogKey.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialogKey.show();
    }

    private void encryptionText() {
        spinnerAlgorithmPosition = spinnerAlgorithm.getSelectedItemPosition();
        Log.d(LOG_TAG, "spinnerAlgorithmPosition = " + spinnerAlgorithmPosition);
        switch (spinnerAlgorithmPosition) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                encryptionText_RSA("RSA", "RSA");
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                encryptionText_RSA("RSA", "RSA/NONE/PKCS1Padding");
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                encryptionText_RSA("RSA", "RSA/ECB/PKCS1Padding");
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                encryptionText_RSA("RSA", "RSA/NONE/OAEPwithSHA-1andMGF1Padding");
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                encryptionText_RSA("RSA", "RSA/ECB/OAEPwithSHA-1andMGF1Padding");
                break;
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                encryptionText_RSA("RSA", "RSA/NONE/OAEPwithSHA-256andMGF1Padding");
                break;
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                encryptionText_RSA("RSA", "RSA/ECB/OAEPwithSHA-256andMGF1Padding");
                break;
            case 35:
            case 40:
                encryptionText_AES("AES", "AES/GCM/NoPadding");
                break;
            case 36:
            case 41:
                encryptionText_AES("AES", "AES/CBC/NoPadding");
                break;
            case 37:
            case 42:
                encryptionText_AES("AES", "AES/ECB/NoPadding");
                break;
            case 38:
            case 43:
                encryptionText_AES("AES", "AES/CBC/PKCS5Padding");
                break;
            case 39:
            case 44:
                encryptionText_AES("AES", "AES/ECB/PKCS5Padding");
                break;
        }
    }

    private void decryptionText() {
        spinnerAlgorithmPosition = spinnerAlgorithm.getSelectedItemPosition();
        Log.d(LOG_TAG, "spinnerAlgorithmPosition = " + spinnerAlgorithmPosition);
        switch (spinnerAlgorithmPosition) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                decryptionText_RSA("RSA", "RSA");
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                decryptionText_RSA("RSA", "RSA/NONE/PKCS1Padding");
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                decryptionText_RSA("RSA", "RSA/ECB/PKCS1Padding");
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                decryptionText_RSA("RSA", "RSA/NONE/OAEPwithSHA-1andMGF1Padding");
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                decryptionText_RSA("RSA", "RSA/ECB/OAEPwithSHA-1andMGF1Padding");
                break;
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                decryptionText_RSA("RSA", "RSA/NONE/OAEPwithSHA-256andMGF1Padding");
                break;
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                decryptionText_RSA("RSA", "RSA/ECB/OAEPwithSHA-256andMGF1Padding");
                break;
            case 35:
            case 40:
                decryptionText_AES("AES", "AES/GCM/NoPadding");
                break;
            case 36:
            case 41:
                decryptionText_AES("AES", "AES/CBC/NoPadding");
                break;
            case 37:
            case 42:
                decryptionText_AES("AES", "AES/ECB/NoPadding");
                break;
            case 38:
            case 43:
                decryptionText_AES("AES", "AES/CBC/PKCS5Padding");
                break;
            case 39:
            case 44:
                decryptionText_AES("AES", "AES/ECB/PKCS5Padding");
                break;
        }
    }

    private void copyToClipboard(String text) {
        if (text != null)
            if (text.length() > 0) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text", text);
                clipboardManager.setPrimaryClip(clipData);
                Toast toast = Toast.makeText(this, getString(R.string.text_copied), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
                toast.show();
            }
    }

    private void encryptionText_AES(String keyTypeField1, String keyTypeField2) {
        try {
            text1 = textField1.getText().toString();
            if (text1 != null && privateKeyString != null)
                if (text1.length() > 0 && privateKeyString.length() > 0) {
                    byte[] publicBytes = Base64.decode(privateKeyString, Base64.DEFAULT);
                    SecretKeySpec skeySpec = new SecretKeySpec(publicBytes, keyTypeField1);
                    Cipher cipher = Cipher.getInstance(keyTypeField2);
                    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                    byte[] encryptedBytes = cipher.doFinal(text1.getBytes());
                    text2 = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
                    if (text2 != null)
                        if (text2.length() > 0) {
                            textField2.setText(text2);
                        }
                } else {
                    Log.d(LOG_TAG, "length = 0");
                    Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
                    toast.show();
                }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception = " + e);
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void encryptionText_RSA(String keyTypeField1, String keyTypeField2) {
        try {
            text1 = textField1.getText().toString();
            if (text1 != null && publicKeyString != null)
                if (text1.length() > 0 && publicKeyString.length() > 0) {
                    byte[] publicBytes = Base64.decode(publicKeyString, Base64.DEFAULT);
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
                    KeyFactory keyFactory = KeyFactory.getInstance(keyTypeField1);
                    PublicKey publicKey = keyFactory.generatePublic(keySpec);

                    Cipher cipher = Cipher.getInstance(keyTypeField2);
                    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

//                    Log.d(LOG_TAG, "publicKey Algorithm = " + publicKey.getAlgorithm() + " publicKey Format = " + publicKey.getFormat());
//                    Log.d(LOG_TAG, "publicKeyString length = " + publicKeyString.length() + " privateKeyString length = " + privateKeyString.length());

                    byte[] encryptedBytes = cipher.doFinal(text1.getBytes());
                    text2 = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
                    if (text2 != null)
                        if (text2.length() > 0) {
                            textField2.setText(text2);
                        }
                } else {
//                    Log.d(LOG_TAG, "length = 0");
                    Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
                    toast.show();
                }
        } catch (Exception e) {
//            Log.d(LOG_TAG, "Exception = " + e);
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private void decryptionText_AES(String keyTypeField1, String keyTypeField2) {
        try {
            text1 = textField1.getText().toString();
            if (text1 != null && privateKeyString != null) {
                byte[] privateBytes = Base64.decode(privateKeyString, Base64.DEFAULT);
                SecretKeySpec skeySpec = new SecretKeySpec(privateBytes, keyTypeField1);
                Cipher cipher = Cipher.getInstance(keyTypeField2);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
                byte[] bytesToDecrypt = Base64.decode(text1, Base64.DEFAULT);
                Log.d(LOG_TAG, "publicKeyString length = " + publicKeyString.length() + " privateKeyString length = " + privateKeyString.length());
                byte[] decryptedBytes = cipher.doFinal(bytesToDecrypt);
                text2 = new String(decryptedBytes);
                if (text2 != null)
                    if (text2.length() > 0) {
                        textField2.setText(text2);
                    }
            } else {
                Log.d(LOG_TAG, "length = 0");
                Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
                toast.show();
            }

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception = " + e);
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void decryptionText_RSA(String keyTypeField1, String keyTypeField2) {
        try {
            text1 = textField1.getText().toString();
            if (text1 != null && privateKeyString != null)
                if (text1.length() > 0 && privateKeyString.length() > 0) {
                    byte[] privateBytes = Base64.decode(privateKeyString, Base64.DEFAULT);
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
                    KeyFactory keyFactory = KeyFactory.getInstance(keyTypeField1);
                    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
                    Cipher cipher = Cipher.getInstance(keyTypeField2);
                    cipher.init(Cipher.DECRYPT_MODE, privateKey);
                    Log.d(LOG_TAG, "privateKey Algorithm = " + privateKey.getAlgorithm() + " privateKey Format = " + privateKey.getFormat());
                    Log.d(LOG_TAG, "publicKeyString length = " + publicKeyString.length() + " privateKeyString length = " + privateKeyString.length());
                    byte[] bytesToDecrypt = Base64.decode(text1, Base64.DEFAULT);
                    byte[] decryptedBytes = cipher.doFinal(bytesToDecrypt);
                    text2 = new String(decryptedBytes);
                    if (text2 != null)
                        if (text2.length() > 0) {
                            textField2.setText(text2);
                        }
                } else {
                    Log.d(LOG_TAG, "length = 0");
                    Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
                    toast.show();
                }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception = " + e);
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private void generateKey() {
        textViewKey1.setText(getText(R.string.wait));
        textViewKey2.setText(getText(R.string.wait));
        spinnerAlgorithmPosition = spinnerAlgorithm.getSelectedItemPosition();
        Log.d(LOG_TAG, "spinnerAlgorithmPosition = " + spinnerAlgorithmPosition);
        switch (spinnerAlgorithmPosition) {
            case 0:
            case 5:
            case 10:
            case 15:
            case 20:
            case 25:
            case 30:
                GenerateRSA generateRSA1 = new GenerateRSA(4096, "RSA");
                generateRSA1.execute();
                break;
            case 1:
            case 6:
            case 11:
            case 16:
            case 21:
            case 26:
            case 31:
                GenerateRSA generateRSA2 = new GenerateRSA(3072, "RSA");
                generateRSA2.execute();
                break;
            case 2:
            case 7:
            case 12:
            case 17:
            case 22:
            case 27:
            case 32:
                GenerateRSA generateRSA3 = new GenerateRSA(2048, "RSA");
                generateRSA3.execute();
                break;
            case 3:
            case 8:
            case 13:
            case 18:
            case 23:
            case 28:
            case 33:
                GenerateRSA generateRSA4 = new GenerateRSA(1024, "RSA");
                generateRSA4.execute();
                break;
            case 4:
            case 9:
            case 14:
            case 19:
            case 24:
            case 29:
            case 34:
                GenerateRSA generateRSA5 = new GenerateRSA(512, "RSA");
                generateRSA5.execute();
                break;
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                GenerateAES generateAES1 = new GenerateAES(256, "AES");
                generateAES1.execute();
                break;
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                GenerateAES generateAES2 = new GenerateAES(128, "AES");
                generateAES2.execute();
                break;
        }
    }

    class GetAllKeysFromSharedPreferences extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if (sharedPreferences.contains("totalKeys")) {
                totalKeys = sharedPreferences.getInt("totalKeys", 0);
            }
            if (totalKeys > 0) {
                Log.d(LOG_TAG, "totalKeys = " + totalKeys);
                for (int i = 0; i < totalKeys; i++) {
                    keys.add(MainActivity.decrypt(sharedPreferences.getString(MainActivity.encrypt("key" + i + "public", pass, pass), ""), pass, pass));
                    keys.add(MainActivity.decrypt(sharedPreferences.getString(MainActivity.encrypt("key" + i + "private", pass, pass), ""), pass, pass));
                    keys.add(MainActivity.decrypt(sharedPreferences.getString(MainActivity.encrypt("key" + i + "algorithm", pass, pass), ""), pass, pass));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            arrayAdapterKeys();
//            if (publicKeyString.length() == 0 && privateKeyString.length() == 0 && totalKeys>0) {
//                publicKeyString = keys.get((totalKeys-1) * 3);
//                privateKeyString = keys.get((totalKeys-1) * 3 + 1);
//                String temp = keys.get((totalKeys-1) * 3 + 2);
//                for (int i = 0; i < algorithms.length; i++) {
//                    if (temp.equals(algorithms[i])) {
//                        spinnerAlgorithm.setSelection(i);
//                        break;
//                    }
//                }
//            }
//            textViewKey1.setText(substringer(publicKeyString, substring, true));
//            textViewKey2.setText(substringer(privateKeyString, substring, true));
        }
    }

    class AddAllKeysToSharedPreferences extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences.Editor sharedPreferenceseditor = sharedPreferences.edit();
            sharedPreferenceseditor.clear();
            sharedPreferenceseditor.apply();
            int temp = 0;
            sharedPreferenceseditor.putInt("totalKeys", totalKeys);
            for (int i = 0; i < totalKeys; i++) {
                String a1 = keys.get(temp);
                temp++;
                String b1 = keys.get(temp);
                temp++;
                String c1 = keys.get(temp);
                temp++;
                String a2 = MainActivity.encrypt(a1, pass, pass);
                String b2 = MainActivity.encrypt(b1, pass, pass);
                String c2 = MainActivity.encrypt(c1, pass, pass);
                String a3 = MainActivity.encrypt("key" + i + "public", pass, pass);
                String b3 = MainActivity.encrypt("key" + i + "private", pass, pass);
                String c3 = MainActivity.encrypt("key" + i + "algorithm", pass, pass);
                sharedPreferenceseditor.putString(a3, a2);
                sharedPreferenceseditor.putString(b3, b2);
                sharedPreferenceseditor.putString(c3, c2);
                sharedPreferenceseditor.apply();
            }
            sharedPreferenceseditor = null;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            arrayAdapterKeys();
            textViewKey1.setText(substringer(publicKeyString, substring, true));
            textViewKey2.setText(substringer(privateKeyString, substring, true));
        }
    }

    class GenerateRSA extends AsyncTask<Void, Void, Void> {
        int keySize;
        String keyTypeField1;

        public GenerateRSA(int keySize, String keyTypeField1) {
            this.keySize = keySize;
            this.keyTypeField1 = keyTypeField1;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textViewKey1.setText(substringer(publicKeyString, substring, true));
            textViewKey2.setText(substringer(privateKeyString, substring, true));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyTypeField1);
                keyPairGenerator.initialize(keySize);
                KeyPair keyPair = keyPairGenerator.genKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();
                publicKeyString = Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT);
                privateKeyString = Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT);
                return null;
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception = " + e);
            }
            return null;
        }
    }

    class GenerateAES extends AsyncTask<Void, Void, Void> {
        int keySize;
        String keyTypeField1;

        public GenerateAES(int keySize, String keyTypeField1) {
            this.keySize = keySize;
            this.keyTypeField1 = keyTypeField1;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textViewKey1.setText(getText(R.string.text_only_private_key));
            textViewKey2.setText(substringer(privateKeyString, substring, true));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(keyTypeField1);
                SecureRandom random = new SecureRandom();
                keyGenerator.init(keySize, random);
                SecretKey secretKey = keyGenerator.generateKey();
                privateKeyString = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT).substring(0, 32);
                publicKeyString = "";
                return null;
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception = " + e);
            }
            return null;
        }
    }


    private String pasteFromClipboard() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (!(clipboardManager.hasPrimaryClip())) {
            Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
            toast.show();
            return "";
        } else if (!(clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {
            Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
            toast.show();
            return "";
        } else {
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            return item.getText().toString();
        }
    }
}
