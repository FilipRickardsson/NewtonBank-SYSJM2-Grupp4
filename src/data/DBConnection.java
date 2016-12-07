package data;

import bank.Account;
import bank.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    public void closeAccount(int accountId){
        
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

    public Customer searchForCustomer(long ssn) {

        Customer customer = null;
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM customer where ssn = '" + ssn + "';");
            customer = new Customer(rs.getString(2), rs.getLong(1));

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }

    public long getCustomerViaIndex(int index) {
        long customerSsn = 0;
        try {

            ResultSet rs = st.executeQuery("SELECT ssn FROM customer LIMIT " + customerSsn + ",1");
            customerSsn = rs.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerSsn;
    }

    public int getAccountIdViaIndex(int index) {
        int accountId = 0;
        try {

            ResultSet rs = st.executeQuery("SELECT * FROM customer LIMIT " + accountId + ",1");
            accountId = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accountId;
    }
}
