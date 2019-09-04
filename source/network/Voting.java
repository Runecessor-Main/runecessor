package network; // dont forget to change packaging ^-^

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import game.content.miscellaneous.Announcement;
import game.content.starter.GameMode;
import game.item.ItemAssistant;
import game.player.Player;


public class Voting implements Runnable {

	public static final String HOST = "80.87.203.19";
	public static final String USER = "user8231_manager";
	public static final String PASS = "elkingbro123";
	public static final String DATABASE = "user8231_vote";

	private Player player;
	private Connection conn;
	private Statement stmt;

	public Voting(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		try {
			if (!connect(HOST, DATABASE, USER, PASS)) {
				return;
			}

			String name = player.getPlayerName().replace(" ", "_");
			ResultSet rs = executeQuery("SELECT * FROM fx_votes WHERE username='"+name+"' AND claimed=0 AND callback_date IS NOT NULL");

			while (rs.next()) {
				String timestamp = rs.getTimestamp("callback_date").toString();
				String ipAddress = rs.getString("ip_address");
				int siteId = rs.getInt("site_id");


				
				

				/*
				 * if (player.playerRights == 3) { rs.updateInt("claimed", 1); // do not delete
				 * otherwise they can reclaim! rs.updateRow(); Announcement.announce(
				 * "<col=FFFF00><shad=0>[<img=2>News]</shad></col> " +
				 * GameMode.getGameModeName(player) +
				 * " has voted and received Vote tickets from Voting Support us ::vote");
				 * 
				 * 
				 * ItemAssistant.addItem(player, 4067, 2); player.getPA().
				 * sendMessage("More extra 2 ticket for donators Thanks for support us"); } else
				 * if (player.playerRights == 4) { rs.updateInt("claimed", 1); // do not delete
				 * otherwise they can reclaim! rs.updateRow(); Announcement.announce(
				 * "<col=FFFF00><shad=0>[<img=2>News]</shad></col> " +
				 * GameMode.getGameModeName(player) +
				 * " has voted and received Vote tickets from Voting Support us ::vote");
				 * 
				 * 
				 * ItemAssistant.addItem(player, 4067, 4); player.getPA().
				 * sendMessage("More extra 4 ticket for donators Thanks for support us"); } else
				 * if (player.playerRights == 5) { rs.updateInt("claimed", 1); // do not delete
				 * otherwise they can reclaim! rs.updateRow(); Announcement.announce(
				 * "<col=FFFF00><shad=0>[<img=2>News]</shad></col> " +
				 * GameMode.getGameModeName(player) +
				 * " has voted and received Vote tickets from Voting Support us ::vote");
				 * 
				 * 
				 * ItemAssistant.addItem(player, 4067, 6); player.getPA().
				 * sendMessage("More extra 6 ticket for donators Thanks for support us"); } else
				 * if (player.playerRights == 6) { rs.updateInt("claimed", 1); // do not delete
				 * otherwise they can reclaim! rs.updateRow(); Announcement.announce(
				 * "<col=FFFF00><shad=0>[<img=2>News]</shad></col> " +
				 * GameMode.getGameModeName(player) +
				 * " has voted and received Vote tickets from Voting Support us ::vote");
				 * 
				 * 
				 * ItemAssistant.addItem(player, 4067, 8); player.getPA().
				 * sendMessage("More extra 8 ticket for donators Thanks for support us"); } else
				 * if (player.playerRights == 22) { rs.updateInt("claimed", 1); // do not delete
				 * otherwise they can reclaim! rs.updateRow(); Announcement.announce(
				 * "<col=FFFF00><shad=0>[<img=2>News]</shad></col> " +
				 * GameMode.getGameModeName(player) +
				 * " has voted and received Vote tickets from Voting Support us ::vote");
				 * 
				 * 
				 * ItemAssistant.addItem(player, 4067, 10); player.getPA().
				 * sendMessage("More extra 10 ticket for donators Thanks for support us"); }
				 */
				ItemAssistant.addItem(player, 4067, 1);
				System.out.println("[Manager] Vote claimed by "+name+". (sid: "+siteId+", ip: "+ipAddress+", time: "+timestamp+")");

				rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
				rs.updateRow();
			}

			destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean connect(String host, String database, String user, String pass) {
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, pass);
			return true;
		} catch (SQLException e) {
			System.out.println("Failing connecting to database!");
			return false;
		}
	}

	public void destroy() {
        try {
    		conn.close();
        	conn = null;
        	if (stmt != null) {
    			stmt.close();
        		stmt = null;
        	}
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	public int executeUpdate(String query) {
        try {
        	this.stmt = this.conn.createStatement(1005, 1008);
            int results = stmt.executeUpdate(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

	public ResultSet executeQuery(String query) {
        try {
        	this.stmt = this.conn.createStatement(1005, 1008);
            ResultSet results = stmt.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
