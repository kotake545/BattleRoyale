package com.tyoku.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRGameStatus;
import com.tyoku.util.BRConst;

public class FirstInvincibleTime extends BukkitRunnable {
	private final BattleRoyale plugin;
	private final int time;

	public FirstInvincibleTime(BattleRoyale plugin, int time) {
		this.plugin = plugin;
		this.time = time;
	}

	@Override
	public void run() {
		this.plugin.getServer().broadcastMessage(BRConst.MSG_IMPORTANT_COLOR + "無敵時間を"+BRConst.MSG_IMPORTANT_NUM_COLOR+this.time+BRConst.MSG_IMPORTANT_COLOR+"秒用意しました。その間にできるだけゲームの準備をしてください。");
		for (int i = this.time; i > 0; i--) {
			try {
				if(i <= 5 || i == 30 )
					this.plugin.getServer().broadcastMessage(BRConst.MSG_IMPORTANT_COLOR + "無敵時間終了まで"+BRConst.MSG_IMPORTANT_NUM_COLOR+""+i+""+BRConst.MSG_IMPORTANT_COLOR+"秒。");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		this.plugin.getServer().broadcastMessage(BRConst.MSG_IMPORTANT_COLOR + "無敵時間終了。");
		this.plugin.getBrManager().setGameStatus(BRGameStatus.PLAYING);

	}

}
