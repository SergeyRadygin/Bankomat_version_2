package main.java;

public class Person {
    public String getName() {
        return name;
    }

    private String name;

    public double getBalance() {
        return balance;
    }

    private double balance;

    public int getCardNumber() {
        return cardNumber;
    }

    private int cardNumber;


    public Person(String name, int cardNumber, double balance) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public Person() {
    }
}
