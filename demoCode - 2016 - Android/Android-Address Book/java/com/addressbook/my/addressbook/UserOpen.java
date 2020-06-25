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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UserOpen extends AppCompatActivity {

    public static int userID;

    private String LOG_TAG = "Просмотр";

    private Button buttonEdit;

    private Cursor c;
    private SQLiteDatabase db;
    private UserOpen.DBHelper dbHelper;
    private int id = 1000;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private UserOpen.RecyclerViewAdapter mAdapter;
    private String[] mList;
    private String[] mList2;

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

        buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEdit.focusPosition = 0;
                UserEdit.userID = UserOpen.userID;
                Intent intent = new Intent(UserOpen.this, UserEdit.class);
                startActivity(intent);
            }
        });

        dbHelper = new UserOpen.DBHelper(this);

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

            mRecyclerView = (RecyclerView) findViewById(R.id.listOpen);
            mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mAdapter = new UserOpen.RecyclerViewAdapter(getApplicationContext(), mList, mList2);
            mRecyclerView.setAdapter(mAdapter);

            c.close();
            dbHelper.close();

        } else
            Log.d(LOG_TAG, "!!! База данных пуста !!!");
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

    class RecyclerViewAdapter extends RecyclerView.Adapter<UserOpen.RecyclerViewAdapter.ViewHolder> {
        private Context mContext;
        private String[] mList;
        private String[] mList2;

        public RecyclerViewAdapter(Context contexts, String[] list, String[] list2) {
            this.mContext = contexts;
            this.mList = list;
            this.mList2 = list2;
        }
        @Override
        public UserOpen.RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.itemopen, parent, false);
            return new UserOpen.RecyclerViewAdapter.ViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final UserOpen.RecyclerViewAdapter.ViewHolder holder, final int position) {

            holder.titleTextView.setText(mList[position]);
            holder.titleTextView2.setText(mList2[position]);
            holder.titleTextView2.setId(id);
            id++;

            switch (position){
                case 2:
                case 3:
                case 4:
                    holder.setClickListener(new UserOpen.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            if (isLongClick) {
                                UserEdit.focusPosition = position;
                                UserEdit.userID = UserOpen.userID;
                                Intent intent = new Intent(UserOpen.this, UserEdit.class);
                                startActivity(intent);
                            } else {
                                try {
                                    if(mList2[position].length()>0) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mList2[position], null));
                                        startActivity(intent);
                                    }
                                }catch (Exception e){
                                }
                            }
                        }
                    });
                    break;
                case 5:
                    holder.setClickListener(new UserOpen.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            if (isLongClick) {
                                UserEdit.focusPosition = position;
                                UserEdit.userID = UserOpen.userID;
                                Intent intent = new Intent(UserOpen.this, UserEdit.class);
                                startActivity(intent);
                            } else {
                                try {
                                    if(mList2[position].length()>0) {
                                        if(mList2[position].contains("https://www.facebook.com/"))
                                        {
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList2[position]));
                                            startActivity(intent);
                                        } else{
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+mList2[position]));
                                            startActivity(intent);
                                        }
                                    }
                                }catch (Exception e){
                                }
                            }
                        }
                    });
                    break;
                case 8:
                    holder.setClickListener(new UserOpen.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            if (isLongClick) {

                                UserEdit.focusPosition = position;
                                UserEdit.userID = UserOpen.userID;
                                Intent intent = new Intent(UserOpen.this, UserEdit.class);
                                startActivity(intent);

                            } else {
                                try {
                                    if(mList2[position].length()>0) {
                                        if(mList2[position].contains("@")&&mList2[position].contains(".")){
                                            Intent intent = new Intent(Intent.ACTION_SEND);
                                            intent.setType("message/rfc822");
                                            intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{mList2[position]});
                                            intent.putExtra(Intent.EXTRA_SUBJECT, " ");
                                            intent.putExtra(Intent.EXTRA_TEXT   , " ");
                                            startActivity(Intent.createChooser(intent, "Send email"));
                                        }
                                    }
                                }catch (Exception e){
                                }
                            }
                        }
                    });
                    break;
                case 0:
                case 1:
                case 6:
                case 7:
                    holder.setClickListener(new UserOpen.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            if (isLongClick) {
                                UserEdit.focusPosition = position;
                                UserEdit.userID = UserOpen.userID;
                                Intent intent = new Intent(UserOpen.this, UserEdit.class);
                                startActivity(intent);
                            } else {
                            }
                        }
                    });
                    break;
            }
        }
        @Override
        public int getItemCount() {
            return mList.length;
        }
        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener, View.OnLongClickListener{

            private TextView titleTextView;
            private TextView titleTextView2;

            private UserOpen.ItemClickListener clickListener;
            public ViewHolder(View itemView) {
                super(itemView);

                Log.d(LOG_TAG, "!!! ViewHolder !!! ");

                titleTextView = (TextView)itemView.findViewById(R.id.textView);
                titleTextView2 = (TextView)itemView.findViewById(R.id.textView2);

                itemView.setTag(itemView);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }
            public void setClickListener(UserOpen.ItemClickListener itemClickListener) {
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