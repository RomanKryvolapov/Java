package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// окно ввода пароля
// если пароля нет, а база данных ест (проверилось на предыдущем активити)
// скажет об этом
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OldUser extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView textViewCheck;

    Button buttonCheckAdd1;
    Button buttonCheckAdd2;
    Button buttonCheckAdd3;
    Button buttonCheckAdd4;
    Button buttonCheckAdd5;
    Button buttonCheckAdd6;
    Button buttonCheckAdd7;
    Button buttonCheckAdd8;
    Button buttonCheckAdd9;
    Button buttonCheckClear;
    Button buttonChangePassword;
    TextView editCheckPassword;


    private String password = "";
    private String passwordCheck = "";
    private String passwordView = "";

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
                        Intent intent = new Intent(OldUser.this, ChangePassword.class);
                        startActivity(intent);
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
            if(passwordCheck.length()>0) {
                if (passwordCheck.charAt(8) == password.charAt(0) &&
                        passwordCheck.charAt(3) == password.charAt(1) &&
                        passwordCheck.charAt(25) == password.charAt(2) &&
                        passwordCheck.charAt(11) == password.charAt(3)) {
                    Intent intent = new Intent(OldUser.this, UserList.class);
                    startActivity(intent);
                } else {
                    textViewCheck.setText("Password incorrect");
                    password = "";
                    passwordView = "";
                    editCheckPassword.setText(passwordView);
                }
            } else{
                textViewCheck.setText("Can not work without pass file");
                password = "";
                passwordView = "";
                editCheckPassword.setText(passwordView);
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
        finish();
    }
}
