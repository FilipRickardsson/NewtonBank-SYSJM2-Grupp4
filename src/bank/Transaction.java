package bank;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private final int accountID;
    private final String date;
    private final String time;
    private final boolean withdrawal;
    private final double amount;
    private final double updatedBalance;

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
    Date currentDate = new Date();

    // TODO change constructor to retrieve date and time from system clock
    public Transaction(int accountID, String date, String time,
            boolean withdrawal, double amount, double updatedBalance) {
        this.accountID = accountID;
        this.date = df.format(currentDate); // Change this
        this.time = df1.format(currentDate); // Change this
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
    
//   Behövs denna ?    
//   public String inOrOut(){
//        if (withdrawal == true){
//            return "Out: ";
//        }
//        else
//            return "In: ";       
//    }
    @Override
    public String toString() {
        return date + " " + time + " " + withdrawal + amount + " Balance: " + updatedBalance;
    }
}
