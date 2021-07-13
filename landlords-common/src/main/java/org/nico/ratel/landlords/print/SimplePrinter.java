package org.nico.ratel.landlords.print;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.helper.PokerHelper;

public class SimplePrinter {

	private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static int pokerDisplayFormat = 0;

	public static void printPokers(List<Poker> pokers) {
		System.out.println(PokerHelper.printPoker(pokers));
	}

	public static void printNotice(String msg) {
		System.out.println(msg);
	}

	public static void printNotice(String msgKey, String locale) {
		//TODO : read locale
		Map<String, Map<String, String>> map = new HashMap<>();
		map.put("english", new HashMap<>());
		map.get("eng").put("caterpillar", "caterpillar's message!!");

		System.out.println(map.get(locale).get(msgKey));
	}

	public static void serverLog(String msg) {
		System.out.println(FORMAT.format(new Date()) + "-> " + msg);
	}
}
