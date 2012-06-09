/**
 * Copyright © 2012 Joseph Walton-Rivers <bruce@bcowan.me.uk>
 * Copyright © 2012 Bruce Cowan <bruce@bcowan.me.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with uc_PircBotX.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.User;

import uk.co.unitycoders.pircbotx.types.Lart;

/**
 * Stores a load of larts.
 * 
 * @author Joseph Walton-Rivers
 * @author Bruce Cowan
 */
public class LartModel
{
	private final Connection conn;
	private final PreparedStatement createLart;
	private final PreparedStatement readLarts;
	private final PreparedStatement deleteLart;
	private final PreparedStatement specificLart;
	private final PreparedStatement randomLart;

	// FIXME sometimes these columns are 1,2,3; other times 2,3,4
	private final int CHANNEL_COLUMN = 1;
	private final int NICK_COLUMN = 2;
	private final int PATTERN_COLUMN = 3;

	public LartModel(Connection conn) throws Exception
	{
		this.conn = conn;

		buildTable();
		this.createLart = conn.prepareStatement("INSERT INTO larts VALUES(null, ?, ?, ?)");
		this.readLarts = conn.prepareStatement("SELECT * FROM larts");
		this.deleteLart = conn.prepareStatement("DELETE FROM larts WHERE id = ?");
		this.specificLart = conn.prepareStatement("SELECT * FROM larts WHERE id = ?");
		this.randomLart = conn.prepareStatement("SELECT * FROM larts ORDER BY RANDOM() LIMIT 1");
	}

	private void buildTable() throws SQLException
	{
		Statement stmt = this.conn.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS larts" +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT, channel STRING, nick STRING, pattern STRING)");
	}

	public int storeLart(Channel channel, User user, String pattern) throws Exception
	{
		if(!pattern.contains("$who"))
			throw new IllegalArgumentException("No $who section found");

		createLart.clearParameters();
		createLart.setString(CHANNEL_COLUMN, channel.getName());
		createLart.setString(NICK_COLUMN, user.getNick());
		createLart.setString(PATTERN_COLUMN, pattern);
		createLart.execute();

		// FIXME doesn't return the right row id
		ResultSet rs = createLart.getGeneratedKeys();
		return rs.getInt(1);
	}

	public void deleteLart(int id) throws Exception
	{
		deleteLart.clearParameters();
		deleteLart.setInt(1, id);
		deleteLart.execute();
	}

	private Lart buildLart(ResultSet rs) throws SQLException
	{
		String channel = rs.getString(2);
		String nick = rs.getString(3);
		String pattern = rs.getString(4);
		return new Lart(channel, nick, pattern);
	}

	public Lart getLart(int id) throws Exception
	{
		specificLart.clearParameters();
		specificLart.setInt(1, id);
		specificLart.execute();

		ResultSet rs = specificLart.getResultSet();
		return buildLart(rs);
	}

	public Lart getRandomLart() throws Exception
	{
		ResultSet rs = randomLart.executeQuery();
		return buildLart(rs);
	}
	
	public List<Lart> getAllLarts()
	{
		List<Lart> larts = new ArrayList<Lart>();

		try
		{
			ResultSet rs = readLarts.executeQuery();
			while(rs.next())
			{
				String channel = rs.getString(1);
				String nick = rs.getString(2);
				String pattern = rs.getString(3);

				Lart lart = new Lart(channel, nick, pattern);
				larts.add(lart);
			}
			rs.close();
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}

		return larts;
	}
}
