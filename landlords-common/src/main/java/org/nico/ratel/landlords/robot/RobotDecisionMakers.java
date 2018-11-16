package org.nico.ratel.landlords.robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;

/**
 * How does the machine decide on a better strategy to win the game
 * 
 * @author nico
 */
public class RobotDecisionMakers {
	
	private static Map<Integer, AbstractRobotDecisionMakers> decisionMakersMap = new HashMap<Integer, AbstractRobotDecisionMakers>() {
		private static final long serialVersionUID = 8541568961784067309L;
		{
			put(1, new SimpleRobotDecisionMakers());
		}
	};
	
	public static boolean contains(int difficultyCoefficient) {
		return decisionMakersMap.containsKey(difficultyCoefficient);
	}
	
	public static PokerSell howToPlayPokers(int difficultyCoefficient, PokerSell lastPokerSell, List<Poker> myPokers){
		return decisionMakersMap.get(difficultyCoefficient).howToPlayPokers(lastPokerSell, myPokers);
	}
	
	public static boolean howToChooseLandlord(int difficultyCoefficient, List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		return decisionMakersMap.get(difficultyCoefficient).howToChooseLandlord(leftPokers, rightPokers, myPokers);
	}
	
}
