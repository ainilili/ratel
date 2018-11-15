package org.nico.ratel.landlords.robot;

import java.util.ArrayList;
import java.util.List;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.SellType;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月15日 上午12:13:49
 */

public class SimpleRobotDecisionMakers extends AbstractRobotDecisionMakers{

	@Override
	public PokerSell howToPlayPokers(PokerSell lastPokerSell, List<Poker> myPokers) {

		return null;
	}

	@Override
	public boolean howToChooseLandlord(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		return true;
	}

}
