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
            Person currentPersonLogin = null;
            boolean secondMenu = false;
            boolean startMenu = false;
            while (!startMenu) {
                System.out.println("Добро пожаловать!");
                System.out.println("1.Войти в систему");
                System.out.println("2.Зарегистироватся");
                System.out.println("3.Забыли пароль?");
                System.out.println("4.Выход");
                int numOperation = scanner.nextInt();
                switch (numOperation) {
                    case 1: {
                        System.out.println("Введите логин:");
                        String logIn = scanner.next();
                        currentPersonLogin = userRepo.getLoginWoPass(logIn);
                        if (currentPersonLogin != null) {
                            System.out.println("Введите пароль:");
                            String password = scanner.next();
                            currentPerson = userRepo.getPersonByLogin(logIn, password);
                            if (currentPerson != null) {
                                startMenu = true;
                            } else {
                                System.out.println("Не верный пароль!");
                                secondMenu = true;
                            }
                        } else {
                            System.out.println("Пользователь не найден!");
                            secondMenu = true;
                        }


                        /*System.out.println("Введите пароль:");
                        String password = scanner.next();
                        currentPerson = userRepo.getPersonByLogin(logIn, password);
                        if (currentPerson == null) {
                            System.out.println("Пользователь не найден!");
                            *//*System.out.println("Введите логин:");
                            logIn = scanner.next();
                            System.out.println("Введите пароль:");
                            password = scanner.next();
                            userRepo.getPersonByLogin(logIn, password);*//*
                        }*/
                        startMenu = true;
                        break;
                    }
                    case 2: {
                        System.out.println("Введите имя:");
                        String name = scanner.next();
                        System.out.println("введите пароль:");
                        String password = scanner.next();
                        currentPerson = userRepo.createNewUser(name, password);
                        startMenu = true;
                        break;
                    }
                    case 3: {
                        System.out.println("Введите логин:");
                        String logIn = scanner.next();
                        if (userRepo.getLoginWoPass(logIn) != null) {
                            currentPerson = userRepo.getLoginWoPass(logIn);
                            System.out.println("Введите новый пароль:");
                            userRepo.changePassword(currentPerson, scanner.next());
                        } else {
                            System.out.println("Пользователь не найден!");
                        }
                        break;
                    }
                    case 4: {
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
                        System.out.println(currentPerson.getName() + ": " + "Баланс " + currentPerson.getBalance());
                        System.out.println("-------------------------");
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
                        System.out.println("Пароль сброшен! Введите новый пароль:");
                        userRepo.changePassword(currentPerson, scanner.next());
                        break;
                    }
                    case 6: {
                        userRepo.currentPersonInfo (currentPerson);
                        break;
                    }
                    case 7: {
                        //Записываем в json наших пользователей с обновленной информацией
                        JsonUserRepo.mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(String.valueOf(JsonUserRepo.file)), JsonUserRepo.listPerson);
                        secondMenu = true;
                        break;
                    }
                    case 8: {
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
        System.out.println("5.Сменить пароль");
        System.out.println("6.Информация об аккаунте");
        System.out.println("7.Выйти из аккаунта");
        System.out.println("8.Выход");
        System.out.println("------------------------");
    }
}