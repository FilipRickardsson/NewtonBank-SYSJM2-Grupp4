/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

/**
 *
 * @author Diddi
 */
public class SavingAccount {

    private int accountNumber;
    private double amount;
    private double saldo;
    private double interest;
    private String accountType;
    private boolean firstWithdrawal;

    public void SavingsAccount() {

    }

    public void SavingsAccount(int accountNumber, double amount, double saldo, double interest) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.saldo = saldo;
        this.interest = interest;
    }

    public void deposit(int amount) {
        if (amount > 0) {
            amount += saldo;
        }
    }

    public void withdrawal(int amount) {
        if (amount > 0 && saldo>=amount) {
            if (firstWithdrawal == true) {
                amount -= saldo;
            } else {
                saldo = (1.02 * amount);
            }
        }
    }

    public double calculateInterest() {
        double result = saldo * 1.01;
        return result;
    }

    @Override
    public String toString() {
        String result = accountNumber + " " + saldo + " " + accountType + " " + interest;
        return result;
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
