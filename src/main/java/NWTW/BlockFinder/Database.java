package NWTW.BlockFinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import NWTW.BlockFinder.Data.BlockData;
import NWTW.BlockFinder.Data.ChestData;
import NWTW.BlockFinder.Data.Data;
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
		    	loader.getLogger().alert(e.getMessage());
		    }
	}
	public void disconnect() {
		try {
			connection.close();
			loader.getLogger().alert("database關閉");
		} catch (SQLException exception) {
			// TODO: handle exception
			loader.getLogger().alert(exception.getSQLState());
		}
	}
	public boolean isConnect() {
		return connection!=null;
	}
	public void createTable() {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("CREATE TABLE IF NOT EXISTS blockdata (id integer, name text, type integer, x integer, y integer, z integer, time text, level text)");
			stmt.execute("CREATE TABLE IF NOT EXISTS chestdata (id integer, meta integer, count integer, name text, type integer, x integer, y integer, z integer, time text, level text, player text)");
			loader.getLogger().alert("表格創建成功");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			loader.getLogger().alert(e.getSQLState());
		}
	}
	public void insertData(Data data) {
		PreparedStatement stmtStatement;
		if (data instanceof BlockData bdata) {
			try {
			stmtStatement = connection.prepareStatement("INSERT INTO blockdata(id,name,type,x,y,z,time,level) VALUES (?,?,?,?,?,?,?,?)");
			stmtStatement.setInt(1, bdata.getBlockid());
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
				loader.getLogger().alert(e.getSQLState());
			}
		}else {
			try {
				ChestData chestData = (ChestData)data;
				stmtStatement = connection.prepareStatement("INSERT INTO chestdata(id,meta,count,name,type,x,y,z,time,level,player) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				stmtStatement.setInt(1, chestData.getItem()[0]);
				stmtStatement.setInt(2, chestData.getItem()[1]);
				stmtStatement.setInt(3, chestData.getItem()[2]);
				stmtStatement.setString(4, chestData.getItemName());
				stmtStatement.setInt(5, chestData.getType());
				stmtStatement.setInt(6, chestData.getLocation()[0]);
				stmtStatement.setInt(7, chestData.getLocation()[1]);
				stmtStatement.setInt(8, chestData.getLocation()[2]);
				stmtStatement.setString(9, chestData.getTime());
				stmtStatement.setString(10, chestData.getLevel());
				stmtStatement.setString(11, chestData.getPlayer());
				stmtStatement.executeUpdate();
				stmtStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				loader.getLogger().alert(e.getSQLState());
			}
		}
		
	}
	public ArrayList<BlockData> searchBlockDataByLocation(Block block) {
		ArrayList<BlockData> list = new ArrayList<BlockData>();
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM blockdata WHERE x = "+block.getFloorX()+" AND y = "+block.getFloorY()+" AND z = "+block.getFloorZ()+" AND level = "+"'"+block.getLevelName()+"'");
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
			loader.getLogger().alert(e.getMessage());
		}
		Collections.reverse(list);
		return list;
	}
	public ArrayList<BlockData> searchBlockDataByPlayer(String name) {
		ArrayList<BlockData> list = new ArrayList<BlockData>();
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM blockdata WHERE name = '"+name+"'");
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
			loader.getLogger().alert(e.getSQLState());
		}
		Collections.reverse(list);
		return list;
	}
	public ArrayList<ChestData> searchChestDataByLocation(Block block) {
		ArrayList<ChestData> list = new ArrayList<>();
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM chestdata WHERE x = "+block.getFloorX()+" AND y = "+block.getFloorY()+" AND z = "+block.getFloorZ()+" AND level = "+"'"+block.getLevelName()+"'");
			while (set.next()) {
				ChestData data = new ChestData();
				data.setItem(new int[] {set.getInt("id"),set.getInt("meta"),set.getInt("count")});
				data.setItemName(set.getString("name"));
				data.setLevel(set.getString("level"));
				data.setLocation(new int[] {set.getInt("x"),set.getInt("y"),set.getInt("z")});
				data.setPlayer(set.getString("player"));
				data.setTime(set.getString("time"));
				data.setType(set.getInt("type"));
				list.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			loader.getLogger().alert(e.getMessage());
		}
		Collections.reverse(list);
		return list;
	}
	public ArrayList<ChestData> searchChestDataByPlayer(String name) {
		ArrayList<ChestData> list = new ArrayList<>();
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM chestdata WHERE name = '"+name+"'");
			while (set.next()) {
				ChestData data = new ChestData();
				data.setItem(new int[] {set.getInt("id"),set.getInt("meta"),set.getInt("count")});
				data.setItemName(set.getString("name"));
				data.setLevel(set.getString("level"));
				data.setLocation(new int[] {set.getInt("x"),set.getInt("y"),set.getInt("z")});
				data.setPlayer(set.getString("player"));
				data.setTime(set.getString("time"));
				data.setType(set.getInt("type"));
				list.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			loader.getLogger().alert(e.getSQLState());
		}
		Collections.reverse(list);
		return list;
	}
}
