package org.nico.ratel.landlords.robot;

import java.util.List;
import java.util.Random;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;

/**
 * @author nico
 * @date 2018-11-15 12:13:49
 */
public class EasyRobotDecisionMakers extends AbstractRobotDecisionMakers {

	private static Random random = new Random();

	@Override
	public PokerSell howToPlayPokers(PokerSell lastPokerSell, ClientSide robot) {
		if (lastPokerSell != null && lastPokerSell.getSellType() == SellType.KING_BOMB) {
			return null;
		}

		List<PokerSell> sells = PokerHelper.parsePokerSells(robot.getPokers());
		if (lastPokerSell == null) {
			return sells.get(random.nextInt(sells.size()));
		}

		for (PokerSell sell : sells) {
			if (sell.getSellType() == lastPokerSell.getSellType()) {
				if (sell.getScore() > lastPokerSell.getScore() && sell.getSellPokers().size() == lastPokerSell.getSellPokers().size()) {
					return sell;
				}
			}
		}
		if (lastPokerSell.getSellType() != SellType.BOMB) {
			for (PokerSell sell : sells) {
				if (sell.getSellType() == SellType.BOMB) {
					return sell;
				}
			}
		}
		for (PokerSell sell : sells) {
			if (sell.getSellType() == SellType.KING_BOMB) {
				return sell;
			}
		}
		return null;
	}

	@Override
	public int getLandlordScore(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		List<PokerSell> leftSells = PokerHelper.parsePokerSells(leftPokers);
		List<PokerSell> mySells = PokerHelper.parsePokerSells(myPokers);
		List<PokerSell> rightSells = PokerHelper.parsePokerSells(rightPokers);
		int expectedScore = 0;
		if (mySells.size() > leftSells.size()) {
			++expectedScore;
		}

		if (mySells.size() > rightSells.size()) {
			++expectedScore;
		}

		if (expectedScore != 0) {
			++expectedScore;
		}

		return expectedScore;
	 }
}
