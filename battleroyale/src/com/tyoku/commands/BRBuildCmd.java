package com.tyoku.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRBuilding;

public class BRBuildCmd extends BRCmdExe {
	private Location location1;
	private Location location2;
	private Location locationBuild;

	public BRBuildCmd(BattleRoyale plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String paramString, String[] args) {
		if(args.length != 1 && args.length != 2 ){
			return false;
		}

		try {
			if ((sender instanceof Player)) {
				Player player = (Player)sender;

				//一覧
				if("list".equals(args[0])){
					if(args.length != 1){
						return false;
					}
					StringBuffer sb = new StringBuffer();
					sb.append(ChatColor.YELLOW + "BR建築物：" + ChatColor.GOLD);
					if(this.plugin.getBrBuilding().size() > 0){
						for(BRBuilding brb : this.plugin.getBrBuilding().values()){
							sb.append(brb.getName()+",");
						}
					}else{
						sb.append("登録されていません。");
					}
					player.sendMessage(sb.toString());
				}

				//建築
				if("create".equals(args[0])){
					if(args.length != 2){
						return false;
					}
					BRBuilding brb = this.plugin.getBrBuilding().get(args[1]);
					if(brb == null || !brb.isCreatable()){
						player.sendMessage(ChatColor.YELLOW + "建築物が存在しないか、建築できる状態ではありません。");
					}
					brb.create(player);
				}

				//保存
				if("save".equals(args[0])){
					if(args.length != 2){
						return false;
					}
					if("".equals(args[1])){
						return false;
					}
					if(this.location1 == null
							|| this.location2 == null
							|| this.locationBuild == null){
						player.sendMessage(ChatColor.YELLOW + "ロケーション「１、２、ホーム」全て設定してください。");
					}

					BRBuilding brb = new BRBuilding(player, args[1], this.location1, this.location2, this.locationBuild);
					if(!brb.isCreatable()){
						player.sendMessage(ChatColor.YELLOW + "建築物の認識に失敗したかもー");
					}
					if(brb.save()){
						player.sendMessage(ChatColor.YELLOW + "建築物を保存しました："+brb.getName()+" ブロック数:"+brb.getBlockNum());
					}else{
						player.sendMessage(ChatColor.YELLOW + "建築物の保存に失敗したかもー");
					}
				}

				//設定
				if("set".equals(args[0])){
					if(args.length != 2){
						return false;
					}
					if("1".equals(args[1])){
						this.location1 = player.getLocation();
						player.sendMessage(String.format(ChatColor.YELLOW + "ロケーション１を座標(X:%d, Y:%d, Z%d)に設定しました。"
								, this.location1.getBlockX()
								, this.location1.getBlockY()
								, this.location1.getBlockZ()));
					}
					if("2".equals(args[1])){
						this.location2 = player.getLocation();
						player.sendMessage(String.format(ChatColor.YELLOW + "ロケーション2を座標(X:%d, Y:%d, Z%d)に設定しました。"
								, this.location2.getBlockX()
								, this.location2.getBlockY()
								, this.location2.getBlockZ()));
					}
					if("home".equals(args[1])){
						this.locationBuild = player.getLocation();
						player.sendMessage(String.format(ChatColor.YELLOW + "建築ホームを座標(X:%d, Y:%d, Z%d)に設定しました。"
								, this.locationBuild.getBlockX()
								, this.locationBuild.getBlockY()
								, this.locationBuild.getBlockZ()));
					}
				}

			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player!");
				return false;
			}

		} catch (Exception e){
			return false;
		}
		return false;
	}

}
