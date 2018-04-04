package com.company;
// made by Roman Kryvolapov
//
// Переменная string класса Main передана вместе с классом Main методу method класса Second
// Это аналогично new Main().string() внутри метода method класса Second
public class Main{

    String string = "Переменная string класса Main";

    public static void main(String[] args) {

        System.out.println();

        new Second().method(new Main(), " передана вместе с классом Main методу method класса Second");

        System.out.println();

        System.out.println("Это аналогично new Main().string() внутри метода method класса Second");

    }
}

class Second{

    void method(Main main, String string){

        System.out.println(main.string+string);

    }
}