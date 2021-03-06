package bank;

import data.DBConnection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Handles the logic of the bank. Designed with the designpattern Singelton
 *
 * @author Grupp 4
 */
public class BankLogic {

    private static BankLogic bankLogic;
    private DBConnection dbConnection;

    private BankLogic() {
        dbConnection = DBConnection.getDBConnection();
    }

    /**
     * Singelton - returns a BankLogic object
     *
     * @return The BankLogic object
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
     * @return The customer list
     */
    public ArrayList<String> getCustomers() {
        ArrayList<String> customerPresentation = new ArrayList();
        ArrayList<Customer> customers = dbConnection.getCustomers();
        for (int i = 0; i < customers.size(); i++) {
            customerPresentation.add(customers.get(i).toString());
        }

        return customerPresentation;
    }

    /**
     * Creates a new customer and adds it to the Customer-list. Returns true if
     * created and false if not.
     *
     * @param name Name of the new customer
     * @param ssn SSN of the new customer
     * @return True if successfully created customer
     */
    public boolean addCustomer(String name, long ssn) {
        Customer customer = dbConnection.getCustomer(ssn);
        if (customer != null) {
            return false;
        } else {
            customer = new Customer(name, ssn);
            dbConnection.addCustomer(customer);
            return true;
        }
    }

    /**
     * Returns a list containing information about the customer matching the
     * given social security number.
     *
     * @param ssn SSN of the customer
     * @return A list of information about the customer
     */
    public List<String> getCustomer(long ssn) {
        ArrayList<String> customerInformation = new ArrayList();
        Customer customer = dbConnection.getCustomer(ssn);
        if (customer != null) {
            customerInformation.add(customer.getName());
            customerInformation.add(Long.toString(customer.getSsn()));
            ArrayList accounts = dbConnection.getAccounts(ssn);
            Collections.sort(accounts);
            for (int i = 0; i < accounts.size(); i++) {
                customerInformation.add(accounts.get(i).toString());
            }
        }
        return customerInformation;
    }

    /**
     * Changes the name of a customer connected to a given social security
     * number. Returns true if the name is changed.
     *
     * @param name The new name of the customer
     * @param ssn The ssn of the customer
     * @return True if successfully changed the name
     */
    public boolean changeCustomer(String name, long ssn) {
        Customer customer = dbConnection.getCustomer(ssn);
        if (customer != null) {
            dbConnection.changeCustomer(customer, name);
            return true;
        }
        return false;
    }

    /**
     * Removes a customer and returns a list with information about the customer
     * and the closed accounts.
     *
     * @param ssn
     * @return
     */
    public List<String> removeCustomer(long ssn) {
        List<String> info = new ArrayList();
        Customer customer = dbConnection.getCustomer(ssn);
        if (customer != null) {
            double sumInterest = 0;
            double sumSaldo = 0;
            info.add(customer.toString());
            ArrayList<Account> accounts = dbConnection.getAccounts(ssn);
            for (int j = 0; j < accounts.size(); j++) {
                info.add(accounts.get(j).toString());
                sumInterest += accounts.get(j).calcInterest();
                sumSaldo += accounts.get(j).getSaldo();
                dbConnection.closeAccount(accounts.get(j).getAccountID());
            }
            if (sumSaldo + sumInterest < 0) {

                info.add("Total debt: " + String.format("%.2f", sumSaldo + sumInterest)
                        + " whereof interest is: " + String.format("%.2f", sumInterest));
            } else {
                info.add("Total money back: " + String.format("%.2f", sumSaldo + sumInterest)
                        + " whereof interest is: " + String.format("%.2f", sumInterest));
            }
            dbConnection.removeCustomer(customer);
        }
        return info;
    }

    /**
     * Adds a SavingAccount to customer.
     *
     * @param ssn The ssn of the customer
     * @return The accountID of the new account. -1 If the customer was not
     * found
     */
    public int addSavingsAccount(long ssn) {
        int accountNbr = -1;
        Customer customer = dbConnection.getCustomer(ssn);
        if (customer != null) {
            accountNbr = dbConnection.addSavingsAccount(ssn);
        }
        return accountNbr;
    }

    /**
     * Returns a string with the accountID, balance, type of account and
     * interest using the accountID that belongs to the ssn we use
     *
     * @param ssn SSN of the customer
     * @param accountId ID of the account
     * @return Information about the account, or error message if the account
     * was not found
     */
    public String getAccount(long ssn, int accountId) {
        Account acc = dbConnection.getAccount(ssn, accountId);
        if (acc != null) {
            return acc.toString();
        }
        return "Could not find account";
    }

