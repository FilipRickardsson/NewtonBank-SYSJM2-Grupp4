package bank;

/**
 * A savingaccount in the bank
 *
 * @author Grupp 4
 */
public class SavingAccount extends Account {

    private final boolean firstWithdrawal;
    private final double withdrawalFee;

    /**
     * Constructor
     *
     * @param accountID
     * @param interest
     * @param firstWithDrawal
     * @param withdrawalFee
     * @param saldo
     */
    public SavingAccount(int accountID, double interest, boolean firstWithDrawal, double withdrawalFee, double saldo) {
        super(accountID, "Saving Account", saldo);
        this.interest = interest;
        this.firstWithdrawal = firstWithDrawal;
        this.withdrawalFee = withdrawalFee;
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
                + ", Interest: " + interest * 100 + "%" + "\nSaldo: " + String.format("%.2f", saldo);
    }

}
