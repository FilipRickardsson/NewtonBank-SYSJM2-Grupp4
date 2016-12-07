package bank;

/**
 * A creditaccount in the bank
 *
 * @author Grupp 4
 */
public class CreditAccount extends Account {

    private final int creditLimit;
    private final double creditInterest;

    /**
     * Constructor
     *
     * @param accountNumber The unique account id
     */
    public CreditAccount(int accountNumber,double creditInterest,double interest,int creditLimit,double saldo) {
        super(accountNumber, "Credit Account",saldo);
        this.creditInterest = 0.07;
        this.interest = 0.005;
        this.creditLimit = -5000;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public double getCreditInterest() {
        return creditInterest;
    }
        
    /**
     * Calculates the interest
     *
     * @return The interest
     */
    @Override
    public double calcInterest() {
        if (saldo < 0) {
            return Math.round(saldo * creditInterest * 100.0) / 100.0;
        } else {
            return Math.round(saldo * interest * 100.0) / 100.0;
        }
    }

    @Override
    public String toString() {
        if (saldo < 0) {
            return "AccountID: " + accountID + ", Type: " + accountType
                    + ", Interest: " + interest + "\nSaldo: " + String.format("%.2f", saldo);
        } else {
            return "AccountID: " + accountID + ", Type: " + accountType
                    + ", Interest: " + interest + "\nSaldo: " + String.format("%.2f", saldo);
        }
    }
}
