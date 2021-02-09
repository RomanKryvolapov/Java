package com.romankryvolapov.exchangerates;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TableLayout PBcourse;
    private TableLayout NBUcourse;
    private boolean rowCounter = false;
    private String TAG = "MainActivity";
    private int textSize = 18;
    private String currentDateAndTime;
    private TextView dateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PBcourse = findViewById(R.id.PBcourse);
        NBUcourse = findViewById(R.id.NBUcourse);
        dateAndTime = findViewById(R.id.dateAndTime);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        currentDateAndTime = sdf.format(new Date());
        getCourseJson(currentDateAndTime);
        dateAndTime.setText(currentDateAndTime);
    }

    private TableRow setPBcourse(String currency, String purchaseRate, String saleRate) {
        Log.d(TAG, currency + " " + purchaseRate + " " + saleRate);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.setGravity(Gravity.CENTER);

        TextView textViewCurrency = new TextView(this);
        textViewCurrency.setText(currency);
        textViewCurrency.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textViewCurrency.setGravity(Gravity.CENTER);
        textViewCurrency.setTextColor(getResources().getColor(R.color.text_2));
        textViewCurrency.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textViewCurrency.setTypeface(textViewCurrency.getTypeface(), Typeface.BOLD);
        tableRow.addView(textViewCurrency);

        TextView textViewPurchaseRate = new TextView(this);
        textViewPurchaseRate.setText(purchaseRate);
        textViewPurchaseRate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textViewPurchaseRate.setGravity(Gravity.CENTER);
        textViewPurchaseRate.setTextColor(getResources().getColor(R.color.text_2));
        textViewPurchaseRate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textViewPurchaseRate.setTypeface(textViewPurchaseRate.getTypeface(), Typeface.BOLD);
        tableRow.addView(textViewPurchaseRate);

        TextView textViewSaleRate = new TextView(this);
        textViewSaleRate.setText(saleRate);
        textViewSaleRate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textViewSaleRate.setGravity(Gravity.CENTER);
        textViewSaleRate.setTextColor(getResources().getColor(R.color.text_2));
        textViewSaleRate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textViewSaleRate.setTypeface(textViewSaleRate.getTypeface(), Typeface.BOLD);
        tableRow.addView(textViewSaleRate);

        return tableRow;
    }

    private TableRow setNBcourse(String currency, String purchaseRateNB) {
        Log.d(TAG, currency + " " + purchaseRateNB);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.setGravity(Gravity.CENTER);

        if(rowCounter){
            rowCounter = false;
            tableRow.setBackgroundColor(getResources().getColor(R.color.color_2));
        } else {
            rowCounter = true;
        }

        TextView textViewPurchaseRateNB = new TextView(this);
        textViewPurchaseRateNB.setPadding(100, 0, 0, 0);
        textViewPurchaseRateNB.setText(currency);
        textViewPurchaseRateNB.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textViewPurchaseRateNB.setGravity(Gravity.START);
        textViewPurchaseRateNB.setTextColor(getResources().getColor(R.color.text_2));
        textViewPurchaseRateNB.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textViewPurchaseRateNB.setTypeface(textViewPurchaseRateNB.getTypeface(), Typeface.BOLD);
        tableRow.addView(textViewPurchaseRateNB);

        TextView textViewSaleRateNB = new TextView(this);
        textViewSaleRateNB.setPadding(0, 0, 100, 0);
        textViewSaleRateNB.setText(purchaseRateNB);
        textViewSaleRateNB.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textViewSaleRateNB.setGravity(Gravity.END);
        textViewSaleRateNB.setTextColor(getResources().getColor(R.color.text_2));
        textViewSaleRateNB.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textViewSaleRateNB.setTypeface(textViewSaleRateNB.getTypeface(), Typeface.BOLD);
        tableRow.addView(textViewSaleRateNB);

        return tableRow;
    }

    private void parseCourse(String dataJson) {
        try {
            JSONObject jsonObject = new JSONObject(dataJson);
            JSONArray jsonArray = jsonObject.getJSONArray("exchangeRate");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectValue = jsonArray.getJSONObject(i);
                String currency = jsonObjectValue.optString("currency", null);
                String purchaseRate = jsonObjectValue.optString("purchaseRate", null);
                String saleRate = jsonObjectValue.optString("saleRate", null);
                String purchaseRateNB = jsonObjectValue.optString("purchaseRateNB", null);
                String saleRateNB = jsonObjectValue.optString("saleRateNB", null);
                if (currency != null && currency.length()>0 && purchaseRate != null && saleRate != null)
                    PBcourse.addView(setPBcourse(currency, purchaseRate, saleRate));
                if (currency != null && currency.length()>0 && purchaseRateNB != null && saleRateNB != null && !currency.equals("UAH"))
                    NBUcourse.addView(setNBcourse(currency, purchaseRateNB + " UAH"));
            }
        } catch (Exception e) {
            Log.e(TAG, "parsePBcourse Exception " + e);
        }

    }


    private void getCourseJson(String date) {
        new ObservableCreate<String>(emitter -> {
            Request.Builder builder = new Request.Builder();
            builder.url("https://api.privatbank.ua/p24api/exchange_rates?json&date=" + date);
            Request request = builder.build();
            Response response = buildHttpClient().newCall(request).execute();
            emitter.onNext(response.body().string());
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        parseCourse(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    private OkHttpClient buildHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }


}