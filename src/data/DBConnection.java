package data;

import bank.Account;
import bank.CreditAccount;
import bank.Customer;
import bank.SavingAccount;
import bank.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static DBConnection dbConnection;
    private Connection con;
    private Statement st;
    private PreparedStatement ps;
    private DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    private DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
    private Date currentDate = new Date();
    
    private DBConnection() {
        connectToDB();
    }

    public static DBConnection getDBConnection() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    private void connectToDB() {
        String url = "jdbc:mysql://localhost:3306/newtonbank?autoReconnect=true&useSSL=false";
        String user = "newtonbank";
        String password = "kaffekopp";
        
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList getCustomers() {
        ArrayList<Customer> customers = new ArrayList();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                System.out.println("1");
                customers.add(new Customer(rs.getString(2), rs.getLong(1)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }
    public Account getAccount(long ssn, int accountId){
        Account acc=null;
        try {
            ResultSet rs=st.executeQuery(String.format("SELECT * "
                           + "FROM savingAccount "
                           + "WHERE Account_accountId=%d",accountId));
            if(rs.next()){
                rs=st.executeQuery(String.format("SELECT "
                           + "accountId,"
                           + "AccountType_type,"
                           + "AccountType.interest,"
                           + "saldo "
                           + "withdrawalFee "
                           + "firstwithdrawal "
                           + "FROM Account "
                           + "JOIN AccountType "
                           + "ON AccountType_type=type "
                           + "JOIN SavingsAccount"
                           + "ON accountId=account_accountId"
                           + "WHERE accountId=%d"
                           + " AND Customer.ssn=%d", accountId,ssn));
            }else{
               rs=st.executeQuery(String.format(""
                           + "SELECT "
                           + "accountId,"
                           + "AccountType_type,"
                           + "AccountType.interest,"
                           + "saldo, "
                              + "creditInterest,"
                              + "creditLimit "
                           + "FROM Account "
                           + "JOIN AccountType "
                           + "ON AccountType_type=type"
                           + "WHERE accountId=%d"
                           + " AND Customer.ssn=%d", accountId,ssn)); 
            }
            
            while(rs.next()){
                if(rs.getString(2).equals("Saving Account")){
                    acc=new SavingAccount(rs.getInt(1),rs.getDouble(3),rs.getBoolean(6)
                       ,rs.getDouble(5),rs.getInt(4));
                }else{
                    acc=new CreditAccount(rs.getInt(1)
                       ,rs.getDouble(5),rs.getDouble(3),rs.getInt(6)
                       ,rs.getInt(4));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return acc;
    }
    public void deposit(int accountId, double amount){
        try {
            ps=con.prepareStatement(String.format("UPDATE Account "
                           + "SET saldo=saldo+%f "
                           + "WHERE accountId=%d",amount,accountId));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void withdraw(int accountId, double amount){
        try {
            ps=con.prepareStatement(String.format("UPDATE Account "
                           + "SET saldo=saldo-%f "
                           + "WHERE accountId=%d",amount,accountId));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addTransaction(int accountID, boolean withdrawal,
            double amount, double updatedBalance){
        try {
            ps=con.prepareStatement(String.format("INSERT INTO Transaction "
                           + "(date,time,withdrawal,amount,updatebalance,Account_accountId)"
                           + "VALUES (%s,&s,%b,%f,%f)", df,df1,withdrawal,amount,updatedBalance));
            //SELECT NOW() CURTIME() CURDATE() till datum&Tid
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public ArrayList getTransactions(int accountID){
        ArrayList<Transaction> transactionList= new ArrayList();
        try {
            ResultSet rs=st.executeQuery(String.format("SELECT * FROM Transaction WHERE "
                           + "accountId=%d",accountID));
            while(rs.next()){
                transactionList.add(new Transaction(rs.getInt(7),rs.getString(2),rs.getString(3)
                ,rs.getBoolean(4),rs.getDouble(5),rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       return transactionList; 
    }

}
