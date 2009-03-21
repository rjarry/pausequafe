package org.jevemon.data.business;

public class ServerStatus {
	private boolean isOnLine;
	private boolean isUnknown;
	private int playerCount;
	
	public ServerStatus(){
		this.setUnknown(true);
		this.isOnLine = false;
		this.playerCount = 0;
	}

	public ServerStatus(boolean isOnLine, int playerCount){
		this.setUnknown(false);
		this.isOnLine = isOnLine;
		this.playerCount = playerCount;
	}

	public boolean isOnLine() {
		return isOnLine;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public void setUnknown(boolean isUnknown) {
		this.isUnknown = isUnknown;
	}

	public boolean isUnknown() {
		return isUnknown;
	}



}
