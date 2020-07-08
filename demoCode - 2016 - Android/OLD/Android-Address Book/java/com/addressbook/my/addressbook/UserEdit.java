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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserEdit extends AppCompatActivity {

    public static int userID;

    private String LOG_TAG = "MyLog";

    private Button buttonSave;
    private Button buttonDelete;
    private Cursor c;
    private SQLiteDatabase db;
    private UserEdit.DBHelper dbHelper;
    private int id = 1000;

    public static int focusPosition = 0;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private UserEdit.RecyclerViewAdapter mAdapter;
    private String[] mList;
    private String[] mList2;

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

        buttonDelete = findViewById(R.id.buttonDelete);
        buttonSave = findViewById(R.id.buttonSave);

        dbHelper = new UserEdit.DBHelper(this);

        db = dbHelper.getWritableDatabase();

        String query = "SELECT * FROM mytable WHERE id = ?";

        c = db.rawQuery(query, new String[]{Integer.toString(userID)});

        if (c.moveToFirst()) {

            idColumns();

            mList = new String[9];
            mList2 = new String[9];

            mList[0] = "First Name :";
            mList2[0] = c.getString(UserList.intfirstname);
            mList[1] = "Last Name :";
            mList2[1] = c.getString(UserList.intlastname);
            mList[2] = "Telephone 1:";
            mList2[2] = c.getString(UserList.inttelephone1);
            mList[3] = "Telephone 2:";
            mList2[3] = c.getString(UserList.inttelephone2);
            mList[4] = "Telephone 3:";
            mList2[4] = c.getString(UserList.inttelephone3);
            mList[5] = "Facebook :";
            mList2[5] = c.getString(UserList.intfacebook);
            mList[6] = "Viber :";
            mList2[6] = c.getString(UserList.intviber);
            mList[7] = "Telegram :";
            mList2[7] = c.getString(UserList.inttelegram);
            mList[8] = "Email :";
            mList2[8] = c.getString(UserList.intemail);

            mRecyclerView = (RecyclerView) findViewById(R.id.listEdit);
            mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mAdapter = new UserEdit.RecyclerViewAdapter(getApplicationContext(), mList, mList2);
            mRecyclerView.setAdapter(mAdapter);

            c.close();
            dbHelper.close();

        } else
            Log.d(LOG_TAG, "!!! База данных пуста !!!");

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
                Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserEdit.this, UserList.class);
                startActivity(intent);
                finish();

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "!!! Обновление базы данных !!!");

                // заносит поля в массив
                for (int i = 0; i < 9; i++) {
                    EditText newEditText = findViewById(1000+i);
                    mList2[i] = newEditText.getText().toString();
                }

                // проеряет введено ли имя или фамилия
                if(!mList2[0].equals("")||!mList2[1].equals(""))
                {

                    dbHelper = new UserEdit.DBHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    String queryUpdate = "UPDATE mytable SET firstname = '"+mList2[0]+"', lastname = '"+mList2[1]+"', " +
                            "telephone1 = '"+mList2[2]+"', telephone2 = '"+mList2[3]+"', " +
                            "telephone3 = '"+mList2[4]+"', facebook = '"+mList2[5]+"', " +
                            "viber = '"+mList2[6]+"', telegram = '"+mList2[7]+"'," +
                            "email = '"+mList2[8]+"'  WHERE id = "+Integer.toString(userID);

                    db.execSQL(queryUpdate);

                    c.close();
                    dbHelper.close();

                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserEdit.this, UserOpen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    void idColumns(){
        if(!UserList.intChecked) {
            Log.d(LOG_TAG, "!!! Проверка ID - должна быть 1 раз за программу !!! ");
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
            db.execSQL("create table mytable (" + "id integer primary key autoincrement,"
                    + "firstname text," + "lastname text," + "telephone1 text," + "telephone2 text," + "telephone3 text," + "facebook text," + "viber text,"
                    + "telegram text," + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<UserEdit.RecyclerViewAdapter.ViewHolder> {
        private Context mContext;
        private String[] mList;
        private String[] mList2;

        public RecyclerViewAdapter(Context contexts, String[] list, String[] list2) {
            this.mContext = contexts;
            this.mList = list;
            this.mList2 = list2;
        }
        @Override
        public UserEdit.RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.itemedit, parent, false);
            return new UserEdit.RecyclerViewAdapter.ViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final UserEdit.RecyclerViewAdapter.ViewHolder holder, final int position) {

            holder.titleTextView.setText(mList[position]);
            holder.titleTextView2.setText(mList2[position]);
            holder.titleTextView2.setId(id);
            id++;

            switch (position){
                case 0:
                case 1:
                    holder.titleTextView2.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                    break;
                case 2:
                case 3:
                case 4:
                    holder.titleTextView2.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case 8:
                    holder.titleTextView2.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
                    default:
                        holder.titleTextView2.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
            }
            if(position==focusPosition)
                holder.titleTextView2.requestFocus();

            Log.d(LOG_TAG, "!!! focusPosition = "+focusPosition+" !!! ");



            holder.setClickListener(new UserEdit.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {

//                    Toast.makeText(mContext, "#" + position + " - " + mList[position] + " (Long click)", Toast.LENGTH_SHORT).show();
                    } else {

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
            private EditText titleTextView2;

            private UserEdit.ItemClickListener clickListener;
            public ViewHolder(View itemView) {
                super(itemView);

                Log.d(LOG_TAG, "!!! ViewHolder !!! ");

                titleTextView = (TextView)itemView.findViewById(R.id.textView);
                titleTextView2 = (EditText)itemView.findViewById(R.id.textView2);

                itemView.setTag(itemView);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }
            public void setClickListener(UserEdit.ItemClickListener itemClickListener) {
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