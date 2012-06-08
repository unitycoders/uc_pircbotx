/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Java SQLite backed database driver
 */
public class DBConnection {
    private static Connection instance;

    //utiltiy class = private constructor
    private DBConnection() {
    }

    public static Connection getInstance() throws Exception{
        if(instance == null){
            Class.forName("org.sqlite.JDBC");
            instance = DriverManager.getConnection("jdbc:sqlite:bot.db");
        }

        return instance;
    }

    public static LineModel getLineModel() throws Exception{
        return new LineModel(getInstance());
    }


}
