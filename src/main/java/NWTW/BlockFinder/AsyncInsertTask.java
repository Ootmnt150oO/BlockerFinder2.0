package NWTW.BlockFinder;

import NWTW.BlockFinder.Data.Data;
import cn.nukkit.scheduler.AsyncTask;

public class AsyncInsertTask extends AsyncTask{
	private Data data;
	public AsyncInsertTask(Data data) {
		this.data = data;
	}

	@Override
	public void onRun() {
		// TODO Auto-generated method stub
		Loader.database.insertData(data);
	}

}
