package com.company;
// made by Roman Kryvolapov
// Start with timeANDmemory.start(); in the start of program
// Stop with timeANDmemory.stop(); in the end of program
/*

Вывод

// Program

Приложение занимает в памяти JVM: 3 мегабайт
Минимальная доступная память JVM до выполнения приложения: 128 мегабайт
Доступная память JVM не увеличилась после выполнения приложения (приложению хватило минимальной доступной памяти 128 мегабайт)
Время выполнения приложения: 0 секунд
Время выполнения приложения: 0 миллисекунд
Время выполнения приложения: 183 микросекунд

Пустое приложение занимает в памяти JVM 2-3 мегабайт
Увеличить минимальную память JVM можно с помощью опции -Xms...m (в мегабайтах) или -Xms...g (в гигабайтах) в поле компилятора VM options или файле idea.vmoptions.
По умолчанию Xms для Java 10 & IDEA 2018 = 128 мегабайт
Увеличить максимально доступную память JVM можно с помощью опции -Xmx...m (в мегабайтах) или -Xms...g (в гигабайтах) в поле компилятора VM options или файле idea.vmoptions.
По умолчанию Xmx для Java 10 & IDEA 2018 = 750 мегабайт
Время выполнения пустого приложения на Core i5 & Java 10 & IDEA 2018 = 100-500 микросекунд

*/

class timeANDmemory{

    private static long jvmMemory=0;
    private static long startTime=0;
    private static long stopTime=0;
    private static long freeMemory=0;
    private static long jvmNewMemory=0;

    static void start(){
        jvmMemory = Runtime.getRuntime().totalMemory();
        startTime = System.nanoTime();
    }

    static void stop(){
        System.out.println();
        stopTime = System.nanoTime();
        freeMemory = Runtime.getRuntime().freeMemory();
        jvmNewMemory = Runtime.getRuntime().totalMemory();
        System.out.println("\033[30;1m"+"Приложение занимает в памяти JVM: "+"\033[31;1m"+(jvmNewMemory-freeMemory)/1024/1024+" мегабайт"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Минимальная доступная память JVM до выполнения приложения: "+"\033[31;1m"+jvmMemory/1024/1024+" мегабайт"+"\u001B[0m");
        if((jvmNewMemory-jvmMemory)>jvmMemory)
            System.out.println("\033[30;1m"+"Доступная память JVM после выполнения приложения: "+"\033[31;1m"+jvmNewMemory/1024/1024
                    +" мегабайт"+"\033[30;1m"+" (приложение увеличило доступную память на "+(jvmNewMemory-jvmMemory)/1024/1024+" мегабайт)"+"\u001B[0m");
        else System.out.println("\033[30;1m"+"Доступная память JVM не увеличилась после выполнения приложения (приложению хватило минимальной доступной памяти "
                +jvmMemory/1024/1024+" мегабайт)"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Время выполнения приложения: "+"\033[31;1m"+(stopTime-startTime)/1000000000+" секунд"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Время выполнения приложения: "+"\033[31;1m"+(stopTime-startTime)/1000000+" миллисекунд"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Время выполнения приложения: "+"\033[31;1m"+(stopTime-startTime)/1000+" микросекунд"+"\u001B[0m");
        jvmMemory=0;
        startTime=0;
        stopTime=0;
        freeMemory=0;
        jvmNewMemory=0;
        System.gc();
    }

    static void comments(){
        System.out.println();
        System.out.println("\u001B[32m"+"Пустое приложение занимает в памяти JVM 2-3 мегабайт"+"\u001B[0m");
        System.out.println("\u001B[32m"+"Увеличить минимальную память JVM можно с помощью опции -Xms...m (в мегабайтах) или -Xms...g (в гигабайтах) " +
                        "в поле компилятора VM options или файле idea.vmoptions.\nПо умолчанию Xms для Java 10 & IDEA 2018 = 128 мегабайт"+"\u001B[0m");
        System.out.println("\u001B[32m"+"Увеличить максимально доступную память JVM можно с помощью опции -Xmx...m (в мегабайтах) или -Xms...g (в гигабайтах) " +
                        "в поле компилятора VM options или файле idea.vmoptions.\nПо умолчанию Xmx для Java 10 & IDEA 2018 = 750 мегабайт"+"\u001B[0m");
        System.out.println("\u001B[32m"+"Время выполнения пустого приложения на Core i5 & Java 10 & IDEA 2018 = 100-500 микросекунд"+"\u001B[0m");

    }
}