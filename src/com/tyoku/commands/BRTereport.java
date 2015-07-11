package com.tyoku.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRPlayer;
import com.tyoku.dto.BRPlayerStatus;
import com.tyoku.util.CommonUtil;

public class BRTereport extends BRCmdExe {

	public BRTereport(BattleRoyale plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String paramString, String[] args) {
		if(args.length != 1){
			return false;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("プレイヤーのコマンドです。");
			return true;
		}

		Player player = (Player)sender;
		BRPlayer brp = plugin.getPlayerStat().get(player.getName());

		if(brp == null || BRPlayerStatus.PLAYING.equals(brp.getStatus())){
			player.sendMessage("観戦者専用コマンドです。");
			return true;
		}

		if(player.getName().equals(args[0])){
			player.sendMessage("自分へ飛ぶことはできません。");
			return true;
		}

		Player[] ps = CommonUtil.getOnlinePlayers();
		for(int i = 0; i < ps.length; i++){
			if(ps[i].getName().equals(args[0])){
				player.teleport(ps[i].getLocation());
			}
		}
		return true;
	}

}
