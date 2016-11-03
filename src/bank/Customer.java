package bank;

import java.util.ArrayList;

public class Customer {

    private String name;
    private final long ssn;
    private final ArrayList<Account> accounts;

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
