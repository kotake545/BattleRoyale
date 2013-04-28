package com.tyoku.listener;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BRPlayerListener implements Listener {
	private Logger log;

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	    Player player = event.getPlayer(); // Joinしたプレイヤー
	    PlayerInventory inventory = player.getInventory(); // プレイヤーのインベントリ

        player.sendMessage(ChatColor.GOLD + "やっほおおお！！");
	    if (!inventory.contains(Material.DIAMOND)) {
	        inventory.addItem(new ItemStack(Material.DIAMOND, 64)); // プレイヤーインベントリに山積みのダイヤモンドを加える
	        player.sendMessage(ChatColor.GOLD + "よく来たな!もっとダイヤモンドをくれてやろう、このとんでもない成金め!!");
	    }
	}

	@EventHandler
	public void onWorldLoad(WorldLoadEvent event){

		this.log.info(ChatColor.YELLOW + "BattleRoyale Start.");
	}


}
