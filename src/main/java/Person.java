package main.java;

import java.util.ArrayList;

public class Person {

    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    private double balance;
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }
    private int cardNumber;
    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }
    public int getCardNumber() {
        return cardNumber;
    }
    private ArrayList<String> history;
    public void setHistory(ArrayList<String> history){
        this.history = history;
    }
    public ArrayList<String> getHistory(){
        return history;
    }



    public Person(String name, int cardNumber, double balance, ArrayList<String> history) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.history = history;
    }
    public Person() {
    }
}
