package org.nico.ratel.landlords.robot;

import java.util.List;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月15日 上午12:12:15
 */

public abstract class AbstractRobotDecisionMakers {

	public abstract PokerSell howToPlayPokers(PokerSell lastPokerSell, ClientSide robot);
	
	public abstract int getLandlordScore(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers);
}
