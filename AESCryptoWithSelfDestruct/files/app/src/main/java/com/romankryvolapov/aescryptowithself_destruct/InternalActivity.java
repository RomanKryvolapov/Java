package com.romankryvolapov.aescryptowithself_destruct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class InternalActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Status";

    private Dialog dialog;
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
    private Button buttonDialogNo;
    private Button buttonDialogYes;
    private Button buttonUse;
    private Spinner spinnerKeys;
    private Spinner spinnerAlgorithm;
    private TextView textViewKey1;
    private TextView textViewKey2;
    private TextView textField1;
    private TextView textField2;
    private String text1 = "";
    private String text2 = "";
    private String pass = "";
    private SharedPreferences sharedPreferences;
    EditText textDialog;

    private Boolean EraseAll1 = false;
    private Boolean EraseAll2 = false;
    private Boolean EraseAll3 = false;
    private int substring = 30;

    private String publicKeyString = "";
    private String privateKeyString = "";
    private int spinnerAlgorithmPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);
        setStatusBarColor();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pass = bundle.getString("pass");
        }

        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

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
        textField1.setMovementMethod(new ScrollingMovementMethod());
        textField1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newDialog();


            }
        });
        textField2 = findViewById(R.id.textField2);
        textField2.setMovementMethod(new ScrollingMovementMethod());
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


        String[] algorithms = new String[]{
                "RSA NoPadding 4096 bit",
                "RSA NoPadding 3072 bit",
                "RSA NoPadding 2048 bit",
                "RSA NoPadding 1024 bit",
                "RSA NoPadding 515 bit",
                "RSA None PKCS1 4096 bit",
                "RSA None PKCS1 3072 bit",
                "RSA None PKCS1 2048 bit",
                "RSA None PKCS1 1024 bit",
                "RSA None PKCS1 515 bit",
                "RSA ECB PKCS1 4096 bit",
                "RSA ECB PKCS1 3072 bit",
                "RSA ECB PKCS1 2048 bit",
                "RSA ECB PKCS1 1024 bit",
                "RSA ECB PKCS1 515 bit",
                "RSA None OAEP SHA-1 MGF 4096 bit",
                "RSA None OAEP SHA-1 MGF1 3072 bit",
                "RSA None OAEP SHA-1 MGF1 2048 bit",
                "RSA None OAEP SHA-1 MGF1 1024 bit",
                "RSA None OAEP SHA-1 MGF1 515 bit",
                "RSA ECB OAEP SHA-1 MGF1 4096 bit",
                "RSA ECB OAE SHA-1 MGF1 3072 bit",
                "RSA ECB OAEP SHA-1 MGF1 2048 bit",
                "RSA ECB OAEP SHA-1 MGF1 1024 bit",
                "RSA ECB OAEP SHA-1 MGF1 515 bit",
                "RSA None OAEP SHA-256 MGF1 4096 bit",
                "RSA None OAEP SHA-256 MGF1 3072 bit",
                "RSA None OAEP SHA-256 MGF1 2048 bit",
                "RSA None OAEP SHA-256 MGF1 1024 bit",
                "RSA None OAEP SHA-256 MGF1 515 bit",
                "RSA ECB OAEP SHA-256 MGF1 4096 bit",
                "RSA ECB OAEP SHA-256 MGF1 3072 bit",
                "RSA ECB OAEP SHA-256 MGF1 2048 bit",
                "RSA ECB OAEP SHA-256 MGF1 1024 bit",
                "RSA ECB OAEP SHA-256 MGF1 515 bit",
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

        for (int i = 0; i < algorithms.length; i++) {
            String temp = (i + 1) + ". " + algorithms[i];
            algorithms[i] = temp;
        }

        ArrayAdapter<String> arrayAdapterAlgorithm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, algorithms) {
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
        spinnerAlgorithm.setSelection(0);

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
                if (publicKeyString.length() > 0)
                    copyToClipboard(publicKeyString);
            }
        });
        buttonCopyPrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                text1 = textField1.getText().toString();
                if (text1.length() > 0 && publicKeyString.length() > 0) {
                    text2 = encryptionText(text1, publicKeyString);
                    if (text2.length() > 0) {
                        textField1.setText(text1);
                        textField2.setText(text2);
                    }
                }
            }
        });
        buttonDecryptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonPastePublicKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = pasteFromClipboard();
                if (key.length() > 0)
                    if (encryptionText("qwerty", key).length() > 0) {
                        publicKeyString = key;
                        textViewKey1.setText(publicKeyString.substring(0, substring) + " ...");
                    }
            }
        });
        buttonCopyOriginalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = textField1.getText().toString();
                if (text1.length() > 0) {
                    copyToClipboard(text1);
                    textField1.setText(text1);
                }
            }
        });
        buttonCopyEncryptedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text2.length() > 0) {
                    copyToClipboard(text2);
                    textField2.setText(text2);
                }
            }
        });
        buttonPasteOriginalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = pasteFromClipboard();
                if (text1.length() > 0) {
                    textField1.setText(text1);
                }
            }
        });
        buttonPasteEncryptedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2 = pasteFromClipboard();
                if (text2.length() > 0) {
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
        buttonPastePrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                qqq
            }
        });

    }

    private void buttonEraseAll() {
        if (EraseAll1 && EraseAll2 && EraseAll3) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            this.finish();
        }
    }
    private void newDialog(){
        dialog = new Dialog(InternalActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        textDialog = dialog.findViewById(R.id.textDialog);
        textDialog.setText(text1);

        buttonDialogNo = dialog.findViewById(R.id.buttonDialogNo);
        buttonDialogYes = dialog.findViewById(R.id.buttonDialogYes);
        buttonDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonDialogYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1 = textDialog.getText().toString();
                textField1.setText(text1);
                dialog.dismiss();
            }
        });
        dialog.show();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(textDialog.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        textDialog.requestFocus();
    }

    private String encryptionText(String text, String key) {
        if (text.length() > 0) {
            spinnerAlgorithmPosition = spinnerAlgorithm.getSelectedItemPosition();
            Log.d(LOG_TAG, "spinnerAlgorithmPosition = " + spinnerAlgorithmPosition);
            switch (spinnerAlgorithmPosition) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    return encryptionText_RSA(text, key, "RSA", "RSA");
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    return encryptionText_RSA(text, key, "RSA", "RSA/NONE/PKCS1Padding");
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    return encryptionText_RSA(text, key, "RSA", "RSA/ECB/PKCS1Padding");
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    return encryptionText_RSA(text, key, "RSA", "RSA/NONE/OAEPwithSHA-1andMGF1Padding");
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                    return encryptionText_RSA(text, key, "RSA", "RSA/ECB/OAEPwithSHA-1andMGF1Padding");
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    return encryptionText_RSA(text, key, "RSA", "RSA/NONE/OAEPwithSHA-256andMGF1Padding");
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                    return encryptionText_RSA(text, key, "RSA", "RSA/ECB/OAEPwithSHA-256andMGF1Padding");
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:

                    return null;
            }
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
            toast.show();
        }
        return null;
    }

    private void copyToClipboard(String text) {
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

    private String encryptionText_RSA(String text, String key, String keyTypeField1, String keyTypeField2) {
        try {

            byte[] publicBytes = Base64.decode(key, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(keyTypeField1);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

//            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
//            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

//            Log.d(LOG_TAG, "publicKey = " + publicKey.toString());
//            publicKeyString = Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT);
//            textViewKey1.setText(publicKeyString);
//            textViewKey2.setText(privateKeyString);

            Cipher cipher = Cipher.getInstance(keyTypeField2);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encrypted = cipher.doFinal(text.getBytes());
            text2 = Base64.encodeToString(encrypted, Base64.DEFAULT);
            Log.d(LOG_TAG, "publicKeyString length = " + publicKeyString.length() + " privateKeyString length = " + privateKeyString.length());
            return text2;
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, getString(R.string.exception), Toast.LENGTH_SHORT);
            toast.show();
            return "";
        }
    }

//    private String decryptionText(String text) {
//        text1 = textField1.getText().toString();
//        if (text1.length() > 0) {
//            spinnerAlgorithmPosition = spinnerAlgorithm.getSelectedItemPosition();
//            switch (spinnerAlgorithmPosition) {
//                case 0:
//
//
//                    return "";
//                case 1:
//
//
//                    return "";
//                case 2:
//
//
//                    return "";
//            }
//        } else {
//            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.exception), Toast.LENGTH_SHORT);
//            toast.show();
//
//        }
//        return null;
//    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private void generateKey() {
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

    class GenerateRSA extends AsyncTask<Void, Void, Void> {
        int keySize;
        String keyTypeField1;

        public GenerateRSA(int keySize, String keyTypeField1) {
            this.keySize = keySize;
            this.keyTypeField1 = keyTypeField1;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textViewKey1.setText(publicKeyString.substring(0, substring) + " ...");
            textViewKey2.setText(privateKeyString.substring(0, substring) + " ...");
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
                Log.d(LOG_TAG, "publicKeyString length = " + publicKeyString.length() + " privateKeyString length = " + privateKeyString.length());
                return null;
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception!!!");
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
            textViewKey1.setText("NO");
            textViewKey2.setText(privateKeyString.substring(0, substring) + " ...");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(keyTypeField1);
                keyGenerator.init(keySize);
                SecretKey secretKey = keyGenerator.generateKey();
                privateKeyString = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
                publicKeyString = "";
                return null;
            } catch (Exception e) {
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
