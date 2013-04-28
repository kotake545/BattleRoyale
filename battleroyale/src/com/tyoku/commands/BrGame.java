package com.tyoku.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.tyoku.BattleRoyale;
import com.tyoku.util.BRStatus;
import com.tyoku.util.BRUtils;

public class BrGame extends BRCmdExe {

	public BrGame(BattleRoyale plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		if(paramArrayOfString.length != 1){
			return false;
		}
		if("start".equals(paramArrayOfString[0])){
			BRUtils.announce(this.plugin, "さぁ、ゲームの始まりです！");
			this.plugin.getManager().setGameStatus(BRStatus.START);

		}else if("stop".equals(paramArrayOfString[0])){
			BRUtils.announce(this.plugin, "ゲームを終了します！");
			this.plugin.getManager().setGameStatus(BRStatus.END);

		}else{
			return false;
		}
		return true;
	}

}
