package NWTW.BlockFinder;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerInteractEvent;

public class PlayerBehvavior implements Listener{
	Loader loader;
	public PlayerBehvavior(Loader loader) {
		this.loader = loader;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMine(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (event.isCancelled()) {
			return;
		}
		Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(player, block, Action.Mine)));
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (event.isCancelled()) {
			return;
		}
		Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(player, block, Action.Place)));
	}
	@EventHandler
	public void onOpenChest(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction().equals(cn.nukkit.event.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
			if (event.getBlock().getId() == BlockID.CHEST || event.getBlock().getId() == BlockID.SHULKER_BOX|| event.getBlock().getId() == BlockID.UNDYED_SHULKER_BOX) {
				Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(player, event.getBlock(), Action.Open)));
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void findMode(BlockBreakEvent event) {
		if (FinderCommand.list.contains(event.getPlayer().getName())) {
			FormControler.show(event.getPlayer(), Loader.database.searchByLocation(event.getBlock()));
			event.setCancelled(true);
		}
	}
	private BlockData initData(Player player,Block block,Action action) {
		BlockData data = new BlockData();
		data.setBlockid(block.getId());
		data.setLevel(block.getLevelName());
		data.setPlayer(player.getName());
		data.setType(action.data);
		data.setLocation(new int[] {block.getFloorX(),block.getFloorY(),block.getFloorZ()});
		data.setTime(Loader.getTime());
		return data;
	}
}
