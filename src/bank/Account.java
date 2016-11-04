package bank;

import java.util.ArrayList;

/**
 * Superclass for an account
 *
 * @author Grupp 4
 */
public abstract class Account {

    protected final int accountID;
    protected double saldo;
    protected double interest;
    protected final String accountType;
    protected final ArrayList<Transaction> transactions;

    /**
     * Constructor
     *
     * @param accountID The unique id of the account
     * @param accountType The type of account
     */
    public Account(int accountID, String accountType) {
        this.accountID = accountID;
        this.accountType = accountType;
        saldo = 0;
        transactions = new ArrayList();
    }

    public int getAccountID() {
        return accountID;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getInterest() {
        return interest;
    }

    public double getSaldo() {
        return saldo;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Deposits an amount to the account and creates a transaction
     *
     * @param amount The amount to deposit
     */
    public void deposit(double amount) {
        saldo += amount;
        transactions.add(new Transaction(accountID, false, amount, saldo));
    }

    /**
     * Withdraw an amount from the account and creates a transation
     *
     * @param amount The amount to withdraw
     */
    public abstract void withdraw(double amount);

    /**
     * Calculates the interest
     *
     * @return The interest
     */
    public abstract double calcInterest();

}
