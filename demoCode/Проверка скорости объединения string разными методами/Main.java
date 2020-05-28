package com.company;
// made by Roman Kryvolapov
/*
Вывод

Метод > a.concat

Проверка >> Длинна а = 1000

Приложение занимает в памяти JVM: 3 мегабайт
Минимальная доступная память JVM до выполнения приложения: 128 мегабайт
Доступная память JVM не увеличилась после выполнения приложения (приложению хватило минимальной доступной памяти 128 мегабайт)
Время выполнения приложения: 0 секунд
Время выполнения приложения: 44 миллисекунд
Время выполнения приложения: 44855 микросекунд

Метод > a = a + b

Проверка >> Длинна а = 1000

Приложение занимает в памяти JVM: 5 мегабайт
Минимальная доступная память JVM до выполнения приложения: 128 мегабайт
Доступная память JVM не увеличилась после выполнения приложения (приложению хватило минимальной доступной памяти 128 мегабайт)
Время выполнения приложения: 0 секунд
Время выполнения приложения: 67 миллисекунд
Время выполнения приложения: 67954 микросекунд

Метод > StringBuilder

Проверка >> Длинна а = 1000

Приложение занимает в памяти JVM: 6 мегабайт
Минимальная доступная память JVM до выполнения приложения: 128 мегабайт
Доступная память JVM не увеличилась после выполнения приложения (приложению хватило минимальной доступной памяти 128 мегабайт)
Время выполнения приложения: 0 секунд
Время выполнения приложения: 3 миллисекунд
Время выполнения приложения: 3121 микросекунд

Метод > StringBuffer

Проверка >> Длинна а = 1000

Приложение занимает в памяти JVM: 7 мегабайт
Минимальная доступная память JVM до выполнения приложения: 128 мегабайт
Доступная память JVM не увеличилась после выполнения приложения (приложению хватило минимальной доступной памяти 128 мегабайт)
Время выполнения приложения: 0 секунд
Время выполнения приложения: 5 миллисекунд
Время выполнения приложения: 5067 микросекунд

Пустое приложение занимает в памяти JVM 2-3 мегабайт
Увеличить минимальную память JVM можно с помощью опции -Xms...m (в мегабайтах) или -Xms...g (в гигабайтах) в поле компилятора VM options или файле idea.vmoptions.
По умолчанию Xms для Java 10 & IDEA 2018 = 128 мегабайт
Увеличить максимально доступную память JVM можно с помощью опции -Xmx...m (в мегабайтах) или -Xms...g (в гигабайтах) в поле компилятора VM options или файле idea.vmoptions.
По умолчанию Xmx для Java 10 & IDEA 2018 = 750 мегабайт
Время выполнения пустого приложения на Core i5 & Java 10 & IDEA 2018 = 100-500 микросекунд

 */
public class Main {


    public static void main(String[] args) {
        timeANDmemory.start();

        System.out.println();
        System.out.println("Метод > a.concat");
        String.format("%,12d", test("a.concat"));

        timeANDmemory.stop();
        timeANDmemory.start();

        System.out.println();
        System.out.println("Метод > a = a + b");
        String.format("%,12d", test("a = a + b"));

        timeANDmemory.stop();
        timeANDmemory.start();

        System.out.println();
        System.out.println("Метод > StringBuilder");
        String.format("%,12d", test("StringBuilder"));

        timeANDmemory.stop();
        timeANDmemory.start();

        System.out.println();
        System.out.println("Метод > StringBuffer");
        String.format("%,12d", test("StringBuffer"));



        timeANDmemory.stop();
        timeANDmemory.comments();



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

