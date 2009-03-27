package org.pausequafe.data.business;

public class ServerStatus {
	private boolean onLine;
	private boolean unknown;
	private int playerCount;
	
	public ServerStatus(){
		unknown = true;
		onLine = false;
		playerCount = 0;
	}

	public ServerStatus(boolean isOnLine, int playerCount){
		this.unknown = false;
		this.onLine = isOnLine;
		this.playerCount = playerCount;
	}

	public boolean isOnLine() {
		return onLine;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setOnLine(boolean isOnLine) {
		this.onLine = isOnLine;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public void setUnknown(boolean isUnknown) {
		this.unknown = isUnknown;
	}

	public boolean isUnknown() {
		return unknown;
	}



}
