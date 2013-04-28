package com.tyoku;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.tyoku.commands.StartPosCmd;
import com.tyoku.db.DBManager;
import com.tyoku.listener.BRPlayerListener;

public class BattleRoyale extends JavaPlugin{
	private Logger log;
	public DBManager dbm = new DBManager("battleroyale.sqlite3");
	public final BRPlayerListener pl = new BRPlayerListener();

	@Override
	public void onEnable() {
		this.log = this.getLogger();
		this.log.info("BattleRoyale configre preparing....");

		this.getCommand("brcleardata").setExecutor(new StartPosCmd(this));

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(pl, this);

		this.log.info("BattleRoyale Enabled.");
	}

	@Override
	public void onDisable() {
		this.log.info("BattleRoyale Disabled.");
	}

}
