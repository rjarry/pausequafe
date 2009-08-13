package org.pausequafe.misc.util;

import org.pausequafe.data.business.ServerStatus;
import org.pausequafe.data.dao.ServerStatusFactory;

import com.trolltech.qt.QSignalEmitter;

public class ServerStatusRequest extends QSignalEmitter implements Runnable {

	private ServerStatus status;
	public Signal0 requestStarted = new Signal0();
	public Signal1<ServerStatus> requestFinished;
	
	public ServerStatusRequest(){
		requestFinished = new Signal1<ServerStatus>();
	}
	
	public void run() {
		requestStarted.emit();
		status = ServerStatusFactory.getStatus();
		requestFinished.emit(status);
	}

}
