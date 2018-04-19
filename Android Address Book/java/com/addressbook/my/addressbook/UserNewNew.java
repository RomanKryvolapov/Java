package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити создания нового контакта
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

    String LOG_TAG = "MyLog";

    Button buttonSaveNew;
    EditText firstnameNew;
    EditText lastnameNew;
    EditText telephone1New;
    EditText telephone2New;
    EditText telephone3New;
    EditText facebookNew;
    EditText viberNew;
    EditText telegramNew;
    EditText emailNew;
    UserNewNew.DBHelper dbHelper;
    SQLiteDatabase db;
    String firstname;
    String lastname;
    String telephone1;
    String telephone2;
    String telephone3;
    String facebook;
    String viber;
    String telegram;
    String email;

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
        firstnameNew.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        buttonSaveNew = findViewById(R.id.buttonSaveNew);
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
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor c = db.query("mytable", null, null, null, null, null, null);

                    ContentValues cv = new ContentValues();
                    cv.put("firstname", firstname);
                    cv.put("lastname", lastname);
                    cv.put("telephone1", telephone1);
                    cv.put("telephone2", telephone2);
                    cv.put("telephone3", telephone3);
                    cv.put("facebook", facebook);
                    cv.put("viber", viber);
                    cv.put("telegram", telegram);
                    cv.put("email", email);
                    long rowID = db.insert("mytable", null, cv);

                    c.close();
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
