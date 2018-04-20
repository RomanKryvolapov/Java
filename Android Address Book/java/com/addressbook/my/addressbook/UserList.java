package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити списка контактов
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    private String LOG_TAG = "Список";

    private Button buttonAddNew;
    private Button buttonClearSearchStrings;
    private EditText editSearchFirstName;
    private EditText editSearchLastName;

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor c;

    ArrayList<String> newList = new ArrayList<>();

    static String searchFirstName = "";
    static String searchLastName = "";

    public static int intID;
    public static int intfirstname;
    public static int intlastname;
    public static int inttelephone1;
    public static int inttelephone2;
    public static int inttelephone3;
    public static int intfacebook;
    public static int intviber;
    public static int inttelegram;
    public static int intemail;
    public static boolean intChecked = false;
    public static ArrayList<Integer> sqlIDnumber = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(LOG_TAG, "!!! Start !!!");
        editSearchFirstName.setText(searchFirstName);
        editSearchLastName.setText(searchLastName);

        reloadTable();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Log.d(LOG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!");

        editSearchFirstName = findViewById(R.id.editSearchFirstName);
        editSearchLastName = findViewById(R.id.editSearchLastName);
        buttonAddNew = findViewById(R.id.buttonAddNew);
        buttonClearSearchStrings = findViewById(R.id.buttonClearSearchStrings);

        editSearchFirstName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                searchFirstName = editSearchFirstName.getText().toString();
                searchLastName = editSearchLastName.getText().toString();
                reloadTable();
                return false;
            }
        });
        editSearchLastName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                searchFirstName = editSearchFirstName.getText().toString();
                searchLastName = editSearchLastName.getText().toString();
                reloadTable();
                return false;
            }
        });

        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserList.this, UserNewNew.class);
                startActivity(intent);
            }
        });

        buttonClearSearchStrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearchFirstName.setText("");
                editSearchLastName.setText("");
                searchFirstName = "";
                searchLastName = "";
                reloadTable();
            }
        });


    }

    void reloadTable(){

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        if (searchFirstName.equals("") && searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие 1 !!!");
            String query = "SELECT id, firstname, lastname FROM mytable ORDER BY firstname";
            c = db.rawQuery(query, null);
        } else if (!searchFirstName.equals("") && searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие 2 !!!");
            String query = "SELECT id, firstname, lastname FROM mytable WHERE firstname LIKE ? ORDER BY firstname";
            String query2 = "%"+searchFirstName+"%";
            c = db.rawQuery(query, new String[]{query2});
        } else if (searchFirstName.equals("") && !searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие 3 !!!");
            String query = "SELECT id, firstname, lastname FROM mytable WHERE lastname LIKE ? ORDER BY firstname";
            String query2 = "%"+searchLastName+"%";
            c = db.rawQuery(query, new String[]{query2});
        } else if (!searchFirstName.equals("") && !searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие 4 !!!");
            String query2 = "%"+searchFirstName+"%";
            String query3 = "%"+searchLastName+"%";
            String query = "SELECT id, firstname, lastname FROM mytable WHERE firstname LIKE ? AND lastname LIKE ? ORDER BY firstname";
            c = db.rawQuery(query, new String[]{query2, query3});
        }

        if (c.moveToFirst()) {

            sqlIDnumber = new ArrayList<Integer>();
            c.moveToFirst();

            do {
                sqlIDnumber.add(c.getInt(intID));
            } while (c.moveToNext());

            if (!intChecked)
                idColumns();

            newList = new ArrayList<String>();
            c.moveToFirst();

            do {
                newList.add(c.getString(intfirstname));
                newList.add(c.getString(intlastname));
            } while (c.moveToNext());

            GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setNumColumns(2);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, newList);
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    UserOpen.userID = sqlIDnumber.get(i/2);
                    Intent intent = new Intent(UserList.this, UserOpen.class);
                    startActivity(intent);
                    finish();

                }
            });

            c.close();
            dbHelper.close();

        } else
        {
            GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setNumColumns(2);
            String newListNull[] = new String[0];
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, newListNull);
            gridView.setAdapter(adapter);
        }
    }

    void idColumns(){
        Log.d(LOG_TAG, "!!! Проверка ID !!! ");
        String query = "SELECT * FROM mytable";
        Cursor cursor = db.rawQuery(query, null);
        intID = cursor.getColumnIndex("id");
        intfirstname = cursor.getColumnIndex("firstname");
        intlastname = cursor.getColumnIndex("lastname");
        inttelephone1 = cursor.getColumnIndex("telephone1");
        inttelephone2 = cursor.getColumnIndex("telephone2");
        inttelephone3 = cursor.getColumnIndex("telephone3");
        intfacebook = cursor.getColumnIndex("facebook");
        intviber = cursor.getColumnIndex("viber");
        inttelegram = cursor.getColumnIndex("telegram");
        intemail = cursor.getColumnIndex("email");
        intChecked = true;
        cursor.close();
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