package main.java;

public class Person {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public double getBalance() {
        return balance;
    }

    private double balance;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    private int cardNumber;

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Person(String name, int cardNumber, double balance) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public Person() {
    }
}
