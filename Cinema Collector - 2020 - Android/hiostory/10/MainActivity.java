package com.example.cinemacollector;

// настройки поиска будут записываться в SharedPreferences
// чуть позже допишу код, который будет проверять при запуске, есть ли там что то, чтобы сохранялись предыдущие параметры поиска
// статусы фильмов будут писаться туда же или может быть запилю в SQLite но не уверен, так как там будет всего лишь id фильма и номер статуса
// после нажатия кнопки будут открываться фильмы
// хочу сделать классическую навигацию по списку фильмов в стиле 1, 2, 3, ... 999 а не бесконечный прокручивающийся список,
// и будет поле для ввода для перехода на страницу списка

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static int yearNow = Calendar.getInstance().get(Calendar.YEAR);
//    private boolean country1_selected = true;
//    private boolean country2_selected = true;
//    private boolean country3_selected = true;
//    private boolean country4_selected = true;
//    private boolean country5_selected = true;
    private final int vote_count = 100;
    private int rating = 1;
    private int year1;
    private int year2;
    SharedPreferences mSettings;
    private static final String api_key = "358d127cfc98138fe9a600b0b63bd94f";
    public String requestUrl = "";
    RadioGroup RatingGroup_rating;
    EditText editTextYearFrom;
    EditText editTextYearTo;
    CheckBox checkBox_country1;
    CheckBox checkBox_country2;
    CheckBox checkBox_country3;
    CheckBox checkBox_country4;
    CheckBox checkBox_country5;
    RadioGroup RatingGroup_sort;
    Button buttonSearch;
    Button button_watch_list;
    Button button_saw;
    private String sort_by = "radioButton_sort_by_vote_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearCheck();
                requestGenerator();
                Intent intent = new Intent(MainActivity.this, activity_list.class);
                intent.putExtra("requestUrl", requestUrl);
                startActivity(intent);
            }
        });

        button_watch_list = (Button) findViewById(R.id.button_want_to_see);
        button_watch_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyListsActivity.class);
                intent.putExtra("status", 2);
                startActivity(intent);

            }
        });

        button_saw = (Button) findViewById(R.id.button_saw);
        button_saw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyListsActivity.class);
                intent.putExtra("status", 3);
                startActivity(intent);
            }
        });

        setStatusBarColor(R.color.colorPrimaryDark);

        editTextYearFrom = (EditText) findViewById(R.id.yearFrom);
        editTextYearFrom.setText(Integer.toString(yearNow));
        editTextYearTo = (EditText) findViewById(R.id.yearTo);
        editTextYearTo.setText(Integer.toString(yearNow));

        RatingGroup_rating = (RadioGroup) findViewById(R.id.RatingGroup_rating);
        RadioButton radioButton_rating1 = (RadioButton) RatingGroup_rating.findViewById(R.id.rating1);
        radioButton_rating1.setChecked(true);
        rating = 1;
        RatingGroup_rating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rating1:
                        rating = 1;
                        break;
                    case R.id.rating2:
                        rating = 2;
                        break;
                    case R.id.rating3:
                        rating = 3;
                        break;
                    case R.id.rating4:
                        rating = 4;
                        break;
                    case R.id.rating5:
                        rating = 5;
                        break;
                    case R.id.rating6:
                        rating = 6;
                        break;
                    case R.id.rating7:
                        rating = 7;
                        break;
                    case R.id.rating8:
                        rating = 8;
                        break;
                    case R.id.rating9:
                        rating = 9;
                        break;

                }
            }
        });

        RatingGroup_sort = (RadioGroup) findViewById(R.id.RatingGroup_sort);
        RadioButton radioButton_sort_by_vote_count = (RadioButton) RatingGroup_sort.findViewById(R.id.radioButton_sort_by_vote_count);
        radioButton_sort_by_vote_count.setChecked(true);

        RatingGroup_sort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_sort_by_vote_count:
                        sort_by = "vote_count.desc";
                        break;
                    case R.id.radioButton_sort_by_vote_average:
                        sort_by = "vote_average.desc";
                        break;
                    case R.id.radioButton_sort_by_primary_release_date:
                        sort_by = "primary_release_date.desc";
                        break;
                }
            }
        });


