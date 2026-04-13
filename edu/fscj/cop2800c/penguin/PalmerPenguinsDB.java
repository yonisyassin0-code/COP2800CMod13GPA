// PenguinRookeryDB.java
// D. Singletary
// 3/28/23
// Class for Penguin Rookery DB operations

package edu.fscj.cop2800c.penguin;

import java.sql.*;
import java.util.ArrayList;

public class PalmerPenguinsDB
{     
    public static void createDB(ArrayList<Penguin> penguins) {
        final String DB_NAME = "PalmerPenguins";
        final String CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        final String CONN_URL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;";
        final String SQL_DROP_TABLE = "DROP TABLE Penguin";
    
        try {
            Class.forName(CLASS_NAME);
    
            try (Connection con = DriverManager.getConnection(CONN_URL, "sa", "2547");
     Statement stmt = con.createStatement()) {
    
                try {
                    stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                    System.out.println("DB created");
                } catch (SQLException e) {
                    System.out.println("could not create DB, already exists");
                }
    
                // Switch context to the new DB
                stmt.executeUpdate("USE " + DB_NAME);
                
                // Drop table if it already exists
                    try {
                         stmt.executeUpdate("DROP TABLE Penguin");
                         System.out.println("Old table dropped");
                 }  catch (SQLException e) {
                      // ignore if it doesn't exist
                 }
    
                // Create table
                String createTable = "CREATE TABLE Penguin " +
                      "(SAMPLENUM smallint PRIMARY KEY NOT NULL," +
                      "CULMENLEN float NOT NULL," +
                      "CULMENDEPTH float NOT NULL," +
                      "BODYMASS smallint NOT NULL," +
                      "SEX char(1) NOT NULL," +
                      "SPECIES varchar(20) NOT NULL," +
                      "FLIPPERLEN float NOT NULL)";

                stmt.executeUpdate(createTable);
                System.out.println("Table created");
                
                
                
    
                // Insert records using batch with try-with-resources
                String insertQuery = "INSERT INTO Penguin (SAMPLENUM, CULMENLEN, CULMENDEPTH, " +
                                     "BODYMASS, SEX, SPECIES, FLIPPERLEN) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                    for (Penguin penguin : penguins) {
                        pstmt.setInt(1, penguin.getSampleNum());
                        pstmt.setDouble(2, penguin.getCulmenLength());
                        pstmt.setDouble(3, penguin.getCulmenDepth());
                        pstmt.setInt(4, (int) penguin.getBodyMass());

                        String sex = penguin.getSex();
                        pstmt.setString(5, (sex != null && !sex.isEmpty()) ? sex.substring(0, 1) : "?");
    
                        pstmt.setString(6, penguin.getSpecies().toString());
                        pstmt.setDouble(7, penguin.getFlipperLength());
    
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                    System.out.println("Data inserted");
                }
    
                // Query and print results using try-with-resources
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM Penguin")) {
                while (rs.next()) {
                   System.out.println(
                       rs.getInt("SAMPLENUM") + "," +
                       rs.getDouble("CULMENLEN") + "," +
                       rs.getDouble("CULMENDEPTH") + "," +
                       rs.getInt("BODYMASS") + "," +
                       rs.getString("SEX") + "," +
                       rs.getString("SPECIES") + "," +
                       rs.getDouble("FLIPPERLEN")
                     );
                  }

                }
    
                // Drop the table
                stmt.executeUpdate(SQL_DROP_TABLE);
                System.out.println("Penguin table dropped");
    
                try {
                    stmt.executeUpdate("DROP DATABASE " + DB_NAME + ";");
                    System.out.println("DB dropped");
                } catch (SQLException e) {
                    System.out.println("could not drop DB, in use");
                }
    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
