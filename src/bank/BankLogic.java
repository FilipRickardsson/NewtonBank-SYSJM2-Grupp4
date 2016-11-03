package bank;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankLogic {

    private static BankLogic bankLogic;

    private final List<Customer> customers;
    private int accountNbrCounter;

    private BankLogic() {
        customers = new ArrayList();
        accountNbrCounter = 1001;

        //To test
        customers.add(new Customer("Dijana Popovic", 7912120101L));
        customers.add(new Customer("Johan Jonsson", 9702020101L));
        customers.add(new Customer("Christoffer Flystam", 9202254545L));
        customers.add(new Customer("Tobias Hjertelundh", 8706045625L));
        customers.add(new Customer("Bekir Halvadzic", 9909195421L));
        customers.add(new Customer("Filip Rickardsson", 8802023251L));
        //To test
        for (int i = 0; i < customers.size(); i++) {
            addSavingsAccount(customers.get(i).getSsn());
            addCreditAccount(customers.get(i).getSsn());
        }
    }

    /**
     * Singelton - returns a BankLogic object
     *
     * @return
     */
    public static BankLogic getBankLogic() {
        if (bankLogic == null) {
            bankLogic = new BankLogic();
        }
        return bankLogic;
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
        Customer customer = searchForCustomer(ssn);
        if (customer != null) {
            return false;
        } else {
            customers.add(new Customer(name, ssn));
            return true;
        }
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

        Customer customer = searchForCustomer(ssn);
        if (customer != null) {
            customerInformation.add(customer.getName());
            customerInformation.add(Long.toString(customer.getSsn()));
            ArrayList accounts = customer.getAccounts();
            for (int j = 0; j < accounts.size(); j++) {
                customerInformation.add(accounts.get(j).toString());
            }

            for (int i = 0; i < customerInformation.size(); i++) {
                System.out.println(customerInformation.get(i));
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
        Customer customer = searchForCustomer(ssn);
        if (customer != null) {
            customer.setName(name);
            return true;
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
    public List<String> removeCustomer(long ssn) {
        List<String> info = new ArrayList();
        Customer customer = searchForCustomer(ssn);
        if (customer != null) {
            double sumInterest = 0;
            double sumSaldo = 0;
            info.add(customer.toString());
            ArrayList<SavingAccount> accounts = customer.getAccounts();
            for (int j = 0; j < accounts.size(); j++) {
                info.add(accounts.get(j).toString());
                sumInterest += accounts.get(j).calcInterest();
                sumSaldo += accounts.get(j).getSaldo();
            }
            if (sumSaldo + sumInterest < 0) {

                info.add("Total debt: " + String.format("%.2f", sumSaldo + sumInterest)
                        + " whereof interest is: " + String.format("%.2f", sumInterest));
            } else {
                info.add("Total money back: " + String.format("%.2f", sumSaldo + sumInterest)
                        + " whereof interest is: " + String.format("%.2f", sumInterest));
            }

            customers.remove(customer);
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
        Customer customer = searchForCustomer(ssn);
        if (customer != null) {
            accountNbr = accountNbrCounter;
            customer.getAccounts().add(new SavingAccount(accountNbr, "Saving Account"));
            accountNbrCounter++;
        }
        return accountNbr;
    }

    /**
     * Returns a string with the accountID,balance,type of account and interest
     * using the accountID that belongs to the ssn we use
     *
     * @param ssn
     * @param accountId
     * @return
     */
    public String getAccount(long ssn, int accountId) {
        SavingAccount acc = searchForAccount(ssn, accountId);
        if (acc != null) {
            return acc.toString();
        }
        return "Could not find account";
    }

    /**
     * makes a deposit from the accountnumber that belongs to the right social
     * security number returns true if it went through otherwise i returnes
     * false
     *
     * @param ssn
     * @param accountId
     * @param amount
     * @return
     */
    public boolean deposit(long ssn, int accountId, double amount) {
        if (amount > 0) {
            searchForAccount(ssn, accountId).deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * makes a withdrawal from accountnumber that belongs to the right social
     * security number returns true if it went through otherwise i returnes
     * false
     *
     * @param ssn
     * @param accountId
     * @param amount
     * @return
     */
    public boolean withdraw(long ssn, int accountId, double amount) {
        SavingAccount acc = searchForAccount(ssn, accountId);

        if (acc instanceof CreditAccount && amount > 0) {
            CreditAccount acc2 = (CreditAccount) acc;
            if (acc.saldo - amount >= acc2.getCreditLimit()) {
                acc2.withdraw(amount);
                return true;
            } else {
                return false;
            }
        } else if (acc instanceof SavingAccount && amount > 0) {
            if ((amount + (amount * acc.getWithdrawalFee())) <= acc.getSaldo()) {
                acc.withdraw(amount);
                return true;

            } else if (acc.isFirstWithdrawal() && amount <= acc.getSaldo()) {
                acc.withdraw(amount);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Closes an account connected to a ssn and accountId if accountId is
     * provided.
     *
     * @param ssn
     * @param accountId
     * @return info
     */
    public String closeAccount(long ssn, int accountId) {
        SavingAccount acc = searchForAccount(ssn, accountId);
        String info = null;
        if (acc != null) {
            info = "SSN: " + ssn + ", Type: " + acc.getAccountType()
                    + ", Saldo: " + String.format("%.2f", acc.getSaldo())
                    + ", Interest: " + String.format("%.2f", acc.calcInterest());
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
        Customer customer = searchForCustomer(ssn);
        if (customer != null) {
            accountNbr = accountNbrCounter;
            searchForCustomer(ssn).getAccounts().add(new CreditAccount(accountNbr, "Credit Account"));
            accountNbrCounter++;
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

        SavingAccount acc = searchForAccount(ssn, accountID);

        for (int j = 0; j < acc.getTransactions().size(); j++) {
            transactionInformation.add(acc.getTransactions().
                    get(j).toString());
        }

        return transactionInformation;
    }

    /**
     * Searches through the list of customers with their ssn then returns the
     * customer with that ssn
     *
     * @param ssn
     * @return
     */
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

    /**
     * Searches through Customers first then through their account with the
     * accountID and gives us that account
     *
     * @param ssn
     * @param accountNumber
     * @return
     */
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

    /**
     * Prints all customers to a text file
     */
    public void customerToFile() {
        try {
            FileWriter write = new FileWriter("Customerlist.txt");
            BufferedWriter bf = new BufferedWriter(write);
            PrintWriter pw = new PrintWriter(bf);
            for (int i = 0; i < customers.size(); i++) {
                pw.println(customers.get(i).toString());
            }
            pw.close();

        } catch (IOException ex) {
            Logger.getLogger(BankLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns customer ssn og given index
     *
     * @param CustomerIndex
     * @return
     */
    public long getCustomerSsnViaIndex(int CustomerIndex) {
        return customers.get(CustomerIndex).getSsn();
    }

    /**
     * Returns the accountId of given index
     *
     * @param AccountIdIndex
     * @return
     */
    public int getCustomerAccountIdViaIndex(int AccountIdIndex) {
        Customer customer = searchForCustomer(BaseController.selectedCustomerSSN);
        return customer.getAccounts().get(AccountIdIndex).getAccountNumber();
    }

    /**
     * Makes sure a string only contains letters
     *
     * @param str
     * @return
     */
    public boolean isAlpha(String str) {
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateName(String firstName, String lastName) {
        boolean firstNameValid = false, lastNameValid = false;
        if (firstName.matches("^[A-zåäöÅÄÖ-]+$")) {
            if (!firstName.contains("--") && !firstName.substring(0, 1).contains("-") && !firstName.substring(firstName.length() - 1).contains("-")) {
                firstNameValid = true;
            }
        }

        if (lastName.matches("^[A-zåäöÅÄÖ ]+$")) {
            if (!lastName.contains("  ") && !lastName.substring(0, 1).contains(" ") && !lastName.substring(lastName.length() - 1).contains(" ")) {
                lastNameValid = true;
            }
        }

        return firstNameValid && lastNameValid;
    }

}
