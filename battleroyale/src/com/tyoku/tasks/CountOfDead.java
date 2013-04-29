package com.tyoku.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRPlayer;
import com.tyoku.dto.BRPlayerStatus;
import com.tyoku.util.BRConst;

public class CountOfDead extends BukkitRunnable {
	private final BattleRoyale plugin;
	private final Player player;
	private final BRPlayer brp;

	public CountOfDead(BattleRoyale plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		this.brp = plugin.getPlayerStat().get(player.getName());
	}

	@Override
	public void run() {
		if(BRPlayerStatus.DEAD.equals(brp.getStatus())){
			this.cancel();
		}
		player.sendMessage(ChatColor.RED + "ゲームエリア外に出た為、5秒後に爆死します。");
		for (int i = 5; i > 0; i--) {
			try {
				player.sendMessage(ChatColor.RED + "爆発まで・・・"+i+"秒。");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}

		float explosionPower = 4F;
		player.sendMessage(ChatColor.RED + "ByeBye!! BOOOOOOOOOOOM!!!");
		player.getWorld().createExplosion(player.getLocation(), explosionPower);
		player.setHealth(0);
		player.setPlayerListName(BRConst.LIST_COLOR_DEAD+player.getName());
		brp.setStatus(BRPlayerStatus.DEAD);
		plugin.getPlayerStat().put(player.getName(),brp);
	}
}
