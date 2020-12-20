package org.nico.ratel.landlords.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;

/**
 * Trial game algorithm
 *
 * @author nico
 * @date 2020-12-19 16:36
 */
public class MediumRobotDecisionMakers extends AbstractRobotDecisionMakers{

	@Override
	public PokerSell howToPlayPokers(PokerSell lastPokerSell, ClientSide robot) {
		if(lastPokerSell != null && lastPokerSell.getSellType() == SellType.KING_BOMB) {
			return null;
		}
		List<Poker> selfPoker = PokerHelper.clonePokers(robot.getPokers());
		List<Poker> leftPoker = PokerHelper.clonePokers(robot.getPre().getPokers());
		List<Poker> rightPoker = PokerHelper.clonePokers(robot.getNext().getPokers());
		PokerHelper.sortPoker(selfPoker);
		PokerHelper.sortPoker(leftPoker);
		PokerHelper.sortPoker(rightPoker);
		
		List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
		pokersList.add(selfPoker);
		pokersList.add(rightPoker);
		pokersList.add(leftPoker);
		
		List<PokerSell> sells = validSells(lastPokerSell, selfPoker);
		if(sells == null || sells.size() == 0) {
			return null;
		}
		PokerSell bestSell = null;
		Long weight = null;
		Map<String, Long> dp = new HashMap<String, Long>();
		for(PokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(selfPoker);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				return sell;
			}
			pokersList.set(0, pokers);
			AtomicLong counter = new AtomicLong();
			deduce(0, sell, 1, pokersList, counter, dp);
			if(weight == null) {
				bestSell = sell;
				weight = counter.get();
			}else if (counter.get() > weight){
				bestSell = sell;
				weight = counter.get();
			}
			pokersList.set(0, selfPoker);
		}
		return bestSell;
	}
	
	private Boolean deduce(int sellCursor, PokerSell lastPokerSell, int cursor, List<List<Poker>> pokersList, AtomicLong counter, Map<String, Long> dp) {
		if(cursor > 2) {
			cursor = 0;
		}
		if(sellCursor == cursor) {
			lastPokerSell = null;
		}
		
		List<Poker> original = pokersList.get(cursor);
		List<PokerSell> sells = validSells(lastPokerSell, original);
		if(sells == null || sells.size() == 0) {
			if(sellCursor != cursor) {
				return deduce(sellCursor, lastPokerSell, cursor + 1, pokersList, counter, dp);
			}
		}
		for(PokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(original);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				return cursor == 0;
			}else {
				pokersList.set(cursor, pokers);
				
				String key = serialKey(cursor, sell, cursor + 1, pokersList);
				Long score = dp.get(key);
				if(score != null) {
					counter.addAndGet(score);
				}else {
					Boolean suc = deduce(cursor, sell, cursor + 1, pokersList, counter, dp);
					if(cursor != 0) {
						return suc;
					}
					if(suc != null) {
						score = (long)(suc ? 1 : -1);
						counter.addAndGet(score);
						dp.put(key, score);	
					}
				}
//				if(counter.get() < -999999999 || counter.get() > 999999999) {
//					return;
//				}
				pokersList.set(cursor, original);
			}
		}
		return null;
	}
	
	private String serialKey(int sellCursor, PokerSell lastPokerSell, int cursor, List<List<Poker>> pokersList) {
		return sellCursor + "$" + (lastPokerSell == null ? "[]" : serialPokers(lastPokerSell.getSellPokers())) + "$" + cursor + "$" + serialPokersList(pokersList);
	}
	
	private static String serialPokers(List<Poker> pokers){
		if(pokers == null || pokers.size() == 0) {
			return "[]";
		}
		StringBuilder builder = new StringBuilder();
		if(pokers != null && pokers.size() > 0) {
			for(int index = 0; index < pokers.size(); index ++) {
				builder.append(pokers.get(index).getLevel() + (index == pokers.size() - 1 ? "" : ","));
			}
		}
		return builder.toString();
	}
	
	private static String serialPokersList(List<List<Poker>> pokersList){
		StringBuilder builder = new StringBuilder();
		for(int index = 0; index < pokersList.size(); index ++) {
			List<Poker> pokers = pokersList.get(index);
			builder.append(serialPokers(pokers) + (index == pokersList.size() - 1 ? "" : "|"));
		}
		return builder.toString();
	}
	
	private List<PokerSell> validSells(PokerSell lastPokerSell, List<Poker> pokers) {
		List<PokerSell> sells = PokerHelper.parsePokerSells(pokers);
		if(lastPokerSell == null) {
			return sells;
		}
		
		List<PokerSell> validSells = new ArrayList<PokerSell>();
		for(PokerSell sell: sells) {
			if(sell.getSellType() == lastPokerSell.getSellType()) {
				if(sell.getScore() > lastPokerSell.getScore() && sell.getSellPokers().size() == lastPokerSell.getSellPokers().size()) {
					validSells.add(sell);
				}
			}
			if(sell.getSellType() == SellType.KING_BOMB) {
				validSells.add(sell);
			}
		}
		if(lastPokerSell.getSellType() != SellType.BOMB) {
			for(PokerSell sell: sells) {
				if(sell.getSellType() == SellType.BOMB) {
					validSells.add(sell);
				}
			}
		}
		return validSells;
	}
	

	@Override
	public boolean howToChooseLandlord(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		int leftScore = PokerHelper.parsePokerColligationScore(leftPokers);
		int rightScore = PokerHelper.parsePokerColligationScore(rightPokers);
		int myScore = PokerHelper.parsePokerColligationScore(myPokers);
		return myScore >= (leftScore + rightScore)/2;
	}

}
