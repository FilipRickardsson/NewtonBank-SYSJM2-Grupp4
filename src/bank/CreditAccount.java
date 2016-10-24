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
public class CreditAccount extends SavingAccount {

    private int creditBound;
    private double creditInterest;

    public CreditAccount(int accountNumber) {
        super(accountNumber);
        this.creditInterest = 0.07;
        this.interest = 0.005;
        this.creditBound = (-5000);
    }

    public double calculateCredit(double saldo) {
        if (saldo <= 0) {
            saldo = saldo * creditInterest - saldo;
        } else if (saldo >= 1) {
            saldo = saldo * interest + saldo;
        }
        return saldo;
    }

    @Override
    public void withdrawal(double amount) {
        if (saldo - amount >= creditBound && amount > 0) {
            saldo -= amount;
        }
    }

}
