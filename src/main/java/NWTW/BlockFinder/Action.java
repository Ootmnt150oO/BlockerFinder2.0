package NWTW.BlockFinder;

public enum Action {
	Mine(0),
	Place(1),
	Open(2);
	public int data;
	private Action(int data) {
		this.data = data;
	}
}
