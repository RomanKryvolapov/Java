package com.company;

public class Main extends Second{

    public static void main(String[] args) {

        System.out.println();

        new Second().method();
        new Main().method(true);
        new Main().method();
        Main main = new Main(){
            void method(boolean a){
                System.out.println("Перезагрузка внутри анонимного класса");
            }
            @Override
            void method() {
                System.out.println("Перезапись внутри анонимного класса");
            }
        };
        main.method(true);
        main.method();

    }

    void method(boolean a) {
        System.out.println("Перезагрузка метода");
    }

    @Override
    void method() {
        System.out.println("Перезапись метода");
    }

}

class Second{
    void method(){
        System.out.println("Оригинальный метод");
    }
}




