package zitate;

import java.sql.*;


public class MySQL {

    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String pw = "";
    String dbName = "quotes_db";

    /**
     * Creates the database if it does not exist.
     */
    public void createDatabase() {
        try (Connection conn = DriverManager.getConnection(url,user,pw);
             Statement stmt = conn.createStatement()) { // Use Statement for DDL operations

            String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
            stmt.executeUpdate(sql);
            System.out.println("Database '" + dbName + "' created successfully (or already exists).");

        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    /**
     * Creates the zitate table if it does not exist.
     *
     */
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Zitate (" +
                "Id INT AUTO_INCREMENT PRIMARY KEY," +
                "Person VARCHAR(255) NOT NULL," +
                "Text VARCHAR(1000) NOT NULL" +
                ")";

        try (Connection conn = DriverManager.getConnection(url + dbName, user, pw);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Table 'Zitate' created successfully (or already exists).");

        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    /**
     * Initializes the database and table if it does not already exist.
     */
    public void init_DatabaseAndTable() {
        createDatabase();
        createTable();
    }

    public void addQuote(String text, String person) {
        String sql = "INSERT INTO Zitate (Text, Person) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url + dbName, user, pw);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, text);
            pstmt.setString(2, person);
            pstmt.executeUpdate();
            System.out.println("Quote added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding quote: " + e.getMessage());
        }
    }

    public void listAllQuotes() {
        String sql = "SELECT * FROM Zitate";

        try (Connection conn = DriverManager.getConnection(url + dbName, user, pw);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("Id");
                String person = rs.getString("Person");
                String text = rs.getString("Text");
                System.out.println("\n-> ID: " + id + "\n\tPerson: " + person + "\n\tQuote: " + text);
            }

        } catch (SQLException e) {
            System.err.println("Error listing quotes: " + e.getMessage());
        }
    }

    public void reverseQuote(String quote){
        String sql = "SELECT * FROM Zitate WHERE Text LIKE ?";

        try (Connection conn = DriverManager.getConnection(url + dbName, user, pw);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%"+quote.toLowerCase()+"%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Original Quote: " + rs.getString("Text"));
                String reversedQuote = new StringBuilder(rs.getString("Text")).reverse().toString();
                System.out.println("Reversed Quote: " + reversedQuote);
            } else {
                System.out.println("Quote not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error reversing quote: " + e.getMessage());
        }
    }

}
