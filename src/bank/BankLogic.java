package bank;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {

    List<Customer> customers;
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

        //check variable names
        for (int i = 0; i < customers.size(); i++) {
            customerPresentation.add(customers.get(i).getName() + " "
                    + customers.get(i).getSsn);
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
        //check variable names 
        for (int i = 0; i < customers.size(); i++) {
            if (ssn == customers.get(i).getSsn()) {
                return false;
            }
        }

        //check constructor in Customer-klass
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

        //check variable names 
        for (int i = 0; i < customers.size(); i++) {
            if (ssn == customers.get(i).getSsn()) {
                customerInformation.add(customers.get(i).getName() + " "
                        + customers.get(i).getSsn);
                for (int j = 0; j < customers.get(i).getAccounts().size(); j++) {
                    customerInformation.add(customers.get(i).getAccounts()
                            .get(j).toString);
                }
            }
        }

        return customerInformation;
    }

    public boolean changeCustomer(String name, long ssn) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getSsn == ssn) {
                customers.get(i).setName(name);
                return true;
            }
        }
        return false;
    }

    // TODO Add saldo + interest in info
    public List<String> removeCustomer(long ssn) {
        List<String> info = new ArrayList();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getSsn == ssn) {
                ArrayList accounts = customers.get(i).getAccounts();
                for (int j = 0; j < accounts.size(); j++) {
                    info.add(accounts.get(j).toString()); // Change/Add here
                }
            }
        }
        return info;
    }

    public int addSavingsAccount(long ssn) {
        int accountNbr = -1;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getSsn == ssn) {
                accountNbr = accountNbrCounter;
                customers.get(i).getAccounts.add(new SavingsAccount(accountNbr));
                accountNbrCounter++;
            }
        }
        return accountNbr;
    }

    public String getAccount(long ssn, int accountId) {
        return "Social security: " + ssn + " account number: " + accountId;
    }

    public boolean deposit(long ssn, int accountId, double amount) {
        if (deposit) {
            return true;
        } else {
            return false;
        }

    }

    public boolean withdraw(long ssn, int accountId, double amount) {
        if (withdrawal) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * look in customer list for ssn and creates an credit account when search
     * fails returns -1
     */
    public int addCreditAccount(long ssn) {
        int accountNbr = -1;

        for (int i = 0; i < customers.size(); i++) {
            if (ssn == customers.get(i).get(ssn)) {
                customers.get(i).getAccounts.add(new CreditAccount(accountNbr));
                accountNbr = accountNbrCounter;
                accountNbrCounter++;
                break;
            }
        }
        return accountNumber;
    }

    /**
     * looks through customer list by ssn and account id and returns all
     * transactions on that account if it find nothing return -1
     *
     * @return
     */
    public arrayList<transactions> getTransactions(long ssn, int accountID) {
        for (int i = 0; i < customers.size(); i++) {
            // perhaps change to onyl search for ssn
            if (ssn == customers.get(i).get(ssn) && accountID == customers.get(i).get(accountID)) {

                for (int j = 0; j < transactions.size(); j++) {

                    return transactions.get[i].toString();
                }
            }
        }
        return -1;
    }

}
