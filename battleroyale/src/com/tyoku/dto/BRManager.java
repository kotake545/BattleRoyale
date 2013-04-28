package com.tyoku.dto;

import com.tyoku.util.BRStatus;

public class BRManager {
	private BRStatus gameStatus = BRStatus.PREPARE;

	public BRStatus getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(BRStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
}
