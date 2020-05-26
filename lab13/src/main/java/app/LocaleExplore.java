package app;

import com.DisplayLocales;
import com.Info;
import com.SetLocale;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String args[]){
        Scanner s = new Scanner(System.in);
        String baseName = "res.Messages";
        String baseName2="res.Commands";
        while(true){
            Locale l = Locale.getDefault();
            ResourceBundle messages = ResourceBundle.getBundle(baseName, l);
            ResourceBundle commands = ResourceBundle.getBundle(baseName2, l);
            System.out.println(messages.getString("available")+
                    commands.getString("set-locale.command")+", "+
                    commands.getString("info-locale.command")+", "+
                    commands.getString("display-locales.command")+", "+
                    commands.getString("exit.command"));
            System.out.println(messages.getString("prompt"));
            String command = s.nextLine();
            if(command.equals(commands.getString("info-locale.command"))){
                Class c = null;
                try {
                    c = Class.forName(commands.getString("info-locale.impl"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    Constructor constr= c.getConstructor();
                    constr.newInstance();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }else if(command.equals(commands.getString("display-locales.command"))){
                Class c = null;
                try {
                    c = Class.forName(commands.getString("display-locales.impl"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    Constructor constr= c.getConstructor();
                    constr.newInstance();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }else if(command.equals(commands.getString("set-locale.command"))){
                System.out.println("Locale: ");
                System.out.println("1. ro");
                System.out.println("2. en");
                System.out.println(messages.getString("choice"));
                String option = s.nextLine();
                String locale;
                if(option.equals("1")){
                    locale="ro-RO";
                }else locale="en-US";
                Class c = null;
                try {
                    c = Class.forName(commands.getString("set-locale.impl"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    Constructor constructor = c.getConstructor(String.class);
                    Object instanceOfMyClass = constructor.newInstance(locale);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }else if(command.equals(commands.getString("exit.command"))){
                System.out.println(messages.getString("bye"));
                break;
            }else System.out.println(messages.getString("invalid"));
        }

    }
}
