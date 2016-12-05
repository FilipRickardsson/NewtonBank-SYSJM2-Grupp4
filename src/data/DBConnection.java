package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private DBConnection dbConnection;
    private Connection con;
    private Statement st;

    private DBConnection() {
        connectToDB();
    }

    public DBConnection getDBConnection() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    private void connectToDB() {
        String url = "jdbc:mysql://localhost:3306/sys?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "root";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            st.executeQuery("USE newtonbank");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
