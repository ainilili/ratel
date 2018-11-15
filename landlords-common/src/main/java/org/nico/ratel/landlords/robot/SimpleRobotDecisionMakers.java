package org.nico.ratel.landlords.robot;

import java.util.ArrayList;
import java.util.List;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月15日 上午12:13:49
 */

public class SimpleRobotDecisionMakers extends AbstractRobotDecisionMakers{

	@Override
	public PokerSell howToPlayPokers(PokerSell lastPokerSell, List<Poker> myPokers) {
		if(lastPokerSell.getSellType() == SellType.KING_BOMB) {
			return null;
		}
		List<PokerSell> sells = PokerHelper.parsePokerSells(myPokers);
		for(PokerSell sell: sells) {
			if(sell.getSellType() == lastPokerSell.getSellType()) {
				if(sell.getScore() > lastPokerSell.getScore()) {
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
		
		return null;
	}

	@Override
	public boolean howToChooseLandlord(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		return true;
	}

}
