package bank;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A transaction created by an account
 *
 * @author Grupp 4
 */
public class Transaction {

    private final int accountID;
    private final String date;
    private final String time;
    private final boolean withdrawal;
    private final double amount;
    private final double updatedBalance;

    /**
     * Constructor
     *
     * @param accountID The unique account id
     * @param withdrawal true if a withdrawal was made
     * @param amount The amount to deposit/withdraw
     * @param updatedBalance The new balance of the account
     */
    public Transaction(int accountID, boolean withdrawal,
            double amount, double updatedBalance) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = new Date();

        this.accountID = accountID;
        this.date = df.format(currentDate);
        this.time = df1.format(currentDate);
        this.withdrawal = withdrawal;
        this.amount = amount;
        this.updatedBalance = updatedBalance;
    }

    public Transaction(int accountID, String date, String time, boolean withdrawal,
            double amount, double updatedBalance) {
        this.accountID = accountID;
        this.date = date;
        this.time = time;
        this.withdrawal = withdrawal;
        this.amount = amount;
        this.updatedBalance = updatedBalance;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public String getTime() {
        return time;
    }

    public int getAccountID() {
        return accountID;
    }

    public String getDate() {
        return date;
    }

    public boolean getTransactionType() {
        return withdrawal;
    }

    public double getAmount() {
        return amount;
    }

    public double getUpdatedBalance() {
        return updatedBalance;
    }

    public boolean isWithdrawal() {
        return withdrawal;
    }

    //</editor-fold>
    @Override
    public String toString() {
        if (withdrawal) {
            return date + " " + time + " " + " Out: " + String.format("%.2f", amount)
                    + " Balance: " + String.format("%.2f", updatedBalance);
        } else {
            return date + " " + time + " " + " In: " + String.format("%.2f", amount)
                    + " Balance: " + String.format("%.2f", updatedBalance);
        }
    }
}
