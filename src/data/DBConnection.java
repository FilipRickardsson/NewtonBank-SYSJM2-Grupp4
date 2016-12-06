package data;

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
    
    public Customer getCustomer (long customerSsn) {
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
    
    public void changeCustomer (Customer customer, String newName) {
        try {
            ps = con.prepareStatement("UPDATE customer SET name = '?' WHERE ssn = ?;");
            ps.setLong(1, customer.getSsn());
            ps.setString(2, newName);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void removeCustomer (Customer customerToBeRemoved) {
        try {
            st.executeUpdate("DELETE FROM customer WHERE ssn = " + customerToBeRemoved.getSsn());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    
    

}
