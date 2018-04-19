package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити списка контактов
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.TextView;

public class UserList extends AppCompatActivity {

    TextView firstNameArray[];
    TextView lastNameArray[];

    int firstNameArrayID[];
    int lastNameArrayID[];
    int Size;
    int myID;

    String LOG_TAG = "Список";

    ConstraintLayout constraintLayout[][];
    ConstraintLayout myUserListLayout;
    ConstraintSet myConstraintSet;
    SQLiteDatabase db;
    Cursor c;
    View.OnClickListener onClickListener;
    Button buttonAddNew;
    Button buttonSearch;
    Button buttonClearSearchStrings;

    DBHelper dbHelper;

    String firstname[];
    String lastname[];
    String telephone1[];
    String telephone2[];
    String telephone3[];
    String facebook[];
    String viber[];
    String telegram[];
    String email[];

    static String searchFirstName = "";
    static String searchLastName = "";

    EditText editSearchFirstName;
    EditText editSearchLastName;

    static int intID;
    static int intfirstname;
    static int intlastname;
    static int inttelephone1;
    static int inttelephone2;
    static int inttelephone3;
    static int intfacebook;
    static int intviber;
    static int inttelegram;
    static int intemail;
    static boolean intChecked = false;
    static int sqlIDnumber[];

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

        myID=10000;

        Log.d(LOG_TAG, "!!! Start !!!");

        editSearchFirstName = findViewById(R.id.editSearchFirstName);
        editSearchLastName = findViewById(R.id.editSearchLastName);
        editSearchFirstName.setText(searchFirstName);
        editSearchLastName.setText(searchLastName);

        myUserListLayout = findViewById(R.id.UserListLayout);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        getSize();

        if (c.moveToFirst()) {

            if (!intChecked)
            idColumns();

            // создаем нужное количество элементов
            firstNameArray = new TextView[Size];
            lastNameArray = new TextView[Size];
            constraintLayout = new ConstraintLayout[Size][3];
            // создаем массивы строк с содержимым
            firstname = new String[Size];
            lastname = new String[Size];
            telephone1 = new String[Size];
            telephone2 = new String[Size];
            telephone3 = new String[Size];
            facebook = new String[Size];
            viber = new String[Size];
            telegram = new String[Size];
            email = new String[Size];
            firstNameArrayID = new int[Size];
            lastNameArrayID = new int[Size];

            c.moveToFirst();

            int i = 0;

            do {
                getStrings(i);
                i++;
            } while (c.moveToNext());

            c.close();
            dbHelper.close();

            create();
            setConstraints();

        } else
            Log.d(LOG_TAG, "!!! База данных пустая !!!");

        buttonAddNew = findViewById(R.id.buttonAddNew);
        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserList.this, UserNewNew.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchFirstName = editSearchFirstName.getText().toString();
                searchLastName = editSearchLastName.getText().toString();

