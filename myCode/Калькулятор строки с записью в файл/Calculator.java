package com.company;
// made by Roman Kryvolapov
// Main.proirity это приоритет вырвжения в скобках
public class Calculator {

    static void calculator() {

        int maxPriority = 0;

        for (int i : Main.proirity) {
            if (maxPriority < i)
                maxPriority = i;
        }

        for (int currentPriority = maxPriority; currentPriority >= 0; currentPriority--) {

            for (int i = 0; i < Main.operators.size(); i++) {
                if (Main.operators.get(i) == '*' && Main.proirity.get(i) == currentPriority) {
                    Main.digits.set(i + 1, Main.digits.get(i) * Main.digits.get(i + 1));
                    removeLast(i);
                    i--;
                } else
                if (Main.operators.get(i) == '/' && Main.proirity.get(i) == currentPriority) {
                    if (Main.digits.get(i + 1) != 0)
                        Main.digits.set(i + 1, Main.digits.get(i) / Main.digits.get(i + 1));
                    else
                        System.out.print("\nDivision by zero error >> remove block with it");
                    removeLast(i);
                     i--;
                }
            }
            for (int i = 0; i < Main.operators.size(); i++) {
                if (Main.operators.get(i) == '+' && Main.proirity.get(i) == currentPriority) {
                    Main.digits.set(i + 1, Main.digits.get(i) + Main.digits.get(i + 1));
                    removeLast(i);
                      i--;
                } else
                if (Main.operators.get(i) == '-' && Main.proirity.get(i) == currentPriority) {
                    Main.digits.set(i + 1, Main.digits.get(i) - Main.digits.get(i + 1));
                    removeLast(i);
                      i--;
                }
            }
        }
    }

    static void removeLast(int i){
        Main.digits.remove(i);
        Main.operators.remove(i);
        Main.proirity.remove(i);
    }
}