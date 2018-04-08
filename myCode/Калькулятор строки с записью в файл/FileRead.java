package com.company;
// made by Roman Kryvolapov
// Считывает набор символов из файла и заносил в массив как математическое выражение
// Main.proirity это приоритет вырвжения в скобках
import java.io.FileInputStream;

public class FileRead {

    static void fileRead() throws Exception {

        int priority = 0;
        String tempDigits = "";
        FileInputStream printIn = new FileInputStream("printOut.txt");
        char content = (char) printIn.read();

        boolean nextStep = true;

        while (nextStep) {

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
                    if(tempDigits.length()<19)
                    tempDigits += content;
                    break;
                case '\uFFFF':
                    if(tempDigits!="")
                    Main.digits.add(Long.parseLong(tempDigits));
                    else if(Main.operators.size()>1&&Main.operators.size()>=Main.digits.size()) {
                        Main.operators.remove(Main.operators.size() - 1);
                        Main.proirity.remove(Main.proirity.size()-1);
                    }
                    Main.proirity.add(0);
                    priority=0;
                    nextStep = false;
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if (tempDigits !=""&&Main.operators.size() <= Main.digits.size()) {
                        Main.digits.add(Long.parseLong(tempDigits));
                        tempDigits = "";
                        Main.operators.add(content);
                        Main.proirity.add(priority);
                    }
                    break;
                case '(':
                    if (Main.operators.size()==Main.digits.size()&&tempDigits=="") {
                        priority++;
                    }
                    break;
                case ')':
                    if (priority>0&&tempDigits!="") {
                        priority--;
                    }
                    break;
            }
            content = (char) printIn.read();

        }
        printIn.close();
        printIn = null;
    }
}