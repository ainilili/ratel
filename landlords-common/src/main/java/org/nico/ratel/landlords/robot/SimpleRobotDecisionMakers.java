package org.nico.ratel.landlords.robot;

import java.util.List;
import java.util.Random;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月15日 上午12:13:49
 */

public class SimpleRobotDecisionMakers extends AbstractRobotDecisionMakers{

	private static Random random = new Random();
	
	@Override
	public PokerSell howToPlayPokers(PokerSell lastPokerSell, List<Poker> myPokers) {
		
		if(lastPokerSell != null && lastPokerSell.getSellType() == SellType.KING_BOMB) {
			return null;
		}
		
		List<PokerSell> sells = PokerHelper.parsePokerSells(myPokers);
		if(lastPokerSell == null) {
			return sells.get(random.nextInt(sells.size()));
		}
		
		for(PokerSell sell: sells) {
			if(sell.getSellType() == lastPokerSell.getSellType()) {
				if(sell.getScore() > lastPokerSell.getScore() && sell.getSellPokers().size() == lastPokerSell.getSellPokers().size()) {
					return sell;
				}
			}
		}
		if(lastPokerSell.getSellType() != SellType.BOMB) {
			for(PokerSell sell: sells) {
				if(sell.getSellType() == SellType.BOMB) {
					return sell;
				}
			}
		}
		for(PokerSell sell: sells) {
			if(sell.getSellType() == SellType.KING_BOMB) {
				return sell;
			}
		}
		return null;
	}

	@Override
	public boolean howToChooseLandlord(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		List<PokerSell> leftSells = PokerHelper.parsePokerSells(leftPokers);
		List<PokerSell> mySells = PokerHelper.parsePokerSells(myPokers);
		List<PokerSell> rightSells = PokerHelper.parsePokerSells(rightPokers);
		return mySells.size() > leftSells.size() && mySells.size() > rightSells.size();
	}
	

}
