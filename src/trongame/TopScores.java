/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * TopScores class which is a database with all necessary functionalities to interact 
 * @author v7i2jb
 */
public class TopScores {
    int topScores;
    Connection connection;
    PreparedStatement defaultStatement;
    PreparedStatement updateStatement;
    PreparedStatement insertStatement;
    
    /**
     * Statements initialize along the database connection
     * @param topScores
     * @throws java.sql.SQLException
     */
    public TopScores(int topScores) throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.topScores = topScores;
            String dbURL = "jdbc:mysql://localhost:3306/mydatabase";
            connection = DriverManager.getConnection(dbURL, "root","404048jZhainagul.");

            // Check if the table exists
            if (!isTableExists("TOPSCORES")) {
                createTable();
            }
            String insertQuery = "INSERT INTO TOPSCORES (NAME, SCORE) VALUES (?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
            String updatequery = "UPDATE TOPSCORES SET SCORE=(?) WHERE NAME=(?)";
            updateStatement = connection.prepareStatement(updatequery);
        } catch (SQLException e){
            System.out.println("An error occurred. User name or password is invalid!");
        } catch (ClassNotFoundException ex) {
            System.out.println("A fatal error occurred");
        }
    }
/**
* Returning an array of TopScore values from the database, contains a safety in case there is no database
     * @return 
     * @throws java.sql.SQLException
*/
public ArrayList<TopScore> getTopScores() throws SQLException {
    String query = "SELECT * FROM TOPSCORES LIMIT 10";
    ArrayList<TopScore> topScores = new ArrayList<>();
    Statement statement = connection.createStatement();
    ResultSet results;

    try {
        results = statement.executeQuery(query);
    } catch (SQLException e) {
        // If the table doesn't exist, create it and try again
        createTable();
        results = statement.executeQuery(query);
    }

    while (results.next()){
        String name = results.getString("NAME");
        int score = results.getInt("SCORE");
        topScores.add(new TopScore(name, score));
    }

    sortTopScores(topScores);
    return topScores;
}

    /**
    * Function to increase the score, dependent on a helper function updateScores()
    * @param checkName
    * @throws SQLException
    */
    public void increaseScore(String checkName) throws SQLException {
        String query = "SELECT * FROM TOPSCORES";
        
        Statement statement = connection.createStatement();
        ResultSet results;
        try{
            results = statement.executeQuery(query);
        }
        catch(SQLException e){
            defaultStatement.executeUpdate();
            results = statement.executeQuery(query);
        }
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            if(checkName.equals(name)){
                updateScores(name,score);
            }
        }
    }

    /**
    * Function to insert the TopScore into the database, dependent on helper function insertScore()
    * @param name
    * @param score
    * @throws SQLException
    */
    public void putTopScore(String name, int score) throws SQLException {
        insertScore(name, score);
    }

    /**
    * Sort the top scores in descending order.
    * @param topScores 
    */
    private void sortTopScores(ArrayList<TopScore> topScores) {
        Collections.sort(topScores, new Comparator<TopScore>() {
            @Override
            public int compare(TopScore t, TopScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }

    /**
    * Helper function for putTopScore, sets the insertStatement. Contains a safety net
    * @param name
    * @param score
    * @throws SQLException
    */
    private void insertScore(String name, int score) throws SQLException {
        try{
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.executeUpdate();
        }
        catch(SQLException e){
            defaultStatement.executeUpdate();
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.executeUpdate();
        }
        
    }
 
    /**
    * Helper function for increaseScore(), sets the updateStatement. Contains a safety net
    * @param name
    * @param score
    * @throws SQLException
    */
    private void updateScores(String name,int score) throws SQLException {
        try{
            updateStatement.setInt(1, score+1);
            updateStatement.setString(2, name);
            updateStatement.executeUpdate();
        }
        catch(SQLException e){
            defaultStatement.executeUpdate();
            updateStatement.setInt(1, score+1);
            updateStatement.setString(2, name);
            updateStatement.executeUpdate();
        }
    }

    private boolean isTableExists(String tableName) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSet = metadata.getTables(null, null, tableName, null);
        return resultSet.next();
    }

    private void createTable() throws SQLException {
        String createQuery = "CREATE TABLE TOPSCORES (Id int(20) primary key auto_increment, NAME varchar(200) not null, SCORE int(20))";
        defaultStatement = connection.prepareStatement(createQuery);
        defaultStatement.executeUpdate();
    }
}
