package bank;

import java.util.ArrayList;

public class Customer {

    private String name;
    private final long ssn;
    private final ArrayList<SavingAccount> accounts;

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

    public ArrayList<SavingAccount> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return name+ "" +ssn;
    }
    
}
