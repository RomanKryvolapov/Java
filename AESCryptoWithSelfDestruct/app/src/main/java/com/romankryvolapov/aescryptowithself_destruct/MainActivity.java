package com.romankryvolapov.aescryptowithself_destruct;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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

    Button buttonPass1;
    Button buttonPass2;
    Button buttonPass3;
    Button buttonPass4;
    Button buttonPass5;
    Button buttonPass6;
    Button buttonPass7;
    Button buttonPass8;
    Button buttonPass9;
    Button buttonPassOk;
    Button buttonPassDel;
    TextView textViewPass1;
    TextView textViewPass2;
    TextView textViewPass3;
    TextView textViewPass4;
    TextView textViewPassInfo;
    Button buttonEraseAllatStart1;
    Button buttonEraseAllatStart2;
    Button buttonEraseAllatStart3;
    private String pass = "";
    private Boolean noSavedPass = true;
    private Boolean newEnter = true;
    SharedPreferences sharedPreferences;
    private Boolean EraseAllatStart1 = false;
    private Boolean EraseAllatStart2 = false;
    private Boolean EraseAllatStart3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor();
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

    public void buttonEraseAll() {
        if (EraseAllatStart1 && EraseAllatStart2 && EraseAllatStart3) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            this.finish();
        }


    }

    public String encrypt(String strToEncrypt, String key, String salt)
    {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            }
        }
        catch (Exception e)
        {
            textViewPassInfo.setText("Exception");
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String key, String salt) {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            }
        }
        catch (Exception e) {
        }
        return null;
    }

    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    public void generateKey() {
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("secretKey", encrypt("secretKey", pass, pass));
        editor.apply();
        editor = null;
        Intent intent = new Intent(this, InternalActivity.class);
        intent.putExtra("pass", pass);
        startActivity(intent);
        this.finish();
    }

    public void checkKey() {
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        String secretKey = sharedPreferences.getString("secretKey", "");
        if(decrypt(secretKey, pass, pass)!=null){
            Toast toast = Toast.makeText(getApplicationContext(), "Pass OK", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, InternalActivity.class);
            intent.putExtra("pass", pass);
            startActivity(intent);
            this.finish();

        }  else {
            Toast toast = Toast.makeText(getApplicationContext(), "Pass NOT OK", Toast.LENGTH_SHORT);
            toast.show();
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
            startActivity(intent);
            this.finish();

        }


    }

    private void buttonDel() {
        newEnter = true;
        pass = "";
        textViewPass1.setText("○");
        textViewPass2.setText("○");
        textViewPass3.setText("○");
        textViewPass4.setText("○");
        if (noSavedPass) {
            textViewPassInfo.setText("Create password");
        } else {
            textViewPassInfo.setText("Enter password");
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

    private void buttonPOk() {
        if (pass.length() == 4 && noSavedPass) {
            if (newEnter) {
                newEnter = false;
                textViewPassInfo.setText("Are you sure?");
            } else {
                generateKey();
            }

        } else if (pass.length() == 4 && !noSavedPass) {
            if (newEnter) {
                newEnter = false;
                textViewPassInfo.setText("Are you sure?");
            } else {
                checkKey();
            }
        }

    }
}
