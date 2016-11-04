package bank;

import java.util.ArrayList;

/**
 * A Customer in the bank
 * @author Grupp 4
 */
public class Customer {

    private String name;
    private final long ssn;
    private final ArrayList<Account> accounts;

    /**
     * Constructor
     * @param name Name of the customer
     * @param ssn SSN of the customer
     */
    public Customer(String name, long ssn) {
        this.name = name;
        this.ssn = ssn;
        accounts = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSsn() {
        return ssn;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", SSN: " + ssn;
    }

}