                Intent intent = new Intent(UserList.this, UserList.class);
                startActivity(intent);
                finish();

            }
        });

        buttonClearSearchStrings = findViewById(R.id.buttonClearSearchStrings);
        buttonClearSearchStrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editSearchFirstName.setText("");
                editSearchLastName.setText("");
                searchFirstName = "";
                searchLastName = "";

                Intent intent = new Intent(UserList.this, UserList.class);
                startActivity(intent);
                finish();

            }
        });

    }

    void getStrings(int i){
        firstname[i] = c.getString(intfirstname);
        lastname[i] = c.getString(intlastname);
        telephone1[i] = c.getString(inttelephone1);
        telephone2[i] = c.getString(inttelephone2);
        telephone3[i] = c.getString(inttelephone3);
        facebook[i] = c.getString(intfacebook);
        viber[i] = c.getString(intviber);
        telegram[i] = c.getString(inttelegram);
        email[i] = c.getString(intemail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Log.d(LOG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    void create(){

        for (int x = 0; x < Size; x++) {
            for (int y = 0; y < 2; y++) {
                myID++;
                constraintLayout[x][y] = new ConstraintLayout(this);
                constraintLayout[x][y].setId(myID);
            }
        }

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                for (int i = 0; i < Size; i++) {
                    if(id == firstNameArrayID[i]||id == lastNameArrayID[i]) {
                        UserOpen.userID = sqlIDnumber[i];
                        Intent intent = new Intent(UserList.this, UserOpen.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                }
            }
        };


        for (int y = 0; y < Size; y++) {
            myID++;
            firstNameArray[y] = new TextView(this);
            firstNameArray[y].setText(firstname[y]);
            firstNameArray[y].setId(myID);
            firstNameArray[y].setGravity(Gravity.CENTER);
            firstNameArray[y].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            firstNameArray[y].setBackgroundResource(R.color.colorPrimary);
            firstNameArray[y].setTextColor(getResources().getColor(R.color.light_white));
            firstNameArray[y].setOnClickListener(onClickListener);
            firstNameArrayID[y]=myID;
            myID++;
            lastNameArray[y] = new TextView(this);
            lastNameArray[y].setText(lastname[y]);
            lastNameArray[y].setId(myID);
            lastNameArray[y].setGravity(Gravity.CENTER);
            lastNameArray[y].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            lastNameArray[y].setBackgroundResource(R.color.colorPrimary);
            lastNameArray[y].setTextColor(getResources().getColor(R.color.light_white));
            lastNameArray[y].setOnClickListener(onClickListener);
            lastNameArrayID[y]=myID;
        }


        for (int y = 0; y < Size; y++) {
            constraintLayout[y][0].addView(firstNameArray[y]);
            constraintLayout[y][1].addView(lastNameArray[y]);
        }

        for (int x = 0; x < Size; x++) {
            for (int y = 0; y < 2; y++) {
                myUserListLayout.addView(constraintLayout[x][y]);
            }
        }

    }

    void setConstraints() {

        if(Size>0) {

            myConstraintSet = new ConstraintSet();

            for (int y = 0; y < Size; y++) {
                myConstraintSet.clone(constraintLayout[y][0]);
                myConstraintSet.connect(firstNameArray[y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                myConstraintSet.connect(firstNameArray[y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                myConstraintSet.connect(firstNameArray[y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                myConstraintSet.connect(firstNameArray[y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                myConstraintSet.setMargin(firstNameArray[y].getId(), ConstraintSet.END, 0);
                myConstraintSet.setMargin(firstNameArray[y].getId(), ConstraintSet.START, 0);
                myConstraintSet.setMargin(firstNameArray[y].getId(), ConstraintSet.TOP, 5);
                myConstraintSet.setMargin(firstNameArray[y].getId(), ConstraintSet.BOTTOM, 5);
                myConstraintSet.constrainWidth(firstNameArray[y].getId(), ConstraintSet.MATCH_CONSTRAINT);
                myConstraintSet.constrainHeight(firstNameArray[y].getId(), ConstraintSet.WRAP_CONTENT);
                myConstraintSet.applyTo(constraintLayout[y][0]);
                myConstraintSet.clear(firstNameArray[y].getId());
                myConstraintSet.clone(constraintLayout[y][1]);
                myConstraintSet.connect(lastNameArray[y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                myConstraintSet.connect(lastNameArray[y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                myConstraintSet.connect(lastNameArray[y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                myConstraintSet.connect(lastNameArray[y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                myConstraintSet.setMargin(lastNameArray[y].getId(), ConstraintSet.END, 0);
                myConstraintSet.setMargin(lastNameArray[y].getId(), ConstraintSet.START, 0);
                myConstraintSet.setMargin(lastNameArray[y].getId(), ConstraintSet.TOP, 5);
                myConstraintSet.setMargin(lastNameArray[y].getId(), ConstraintSet.BOTTOM, 5);
                myConstraintSet.constrainWidth(lastNameArray[y].getId(), ConstraintSet.MATCH_CONSTRAINT);
                myConstraintSet.constrainHeight(lastNameArray[y].getId(), ConstraintSet.WRAP_CONTENT);
                myConstraintSet.applyTo(constraintLayout[y][1]);
                myConstraintSet.clear(lastNameArray[y].getId());
            }

            myConstraintSet.clone(myUserListLayout);

            int xSize = Size;

            int ySize = 2;

            if (Size > 1) {


                for (int x = 0; x < xSize; x++) {
                    for (int y = 0; y < ySize; y++) {

                        if (x == 0 && y == 0) //1
                        {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, constraintLayout[x][y + 1].getId(), ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, constraintLayout[x + 1][y].getId(), ConstraintSet.TOP);
                        } else if (x == 0 && y != 0 && y != ySize - 1) //2
                        {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, constraintLayout[x][y - 1].getId(), ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, constraintLayout[x][y + 1].getId(), ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, constraintLayout[x + 1][y].getId(), ConstraintSet.TOP);
                        } else if (x == 0 && y == ySize - 1) //3
                        {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, constraintLayout[x][y - 1].getId(), ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, constraintLayout[x + 1][y].getId(), ConstraintSet.TOP);
                        } else if (x != xSize - 1 && x != 0 && y == ySize - 1) {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, constraintLayout[x][y - 1].getId(), ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, constraintLayout[x - 1][y].getId(), ConstraintSet.BOTTOM);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, constraintLayout[x + 1][y].getId(), ConstraintSet.TOP);
                        } else if (x == xSize - 1 && y == ySize - 1) {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, constraintLayout[x][y - 1].getId(), ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, constraintLayout[x - 1][y].getId(), ConstraintSet.BOTTOM);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                        } else if (x == xSize - 1 && y != ySize - 1 && y != 0) {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, constraintLayout[x][y - 1].getId(), ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, constraintLayout[x][y + 1].getId(), ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, constraintLayout[x - 1][y].getId(), ConstraintSet.BOTTOM);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                        } else if (x == xSize - 1 && y == 0) {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, constraintLayout[x][y + 1].getId(), ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, constraintLayout[x - 1][y].getId(), ConstraintSet.BOTTOM);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                        } else if (x != 0 && x != xSize - 1 && y == 0) {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, constraintLayout[x][y + 1].getId(), ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, constraintLayout[x - 1][y].getId(), ConstraintSet.BOTTOM);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, constraintLayout[x + 1][y].getId(), ConstraintSet.TOP);
                        } else {
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.LEFT, constraintLayout[x][y - 1].getId(), ConstraintSet.RIGHT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.RIGHT, constraintLayout[x][y + 1].getId(), ConstraintSet.LEFT);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.TOP, constraintLayout[x - 1][y].getId(), ConstraintSet.BOTTOM);
                            myConstraintSet.connect(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, constraintLayout[x + 1][y].getId(), ConstraintSet.TOP);
                        }
                        // Margin
                        myConstraintSet.setMargin(constraintLayout[x][y].getId(), ConstraintSet.END, 0);
                        myConstraintSet.setMargin(constraintLayout[x][y].getId(), ConstraintSet.START, 0);
                        myConstraintSet.setMargin(constraintLayout[x][y].getId(), ConstraintSet.TOP, 20);
                        myConstraintSet.setMargin(constraintLayout[x][y].getId(), ConstraintSet.BOTTOM, 20);
                        // Размер
                        myConstraintSet.constrainWidth(constraintLayout[x][y].getId(), ConstraintSet.MATCH_CONSTRAINT);
                        myConstraintSet.constrainHeight(constraintLayout[x][y].getId(), ConstraintSet.WRAP_CONTENT);

                        myConstraintSet.applyTo(myUserListLayout);
                        myConstraintSet.clear(constraintLayout[x][y].getId());

                    }
                }
            } else{

                myConstraintSet.connect(constraintLayout[0][0].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                myConstraintSet.connect(constraintLayout[0][0].getId(), ConstraintSet.RIGHT, constraintLayout[0][1].getId(), ConstraintSet.LEFT);
                myConstraintSet.connect(constraintLayout[0][0].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                myConstraintSet.connect(constraintLayout[0][0].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

                myConstraintSet.connect(constraintLayout[0][1].getId(), ConstraintSet.LEFT, constraintLayout[0][0].getId(), ConstraintSet.RIGHT);
                myConstraintSet.connect(constraintLayout[0][1].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                myConstraintSet.connect(constraintLayout[0][1].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                myConstraintSet.connect(constraintLayout[0][1].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

                myConstraintSet.setMargin(constraintLayout[0][0].getId(), ConstraintSet.END, 0);
                myConstraintSet.setMargin(constraintLayout[0][0].getId(), ConstraintSet.START, 0);
                myConstraintSet.setMargin(constraintLayout[0][0].getId(), ConstraintSet.TOP, 20);
                myConstraintSet.setMargin(constraintLayout[0][0].getId(), ConstraintSet.BOTTOM, 20);

                myConstraintSet.setMargin(constraintLayout[0][1].getId(), ConstraintSet.END, 0);
                myConstraintSet.setMargin(constraintLayout[0][1].getId(), ConstraintSet.START, 0);
                myConstraintSet.setMargin(constraintLayout[0][1].getId(), ConstraintSet.TOP, 20);
                myConstraintSet.setMargin(constraintLayout[0][1].getId(), ConstraintSet.BOTTOM, 20);

                myConstraintSet.constrainWidth(constraintLayout[0][0].getId(), ConstraintSet.MATCH_CONSTRAINT);
                myConstraintSet.constrainHeight(constraintLayout[0][0].getId(), ConstraintSet.WRAP_CONTENT);

                myConstraintSet.constrainWidth(constraintLayout[0][1].getId(), ConstraintSet.MATCH_CONSTRAINT);
                myConstraintSet.constrainHeight(constraintLayout[0][1].getId(), ConstraintSet.WRAP_CONTENT);

                myConstraintSet.applyTo(myUserListLayout);
                myConstraintSet.clear(constraintLayout[0][0].getId());
                myConstraintSet.clear(constraintLayout[0][1].getId());
            }


        }
    }

    void idColumns(){
            Log.d(LOG_TAG, "!!! Проверка ID !!! ");
            intID = c.getColumnIndex("id");
            intfirstname = c.getColumnIndex("firstname");
            intlastname = c.getColumnIndex("lastname");
            inttelephone1 = c.getColumnIndex("telephone1");
            inttelephone2 = c.getColumnIndex("telephone2");
            inttelephone3 = c.getColumnIndex("telephone3");
            intfacebook = c.getColumnIndex("facebook");
            intviber = c.getColumnIndex("viber");
            inttelegram = c.getColumnIndex("telegram");
            intemail = c.getColumnIndex("email");
            intChecked = true;
    }

    void getSize(){

        Size = 0;

        if (searchFirstName.equals("") && searchLastName.equals("")) {

            Log.d(LOG_TAG, "!!! Условие 1 !!!");

            String query = "SELECT * FROM mytable ORDER BY firstname";

            c = db.rawQuery(query, null);

            Size = c.getCount();

        } else if (!searchFirstName.equals("") && searchLastName.equals("")) {

            Log.d(LOG_TAG, "!!! Условие 2 !!!");

            String query = "SELECT * FROM mytable WHERE firstname LIKE ? ORDER BY firstname";

            String query2 = "%"+searchFirstName+"%";

            c = db.rawQuery(query, new String[]{query2});

            Size = c.getCount();


        } else if (searchFirstName.equals("") && !searchLastName.equals("")) {

            Log.d(LOG_TAG, "!!! Условие 3 !!!");

            String query = "SELECT * FROM mytable WHERE lastname LIKE ? ORDER BY firstname";

            String query2 = "%"+searchLastName+"%";

            c = db.rawQuery(query, new String[]{query2});

            Size = c.getCount();

        } else if (!searchFirstName.equals("") && !searchLastName.equals("")) {

            Log.d(LOG_TAG, "!!! Условие 4 !!!");

            String query2 = "%"+searchFirstName+"%";

            String query3 = "%"+searchLastName+"%";

            String query = "SELECT * FROM mytable WHERE firstname LIKE ? AND lastname LIKE ? ORDER BY firstname";

            c = db.rawQuery(query, new String[]{query2, query3});

            Size = c.getCount();

        }

        if (c.moveToFirst()) {

            sqlIDnumber = new int[Size];

            c.moveToFirst();

            int iterator1 = 0;

            do {
                sqlIDnumber[iterator1] = c.getInt(intID);
                iterator1++;
            } while (c.moveToNext());

            iterator1 = 0;

        } else {
            Log.d(LOG_TAG, "!!! Нет строк для отображения !!!");

        }
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
