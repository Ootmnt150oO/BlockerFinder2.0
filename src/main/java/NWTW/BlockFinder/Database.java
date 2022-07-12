package NWTW.BlockFinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cn.nukkit.block.Block;

public class Database {
	private Connection connection;
	private String path;
	private Loader loader;
	public Database(Loader loader, String path) {
		this.path = path;
		this.loader = loader;
	}
	public void connect() {
		try {
		      Class.forName("org.sqlite.JDBC");
		      connection = DriverManager.getConnection("jdbc:sqlite:"+path);
		      connection.setAutoCommit(true);
		      loader.getLogger().alert("database連接成功");
		    } catch ( Exception e ) {
		    	e.printStackTrace();
		    }
	}
	public void disconnect() {
		try {
			connection.close();
			loader.getLogger().alert("database關閉");
		} catch (SQLException exception) {
			// TODO: handle exception
			exception.printStackTrace();
		}
	}
	public void createTable() {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS blockdata (id integer, name text, type integer, x integer, y integer, z integer, time text, level text)");
			loader.getLogger().alert("表格創建成功");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertData(BlockData data) {
		PreparedStatement stmtStatement;
		try {
			stmtStatement = connection.prepareStatement("INSERT INTO blockdata (id,name,type,x,y,z,time,level)");
			stmtStatement.setInt(1, data.getBlockid());
		stmtStatement.setString(2, data.getPlayer());
		stmtStatement.setInt(3, data.getType());
		stmtStatement.setInt(4, data.getLocation()[0]);
		stmtStatement.setInt(5, data.getLocation()[1]);
		stmtStatement.setInt(6, data.getLocation()[2]);
		stmtStatement.setString(7, data.getTime());
		stmtStatement.setString(8, data.getLevel());
		stmtStatement.executeUpdate();
		stmtStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<BlockData> searchByLocation(Block block) {
		ArrayList<BlockData> list = new ArrayList<BlockData>();
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM blockdata WHERE x = "+block.getFloorX()+", y = "+block.getFloorY()+", z = "+block.getFloorZ()+", level = "+block.getLevelName());
			while (set.next()) {
				BlockData data = new BlockData();
				data.setBlockid(set.getInt("id"));
				data.setLevel(set.getString("level"));
				data.setLocation(new int[] {set.getInt("x"),set.getInt("y"),set.getInt("z")});
				data.setPlayer(set.getString("name"));
				data.setTime(set.getString("time"));
				data.setType(set.getInt("type"));
				list.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<BlockData> searchByPlayer(String name) {
		ArrayList<BlockData> list = new ArrayList<BlockData>();
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM blockdata WHERE name = "+name);
			while (set.next()) {
				BlockData data = new BlockData();
				data.setBlockid(set.getInt("id"));
				data.setLevel(set.getString("level"));
				data.setLocation(new int[] {set.getInt("x"),set.getInt("y"),set.getInt("z")});
				data.setPlayer(set.getString("name"));
				data.setTime(set.getString("time"));
				data.setType(set.getInt("type"));
				list.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
