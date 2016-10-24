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
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(double updatedBalance) {
        this.updatedBalance = updatedBalance;
    }

}
