package com.tyoku.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.BattleRoyale;
import com.tyoku.dto.BRBuilding;

public class BRBuildCmd extends BRCmdExe {

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

				if(!player.isOp()){
					player.sendMessage(ChatColor.RED + "権限不足");
					return true;
				}

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
					return true;
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
					return brb.create(player.getWorld(), player.getLocation());
				}

				//保存
				if("save".equals(args[0])){
					if(args.length != 2){
						return false;
					}

					if(this.plugin.getLocation1() == null
							|| this.plugin.getLocation2() == null
							|| this.plugin.getLocationBuild() == null){
						player.sendMessage(ChatColor.YELLOW + "ロケーション「１、２、ホーム」全て設定してください。");
					}

					BRBuilding brb = new BRBuilding(player, args[1], this.plugin.getLocation1(), this.plugin.getLocation2(), this.plugin.getLocationBuild());
					if(!brb.isCreatable()){
						player.sendMessage(ChatColor.YELLOW + "建築物の認識に失敗したかもー");
					}else if(brb.save(this.plugin)){
						player.sendMessage(ChatColor.YELLOW + "建築物を保存しました："+brb.getName()+" ブロック数:"+brb.getBlockNum());
					}else{
						player.sendMessage(ChatColor.YELLOW + "建築物の保存に失敗したかもー");
					}
					return true;
				}

				//設定
				if("set".equals(args[0])){
					if(args.length != 2){
						return false;
					}

					if("1".equals(args[1])){
						this.plugin.setLocation1(player.getLocation());
						player.sendMessage(String.format(ChatColor.YELLOW + "ロケーション１を座標(X:%d, Y:%d, Z:%d)に設定しました。"
								, this.plugin.getLocation1().getBlockX()
								, this.plugin.getLocation1().getBlockY()
								, this.plugin.getLocation1().getBlockZ()));
					}
					if("2".equals(args[1])){
						this.plugin.setLocation2(player.getLocation());
						player.sendMessage(String.format(ChatColor.YELLOW + "ロケーション2を座標(X:%d, Y:%d, Z:%d)に設定しました。"
								, this.plugin.getLocation2().getBlockX()
								, this.plugin.getLocation2().getBlockY()
								, this.plugin.getLocation2().getBlockZ()));
					}
					if("home".equals(args[1])){
						this.plugin.setLocationBuild(player.getLocation());
						player.sendMessage(String.format(ChatColor.YELLOW + "建築ホームを座標(X:%d, Y:%d, Z:%d)に設定しました。"
								, this.plugin.getLocationBuild().getBlockX()
								, this.plugin.getLocationBuild().getBlockY()
								, this.plugin.getLocationBuild().getBlockZ()));
					}
					return true;
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
