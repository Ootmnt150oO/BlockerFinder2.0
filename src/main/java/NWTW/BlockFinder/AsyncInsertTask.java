package NWTW.BlockFinder;

import cn.nukkit.scheduler.AsyncTask;

public class AsyncInsertTask extends AsyncTask{
	private BlockData data;
	public AsyncInsertTask(BlockData data) {
		this.data = data;
	}

	@Override
	public void onRun() {
		// TODO Auto-generated method stub
		Loader.database.insertData(data);
	}

}
