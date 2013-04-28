package com.tyoku;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.tyoku.commands.StartPosCmd;
import com.tyoku.db.DBManager;
import com.tyoku.listener.BRPlayerListener;

public class BattleRoyale extends JavaPlugin{
	private Logger log;
	private File config;
	@SuppressWarnings("unused")
	private DBManager dbm = new DBManager("battleroyale.sqlite3");

	@Override
	public void onEnable() {
		this.log = this.getLogger();
		this.log.info("BattleRoyale configre preparing....");
		this.config = new File(this.getDataFolder(), "config.yml");
		if (!config.exists()) {
		    this.saveDefaultConfig();
		}

		this.log.info("BattleRoyale commands preparing....");
		this.getCommand("warp").setExecutor(new StartPosCmd(this));

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new BRPlayerListener(this), this);

		this.log.info("BattleRoyale Enabled.");
	}

	@Override
	public void onDisable() {
		this.log.info("BattleRoyale Disabled.");
	}

}
