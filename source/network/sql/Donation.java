package network.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import game.item.ItemAssistant;
import game.player.Player;



/**
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Donation(player)).start();
 */
public class Donation implements Runnable {

	public static final String HOST = "136.243.101.233"; // website ip address
	public static final String USER = "ionxvebe_store";
	public static final String PASS = "7.guHqZM&Udl";
	public static final String DATABASE = "ionxvebe_store";

	private Player player;
	private Connection conn;
	private Statement stmt;

	/**
	 * The constructor
	 * @param player
	 */
	public Donation(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		try {
			if (!connect(HOST, DATABASE, USER, PASS)) {
				return;
			}

			String name = player.getPlayerName().replace("_", " ");
			ResultSet rs = executeQuery("SELECT * FROM payments WHERE player_name='"+name+"' AND status='Completed' AND claimed=0");

			while (rs.next()) {
				int item_number = rs.getInt("item_number");
				double paid = rs.getDouble("amount");
				int quantity = rs.getInt("quantity");

				switch (item_number) {// add products according to their ID in the ACP

				case 10: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 100);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 100 Donator Token ::donate");
					
					break;
				case 11: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 200);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 200 Donator Token ::donate");
					
					break;
				case 17: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 300);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 300 Donator Token ::donate");
					
					break;
				case 19: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 400);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 400 Donator Token ::donate");
					
					break;
				case 20: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 500);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 500 Donator Token ::donate");
					
					break;
				case 21: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 600);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 600 Donator Token ::donate");
					
					break;
				case 22: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 700);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 700 Donator Token ::donate");
					
					break;
				case 23: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 800);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 800 Donator Token ::donate");
					
					break;
				case 24: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 900);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 900 Donator Token ::donate");
					
					break;
				case 25: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 7478, 1000);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with 1000 Donator Token ::donate");
					
					break;
					
					/**
					 * Pets
					 */
					
				case 26: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 16020, 1);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with Yoshi pet ::donate");
					
					break;
				case 27: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 16038, 1);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with Donator pet ::donate");
					
					break;
				case 28: // example
					ItemAssistant.addItemToInventoryOrDrop(player, 16147, 1);
					player.getPA().sendMessage("Thank you for Support us.");
					player.getPA().announce(" "+player.getPlayerName()+ " Just Support Runecessor with Fairy Father pet ::donate");
					
					break;
				}

				rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
				rs.updateRow();
			}

			destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param host the host ip address or url
	 * @param database the name of the database
	 * @param user the user attached to the database
	 * @param pass the users password
	 * @return true if connected
	 */
	public boolean connect(String host, String database, String user, String pass) {
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, pass);
			return true;
		} catch (SQLException e) {
			System.out.println("Failing connecting to database!");
			return false;
		}
	}

	/**
	 * Disconnects from the MySQL server and destroy the connection
	 * and statement instances
	 */
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

	/**
	 * Executes an update query on the database
	 * @param query
	 * @see {@link Statement#executeUpdate}
	 */
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

	/**
	 * Executres a query on the database
	 * @param query
	 * @see {@link Statement#executeQuery(String)}
	 * @return the results, never null
	 */
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
