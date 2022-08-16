package main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class Main {

    static String name;
    static int cardNumber;
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
                System.out.println("Пожалуйста представтесь:");

                String verificationName = scanner.next();

                for (Person existingPerson : listPerson) {
                    if (verificationName.equals(existingPerson.getName())) {
                        currentPerson = existingPerson;
                        exit = true;
                    }
                }
                if (currentPerson == null) {
                    System.out.println("Пользователь в системе не обнаружен!");
                    System.out.println("Зарегистрируемся: (Yes/No)");

                    String answer = scanner.next();


                    switch (answer) {
                        case "Yes": {
                            System.out.println("Введите имя:");
                            name = verificationName;
                            int min = 100000;
                            int max = 999999;
                            cardNumber = random.nextInt(max - min) + min;
                            currentPerson = new Person(name, cardNumber, 0, new ArrayList<String>());
                            listPerson.add(currentPerson);
                            System.out.println(currentPerson.getName() + "\n" + currentPerson.getCardNumber() + "\n" + currentPerson.getBalance() + "\n" + currentPerson.getHistory());
                            exit = true;
                            break;
                        }
                        case "No": {
                            System.out.println("1.Продолжить работу");
                            System.out.println("2.Завершить работу программы ");
                            int operation = scanner.nextInt();
                            if (operation == 1) {
                                continue;

                            } else {
                                System.exit(0);
                                break;
                            }
                        }
                        default:
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
                        String balanceMessage = "Баланс: " + currentPerson.getBalance();
                        System.out.println(currentPerson.getName() + ": " + balanceMessage);
                        currentPerson.getHistory().add(currentPerson.getName() + ": " + balanceMessage);
                        System.out.println("-------------------------");
                        break;
                    }
                    case 2: {
                        System.out.println("На сколько пополнить счет: ");
                        double topUp = scanner.nextDouble();
                        System.out.println(topUpBalance(topUp));
                        currentPerson.getHistory().add(currentPerson.getName() + " пополнил баланс на: " + topUp);
                        break;
                    }
                    case 3: {
                        makeTransfer(listPerson);
                        break;
                    }
                    case 4: {
                        for (String message : currentPerson.getHistory()) {
                            System.out.println(message);
                        }
                        currentPerson.getHistory().add(currentPerson.getName() + " запросил историю операций");
                        break;
                    }
                    case 5: {
                        exit = true;
                        break;
                    }
                    case 6: {
                        currentPerson.getHistory().clear();
                        mapper.writeValue(new File(FILENAME), listPerson);
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

        private static void printMenu () {
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

        private static double topUpBalance ( double topUp){

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

        private static void makeTransfer (ArrayList < Person > listPerson) {
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
            } else {
                System.out.println("Какую сумму хотите перевести?: ");

                double transfer = scanner.nextDouble();
                if (transfer < currentPerson.getBalance()) {
                    double currentPersonBalance = currentPerson.getBalance() - transfer;
                    currentPerson.setBalance(currentPersonBalance);
                    double makePersonBalance = makePerson.getBalance() + transfer;
                    makePerson.setBalance(makePersonBalance);
                    System.out.println("Баланс: " + currentPerson.getBalance());
                    currentPerson.getHistory().add(currentPerson.getName() + " перевел " + transfer + " на карту " + cardNumber);
                } else {
                    System.out.println("Недостаточно средств для перевода!");
                    currentPerson.getHistory().add(currentPerson.getName() + ": не достаточно средств для перевода на карту " + cardNumber);
                }
            }
        }
    }