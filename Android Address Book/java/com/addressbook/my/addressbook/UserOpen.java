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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserOpen extends AppCompatActivity {

    public static int userID;

    private String LOG_TAG = "Просмотр";

//    private String firstname;
//    private String lastname;
//    private String telephone1;
//    private String telephone2;
//    private String telephone3;
//    private String facebook;
//    private String viber;
//    private String telegram;
//    private String email;

//    String[] newList = new String[20];

    ArrayList<String> newList = new ArrayList<String>();

//    private TextView firstnameView;
//    private TextView lastnameView;
//    private TextView telephone1View;
//    private TextView telephone2View;
//    private TextView telephone3View;
//    private TextView facebookView;
//    private TextView viberView;
//    private TextView telegramView;
//    private TextView emailView;

    private Cursor c;
    private Button buttonEdit;
    private SQLiteDatabase db;
    private UserOpen.DBHelper dbHelper;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserOpen.this, UserList.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        telephone1View.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                        if(telephone1.length()>0) {
//                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telephone1, null));
//                            startActivity(intent);
//                        }
//                }catch (Exception e){
//                }
//            }
//        });
//        telephone2View.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                        if(telephone1.length()>0) {
//                          Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telephone2, null));
//                          startActivity(intent);
//                          }
//                }catch (Exception e){
//                }
//            }
//        });
//        telephone3View.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                if(telephone1.length()>0) {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telephone3, null));
//                    startActivity(intent);
//                }
//                }catch (Exception e){
//                }
//            }
//        });
//        facebookView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(facebook.length()>0){
//                    try {
//                    if(facebook.contains("https://www.facebook.com/"))
//                    {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
//                        startActivity(intent);
//                    } else{
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+facebook));
//                        startActivity(intent);
//                    }
//                    }catch (Exception e){
//
//                    }
//                }
//            }
//        });
//        emailView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    if(email.contains("@")&&email.contains(".")){
//
//                        Intent intent = new Intent(Intent.ACTION_SEND);
//                        intent.setType("message/rfc822");
//                        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
//                        intent.putExtra(Intent.EXTRA_SUBJECT, " ");
//                        intent.putExtra(Intent.EXTRA_TEXT   , " ");
//                        startActivity(Intent.createChooser(intent, "Send email"));
//
//                    }
//                }catch (Exception e){
//
//                }
//            }
//        });

        dbHelper = new UserOpen.DBHelper(this);
        db = dbHelper.getWritableDatabase();

        userID = 20;

        String query = "SELECT * FROM mytable WHERE id = ?";

        c = db.rawQuery(query, new String[]{Integer.toString(userID)});

        if (c.moveToFirst()) {

            idColumns();

            newList.add("First Name :");
            newList.add(c.getString(UserList.intfirstname));
            newList.add("Last Name :");
            newList.add(c.getString(UserList.intlastname));
            newList.add("Telephone 1:");
            newList.add(c.getString(UserList.inttelephone1));
            newList.add("Telephone 2:");
            newList.add(c.getString(UserList.inttelephone2));
            newList.add("Telephone 3:");
            newList.add(c.getString(UserList.inttelephone3));
            newList.add("Facebook :");
            newList.add(c.getString(UserList.intfacebook));
            newList.add("Viber :");
            newList.add(c.getString(UserList.intviber));
            newList.add("Telegram :");
            newList.add(c.getString(UserList.inttelegram));
            newList.add("Email :");
            newList.add(c.getString(UserList.intemail));

        } else
            Log.d(LOG_TAG, "!!! База данных пуста !!!");

        c.close();
        dbHelper.close();

        GridView gridOpen = (GridView) findViewById(R.id.gridOpen);
        gridOpen.setNumColumns(2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item2, newList);
        gridOpen.setAdapter(adapter);

        buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEdit.userID=UserOpen.userID;
                Intent intent = new Intent(UserOpen.this, UserEdit.class);
                startActivity(intent);
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
