package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by webpigeon on 16/12/13.
 */
public class FactoidModel {
    private Connection connection;

    private final PreparedStatement createStmt;
    private final PreparedStatement readStmt;
    private final PreparedStatement updateStmt;
    private final PreparedStatement deleteStmt;

    public FactoidModel(Connection connection) throws SQLException {
        this.connection = connection;
        buildTable();

        createStmt = connection.prepareStatement("INSERT INTO factoid VALUES (?,?);");
        readStmt = connection.prepareStatement("SELECT body FROM factoid WHERE name=?;");
        updateStmt = connection.prepareStatement("UPDATE factoid SET body=? WHERE name=?");
        deleteStmt = connection.prepareStatement("DELETE FROM factoid WHERE name=?");
    }

    private void buildTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS factoid (name TEXT PRIMARY KEY, body TEXT)");
    }

    public boolean addFactoid(String factoid, String text) throws SQLException {
        createStmt.clearParameters();
        createStmt.setString(1, factoid);
        createStmt.setString(2, text);
        return createStmt.execute();
    }

    public String getFactoid(String factoid) throws SQLException {
        readStmt.clearParameters();
        readStmt.setString(1, factoid);

        ResultSet rs = readStmt.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }

        return null;
    }

    public boolean editFactoid(String factoid, String text) throws SQLException {
        updateStmt.clearParameters();
        updateStmt.setString(1, factoid);
        updateStmt.setString(2, text);
        return updateStmt.executeUpdate() == 1;
    }

    public boolean deleteFactoid(String factoid) throws SQLException {
        deleteStmt.clearParameters();
        deleteStmt.setString(1, factoid);
        return deleteStmt.execute();
    }

}
