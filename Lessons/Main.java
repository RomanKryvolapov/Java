package com.company;

// Проверка скорости исполнения различных методов обработки строк- как комментарий к уроку
//
// Вывод:
//
//Проверка >> Длинна а = 1000
//Метод a.concat >> время выполнения    1 398 751 наносекунд
//
//Проверка >> Длинна а = 1000
//Метод a = a + b >> время выполнения   48 299 503 наносекунд
//
//Проверка >> Длинна а = 1000
//Метод StringBuilder >> время выполнения    2 069 626 наносекунд
//
//Проверка >> Длинна а = 1000
//Метод StringBuffer >> время выполнения    3 559 796 наносекунд

public class Main {

    public static void main(String[] args) {
        System.out.println("Метод a.concat >> время выполнения " + String.format("%,12d", test("a.concat")) + " наносекунд");
        System.out.println("Метод a = a + b >> время выполнения " + String.format("%,12d", test("a = a + b")) + " наносекунд");
        System.out.println("Метод StringBuilder >> время выполнения " + String.format("%,12d", test("StringBuilder")) + " наносекунд");
        System.out.println("Метод StringBuffer >> время выполнения " + String.format("%,12d", test("StringBuffer")) + " наносекунд");
    }

    public static long test(String method) {
        long start, stop;
        start = System.nanoTime();
        String a = "";
        String b = "1";

            switch (method) {
                case "a.concat":
                    for (int i = 0; i < 1000; i++) {
                    a = a.concat(b);
                    }
                    break;
                case "a = a + b":
                    for (int i = 0; i < 1000; i++) {
                    a = a + b;
                    }
                    break;
                case "StringBuilder":
                    for (int i = 0; i < 1000; i++) {
                    StringBuilder a1 = new StringBuilder(a);
                    a1.append(b);
                    a = a1.toString();
                    }
                    break;
                case "StringBuffer":
                    for (int i = 0; i < 1000; i++) {
                    StringBuffer a2 = new StringBuffer(a);
                    a2.append(b);
                    a = a2.toString();
                    }
                    break;

        }
        stop = System.nanoTime();
        System.out.println("\nПроверка >> Длинна а = " + a.length());
        return stop - start;
    }
}
