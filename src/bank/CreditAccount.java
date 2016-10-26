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

//    public double getCreditInterest() {
//        return creditInterest;
//    }
//
//    public double calcDebt() {
//        return saldo * creditInterest;
//    }
    /**
     * Makes a withdrawal
     * @param amount 
     */
    @Override
    public void withdraw(double amount) {
        saldo -= amount;
        transactions.add(new Transaction(accountNumber, true, amount, saldo));
    }
    
    /**
     * Calculates the interest
     * @return 
     */
    @Override
    public double calcInterest() {
        if (saldo < 0) {
            return saldo * creditInterest;
        } else if (saldo >= 0) {
            return saldo * interest;
        }
        return saldo;
    }
}
