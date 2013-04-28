package com.tyoku.listener;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
	    PlayerInventory inventory = player.getInventory(); // プレイヤーのインベントリ

	    //プリセット位置へプレイヤーを飛ばす
	    int x = this.plugin.getConfig().getInt("roompos.x");
	    int y = this.plugin.getConfig().getInt("roompos.y");
	    int z = this.plugin.getConfig().getInt("roompos.z");

	    if(y == -100){
		    x = player.getLocation().getBlockX();
		    y = player.getLocation().getBlockY();
		    z = player.getLocation().getBlockZ();
		    this.plugin.getConfig().set("roompos.x",x);
		    this.plugin.getConfig().set("roompos.y",y);
		    this.plugin.getConfig().set("roompos.z",z);
		    this.plugin.saveConfig();
	    }
	    World w = player.getWorld();
	    Location nLoc = new Location(w, x, y, z);
	    this.log.info(String.format("プレイヤーを(X:%d Y:%d Z:%d)へ転送", x,y,z));
        player.teleport(nLoc);
        player.sendMessage(ChatColor.GOLD + "バトロワへようこそ！");

	    if (!inventory.contains(Material.DIAMOND)) {
	        inventory.addItem(new ItemStack(Material.DIAMOND, 64)); // プレイヤーインベントリに山積みのダイヤモンドを加える
	        player.sendMessage(ChatColor.GOLD + "よく来たな!もっとダイヤモンドをくれてやろう、このとんでもない成金め!!");
	    }
	}
}
