/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.ArrayList;

/**
 *
 * @author Diddi
 */
public class SavingAccount {

    protected int accountNumber;
    protected double amount;
    protected double saldo;
    protected double interest;
    protected String accountType;
    protected boolean firstWithdrawal;
    protected ArrayList<Transaction> transa;

    public SavingAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        this.saldo = 0;
        this.interest = 1.01;
        transa=new ArrayList();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            amount += saldo;
        }
    }

    public void withdrawal(double amount) {
        if (amount > 0 && saldo>=amount) {
            if (firstWithdrawal == true) {
                amount -= saldo;
            } else {
                saldo = (1.02 * amount);
            }
        }
    }

    public double calculateInterest() {
        return saldo *= interest;
    }

    @Override
    public String toString() {
        return accountNumber + " " + saldo + " " + accountType + " " + interest;
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters">
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    //</editor-fold>

}
