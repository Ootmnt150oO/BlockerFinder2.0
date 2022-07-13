package NWTW.BlockFinder.Data;


public class ChestData extends Data{
	private int[] item;
	private String itemName;
	public int[] getItem() {
		return item;
	}
	public String getItemName() {
		return itemName;
	}
	
	public void setItem(int[] item) {
		this.item = item;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
