package NWTW.BlockFinder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.nukkit.plugin.PluginBase;

public class Loader extends PluginBase{
	public static Database database;
	@Override
	public void onEnable() {
		this.getLogger().alert("插件啟動");
		File file = new File(getDataFolder()+"/");
		if (!file.exists()) {
		file.mkdirs();
		}
		file = new File(getDataFolder()+"/database.db");
		if (!file.exists()) {
			try {
				file.createNewFile();
				getLogger().alert("未找到database正在創建一個");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		database = new Database(this,getDataFolder()+"/database.db");
		getLogger().alert("正在連接database");
		database.connect();
		database.createTable();
		getServer().getPluginManager().registerEvents(new PlayerBehvavior(this), this);
		getServer().getCommandMap().register("finder", new FinderCommand("finder","進入/退出調查模式"));
		getServer().getCommandMap().register("search", new SearchCommand("search", "管理員查詢玩家破壞紀錄指令"));
		super.onEnable();
	}
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		this.getLogger().alert("插件關閉");
		database.disconnect();
		super.onDisable();
	}
	public static String getTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}
}
