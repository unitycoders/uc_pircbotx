package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KarmaModel
{
	private final Connection conn;

	private final PreparedStatement newKarma;
	private final PreparedStatement getKarma;
	private final PreparedStatement incrementKarma;
	private final PreparedStatement decrementKarma;

	public KarmaModel(Connection conn) throws SQLException
	{
		this.conn = conn;
		buildTable();
		newKarma = conn.prepareStatement("INSERT INTO karma VALUES (?, 1)");
		getKarma = conn.prepareStatement("SELECT karma FROM karma WHERE target = ?");
		incrementKarma = conn.prepareStatement("UPDATE karma SET karma = karma + 1 WHERE target = ?");
		decrementKarma = conn.prepareStatement("UPDATE karma SET karma = karma - 1 WHERE target = ?");
	}

	private void buildTable() throws SQLException
	{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS karma (target STRING PRIMARY KEY, karma INTEGER)");
	}

	public int getKarma(String target)
	{
		try
		{
			getKarma.clearParameters();
			getKarma.setString(1, target);
			getKarma.execute();

			ResultSet rs = getKarma.getResultSet();
			return rs.getInt(1);
		} catch (SQLException ex)
		{
			// Probably not in the database yet, so return 0
			return 0;
		}
	}

	private void newKarma(String target) throws SQLException
	{
		newKarma.clearParameters();
		newKarma.setString(1, target);
		newKarma.execute();
	}

	public int incrementKarma(String target) throws SQLException
	{
		incrementKarma.clearParameters();
		incrementKarma.setString(1, target);
		int rows = incrementKarma.executeUpdate();

		if (rows == 0)
			newKarma(target);

		return getKarma(target);
	}

	public int decrementKarma(String target) throws SQLException
	{
		decrementKarma.clearParameters();
		decrementKarma.setString(1, target);
		decrementKarma.execute();

		return getKarma(target);
	}
}
