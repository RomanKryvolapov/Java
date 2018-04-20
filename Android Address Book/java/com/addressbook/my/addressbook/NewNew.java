package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// проверка - задан ли пароль и пустая ли база данных
// если база данных не пустая, а файл пароля кто то удалил,
// чтобы сбросить пароль, окно проверки пароля скажет об этом
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class NewNew extends AppCompatActivity {

    NewNew.DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new NewNew.DBHelper(this);
        db = dbHelper.getWritableDatabase();
        c = db.query("mytable", null, null, null, null, null, null);

        SharedPreferences sharedPreferences = getSharedPreferences("cache", MODE_PRIVATE);

        if(!c.moveToFirst()&&sharedPreferences.getString("Password", "").length()==0) {
            c.close();
            dbHelper.close();
            Intent intent = new Intent(NewNew.this, NewUser.class);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }else
        {
            c.close();
            dbHelper.close();
            Intent intent = new Intent(NewNew.this, OldUser.class);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }

    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("NewNew", "!!! Создание базы данных !!!");
            db.execSQL("create table mytable (" + "id integer primary key autoincrement,"
                    + "firstname text," + "lastname text," + "telephone1 text," + "telephone2 text," + "telephone3 text," + "facebook text," + "viber text,"
                    + "telegram text," + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
