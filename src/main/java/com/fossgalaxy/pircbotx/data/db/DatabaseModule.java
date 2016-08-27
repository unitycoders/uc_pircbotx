package com.fossgalaxy.pircbotx.data.db;

import com.fossgalaxy.pircbotx.commands.factoid.FactoidModel;
import com.fossgalaxy.pircbotx.commands.joins.JoinModel;
import com.fossgalaxy.pircbotx.commands.karma.KarmaModel;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by webpigeon on 14/08/16.
 */
public class DatabaseModule extends AbstractModule {
    private final static Logger LOG = LoggerFactory.getLogger(DatabaseModule.class);

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
        } catch (SQLException | ClassNotFoundException ex) {
            LOG.error("unable to connect to database", ex);
            return null;
        }
    }

}
