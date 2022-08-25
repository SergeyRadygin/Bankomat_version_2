package main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class JsonUserRepo implements IUserRepo {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    static final Path file = Paths.get("C:\\Users\\Edge1\\IdeaProjects", "Bankomat_version_2\\src\\main\\resources\\Persons.json");
    static ObjectMapper mapper = new ObjectMapper();
    //Считываем json с нашими пользователями
    static Person[] persons;

    static {
        try {
            persons = mapper.readValue(new File(String.valueOf(file)), Person[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Создаем список на основе массива, т.к. будем добавлять новых пользователей
    static ArrayList<Person> listPerson = new ArrayList<>(Arrays.asList(persons));


    public Person getPersonByLogin(String logIn, String password) throws NoSuchAlgorithmException {
        for (Person existingPerson : listPerson) {
            if (logIn.equals(existingPerson.getLogin()) && hashPassword(password).equals(existingPerson.getPassword())) {
                return existingPerson;
            }
        }
        return null;
    }

    public Person createNewUser(ArrayList<Person> listPerson) throws NoSuchAlgorithmException {
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
        return currentPerson;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger noHash = new BigInteger(1, hashBytes);
        String hashStr = noHash.toString(16);
        return hashStr;
    }

    public double showBalance(Person currentPerson) {
        System.out.println(currentPerson.getName() + ": " + "Баланс " + currentPerson.getBalance());
        System.out.println("-------------------------");
        return currentPerson.getBalance();
    }

    public double topUpBalance(Person currentPerson) {
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
        return currentPerson.getBalance();
    }

    public ArrayList showHistory(Person currentPerson) {
        for (String message : currentPerson.getHistory()) {
            System.out.println(message);
        }
        return currentPerson.getHistory();
    }
    public Person makeTransfer(ArrayList<Person> listPerson, Person currentPerson) {
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
        return currentPerson;
    }
}