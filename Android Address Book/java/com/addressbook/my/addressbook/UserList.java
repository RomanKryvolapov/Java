package com.addressbook.my.addressbook;
// made by Roman Kryvolapov
// активити списка контактов
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.TextView;

public class UserList extends AppCompatActivity {

    private String LOG_TAG = "Список";

    private Button buttonAddNew;
    private Button buttonClearSearchStrings;
    private EditText editSearchFirstName;
    private EditText editSearchLastName;

    public static boolean correctEnter = false;

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor c;

    private int sqlIDnumber[];
    private int Size = 0;

    static String searchFirstName = "";
    static String searchLastName = "";

    public static int userID;

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

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private String[] mList;
    private String[] mList2;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(LOG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(correctEnter){
        setContentView(R.layout.activity_user_list);
        Log.d(LOG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        editSearchFirstName = findViewById(R.id.editSearchFirstName);
        editSearchLastName = findViewById(R.id.editSearchLastName);
        buttonAddNew = findViewById(R.id.buttonAddNew);
        buttonClearSearchStrings = findViewById(R.id.buttonClearSearchStrings);

            editSearchFirstName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    searchFirstName = charSequence.toString();
                    reloadTable();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editSearchLastName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    searchLastName = charSequence.toString();
                    reloadTable();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

//        editSearchLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                    searchFirstName = editSearchFirstName.getText().toString();
//                    searchLastName = editSearchLastName.getText().toString();
//                    return false;
//                }
//            });
//
//        editSearchFirstName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                    searchFirstName = editSearchFirstName.getText().toString();
//                    searchLastName = editSearchLastName.getText().toString();
//                    reloadTable();
//                    return false;
//                }
//            });
//
//        editSearchFirstName.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                searchFirstName = editSearchFirstName.getText().toString();
//                searchLastName = editSearchLastName.getText().toString();
//                reloadTable();
//                return false;
//            }
//        });
//        editSearchLastName.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                searchFirstName = editSearchFirstName.getText().toString();
//                searchLastName = editSearchLastName.getText().toString();
//                reloadTable();
//                return false;
//            }
//        });

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
            editSearchFirstName.setText(searchFirstName);
            editSearchLastName.setText(searchLastName);

            reloadTable();

    }
    }

    void reloadTable(){

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        if (searchFirstName.equals("") && searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие - поля поиска пусты !!!");
            String query = "SELECT id, firstname, lastname FROM mytable ORDER BY firstname, lastname";
            c = db.rawQuery(query, null);
            Size = c.getCount();
        } else if (!searchFirstName.equals("") && searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие - поле поиска 1 заполнено !!!");
            String query = "SELECT id, firstname, lastname FROM mytable WHERE firstname LIKE ? ORDER BY firstname, lastname";
            String query2 = "%"+searchFirstName+"%";
            c = db.rawQuery(query, new String[]{query2});
            Size = c.getCount();
        } else if (searchFirstName.equals("") && !searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие - поле поиска 2 заполнено !!!");
            String query = "SELECT id, firstname, lastname FROM mytable WHERE lastname LIKE ? ORDER BY firstname, lastname";
            String query2 = "%"+searchLastName+"%";
            c = db.rawQuery(query, new String[]{query2});
            Size = c.getCount();
        } else if (!searchFirstName.equals("") && !searchLastName.equals("")) {
            Log.d(LOG_TAG, "!!! Условие - оба поля поиска заполнены !!!");
            String query2 = "%"+searchFirstName+"%";
            String query3 = "%"+searchLastName+"%";
            String query = "SELECT id, firstname, lastname FROM mytable WHERE firstname LIKE ? AND lastname LIKE ? ORDER BY firstname, lastname";
            c = db.rawQuery(query, new String[]{query2, query3});
            Size = c.getCount();
        }

        if (c.moveToFirst()) {

            sqlIDnumber = new int[Size];
            mList = new String[Size];
            mList2 = new String[Size];

            c.moveToFirst();

            if (!intChecked)
                idColumns();

            c.moveToFirst();

            int iterator = 0;

            do {
                sqlIDnumber[iterator] = c.getInt(intID);
                mList[iterator] = c.getString(intfirstname);
                mList2[iterator] = c.getString(intlastname);
                iterator++;
            } while (c.moveToNext());

            c.close();
            dbHelper.close();

            mRecyclerView = (RecyclerView) findViewById(R.id.list);
            mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mAdapter = new RecyclerViewAdapter(getApplicationContext(), mList, mList2);
            mRecyclerView.setAdapter(mAdapter);


        } else
        {

            mList = new String[0];
            mList2 = new String[0];

            mRecyclerView = (RecyclerView) findViewById(R.id.list);
            mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mAdapter = new RecyclerViewAdapter(getApplicationContext(), mList, mList2);
            mRecyclerView.setAdapter(mAdapter);


        }
    }

    void idColumns(){
        Log.d(LOG_TAG, "!!! Проверка ID колонок - должна быть 1 раз !!! ");
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

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private Context mContext;
        private String[] mList;
        private String[] mList2;

        public RecyclerViewAdapter(Context contexts, String[] list, String[] list2) {
            this.mContext = contexts;
            this.mList = list;
            this.mList2 = list2;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item, parent, false);
            return new ViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.titleTextView.setText(mList[position]);
            holder.titleTextView2.setText(mList2[position]);

            holder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {

                        UserList.userID = position;
                        UserEdit.focusPosition = 0;
                        Log.d(LOG_TAG, "!!! Открытие редактирования !!! ");
                        UserOpen.userID = sqlIDnumber[userID];
                        UserEdit.userID = sqlIDnumber[userID];
                        Intent intent = new Intent(UserList.this, UserEdit.class);
                        startActivity(intent);
                        finish();

//                    Toast.makeText(mContext, "#" + position + " - " + mList[position] + " (Long click)", Toast.LENGTH_SHORT).show();
                    } else {

                        UserList.userID = position;
                        UserEdit.focusPosition = 0;
                        Log.d(LOG_TAG, "!!! Открытие просмотра !!! ");
                        UserOpen.userID = sqlIDnumber[userID];
                        Intent intent = new Intent(UserList.this, UserOpen.class);
                        startActivity(intent);
                        finish();

//                    Toast.makeText(mContext, "#" + position + " - " + mList[position], Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return mList.length;
        }
        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener, View.OnLongClickListener{

            private TextView titleTextView;
            private TextView titleTextView2;

            private ItemClickListener clickListener;
            public ViewHolder(View itemView) {
                super(itemView);

                Log.d(LOG_TAG, "!!! ViewHolder - должен быть несколько раз !!! ");
                titleTextView = (TextView)itemView.findViewById(R.id.textView);
                titleTextView2 = (TextView)itemView.findViewById(R.id.textView2);

                itemView.setTag(itemView);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }
            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getPosition(), false);
            }
            @Override
            public boolean onLongClick(View view) {
                clickListener.onClick(view, getPosition(), true);
                return true;
            }
        }
    }

    interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }



}
