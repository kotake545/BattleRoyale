package com.tyoku.commands;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.tyoku.BattleRoyale;

abstract class BRCommandExecutor implements CommandExecutor{
	public Logger log;
	public BattleRoyale plugin;

	public BRCommandExecutor(BattleRoyale plugin) {
		this.log = plugin.getLogger();
		this.plugin = plugin;
	}

	@Override
	abstract public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,String[] paramArrayOfString);

}