//        checkBox_country1 = (CheckBox) findViewById(R.id.country1);
//        checkBox_country1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    country1_selected = true;
//                    Log.i("status", "country1 - True");
//                } else {
//                    country1_selected = false;
//                    Log.i("status", "country1 - False");
//                }
//            }
//        });
//
//        checkBox_country2 = (CheckBox) findViewById(R.id.country2);
//        checkBox_country2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    country2_selected = true;
//                    Log.i("status", "country2 - True");
//                } else {
//                    country2_selected = false;
//                    Log.i("status", "country2 - False");
//                }
//            }
//        });
//        checkBox_country3 = (CheckBox) findViewById(R.id.country3);
//        checkBox_country3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    country3_selected = true;
//                    Log.i("status", "country3 - True");
//                } else {
//                    country3_selected = false;
//                    Log.i("status", "country3 - False");
//                }
//            }
//        });
//        checkBox_country4 = (CheckBox) findViewById(R.id.country4);
//        checkBox_country4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    country4_selected = true;
//                    Log.i("status", "country4 - True");
//                } else {
//                    country4_selected = false;
//                    Log.i("status", "country4 - False");
//                }
//            }
//        });
//        checkBox_country5 = (CheckBox) findViewById(R.id.country5);
//        checkBox_country5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    country5_selected = true;
//                    Log.i("status", "country5 - True");
//                } else {
//                    country5_selected = false;
//                    Log.i("status", "country5 - False");
//                }
//            }
//        });

    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }





    private void yearCheck() {

        editTextYearFrom = (EditText) findViewById(R.id.yearFrom);
        String year1 = editTextYearFrom.getText().toString();
        editTextYearTo = (EditText) findViewById(R.id.yearTo);
        String year2 = editTextYearTo.getText().toString();

        if (!year2.equals("")) {
            this.year2 = Integer.parseInt(year2); // если год 2 заполнен, парсим год
        } else {
            this.year2 = yearNow; // если год 2 не заполнен, ставим текущий
        }
        if (!year1.equals("")) {
            this.year1 = Integer.parseInt(year1); // если год 1 заполнен, парсим год
        } else {
            this.year1 = this.year2; // если год 1 не заполнен, ставим тот же, что во втором поле
        }
        if (this.year2 > 1900 && this.year2 <= yearNow) {
        } else {
            this.year2 = yearNow; // если вне указанных значений, то ставим текущий
        }
        if (this.year1 > 1900 && this.year1 <= yearNow) {
        } else {
            this.year1 = this.year2; // если вне указанных значений, то ставим из года 2
        }
        if (this.year1 > this.year2) {
            this.year2 = this.year1;// если год 2 меньше год 1 то ставим год 2 = год 1
        }

        editTextYearFrom.setText(Integer.toString(this.year1));
        editTextYearTo.setText(Integer.toString(this.year2));

    }

    private void requestGenerator() {

//        String country1 = "US%2C";
//        String country2 = "AD%2CAL%2CAM%2CAT%2CAX%2CAZ%2CBA%2CBE%2CBG%2CBY%2CCH%2CCY%2CCZ%2CDE%2CDK%2CEE%2CES%2" +
//                "CFI%2CFO%2CFR%2CGB%2CGE%2CGG%2CGI%2CGR%2CHR%2CHU%2CIE%2CIM%2CIS%2CIT%2CJE%2CKZ%2CLI%2CLT%2CLU%2CL" +
//                "V%2CMC%2CMD%2CME%2CMK%2CMT%2CNL%2CNO%2CPL%2CPT%2CRO%2CRS%2CRU%2CSE%2CSI%2CSJ%2CSK%2CSM%2CTR%2CUA%2CVA";
//        String country3 = "BY%2CKZ%2CRU%2CMD%2CGE%2CUA";
//        String country4 = "TW%2CCN%2CKP%2CTH%2CVN%2CJP%2CKR";
////        int pageNumber = 1;
//        String country = "";
//
//
//        /*
//        список стран
//        США, Европа, СНГ, Азия, Другое
//        в эту строку записывается запрос по странам, если выбраны определенные
//        коды стран европейского региона
//        AD,AL,AM,AT,AX,AZ,BA,BE,BG,BY,CH,CY,CZ,DE,DK,EE,ES,FI,FO,FR,GB,GE,GG,GI,GR,HR,HU,IE,IM,IS,IT,JE,KZ,LI,LT,LU,LV,MC,MD,ME,MK,MT,
//        NL,NO,PL,PT,RO,RS,RU,SE,SI,SJ,SK,SM,TR,UA,V
//        коды стран снг
//        BY,KZ,RU,MD,GE,UA
//        коды стран азии
//        TW,CN,KP,TH,VN,JP,KR
//        взято отсюда https://ru.wikipedia.org/wiki/ISO_3166-1
//         */
//
//
//        if (country1_selected) {
//            country = country + country1;
//        }
//        if (country2_selected) {
//            country = country + country2;
//        }
//        if (this.country3_selected) {
//            country = country + country3;
//        }
//        if (this.country4_selected) {
//            country = country + country4;
//        }


        requestUrl = "https://api.themoviedb.org/3/discover/movie?" +
                "api_key=" +
                api_key +
                "&language=ru-RU" +
//                "&region=" +
//                country +
                "&sort_by=" +
                sort_by +
                "&include_adult=false" +
                "&include_video=false" +
                "&primary_release_date.gte=" +
                this.year1 +
                "-01-01" +
                "&primary_release_date.lte=" +
                this.year2 +
                "-12-31" +
                "&vote_average.gte=" +
                this.rating +
                "&vote_count.gte=" +
                vote_count +
                "&page=";


    }
}
