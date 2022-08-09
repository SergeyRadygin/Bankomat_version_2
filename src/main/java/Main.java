package main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class Main {

    String name;
    int cardNumber;
    double balance = 0;
    private static final String FILENAME = "/C:/Users/Edge1/IdeaProjects/Bankomat_version_2/src/main/resources/Persons.json";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        ObjectMapper mapper = new ObjectMapper();

        Person[] persons = new Person[2];

        persons[0] = new Person("Mike", 654321, 0);
        persons[1] = new Person("John", 123456, 2000);
        mapper.writeValue(new File(FILENAME), persons);

        //Person[] person = mapper.readValue(new File(FILENAME), Person[].class);

        for (int i = 0; i < persons.length; i++) {
            System.out.println(persons[i].getName() + " " + persons[i].getCardNumber() + " " + persons[i].getBalance());
        }



            //System.out.println(showBalance(scanner,prs));
        /*while (true) {
            printMenu();
            int operation = scanner.nextInt();
            if (operation == 1) {
            }
        }*/
    }


    /*private static void printMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите номер операции:");
        System.out.println("------------------------");
        System.out.println("1.Баланс");
        System.out.println("2.Пополнить баланс");
        System.out.println("3.Перевести деньги");
        System.out.println("4.Выход");
        System.out.println("------------------------");
    }*/

    private static double showBalance(Scanner scanner, Person person) {
        System.out.println("Введите имя:");
        String verificName = scanner.nextLine();
        if (verificName.equals(person.getName())) {
            return person.getBalance();
        } else {
            System.out.println("Нет такого клиента!");
        }
        return -1;
    }

    /*private static double topUpBalance(Scanner scanner, Person person) {
        double topUp = scanner.nextDouble();
        person.balance += topUp;
        return person.balance;
    }

    private static double makeTransfer(Scanner scanner, Person person) {
        int cardNumber = person.cardNumber;
        double transfer = scanner.nextDouble();

        return balance;
    }*/

}