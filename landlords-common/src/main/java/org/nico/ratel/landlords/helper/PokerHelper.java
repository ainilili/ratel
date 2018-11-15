package org.nico.ratel.landlords.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;
import org.nico.ratel.landlords.enums.SellType;

public class PokerHelper {
	
	/**
	 * Print the type of poker style 
	 */
	public static int pokerPrinterType = 0;
	public static int totalPrinters = 5;
	
	/**
	 * The list of all pokers, by 54
	 */
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
	
	public static int[] getIndexes(Character[] options, List<Poker> pokers) {
		List<Poker> copyList = new ArrayList<>(pokers.size());
		copyList.addAll(pokers);
		int[] indexes = new int[options.length];
		for(int index = 0; index < options.length; index ++) {
			char option = options[index];
			boolean isTarget = false;
			for(int pi = 0; pi < copyList.size(); pi ++) {
				Poker poker = copyList.get(pi);
				if(poker != null) {
					if(Arrays.asList(poker.getLevel().getAlias()).contains(option)) {
						isTarget = true;
						//Index start from 1, not 0
						indexes[index] = pi + 1;
						copyList.set(pi, null);
						break;
					}
				}
			}
			if(! isTarget) {
				return null;
			}
		}
		Arrays.sort(indexes);
		return indexes;
	}

	public static boolean checkPokerIndex(int[] indexes, List<Poker> pokers){
		boolean access = true;
		if(indexes == null || indexes.length == 0) {
			access = false;
		}else {
			for(int index: indexes){
				if(index > pokers.size() || index < 1){
					access = false;
				}
			}
		}
		return access;
	}

