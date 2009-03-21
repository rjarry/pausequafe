package org.jevemon.misc.util;

import org.jevemon.data.business.ServerStatus;
import org.jevemon.data.dao.ServerStatusFactory;

import com.trolltech.qt.QSignalEmitter;

public class ServerStatusRequest extends QSignalEmitter implements Runnable {

	private ServerStatus status;
	public Signal1<ServerStatus> finished;
	
	public ServerStatusRequest(){
		finished = new Signal1<ServerStatus>();
	}
	
	public void run() {
		status = ServerStatusFactory.getStatus();
		finished.emit(status);
	}

}
