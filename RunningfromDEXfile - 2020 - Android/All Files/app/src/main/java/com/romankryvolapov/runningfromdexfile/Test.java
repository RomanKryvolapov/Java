package com.romankryvolapov.runningfromdexfile;
public class Test {
    String testString1 = "10";
    String testString2 = "11";
    String testString3 = "12";
    Integer testInt = 7;

    public void test() {
        MainActivity.myTextView.setText("123");
        MainActivity.myLayout.removeAllViews();
        MainActivity.myLayout.addView(MainActivity.myTextView);
    }

    public void test2() {
        MainActivity.myTextView.setText("456");
        MainActivity.myLayout.removeAllViews();
        MainActivity.myLayout.addView(MainActivity.myTextView);
    }
}
