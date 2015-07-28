package db;

import java.sql.*;

public class DBManager {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:derby://localhost:1527/cinemangiare_db";

    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;
            sql = "";  // fare una query tra virgolette
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {  // risultati della query, forse è qui che si chiamano i Beans?
                int id = rs.getInt("id");   // qui si chiamano le funzioni degli oggetti dei bean e li si assegna un valore???
                System.out.print("ID: " + id); // e qui
            }
            //chiude connessioni e cose così
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }//end main
}//end FirstExample
