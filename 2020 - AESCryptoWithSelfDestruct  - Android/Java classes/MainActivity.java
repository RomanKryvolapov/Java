package com.romankryvolapov.aescryptowithself_destruct;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "Status";
    private Button buttonPass1;
    private Button buttonPass2;
    private Button buttonPass3;
    private Button buttonPass4;
    private Button buttonPass5;
    private Button buttonPass6;
    private Button buttonPass7;
    private Button buttonPass8;
    private Button buttonPass9;
    private Button buttonPassOk;
    private Button buttonPassDel;
    private TextView textViewPass1;
    private TextView textViewPass2;
    private TextView textViewPass3;
    private TextView textViewPass4;
    private TextView textViewPassInfo;
    private Button buttonEraseAllatStart1;
    private Button buttonEraseAllatStart2;
    private Button buttonEraseAllatStart3;
    private String pass = "";
    private Boolean noSavedPass = true;
    private Boolean newEnter = true;
    private SharedPreferences sharedPreferences;
    private Boolean EraseAllatStart1 = false;
    private Boolean EraseAllatStart2 = false;
    private Boolean EraseAllatStart3 = false;
    private String received;
    private String receivedText;

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
//        this activity does not imply pasting data from another application via the clipboard,
//        so when paused, the application closes
    }

    @Override
    public void onBackPressed() {
//        the program should have the following behavior - when you press the back button in the main activity, the program closes,
//        when you click the back button in the dialog box, it returns to the main activity
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Intent receivedIntent = getIntent();
            String receivedAction = receivedIntent.getAction();
            String receivedType = receivedIntent.getType();
//            receivedText is the text that the program receives along with intent, if the user
//            when share text chose this program
//            here it is accepted and after entering the password it is passed to the next activity
            if (receivedAction.equals(Intent.ACTION_SEND)) {
                received = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                if (received != null && receivedType.startsWith("text/")) {
                    if (received.length() > 0) {
                        receivedText = received;
                    }
                }
            }
        } catch (Exception e) {
//            if something is wrong with the incoming text, toast will pop up
//            in general, the program does not know how to correctly log errors and send them, but there are potentially not many errors either
//            due to the specifics of the program, I tried to make it so that the user could find out
//            what exactly is the mistake
            Log.d(LOG_TAG, "Exception = " + e);
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
        setStatusBarColor();
        findAllElements();
        addListiners();
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("secretKey")) {
            if (sharedPreferences.getString("secretKey", "").length() > 0) {
                noSavedPass = false;
            } else {
                noSavedPass = true;
            }
        } else {
            noSavedPass = true;
        }
        if (noSavedPass) {
            textViewPassInfo.setText("Create password");
        } else {
            textViewPassInfo.setText("Enter password");
        }

    }

    private void addListiners() {
        // could have been used here ->
        buttonPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("1");
            }
        });
        buttonPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("2");
            }
        });
        buttonPass3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("3");
            }
        });
        buttonPass4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("4");
            }
        });
        buttonPass5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("5");
            }
        });
        buttonPass6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("6");
            }
        });
        buttonPass7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("7");
            }
        });
        buttonPass8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("8");
            }
        });
        buttonPass9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPass("9");
            }
        });
        buttonPassDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDel();
            }
        });
        buttonPassOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                buttonPOk();
            }
        });
        buttonEraseAllatStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseAllatStart1 = true;
                buttonEraseAllatStart1.setText("OK");
                buttonEraseAll();
            }
        });
        buttonEraseAllatStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseAllatStart2 = true;
                buttonEraseAllatStart2.setText("OK");
                buttonEraseAll();
            }
        });
        buttonEraseAllatStart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseAllatStart3 = true;
                buttonEraseAllatStart3.setText("OK");
                buttonEraseAll();
            }
        });



    }

    private void findAllElements() {
        buttonPass1 = findViewById(R.id.buttonPass1);
        buttonPass2 = findViewById(R.id.buttonPass2);
        buttonPass3 = findViewById(R.id.buttonPass3);
        buttonPass4 = findViewById(R.id.buttonPass4);
        buttonPass5 = findViewById(R.id.buttonPass5);
        buttonPass6 = findViewById(R.id.buttonPass6);
        buttonPass7 = findViewById(R.id.buttonPass7);
        buttonPass8 = findViewById(R.id.buttonPass8);
        buttonPass9 = findViewById(R.id.buttonPass9);
        buttonPassOk = findViewById(R.id.buttonPassOk);
        buttonPassDel = findViewById(R.id.buttonPassDel);
        buttonEraseAllatStart1 = findViewById(R.id.buttonEraseAllatStart1);
        buttonEraseAllatStart2 = findViewById(R.id.buttonEraseAllatStart2);
        buttonEraseAllatStart3 = findViewById(R.id.buttonEraseAllatStart3);
        textViewPass1 = findViewById(R.id.textViewPass1);
        textViewPass2 = findViewById(R.id.textViewPass2);
        textViewPass3 = findViewById(R.id.textViewPass3);
        textViewPass4 = findViewById(R.id.textViewPass4);
        textViewPassInfo = findViewById(R.id.textViewPassInfo);
    }

    private void buttonEraseAll() {
//        by consecutive pressing of 3 erase buttons all saved keys are erased and the program is closed
//        in the future, there is an idea to make the program uninstall or at least remove it from the list
//        last running programs
//        also in the future there is an idea to make the program code encrypted and it could not be decompiled
//        or run dynamic analysis
        if (EraseAllatStart1 && EraseAllatStart2 && EraseAllatStart3) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            this.finish();
        }
    }

    public static String encrypt(String text, String key, String salt) {
//        all data is decrypted using the password from the startup activity
//        if the password was entered incorrectly, all saved keys are erased and the program uses the entered password as a new one
//        at the same time, the one who entered the wrong password will not be able to find out that the password is incorrect, he will see the same
//        what will see when the program is first launched
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes("UTF-8")));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String decrypt(String text, String key, String salt) {
//        all data is decrypted using the password from the startup activity
//        if the password was entered incorrectly, all saved keys are erased and the program uses the entered password as a new one
//        at the same time, the one who entered the wrong password will not be able to find out that the password is incorrect, he will see the same
//        what will see when the program is first launched
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
            }
        } catch (Exception e) {
        }
        return null;
    }

    private void setStatusBarColor() {
//        in some android versions the top bar does not change color when the application is opened, this method changes color forcibly
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private void generateKey() {
//        pass is the password entered when entering the program, with the help of it all data is decrypted
//        if the password was entered incorrectly, all saved keys are erased and the program uses the entered password as a new one
//        at the same time, the one who entered the wrong password will not be able to find out that the password is incorrect, he will see the same
//        what will see when the program is first launched
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("secretKey", encrypt("secretKey", pass, pass));
        editor.apply();
        editor = null;
        Intent intent = new Intent(this, InternalActivity.class);
        intent.putExtra("pass", pass);
        if (receivedText != null)
            if (receivedText.length() > 0)
                intent.putExtra("receivedText", receivedText);
        startActivity(intent);
        this.finish();
    }

    private void checkKey() {
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        String secretKey = sharedPreferences.getString("secretKey", "");
//        it is checked whether the "secretKey" phrase can be encrypted, if possible, the key is correct
//        if not, the data is erased
//        at the same time, the one who entered the wrong password will not be able to find out that the password is incorrect, he will see the same
//        what will see when the program is first launched
        if (decrypt(secretKey, pass, pass) != null) {
//            Toast toast = Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);
//            toast.show();
            Intent intent = new Intent(this, InternalActivity.class);
            intent.putExtra("pass", pass);
            if (receivedText != null)
                if (receivedText.length() > 0)
                    intent.putExtra("receivedText", receivedText);
            startActivity(intent);
            this.finish();

        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            editor = null;
            sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString("secretKey", encrypt("secretKey", pass, pass));
            editor2.apply();
            editor2 = null;
            Intent intent = new Intent(this, InternalActivity.class);
            intent.putExtra("pass", pass);
            if (receivedText != null)
                if (receivedText.length() > 0)
                    intent.putExtra("receivedText", receivedText);
            startActivity(intent);
            this.finish();
        }


    }

    private void buttonDel() {
//        erases the password if the user saw that he entered it incorrectly
//        also resets the Erase All buttons if the user accidentally presses one of them
        newEnter = true;
        pass = "";
        textViewPass1.setText("○");
        textViewPass2.setText("○");
        textViewPass3.setText("○");
        textViewPass4.setText("○");
        if (noSavedPass) {
            textViewPassInfo.setText(getString(R.string.pass_create));
        } else {
            textViewPassInfo.setText(getString(R.string.pass_enter_step_1));
        }
        EraseAllatStart1 = false;
        EraseAllatStart2 = false;
        EraseAllatStart3 = false;
        buttonEraseAllatStart1.setText("delete all");
        buttonEraseAllatStart2.setText("delete all");
        buttonEraseAllatStart3.setText("delete all");
    }

    private void buttonPass(String key) {

        if (pass.length() < 4) {
            newEnter = true;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pass);
            stringBuffer.append(key);
            pass = stringBuffer.toString();
            switch (pass.length()) {
                case 1:
                    textViewPass1.setText(pass.charAt(0) + "");
                    break;
                case 2:
                    textViewPass1.setText(pass.charAt(0) + "");
                    textViewPass2.setText(pass.charAt(1) + "");
                    break;
                case 3:
                    textViewPass1.setText(pass.charAt(0) + "");
                    textViewPass2.setText(pass.charAt(1) + "");
                    textViewPass3.setText(pass.charAt(2) + "");
                    break;
                case 4:
                    textViewPass1.setText(pass.charAt(0) + "");
                    textViewPass2.setText(pass.charAt(1) + "");
                    textViewPass3.setText(pass.charAt(2) + "");
                    textViewPass4.setText(pass.charAt(3) + "");
                    break;
            }

        }


    }

    private void buttonPOk(){
//        the user is given the opportunity to think if he made a mistake when entering the key
        if (pass.length() == 4 && noSavedPass) {
            if (newEnter) {
                newEnter = false;
                textViewPassInfo.setText(getString(R.string.pass_enter_step_2));
            } else {
                generateKey();
            }

        } else if (pass.length() == 4 && !noSavedPass) {
            if (newEnter) {
                newEnter = false;
                textViewPassInfo.setText(getString(R.string.pass_enter_step_2));
            } else {
                checkKey();
            }
        }

    }
}
