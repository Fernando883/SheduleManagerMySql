package persistence;

import java.sql.*;

/**
 * Class ConnectionDB
 * @author Group2
 */
public class ConnectionDB {
    private static Connection connection;
    private static Statement statementSQL;
    private static ResultSet result;
    private static ConnectionDB instance = null;
  
    /**
     * Class Constructor
     */
    public ConnectionDB() {
//        String url = "jdbc:oracle:thin:INFTEL15_5/INFTEL@olimpia.lcc.uma.es:1521:edgar";
        String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7281144?user=sql7281144&password=bpURq6G7zb";
        
        try {
//            String driver = "oracle.jdbc.driver.OracleDriver";
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            connection = DriverManager.getConnection(url);
            statementSQL = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println("Error loading the driver or connect to data base");
        }
    }
    
    /**
     * Method that creates a instance object of this class
     */
    private synchronized static void createInstance(){
        if(instance == null)
            instance = new ConnectionDB();
    }
    
    /**
     * Method that returns the Singleton instance
     * @return ConnectionDB
     */
    public static ConnectionDB getInstance(){
        if(instance == null) createInstance();
        return instance;
    }
    
    /**
     * Method that delete the singleton instance
     */
    public static void deleteInstance(){
        instance = null;
        closeConnection();
    }
    
    /**
     * Method that retuns the connection
     * @return Connection
     */
    public static Connection getConnection(){
        return connection;
    }
    
    /**
     * Method which gets ResultSet for a table
     * @param tabla
     * @return ResultSet
     */
    public ResultSet getTable(String table) {
        try {
            result = statementSQL.executeQuery("SELECT * FROM " + table);
        } catch (SQLException ex) {
            System.out.println("Error performing query");
        }
        
        return result;
    }
    
    /**
     * Method that shwos a row
     * @throws SQLException 
     */
    public void showRow() throws SQLException {
        int nColumnas = result.getMetaData().getColumnCount();
        
        for (int i = 1; i <= nColumnas; ++i) {
            System.out.print(result.getString(i) + " ");
        }
        
        System.out.println();
    }

    /**
     * Method that shows a table
     * @throws SQLException 
     */
    public void showTable() throws SQLException {
        while (result.next()) {
            System.out.println(result.getString("DNI") + " " + result.getString("PASS"));
        }
    }

    /**
     * Method that show a given table
     * @param table
     * @throws SQLException 
     */
    public void showTable2(String table) throws SQLException {
        result = getTable(table);
        
        while (result.next()) {
            showRow();
        }
    }

    /**
     * Method Connection begins with data base
     * @param user
     * @param passw
     * @return inciciado
     * @throws SQLException 
     */
    public boolean startSession(String user, String passw) throws SQLException {
        boolean started = false;
        
        result = statementSQL.executeQuery("SELECT * FROM user_u WHERE DNI = '" + user + "'");
        if (result != null) {
            while (result.next()) {
                String pass = result.getString("PASS");
                if (pass.equals(passw)) {
                    started = true;
                }
            }
        }
        
        return started;
    }
    
    /**
     * Static Method that closes connection
     */
    public static void closeConnection() {
        try {
            if (result != null) {
                result.close();
            }
            if (statementSQL != null) {
                statementSQL.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("The connection was closed successfully");
        } catch (SQLException ex1) {
            System.out.println("Error closing the connection");
        }
    }
}
