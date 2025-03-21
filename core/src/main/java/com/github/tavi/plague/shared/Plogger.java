package com.github.tavi.plague.shared;

import com.github.tavi.plague.sciacallo.Noto;

/**
 * "Plogger" stands for "Plague logger".
 * Implementations are used to log messages (either into the console or a file, or both)
 * @see Noto
 */
public interface Plogger {
	
	/**
	 * Get the singleton instance of a Plogger logger. <br>
	 * As of v1.0.0 (Sciacallo) Noto is used.
	 * @return the singleton of Noto logger
	 * @see Noto
	 */
	public static Plogger get() {
		return Noto.get();
	}
	
	/**
	 * Logs any kind of Objects to a console, given that they have a toString() method.
	 * The resulting String consists of this toString() calls, divided by a whitespace.
	 * @param messages Some toString'able information to log
	 */
	public void info(Object... messages);
	
	/**
	 * Logs an exception and some informative message alongside with it
	 * @param e Caught exception
	 * @param messages Some toString'able information to log
	 */
	public void error(Exception e, Object... messages);
	
	/**
	 * Same as {@link #Plogger.info(Object... messages)}, but is preferably used to
	 * log some garbage information that should not be included to a final
	 * build's .log files
	 * @param messages
	 */
	public void debug(Object... messages);
	
}
