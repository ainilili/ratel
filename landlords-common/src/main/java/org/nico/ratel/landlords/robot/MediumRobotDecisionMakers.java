package org.nico.ratel.landlords.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
		
		List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
		pokersList.add(selfPoker);
		pokersList.add(rightPoker);
		pokersList.add(leftPoker);
		
		List<PokerSell> sells = validSells(lastPokerSell, selfPoker);
		if(sells == null || sells.size() == 0) {
			return null;
		}
		PokerSell bestSell = null;
		Integer weight = null;
		for(PokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(selfPoker);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				return sell;
			}
			pokersList.set(0, pokers);
			AtomicInteger counter = new AtomicInteger();
			deduce(0, sell, 1, pokersList, counter);
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
	
	private void deduce(int sellCursor, PokerSell lastPokerSell, int cursor, List<List<Poker>> pokersList, AtomicInteger counter) {
		if(cursor > 2) {
			cursor = 0;
		}
		List<Poker> original = pokersList.get(cursor);
		List<PokerSell> sells = validSells(lastPokerSell, original);
		if(sells == null || sells.size() == 0) {
			if(sellCursor != cursor) {
				deduce(sellCursor, lastPokerSell, cursor + 1, pokersList, counter);
			}else {
				deduce(sellCursor, null, cursor, pokersList, counter);
			}
		}
		
		for(PokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(original);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				counter.addAndGet(cursor != 0 ? -1 : 1);
				return;
			}else {
				pokersList.set(cursor, pokers);
				deduce(cursor, sell, cursor + 1, pokersList, counter);
				if(counter.get() < -999999 || counter.get() > 999999) {
					return;
				}
				pokersList.set(cursor, original);
			}
		}
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
