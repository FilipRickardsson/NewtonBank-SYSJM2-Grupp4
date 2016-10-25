package bank;

import java.util.ArrayList;

public class SavingAccount {

    protected final int accountNumber;
    protected double saldo;
    protected double interest;
    protected final String accountType;
    protected boolean firstWithdrawal;
    protected final double withdrawalFee;
    protected ArrayList<Transaction> transactions;

    public SavingAccount(int accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.saldo = 0;
        this.interest = 0.01;
        this.accountType = accountType;
        this.withdrawalFee = 0.02;
        transactions = new ArrayList();
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters">
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
    
    

    //</editor-fold>
    public void deposit(double amount) {
        saldo += amount;
        transactions.add(new Transaction(accountNumber, false, amount, saldo));
    }

    public void withdraw(double amount) {
        if (firstWithdrawal) {
            saldo -= amount;
            firstWithdrawal = false;
        } else {
            saldo -= amount * withdrawalFee + amount;
        }
        transactions.add(new Transaction(accountNumber, true, amount, saldo));
    }

    public double calcInterest() {
        return saldo * interest;
    }

    // TODO change this later
    @Override
    public String toString() {
        return accountNumber + " " + saldo + " " + accountType + " " + interest;
    }

}
