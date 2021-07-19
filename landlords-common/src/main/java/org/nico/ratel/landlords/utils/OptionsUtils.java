package org.nico.ratel.landlords.utils;

public class OptionsUtils {

	public static int getOptions(String line) {
		int option = -1;
		try {
			option = Integer.parseInt(line);
		} catch (Exception ignored) {}
		return option;
	}
}
