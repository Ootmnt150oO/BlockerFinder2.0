package NWTW.BlockFinder;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

public class FormControler {
	public static void show(Player player,ArrayList<BlockData> datas) {
		StringBuilder builder = new StringBuilder();
		if(datas.isEmpty()) builder.append("查無資料");
		for(BlockData data:datas) {
			Block block = Block.get(data.getBlockid());
			builder.append(data.getTime()).append(" 玩家:").append(TextFormat.BLUE).append(data.getPlayer()).append(" ").append(TextFormat.RED).append(type2String(data.getType())).append(TextFormat.WHITE).append(block.getName()).append("\n");
		}
		FormWindowSimple simple = new FormWindowSimple("方塊查詢系統", builder.toString());
		player.showFormWindow(simple);
	}
	public static void showAdmin(Player player,ArrayList<BlockData> datas) {
		StringBuilder builder = new StringBuilder();
		if(datas.isEmpty()) builder.append("查無資料");
		for(BlockData data:datas) {
			Block block = Block.get(data.getBlockid());
			builder.append(data.getTime()).append("在").append(TextFormat.AQUA).append(data.getLevel()).append(":").append(data.getLocation()[0]).append(":").append(data.getLocation()[1]).append(":").append(data.getLocation()[2]).append(TextFormat.WHITE).append(type2String(data.getType())).append(" ").append(block.getName()).append("\n");
		}
		FormWindowSimple simple = new FormWindowSimple("方塊查詢系統", builder.toString());
		player.showFormWindow(simple);
	}
	private static String type2String(int type) {
		switch (type) {
		case 0: {
				return "挖掉了";
		}
		case 1:{
			return "放置了";
		}
		case 2:{
			return "打開了";
		}
		default:
			return "未知行為";
		}
	}
}
