package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// окно ввода пароля
// если пароля нет, а база данных ест (проверилось на предыдущем активити)
// скажет об этом
import android.animation.TimeAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class OldUser extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView textViewCheck;
    private Button buttonCheckAdd1;
    private Button buttonCheckAdd2;
    private Button buttonCheckAdd3;
    private Button buttonCheckAdd4;
    private Button buttonCheckAdd5;
    private Button buttonCheckAdd6;
    private Button buttonCheckAdd7;
    private Button buttonCheckAdd8;
    private Button buttonCheckAdd9;
    private Button buttonCheckClear;
    private Button buttonChangePassword;
    private TextView editCheckPassword;
    private boolean changemode = false;
    private int checkCount = 0;
    private String password = "";
    private String passwordCheck = "";
    private String passwordView = "";
    private int sleeptimer = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_user);

        textViewCheck = findViewById(R.id.textViewCheck);
        buttonCheckAdd1 = findViewById(R.id.buttonCheckChange1);
        buttonCheckAdd2 = findViewById(R.id.buttonCheckAdd2);
        buttonCheckAdd3 = findViewById(R.id.buttonCheckAdd3);
        buttonCheckAdd4 = findViewById(R.id.buttonCheckAdd4);
        buttonCheckAdd5 = findViewById(R.id.buttonCheckAdd5);
        buttonCheckAdd6 = findViewById(R.id.buttonCheckAdd6);
        buttonCheckAdd7 = findViewById(R.id.buttonCheckAdd7);
        buttonCheckAdd8 = findViewById(R.id.buttonCheckAdd8);
        buttonCheckAdd9 = findViewById(R.id.buttonCheckAdd9);
        editCheckPassword = findViewById(R.id.editCheckPasswordChange);
        buttonCheckClear = findViewById(R.id.buttonChangeClear);
        buttonChangePassword = findViewById(R.id.buttonChangePasswordCancel);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!changemode) {
                    changemode = true;
                    textViewCheck.setBackgroundColor(0xFFFF8C00);
                    textViewCheck.setText("Enter old password");
                    buttonChangePassword.setText("c\na\nn\nc\ne\nl");
                } else{
                    changemode = false;
                    textViewCheck.setBackgroundColor(0xFF000000);
                    textViewCheck.setText("Enter your password");
                    buttonChangePassword.setText("c\nh\na\nn\ng\ne");
                }
            }
        });
        buttonCheckClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = "";
                passwordView = "";
                editCheckPassword.setText(passwordView);
                passwordCheck();
            }
        });


        buttonCheckAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "1";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "2";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "3";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "4";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "5";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "6";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "7";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "8";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonCheckAdd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "9";
                    passwordView += "*";
                    editCheckPassword.setText(passwordView);
                    passwordCheck();
                }


            }
        });



    }

    private void passwordCheck(){
        if(password.length()==4) {
            sharedPreferences = getSharedPreferences("cache", MODE_PRIVATE);
            passwordCheck = sharedPreferences.getString("Password", "");
            if (passwordCheck.length() > 0) {
                if (passwordCheck.charAt(7+Integer.parseInt(password.charAt(3)+"")/3*2) == password.charAt(0) &&
                        passwordCheck.charAt(26+Integer.parseInt(password.charAt(2)+"")/3*2) == password.charAt(1) &&
                        passwordCheck.charAt(55+Integer.parseInt(password.charAt(1)+"")/3*2) == password.charAt(2) &&
                        passwordCheck.charAt(70+Integer.parseInt(password.charAt(0)+"")/3*2) == password.charAt(3)) {
                    textViewCheck.setBackgroundColor(0xFF00FF00);
                    textViewCheck.setText("Password correct");
                    if (!changemode) {
                        UserList.correctEnter=true;
                        Intent intent = new Intent(OldUser.this, UserList.class);
                        startActivity(intent);
                    } else{
                        Intent intent = new Intent(OldUser.this, NewUser.class);
                        startActivity(intent);
                    }
                } else {
                    textViewCheck.setBackgroundColor(0xFFFF0000);
                    textViewCheck.setText("Password incorrect");
                    password = "";
                    passwordView = "";
                    editCheckPassword.setText(passwordView);
                    checkCount++;
                    passwordIncorrect();
                    if(checkCount>4){
                        finish();
                    }
                }
            } else {
                textViewCheck.setBackgroundColor(0xFFFF0000);
                textViewCheck.setText("Can not work without pass file");
                password = "";
                passwordView = "";
                editCheckPassword.setText(passwordView);
                UserList.correctEnter=false;
            }
        }
    }

    void passwordIncorrect(){
        for (int i = 0; i < sleeptimer; i++) {
            try {
                Thread.sleep(1);
            }catch (Exception e){

            }
        }
        sleeptimer+=3000;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
