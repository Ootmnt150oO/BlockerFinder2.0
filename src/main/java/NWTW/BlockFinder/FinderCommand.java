package NWTW.BlockFinder;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class FinderCommand extends Command{

	public FinderCommand(String name, String description) {
		super(name, description);
		// TODO Auto-generated constructor stub
	}
	public static ArrayList<String> list = new ArrayList<>();
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (sender instanceof Player player) {
			if (list.contains(player.getName())) {
				list.remove(player.getName());
				player.sendMessage("你已退出調查模式");
			}else {
				list.add(player.getName());
				player.sendMessage("你已進入調查模式");
			}
		}
		return true;
	}

}
