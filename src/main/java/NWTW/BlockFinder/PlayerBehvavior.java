package NWTW.BlockFinder;



import NWTW.BlockFinder.Data.BlockData;
import NWTW.BlockFinder.Data.ChestData;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
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
	public void onInventoryTransactionEvent(InventoryTransactionEvent event) {
		InventoryTransaction transaction = event.getTransaction();
		for(InventoryAction action: transaction.getActionList()) {
			if(action instanceof SlotChangeAction slot) {
				if(slot.getInventory().getHolder() instanceof BlockEntityChest chest) {
					if (slot.getSourceItem().getId() == 0) {
						Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getBlock(),Action.Put,slot.getTargetItem())));
						if(chest.isPaired()) {
							Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getPair().getBlock(),Action.Put,slot.getTargetItem())));
						}
				}
				if (slot.getTargetItem().getId() == 0) {
					Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getBlock(),Action.Take,slot.getSourceItem())));
					if(chest.isPaired()) {
						Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getPair().getBlock(),Action.Take,slot.getSourceItem())));
					}
				}
				if (slot.getSourceItem().getId()!=0 && slot.getTargetItem().getId()!=0) {
					Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getBlock(),Action.Put,slot.getTargetItem())));
					Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getBlock(),Action.Take,slot.getSourceItem())));
					if(chest.isPaired()) {
						Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getPair().getBlock(),Action.Put,slot.getTargetItem())));
						Server.getInstance().getScheduler().scheduleAsyncTask(loader,new AsyncInsertTask(initData(transaction.getSource(),chest.getPair().getBlock(),Action.Take,slot.getSourceItem())));
					}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void findMode(PlayerInteractEvent event) {
		if (FinderCommand.list.contains(event.getPlayer().getName())) {
			Server.getInstance().getScheduler().scheduleAsyncTask(loader, new AsyncSearchTask(event.getBlock(), event.getPlayer(),event.getAction()));
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
