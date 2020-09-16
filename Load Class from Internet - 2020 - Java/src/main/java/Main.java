import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;

// В этом классе я пробую загружать другие классы из сети в формате .jar
// дальше к ним можно получать доступ через Reflection API

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.loadClassLocal();
        main.loadClassFromInternet();

    }

    void loadClassLocal() {
//IntegratorHelper.class
//LxsimenvHelper.class
//PosixHelper.class
//PosixXHelper.class
//Stm8010Helper.class
//TestUtil.class
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new File(System.getProperty("user.dir") + "/src/main/resources/java.jar").toURI().toURL()});
            Class loadedClass = urlClassLoader.loadClass("PosixHelper");

            Class newClass [] = Class.class.getDeclaredClasses();
            Field newField [] = Class.class.getDeclaredFields();
            System.out.println("Local:");
            System.out.println(newClass[0]);
            System.out.println(newField[0]);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    void loadClassFromInternet() {
//IntegratorHelper.class
//LxsimenvHelper.class
//PosixHelper.class
//PosixXHelper.class
//Stm8010Helper.class
//TestUtil.class
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("https://github.com/RomanKryvolapov/Java-and-Android/raw/master/Old%202016/java.jar").toURI().toURL()});
            Class loadedClass = urlClassLoader.loadClass("PosixHelper");

            Class newClass [] = Class.class.getDeclaredClasses();
            Field newField [] = Class.class.getDeclaredFields();
            System.out.println("From Internet:");
            System.out.println(newClass[0]);
            System.out.println(newField[0]);

        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }
}


