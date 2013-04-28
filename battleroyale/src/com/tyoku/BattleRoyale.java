package com.tyoku;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.tyoku.commands.BrGame;
import com.tyoku.commands.GameArea;
import com.tyoku.commands.StartPosCmd;
import com.tyoku.db.DBManager;
import com.tyoku.dto.BRManager;
import com.tyoku.listener.BRPlayerListener;

public class BattleRoyale extends JavaPlugin{
	private Logger log;
	private File config;
	private BRManager manager;

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
		setManager(new BRManager());

		//コマンド登録
		this.log.info("BattleRoyale commands preparing....");
		this.getCommand("setroom").setExecutor(new StartPosCmd(this));
		this.getCommand("brgame").setExecutor(new BrGame(this));
		this.getCommand("setbrarea").setExecutor(new GameArea(this));

		//リスナー登録
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new BRPlayerListener(this), this);

		this.log.info("BattleRoyale Enabled.");
	}

	@Override
	public void onDisable() {
		this.log.info("BattleRoyale Disabled.");
	}

	public BRManager getManager() {
		return manager;
	}

	public void setManager(BRManager manager) {
		this.manager = manager;
	}

}
