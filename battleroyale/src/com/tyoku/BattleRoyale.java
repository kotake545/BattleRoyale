package com.tyoku;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.tyoku.util.BRUtils;

public class BattleRoyale extends JavaPlugin{
        private Logger log;
        private File config;
        private BRManager brManager;
        private Map<String, BRPlayer> playerStat;
        private Map<String,BukkitTask> playerTask;
        private List<String> randomMapBlocks;
        private List<String> deadAreaBlocks;
        private List<String> nextAreaBlocks;

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

                this.randomMapBlocks = BRUtils.getRundumMRMapBlocks();
                deadAreaBlocks = new ArrayList<String>();
                nextAreaBlocks = new ArrayList<String>();
                deadAreaBlocks.add(randomMapBlocks.get(0));
                deadAreaBlocks.add(randomMapBlocks.get(1));
                deadAreaBlocks.add(randomMapBlocks.get(2));
                deadAreaBlocks.add(randomMapBlocks.get(3));
                deadAreaBlocks.add(randomMapBlocks.get(4));
                nextAreaBlocks.add(randomMapBlocks.get(5));
                nextAreaBlocks.add(randomMapBlocks.get(6));
                nextAreaBlocks.add(randomMapBlocks.get(7));
                nextAreaBlocks.add(randomMapBlocks.get(8));
                for(String d : deadAreaBlocks){
                	this.log.info("DeadArea:"+d);
                }
                for(String d : nextAreaBlocks){
                	this.log.info("AlertArea:"+d);
                }

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
        public List<String> getRandomMapBlocks() {
                return randomMapBlocks;
        }

        public void setRandomMapBlocks(List<String> randomMapBlocks) {
                this.randomMapBlocks = randomMapBlocks;
        }

        public List<String> getDeadAreaBlocks() {
                return deadAreaBlocks;
        }

        public void setDeadAreaBlocks(List<String> deadAreaBlocks) {
                this.deadAreaBlocks = deadAreaBlocks;
        }

        /**
         * @return nextAreaBlocks
         */
        public List<String> getNextAreaBlocks() {
            return nextAreaBlocks;
        }

        /**
         * @param nextAreaBlocks セットする nextAreaBlocks
         */
        public void setNextAreaBlocks(List<String> nextAreaBlocks) {
            this.nextAreaBlocks = nextAreaBlocks;
        }
}
