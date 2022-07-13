package NWTW.BlockFinder;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class SearchCommand extends Command{

	public SearchCommand(String name, String description) {
		super(name, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (sender instanceof Player player) {
			if (player.isOp()) {
				if (args.length == 1) {
					FormControler.showAdmin(player,Loader.database.searchBlockDataByPlayer(args[0]),Loader.database.searchChestDataByPlayer(args[0]));
				}
			}
		}
		// TODO Auto-generated method stub
		return true;
	}

}
