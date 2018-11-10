package org.nico.ratel.landlords.print;

import java.util.List;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.helper.PokerHelper;

public class SimplePrinter {

	public static void printPokers(List<Poker> pokers) {
		System.out.println(PokerHelper.printPoker(pokers));
	}
	
	public static void printNotice(String msg) {
		System.out.println(msg);
	}
}
