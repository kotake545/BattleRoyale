package com.tyoku.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.BattleRoyale;
import com.tyoku.util.BRConst;
import com.tyoku.util.BRUtils;

public class GameArea extends BRCmdExe{

	public GameArea(BattleRoyale plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String paramString, String[] args) {
		try {
			if ((sender instanceof Player)) {
				if(args.length != 1 && args.length != 3){
					return false;
				}
				if(!"1".equals(args[0]) && !"2".equals(args[0])){
					return false;
				}

				Player player = (Player) sender;
				Location myLoc = player.getLocation();

				//ユーザの座標を保持
				int x = myLoc.getBlockX();
				int z = myLoc.getBlockZ();
				x = myLoc.getBlockX();
				z = myLoc.getBlockZ();
				if(args.length == 2){
					Integer ix = BRUtils.String2Integer(args[1]);
					Integer iz = BRUtils.String2Integer(args[2]);
					if(ix != null && iz != null){
						//引数があればそれを座標に設定
						x = ix;
						z = iz;
					}
				}

				//設定上書き
			    this.plugin.getConfig().set(String.format("gamearea.pos%s.x", args[0]),x);
			    this.plugin.getConfig().set(String.format("gamearea.pos%s.z", args[0]),z);
			    this.plugin.saveConfig();
			    player.sendMessage(String.format(BRConst.MSG_SYS_COLOR + "ゲームエリア[%s]座標を(X:%d Z:%d)に設定しました。", args[0],x,z));

			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player!");
				return false;
			}

		} catch (Exception e){
		}
		return true;
	}

}
