package com.github.tavi.plague.sciacallo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.tavi.plague.shared.Plogger;

/**
 * The "Sciacallo" version of Plogger.
 * Writes to the maximum of 3 files (the older are deleted at the application start),
 * at the same time logging to the console. All logging levels are on by default.
 * @see Plogger
 */
public class Noto implements Plogger {
	
	private static volatile Noto INSTANCE = null;
	
	/**
	 * Get a singleton instance of the Noto logger
	 * @return Noto logger singleton
	 * @see Plogger
	 * @see Noto
	 */
	public static Noto get() {
		if (INSTANCE == null) {
			synchronized (Noto.class) {
				INSTANCE = new Noto();
			}
		}
		return INSTANCE;
	}
	
	
	
	//private String tag = "Plague";
	private long lastUpdate = System.currentTimeMillis();
	private FileHandle fh;
	
	/**
	 * Creates a Noto logger instance, also creating a new .log file and deleting the old ones,
	 * if there are more than 3 of them.
	 */
	private Noto() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		String formattedDate = format.format(date);
		fh = Gdx.files.local("logs/" + formattedDate + ".log");
		FileHandle[] files = Gdx.files.local("logs/").list(".log");
		List<String> names = new ArrayList<>(Arrays.stream(files).map(fh -> fh.name()).sorted().toList());
		if (names.size() > 3) {
			for (int i = 0; i < names.size() - 2; i++) {
				Gdx.files.local("logs/" + names.get(i)).delete();
			}
		}
	}

	@Override
	public void info(Object obj, Object... messages) {
		String tag =  obj.getClass().getName();
		String joined = join(messages);
		String result = String.format("[INFO] (%ss) %s", getDelay(), joined);
		Gdx.app.log(tag, result);
		write(tag, result);
	}

	@Override
	public void error(Object obj, Exception e, Object... messages) {
		String tag =  obj.getClass().getName();
		String joined = join(messages);
		String stackTrace = String.join("\n\t", Arrays.stream(e.getStackTrace()).map(st -> st.toString()).toList());
		
		String result = String.format("[ERROR] (%ss) %s %n%s %n%s", getDelay(), joined, e.getLocalizedMessage(), stackTrace);
		Gdx.app.log(tag, result);
		write(tag, result);
	}

	@Override
	public void debug(Object obj, Object... messages) {
		String tag =  obj.getClass().getName();
		String joined = join(messages);
		String result = String.format("[DEBUG] (%ss) %s", getDelay(), joined);
		Gdx.app.log(tag, result);
		write(tag, result);
	}
	
	/**
	 * Returns a String of the specified Objects' toString() values, divided by a whitespace
	 * @param messages An Object array, which must be transformed to a string
	 * @return A result of joining the Objects' toString() values into a single String
	 */
	private String join(Object... messages) {
		return String.join(" ", Arrays.stream(messages).map(o -> o.toString()).toList());
	}
	
	/**
	 * Used to measure the performance of different parts of the code
	 * @return Time passed since the last time logging any message
	 */
	private String getDelay() {
		long time = System.currentTimeMillis();
		long delay = time - lastUpdate;
		lastUpdate = time;
		return String.format("%.3f", ((float)delay / 1000));
	}
	
	/**
	 * Writes a message to the current .log file, accompanied by a time stamp.
	 * The method is synchronized, which means it is safe to use from different threads.
	 * @param message Some text to write to a .log file
	 */
	private void write(String tag, String message) {
		synchronized (this) {
			fh.writeString(new Timestamp(System.currentTimeMillis()).toString(), true);
			
			fh.writeString(String.format("[%s] %s", tag, message), true);
			
			fh.writeString("\n", true);
		}
	}

}
