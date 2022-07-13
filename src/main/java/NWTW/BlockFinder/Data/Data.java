package NWTW.BlockFinder.Data;

public abstract class Data {
	protected String time;
	protected String player;
	protected int[] location;
	protected String level;
	protected int type;
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
