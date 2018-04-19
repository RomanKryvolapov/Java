package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити смены пароля - для запроса старого пароля
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChangePassword extends AppCompatActivity {


    SharedPreferences sharedPreferences;

    Button buttonChange1;
    Button buttonChange2;
    Button buttonChange3;
    Button buttonChange4;
    Button buttonChange5;
    Button buttonChange6;
    Button buttonChange7;
    Button buttonChange8;
    Button buttonChange9;
    Button buttonChangeClear;
    Button buttonChangePasswordCancel;
    TextView textViewCheckChange;
    TextView editCheckPasswordChange;

    private String password = "";
    private String passwordCheck = "";
    private String passwordView = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        buttonChange1 = findViewById(R.id.buttonChange1);
        buttonChange2 = findViewById(R.id.buttonChange2);
        buttonChange3 = findViewById(R.id.buttonChange3);
        buttonChange4 = findViewById(R.id.buttonChange4);
        buttonChange5 = findViewById(R.id.buttonChange5);
        buttonChange6 = findViewById(R.id.buttonChange6);
        buttonChange7 = findViewById(R.id.buttonChange7);
        buttonChange8 = findViewById(R.id.buttonChange8);
        buttonChange9 = findViewById(R.id.buttonChange9);
        buttonChangeClear = findViewById(R.id.buttonChangeClear);
        buttonChangeClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = "";
                passwordView = "";
                editCheckPasswordChange.setText(passwordView);
                passwordCheck();
            }
        });
        buttonChangePasswordCancel = findViewById(R.id.buttonChangePasswordCancel);
        buttonChangePasswordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassword.this, OldUser.class);
                startActivity(intent);
            }
        });
        textViewCheckChange = findViewById(R.id.textViewCheckChange);
        editCheckPasswordChange = findViewById(R.id.editCheckPasswordChange);
        buttonChange1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "1";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "2";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "3";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "4";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "5";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "6";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "7";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "8";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
        buttonChange9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "9";
                    passwordView += "*";
                    editCheckPasswordChange.setText(passwordView);
                    passwordCheck();
                }
            }
        });
    }

    private void passwordCheck(){
        if(password.length()==4) {
            sharedPreferences = getSharedPreferences("cache", MODE_PRIVATE);
            passwordCheck = sharedPreferences.getString("Password", "");
            if(passwordCheck.length()>0) {
                if (passwordCheck.charAt(8) == password.charAt(0) &&
                        passwordCheck.charAt(3) == password.charAt(1) &&
                        passwordCheck.charAt(25) == password.charAt(2) &&
                        passwordCheck.charAt(11) == password.charAt(3)) {
                    Intent intent = new Intent(ChangePassword.this, NewUser.class);
                    startActivity(intent);
                } else {
                    textViewCheckChange.setText("Password incorrect");
                    password = "";
                    passwordView = "";
                    editCheckPasswordChange.setText(passwordView);
                }
            } else{
                textViewCheckChange.setText("Can not work without pass file");
                password = "";
                passwordView = "";
                editCheckPasswordChange.setText(passwordView);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChangePassword.this, OldUser.class);
        startActivity(intent);
    }

}
