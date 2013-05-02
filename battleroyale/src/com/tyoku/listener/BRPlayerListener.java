package com.tyoku.listener;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRGameStatus;
import com.tyoku.dto.BRPlayer;
import com.tyoku.dto.BRPlayerStatus;
import com.tyoku.util.BRConst;
import com.tyoku.util.BRUtils;

public class BRPlayerListener implements Listener {
	private Logger log;
	private BattleRoyale plugin;

	public BRPlayerListener(BattleRoyale battleRoyale) {
		this.plugin = battleRoyale;
		this.log = battleRoyale.getLogger();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	    Player player = event.getPlayer();

	    //プリセット位置へプレイヤーを飛ばす
	    int x = this.plugin.getBrConfig().getClassRoomPosX();
	    int z = this.plugin.getBrConfig().getClassRoomPosZ();
	    int y = player.getLocation().getBlockY();

	    if(x == 1000){
		    x = player.getLocation().getBlockX();
		    z = player.getLocation().getBlockZ();
		    this.plugin.getConfig().set("classroom.pos.x",x);
		    this.plugin.getConfig().set("classroom.pos.z",z);
		    this.plugin.saveConfig();
		    this.plugin.getBrConfig().setClassRoomPosX(x);
		    this.plugin.getBrConfig().setClassRoomPosX(z);
	    }
	    World w = player.getWorld();
	    Location nLoc = new Location(w, x, y, z);
	    this.log.info(String.format("プレイヤーを(X:%d Y:%d Z:%d)へ転送", x,y,z));
        player.teleport(nLoc);
        player.setHealth(20);
        player.setFoodLevel(20);

		//プレイヤーリスト作成
		BRPlayer brps = new BRPlayer();
		brps.setName(player.getName());
		String appendMsg = "";
		if(BRGameStatus.OPENING.equals(this.plugin.getBrManager().getGameStatus())
				|| BRGameStatus.PREPARE.equals(this.plugin.getBrManager().getGameStatus())
				){
			brps.setStatus(BRPlayerStatus.PLAYING);
			player.setPlayerListName(BRConst.LIST_COLOR_PLAYER + player.getName());
		}else{
			brps.setStatus(BRPlayerStatus.DEAD);
			player.setPlayerListName(BRConst.LIST_COLOR_DEAD + player.getName());
			appendMsg = "ゲームは既に始まっています。次回、ご参加ください。";
		}
		this.plugin.getPlayerStat().put(brps.getName(), brps);

		//インベントリを空にする。
    	player.getInventory().clear();
		player.sendMessage(ChatColor.GOLD + "バトロワへようこそ！" + appendMsg);

	}

	@EventHandler
	public void onPlayerChangedWorld(BlockPlaceEvent event){
	    Player player = event.getPlayer();
	    Block block = event.getBlock();
	    player.sendMessage(block.toString());
	    boolean isFirstOpen = this.plugin.getPlayerStat().get(player.getName()).isFiestChestOpend();
	    player.sendMessage(Boolean.toString(isFirstOpen));
		if(isFirstOpen && block.getType().equals(Material.CHEST)){
		    Chest chest = (Chest)block.getState();
		    Inventory inventory = chest.getInventory();
			for(ItemStack is : BRUtils.getFirstItemStacks()){
				inventory.addItem(is);
			}
			this.plugin.getPlayerStat().get(player.getName()).setFiestChestOpend(false);
		}

	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		event.getPlayer().getInventory().clear();
		BRUtils.teleportRoom(plugin, event.getPlayer());
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
	    Player player = event.getPlayer();
	    BRPlayer brp = this.plugin.getPlayerStat().get(player.getName());

	    if(!player.isOnline()
	    		|| BRPlayerStatus.DEAD.equals(brp.getStatus())
	    		|| !BRGameStatus.PLAYING.equals(this.plugin.getBrManager().getGameStatus())
	    		){
	    	return;
	    }
	    if(!BRUtils.isGameArea(this.plugin, player)){
			player.sendMessage(ChatColor.RED + "ゲームエリア外に出た為、5秒後に爆死します。");
			//殺してステータス変更
			BRUtils.deadCount(player, 5);
			brp.setStatus(BRPlayerStatus.DEAD);
			player.setPlayerListName(BRConst.LIST_COLOR_DEAD+player.getName());
			plugin.getPlayerStat().put(player.getName(),brp);

	    }else if(BRUtils.isDeadArea(this.plugin, player)){
			player.sendMessage(ChatColor.RED + "禁止エリアに進入しました。5秒後に爆死します。");
			//殺してステータス変更
			BRUtils.deadCount(player, 5);
			brp.setStatus(BRPlayerStatus.DEAD);
			player.setPlayerListName(BRConst.LIST_COLOR_DEAD+player.getName());
			plugin.getPlayerStat().put(player.getName(),brp);

	    }else if(BRUtils.isAlertArea(this.plugin, player)){
			player.sendMessage(ChatColor.RED + "エリア外付近です。エリア外に出ると爆死します。");
	    }else{
	        //player.sendMessage(ChatColor.GOLD + "ゲームエリア内");
	    }
	}
}
