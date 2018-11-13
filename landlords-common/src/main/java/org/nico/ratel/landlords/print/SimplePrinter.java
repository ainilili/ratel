package org.nico.ratel.landlords.print;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.helper.PokerHelper;

public class SimplePrinter {

	private final static SimpleDateFormat FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	
	public static void printPokers(List<Poker> pokers) {
		System.out.println(PokerHelper.printPoker(pokers));
	}
	
	public static void printNotice(String msg) {
		System.out.println(msg);
	}
	
	public static void serverLog(String msg) {
		System.out.println(FORMAT.format(new Date()) + "-> " + msg);
	}
}
