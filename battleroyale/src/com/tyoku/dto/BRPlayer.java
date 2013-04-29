package com.tyoku.dto;

public class BRPlayer {
	private String name;
	private BRPlayerStatus status;
	private int playCount = 0;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BRPlayerStatus getStatus() {
		return status;
	}
	public void setStatus(BRPlayerStatus status) {
		this.status = status;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
}
