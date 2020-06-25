package com.company;
// made by Roman Kryvolapov
// Записывает массив в файл, передавая символы классу FileWrite и выводит на консоль
public class WriteArrayToFile {

    static void writeArrayToFile() throws Exception{
        int temp1 = 0;
        for (int i = 0; i < Main.digits.size(); i++) {
            if(Main.proirity.size() > i) {
                while (Main.proirity.get(i) > temp1){
                    FileWrite.fileWrite("(");
                    System.out.print("(");
                    Main.check+=("(");
                    temp1++;
                }
            }
            FileWrite.fileWrite(Long.toString(Main.digits.get(i)));
            System.out.print(Long.toString(Main.digits.get(i)));
            Main.check+=(Long.toString(Main.digits.get(i)));
            if(Main.proirity.size() > i) {
                while (Main.proirity.get(i) < temp1) {
                    FileWrite.fileWrite(")");
                    System.out.print(")");
                    Main.check+=(")");
                    temp1--;
                }
            }
            if (Main.operators.size() > i) {
                FileWrite.fileWrite(""+Main.operators.get(i));
                System.out.print(Main.operators.get(i));
                Main.check+=(Main.operators.get(i));
            }

        }
    }
}
