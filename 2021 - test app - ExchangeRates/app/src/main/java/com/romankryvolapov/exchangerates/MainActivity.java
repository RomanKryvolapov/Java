package com.romankryvolapov.exchangerates;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Getter
@Setter
@NoArgsConstructor
public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TableLayout PBcourse;
    private TableLayout NBUcourse;
    private boolean rowCounter = false;
    private final String TAG = "MainActivity";
    private String dateAndTimeString;
    private TextView dateAndTime;
    private TextView dateAndTime2;
    private SimpleDateFormat simpleDateFormat;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPBcourse(findViewById(R.id.PBcourse));
        setNBUcourse(findViewById(R.id.NBUcourse));
        setDateAndTime(findViewById(R.id.dateAndTime));
        setDateAndTime2(findViewById(R.id.dateAndTime2));
        ImageButton dateAndTimeButton = findViewById(R.id.dateAndTimeButton);
        ImageButton dateAndTimeButton2 = findViewById(R.id.dateAndTimeButton2);
        setSimpleDateFormat(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()));
        setDateAndTimeString(getSimpleDateFormat().format(new Date()));
        getDateAndTime().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        getDateAndTime2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        dateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        dateAndTimeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        getCourseJson(getDateAndTimeString());
//        getCourseJson("09.02.2021");

    }

    private TableRow setPBcourse(String currency, String purchaseRate, String saleRate) {
        Log.d(getTAG(), currency + " " + purchaseRate + " " + saleRate);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.setGravity(Gravity.CENTER);
        tableRow.addView(textViewGenerator(currency, Gravity.CENTER, 0, 0, 18, Typeface.BOLD));
        tableRow.addView(textViewGenerator(purchaseRate + " UAH", Gravity.CENTER, 0, 0, 18, Typeface.BOLD));
        tableRow.addView(textViewGenerator(saleRate + " UAH", Gravity.CENTER, 0, 0, 18, Typeface.BOLD));
        return tableRow;
    }

    private TableRow setNBcourse(String currency, String purchaseRateNB) {
        Log.d(getTAG(), currency + " " + purchaseRateNB);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.setGravity(Gravity.CENTER);

        if (isRowCounter()) {
            setRowCounter(false);
            tableRow.setBackgroundColor(getResources().getColor(R.color.color_2));
        } else {
            setRowCounter(true);
        }

        tableRow.addView(textViewGenerator(currency, Gravity.START, 100, 0, 18, Typeface.BOLD));
        tableRow.addView(textViewGenerator(purchaseRateNB + " UAH", Gravity.END, 0, 100, 18, Typeface.BOLD));

        return tableRow;
    }

    @SuppressLint("DefaultLocale")
    private void parseCourse(String dataJson) {
        try {
            JSONObject jsonObject = new JSONObject(dataJson);
            JSONArray jsonArray = jsonObject.getJSONArray("exchangeRate");
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);
            tableRow.addView(textViewGenerator("Валюта", Gravity.CENTER, 0, 0, 18, Typeface.NORMAL));
            tableRow.addView(textViewGenerator("Продажа", Gravity.CENTER, 0, 0, 18, Typeface.NORMAL));
            tableRow.addView(textViewGenerator("Покупка", Gravity.CENTER, 0, 0, 18, Typeface.NORMAL));
            getPBcourse().addView(tableRow);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectValue = jsonArray.getJSONObject(i);
                String currency = jsonObjectValue.optString("currency", null);
                double purchaseRate = jsonObjectValue.optDouble("purchaseRate", 0d);
                double saleRate = jsonObjectValue.optDouble("saleRate", 0d);
                double purchaseRateNB = jsonObjectValue.optDouble("purchaseRateNB", 0d);
//                double saleRateNB = jsonObjectValue.optDouble("saleRateNB", 0d);
                String purchaseRateString = String.format("%.2f", purchaseRate);
                String saleRateString = String.format("%.2f", saleRate);
                String purchaseRateNBString = String.format("%.2f", purchaseRateNB);
                if (currency != null && currency.length() > 0 && !purchaseRateString.equals("0.00") && !saleRateString.equals("0.00")) {
                    getPBcourse().addView(setPBcourse(currency, purchaseRateString, saleRateString));
                }
                if (currency != null && currency.length() > 0 && !purchaseRateNBString.equals("0.00") && !currency.equals("UAH")) {
                    getNBUcourse().addView(setNBcourse(currency, purchaseRateNBString));
                }
            }
        } catch (Exception e) {
            Log.e(getTAG(), "parsePBcourse Exception " + e);
        }

    }

    private void getCourseJson(String date) {
        new ObservableCreate<String>(emitter -> {
            Request.Builder builder = new Request.Builder();
            builder.url("https://api.privatbank.ua/p24api/exchange_rates?json&date=" + date);
            Request request = builder.build();
            Response response = buildHttpClient().newCall(request).execute();
            if (response.code() >= 200 && response.code() < 300) {
                emitter.onNext(response.body().string());
            } else {
                throw new Exception();
            }
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getDateAndTime().setText(getDateAndTimeString());
                        getDateAndTime2().setText(getDateAndTimeString());
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        parseCourse(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(getTAG(), "getCourseJson Exception" + e);
                        Toast toast = Toast.makeText(getApplicationContext(), "Нет интернет соединения", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    private static OkHttpClient buildHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public TextView textViewGenerator(String text, int gravity, int start, int end, int textSize, int typeface) {
        TextView textView = new TextView(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        layoutParams.setMarginStart(start);
        layoutParams.setMarginEnd(end);
        textView.setText(text);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(gravity);
        textView.setTextColor(getResources().getColor(R.color.text_2));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textView.setTypeface(textView.getTypeface(), typeface);
        return textView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        getPBcourse().removeAllViews();
        getNBUcourse().removeAllViews();
        setDateAndTimeString(getSimpleDateFormat().format(date));
        getCourseJson(getDateAndTimeString());
    }

    public void setDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(MainActivity.this, this, year, month, day).show();
    }
}