package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити создания нового контакта
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class UserNewNew extends AppCompatActivity {

    private String LOG_TAG = "MyLog";

    private Button buttonSaveNew;
    private EditText firstnameNew;
    private EditText lastnameNew;
    private EditText telephone1New;
    private EditText telephone2New;
    private EditText telephone3New;
    private EditText facebookNew;
    private EditText viberNew;
    private EditText telegramNew;
    private EditText emailNew;

    private UserNewNew.DBHelper dbHelper;
    private SQLiteDatabase db;

    private String firstname;
    private String lastname;
    private String telephone1;
    private String telephone2;
    private String telephone3;
    private String facebook;
    private String viber;
    private String telegram;
    private String email;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserNewNew.this, UserList.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_new);
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbHelper = new UserNewNew.DBHelper(this);
        db = dbHelper.getWritableDatabase();

        firstnameNew = findViewById(R.id.firstnameNew);
        buttonSaveNew = findViewById(R.id.buttonSaveNew);
        firstnameNew.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        buttonSaveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "!!! Добавлеие в базу данных !!!");

                firstname = firstnameNew.getText().toString();

                if(!firstname.equals("")) {

                    lastnameNew = findViewById(R.id.lastnameNew);
                    telephone1New = findViewById(R.id.telephone1New);
                    telephone2New = findViewById(R.id.telephone2New);
                    telephone3New = findViewById(R.id.telephone3New);
                    facebookNew = findViewById(R.id.facebookNew);
                    viberNew = findViewById(R.id.viberNew);
                    telegramNew = findViewById(R.id.telegramNew);
                    emailNew = findViewById(R.id.emailNew);

                    lastname = lastnameNew.getText().toString();
                    telephone1 = telephone1New.getText().toString();
                    telephone2 = telephone2New.getText().toString();
                    telephone3 = telephone3New.getText().toString();
                    facebook = facebookNew.getText().toString();
                    viber = viberNew.getText().toString();
                    telegram = telegramNew.getText().toString();
                    email = emailNew.getText().toString();

                    dbHelper = new UserNewNew.DBHelper(getApplicationContext());
                    db = dbHelper.getWritableDatabase();

                    String queryUpdate = "INSERT INTO mytable (firstname, lastname, telephone1, telephone2, telephone3," +
                            "facebook, viber, telegram, email) VALUES ('"+firstname+"', '"+lastname+"', '"+telephone1+"', '"+telephone2+"'," +
                            "'"+telephone3+"', '"+facebook+"', '"+viber+"', '"+telegram+"', '"+email+"');";

                    for (int i = 0; i < 50; i++) {

                        db.execSQL(queryUpdate);

                    }

                    dbHelper.close();

                    Intent intent = new Intent(UserNewNew.this, UserList.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "!!! Создание базы данных !!!");
            db.execSQL("create table mytable (" + "id integer primary key autoincrement,"
                    + "firstname text," + "lastname text," + "telephone1 text," + "telephone2 text," + "telephone3 text," + "facebook text," + "viber text,"
                    + "telegram text," + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
