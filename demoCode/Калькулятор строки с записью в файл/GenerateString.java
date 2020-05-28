package com.company;
// made by Roman Kryvolapov
// Генерирует набор символов включая скобки и записывает в файл, передавая строку классу FileWrite
import java.util.Random;

public class GenerateString {

static void generateString(int stringSizeIterator) throws Exception {

    String a = "";

    Random random = new Random();

    a += Integer.toString(random.nextInt(10));
    for (int i = 0; i < Main.stringSizeIterator; i++) {
        switch (random.nextInt(10)) {
            case 1:
                a += "+";
                break;
            case 2:
                a += "-";
                break;
            case 3:
                a += "*";
                break;
            case 4:
                a += "/";
                break;
        }
        switch (random.nextInt(3)) {
            case 1:
                a += "(";
                break;
        }
        a += Integer.toString(random.nextInt(10));
        switch (random.nextInt(3)) {
            case 1:
                a += ")";
                break;
        }
    }

    FileWrite.fileWrite("Generated string\n"+a);
    System.out.println("Generated string\n"+a);
    random = null;
}
}
