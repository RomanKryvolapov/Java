package com.example.cinemacollector;

// настройки поиска будут записываться в SharedPreferences
// чуть позже допишу код, который будет проверять при запуске, есть ли там что то, чтобы сохранялись предыдущие параметры поиска
// статусы фильмов будут писаться туда же или может быть запилю в SQLite но не уверен, так как там будет всего лишь id фильма и номер статуса
// после нажатия кнопки будут открываться фильмы
// хочу сделать классическую навигацию по списку фильмов в стиле 1, 2, 3, ... 999 а не бесконечный прокручивающийся список,
// и будет поле для ввода для перехода на страницу списка

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static int year = Calendar.getInstance().get(Calendar.YEAR);
    private static int APP_PREFERENCES_status = 1;
    private static String APP_PREFERENCES_year_1_S = "";
    private static String APP_PREFERENCES_year_2_S = "";
    private static int APP_PREFERENCES_year_1 = year;
    private static int APP_PREFERENCES_year_2 = year;
    private static int APP_PREFERENCES_rating = 1;
    private static boolean country1 = true;
    private static boolean country2 = true;
    private static boolean country3 = true;
    private static boolean country4 = true;
    private static boolean country5 = true;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        Button button = (Button) findViewById(R.id.buttonSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearCheck();
                countryCheck();
                requestGenerator();
                newActivity ();
            }
        });

        RadioGroup StatusGroup = (RadioGroup) findViewById(R.id.StatusGroup);
        StatusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        APP_PREFERENCES_status = 1;
                        break;
                    case R.id.status1:
                        APP_PREFERENCES_status = 1;
                        break;
                    case R.id.status2:
                        APP_PREFERENCES_status = 2;
                        break;
                    case R.id.status3:
                        APP_PREFERENCES_status = 3;
                        break;
                    case R.id.status4:
                        APP_PREFERENCES_status = 4;
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup RatingGroup = (RadioGroup) findViewById(R.id.RatingGroup);
        RatingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = mSettings.edit();
                switch (checkedId) {
                    case -1:
                        APP_PREFERENCES_rating = 1;
                        break;
                    case R.id.rating1:
                        APP_PREFERENCES_rating = 1;
                        break;
                    case R.id.rating2:
                        APP_PREFERENCES_rating = 2;
                        break;
                    case R.id.rating3:
                        APP_PREFERENCES_rating = 3;
                        break;
                    case R.id.rating4:
                        APP_PREFERENCES_rating = 4;
                        break;
                    case R.id.rating5:
                        APP_PREFERENCES_rating = 5;
                        break;
                    case R.id.rating6:
                        APP_PREFERENCES_rating = 6;
                        break;
                    case R.id.rating7:
                        APP_PREFERENCES_rating = 7;
                        break;
                    case R.id.rating8:
                        APP_PREFERENCES_rating = 8;
                        break;
                    case R.id.rating9:
                        APP_PREFERENCES_rating = 9;
                        break;
                    default:
                        APP_PREFERENCES_rating = 1;
                        break;
                }
            }
        });

    EditText editTextYearFrom = (EditText) findViewById(R.id.yearFrom);
        editTextYearFrom.setText(Integer.toString(year));
    EditText editTextYearTo = (EditText) findViewById(R.id.yearTo);
        editTextYearTo.setText(Integer.toString(year));

        }

        private void newActivity (){
            Intent intent = new Intent(this, activity_list.class);
            startActivity(intent);
        }


    private void countryCheck(){

        CheckBox checkBox1 = (CheckBox)findViewById(R.id.country1);
        CheckBox checkBox2 = (CheckBox)findViewById(R.id.country2);
        CheckBox checkBox3 = (CheckBox)findViewById(R.id.country3);
        CheckBox checkBox4 = (CheckBox)findViewById(R.id.country4);
        CheckBox checkBox5 = (CheckBox)findViewById(R.id.country5);

        if(checkBox1.isChecked()) {
            country1 = true;
        } else {
            country1 = false;
        }
        if(checkBox2.isChecked()) {
            country2 = true;
        } else {
            country2 = false;
        }
        if(checkBox3.isChecked()) {
            country3 = true;
        } else {
            country3 = false;
        }
        if(checkBox4.isChecked()) {
            country4 = true;
        } else {
            country4 = false;
        }
        if(checkBox5.isChecked()) {
            country5 = true;
        } else {
            country5 = false;
        }

    }


    private void yearCheck(){

        EditText editTextYear1 = (EditText) findViewById(R.id.yearFrom);
        APP_PREFERENCES_year_1_S = editTextYear1.getText().toString();
        EditText editTextYear2 = (EditText) findViewById(R.id.yearTo);
        APP_PREFERENCES_year_2_S = editTextYear2.getText().toString();

        if(!APP_PREFERENCES_year_2_S.equals("")) {
            APP_PREFERENCES_year_2 = Integer.parseInt(APP_PREFERENCES_year_2_S); // если год 2 заполнен, парсим год
        } else {
            APP_PREFERENCES_year_2 = year; // если год 2 не заполнен, ставим текущий
        }
        if(! APP_PREFERENCES_year_1_S.equals("")) {
            APP_PREFERENCES_year_1 = Integer.parseInt( APP_PREFERENCES_year_1_S); // если год 1 заполнен, парсим год
        } else {
            APP_PREFERENCES_year_1 = APP_PREFERENCES_year_2; // если год 1 не заполнен, ставим тот же, что во втором поле
        }
        if(APP_PREFERENCES_year_2 > 1900 && APP_PREFERENCES_year_2 <= year){
        } else {
            APP_PREFERENCES_year_2 = year; // если вне указанных значений, то ставим текущий
        }
        if(APP_PREFERENCES_year_1 > 1900 && APP_PREFERENCES_year_1 <= year){
        } else {
            APP_PREFERENCES_year_1 = APP_PREFERENCES_year_2; // если вне указанных значений, то ставим из года 2
        }
        if(APP_PREFERENCES_year_1 > APP_PREFERENCES_year_2){
            APP_PREFERENCES_year_2 = APP_PREFERENCES_year_1;// если год 2 меньше год 1 то ставим год 2 = год 1
        }

        editTextYear1.setText(Integer.toString(APP_PREFERENCES_year_1));
        editTextYear2.setText(Integer.toString(APP_PREFERENCES_year_2));

    }

    private void requestGenerator(){

        String country1 = "US%2C";
        String country2 = "AD%2CAL%2CAM%2CAT%2CAX%2CAZ%2CBA%2CBE%2CBG%2CBY%2CCH%2CCY%2CCZ%2CDE%2CDK%2CEE%2CES%2" +
                "CFI%2CFO%2CFR%2CGB%2CGE%2CGG%2CGI%2CGR%2CHR%2CHU%2CIE%2CIM%2CIS%2CIT%2CJE%2CKZ%2CLI%2CLT%2CLU%2CL" +
                "V%2CMC%2CMD%2CME%2CMK%2CMT%2CNL%2CNO%2CPL%2CPT%2CRO%2CRS%2CRU%2CSE%2CSI%2CSJ%2CSK%2CSM%2CTR%2CUA%2CVA";
        String country3 = "BY%2CKZ%2CRU%2CMD%2CGE%2CUA";
        String country4 = "TW%2CCN%2CKP%2CTH%2CVN%2CJP%2CKR";
        int pageNumber = 1;
        String country =  "";
        String api_key = "358d127cfc98138fe9a600b0b63bd94f";

        /*
        список стран
        США, Европа, СНГ, Азия, Другое
        в эту строку записывается запрос по странам, если выбраны определенные
        коды стран европейского региона
        AD,AL,AM,AT,AX,AZ,BA,BE,BG,BY,CH,CY,CZ,DE,DK,EE,ES,FI,FO,FR,GB,GE,GG,GI,GR,HR,HU,IE,IM,IS,IT,JE,KZ,LI,LT,LU,LV,MC,MD,ME,MK,MT,
        NL,NO,PL,PT,RO,RS,RU,SE,SI,SJ,SK,SM,TR,UA,V
        коды стран снг
        BY,KZ,RU,MD,GE,UA
        коды стран азии
        TW,CN,KP,TH,VN,JP,KR
        взято отсюда https://ru.wikipedia.org/wiki/ISO_3166-1
         */



        if(MainActivity.country1 = true){
            country = country + country1;
        }
        if(MainActivity.country2 = true){
            country = country + country2;
        }
        if(MainActivity.country3 = true){
            country = country + country3;
        }
        if(MainActivity.country4 = true){
            country = country + country4;
        }


        activity_list.requestUrl= "https://api.themoviedb.org/3/discover/movie?api_key=" +
                api_key +
                "&language=ru-RU&region="+
                country+
                "&sort_by=original_title.asc&include_adult=false&include_video=false&page=" +
                Integer.toString(pageNumber) +
                "&release_date.gte="+
                APP_PREFERENCES_year_1+
                "-01-01&release_date.lte="+
                APP_PREFERENCES_year_2+
                "-12-31&vote_average.gte="+
                APP_PREFERENCES_rating;


    }
}
