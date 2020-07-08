package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {

            File file = new File("photo.jpg");
            BufferedImage source = ImageIO.read(file);

            BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

            for (int y = 0; y < source.getHeight(); y++) {
                for (int x = 0; x < source.getWidth(); x++) {

                    Color color = new Color(source.getRGB(x, y));

                    int blue = color.getBlue();
                    int red = color.getRed();
                    int green = color.getGreen();

                    int grey = (int) (red * 0.299 + green * 0.587 + blue * 0.114);

                    if(0<=grey && grey<=21){
                        System.out.print("-");
                    }
                    if(22<=grey && grey<=42){
                        System.out.print(":");
                    }
                    if(43<=grey && grey<=63){
                        System.out.print("!");
                    }
                    if(64<=grey && grey<=84){
                        System.out.print("=");
                    }
                    if(85<=grey && grey<=105){
                        System.out.print("+");
                    }
                    if(106<=grey && grey<=126){
                        System.out.print(">");
                    }
                    if(127<=grey && grey<=147){
                        System.out.print("?");
                    }
                    if(148<=grey && grey<=168){
                        System.out.print("#");
                    }
                    if(169<=grey && grey<=189){
                        System.out.print("%");
                    }
                    if(190<=grey && grey<=210){
                        System.out.print("&");
                    }
                    if(211<=grey && grey<=231){
                        System.out.print("$");
                    }
                    if(232<=grey && grey<=255){
                        System.out.print("@");
                    }



//                    int newRed = grey;
//                    int newGreen = grey;
//                    int newBlue = grey;

//                    Color newColor = new Color(newRed, newGreen, newBlue);
//
//                    result.setRGB(x, y, newColor.getRGB());
                }
                System.out.println(" ");
            }

//            File output = new File("photo_2.jpg");
//            ImageIO.write(result, "jpg", output);
//            System.out.println("Файл не найден или не удалось сохранить");
        } catch (IOException e) {

            System.out.println("Файл не найден или не удалось сохранить");
        }


    }
}


