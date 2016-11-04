package bank;

/**
 * A savingaccount in the bank
 *
 * @author Grupp 4
 */
public class SavingAccount extends Account {

    private boolean firstWithdrawal;
    private final double withdrawalFee;

    /**
     * Constructor
     * @param accountID The unique account id
     */
    public SavingAccount(int accountID) {
        super(accountID, "Saving Account");
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
     * Withdraws given amount from account saldo and adds a new transaction to
     * accounts transaction list.
     *
     * @param amount The amount to withdraw
     */
    @Override
    public void withdraw(double amount) {
        if (firstWithdrawal) {
            saldo -= amount;
            firstWithdrawal = false;
        } else {
            saldo -= amount * withdrawalFee + amount;
        }
        transactions.add(new Transaction(accountID, true, amount, saldo));
    }

    /**
     * Calculates interest of the account.
     *
     * @return The interest
     */
    @Override
    public double calcInterest() {
        return Math.round((saldo * interest) * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "AccountID: " + accountID + ", Type: " + accountType
                + ", Interest: " + interest + "\nSaldo: " + String.format("%.2f", saldo);
    }

}
