package com.company;
import java.util.Random;

public class Second{

    protected void summ() {

        System.out.println("Слагает числа с плавающей запятой любой длинны без потери точности");

    int[] arrayInteger;
    int arrayIndex = 0;
    int tochkaIndex = 0;
    int[] arrayInteger1;
    int arrayIndex1 = 0;
    int tochkaIndex1 = 0;
    int[] arrayInteger2;
    int tochkaIndex2 = 0;

        System.out.println();
        Random ranArray = new Random();
        while(arrayIndex<2) {
            arrayIndex = ranArray.nextInt(299);
        }
        System.out.print("Число 1 количество цифр " + arrayIndex);
        while(tochkaIndex<1) {
            tochkaIndex = ranArray.nextInt(arrayIndex);
        }
        System.out.print(" точка после цифры " + tochkaIndex);
        System.out.println();
        while(arrayIndex1<2) {
            arrayIndex1 = ranArray.nextInt(299);
        }
        System.out.print("Число 2 количество цифр " + arrayIndex1);
        while(tochkaIndex1<1) {
            tochkaIndex1 = ranArray.nextInt(arrayIndex1);
        }
        System.out.print(" точка после цифры " + tochkaIndex1);
        System.out.println();

        arrayInteger = new int[arrayIndex + arrayIndex1 + 10];
        arrayInteger1 = new int[arrayIndex + arrayIndex1 + 10];
        arrayInteger2 = new int[arrayIndex + arrayIndex1 + 10];

        if(tochkaIndex>tochkaIndex1)
        {
            tochkaIndex2=tochkaIndex;
        }else{
            tochkaIndex2=tochkaIndex1;
        }
        int biggestIndex;
        if(arrayIndex>arrayIndex1)
        {
            biggestIndex=arrayIndex;
        }else{
            biggestIndex=arrayIndex1;
        }

       for(int i=0; i<biggestIndex;i++){
           arrayInteger[i]=ranArray.nextInt(10);
           arrayInteger1[i]=ranArray.nextInt(10);
       }
        System.out.println();
     System.out.print("Число 1      ");
        for (int i = 1; i < arrayIndex+1; i++) {
            System.out.print(arrayInteger[i-1]);
            if(i==tochkaIndex){
                System.out.print(",");
            }
        }
        System.out.println();
       System.out.print("Число 2      ");
        for (int i = 1; i < arrayIndex1+1; i++) {
            System.out.print(arrayInteger1[i-1]);
            if(i==tochkaIndex1){
               System.out.print(",");
            }
        }
        System.out.println();
            while ((arrayIndex - tochkaIndex) > (arrayIndex1 - tochkaIndex1)) {
                arrayInteger1[arrayIndex1] = 0;
                arrayIndex1++;
                }
            while ((arrayIndex - tochkaIndex) < (arrayIndex1 - tochkaIndex1)) {
                arrayInteger[arrayIndex] = 0;
                arrayIndex++;
            }
        int temp1;
        int temp2;
        int temp3;
        int temp4 = 0;
        int temp6;
        boolean temp5 = false;
        int arrayIndexTemp1 = arrayIndex;
        int arrayIndexTemp2 = arrayIndex1;
        int arrayIndexTemp3;
        if(arrayIndex>arrayIndex1)
        {
            arrayIndexTemp3=arrayIndex;
        }else{
            arrayIndexTemp3=arrayIndex1;
        }
        int arrayIndexTemp4 = arrayIndexTemp3;
        int arrayIndexTemp5 = arrayIndexTemp3;
        int arrayIndexTemp6 = arrayIndexTemp3;
        for (int i = 0; i < arrayIndexTemp4+1 && arrayIndexTemp3>0; i++)
        {
               if(arrayIndexTemp1-1>=0){
                   temp1 = arrayInteger[arrayIndexTemp1-1];
               }else{
                   temp1 = 0;
               }
                 if(arrayIndexTemp2-1>=0){
                     temp2 = arrayInteger1[arrayIndexTemp2-1];
                 }else{
                     temp2 = 0;
               }
            if((temp1+temp2 + temp4) < 10){
                temp3 = temp1 + temp2 + temp4;
                temp5 = false;
             }
             else{
              temp3 = (temp1 + temp2 + temp4)-10;
              temp5 = true;
           }
            arrayInteger2[arrayIndexTemp3-1] = temp3;
            arrayIndexTemp1--;
            arrayIndexTemp2--;
            arrayIndexTemp3--;
           if(temp5 && arrayIndexTemp3 < 1 && arrayIndexTemp3 >= 0){

                for (int k = 0; k <= arrayIndexTemp6; k++){

                    temp6 = arrayInteger2[arrayIndexTemp5];
                    arrayInteger2[arrayIndexTemp5+1] = temp6;
                    arrayIndexTemp5--;
                }
                arrayIndexTemp4++;
                tochkaIndex2 = tochkaIndex2+1;
                arrayInteger2[0]=1;
                arrayInteger2[1] = temp3;
                temp5 = false;
           }
         if(temp5){
         temp4 = 1;
        }else {
         temp4 = 0;
        }
        }
       System.out.print("Сумма чисел  ");
        for (int j = 1; j < arrayIndexTemp4+1; j++) {
            System.out.print(arrayInteger2[j-1]);
            if(j==tochkaIndex2){
               System.out.print(",");
            }
        }
        System.out.println();
    }
}