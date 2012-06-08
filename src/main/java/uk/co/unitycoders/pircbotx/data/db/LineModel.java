/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.data.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class LineModel {
    private final Connection conn;
    private final PreparedStatement createLine;
    private final PreparedStatement readLines;

    public LineModel(Connection conn) throws Exception{
        this.conn = conn;
        buildTable();
        createLine = conn.prepareStatement("INSERT INTO lines VALUES(?)");
        readLines = conn.prepareStatement("SELECT * FROM lines");
    }

    private void buildTable() throws SQLException{
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS lines (nick string, name string)");
    }

    public void storeLine(String line) throws SQLException{
        createLine.clearParameters();
        createLine.setString(1, line);
        createLine.execute();
    }

    public List<String> getAllLines(){
        List<String> lines = new ArrayList<String>();
        try{
            ResultSet rs = readLines.executeQuery();
            while(rs.next()){
                lines.add(rs.getString(1));
            }
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return lines;
    }

}
