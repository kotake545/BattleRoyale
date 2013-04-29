package com.tyoku;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.tyoku.commands.BrGame;
import com.tyoku.commands.GameArea;
import com.tyoku.commands.StartPosCmd;
import com.tyoku.db.DBManager;
import com.tyoku.dto.BRManager;
import com.tyoku.dto.BRPlayer;
import com.tyoku.listener.BRPlayerListener;
import com.tyoku.listener.MapListener;

public class BattleRoyale extends JavaPlugin{
	private Logger log;
	private File config;
	private BRManager brManager;
	private Map<String, BRPlayer> playerStat;
	private Map<String,BukkitTask> playerTask;

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
		setBrManager(new BRManager());

		this.playerStat = new HashMap<String, BRPlayer>();
		this.setPlayerTask(new HashMap<String, BukkitTask>());

		//コマンド登録
		this.log.info("BattleRoyale commands preparing....");
		this.getCommand("setroom").setExecutor(new StartPosCmd(this));
		this.getCommand("brgame").setExecutor(new BrGame(this));
		this.getCommand("setbrarea").setExecutor(new GameArea(this));

		//リスナー登録
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new BRPlayerListener(this), this);
		pm.registerEvents(new MapListener(this), this);

		this.log.info("BattleRoyale Enabled.");
	}

	@Override
	public void onDisable() {
		this.log.info("BattleRoyale Disabled.");
	}

	public BRManager getBrManager() {
		return brManager;
	}

	public void setBrManager(BRManager manager) {
		this.brManager = manager;
	}

	public Map<String, BRPlayer> getPlayerStat() {
		return playerStat;
	}

	public void setPlayerStat(Map<String, BRPlayer> playerStat) {
		this.playerStat = playerStat;
	}

	public Map<String,BukkitTask> getPlayerTask() {
		return playerTask;
	}

	public void setPlayerTask(Map<String,BukkitTask> playerTask) {
		this.playerTask = playerTask;
	}

}
