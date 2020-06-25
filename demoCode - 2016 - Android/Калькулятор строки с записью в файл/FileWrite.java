package com.company;
// made by Roman Kryvolapov
// Записывает строку str в файл
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileWrite {

    static void fileWrite(String str) throws Exception{

        FileOutputStream printFile =new FileOutputStream("printOut.txt",true);
        PrintStream printOut = new PrintStream(printFile);
        printOut.print(str);
        printFile.close();
        printOut = null;
        printFile = null;
    }
}
