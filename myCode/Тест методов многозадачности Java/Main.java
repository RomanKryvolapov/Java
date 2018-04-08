package com.company;
/*
 made by Roman Kryvolapov
 Тест многопоточности Java
 | и - это 2 разных потока

 Без синхронизации метода mainIterator условие inerator % 180 == 0 срабатывает не каждые 180 символов,
 из за чего переход на новую строку System.out.println() вызывается раньше или позже


|||||||||||||||||||||||||||||||||------------------------------||-|||||||||---------------------||||
|||||||||||||||||||||||||||||||||||||||||||||||--------------------------|||||||||||||||||||||||||||-
----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------
|||||||||||||||||||||||||||||||||||
|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


 С синхронизацией метода метода mainIterator условие inerator % 180 == 0
 срабатывает каждые 180 символов, вызывая переход на новую строку каждые 180 символов


---------------------------------------------------------------------------------------------------|
-----||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||---------------------
----------------------------------------------------------------------------------------------------
---------------||||||||||||||||||||||||||||||||||---------------------------------------------------
--------------------------------------------------------------------------------||||||||||||||||||||


При взаимной передачи приоритета другому потоку методом Thread.yield(); потоки чередуются


-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-||||||-|-|-
|-|-|-|-|-|-|-|-|-|-|-||||-|-|-|-|-|-|-|-|-|-||-||||||||||||||||||-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|
-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-||||||||||-|-||||||||


*/

public class Main{

    static volatile int inerator = 0;
    static final int cycles = 180000;

    public static void main(String[] args) throws Exception{
        Digit1 digit1 = new Digit1();
        Digit2 digit2 = new Digit2();
        digit1.start();
        digit2.start();
        digit1.join();
        digit2.join();
        System.out.println();
        System.out.println();
    }

    static synchronized void mainIterator (String b){
     // Если убрать синхронизацию метода, условие inerator % 100 == 0 будет срабатывать не точно
        inerator++;
        System.out.print(b);
        if (inerator % 100 == 0) {
            System.out.println();
        }
    }
}

class Digit1 extends Thread{
    @Override
    public void run(){
        Digits.writeDigit("-");
    }
}

class Digit2 extends Thread{
    @Override
    public void run() {
        Digits.writeDigit("|");
    }
}

class Digits{
    static void writeDigit(String a){
        while (Main.inerator<Main.cycles) {
                Main.mainIterator(a);
         // Этот мето отдает приоритет другому потоку
                //Thread.yield();
        }
    }
}

