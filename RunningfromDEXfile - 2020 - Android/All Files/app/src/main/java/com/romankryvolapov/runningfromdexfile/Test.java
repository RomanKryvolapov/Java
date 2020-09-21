package com.romankryvolapov.runningfromdexfile;
public class Test {
    String testField1 = "Test test test test test test test test test test test";
    String testField2 = "11";
    String testField3 = "12";
    Integer testField4 = 7;

    public void testMethod1() {
        MainActivity.myTextView.setText("Test test test test test test test test test test test test" +
                " test test test test test test test test test test test test test test test test test");
        MainActivity.myLayout.removeAllViews();
        MainActivity.myLayout.addView(MainActivity.myTextView);
    }

    public void testMethod2() {
        MainActivity.myTextView.setText("456");
        MainActivity.myLayout.removeAllViews();
        MainActivity.myLayout.addView(MainActivity.myTextView);
    }
}
