package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// создание ногово пароля
// попадает на это окно, если нет файла пароля и база данных пустая
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class NewUser extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Random random = new Random();

    TextView editAddPassword;
    Button buttonAdd1;
    Button buttonAdd2;
    Button buttonAdd3;
    Button buttonAdd4;
    Button buttonAdd5;
    Button buttonAdd6;
    Button buttonAdd7;
    Button buttonAdd8;
    Button buttonAdd9;
    Button buttonAdd;
    Button buttonAddClear;

    StringBuffer stringBuffer = new StringBuffer();

    private String password = "";
    private String passwordView = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        editAddPassword = findViewById(R.id.editAddPassword);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd1 = findViewById(R.id.buttonAdd1);
        buttonAdd2 = findViewById(R.id.buttonAdd2);
        buttonAdd3 = findViewById(R.id.buttonAdd3);
        buttonAdd4 = findViewById(R.id.buttonAdd4);
        buttonAdd5 = findViewById(R.id.buttonAdd5);
        buttonAdd6 = findViewById(R.id.buttonAdd6);
        buttonAdd7 = findViewById(R.id.buttonAdd7);
        buttonAdd8 = findViewById(R.id.buttonAdd8);
        buttonAdd9 = findViewById(R.id.buttonAdd9);
        buttonAddClear = findViewById(R.id.buttonAddClear);

        buttonAddClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = "";
                passwordView = "";
                editAddPassword.setText(passwordView);
            }
        });
        buttonAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "1";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "2";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "3";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "4";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "5";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "6";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "7";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "8";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()<4) {
                    password += "9";
                    passwordView += "*";
                    editAddPassword.setText(passwordView);
                }
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length()==4){

                    String passportInsert = "";

                    stringBuffer.setLength(0);

                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));
                    stringBuffer.append(Integer.toString(random.nextInt(1000000000)));

                    stringBuffer.insert(7 + Integer.parseInt(password.charAt(3)+"")/3*2, password.charAt(0));
                    stringBuffer.insert(26 + Integer.parseInt(password.charAt(2)+"")/3*2, password.charAt(1));
                    stringBuffer.insert(55 + Integer.parseInt(password.charAt(1)+"")/3*2, password.charAt(2));
                    stringBuffer.insert(70 + Integer.parseInt(password.charAt(0)+"")/3*2, password.charAt(3));

                    passportInsert = stringBuffer.toString();

                    stringBuffer.setLength(0);

                    sharedPreferences = getSharedPreferences("cache", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedPreferences.edit();

                    ed.putString("Password", passportInsert);
                    ed.commit();

                    UserList.correctEnter=true;
                    Intent intent = new Intent(NewUser.this, UserList.class);
                    startActivity(intent);

                }
            }
        });


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
