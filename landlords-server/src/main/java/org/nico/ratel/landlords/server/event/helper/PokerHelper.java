package org.nico.ratel.landlords.server.event.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;
import org.nico.ratel.landlords.enums.SellType;

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

	public static void sortPoker(List<Poker> pokers){
		Collections.sort(pokers, pokerComparator);
	}

	public static boolean checkPokerIndex(int[] indexes, List<Poker> pokers){
		boolean access = true;
		for(int index: indexes){
			if(index >= pokers.size()){
				access = false;
			}
		}
		return access;
	}

	public static SellType checkPokerType(List<Poker> pokers) {
		if(pokers != null && pokers.isEmpty()) {
			sortPoker(pokers);
			
			int[] levelTable = new int[20];
			for(Poker poker: pokers) {
				levelTable[poker.getLevel().getLevel()] ++; 
			}

			int startIndex = -1;
			int endIndex = -1;
			int count = -1;
			
			int singleCount = 0;
			int doubleCount = 0;
			int threeCount = 0;
			int fourCount = 0;
			for(int index = 0; index < levelTable.length; index ++) {
				int value = levelTable[index];
				if(value != 0) {
					endIndex = index;
					count ++;
					if(startIndex == -1) {
						startIndex = index;
					}
					if(value == 1) {
						singleCount ++;
					}else if(value == 2) {
						doubleCount ++;
					}else if(value == 3) {
						threeCount ++;
					}else if(value == 4) {
						fourCount ++;
					}
				}
			}
			if(count == 1) {
				if(levelTable[startIndex] == 1) {
					return SellType.SINGLE;
				}else if(levelTable[startIndex] == 2) {
					return SellType.DOUBLE;
				}else if(levelTable[startIndex] == 3) {
					return SellType.THREE;
				}else if(levelTable[startIndex] == 4) {
					return SellType.BOMB;
				}
			}
			if(endIndex - startIndex == count - 1 && endIndex < PokerLevel.LEVEL_2.getLevel()) {
				if(levelTable[startIndex] == 1) {
					return SellType.SINGLE_STRAIGHT;
				}else if(levelTable[startIndex] == 2) {
					return SellType.DOUBLE_STRAIGHT;
				}else if(levelTable[startIndex] == 3) {
					return SellType.THREE_STRAIGHT;
				}else if(levelTable[startIndex] == 4) {
					return SellType.FOUR_STRAIGHT;
				}
			}

		}
		return SellType.ILLEGAL;
	}

	public static List<Poker> getPoker(int[] indexes, List<Poker> pokers){
		List<Poker> resultPokers = new ArrayList<>(indexes.length);
		for(int index: indexes){
			resultPokers.add(pokers.get(index - 1));
		}
		sortPoker(resultPokers);
		return resultPokers;
	}

	public static boolean comparePoker(List<Poker> pres, List<Poker> currents){

		return true;
	}

	public static List<List<Poker>> distributePoker(){
		Collections.shuffle(basePokers);
		List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
		List<Poker> pokers1 = new ArrayList<>(17);
		pokers1.addAll(basePokers.subList(0, 17));
		List<Poker> pokers2 = new ArrayList<>(17);
		pokers2.addAll(basePokers.subList(17, 34));
		List<Poker> pokers3 = new ArrayList<>(17);
		pokers3.addAll(basePokers.subList(34, 51));
		List<Poker> pokers4 = new ArrayList<>(3);
		pokers4.addAll(basePokers.subList(51, 54));
		pokersList.add(pokers1);
		pokersList.add(pokers2);
		pokersList.add(pokers3);
		pokersList.add(pokers4);
		for(List<Poker> pokers: pokersList) {
			sortPoker(pokers);
		}
		return pokersList;
	}

	public static String unfoldPoker(List<Poker> pokers, boolean serialFlag) {
		sortPoker(pokers);
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
			if(serialFlag){
				builder.append(System.lineSeparator());
				for(int index = 0; index < pokers.size(); index ++) {
					if(index == 0) {
						builder.append("Index: │");
					}
					String number = String.valueOf(index + 1);
					if(number.length() == 1) number = "0" + number;
					builder.append(number +  "│");
				}
			}
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		List<List<Poker>> pokersList = distributePoker();
		System.out.println(unfoldPoker(pokersList.get(3), false));
		List<Poker> newPokers = pokersList.get(0);
		newPokers.addAll(pokersList.get(3));
		sortPoker(newPokers);
		System.out.println(unfoldPoker(pokersList.get(3), false));
		System.out.println(unfoldPoker(pokersList.get(3), false));
	}
}
