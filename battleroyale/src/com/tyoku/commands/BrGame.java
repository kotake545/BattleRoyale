package com.tyoku.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRGameStatus;
import com.tyoku.dto.BRPlayer;
import com.tyoku.util.BRUtils;

public class BrGame extends BRCmdExe {

	public BrGame(BattleRoyale plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		if(paramArrayOfString.length != 1 && paramArrayOfString.length != 2 ){
			return false;
		}


		Player p = (Player)paramCommandSender;

		if("start".equals(paramArrayOfString[0])){
			if(paramArrayOfString.length != 1){
				return false;
			}
			if(!BRGameStatus.PREPARE.equals(this.plugin.getBrManager().getGameStatus())){
				paramCommandSender.sendMessage(ChatColor.RED + "既に開始済みです。");
				return true;
			}
			BRUtils.announce(this.plugin, "さぁ、ゲームの始まりです！");
			Player[] ps = this.plugin.getServer().getOnlinePlayers();

			//参加者にバトロワMAP配布
			for(int i = 0; i < ps.length; i++){
				ps[i].getInventory().addItem(BRUtils.getBRMap(plugin, ps[i], (short)i));
			}

			this.plugin.getBrManager().setGameStatus(BRGameStatus.PLAYING);

		}else if("stop".equals(paramArrayOfString[0])){
			if(paramArrayOfString.length != 1){
				return false;
			}
			BRUtils.announce(this.plugin, "ゲームを終了します！");
			this.plugin.getBrManager().setGameStatus(BRGameStatus.END);

		}else if("status".equals(paramArrayOfString[0])){
			if(paramArrayOfString.length != 1 && paramArrayOfString.length != 2 ){
				return false;
			}

			if(paramArrayOfString.length != 2){
				p.sendMessage(String.format(
						"バトロワ：%s"
						,this.plugin.getBrManager().getGameStatus()));
				this.plugin.getBrManager().setGameStatus(BRGameStatus.END);
			}else{
				if(paramCommandSender instanceof Player){
					BRPlayer brp = this.plugin.getPlayerStat().get(paramArrayOfString[1]);
					String pstat = brp==null?"?":brp.getStatus().toString();
					p.sendMessage(String.format(
							"バトロワ：%s is %s."
							, paramArrayOfString[1],pstat));
					this.plugin.getBrManager().setGameStatus(BRGameStatus.END);
				}else{
					paramCommandSender.sendMessage(ChatColor.RED + "You must be a player!");
				}
			}

		}else{
			return false;
		}
		return true;
	}

}
