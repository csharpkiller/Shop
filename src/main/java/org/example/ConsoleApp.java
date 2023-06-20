package org.example;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    public void run(){
        User currentUser = null;
        boolean logged = false;
        DataBase dataBase = new DataBase();

        while (true){
            Scanner in = new Scanner(System.in);
            String str = in.nextLine();

            switch (str){
                case "/reg":
                {
                    System.out.println("Write ur info");
                    String reg = in.nextLine();
                    currentUser = reg(reg);
                    System.out.println("Account be registred");
                    break;
                }
                case "/log":
                {
                    System.out.println("Write ur email and password");
                    String log = in.nextLine();
                    String logs[] = log.split(" ");
                    if(dataBase.checkCorrectPassword(logs[0], logs[1])){
                        currentUser = dataBase.getUser(logs[0], logs[1]);
                        logged=true;
                        System.out.println("DONE");
                    }
                    else {
                        System.out.println("Wrong password or email");
                    }
                    break;
                }
                case "/check":
                {
                    dataBase.checkProductType();
                    break;
                }
                case "/check1":{
                    System.out.println("Write number product type or 0 for see all");
                    String ch = in.nextLine();
                    dataBase.checkProduct(Integer.parseInt(ch));
                    break;
                }
                case "/order":{
                    if(!logged){
                        System.out.println("Need logg in");
                        break;
                    }else {
                        System.out.println("Write productId and productCount");
                        String or = in.nextLine();
                        String[] splt = or.split(" ");
                        dataBase.createOrder(currentUser, Integer.parseInt(splt[0]), Integer.parseInt(splt[1]));
                    }
                    break;
                }
                case "/exit":{
                    return;
                }
            }
        }
    }

    private User reg(String str){
        String[] splt = str.split(" ");
        User user = new User(splt[0], splt[1], splt[2], splt[3], Integer.parseInt(splt[4]), splt[5]);
        DataBase dataBase = new DataBase();
        dataBase.addUserToBase(user);
        return user;
    }
}
