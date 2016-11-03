package bank;

import java.util.ArrayList;

public abstract class Account {

    protected final int accountNumber;
    protected double saldo;
    protected double interest;
    protected final String accountType;
    protected final ArrayList<Transaction> transactions;

    public Account(int accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        saldo = 0;
        transactions = new ArrayList();
    }

    public int getAccountNumber() {
        return accountNumber;
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

    public void deposit(double amount) {
        saldo += amount;
        transactions.add(new Transaction(accountNumber, false, amount, saldo));
    }

    public abstract void withdraw(double amount);

    public abstract double calcInterest();

}
