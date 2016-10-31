package bank;

public class CreditAccount extends SavingAccount {

    private final int creditLimit;
    private final double creditInterest;

    public CreditAccount(int accountNumber, String accountType) {
        super(accountNumber, accountType);
        this.creditInterest = 0.07;
        this.interest = 0.005;
        this.creditLimit = -5000;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    /**
     * Makes a withdrawal
     *
     * @param amount
     */
    @Override
    public void withdraw(double amount) {
        saldo -= amount;
        transactions.add(new Transaction(accountNumber, true, amount, saldo));
    }

    /**
     * Calculates the interest
     *
     * @return
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
        if (saldo < 0){
        return "AccountID: " + accountNumber + ", Saldo: " + String.format("%.2f", saldo) 
                + ", Type: " + accountType + ", Interest: " + creditInterest;
        }
        else{
            return "AccountID: " + accountNumber + ", Saldo: " + String.format("%.2f", saldo) 
                    + ", Type: " + accountType + ", Interest: " + interest;
        }
    }
}
