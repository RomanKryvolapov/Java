package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

//In this code, I wanted to test the ability to encrypt text into the last bits of pixels in jpeg format
//The idea is to duplicate the values many times in different pixels, and then, when a certain percentage of values match, consider the value correct
//My experience was unsuccessful as the compression greatly distorts the pixel color values and the data does not match 100 percent.
//01110100011001010111100001110100
//00100100100100100100100100100100

//В этом коде я хотел протестировать возможность зашифровать текст в последние биты пикселей в формате jpeg
//Идея заключается в том, чтобы продублировать значения многократно в разных пикселях, а затем при совпадении определенного процента значений считать значение верных
//Мой опыт оказался неудачным, так как сжатие сильно искажает значения цвета пикселей, и данные не совпадают на 100 процентов.
//01110100011001010111100001110100
//00100100100100100100100100100100

public class Main {

//    public static BufferedImage temp;

    public static void main(String[] args) {

        encryption("text");
        int size = "text".length()*8;
        decryption(size);

    }

    public static void decryption(int size) {
        try {
            File file = new File("photo_2.jpg");
            BufferedImage source = ImageIO.read(file);
//            BufferedImage source = temp;
            StringBuilder result = new StringBuilder();
            int iterator = 0;
            for (int y = 0; y < source.getHeight(); y++) {
                for (int x = 0; x < source.getWidth(); x++) {
                    Color color = new Color(source.getRGB(x, y));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    StringBuilder stringBuilderRed = new StringBuilder(Integer.toBinaryString(red));
                    StringBuilder stringBuilderGreen = new StringBuilder(Integer.toBinaryString(green));
                    StringBuilder stringBuilderBlue = new StringBuilder(Integer.toBinaryString(blue));
                    while (stringBuilderRed.length() < 8) {
                        stringBuilderRed.insert(0, '0');
                    }
                    while (stringBuilderGreen.length() < 8) {
                        stringBuilderGreen.insert(0, '0');
                    }
                    while (stringBuilderBlue.length() < 8) {
                        stringBuilderBlue.insert(0, '0');
                    }
                    if (iterator < size) {
                        result.append(stringBuilderRed.charAt(7));
                        iterator++;
                    }
                    if (iterator < size) {
                        result.append(stringBuilderGreen.charAt(7));
                        iterator++;
                    }
                    if (iterator < size) {
                        result.append(stringBuilderBlue.charAt(7));
                        iterator++;
                    }
                }
            }

            System.out.println(result.toString());


        } catch (Exception e) {
            System.out.println("Exception in decryption " + e);
        }


    }

    public static void encryption(String text) {

        byte[] bytes = text.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
//            binary.append(' ');
        }
        System.out.println(binary.toString());
        try {
            File file = new File("photo.jpg");
            BufferedImage source = ImageIO.read(file);
            BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
            int iterator = 0;
            for (int y = 0; y < source.getHeight(); y++) {
                for (int x = 0; x < source.getWidth(); x++) {
                    Color color = new Color(source.getRGB(x, y));
                    int red = color.getRed();

                    int green = color.getGreen();
                    int blue = color.getBlue();
                    StringBuilder stringBuilderRed = new StringBuilder(Integer.toBinaryString(red));
                    StringBuilder stringBuilderGreen = new StringBuilder(Integer.toBinaryString(green));
                    StringBuilder stringBuilderBlue = new StringBuilder(Integer.toBinaryString(blue));
                    while (stringBuilderRed.length() < 8) {
                        stringBuilderRed.insert(0, '0');
                    }
                    while (stringBuilderGreen.length() < 8) {
                        stringBuilderGreen.insert(0, '0');
                    }
                    while (stringBuilderBlue.length() < 8) {
                        stringBuilderBlue.insert(0, '0');
                    }


                    if (iterator < binary.length()) {
                        stringBuilderRed.replace(7, 8, binary.charAt(iterator) + "");
                        iterator++;
                    }
                    if (iterator < binary.length()) {
                        stringBuilderGreen.replace(7, 8, binary.charAt(iterator) + "");
                        iterator++;
                    }
                    if (iterator < binary.length()) {
                        stringBuilderBlue.replace(7, 8, binary.charAt(iterator) + "");
                        iterator++;
                    }


                    int newRed = Integer.parseInt(stringBuilderRed.toString(), 2);

                    int newGreen = Integer.parseInt(stringBuilderGreen.toString(), 2);
                    int newBlue = Integer.parseInt(stringBuilderBlue.toString(), 2);

                    Color newColor = new Color(newRed, newGreen, newBlue);
                    result.setRGB(x, y, newColor.getRGB());

                }
            }
//            temp = result;
            File output = new File("photo_2.jpg");
            ImageIO.write(result, "jpg", output);
        } catch (Exception e) {
            System.out.println("Exception int encryption " + e);
        }
    }
}

