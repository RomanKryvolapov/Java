package com.stringcalculator.my.stringcalculator;
// made by Roman Kryvolapov
// Новая версия калькулятора, с двумя дисплеями- для ввода и результата
// Умеет считать со скобками, причем скобок можно использовать много уровней
// Тап по дисплеям стирает последнюю цифру
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Double> digits = new ArrayList<Double>();
    // числа
    static ArrayList<Character> operators = new ArrayList<Character>();
    // операторы
    static ArrayList<Integer> proirity = new ArrayList<Integer>();
    // приоритет - скобки, можно использовать много уровней

    static String display = "";
    static String displayConvert = "";
    static int[] buttonID = new int[17];
    static String[] buttonNumber = new String[17];
    static int inerator = 0;
    final static int textSize = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        buttonID[0]=getResources().getIdentifier("button0", "id", getPackageName());
        buttonID[1]=getResources().getIdentifier("button1", "id", getPackageName());
        buttonID[2]=getResources().getIdentifier("button2", "id", getPackageName());
        buttonID[3]=getResources().getIdentifier("button3", "id", getPackageName());
        buttonID[4]=getResources().getIdentifier("button4", "id", getPackageName());
        buttonID[5]=getResources().getIdentifier("button5", "id", getPackageName());
        buttonID[6]=getResources().getIdentifier("button6", "id", getPackageName());
        buttonID[7]=getResources().getIdentifier("button7", "id", getPackageName());
        buttonID[8]=getResources().getIdentifier("button8", "id", getPackageName());
        buttonID[9]=getResources().getIdentifier("button9", "id", getPackageName());
        buttonID[10]=getResources().getIdentifier("buttonTochka", "id", getPackageName());
        buttonID[11]=getResources().getIdentifier("buttonPlus", "id", getPackageName());
        buttonID[12]=getResources().getIdentifier("buttonMinus", "id", getPackageName());
        buttonID[13]=getResources().getIdentifier("buttonUmnojenie", "id", getPackageName());
        buttonID[14]=getResources().getIdentifier("buttonDelenie", "id", getPackageName());
        buttonID[15]=getResources().getIdentifier("buttonSkobkaOtkritie", "id", getPackageName());
        buttonID[16]=getResources().getIdentifier("buttonSkobkaZakritie", "id", getPackageName());

        buttonNumber[0]= "0";
        buttonNumber[1]= "1";
        buttonNumber[2]= "2";
        buttonNumber[3]= "3";
        buttonNumber[4]= "4";
        buttonNumber[5]= "5";
        buttonNumber[6]= "6";
        buttonNumber[7]= "7";
        buttonNumber[8]= "8";
        buttonNumber[9]= "9";
        buttonNumber[10]= ".";
        buttonNumber[11]= "+";
        buttonNumber[12]= "-";
        buttonNumber[13]= "*";
        buttonNumber[14]= "/";
        buttonNumber[15]= "(";
        buttonNumber[16]= ")";

        for(int i = 0; i<11; i++) {
            onTouchListenerColor1();
            inerator++;
        }
        for(int i = 0; i<6; i++) {
            onTouchListenerColor2();
            inerator++;
        }

        onTouchListenerRavno ();
        onTouchListenerC();
        onTouchListenerTr();
        // Слушатели кнопок, те, которые получилось, объединил в циклы
        textSizeChange("");
        textSizeChangeResult("");
        displayDisplay("");
        displayDisplayResult("0");
    }

    public void onTouchListenerTr(){
        Button button = (Button) findViewById(R.id.buttonTr);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if(display.length()>0) {
                            StringBuffer stringBuffer = new StringBuffer(display);
                            stringBuffer.setLength(stringBuffer.length() - 1);
                            display = stringBuffer.toString();
                            stringBuffer = null;
                            // зануляю класс, так как вроде это помогает сборщику мусора
                            displayDisplay("");
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerRavno(){
        Button button = (Button) findViewById(R.id.buttonRavno);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonRavno)).setBackgroundColor(0xFFB46D16);

                        if(display.length()>0) {

                            digits.clear();
                            operators.clear();
                            proirity.clear();

                            DigitsReader.digitsReader(display);
                            ConvertTo.convertTo();
                            // Конвертирует строку в выражение, добавляет скобки, если нужно
                            displayDisplay("");
                            Calculator.calculator();
                            // Делает рассчет
                            displayDisplayResult(Double.toString(digits.get(0)));

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonRavno)).setBackgroundColor(0xFFFA9112);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }


    public void onTouchListenerC(){
        Button button = (Button) findViewById(R.id.buttonC);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonC)).setBackgroundColor(0xFF95969B);

                        digits.clear();
                        operators.clear();
                        proirity.clear();
                        display="";
                        displayConvert="";
                        displayDisplayResult("0");
                        displayDisplay("");

                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonC)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerColor1 (){
        Button button = (Button) findViewById(buttonID[inerator]);
        button.setOnTouchListener(new View.OnTouchListener() {
            int id = buttonID[inerator];
            String vvod = buttonNumber[inerator];
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(id)).setBackgroundColor(0xFF95969B);
                        displayDisplay(vvod);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(id)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerColor2 (){
        Button button = (Button) findViewById(buttonID[inerator]);
        button.setOnTouchListener(new View.OnTouchListener() {
            int id = buttonID[inerator];
            String vvod = buttonNumber[inerator];
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(id)).setBackgroundColor(0xFFB46D16);
                        displayDisplay(vvod);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(id)).setBackgroundColor(0xFFFA9112);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void displayDisplay (String in) {
        // Выводит на верхний экран
        boolean add = true;

        if(display.length()>0&&in.equals("(")) {
            switch (display.charAt(display.length()-1)) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                    add = true;
                    break;
                default:
                    add = false;
                    break;
            }
        }
            if(display.length()==0&&(in.equals(")")||in.equals("+")||in.equals("-")||in.equals("*")||in.equals("/")))
                add=false;

        if(display.length()>0&&(in.equals(")")||in.equals("+")||in.equals("-")||in.equals("*")||in.equals("/"))) {
            switch (display.charAt(display.length()-1)) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                case ')':
                    add = true;
                    break;
                default:
                    add = false;
                    break;
            }
        }

        if(add) {
            StringBuffer stringBuffer = new StringBuffer(display);
            stringBuffer.append(in);
            display = stringBuffer.toString();
            stringBuffer = null;
            // зануляю класс, так как вроде это помогает сборщику мусора
        }
            TextView vivod = (TextView) findViewById(R.id.textOut);
            vivod.setText(display);
        textSizeChange (display);
    }

    public void displayDisplayResult (String result) {
// Выводит на нижник экран
        result = EraseLastZero.eraseLastZero(result);
        textSizeChangeResult (result);
        if (result.length()==0) {
            TextView vivod = (TextView) findViewById(R.id.textResult);
            vivod.setText("0");
        }else {
            TextView vivod = (TextView) findViewById(R.id.textResult);
            vivod.setText(result);
        }
    }

    public void textSizeChange(String result) {
        // Меняет размер щрифта верхнего экрана
        TextView vivod = (TextView) findViewById(R.id.textOut);
        if (result.length() <= 30) {
            switch (result.length()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    vivod.setTextSize(textSize);
                    break;
                case 12:
                    vivod.setTextSize(textSize - 5);
                    break;
                case 13:
                    vivod.setTextSize(textSize - 10);
                    break;
                case 14:
                    vivod.setTextSize(textSize - 15);
                    break;
                case 15:
                    vivod.setTextSize(textSize - 20);
                    break;
                case 16:
                    vivod.setTextSize(textSize - 23);
                    break;
                case 17:
                    vivod.setTextSize(textSize - 26);
                    break;
                case 18:
                    vivod.setTextSize(textSize - 29);
                    break;
                case 19:
                    vivod.setTextSize(textSize - 32);
                    break;
                case 20:
                    vivod.setTextSize(textSize - 34);
                    break;
                case 21:
                    vivod.setTextSize(textSize - 36);
                    break;
                case 22:
                    vivod.setTextSize(textSize - 38);
                    break;
                case 23:
                    vivod.setTextSize(textSize - 40);
                    break;
                case 24:
                    vivod.setTextSize(textSize - 42);
                    break;
                case 25:
                    vivod.setTextSize(textSize - 44);
                    break;
                case 26:
                    vivod.setTextSize(textSize - 45);
                    break;
                case 27:
                    vivod.setTextSize(textSize - 46);
                    break;
                case 28:
                    vivod.setTextSize(textSize - 47);
                    break;
                case 29:
                    vivod.setTextSize(textSize - 48);
                    break;
                case 30:
                    vivod.setTextSize(textSize - 49);
                    break;
            }
        } else {
            vivod.setTextSize(textSize - 49);
        }
    }

    public void textSizeChangeResult(String result) {
// Меняет размер шрифта нижнего экрана, есть задел на использование большего регистра- класса BigDecimal
        TextView vivod = (TextView) findViewById(R.id.textResult);
        if (result.length() <= 30) {
            switch (result.length()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    vivod.setTextSize(textSize);
                    break;
                case 12:
                    vivod.setTextSize(textSize - 5);
                    break;
                case 13:
                    vivod.setTextSize(textSize - 10);
                    break;
                case 14:
                    vivod.setTextSize(textSize - 15);
                    break;
                case 15:
                    vivod.setTextSize(textSize - 20);
                    break;
                case 16:
                    vivod.setTextSize(textSize - 23);
                    break;
                case 17:
                    vivod.setTextSize(textSize - 26);
                    break;
                case 18:
                    vivod.setTextSize(textSize - 29);
                    break;
                case 19:
                    vivod.setTextSize(textSize - 32);
                    break;
                case 20:
                    vivod.setTextSize(textSize - 34);
                    break;
                case 21:
                    vivod.setTextSize(textSize - 36);
                    break;
                case 22:
                    vivod.setTextSize(textSize - 38);
                    break;
                case 23:
                    vivod.setTextSize(textSize - 40);
                    break;
                case 24:
                    vivod.setTextSize(textSize - 42);
                    break;
                case 25:
                    vivod.setTextSize(textSize - 44);
                    break;
                case 26:
                    vivod.setTextSize(textSize - 45);
                    break;
                case 27:
                    vivod.setTextSize(textSize - 46);
                    break;
                case 28:
                    vivod.setTextSize(textSize - 47);
                    break;
                case 29:
                    vivod.setTextSize(textSize - 48);
                    break;
                case 30:
                    vivod.setTextSize(textSize - 49);
                    break;
            }
        } else {
            vivod.setTextSize(textSize - 49);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}




class Calculator {
// Делает рассчеты
// Использует приоритет для скобок
    static void calculator() {

        int maxPriority = 0;
        for (int i : MainActivity.proirity) {
            if (maxPriority < i)
                maxPriority = i;
        }

        for (int currentPriority = maxPriority; currentPriority >= 0; currentPriority--) {
            for (int i = 0; i < MainActivity.operators.size(); i++) {
                if (MainActivity.operators.get(i) == '*' && MainActivity.proirity.get(i) == currentPriority) {
                    MainActivity.digits.set(i + 1, MainActivity.digits.get(i) * MainActivity.digits.get(i + 1));
                    removeLast(i);
                    i--;
                } else
                if (MainActivity.operators.get(i) == '/' && MainActivity.proirity.get(i) == currentPriority) {
                    if (MainActivity.digits.get(i + 1) != 0)
                        MainActivity.digits.set(i + 1, MainActivity.digits.get(i) / MainActivity.digits.get(i + 1));
                    removeLast(i);
                    i--;
                }
            }
            for (int i = 0; i < MainActivity.operators.size(); i++) {
                if (MainActivity.operators.get(i) == '+' && MainActivity.proirity.get(i) == currentPriority) {
                    MainActivity.digits.set(i + 1, MainActivity.digits.get(i) + MainActivity.digits.get(i + 1));
                    removeLast(i);
                    i--;
                } else
                if (MainActivity.operators.get(i) == '-' && MainActivity.proirity.get(i) == currentPriority) {
                    MainActivity.digits.set(i + 1, MainActivity.digits.get(i) - MainActivity.digits.get(i + 1));
                    removeLast(i);
                    i--;
                }
            }
        }
    }

    private static void removeLast(int i){
        MainActivity.digits.remove(i);
        MainActivity.operators.remove(i);
        MainActivity.proirity.remove(i);
    }
}

class DigitsReader {
// Записывает строки в массивы
    static void digitsReader (String digits) {

        int priority = 0;
        String tempDigits = "";
        char content;
        for (int i = 0; i < MainActivity.display.length()&&MainActivity.display.length()>0; i++) {
            content = MainActivity.display.charAt(i);
            switch (content) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.':
                    if (tempDigits.length() < 19) {
                        StringBuffer stringBuffer = new StringBuffer(tempDigits);
                        stringBuffer.append(content);
                        tempDigits = stringBuffer.toString();
                        stringBuffer = null;
                        // зануляю класс, так как вроде это помогает сборщику мусора
                    }
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if (tempDigits != "" && MainActivity.operators.size() <= MainActivity.digits.size()) {
                        MainActivity.digits.add(Double.parseDouble(tempDigits));
                        tempDigits = "";
                        MainActivity.operators.add(content);
                        MainActivity.proirity.add(priority);
                    }
                    break;
                case '(':
                    if (MainActivity.operators.size() == MainActivity.digits.size() && tempDigits == "") {
                        priority++;
                    }
                    break;
                case ')':
                    if (priority > 0 && tempDigits != "") {
                        priority--;
                    }
                    break;
            }

        }
        if(tempDigits!="")
            MainActivity.digits.add(Double.parseDouble(tempDigits));
        else if(MainActivity.operators.size()>1&&MainActivity.operators.size()>=MainActivity.digits.size()) {
            MainActivity.operators.remove(MainActivity.operators.size() - 1);
            MainActivity.proirity.remove(MainActivity.proirity.size()-1);
        }
        if(MainActivity.digits.size()==MainActivity.operators.size())
            MainActivity.operators.remove(MainActivity.operators.size()-1);
        MainActivity.proirity.add(0);
    }
}

class ConvertTo {
// Переводит массивы в строку- выражение
    static void convertTo() {
        int temp1 = 0;
        for (int i = 0; i < MainActivity.digits.size(); i++) {
            if (MainActivity.proirity.size() > i) {
                while (MainActivity.proirity.get(i) > temp1) {
                    StringBuffer stringBuffer1 = new StringBuffer(MainActivity.displayConvert);
                    stringBuffer1.append("(");
                    MainActivity.displayConvert = stringBuffer1.toString();
                    stringBuffer1 = null;
                    // зануляю класс, так как вроде это помогает сборщику мусора
                    temp1++;
                }
            }

            StringBuffer stringBuffer2 = new StringBuffer(MainActivity.displayConvert);
            stringBuffer2.append(EraseLastZero.eraseLastZero(Double.toString(MainActivity.digits.get(i))));
            MainActivity.displayConvert = stringBuffer2.toString();
            stringBuffer2 = null;
            // зануляю класс, так как вроде это помогает сборщику мусора

            if (MainActivity.proirity.size() > i) {
                while (MainActivity.proirity.get(i) < temp1) {
                    StringBuffer stringBuffer3 = new StringBuffer(MainActivity.displayConvert);
                    stringBuffer3.append(")");
                    MainActivity.displayConvert = stringBuffer3.toString();
                    stringBuffer3 = null;
                    // зануляю класс, так как вроде это помогает сборщику мусора
                    temp1--;
                }
            }
            if (MainActivity.operators.size() > i) {

                StringBuffer stringBuffer4 = new StringBuffer(MainActivity.displayConvert);
                stringBuffer4.append(MainActivity.operators.get(i));
                MainActivity.displayConvert = stringBuffer4.toString();
                stringBuffer4 = null;
                // зануляю класс, так как вроде это помогает сборщику мусора
            }
        }
        MainActivity.display = MainActivity.displayConvert;
        MainActivity.displayConvert = "";
    }
}

class EraseLastZero{
// Удаляет нули и точку в конце числа
    static String eraseLastZero (String lastZero){
        if(lastZero.length()>1)
            while ((lastZero.length()>1&&lastZero.charAt(lastZero.length()-1)=='0'&&lastZero.contains("."))||lastZero.charAt(lastZero.length()-1)=='.')
            {
                StringBuffer stringBuffer = new StringBuffer(lastZero);
                stringBuffer.setLength(stringBuffer.length() - 1);
                lastZero = stringBuffer.toString();
                stringBuffer = null;
                // зануляю класс, так как вроде это помогает сборщику мусора
            }
            return lastZero;
    }
}