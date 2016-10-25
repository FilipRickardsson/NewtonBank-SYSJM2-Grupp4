package bank;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {

    private final List<Customer> customers;
    private int accountNbrCounter;

    public BankLogic() {
        customers = new ArrayList();
        accountNbrCounter = 1001;
    }

    /**
     * Returns a list of all customers name and social security number.
     *
     * @return
     */
    public List<String> getCustomers() {
        List<String> customerPresentation = new ArrayList();

        for (int i = 0; i < customers.size(); i++) {
            customerPresentation.add(customers.get(i).toString());
        }

        return customerPresentation;
    }

    /**
     * Creates a new customer and adds it to the Customer-list. Returns true if
     * created and false if not.
     *
     * @param name
     * @param ssn
     * @return
     */
    public boolean addCustomer(String name, long ssn) {
        for (int i = 0; i < customers.size(); i++) {
            if (ssn == customers.get(i).getSsn()) {
                return false;
            }
        }

        customers.add(new Customer(name, ssn));
        return true;
    }

    /**
     * Returns a list containing information about the customer matching the
     * given social security number.
     *
     * @param ssn
     * @return
     */
    public List<String> getCustomer(long ssn) {
        ArrayList<String> customerInformation = new ArrayList();

        for (int i = 0; i < customers.size(); i++) {
            if (ssn == customers.get(i).getSsn()) {
                customerInformation.add(customers.get(i).toString());
                ArrayList accounts = customers.get(i).getAccounts();
                for (int j = 0; j < accounts.size(); j++) {
                    customerInformation.add(accounts.get(i).toString());
                }
            }
        }

        return customerInformation;
    }

    /**
     * Changes the name of a customer connected to a given social security
     * number. Returns true if name is changed.
     *
     * @param name
     * @param ssn
     * @return
     */
    public boolean changeCustomer(String name, long ssn) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getSsn() == ssn) {
                customers.get(i).setName(name);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a customer and returns a list with information about closed
     * accounts.
     *
     * @param ssn
     * @return
     */
    // TODO Add saldo + interest in info
    public List<String> removeCustomer(long ssn) {
        List<String> info = new ArrayList();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getSsn() == ssn) {
                ArrayList accounts = customers.get(i).getAccounts();
                for (int j = 0; j < accounts.size(); j++) {
                    info.add(accounts.get(j).toString()); // Change/Add here
                }
            }
        }
        return info;
    }

    /**
     * Adds a SavingAccount to customer.
     *
     * @param ssn
     * @return
     */
    public int addSavingsAccount(long ssn) {
        int accountNbr = -1;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getSsn() == ssn) {
                accountNbr = accountNbrCounter;
                customers.get(i).getAccounts().add(new SavingAccount(accountNbrCounter, "Saving Account"));
                accountNbrCounter++;
            }
        }
        return accountNbr;
    }

    public String getAccount(long ssn, int accountId) {
        for (int i = 0; i < customers.size(); i++) {
            if (ssn == customers.get(i).getSsn()) {
                ArrayList accounts = customers.get(i).getAccounts();
                for (int j = 0; j < accounts.size(); j++) {
                    return accounts.get(j).toString();
                }
            }
        }
        return "Could not find account";
    }

    public boolean deposit(long ssn, int accountId, double amount) {
        if (amount > 0) {
            searchForAccount(ssn, accountId).deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(long ssn, int accountId, double amount) {
        SavingAccount acc = searchForAccount(ssn, accountId); 
        
        if (acc instanceof SavingAccount && amount > 0) {
            acc.withdraw(amount);
            return true;
        } else if (acc instanceof CreditAccount && amount > 0) {
            CreditAccount acc2 =  (CreditAccount)acc;
            if (acc.saldo - amount >= acc2.getCreditLimit()) {
                acc2.withdraw(amount);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String closeAccount(long ssn, int accountId) {
        SavingAccount acc = searchForAccount(ssn, accountId);
        String info = null;
        if (acc != null) {
            info = acc.toString();
            Customer co = searchForCustomer(ssn);
            co.getAccounts().remove(acc);
        }
        return info;
    }

    /**
     * look in customer list for ssn and creates an credit account when search
     * fails returns -1
     */
    public int addCreditAccount(long ssn) {
        int accountNbr = -1;

        for (int i = 0; i < customers.size(); i++) {
            if (ssn == customers.get(i).getSsn()) {
                customers.get(i).getAccounts().add(new CreditAccount(accountNbr, "Credit Account"));
                accountNbr = accountNbrCounter;
                accountNbrCounter++;
                break;
            }
        }
        return accountNbr;
    }

    /**
     * looks through customer list by ssn and account id and returns all
     * transactions on that account if it find nothing return -1
     *
     * @return
     */
    public List<String> getTransactions(long ssn, int accountID) {
        ArrayList<String> transactionInformation = new ArrayList();
        for (int i = 0; i < customers.size(); i++) {
            // perhaps change to onyl search for ssn
            if (ssn == customers.get(i).getSsn && accountID == customers.get(i).getAccountID) {
                // check transaction name
                for (int j = 0; j < customers.transactions.size(); j++) {
                    transactionInformation.add(customers.get(i).getTransaction.
                                   get(j).toString());
                }

            }
        }
        return transactionInformation;
    }

    private Customer searchForCustomer(long ssn) {
        Customer customer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getSsn() == ssn) {
                customer = customers.get(i);
                break;
            }
        }
        return customer;
    }

    private SavingAccount searchForAccount(long ssn, int accountNumber) {
        SavingAccount acc = null;
        Customer customer = searchForCustomer(ssn);
        if (customer != null) {
            ArrayList<SavingAccount> accounts = customer.getAccounts();
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getAccountNumber() == accountNumber) {
                    acc = accounts.get(i);
                    break;
                }
            }

        }
        return acc;
    }

}
