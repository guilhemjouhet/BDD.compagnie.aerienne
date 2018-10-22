/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
/**
 *
 * @author guilh_000
 */

public class Application {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
        
    public void readDataBase() throws Exception{
        try {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/mydb?useLegacyDatetimeCode=false&serverTimezone=America/New_York"
                            +"&user=sqluser&password=sqluser");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from Appareils");
            //writeResultSet(resultSet);
             while (resultSet.next()){
                String messages = "";
                int id = resultSet.getInt( "immatriculation" );
                String type = resultSet.getString( "type" );
                messages="l'avion immatriculé " + id + " est un " + type;
                System.out.println(messages);
        }
    }   
        catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {}
    }
    }
    
