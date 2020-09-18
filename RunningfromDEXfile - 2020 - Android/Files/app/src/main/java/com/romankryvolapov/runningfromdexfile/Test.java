package com.romankryvolapov.runningfromdexfile;

import android.view.Gravity;
import android.widget.Toast;

public class Test {
    public void test() {
        MainActivity mainActivity = new MainActivity();
        Toast toast = Toast.makeText(mainActivity, "Ok!!!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
