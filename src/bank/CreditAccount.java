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

    public double getCreditInterest() {
        return creditInterest;
    }

    public double calcDebt() {
        return saldo * creditInterest;
    }

    @Override
    public void withdraw(double amount) {
        saldo -= amount;
        transactions.add(new Transaction(accountNumber, true, amount, saldo));
    }

    //    @Override
//    public double calcInterest() {
//        return saldo * interest;
//        
//        if (saldo < 0) {
//            saldo = saldo * creditInterest - saldo;
//        } else if (saldo >= 1) {
//            saldo = saldo * interest + saldo;
//        }
//        return saldo;
//    }
}
