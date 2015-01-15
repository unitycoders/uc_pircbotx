package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import uk.co.unitycoders.pircbotx.types.Karma;

public class KarmaModel {

    private final Connection conn;

    private final PreparedStatement newKarma;
    private final PreparedStatement getKarma;
    private final PreparedStatement topKarma;
    private final PreparedStatement incrementKarma;
    private final PreparedStatement decrementKarma;

    public KarmaModel(Connection conn) throws SQLException {
        this.conn = conn;
        buildTable();
        newKarma = conn.prepareStatement("INSERT INTO karma (target) VALUES (?)");
        topKarma = conn.prepareStatement("SELECT target, karma FROM karma ORDER BY karma DESC LIMIT ?");
        getKarma = conn.prepareStatement("SELECT karma FROM karma WHERE target = ?");
        incrementKarma = conn.prepareStatement("UPDATE karma SET karma = karma + 1 WHERE target = ?");
        decrementKarma = conn.prepareStatement("UPDATE karma SET karma = karma - 1 WHERE target = ?");
    }

    private void buildTable() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS karma (target TEXT PRIMARY KEY, karma INTEGER DEFAULT 1)");
    }
    
    public Collection<Karma> getTopKarma(int limit) {
    	ArrayList<Karma> karmaList = new ArrayList<Karma>();
    	
    	try {
    		topKarma.clearParameters();
    		topKarma.setInt(1, limit);
    		ResultSet rs = topKarma.executeQuery();
    		
    		while(rs.next()) {
    			Karma karmaEntry = new Karma(rs.getString(1), rs.getInt(2));
    			karmaList.add(karmaEntry);
    		}
    		
    		rs.close();
    		return karmaList;
    	} catch (SQLException ex) {
    		return null;
    	}
    }

    public int getKarma(String target) {
        try {
            getKarma.clearParameters();
            getKarma.setString(1, target);
            getKarma.execute();

            ResultSet rs = getKarma.getResultSet();
            return rs.getInt(1);
        } catch (SQLException ex) {
            // Probably not in the database yet, so return 0
            return 0;
        }
    }

    private void newKarma(String target) throws SQLException {
        newKarma.clearParameters();
        newKarma.setString(1, target);
        newKarma.execute();
    }

    public int incrementKarma(String target) throws SQLException {
        incrementKarma.clearParameters();
        incrementKarma.setString(1, target);
        int rows = incrementKarma.executeUpdate();

        if (rows == 0) {
            newKarma(target);
        }

        return getKarma(target);
    }

    public int decrementKarma(String target) throws SQLException {
        decrementKarma.clearParameters();
        decrementKarma.setString(1, target);
        decrementKarma.execute();

        return getKarma(target);
    }
}
