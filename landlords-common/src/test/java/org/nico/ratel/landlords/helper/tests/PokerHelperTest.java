package org.nico.ratel.landlords.helper.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;

import java.util.ArrayList;

public class PokerHelperTest {

	private ArrayList<Poker> pokers = new ArrayList<>();

	@Before
	public void setUp() {
		pokers.add(new Poker(PokerLevel.LEVEL_3, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_4, PokerType.DIAMOND));
		pokers.add(new Poker(PokerLevel.LEVEL_4, PokerType.DIAMOND));
		pokers.add(new Poker(PokerLevel.LEVEL_5, PokerType.CLUB));
		pokers.add(new Poker(PokerLevel.LEVEL_5, PokerType.CLUB));
		pokers.add(new Poker(PokerLevel.LEVEL_5, PokerType.CLUB));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.SPADE));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
	}

	@Test
	public void testComparePoker() {
		Assert.assertTrue(PokerHelper.comparePoker(new ArrayList<>(), new ArrayList<>()));
	}

	@Test
	public void testCheckPokerIndex() {
		Assert.assertFalse(PokerHelper.checkPokerIndex(new int[]{}, new ArrayList<>()));
		Assert.assertFalse(PokerHelper.checkPokerIndex(new int[]{2, -4_194_302}, new ArrayList<>()));
	}

	@Test
	public void testGetIndexes() {
		Assert.assertNull(PokerHelper.getIndexes(new Character[]{'3', '4', '5', '6', '7', '8'}, pokers));
		Assert.assertNotNull(PokerHelper.getIndexes(new Character[]{}, new ArrayList<>()));
		Assert.assertEquals(0, PokerHelper.getIndexes(new Character[]{}, new ArrayList<>()).length);
	}

	@Test
	public void testGetPoker() {
		Assert.assertEquals(PokerLevel.LEVEL_3, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(0).getLevel());
		Assert.assertEquals(PokerType.BLANK, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(0).getType());
		Assert.assertEquals(PokerLevel.LEVEL_4, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(1).getLevel());
		Assert.assertEquals(PokerType.DIAMOND, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(1).getType());
	}

	@Test
	public void testPrintPoker() {
		Assert.assertNotNull(PokerHelper.printPoker(pokers));
	}

	@Test
	public void testDistributePoker() {
		Assert.assertNotNull(PokerHelper.distributePoker());
	}

	@Test
	public void testCheckPokerType1() {
		pokers.clear();
		Assert.assertNull(PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(-1, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.ILLEGAL, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_SMALL_KING, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_BIG_KING, PokerType.DIAMOND));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(2147483647, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.KING_BOMB, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType2() {
		pokers.clear();
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.THREE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(1027, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.BOMB, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.remove(pokers.size() - 1);
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.THREE_ZONES_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.THREE_ZONES_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.THREE_STRAIGHT_WITH_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.THREE_STRAIGHT_WITH_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType3() {
		pokers.clear();
		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.SINGLE_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.DOUBLE_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.THREE_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.FOUR_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType4() {
		pokers.clear();
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.FOUR_ZONES_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_5, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.FOUR_STRAIGHT_WITH_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType5() {
		pokers.clear();
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_9, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_10, PokerType.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.FOUR_ZONES_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerLevel.LEVEL_5, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_5, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.FOUR_STRAIGHT_WITH_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType6() {
		pokers.clear();
		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_6, PokerType.HEART));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_7, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_8, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_4, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_4, PokerType.BLANK));
		pokers.add(new Poker(PokerLevel.LEVEL_5, PokerType.BLANK));
		PokerSell pokerSell = PokerHelper.checkPokerType(pokers);
		Assert.assertEquals(pokers, pokerSell.getSellPokers());
        Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(SellType.THREE_STRAIGHT_WITH_SINGLE, pokerSell.getSellType());
	}
}
