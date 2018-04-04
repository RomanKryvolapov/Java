package com.company;
// made by Roman Kryvolapov
// Код показывает время выполнения приложения и занятую память
import java.util.Formatter;

public class Main {

    public static void main(String[] args) {
        long jvmMemory = Runtime.getRuntime().totalMemory();
        Formatter formatter = new Formatter();
        long startTime = System.nanoTime();
        System.out.println();



        //System.out.println("Приложение");



        System.out.println();
        long stopTime = System.nanoTime();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long jvmNewMemory = Runtime.getRuntime().totalMemory();
        System.out.println("\033[30;1m"+"Приложение занимает в памяти JVM: "+"\033[31;1m"+(jvmNewMemory-freeMemory)/1024/1024+" мегабайт"+"\u001B[0m");
        System.out.println("\u001B[32m"+"Пустое приложение занимает в памяти JVM 2-3 мегабайт"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Минимальная доступная память JVM до выполнения приложения: "+"\033[31;1m"+jvmMemory/1024/1024+" мегабайт"+"\u001B[0m");
        System.out.println("\u001B[32m"+"Увеличить минимальную память JVM можно с помощью опции -Xms...m (в мегабайтах) или -Xms...g (в гигабайтах) " +
                "в поле компилятора VM options или файле idea.vmoptions.\nПо умолчанию для Java 10 & IDEA 2018 = 128 мегабайт"+"\u001B[0m");
        if((jvmNewMemory-jvmMemory)>jvmMemory)
        System.out.println("\033[30;1m"+"Доступная память JVM после выполнения приложения: "+"\033[31;1m"+jvmNewMemory/1024/1024
                +" мегабайт"+"\033[30;1m"+" (приложение увеличило доступную память на "+(jvmNewMemory-jvmMemory)/1024/1024+" мегабайт)"+"\u001B[0m");
        else System.out.println("\033[30;1m"+"Доступная память JVM не увеличилась после выполнения приложения (приложению хватило минимальной доступной памяти "
                +jvmMemory/1024/1024+" мегабайт)"+"\u001B[0m");
        System.out.println("\u001B[32m"+"Увеличить максимально доступную память JVM можно с помощью опции -Xmx...m (в мегабайтах) или -Xms...g (в гигабайтах) " +
                "в поле компилятора VM options или файле idea.vmoptions.\nПо умолчанию для Java 10 & IDEA 2018 = 750 мегабайт"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Время выполнения приложения: "+"\033[31;1m"+(stopTime-startTime)/1000000000+" секунд"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Время выполнения приложения: "+"\033[31;1m"+(stopTime-startTime)/1000000+" миллисекунд"+"\u001B[0m");
        System.out.println("\033[30;1m"+"Время выполнения приложения: "+"\033[31;1m"+(stopTime-startTime)/1000+" микросекунд"+"\u001B[0m");
        System.out.println("\u001B[32m"+"Время выполнения пустого приложения на Core i5 & Java 10 & IDEA 2018 = 100-500 микросекунд"+"\u001B[0m");
    }
}
