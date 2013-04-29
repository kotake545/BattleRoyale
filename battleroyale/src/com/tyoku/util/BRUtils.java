package com.tyoku.util;

import org.bukkit.entity.Player;

import com.tyoku.BattleRoyale;

public class BRUtils {

    static public Integer String2Integer(String str) {
    	Integer ret = null;
        try {
        	ret = Integer.parseInt(str);
            return ret;
        } catch(NumberFormatException e) {
        	return null;
        }
    }

    /**
     * オンラインプレイヤー全員にメッセージを送ります。
     * @param plugin
     * @param message
     */
    static public void announce(BattleRoyale plugin, String message){
    	for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    /**
     * プレイヤーがBRゲームエリア内に存在するか否かを判定する。
     * @param plugin
     * @param player
     * @return
     */
    static public boolean isGameArea(BattleRoyale plugin, Player player){
	    int x1 = plugin.getConfig().getInt("gamearea.pos1.x");
	    int z1 = plugin.getConfig().getInt("gamearea.pos1.z");
	    int x2 = plugin.getConfig().getInt("gamearea.pos2.x");
	    int z2 = plugin.getConfig().getInt("gamearea.pos2.z");
	    int w = 0;
	    if(x1 < x2){
	    	w = x1;
	    	x1 = x2;
	    	x2 = w;
	    }
	    if(z1 < z2){
	    	w = z1;
	    	z1 = z2;
	    	z2 = w;
	    }
	    int xp = player.getLocation().getBlockX();
	    int zp = player.getLocation().getBlockZ();
	    if(x1 >= xp && xp >= x2 && z1 >= zp && zp >= z2){
	    	return true;
	    }

    	return false;
    }

}
