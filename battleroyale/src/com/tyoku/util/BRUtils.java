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
}
