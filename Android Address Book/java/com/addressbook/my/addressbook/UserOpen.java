package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити контактной карточки
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserOpen extends AppCompatActivity {

    static int userID;

    String LOG_TAG = "Просмотр";

    String firstname;
    String lastname;
    String telephone1;
    String telephone2;
    String telephone3;
    String facebook;
    String viber;
    String telegram;
    String email;

    TextView firstnameView;
    TextView lastnameView;
    TextView telephone1View;
    TextView telephone2View;
    TextView telephone3View;
    TextView facebookView;
    TextView viberView;
    TextView telegramView;
    TextView emailView;

    Cursor c;

    Button buttonEdit;

    SQLiteDatabase db;

    UserOpen.DBHelper dbHelper;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserOpen.this, UserList.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "!!! finish !!!");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(LOG_TAG, "!!! Start c userID = !"+userID+"! !!!");

        firstnameView = findViewById(R.id.firstnameView);
        lastnameView = findViewById(R.id.lastnameView);
        telephone1View = findViewById(R.id.telephone1View);
        telephone2View = findViewById(R.id.telephone2View);
        telephone3View = findViewById(R.id.telephone3View);
        facebookView = findViewById(R.id.facebookView);
        viberView = findViewById(R.id.viberView);
        telegramView = findViewById(R.id.telegramView);
        emailView = findViewById(R.id.emailView);
        telephone1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(telephone1.length()>0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telephone1, null));
                    startActivity(intent);
                }

            }
        });
        telephone2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(telephone1.length()>0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telephone2, null));
                    startActivity(intent);
                }

            }
        });
        telephone3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(telephone1.length()>0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telephone3, null));
                    startActivity(intent);
                }

            }
        });

        dbHelper = new UserOpen.DBHelper(this);
        db = dbHelper.getWritableDatabase();

        c = db.query("mytable", null, null, null, null, null, null);

        if (c.moveToFirst()) {

            idColumns();

            do {
                if(c.getInt(UserList.intID)==userID)
                break;
            } while (c.moveToNext());

            firstname=c.getString(UserList.intfirstname);
            lastname=c.getString(UserList.intlastname);
            telephone1=c.getString(UserList.inttelephone1);
            telephone2=c.getString(UserList.inttelephone2);
            telephone3=c.getString(UserList.inttelephone3);
            facebook=c.getString(UserList.intfacebook);
            viber=c.getString(UserList.intviber);
            telegram=c.getString(UserList.inttelegram);
            email=c.getString(UserList.intemail);

        } else
            Log.d(LOG_TAG, "!!! База данных пуста !!!");

        c.close();
        dbHelper.close();

        firstnameView.setText(firstname);
        lastnameView.setText(lastname);
        telephone1View.setText(telephone1);
        telephone2View.setText(telephone2);
        telephone3View.setText(telephone3);
        facebookView.setText(facebook);
        viberView.setText(viber);
        telegramView.setText(telegram);
        emailView.setText(email);

        buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEdit.userID=UserOpen.userID;
                Intent intent = new Intent(UserOpen.this, UserEdit.class);
                startActivity(intent);
                finish();
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
        setContentView(R.layout.activity_user_open);
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table mytable (" + "id integer primary key autoincrement,"
                    + "firstname text," + "lastname text," + "telephone1 text," + "telephone2 text," + "telephone3 text," + "facebook text," + "viber text,"
                    + "telegram text," + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
