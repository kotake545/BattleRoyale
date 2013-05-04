package com.tyoku.listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRBuilding;
import com.tyoku.dto.BRGameStatus;
import com.tyoku.dto.BRPlayer;
import com.tyoku.dto.BRPlayerStatus;
import com.tyoku.tasks.Ending;
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
	    int y = this.plugin.getBrConfig().getClassRoomPosY();

	    if(x == 1000 && y == 1000 && z == 1000){
		    x = player.getLocation().getBlockX();
		    z = player.getLocation().getBlockZ()+13;
		    y = player.getLocation().getBlockY()+6;
		    this.plugin.getBrConfig().setClassRoomPosX(x);
		    this.plugin.getBrConfig().setClassRoomPosZ(z);
		    this.plugin.getBrConfig().setClassRoomPosY(y);
	    }
	    if(!this.plugin.isRoomCreated()){
	    	BRBuilding brb = this.plugin.getBrBuilding().get("home");
	    	if(brb != null && brb.isCreatable()){
	    		brb.create(player.getWorld(), new Location(
	    				player.getWorld()
	    				, player.getLocation().getBlockX()
	    				, player.getLocation().getBlockY()
	    				, player.getLocation().getBlockZ()));
	    		player.getWorld().setSpawnLocation(x, y, z);
	    	}
	    	this.plugin.setRoomCreated(true);
	    }
	    World w = player.getWorld();
	    Location nLoc = new Location(w, x, y, z);
	    this.log.info(String.format("プレイヤーを(X:%d Y:%d Z:%d)へ転送", x,y,z));
        player.teleport(nLoc);
        BRUtils.clearPlayerStatus(player);

		//プレイヤーリスト作成
		BRPlayer brps = new BRPlayer();
		brps.setName(player.getName());
		String appendMsg = "";
		if(BRGameStatus.OPENING.equals(this.plugin.getBrManager().getGameStatus())){
			brps.setStatus(BRPlayerStatus.PLAYING);
			player.setDisplayName(BRConst.LIST_COLOR_PLAYER + player.getName());
			player.setPlayerListName(player.getDisplayName());
		}else{
			brps.setStatus(BRPlayerStatus.DEAD);
			BRUtils.setPlayerDeadMode(plugin, player);
			appendMsg = "ゲームは既に始まっています。次回、ご参加ください。";
		}
		this.plugin.getPlayerStat().put(brps.getName(), brps);

		//インベントリを空にする。
    	player.getInventory().clear();
		player.sendMessage(ChatColor.GOLD + "バトロワへようこそ！" + appendMsg);

	}

	@EventHandler
	public void onPlayerChangedWorld(BlockPlaceEvent event){
		if(this.plugin.getBrManager().getGameStatus().equals(BRGameStatus.OPENING)){
			event.setCancelled(true);
		}
	    Player player = event.getPlayer();
	    Block block = event.getBlock();
	    boolean isFirstOpen = this.plugin.getPlayerStat().get(player.getName()).isFiestChestOpend();
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
		Player player = event.getPlayer();
        BRUtils.clearPlayerStatus(player);
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

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){

		//プレイヤーへのダメージ制限
		if(!this.plugin.getBrManager().getGameStatus().equals(BRGameStatus.PLAYING)){
			if(EntityType.PLAYER.equals(event.getEntityType())){
				event.setCancelled(true);
			}
		}
		if(EntityType.PLAYER.equals(event.getEntityType())){
			Player player = (Player)event.getEntity();
			BRPlayer brp = this.plugin.getPlayerStat().get(player.getName());
			if(brp == null || BRPlayerStatus.DEAD.equals(brp.getStatus())){
				event.setCancelled(true);
			}
		}

		//死者からのダメージ制限
		if(DamageCause.ENTITY_ATTACK.equals(event.getCause())){
           if(event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
                if(EntityType.PLAYER.equals(e.getDamager().getType())){
	    			BRPlayer brp = this.plugin.getPlayerStat().get(e.getDamager());
	    			if(brp == null || BRPlayerStatus.DEAD.equals(brp.getStatus())){
	    				event.setCancelled(true);
	    			}
                }
            }
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(this.plugin.getBrManager().getGameStatus().equals(BRGameStatus.OPENING)){
			event.setCancelled(true);
		}

		BRPlayer brp = this.plugin.getPlayerStat().get(event.getPlayer().getName());
		if(brp == null || BRPlayerStatus.DEAD.equals(brp.getStatus())){
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		BRUtils.setPlayerDeadMode(plugin, player);

		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();

		int pb = BRUtils.getPlayerBalance(plugin);
		if(pb > 1){
			String msg = String.format(
					"%sが（X:%d, Y:%d, Z:%d）で死亡しました。【残り%d人】", player.getName(),x,y,z,pb);
			Bukkit.broadcastMessage(BRConst.MSG_SYS_COLOR + msg);
		}else if(pb == 0){
			Bukkit.broadcastMessage(BRConst.MSG_SYS_COLOR + "ゲーム終了・・・"+ChatColor.RED+"全員死亡"+BRConst.MSG_SYS_COLOR+"の為、優勝者なし。");
			this.plugin.setCreateEnding(new Ending(plugin).runTask(plugin));
		}else if(pb == 1){
			Bukkit.broadcastMessage(BRConst.MSG_SYS_COLOR + String.format(
					"ゲーム終了・・・優勝者は【"+ChatColor.GOLD+"%s"+BRConst.MSG_SYS_COLOR+"】です！おめでとう！！", player.getName()));
			this.plugin.setCreateEnding(new Ending(plugin).runTask(plugin));
		}
		player.kickPlayer(event.getDeathMessage());
	}

	@EventHandler
	public void noSee(EntityTargetEvent event) {
		if (event.getTarget() instanceof Player) {
			Player player = (Player) event.getTarget();
			BRPlayer brp = this.plugin.getPlayerStat().get(player.getName());
			if (brp == null || BRPlayerStatus.DEAD.equals(brp.getStatus())) {
				event.setCancelled(true);
			}
		}
	}


	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerItemHeld(PlayerPickupItemEvent event) {
		BRPlayer brp = this.plugin.getPlayerStat().get(event.getPlayer().getName());
		if (brp == null || BRPlayerStatus.DEAD.equals(brp.getStatus())) {
			event.setCancelled(true);
		}
	}
}
