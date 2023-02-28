package org.nico.ratel.landlords.robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;

/**
 * How does the machine decide on a better strategy to win the game
 * 
 * @author nico
 */
public class RobotDecisionMakers {
	
	private static final Map<Integer, AbstractRobotDecisionMakers> decisionMakersMap = new HashMap<Integer, AbstractRobotDecisionMakers>();
	
	public static void init() {
		decisionMakersMap.put(1, new EasyRobotDecisionMakers());
		decisionMakersMap.put(2, new MediumRobotDecisionMakers());
	}
	
	public static boolean contains(int difficultyCoefficient) {
		return decisionMakersMap.containsKey(difficultyCoefficient);
	}
	
	public static PokerSell howToPlayPokers(int difficultyCoefficient, PokerSell lastPokerSell, ClientSide robot){
		return decisionMakersMap.get(difficultyCoefficient).howToPlayPokers(lastPokerSell, robot);
	}

	public static int getLandlordScore(int difficultyCoefficient, List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		return decisionMakersMap.get(difficultyCoefficient).getLandlordScore(leftPokers, rightPokers, myPokers);
	}

}
