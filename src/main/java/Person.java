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
    private String login;
    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }
    public String password;
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }



    public Person(String name, int cardNumber, double balance, ArrayList<String> history, String login, String password) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.history = history;
        this.login = login;
        this.password = password;
    }
    public Person() {
    }
}
