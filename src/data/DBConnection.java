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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connects, Retrieves, Updates and Deletes data from the database Designed with
 * the designpattern Singelton
 *
 * @author Grupp 4
 */
public class DBConnection {

    private static DBConnection dbConnection;
    private Connection con;
    private Statement st;
    private PreparedStatement ps;

    private DBConnection() {
        connectToDB();
    }

    /**
     * Returns this Singelton object
     *
     * @return DBConnection object
     */
    public static DBConnection getDBConnection() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    /**
     * Connects to the database
     */
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

    /**
     * Retrieves all customers from the database
     *
     * @return ArrayList of customers
     */
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

    /**
     * Retrieves a specific account from the database
     *
     * @param ssn Customer the account belongs to
     * @param accountId The id of the account
     * @return The retrieved account or null if not found
     */
    public Account getAccount(long ssn, int accountId) {
        Account acc = null;
        try {
            ResultSet rs = st.executeQuery(String.format(""
                    + "SELECT * "
                    + "FROM savingAccount "
                    + "WHERE Account_accountId = %d", accountId));
            if (rs.next()) {
                rs = st.executeQuery(String.format(""
                        + "SELECT accountId, AccountType_type, AccountType.interest,"
                        + "saldo, withdrawalFee, firstwithdrawal "
                        + "FROM Account "
                        + "JOIN AccountType "
                        + "ON AccountType_type = type "
                        + "JOIN SavingAccount "
                        + "ON accountId = account_accountId "
                        + "WHERE accountId = %d "
                        + "AND Customer_ssn = %d", accountId, ssn));
            } else {
                rs = st.executeQuery(String.format(""
                        + "SELECT accountId, AccountType_type, AccountType.interest, "
                        + "saldo, creditInterest, creditLimit "
                        + "FROM Account "
                        + "JOIN AccountType "
                        + "ON AccountType_type = type "
                        + "WHERE accountId = %d "
                        + "AND Customer_ssn = %d", accountId, ssn));
            }
            while (rs.next()) {
                if (rs.getString(2).equals("Savings Account")) {
                    acc = new SavingAccount(rs.getInt(1), rs.getDouble(3), rs.getBoolean(6), rs.getDouble(5), rs.getDouble(4));
                } else {
                    acc = new CreditAccount(rs.getInt(1), rs.getDouble(5), rs.getDouble(3), rs.getInt(6), rs.getDouble(4));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return acc;
    }

    /**
     * Deposits an amount to an account
     *
     * @param accountId The account to deposit to
     * @param amount The amount to deposit
     */
    public void deposit(int accountId, double amount) {
        try {
            st.executeUpdate(""
                    + "UPDATE account "
                    + "SET saldo = saldo+" + amount + ""
                    + "WHERE accountid = " + accountId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Withdraws an amount from an account
     *
     * @param accountId The account to withdraw from
     * @param amount The amount to withdraw
     */
    public void withdraw(int accountId, double amount) {
        try {
            st.executeUpdate(""
                    + "UPDATE account "
                    + "SET saldo = saldo-" + amount + ""
                    + "WHERE accountid = " + accountId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Updates the values in the database after a first withdrawal was made
     *
     * @param accountId The account to update
     */
    public void updateFirstWithdrawal(int accountId) {
        try {
            st.executeUpdate(""
                    + "UPDATE savingaccount "
                    + "SET firstwithdrawal = false "
                    + "WHERE account_accountid =  " + accountId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Adds a transaction to the database
     *
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        try {
            st.executeUpdate(""
                    + "INSERT INTO Transaction "
                    + "(date,time,withdrawal,amount,updatedbalance,Account_accountId) "
                    + "VALUES ('" + transaction.getDate() + "','" + transaction.getTime() + "'," + transaction.isWithdrawal() + "," + transaction.getAmount() + "," + transaction.getUpdatedBalance() + "," + transaction.getAccountID() + ")");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Retrieves all the transactions for a specific account
     *
     * @param accountID The specified account
     * @return ArrayList with Transaction objects
     */
    public ArrayList getTransactions(int accountID) {
        ArrayList<Transaction> transactionList = new ArrayList();
        try {
            ResultSet rs = st.executeQuery(String.format(""
                    + "SELECT * "
                    + "FROM Transaction WHERE "
                    + "Account_accountId=%d", accountID));
            while (rs.next()) {
                transactionList.add(new Transaction(rs.getInt(7), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(5), rs.getDouble(6)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return transactionList;
    }

    /**
     * Adds a new customer to the database
     *
     * @param newCustomer The customer to add
     */
    public void addCustomer(Customer newCustomer) {
        try {
            ps = con.prepareStatement("INSERT INTO customer (ssn, name) VALUES (?, ?);");
            ps.setLong(1, newCustomer.getSsn());
            ps.setString(2, newCustomer.getName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Deletes an account from the database
     *
     * @param accountId Id of the account to delete
     */
    public void closeAccount(int accountId) {
        try {
            st.executeUpdate(""
                    + "DELETE FROM SavingAccount "
                    + "WHERE Account_accountId = " + accountId);
            st.executeUpdate(""
                    + "DELETE FROM Account "
                    + "WHERE accountId = " + accountId);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Retrieves a specific customer
     *
     * @param customerSsn SSN of the customer
     * @return A Customer object
     */
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

    /**
     * Updates the name of an customer
     *
     * @param customer The customer to update
     * @param newName The customers new name
     */
    public void changeCustomer(Customer customer, String newName) {
        try {
            ps = con.prepareStatement("UPDATE customer SET name = ? WHERE ssn = ?;");
            ps.setLong(2, customer.getSsn());
            ps.setString(1, newName);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Removes a customer from the database
     *
     * @param customerToBeRemoved
     */
    public void removeCustomer(Customer customerToBeRemoved) {
        try {
            st.executeUpdate("DELETE FROM customer WHERE ssn = " + customerToBeRemoved.getSsn());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Retrieves all the accounts for a specific customer
     *
     * @param ssn SSN of the customer
     * @return ArrayList of Account-objects
     */
    public ArrayList getAccounts(long ssn) {
        ArrayList<Account> accounts = new ArrayList();
        try {
            ResultSet rs = st.executeQuery(String.format(""
                    + "SELECT accountId, AccountType_type, AccountType.interest,"
                    + "saldo, withdrawalFee, firstwithdrawal "
                    + "FROM Account "
                    + "JOIN AccountType "
                    + "ON AccountType_type = type "
                    + "JOIN SavingAccount "
                    + "ON accountId = account_accountId "
                    + "WHERE Customer_ssn = %d "
                    + "AND AccountType_type = 'Savings Account' ", ssn));
            while (rs.next()) {
                accounts.add(new SavingAccount(rs.getInt(1), rs.getDouble(3), rs.getBoolean(6), rs.getDouble(5), rs.getDouble(4)));
            }
            rs = st.executeQuery(String.format(""
                    + "SELECT accountId, AccountType_type, AccountType.interest, "
                    + "saldo, creditInterest, creditLimit "
                    + "FROM Account "
                    + "JOIN AccountType "
                    + "ON AccountType_type = type "
                    + "WHERE Customer_ssn = %d "
                    + "AND AccountType_type = 'Credit Account' ", ssn));

            while (rs.next()) {
                accounts.add(new CreditAccount(rs.getInt(1), rs.getDouble(5), rs.getDouble(3), rs.getInt(6), rs.getDouble(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return accounts;
    }

    /**
     * Adds a savingsaccount to the database to a specific customer
     *
     * @param ssn SSN of the customer
     * @return accountID of the newly added account
     */
    public int addSavingsAccount(long ssn) {
        int accountNbr = -1;
        try {
            ps = con.prepareStatement("INSERT INTO Account (saldo, AccountType_type, Customer_ssn) VALUES (?, ?, ?);");
            ps.setDouble(1, 0);
            ps.setString(2, "Savings Account");
            ps.setLong(3, ssn);
            ps.executeUpdate();

            ResultSet result = st.executeQuery("SELECT MAX(accountId) FROM Account");
            result.next();
            accountNbr = result.getInt(1);

            ps = con.prepareStatement("INSERT INTO SavingAccount VALUES (?, ?);");
            ps.setInt(1, accountNbr);
            ps.setBoolean(2, true);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return accountNbr;
    }

    /**
     * Retrieves a customer ssn based on selected index
     *
     * @param index The selected index
     * @return SSN of the customer
     */
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

    /**
     * Retrieves an accountId based on selected index
     *
     * @param ssn SSN of the customer
     * @param index Selected index
     * @return Id of the account
     */
    public int getAccountIdViaIndex(long ssn, int index) {
        int accountId = 0;
        try {
            ResultSet rs = st.executeQuery(""
                    + "SELECT accountId FROM account "
                    + "WHERE customer_ssn = " + ssn + " LIMIT " + index + ",1");
            while (rs.next()) {
                accountId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return accountId;
    }

    /**
     * Adds a creditaccount to the database to a specific customer
     *
     * @param ssn SSN of the customer
     * @return accountID of the newly added account
     */
    public int addCreditAccount(long ssn) {
        int accountNbr = -1;
        try {
            ps = con.prepareStatement("INSERT INTO Account (saldo,AccountType_type,Customer_ssn) VALUES (?, ?, ?);");
            ps.setDouble(1, 0);
            ps.setString(2, "Credit Account");
            ps.setLong(3, ssn);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return accountNbr;
    }

}
