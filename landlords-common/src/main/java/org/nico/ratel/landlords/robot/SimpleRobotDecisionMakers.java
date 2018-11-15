package org.nico.ratel.landlords.robot;

import java.util.List;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.helper.PokerHelper;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月15日 上午12:13:49
 */

public class SimpleRobotDecisionMakers extends AbstractRobotDecisionMakers{

	@Override
	public List<Poker> howToPlayPokers(PokerSell lastPokerSell, List<Poker> myPokers) {
		return null;
	}

	@Override
	public boolean howToChooseLandlord(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		int leftScore = PokerHelper.parsePokerColligationScore(leftPokers);
		int rightScore = PokerHelper.parsePokerColligationScore(rightPokers);
		int myScore = PokerHelper.parsePokerColligationScore(myPokers);
		return myScore >= (leftScore + rightScore)/2;
	}

}
