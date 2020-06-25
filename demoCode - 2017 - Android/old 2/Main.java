package com.company;
// made by Roman Kryvolapov
//
// Не смог удержаться, чтобы не сделать каку))
public class Main{

    static boolean[][] a = new boolean[60][16];

    public static void main(String[] args) throws Exception {

        for (int i = 3; i < 12; i++) {
            a[i][2]=true;
        }
        for (int i = 3; i < 12; i++) {
            a[3][i]=true;
        }
        for (int i = 3; i < 12; i++) {
            a[i][6]=true;
        }
        for (int i = 2; i < 11; i++) {
            a[16][i]=true;
        }
        for (int i = 2; i < 12; i++) {
            a[26][i]=true;
        }
        for (int i = 17; i < 24; i++) {
            a[i][11]=true;
        }
        a[25][10]=true;
        a[24][10]=true;
        for (int i = 3; i < 11; i++) {
            a[32][i]=true;
        }
        for (int i = 33; i < 42; i++) {
            a[i][2]=true;
        }
        for (int i = 33; i < 42; i++) {
            a[i][11]=true;
        }

        for (int i = 2; i < 12; i++) {
            a[46][i]=true;
        }
        int x = 47;
        int y = 6;
        while(x!=52){
            a[x][y]=true;
            x++;
            y--;
        }
        x = 47;
        y = 6;
        while(x!=53){
            a[x][y]=true;
            x++;
            y++;

        }

        for (int i = 0; i < 16; i++) {
        for (int k = 0; k < 60; k++) {

            if(a[k][i]) {
                System.out.print("#");
            }
            else {
                System.out.print(" ");
            }
        }
            Thread.sleep(200);
            System.out.println();
        }
    }
}