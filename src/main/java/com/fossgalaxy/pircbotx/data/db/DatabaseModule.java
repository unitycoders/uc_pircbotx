package com.fossgalaxy.pircbotx.data.db;

import com.fossgalaxy.pircbotx.types.Karma;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by webpigeon on 14/08/16.
 */
public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FactoidModel.class);
        bind(JoinModel.class);
        bind(KarmaModel.class);
    }

    @Provides
    Connection connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:config/bot.db");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
