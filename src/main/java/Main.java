package main.java;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    private static final Path file = Paths.get("C:\\Users\\Edge1\\IdeaProjects", "Bankomat_version_2\\src\\main\\resources\\Persons.json");
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        //Считываем json с нашими пользователями
        Person[] persons = mapper.readValue(new File(String.valueOf(file)), Person[].class);
        //Создаем список на основе массива, т.к. будем добавлять новых пользователей
        ArrayList<Person> listPerson = new ArrayList<>(Arrays.asList(persons));

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
                        currentPerson = logIn(listPerson);
                        if (currentPerson == null) {
                            System.out.println("Текущий пользователь - Null");
                        }
                        startMenu = true;
                        break;
                    }
                    case 2: {
                        createNewUser(listPerson);
                        startMenu = true;
                        break;
                    }
                    case 3: {
                        mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(String.valueOf(file)), listPerson);
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
                        showBalance(currentPerson);
                        break;
                    }
                    case 2: {
                        topUpBalance(currentPerson);
                        break;
                    }
                    case 3: {
                        makeTransfer(listPerson, currentPerson);
                        break;
                    }
                    case 4: {
                        showHistory(currentPerson);
                        break;
                    }
                    case 5: {
                        //Записываем в json наших пользователей с обновленной информацией
                        mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(String.valueOf(file)), listPerson);
                        secondMenu = true;
                        break;
                    }
                    case 6: {
                        //Записываем в json наших пользователей с обновленной информацией
                        mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(String.valueOf(file)), listPerson);
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

    private static Person logIn(ArrayList<Person> listPerson) throws NoSuchAlgorithmException {
        Person currentPerson = null;
        System.out.println("Введите логин:");
        String verificationLogin = scanner.next();
        System.out.println("Введите пароль:");
        String verificationPassword = scanner.next();
        for (Person existingPerson : listPerson) {
            if (verificationLogin.equals(existingPerson.getLogin()) && hashPassword(verificationPassword).equals(existingPerson.getPassword())) {
                currentPerson = existingPerson;
                return currentPerson;
            }
        }
        System.out.println("Пользователь не найден или неверное введен логин/пароль");
        return logIn(listPerson);
    }


    private static void showBalance(Person currentPerson) {
        System.out.println(currentPerson.getName() + ": " + "Баланс " + currentPerson.getBalance());
        System.out.println("-------------------------");
    }


    private static void topUpBalance(Person currentPerson) {
        System.out.println("На сколько пополнить счет: ");
        String topUp = scanner.next();
        double topUpDouble = Double.parseDouble(topUp);
        if (topUpDouble > 0) {
            double balance = currentPerson.getBalance();
            balance += topUpDouble;
            currentPerson.setBalance(balance);
            System.out.println("Баланс " + currentPerson.getBalance());
        } else {
            System.out.println("Нельзя вводить отрицательное число!");
        }
    }

    private static void makeTransfer(ArrayList<Person> listPerson, Person currentPerson) {
        Person makePerson = null;
        System.out.println("Введите номер карты: ");
        int cardNumber = scanner.nextInt();

        for (Person existingCardPerson : listPerson) {
            if (cardNumber == existingCardPerson.getCardNumber()) {
                makePerson = existingCardPerson;
            }
        }
        if (makePerson == null) {
            System.out.println("Карты с таким номером не обнаружено");
        } else if (currentPerson.getCardNumber() == makePerson.getCardNumber()) {
            System.out.println("Нельзя перевести средства себе!");
        } else {
            System.out.println("Какую сумму хотите перевести?: ");
            String transfer = scanner.next();
            double transferDouble = Double.parseDouble(transfer);

            if (transferDouble > 0 && transferDouble <= currentPerson.getBalance()) {
                currentPerson.setBalance(currentPerson.getBalance() - transferDouble);
                makePerson.setBalance(makePerson.getBalance() + transferDouble);
                System.out.println("Баланс: " + currentPerson.getBalance());
                currentPerson.getHistory().add(currentPerson.getName() + " перевел " + transfer + " на карту " + cardNumber + " клиента " + makePerson.getName());
                makePerson.getHistory().add(currentPerson.getName() + " перевел вам " + transfer);
            } else if (transferDouble < 0) {
                System.out.println("Нельзя перевести отрицательную сумму!");
            } else {
                System.out.println("Недостаточно средств для перевода!");
                currentPerson.getHistory().add(currentPerson.getName() + ": не достаточно средств для перевода на карту " + cardNumber);
            }
        }
    }

    private static void showHistory(Person currentPerson) {
        for (String message : currentPerson.getHistory()) {
            System.out.println(message);
        }
    }

    private static void createNewUser(ArrayList<Person> listPerson) throws NoSuchAlgorithmException {
        System.out.println("Введите имя:");
        String name = scanner.next();
        System.out.println("введите пароль:");
        String password = scanner.next();
        int min = 100000;
        int max = 999999;
        int cardNumber = random.nextInt(max - min) + min;
        String login = name + "@bankomat";
        Person currentPerson = new Person(name, cardNumber, 0, new ArrayList<>(), login, hashPassword(password));
        listPerson.add(currentPerson);
        System.out.println("Ваш логин: " + currentPerson.getLogin());
        System.out.println("Номер карты: " + currentPerson.getCardNumber());
        System.out.println("Ваш баланс: " + currentPerson.getBalance());
    }


    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger noHash = new BigInteger(1, hashBytes);
        String hashStr = noHash.toString(16);
        return hashStr;
    }
}