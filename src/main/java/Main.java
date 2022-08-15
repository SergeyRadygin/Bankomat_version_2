package main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    static String name;
    static int cardNumber;
    static double balance = 0;
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

        System.out.println("Пожалуйста представтесь:");

        String verificationName = scanner.next();

        for (Person existingPerson : listPerson) {
            if (verificationName.equals(existingPerson.getName())) {
                currentPerson = existingPerson;
            }
        }
        if (currentPerson == null) {
            System.out.println("Пользователь в системе не обнаружен!");
            System.out.println("Зарегистрируемся: (Yes/No)");
            String answer = scanner.next();
            while (true) {
                if (answer.equals("Yes")) {
                    System.out.println("Введите имя:");
                    name = scanner.next();
                    int min = 100000;
                    int max = 999999;
                    cardNumber = random.nextInt(max - min) + min;
                    currentPerson = new Person(name, cardNumber, 0);
                    System.out.println(currentPerson.getName() + "\n" + currentPerson.getCardNumber() + "\n" + currentPerson.getBalance());
                } else if (answer.equals("No")) {
                    System.out.println("Завершаем программу.");
                    break;
                } else {
                    System.out.println("Нет такой команды");
                }
            }
        }
        while (true) {
            printMenu();
            int numberOperation = scanner.nextInt();
            if ((numberOperation == 1) && (currentPerson != null)) {
                System.out.println("Баланс: " + currentPerson.getBalance());
                System.out.println("-------------------------");

            } else if (numberOperation == 2) {
                System.out.println("На сколько пополнить счет: ");
                try {
                    System.out.println(topUpBalance(scanner.nextDouble()));
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (numberOperation == 3) {
                makeTransfer(listPerson);
            } else if (numberOperation == 4) {
                mapper.writeValue(new File(FILENAME), persons);
                break;
            } else
                System.out.println("Нет такой операции! или текущий пользователь null");
        }


        //Записываем список в файл


        //Выводим в консоль всех пользователей для проверки

    }


    private static void printMenu() {
        System.out.println("Выберите номер операции:");
        System.out.println("------------------------");
        System.out.println("1.Баланс");
        System.out.println("2.Пополнить баланс");
        System.out.println("3.Перевести деньги");
        System.out.println("4.Выход");
        System.out.println("------------------------");
    }


    private static double topUpBalance(double topUp) {

        if (topUp > 0) {
            double balance = currentPerson.getBalance();
            System.out.print("Баланс: ");
            balance += topUp;
            currentPerson.setBalance(balance);
            return balance;
        } else {
            System.out.println("Нельзя вводить отрицательное число!");
        }
        return currentPerson.getBalance();
    }

    private static double makeTransfer(ArrayList<Person> listPerson) {
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
            return currentPerson.getBalance();
        } else {
            System.out.println("Какую сумму хотите перевести?: ");

            double transfer = scanner.nextDouble();
            if (transfer < currentPerson.getBalance()) {
                double currentPersonBalance = currentPerson.getBalance() - transfer;
                currentPerson.setBalance(currentPersonBalance);
                double makePersonBalance = makePerson.getBalance() + transfer;
                makePerson.setBalance(makePersonBalance);
                System.out.println("Баланс: " + currentPerson.getBalance());
                return currentPerson.getBalance();
            } else {
                System.out.println("Недостаточно средств для перевода!");
                return currentPerson.getBalance();
            }
        }
    }
}