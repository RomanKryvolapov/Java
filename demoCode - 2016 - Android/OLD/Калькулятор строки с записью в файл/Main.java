package com.company;
// made by Roman Kryvolapov
// Генерирует спонтанный набор символов, собирает из него математическое выражение,
// рассчитывает с учетом любого числа уровней скобок, записывает результат в файл printOut.txt
// Для проверки внузи этого класса есть другой калькулятор из интернет (но он рассчитывает только с одним уровнем скобок)
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Main {

    static ArrayList<Long> digits = new ArrayList<Long>();
    // числа
    static ArrayList<Character> operators = new ArrayList<Character>();
    // операторы
    static ArrayList<Integer> proirity = new ArrayList<Integer>();
    // скобки - уровень
    static final int stringSizeIterator = 30;
    // Размер генерируемой строки
    static String check = "";

    public static void main(String[] args) throws Exception {
        timeANDmemory.start();
        // Проверка времени рассчета

        new FileOutputStream("printOut.txt");
        // В папке с программой создастся этот файл
        GenerateString.generateString(stringSizeIterator);
        // Генерирует набор символов включая скобки и записывает в файл
        FileRead.fileRead();
        // Считывает набор символов из файла и заносил в массив как математическое выражение
        FileWrite.fileWrite("\nCorrected string\n");

        System.out.println("Corrected string");

        WriteArrayToFile.writeArrayToFile();
        // Записывает математическое выражение из массива в файл
        Calculator.calculator();
        // Рассчитывает математическое выражение из массива

        System.out.println("\nResult >> "+Main.digits.get(0));
        FileWrite.fileWrite("\nResult >> "+Long.toString(Main.digits.get(0))+"\n");
        // Записывает результат рассчета в файл

//      You can check result with other calculator from Internet
//        System.out.println("Result with other calculator >> "+new Expressions().start(check));

        timeANDmemory.stop();
    }

}