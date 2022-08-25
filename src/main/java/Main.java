package main.java;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.File;
import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) throws Exception {

        IUserRepo userRepo = new JsonUserRepo();

        boolean startAgain = false;
        while (!startAgain) {
            Person currentPerson = null;
            boolean secondMenu = false;
            boolean startMenu = false;
            while (!startMenu) {
                System.out.println("Добро пожаловать!");
                System.out.println("1.Войти в систему");
                System.out.println("2.Зарегистироватся");
                System.out.println("3.Выход");
                int numOperation = scanner.nextInt();
                switch (numOperation) {
                    case 1: {
                        System.out.println("Введите логин:");
                        String logIn = scanner.next();
                        System.out.println("Введите пароль:");
                        String password = scanner.next();
                        currentPerson = userRepo.getPersonByLogin(logIn, password);
                        if (currentPerson == null) {
                            System.out.println("Пользователь не найден!");
                            System.out.println("Введите логин:");
                            logIn = scanner.next();
                            System.out.println("Введите пароль:");
                            password = scanner.next();
                            userRepo.getPersonByLogin(logIn, password);
                        }
                        startMenu = true;
                        break;
                    }
                    case 2: {
                        userRepo.createNewUser(JsonUserRepo.listPerson);
                        startMenu = true;
                        break;
                    }
                    case 3: {
                        JsonUserRepo.mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(String.valueOf(JsonUserRepo.file)), JsonUserRepo.listPerson);
                        startAgain = true;
                        startMenu = true;
                        secondMenu = true;
                        break;
                    }
                    default: {
                        System.out.println("Нет такой команды");
                        break;
                    }
                }
            }

            while (!secondMenu) {
                printMenu();
                int numberOperation = scanner.nextInt();
                switch (numberOperation) {
                    case 1: {
                        userRepo.showBalance(currentPerson);
                        break;
                    }
                    case 2: {
                        userRepo.topUpBalance(currentPerson);
                        break;
                    }
                    case 3: {
                        userRepo.makeTransfer(JsonUserRepo.listPerson, currentPerson);
                        break;
                    }
                    case 4: {
                        userRepo.showHistory(currentPerson);
                        break;
                    }
                    case 5: {
                        //Записываем в json наших пользователей с обновленной информацией
                        JsonUserRepo.mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(String.valueOf(JsonUserRepo.file)), JsonUserRepo.listPerson);
                        secondMenu = true;
                        break;
                    }
                    case 6: {
                        //Записываем в json наших пользователей с обновленной информацией
                        JsonUserRepo.mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(String.valueOf(JsonUserRepo.file)), JsonUserRepo.listPerson);
                        startAgain = true;
                        secondMenu = true;
                        break;
                    }
                    default:
                        System.out.println("Нет такой операции!");
                        break;
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите номер операции:");
        System.out.println("------------------------");
        System.out.println("1.Баланс");
        System.out.println("2.Пополнить баланс");
        System.out.println("3.Перевести деньги");
        System.out.println("4.История операций");
        System.out.println("5.Выйти из аккаунта");
        System.out.println("6.Выход");
        System.out.println("------------------------");
    }
}