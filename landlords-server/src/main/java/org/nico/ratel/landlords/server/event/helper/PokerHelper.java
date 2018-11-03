package org.nico.ratel.landlords.server.event.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;

public class PokerHelper {

	private static List<Poker> basePokers = new ArrayList<Poker>(54);
	
	private static Comparator<Poker> pokerComparator = new Comparator<Poker>() {
		@Override
		public int compare(Poker o1, Poker o2) {
			return o1.getLevel().getLevel() - o2.getLevel().getLevel();
		}
	};
	
	static {
		PokerLevel[] pokerLevels = PokerLevel.values();
		PokerType[] pokerTypes = PokerType.values();
		
		for(PokerLevel level: pokerLevels) {
			if(level == PokerLevel.LEVEL_BIG_KING) {
				basePokers.add(new Poker(level, PokerType.BLANK));
				continue;
			}
			if(level == PokerLevel.LEVEL_SMALL_KING) {
				basePokers.add(new Poker(level, PokerType.BLANK));
				continue;
			}
			for(PokerType type: pokerTypes) {
				if(type == PokerType.BLANK) {
					continue;
				}
				basePokers.add(new Poker(level, type));
			}
		}
		
	}
	
	public static List<List<Poker>> distributePoker(){
		Collections.shuffle(basePokers);
		List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
		pokersList.add(basePokers.subList(0, 17));
		pokersList.add(basePokers.subList(17, 34));
		pokersList.add(basePokers.subList(34, 51));
		pokersList.add(basePokers.subList(51, 54));
		for(List<Poker> pokers: pokersList) {
			Collections.sort(pokers, pokerComparator);
		}
		return pokersList;
	}
	
	public static String unfoldPoker(List<Poker> pokers) {
		Collections.sort(pokers, pokerComparator);
		StringBuilder builder = new StringBuilder();
		if(pokers != null && pokers.size() > 0) {
			
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("Poker: ┌──┐");
				}else if(index == pokers.size() - 1) {
					builder.append("──┐");
				}else {
					builder.append("──┐");
				}
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("       │");
				}
				String name = pokers.get(index).getLevel().getName();
				builder.append(name + (name.length() == 1 ? " " : "" ) +  "|");
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("       │");
				}
				builder.append(pokers.get(index).getType().getName() + " |");
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("       └──┘");
				}else if(index == pokers.size() - 1) {
					builder.append("──┘");
				}else {
					builder.append("──┘");
				}
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("Index: │");
				}
				String number = String.valueOf(index + 1);
				if(number.length() == 1) number = "0" + number;
				builder.append(number +  "│");
			}
			builder.append(System.lineSeparator());
		}
		return builder.toString();
	}
	
	
	
	public static void main(String[] args) {
		List<List<Poker>> pokersList = distributePoker();
		for(List<Poker> pokers: pokersList) {
			System.out.println(unfoldPoker(pokers));
		}
	}
}
