package com.tyoku.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRGameStatus;
import com.tyoku.util.BRUtils;

public class Ending extends BukkitRunnable {
	private final BattleRoyale plugin;

	public Ending(BattleRoyale plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if(this.plugin.getBrManager().getGameStatus().equals(BRGameStatus.END)){
			Bukkit.shutdown();
			return;
		}
		this.plugin.getBrManager().setGameStatus(BRGameStatus.END);
		BRUtils.announce(plugin, "サーバを30秒後に再起動します。");
    	Player[] players = plugin.getServer().getOnlinePlayers();
        for(int i = 0; i < players.length; i++){
            for(int j = 0; j < players.length; j++){
            	players[i].showPlayer(players[j]);
            }
        }
		for (int i = 30; i > 0; i--) {
			try {
				if(i <= 5 || i == 30 )
					BRUtils.announce(plugin, String.format("再起動まで%d秒", i));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		Bukkit.shutdown();
	}


}
