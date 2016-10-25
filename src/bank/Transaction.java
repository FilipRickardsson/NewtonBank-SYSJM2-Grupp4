package bank;

public class Transaction {

    private final int accountID;
    private final String date;
    private final String time;
    private final boolean withdrawal;
    private final double amount;
    private final double updatedBalance;

    // TODO change constructor to retrieve date and time from system clock
    public Transaction(int accountID, String date, String time,
            boolean withdrawal, double amount, double updatedBalance) {
        this.accountID = accountID;
        this.date = date; // Change this
        this.time = time; // Change this
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

    //</editor-fold>
    
    // TODO add toString method
}
