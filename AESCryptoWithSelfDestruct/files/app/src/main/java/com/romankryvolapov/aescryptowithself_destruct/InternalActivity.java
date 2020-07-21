package com.romankryvolapov.aescryptowithself_destruct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class InternalActivity extends AppCompatActivity {


    Button buttonEraseAll1;
    Button buttonEraseAll2;
    Button buttonEraseAll3;
    Button buttonDelKey;
    Button buttonGenerateKey;
    Button buttonAddKeyToBase;
    Button buttonCopyPublicKey;
    Button buttonPastePublicKey;
    Button buttonDeletePublicKey;
    Button buttonCopyPrivateKey;
    Button buttonPastePrivateKey;
    Button buttonDeletePrivateKey;
    Button buttonEncryptionText;
    Button buttonDecryptionText;
    Button buttonSentText;
    Button buttonCopyOriginalText;
    Button buttonPasteOriginalText;
    Button buttonDeleteOriginalText;
    Button buttonCopyEncryptedText;
    Button buttonPasteEncryptedText;
    Button buttonDeleteEncryptedText;
    Spinner spinnerKeys;
    Spinner spinnerAlgorithm;
    TextView textViewKey1;
    TextView textViewKey2;
    EditText editTextResult;
    TextView textViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);
        setStatusBarColor();
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
         buttonSentText = findViewById(R.id.buttonSentText);
         buttonCopyOriginalText = findViewById(R.id.buttonCopyOriginalText);
         buttonPasteOriginalText = findViewById(R.id.buttonPasteOriginalText);
         buttonDeleteOriginalText = findViewById(R.id.buttonDeleteOriginalText);
         buttonCopyEncryptedText = findViewById(R.id.buttonCopyEncryptedText);
         buttonPasteEncryptedText = findViewById(R.id.buttonPasteEncryptedText);
         buttonDeleteEncryptedText = findViewById(R.id.buttonDeleteEncryptedText);
         spinnerKeys = findViewById(R.id.spinnerKeys);
         spinnerAlgorithm = findViewById(R.id.spinnerAlgorithm);
         textViewKey1 = findViewById(R.id.textViewKey1);
         textViewKey2 = findViewById(R.id.textViewKey2);
         editTextResult = findViewById(R.id.editTextResult);
         textViewResult = findViewById(R.id.textViewResult);

         String [] algorithms = new String[] { "RSA 2048 bit", "RSA 1024 bit", "DSA 1024 bit", "DiffieHellman 1024 bit" };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,algorithms){

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
        spinnerAlgorithm.setAdapter(adapter);
        spinnerAlgorithm.setSelection(0);

    }

    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }
}
