package bank;

public class Transaction {

    private int accountID;
    private String date;
    private String time;
    private String transactionType;
    private double amount;
    private double updatedBalance;

    public Transaction(int accountID, String date, String time, String transactionType, double amount, double updatedBalance) {
        this.accountID = accountID;
        this.date = date;
        this.time = time;
        this.transactionType = transactionType;
        this.amount = amount;
        this.updatedBalance = updatedBalance;
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    public String getTime() {
        return time;
    }


    public int getAccountID() {
        return accountID;
    }


    public String getDate() {
        return date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public double getUpdatedBalance() {
        return updatedBalance;
    }
    //</editor-fold>
}
