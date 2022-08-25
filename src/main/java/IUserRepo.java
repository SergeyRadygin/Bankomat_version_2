package main.java;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface IUserRepo {

    Person getPersonByLogin(String logIn, String password) throws NoSuchAlgorithmException;

    Person createNewUser(ArrayList<Person> listPerson) throws NoSuchAlgorithmException;

    String hashPassword(String password) throws NoSuchAlgorithmException;

    double showBalance(Person currentPerson);

    double topUpBalance(Person currentPerson);

    ArrayList<Person> showHistory(Person currentPerson);

    Person makeTransfer(ArrayList<Person> listPerson, Person currentPerson);
}