    /**
     * Adds a transaction to chosen account
     *
     * @param account Account witch made the transaction
     * @param isWithdrawal True if a withdrawal was made
     * @param amount The amount that was withdrawn or deposited
     */
    private void addTransaction(Account account, boolean isWithdrawal, double amount) {
        double updatedBalance;
        if (isWithdrawal) {
            updatedBalance = account.getSaldo() - amount;
        } else {
            updatedBalance = account.getSaldo() + amount;
        }
        Transaction transaction = new Transaction(account.getAccountID(), isWithdrawal, amount, updatedBalance);
        dbConnection.addTransaction(transaction);
    }

    /**
     * Makes a deposit to an account that belongs to the customer with the
     * social security number.
     *
     * @param ssn SSN of the customer
     * @param accountId ID of the account
     * @param amount Amount to deposit
     * @return True if successfully deposited money
     */
    public boolean deposit(long ssn, int accountId, double amount) {
        Account account = dbConnection.getAccount(ssn, accountId);
        if (amount > 0) {
            dbConnection.deposit(accountId, amount);
            addTransaction(account, false, amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes a withdrawal from an account that belongs to the customer with the
     * social security number.
     *
     * @param ssn SSN of the customer
     * @param accountId ID of the account
     * @param amount Amount to withdraw
     * @return True if successfully withdrew money
     */
    public boolean withdraw(long ssn, int accountId, double amount) {
        Account account = dbConnection.getAccount(ssn, accountId);

        if (account instanceof CreditAccount && amount > 0) {
            CreditAccount acc2 = (CreditAccount) account;
            if (account.saldo - amount >= acc2.getCreditLimit()) {
                dbConnection.withdraw(accountId, amount);
                addTransaction(account, true, amount);
                return true;
            } else {
                return false;
            }
        } else if (account instanceof SavingAccount && amount > 0) {
            SavingAccount acc2 = (SavingAccount) account;
            if (!acc2.isFirstWithdrawal() && (amount + (amount * acc2.getWithdrawalFee())) <= acc2.getSaldo()) {
                dbConnection.withdraw(accountId, amount + (amount * acc2.getWithdrawalFee()));
                addTransaction(account, true, amount + (amount * acc2.getWithdrawalFee()));
                return true;

            } else if (acc2.isFirstWithdrawal() && amount <= acc2.getSaldo()) {
                dbConnection.withdraw(accountId, amount);
                dbConnection.updateFirstWithdrawal(accountId);
                addTransaction(account, true, amount);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Closes an account connected to a ssn
     *
     * @param ssn SSN of the customer
     * @param accountId ID of the account to close
     * @return info Information about the account and transactions made
     */
    public String closeAccount(long ssn, int accountId) {
        Account acc = dbConnection.getAccount(ssn, accountId);
        String info = null;
        if (acc != null) {
            info = "SSN: " + ssn + ", Type: " + acc.getAccountType()
                    + ", Saldo: " + String.format("%.2f", acc.getSaldo())
                    + ", Interest: " + String.format("%.2f", acc.calcInterest());
            dbConnection.closeAccount(accountId);
        }
        return info;
    }

    /**
     * Searches for a customer and if found, adds a credit account
     *
     * @param ssn SSN of the customer
     * @return The new accountID if successfully added otherwise -1
     */
    public int addCreditAccount(long ssn) {
        int accountNbr = -1;
        Customer customer = dbConnection.getCustomer(ssn);
        if (customer != null) {
            accountNbr = dbConnection.addCreditAccount(ssn);
        }
        return accountNbr;
    }

    /**
     * Searches for a customer and an account belonging to that customer and
     * returns the transactions made
     *
     * @param ssn SSN of the customer
     * @param accountID ID of the account
     * @return Transactions made
     */
    public List<String> getTransactions(long ssn, int accountID) {
        ArrayList<String> transactionInformation = new ArrayList();
        ArrayList<Transaction> transactions = dbConnection.getTransactions(accountID);
        for (int j = 0; j < transactions.size(); j++) {
            transactionInformation.add(transactions.get(j).toString());
        }
        return transactionInformation;
    }

    /**
     * Prints all customers to a text file.
     *
     * @return True if file successfully written
     */
    public boolean customerToFile() {
        try {
            FileWriter write = new FileWriter("Customerlist.txt");
            BufferedWriter bf = new BufferedWriter(write);
            PrintWriter pw = new PrintWriter(bf);
            ArrayList<Customer> c = dbConnection.getCustomers();
            for (int i = 0; i < c.size(); i++) {
                pw.println(c.get(i).toString());
            }
            pw.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean transactionsToFile(int accountId) {
        try {
            FileWriter write = new FileWriter("Transactionlist.txt");
            BufferedWriter bf = new BufferedWriter(write);
            PrintWriter pw = new PrintWriter(bf);
            ArrayList<Transaction> transactions = dbConnection.getTransactions(accountId);
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
            Date currentDate = new Date();
            pw.println("***** Newton Bank *****");
            pw.println("Date of account history: " + df.format(currentDate) + " "
                    + df1.format(currentDate));
            pw.println();
            pw.println("Transactions:");
            if (transactions.isEmpty()) {
                pw.println("None");
            }
            for (int i = 0; i < transactions.size(); i++) {
                pw.println(transactions.get(i).toString());
            }
            pw.close();
            return true;

        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Returns customer ssn of given index
     *
     * @param CustomerIndex Selected index
     * @return SSN of selected customer
     */
    public long getCustomerSsnViaIndex(int CustomerIndex) {
        long ssn = dbConnection.getCustomerViaIndex(CustomerIndex);
        return ssn;
    }

    /**
     * Returns the accountId of given index
     *
     * @param ssn SSN of the customer
     * @param AccountIdIndex Selected index
     * @return accountID of selected account
     */
    public int getCustomerAccountIdViaIndex(long ssn, int AccountIdIndex) {
        int accountId = dbConnection.getAccountIdViaIndex(ssn, AccountIdIndex);
        return accountId;
    }

    /**
     * Validates the names given from the user based on which characters it
     * contains and if the names start and end with letters
     *
     * @param firstName First name of the customer
     * @param lastName Last name of the customer
     * @return True if both name are valid
     */
    public boolean validateName(String firstName, String lastName) {
        boolean firstNameValid = false, lastNameValid = false;
        if (firstName.matches("^[A-zåäöÅÄÖ-]+$")) {
            if (!firstName.contains("--") && !firstName.substring(0, 1).contains("-") && !firstName.substring(firstName.length() - 1).contains("-") && !firstName.substring(firstName.length() - 1).contains("\\") && !firstName.substring(firstName.length() - 1).contains("[") && !firstName.substring(firstName.length() - 1).contains("]")) {
                firstNameValid = true;
            }
        }

        if (lastName.matches("^[A-zåäöÅÄÖ ]+$")) {
            if (!lastName.contains("  ") && !lastName.substring(0, 1).contains(" ") && !lastName.substring(lastName.length() - 1).contains(" ") && !lastName.substring(lastName.length() - 1).contains("\\") && !lastName.substring(lastName.length() - 1).contains("[") && !lastName.substring(lastName.length() - 1).contains("]")) {
                lastNameValid = true;
            }
        }

        return firstNameValid && lastNameValid;
    }

    public boolean validateSSN(long ssn) {
        long tempSSN = ssn;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int year = (int) (tempSSN / 100000000);
        tempSSN %= 100000000;
        int month = (int) (tempSSN / 1000000);
        tempSSN %= 1000000;
        int day = (int) (tempSSN / 10000);
        tempSSN %= 10000;
        int x1 = (int) (tempSSN / 10);
        int x2 = (int) (tempSSN % 10);
        if (year <= currentYear - 18) {
            if (month >= 0 && month <= 12) {
                int[] daysInMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                if (year % 4 == 0) {
                    daysInMonths[1] = 29;
                }
                if (day <= daysInMonths[month - 1]) {

                    /* -----------------------------------------
                     * Validates last digit in SSN
                     * Comment this block to not validate the last digit
                     */
                    int[] oneAndTwo = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    long partSSN = (ssn % 10000000000L) / 10;
                    int[] splittedSSN = new int[9];
                    for (int i = splittedSSN.length - 1; i >= 0; i--) {
                        splittedSSN[i] = ((int) partSSN % 10) * oneAndTwo[i];
                        partSSN = partSSN / 10;
                    }

                    ArrayList<Integer> splittedProduct = new ArrayList();
                    for (int i = 0; i < splittedSSN.length; i++) {
                        int temp = splittedSSN[i];
                        while (temp > 0) {
                            splittedProduct.add(temp % 10);
                            temp = temp / 10;
                        }
                    }

                    int sum = 0;
                    for (int value : splittedProduct) {
                        sum += value;
                    }

                    int lastDigit = (sum % 10);
                    if (lastDigit != 0) {
                        lastDigit = 10 - lastDigit;
                    }

                    if (lastDigit == x2) {
                        return true;
                    }
                    // -----------------------------------------

//                    return true; // Uncomment this to not validate last digit
                }
            }
        }
        return false;
    }

}
