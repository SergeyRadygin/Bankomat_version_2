package main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static String name;
    static int cardNumber;
    static double balance = 0;
    private static final String FILENAME = "/C:/Users/Edge1/IdeaProjects/Bankomat_version_2/src/main/resources/Persons.json";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        //Считываем json с нашими пользователями
        Person[] persons = mapper.readValue(new File(FILENAME), Person[].class);
        //Создаем список на основе массива, т.к. будем добавлять новых пользователей
        ArrayList<Person> listPerson = new ArrayList<>(Arrays.asList(persons));

        System.out.println("Пожалуйста представтесь:");
        Person currentPerson = null;
        String verificationName = scanner.next();

        for (Person existingPerson : listPerson) {
            if (verificationName.equals(existingPerson.getName())) {
                currentPerson = existingPerson;

            } /*else {
                System.out.println("Пользователь в системе не обнаружен:");
                System.out.println("Зарегистрируемся: (Да/Нет)");
                String answer = scanner.next();
                if (answer.equals("Да")) {
                    System.out.println("Введите имя:");
                    name = scanner.next();
                    cardNumber = ThreadLocalRandom.current().nextInt(100000, 999999);
                    currentPerson = new Person(name, cardNumber, 0);
                } else if (answer.equals("Нет")){
                    return;
                }
            }*/

            while (true) {
                printMenu();
                int numberOperation = scanner.nextInt();
                if (numberOperation == 1) {
                    System.out.println(showBalance(currentPerson));
                } else if (numberOperation == 4) {
                    break;
                } else
                    System.out.println("Нет такой операции!");
            }
        }

    }


    //Записываем список в файл
    //mapper.writeValue(new File(FILENAME), persons);
    //Выводим в консоль всех пользователей для проверки


    private static void printMenu() {
        System.out.println("Выберите номер операции:");
        System.out.println("------------------------");
        System.out.println("1.Баланс");
        System.out.println("2.Пополнить баланс");
        System.out.println("3.Перевести деньги");
        System.out.println("4.Выход");
        System.out.println("------------------------");
    }

    private static double showBalance(Person currentPerson) {
        try {
            return currentPerson.getBalance();
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    private static double topUpBalance() {
        return -2;
    }

    private static double makeTransfer() {
        return -3;
    }
}