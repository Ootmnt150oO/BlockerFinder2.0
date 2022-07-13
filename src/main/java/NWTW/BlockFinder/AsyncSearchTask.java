package NWTW.BlockFinder;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.scheduler.AsyncTask;

public class AsyncSearchTask extends AsyncTask {
	private Block block;
	private Player player;
	public AsyncSearchTask(Block block , Player player) {
		this.block = block;
		this.player = player;
	}
	@Override
	public void onRun() {
		// TODO Auto-generated method stub
		FormControler.show(player, Loader.database.searchBlockDataByLocation(block),Loader.database.searchChestDataByLocation(block));
	}

}
