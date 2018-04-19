package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити изменения данных пользователя
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

public class UserEdit extends AppCompatActivity {

    static int userID;

    String LOG_TAG = "MyLog";

    String firstname;
    String lastname;
    String telephone1;
    String telephone2;
    String telephone3;
    String facebook;
    String viber;
    String telegram;
    String email;
    EditText firstnameEdit;
    EditText lastnameEdit;
    EditText telephone1Edit;
    EditText telephone2Edit;
    EditText telephone3Edit;
    EditText facebookEdit;
    EditText viberEdit;
    EditText telegramEdit;
    EditText emailEdit;
    Button buttonSave;
    Button buttonDelete;
    UserEdit.DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserEdit.this, UserOpen.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(LOG_TAG, "!!! Редактирование Запуск !!!");

        dbHelper = new UserEdit.DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Log.d(LOG_TAG, "!!! Редактирование Содержимое базы данных !!!");

        String query = "SELECT * FROM mytable WHERE id = ?";

        c = db.rawQuery(query, new String[]{Integer.toString(userID)});

        if (c.moveToFirst()) {

            idColumns();
//
//            do {
//                if(c.getInt(UserList.intID)==userID)
//                    break;
//            } while (c.moveToNext());

            firstname = c.getString(UserList.intfirstname);
            lastname = c.getString(UserList.intlastname);
            telephone1 = c.getString(UserList.inttelephone1);
            telephone2 = c.getString(UserList.inttelephone2);
            telephone3 = c.getString(UserList.inttelephone3);
            facebook = c.getString(UserList.intfacebook);
            viber = c.getString(UserList.intviber);
            telegram = c.getString(UserList.inttelegram);
            email = c.getString(UserList.intemail);

        } else
            Log.d(LOG_TAG, "!!! База данных пустая !!!");

        c.close();
        dbHelper.close();

        firstnameEdit = findViewById(R.id.firstnameEdit);
        lastnameEdit = findViewById(R.id.lastnameEdit);
        telephone1Edit = findViewById(R.id.telephone1Edit);
        telephone2Edit = findViewById(R.id.telephone2Edit);
        telephone3Edit = findViewById(R.id.telephone3Edit);
        facebookEdit = findViewById(R.id.facebookEdit);
        viberEdit = findViewById(R.id.viberEdit);
        telegramEdit = findViewById(R.id.telegramEdit);
        emailEdit = findViewById(R.id.emailEdit);

        firstnameEdit.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        firstnameEdit.setText(firstname);
        lastnameEdit.setText(lastname);
        telephone1Edit.setText(telephone1);
        telephone2Edit.setText(telephone2);
        telephone3Edit.setText(telephone3);
        facebookEdit.setText(facebook);
        viberEdit.setText(viber);
        telegramEdit.setText(telegram);
        emailEdit.setText(email);

        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "!!!Редактирование Удаление из базы данных !!!");

                dbHelper = new UserEdit.DBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String queryDel = "DELETE FROM mytable WHERE id = "+Integer.toString(userID);

                db.execSQL(queryDel);

                c.close();
                dbHelper.close();
                Intent intent = new Intent(UserEdit.this, UserList.class);
                startActivity(intent);
                finish();

            }
        });

        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "!!!Редактирование Обновление базы данных !!!");

                firstname = firstnameEdit.getText().toString();

                if(!firstname.equals("")) {
                    lastname = lastnameEdit.getText().toString();
                    telephone1 = telephone1Edit.getText().toString();
                    telephone2 = telephone2Edit.getText().toString();
                    telephone3 = telephone3Edit.getText().toString();
                    facebook = facebookEdit.getText().toString();
                    viber = viberEdit.getText().toString();
                    telegram = telegramEdit.getText().toString();
                    email = emailEdit.getText().toString();
                    dbHelper = new UserEdit.DBHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    String queryUpdate = "UPDATE mytable SET firstname = '"+firstname+"', lastname = '"+lastname+"', " +
                            "telephone1 = '"+telephone1+"', telephone2 = '"+telephone2+"', " +
                            "telephone3 = '"+telephone3+"', facebook = '"+facebook+"', " +
                            "viber = '"+viber+"', telegram = '"+telegram+"'," +
                            "email = '"+email+"'  WHERE id = "+Integer.toString(userID);

                    db.execSQL(queryUpdate);

                    c.close();
                    dbHelper.close();
                    Intent intent = new Intent(UserEdit.this, UserOpen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    void idColumns(){
        if(!UserList.intChecked) {
            Log.d(LOG_TAG, "!!! Проверка ID !!! ");
            UserList.intID = c.getColumnIndex("id");
            UserList.intfirstname = c.getColumnIndex("firstname");
            UserList.intlastname = c.getColumnIndex("lastname");
            UserList.inttelephone1 = c.getColumnIndex("telephone1");
            UserList.inttelephone2 = c.getColumnIndex("telephone2");
            UserList.inttelephone3 = c.getColumnIndex("telephone3");
            UserList.intfacebook = c.getColumnIndex("facebook");
            UserList.intviber = c.getColumnIndex("viber");
            UserList.inttelegram = c.getColumnIndex("telegram");
            UserList.intemail = c.getColumnIndex("email");
            UserList.intChecked=true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
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