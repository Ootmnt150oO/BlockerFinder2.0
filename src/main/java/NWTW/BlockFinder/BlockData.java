package NWTW.BlockFinder;


public class BlockData {
	private	String time;
	private String player;
	private int blockid;
	private int[] location;
	private String level;
	private int type;
	public int getBlockid() {
		return blockid;
	}
	public String getLevel() {
		return level;
	}
	public int[] getLocation() {
		return location;
	}
	public String getPlayer() {
		return player;
	}
	public String getTime() {
		return time;
	}
	public int getType() {
		return type;
	}
	public void setBlockid(int blockid) {
		this.blockid = blockid;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setLocation(int[] location) {
		this.location = location;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setType(int type) {
		this.type = type;
	}
}
