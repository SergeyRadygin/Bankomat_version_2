import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface IUserRepo {

    Person getPersonByLogin(String logIn, String password) throws NoSuchAlgorithmException;
    Person getLoginWoPass (String logIn);
    Person createNewUser(String name, String password) throws NoSuchAlgorithmException;
    Person currentPersonInfo(Person currentPerson);
    String hashPassword(String password) throws NoSuchAlgorithmException;



    //public interface IUserOperations
    double showBalance(Person currentPerson);
    double topUpBalance(Person currentPerson);
    ArrayList<Person> showHistory(Person currentPerson);
    Person makeTransfer(ArrayList<Person> listPerson, Person currentPerson);
    Person changePassword (Person currentPerson, String newPassword) throws NoSuchAlgorithmException;
}

