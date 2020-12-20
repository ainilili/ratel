package org.nico.ratel.landlords.robot.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.robot.MediumRobotDecisionMakers;

public class MediumRobotDecisionMakersTests {

	private List<Poker> buildPokers(Object[] values){
		List<Poker> pokers = new ArrayList<Poker>();
		for(Object v: values) {
			if(v == null){
				continue;
			}
			if(v instanceof String) {
				pokers.add(new Poker(PokerLevel.parseByName((String)v), PokerType.CLUB));
			}else if(v instanceof Integer || v.getClass() == int.class) {
				pokers.add(new Poker(PokerLevel.parseByLevel((int)v), PokerType.CLUB));
			}
		}
		return pokers;
	}
	
	@Test
	public void testMedium_1() {
		ClientSide self = new ClientSide();
		ClientSide right = new ClientSide();
		ClientSide left = new ClientSide();
		self.setPre(left);
		self.setNext(right);
		left.setPre(right);
		left.setNext(self);
		right.setPre(self);
		right.setNext(left);
		self.setPokers(buildPokers(new Object[] {3,3,3,4,4,4,5,6,8,8,11,12,13,13,14,15,15}));
		right.setPokers(buildPokers(new Object[] {3,4,5,6,7,9,9,11,11,12,12,12,14,16}));
		left.setPokers(buildPokers(new Object[] {6,6,7,7,7,8,8,9,9,10,10,10,10,11,13,14,14,17}));
		
//		self.setPokers(buildPokers(new Object[] {9,9,3,4}));
//		right.setPokers(buildPokers(new Object[] {3,3,9,9,10}));
//		left.setPokers(buildPokers(new Object[] {6,6,8,8}));
		
		MediumRobotDecisionMakers mediumRobot = new MediumRobotDecisionMakers();
		
		PokerSell lastSell = PokerHelper.checkPokerType(buildPokers(new Object[] {5,5}));
		PokerSell sell = mediumRobot.howToPlayPokers(lastSell, self);
		if(sell == null) {
			System.out.println("打不起");
		}else {
			System.out.println(PokerHelper.printPoker(sell.getSellPokers()));
		}
		
	}
}
