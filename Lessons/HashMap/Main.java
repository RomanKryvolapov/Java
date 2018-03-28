package com.company;
//made by Roman Kryvolapov
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // Досутп к методам класса через HashMap
        ObjectsToMap objectsToMap = new ObjectsToMap();
        Map<String, ObjectsToMap> map1 = new HashMap<>();
        map1.put("index1", objectsToMap);
        ObjectsToMap newMap1 = map1.get("index1");

        System.out.println(newMap1);
        System.out.println(newMap1.text);
        System.out.println(newMap1.method());

        System.out.println();

        // Разные типы переменных в качестве индекса HashMap
        // Для объектов в качестве индекса нужно переопределять HashMap и Equals
        Map map2 = new HashMap();
        map2.put(1, "int 1 as index");
        map2.put("1", "string 1 as index");
        map2.put('1', "char 1 as index");

        System.out.println(map2.get(1));
        System.out.println(map2.get("1"));
        System.out.println(map2.get('1'));
    }
}

class ObjectsToMap{
    String text = "Class ObjectsToMap String text";
    String method(){
        return "Class ObjectsToMap Method method return this text";
    }
}
