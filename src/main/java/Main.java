package main.java;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class Main {
    static String name;
    static int cardNumber;
    static String login;
    static String password;
    private static final String FILENAME = "/C:/Users/Edge1/IdeaProjects/Bankomat_version_2/src/main/resources/Persons.json";
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static Person currentPerson = null;

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        //Считываем json с нашими пользователями
        Person[] persons = mapper.readValue(new File(FILENAME), Person[].class);
        //Создаем список на основе массива, т.к. будем добавлять новых пользователей
        ArrayList<Person> listPerson = new ArrayList<>(Arrays.asList(persons));

        boolean startAgain = false;
        while (!startAgain) {
            boolean exit = false;
            while (!exit) {
                System.out.println("Добро пожаловать!");
                System.out.println("1.Войти в систему");
                System.out.println("2.Зарегистироватся");
                System.out.println("3.Выход");
                int numOperation = scanner.nextInt();
                switch (numOperation) {
                    case 1: {
                        logIn(listPerson);
                        if (currentPerson == null) {
                            System.out.println("Неверно введен логин или пароль/Пользователь не найден в системе!");
                        } else {
                            exit = true;
                        }
                        break;
                    }
                    case 2: {
                        if (currentPerson == null) {
                            createNewUser(listPerson);
                            exit = true;
                        }
                        break;
                    }
                    case 3: {
                        mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(FILENAME), listPerson);
                        System.exit(0);
                        break;
                    }
                    default: {
                        System.out.println("Нет такой команды");
                        break;
                    }
                }
            }

            exit = false;
            while (!exit) {
                printMenu();
                int numberOperation = scanner.nextInt();
                switch (numberOperation) {
                    case 1: {
                        showBalance();
                        break;
                    }
                    case 2: {
                        topUpBalance();
                        break;
                    }
                    case 3: {
                        makeTransfer(listPerson);
                        break;
                    }
                    case 4: {
                        showHistory();
                        break;
                    }
                    case 5: {
                        //Записываем в json наших пользователей с обновленной информацией
                        mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(FILENAME), listPerson);
                        currentPerson = null;
                        exit = true;
                        break;
                    }
                    case 6: {
                        //Записываем в json наших пользователей с обновленной информацией
                        mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(FILENAME), listPerson);
                        System.exit(0);
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

    private static void showBalance() {
        String balanceMessage = "Баланс " + currentPerson.getBalance();
        System.out.println(currentPerson.getName() + ": " + balanceMessage);
        System.out.println("-------------------------");
    }


    private static void topUpBalance() {
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

    private static void makeTransfer(ArrayList<Person> listPerson) {
        Person makePerson = null;
        System.out.println("Введите номер карты: ");
        cardNumber = scanner.nextInt();

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

    private static void showHistory() {
        for (String message : currentPerson.getHistory()) {
            System.out.println(message);
        }
    }

    private static void createNewUser (ArrayList<Person> listPerson) {
        System.out.println("Введите имя:");
        name = scanner.next();
        System.out.println("введите пароль:");
        password = scanner.next();
        int min = 100000;
        int max = 999999;
        cardNumber = random.nextInt(max - min) + min;
        login = name + "@bankomat";
        currentPerson = new Person(name, cardNumber, 0, new ArrayList<>(), login, password);
        listPerson.add(currentPerson);
        System.out.println("Ваш логин: " + currentPerson.getLogin());
        System.out.println("Номер карты: " + currentPerson.getCardNumber());
        System.out.println("Ваш баланс: " + currentPerson.getBalance());
    }

    private static Person logIn (ArrayList<Person> listPerson) {
        System.out.println("Введите логин:");
        String verificationLogin = scanner.next();
        System.out.println("Введите пароль:");
        String verificationPassword = scanner.next();

        for (Person existingPerson : listPerson) {
            if (verificationLogin.equals(existingPerson.getLogin()) && verificationPassword.equals(existingPerson.getPassword())) {
                int verifPass = verificationPassword.hashCode();
                int pass = existingPerson.getPassword().hashCode();
                if (verifPass == pass) {
                    currentPerson = existingPerson;
                }
            }
        }
        return currentPerson;
    }
}