package NWTW.BlockFinder;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.scheduler.AsyncTask;

public class AsyncSearchTask extends AsyncTask {
	private Block block;
	private Player player;
	private Action action;
	public AsyncSearchTask(Block block , Player player,Action action) {
		this.block = block;
		this.player = player;
		this.action = action;
	}
	@Override
	public void onRun() {
		// TODO Auto-generated method stub
		if (action.equals(Action.LEFT_CLICK_BLOCK)) {
			FormControler.show(player, Loader.database.searchBlockDataByLocation(block));
		}else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
			FormControler.showChest(player,Loader.database.searchChestDataByLocation(block));
		}
	}

}
