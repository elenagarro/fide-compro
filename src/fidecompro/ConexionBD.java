package fidecompro;

import java.sql.*;

public class ConexionBD {
    private static final String URL  =
        "jdbc:mysql://localhost:3306/fidecompro?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "admin";
    private static final String PASS = "123";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Falta mysql-connector-j en Libraries.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(URL, USER, PASS);
        c.setAutoCommit(true); 
        try (Statement st = c.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT @@port, DATABASE()")) {
                if (rs.next()) {
                    System.out.println("[BD] URL JDBC: " + c.getMetaData().getURL());
                    System.out.println("[BD] Puerto    : " + rs.getInt(1));
                    System.out.println("[BD] Schema    : " + rs.getString(2));
                }
            }
        }
        return c;
    }
}
