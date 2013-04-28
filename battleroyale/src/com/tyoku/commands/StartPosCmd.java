package com.tyoku.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.BattleRoyale;

public class StartPosCmd implements CommandExecutor{
	@SuppressWarnings("unused")
	private BattleRoyale plugin;

	public StartPosCmd(BattleRoyale battleRoyale) {
		this.plugin = battleRoyale;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		try {

			if ((sender instanceof Player)) {

				Player player = (Player) sender;
				Location myLoc = player.getLocation();

				int x = myLoc.getBlockX();
				int y = myLoc.getBlockY()+10;
				int z = myLoc.getBlockZ();
				World w = myLoc.getWorld();
				Location loc = new Location(w, x, y, z); //defines new location

				sender.sendMessage(ChatColor.RED + "ワープ！！！！！");
				player.teleport(loc);

			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player!");
				return false;
			}

		} catch (Exception e){
		}
		return true;
	}

}
