package com.company;
// made by Roman Kryvolapov
// Получене доступа к приватной строке и методу
//
// Вывод
//
// Доступ ко всем полям класса
// Some private text
//
// Доступ к полю класса по имени
// Some private text
//
// Доступ ко всем методам класса
// Some private method
//
// Доступ к методу класса по имени
// Some private method
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main{

    public static void main(String[] args) throws Exception{

        System.out.println();

        Second second = new Second();

        System.out.println("Доступ ко всем полям класса");
        Field[] field1 = second.getClass().getDeclaredFields();
        for(Field field:field1) {
            field.setAccessible(true);
            System.out.println(field.get(second));
        }

        System.out.println();

        System.out.println("Доступ к полю класса по имени");
        Field field2 = second.getClass().getDeclaredField("a");
        field2.setAccessible(true);
        System.out.println(field2.get(second));

        System.out.println();

        System.out.println("Доступ ко всем методам класса");
        Method[] method1 = second.getClass().getDeclaredMethods();
        for(Method method:method1){
            method.setAccessible(true);
            method.invoke(second);
        }

        System.out.println();

        System.out.println("Доступ к методу класса по имени");
        Method method2 = second.getClass().getDeclaredMethod("privateMethod");
        method2.setAccessible(true);
        method2.invoke(second);

    }
}

class Second{

    private String a = "Some private text";

    private void privateMethod(){

        System.out.println("Some private method");

    }

}
