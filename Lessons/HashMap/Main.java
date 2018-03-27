package com.company;

//Вывод
//com.company.ObjectsToMap@5fe5c6f
//        1
//        2
//        3
//        123

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ObjectsToMap objectsToMap = new ObjectsToMap();
        // Новый экземпляр класса ObjectsToMap

        Map<String, ObjectsToMap> map1 = new HashMap<>();
        // Мы создали HashMap с именем map1 и индексом типа String

        map1.put("index1", objectsToMap);
        // Мы положили в Map под индексом index1 класс objectsToMap со всеми полями и методами

        ObjectsToMap newMap1 = map1.get("index1");
        // Мы создали объект newMap1 с содержимым соответствующим индексу index1

        System.out.println(newMap1);
        System.out.println(newMap1.text1);
        System.out.println(newMap1.text2);
        System.out.println(newMap1.text3);
        System.out.println(newMap1.method());


    }

}

class ObjectsToMap{
    String text1 = "1";
    String text2 = "2";
    String text3 = "3";
    int method(){
        return 123;
    }
}