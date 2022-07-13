package NWTW.BlockFinder;


import NWTW.BlockFinder.Data.BlockData;
import NWTW.BlockFinder.Data.ChestData;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.inventory.InventoryMoveItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;

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
	@EventHandler
	public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
		if (event.getAction().equals(cn.nukkit.event.inventory.InventoryMoveItemEvent.Action.SLOT_CHANGE)) {
			if(event.getInventory().getHolder() instanceof BlockEntityChest  chs && event.getTargetInventory().getHolder() instanceof EntityHuman human) {
				Player player = Server.getInstance().getPlayer(human.getName()); 
				Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(player, player.getLevel().getBlock(chs.getFloorX(), chs.getFloorY(), chs.getFloorZ()), Action.Take,event.getItem())));
			}else if (event.getInventory().getHolder() instanceof EntityHuman hum&& event.getTargetInventory().getHolder() instanceof BlockEntityChest  chs) {
				Player player = Server.getInstance().getPlayer(hum.getName()); 
				Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(player, player.getLevel().getBlock(chs.getFloorX(), chs.getFloorY(), chs.getFloorZ()), Action.Put,event.getItem())));
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void findMode(BlockBreakEvent event) {
		if (FinderCommand.list.contains(event.getPlayer().getName())) {
			Server.getInstance().getScheduler().scheduleAsyncTask(loader, new AsyncSearchTask(event.getBlock(), event.getPlayer()));
			event.getPlayer().sendMessage("正在查詢請稍後......");
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
	private ChestData initData(Player player,Block block,Action action,Item item) {
		ChestData data = new ChestData();
		data.setItem(new int[] {item.getId(),item.getDamage(),item.getCount()});
		data.setItemName(item.getCustomName());
		data.setLevel(block.getLevelName());
		data.setPlayer(player.getName());
		data.setType(action.data);
		data.setLocation(new int[] {block.getFloorX(),block.getFloorY(),block.getFloorZ()});
		data.setTime(Loader.getTime());
		return data;
	}
}
