package com.github.tavi.plague.testing;

import com.github.tavi.plague.util.logging.Plogger;

/**
 * This thread logs three messages in a row to a file (and a console)
 * using Plogger. Used to check if multi-threaded logging works fine,
 * without creating multiple files or other issues
 */
public class LoggingThread extends Thread {

	private final int id;
	private final Plogger log;
	
	/**
	 * Creates a thread with the specified id, which logs three messages in a row
	 * to a file using the default Plogger singleton once started
	 * @param id Some number to distinguish this thread from the others
	 */
	public LoggingThread(int id) {
		this.id = id;
		log = Plogger.get();
	}
	
	@Override
	public void run() {
		for (int i = 1; i <= 3; i++) {
			log.debug("Thread #", id, "counted to", i);
		}
	}
	
}