	public static PokerSell checkPokerType(List<Poker> pokers) {
		
		if(pokers != null && ! pokers.isEmpty()) {
			sortPoker(pokers);
			int score = -1;
			
			int[] levelTable = new int[20];
			for(Poker poker: pokers) {
				levelTable[poker.getLevel().getLevel()] ++; 
			}

			int startIndex = -1;
			int endIndex = -1;
			int count = 0;
			
			int singleCount = 0;
			int doubleCount = 0;
			int threeCount = 0;
			int threeStartIndex = -1;
			int threeEndIndex = -1;
			int fourCount = 0;
			int fourStartIndex = -1;
			int fourEndIndex = -1;
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
						if(threeStartIndex == -1) {
							threeStartIndex = index;
						}
						threeEndIndex = index;
						threeCount ++;
					}else if(value == 4) {
						if(fourStartIndex == -1) {
							fourStartIndex = index;
						}
						fourEndIndex = index;
						fourCount ++;
					}
				}
			}
			
			if(singleCount == doubleCount && singleCount == threeCount && singleCount == 0 && fourCount == 1) {
				score = startIndex * 4 + 999;
				return new PokerSell(score, SellType.BOMB, pokers);
			}
			
			if(singleCount == 2 && startIndex == PokerLevel.LEVEL_SMALL_KING.getLevel() && endIndex == PokerLevel.LEVEL_BIG_KING.getLevel()) {
				score = Integer.MAX_VALUE;
				return new PokerSell(score, SellType.KING_BOMB, pokers);
			}
			
			if(startIndex == endIndex) {
				score = startIndex;
				if(levelTable[startIndex] == 1) {
					return new PokerSell(score, SellType.SINGLE, pokers);
				}else if(levelTable[startIndex] == 2) {
					return new PokerSell(score, SellType.DOUBLE, pokers);
				}else if(levelTable[startIndex] == 3) {
					return new PokerSell(score, SellType.THREE, pokers);
				}
			}
			if(endIndex - startIndex == count - 1 && endIndex < PokerLevel.LEVEL_2.getLevel()) {
				score = endIndex;
				if(levelTable[startIndex] == 1 && singleCount > 4 && doubleCount + threeCount + fourCount == 0) {
					return new PokerSell(score, SellType.SINGLE_STRAIGHT, pokers);
				}else if(levelTable[startIndex] == 2 && doubleCount > 2 && singleCount + threeCount + fourCount == 0) {
					return new PokerSell(score, SellType.DOUBLE_STRAIGHT, pokers);
				}else if(levelTable[startIndex] == 3 && threeCount > 1 && doubleCount + singleCount + fourCount == 0) {
					return new PokerSell(score, SellType.THREE_STRAIGHT, pokers);
				}else if(levelTable[startIndex] == 4 && fourCount > 1 && doubleCount + threeCount + singleCount == 0) {
					return new PokerSell(score, SellType.FOUR_STRAIGHT, pokers);
				}
			}
			
			if(threeCount != 0) {
				score = threeEndIndex;
				if(singleCount != 0 && singleCount == threeCount && doubleCount == 0 && fourCount == 0) {
					if(threeCount == 1) {
						return new PokerSell(score, SellType.THREE_ZONES_SINGLE, pokers);
					}else {
						if(threeEndIndex - threeStartIndex + 1 == threeCount && threeEndIndex < PokerLevel.LEVEL_2.getLevel()) {
							return new PokerSell(score, SellType.THREE_STRAIGHT_WITH_SINGLE, pokers);
						}
					}
				}else if(doubleCount != 0 && doubleCount == threeCount && singleCount == 0 && fourCount == 0) {
					if(threeCount == 1) {
						return new PokerSell(score, SellType.THREE_ZONES_DOUBLE, pokers);
					}else {
						if(threeEndIndex - threeStartIndex + 1 == threeCount && threeEndIndex < PokerLevel.LEVEL_2.getLevel()) {
							return new PokerSell(score, SellType.FOUR_STRAIGHT_WITH_DOUBLE, pokers);
						}
					}
				}
			}
			
			if(fourCount != 0) {
				score = fourEndIndex;
				if(singleCount != 0 && singleCount == fourCount * 2 && doubleCount == 0 && threeCount == 0) {
					if(fourCount == 1) {
						return new PokerSell(score, SellType.FOUR_ZONES_SINGLE, pokers);
					}else {
						if(fourEndIndex - fourStartIndex + 1 == fourCount && fourEndIndex < PokerLevel.LEVEL_2.getLevel()) {
							return new PokerSell(score, SellType.FOUR_STRAIGHT_WITH_SINGLE, pokers);
						}
					}
				}else if(doubleCount != 0 && doubleCount == fourCount * 2 && singleCount == 0 && threeCount == 0) {
					if(fourCount == 1) {
						return new PokerSell(score, SellType.FOUR_ZONES_DOUBLE, pokers);
					}else {
						if(fourEndIndex - fourStartIndex + 1 == fourCount && fourEndIndex < PokerLevel.LEVEL_2.getLevel()) {
							return new PokerSell(score, SellType.FOUR_STRAIGHT_WITH_DOUBLE, pokers);
						}
					}
				}
			}
		}
		return new PokerSell(-1, SellType.ILLEGAL, null);
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

	public static String printPoker(List<Poker> pokers) {
		sortPoker(pokers);
		switch(pokerPrinterType){
			case 0:
			return buildHandStringSharp(pokers);
			case 1:
			return buildHandStringRounded(pokers);
			case 2:
			return textOnly(pokers);
			case 3:
			return textOnlyNoType(pokers);
			case 4:
			return playingCardUnicode(pokers);
			default:
			return buildHandStringSharp(pokers);

		}
		
	}
	private static String buildHandStringSharp(List<Poker> pokers){
		StringBuilder builder = new StringBuilder();
		if(pokers != null && pokers.size() > 0) {

			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("┌──┐");
				}else {
					builder.append("──┐");
				}
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("│");
				}
				String name = pokers.get(index).getLevel().getName();
				builder.append(name + (name.length() == 1 ? " " : "" ) +  "|");
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("│");
				}
				builder.append(pokers.get(index).getType().getName() + " |");
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("└──┘");
				} else {
					builder.append("──┘");
				}
			}
		}
		return builder.toString();
	}
	private static String buildHandStringRounded(List<Poker> pokers){
		StringBuilder builder = new StringBuilder();
		if(pokers != null && pokers.size() > 0) {

			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("┌──╮");
				}else {
					builder.append("──╮");
				}
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("│");
				}
				String name = pokers.get(index).getLevel().getName();
				builder.append(name + (name.length() == 1 ? " " : "" ) +  "|");
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("│");
				}
				builder.append(pokers.get(index).getType().getName() + " |");
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				if(index == 0) {
					builder.append("└──╯");
				} else {
					builder.append("──╯");
				}
			}
		}
		return builder.toString();
	}

	private static String textOnly(List<Poker> pokers){
		StringBuilder builder = new StringBuilder();
		if(pokers != null && pokers.size() > 0) {
			for(int index = 0; index < pokers.size(); index ++) {
				String name = pokers.get(index).getLevel().getName();
				builder.append(name+" ");
			}
			builder.append(System.lineSeparator());
			for(int index = 0; index < pokers.size(); index ++) {
				String type = pokers.get(index).getType().getName();
				builder.append(type+" ".repeat(pokers.get(index).getLevel().getName().length()));
			}

		}
		return builder.toString();
	}
	private static String playingCardUnicode(List<Poker> pokers){
		return pokers.stream().map(
			elt -> {
				int level = elt.getLevel().getLevel();
				if (level == 16){//s
					return String.valueOf(Character.toChars(0x1F0DF));
				}
				if (level == 17){//x
					return String.valueOf(Character.toChars(0x1F0CF));
				}
				if (level == 14 || level == 15){level = level - 13;}
				level--;
				switch (elt.getType()) {
					case SPADE:
					return String.valueOf(Character.toChars(0x1F0A1 + level));
					case HEART:
					return String.valueOf(Character.toChars(0x1F0B1 + level));
					case DIAMOND:
					return String.valueOf(Character.toChars(0x1F0C1 + level));
					case CLUB:
					return String.valueOf(Character.toChars(0x1F0D1 + level));
					default:
					return "";
				}
			}
		).collect(Collectors.joining(""));
	}
	private static String textOnlyNoType(List<Poker> pokers){
		StringBuilder builder = new StringBuilder();
		if(pokers != null && pokers.size() > 0) {
			for(int index = 0; index < pokers.size(); index ++) {
				String name = pokers.get(index).getLevel().getName();
				builder.append(name);
			}
		}
		return builder.toString();
	}
	
	public static List<PokerSell> parsePokerSells(List<Poker> pokers){
		List<PokerSell> allPokerSell = new ArrayList<>();
		//single or double or three or four
		int count = 0;
		int lastLevel = -1;
		List<Poker> ps = new ArrayList<>();
		for(int index = 0; index < pokers.size(); index ++){
			int level = pokers.get(index).getLevel().getLevel();
			if(lastLevel == -1 || level == lastLevel){
				++ count;
				ps.add(pokers.get(index));
			}else{
				count = 0;
				ps.clear();
			}
			if(count == 1){
				allPokerSell.add(new PokerSell(level, SellType.SINGLE, ps));
			}else if(count == 2){
				allPokerSell.add(new PokerSell(level, SellType.DOUBLE, ps));
			}else if(count == 3){
				allPokerSell.add(new PokerSell(level, SellType.THREE, ps));
			}else if(count == 4){
				allPokerSell.add(new PokerSell(level + 999, SellType.BOMB, ps));
			}
		}
		
		
		return null;
	}
	
	public static int parsePokerColligationScore(List<Poker> pokers){
		int score = 0;
		int count = 0;
		int increase = 0;
		int lastLevel = -1;
		if(pokers != null && ! pokers.isEmpty()){
			for(int index = 0; index < pokers.size(); index ++){
				int level = pokers.get(index).getLevel().getLevel();
				if(lastLevel == -1){
					increase ++;
					count ++;
					score += lastLevel;
				}else{
					if(level == lastLevel){
						++ count;
					}else{
						count = 0;
					}
					if(level < PokerLevel.LEVEL_2.getLevel() && level - 1 == lastLevel){
						++ increase;
					}else{
						increase = 0;
					}
					
					score += (count + (increase > 4 ? increase : 0)) * level;
				}
				
				if(level == PokerLevel.LEVEL_2.getLevel()){
					score += level * 2;
				}else if(level > PokerLevel.LEVEL_2.getLevel()){
					score += level * 3;
				}
				lastLevel = level;
			}
		}
		return score;
	}
}
