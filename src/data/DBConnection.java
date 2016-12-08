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
            st.executeQuery("USE newtonbank");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList getCustomers() {
        ArrayList<Customer> customers = new ArrayList();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                customers.add(new Customer(rs.getString(2), rs.getLong(1)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }

    public Account getAccount(long ssn, int accountId) {
        Account acc = null;
        try {
            ResultSet rs = st.executeQuery(String.format("SELECT * "
                    + "FROM savingAccount "
                    + "WHERE Account_accountId=%d", accountId));
            if (rs.next()) {
                rs = st.executeQuery(String.format("SELECT "
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
                        + " AND Customer.ssn=%d", accountId, ssn));
            } else {
                rs = st.executeQuery(String.format(""
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
                        + " AND Customer.ssn=%d", accountId, ssn));
            }

            while (rs.next()) {
                if (rs.getString(2).equals("Saving Account")) {
                    acc = new SavingAccount(rs.getInt(1), rs.getDouble(3), rs.getBoolean(6), rs.getDouble(5), rs.getInt(4));
                } else {
                    acc = new CreditAccount(rs.getInt(1), rs.getDouble(5), rs.getDouble(3), rs.getInt(6), rs.getInt(4));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return acc;
    }

    public void deposit(int accountId, double amount) {
        try {
            ps = con.prepareStatement(String.format("UPDATE Account "
                    + "SET saldo=saldo+%f "
                    + "WHERE accountId=%d", amount, accountId));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void withdraw(int accountId, double amount) {
        try {
            ps = con.prepareStatement(String.format("UPDATE Account "
                    + "SET saldo=saldo-%f "
                    + "WHERE accountId=%d", amount, accountId));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addTransaction(Transaction transaction) {

        try {
            ps = con.prepareStatement(String.format("INSERT INTO Transaction "
                    + "(accountId,date,time,withdrawal,amount,updatebalance,Account_accountId)"
                    + "VALUES (%d,%s,&s,%b,%f,%f)", transaction.getAccountID(), transaction.getDate(), transaction.getTime(), transaction.isWithdrawal(), transaction.getAmount(), transaction.getUpdatedBalance()));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public ArrayList getTransactions(int accountID) {
        ArrayList<Transaction> transactionList = new ArrayList();
        try {
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM Transaction WHERE "
                    + "accountId=%d", accountID));
            while (rs.next()) {
                transactionList.add(new Transaction(rs.getInt(7), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(5), rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return transactionList;
    }

    public void addCustomer(Customer newCustomer) {
        try {
            ps = con.prepareStatement("INSERT INTO customer (ssn, name) VALUES (?, '?');");
            ps.setLong(1, newCustomer.getSsn());
            ps.setString(2, newCustomer.getName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeAccount(int accountId) {

        try {
            st.executeUpdate(String.format(""
                    + "DELETE FROM Account WHERE accountId = %d ", accountId));

            st.executeUpdate(String.format(""
                    + "DELETE FROM SavingAccount WHERE accountId = %d ", accountId));

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Customer getCustomer(long customerSsn) {
        Customer customer = null;
        try {
            ResultSet result = st.executeQuery("SELECT * FROM customer WHERE "
                    + "ssn = " + customerSsn);
            result.next();
            customer = new Customer(result.getString(2), result.getLong(1));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return customer;
    }

    public void changeCustomer(Customer customer, String newName) {
        try {
            ps = con.prepareStatement("UPDATE customer SET name = '?' WHERE ssn = ?;");
            ps.setLong(1, customer.getSsn());
            ps.setString(2, newName);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeCustomer(Customer customerToBeRemoved) {
        try {
            st.executeUpdate("DELETE FROM customer WHERE ssn = " + customerToBeRemoved.getSsn());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public int AddSavingsAccount(long ssn) {
        int accountNbr = -1;
        try {

            ps = con.prepareStatement("INSERT INTO Account (saldo, AccountType_type, Customer_ssn) VALUES (?, '?', ?);");
            ps.setDouble(1, 0);
            ps.setString(2, "SavingsAccount");
            ps.setLong(3, ssn);
            ps.executeUpdate();

            ResultSet result = st.executeQuery("SELECT MAX(accountId) FROM Account");
            result.next();
            accountNbr = result.getInt(1);

            ps = con.prepareStatement("INSERT INTO SavingAccount (Account_accountId, firstwithdrawal) VALUES (?, ?);");
            ps.setInt(1, accountNbr);
            ps.setBoolean(2, true);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return accountNbr;

    }

    public long getCustomerViaIndex(int index) {
        long customerSsn = 0;
        try {
            ResultSet rs = st.executeQuery("SELECT ssn FROM customer LIMIT " + index + ",1");
            while (rs.next()) {
                customerSsn = rs.getLong(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerSsn;
    }

    public int getAccountIdViaIndex(int index) {
        int accountId = 0;
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM customer LIMIT " + index + ",1");
            accountId = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accountId;
    }

    public int addCreditAccount(long ssn) {
        int accountNbr = -1;
        try {
            ps = con.prepareStatement("INSERT INTO Account (saldo, AccountType_type, Customer_ssn) VALUES (?, '?', ?);");
            ps.setDouble(1, 0);
            ps.setString(2, "CreditAccount");
            ps.setLong(3, ssn);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return accountNbr;
    }
    
   
}
