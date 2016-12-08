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

    /**
     * Constructor
     *
     * @param accountID The unique id of the account
     * @param accountType The type of account
     * @param saldo
     */
    public Account(int accountID, String accountType,double saldo) {
        this.accountID = accountID;
        this.accountType = accountType;
        this.saldo=saldo;
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

    /**
     * Calculates the interest
     *
     * @return The interest
     */
    public abstract double calcInterest();

}
