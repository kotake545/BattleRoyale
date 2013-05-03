package com.tyoku.dto;

import java.io.Serializable;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BRBuilding implements Serializable {
	private String name;
	private List<Block> blocks;

	public BRBuilding(Player player, String name, Location loc1, Location loc2, Location standLoc) {
		this.setName(name);
	}

	/**
	 * 指定のロケーションにBR建造物を建てる。
	 * @param world
	 * @param buildLocation
	 */
	public void create(Player player){
	}

	/**
	 * このクラスオブジェクトシリアライズしてファイルとして保存する。
	 */
	public boolean save(){
		return true;
	}

	public boolean isCreatable(){
		return blocks != null && blocks.size() > 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getBlockNum(){
		return blocks.size();
	}
}
