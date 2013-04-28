package com.tyoku.listener;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.tyoku.BattleRoyale;

public class BRPlayerListener implements Listener {
	private Logger log;
	private BattleRoyale plugin;

	public BRPlayerListener(BattleRoyale battleRoyale) {
		this.plugin = battleRoyale;
		this.log = battleRoyale.getLogger();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	    Player player = event.getPlayer(); // Joinしたプレイヤー

	    //プリセット位置へプレイヤーを飛ばす
	    int x = this.plugin.getConfig().getInt("classroom.pos.x");
	    int y = this.plugin.getConfig().getInt("classroom.pos.y");
	    int z = this.plugin.getConfig().getInt("classroom.pos.z");

	    if(y == -100){
		    x = player.getLocation().getBlockX();
		    y = player.getLocation().getBlockY();
		    z = player.getLocation().getBlockZ();
		    this.plugin.getConfig().set("classroom.pos.x",x);
		    this.plugin.getConfig().set("classroom.pos.y",y);
		    this.plugin.getConfig().set("classroom.pos.z",z);
		    this.plugin.saveConfig();
	    }
	    World w = player.getWorld();
	    Location nLoc = new Location(w, x, y, z);
	    this.log.info(String.format("プレイヤーを(X:%d Y:%d Z:%d)へ転送", x,y,z));
        player.teleport(nLoc);
        player.sendMessage(ChatColor.GOLD + "バトロワへようこそ！");
	}
}
