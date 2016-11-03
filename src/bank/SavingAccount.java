package bank;

public class SavingAccount extends Account {

    protected boolean firstWithdrawal;
    protected final double withdrawalFee;

    public SavingAccount(int accountNumber) {
        super(accountNumber, "Saving Account");
        interest = 0.01;
        firstWithdrawal = true;
        withdrawalFee = 0.02;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public boolean isFirstWithdrawal() {
        return firstWithdrawal;
    }

    public double getWithdrawalFee() {
        return withdrawalFee;
    }

    //</editor-fold>
    /**
     * Removes given amount from accounts saldo and adds a new transaction to
     * accounts transaction list.
     *
     * @param amount
     */
    @Override
    public void withdraw(double amount) {
        if (firstWithdrawal) {
            saldo -= amount;
            firstWithdrawal = false;
        } else {
            saldo -= amount * withdrawalFee + amount;
        }
        transactions.add(new Transaction(accountNumber, true, amount, saldo));
    }

    /**
     * Calculates interest of the account.
     *
     * @return
     */
    @Override
    public double calcInterest() {
        return Math.round((saldo * interest) * 100.0) / 100.0;
    }

    // TODO change this later
    @Override
    public String toString() {
        return "AccountID: " + accountNumber + ", Type: " + accountType
                + ", Interest: " + interest + "\nSaldo: " + String.format("%.2f", saldo);
    }

}
