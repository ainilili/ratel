package org.nico.ratel.landlords.robot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.utils.StreamUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Trial game algorithm
 *
 * @author nico
 * @date 2020-12-19 16:36
 */
public class MediumRobotDecisionMakers extends AbstractRobotDecisionMakers{

	private static Gson gson = new Gson();
	
	private static Map<String, Long> DP = new ConcurrentHashMap<String, Long>();
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final Long DEDUCE_LIMIT = 100 * 3L;

	public MediumRobotDecisionMakers() {
    	try {
    		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("dp.json");
        	String dpJson = StreamUtils.convertToString(stream);
        	DP = gson.fromJson(dpJson, new TypeToken<ConcurrentHashMap<String, Long>>() {}.getType());
        	SimplePrinter.serverLog("Medium Robot init with dp.json.");
    	}catch(Exception e) {
    		SimplePrinter.serverLog("Medium Robot init without dp.json.");
    	}
	}

	@Override
	public PokerSell howToPlayPokers(PokerSell lastPokerSell, ClientSide robot) {
		if(lastPokerSell != null && lastPokerSell.getSellType() == SellType.KING_BOMB) {
			return null;
		}
		List<Poker> selfPoker = PokerHelper.clonePokers(robot.getPokers());
		List<Poker> leftPoker = PokerHelper.clonePokers(robot.getPre().getPokers());
		List<Poker> rightPoker = PokerHelper.clonePokers(robot.getNext().getPokers());
		PokerHelper.sortPoker(selfPoker);
		PokerHelper.sortPoker(leftPoker);
		PokerHelper.sortPoker(rightPoker);

		List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
		pokersList.add(selfPoker);
		pokersList.add(rightPoker);
		pokersList.add(leftPoker);

		List<PokerSell> sells = PokerHelper.validSells(lastPokerSell, selfPoker);
		if(sells == null || sells.size() == 0) {
			return null;
		}
		PokerSell bestSell = null;
		Long weight = null;
		for(PokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(selfPoker);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				return sell;
			}
			pokersList.set(0, pokers);
			AtomicLong counter = new AtomicLong();
			deduce(0, sell, 1, pokersList, counter, DP);
			if(weight == null) {
				bestSell = sell;
				weight = counter.get();
			}else if (counter.get() > weight){
				bestSell = sell;
				weight = counter.get();
			}
			pokersList.set(0, selfPoker);
		}
		return bestSell;
	}

	private Boolean deduce(int sellCursor, PokerSell lastPokerSell, int cursor, List<List<Poker>> pokersList, AtomicLong counter, Map<String, Long> dp) {
		if(cursor > 2) {
			cursor = 0;
		}
		if(sellCursor == cursor) {
			lastPokerSell = null;
		}

		List<Poker> original = pokersList.get(cursor);
		List<PokerSell> sells = PokerHelper.validSells(lastPokerSell, original);
		if(sells == null || sells.size() == 0) {
			if(sellCursor != cursor) {
				return deduce(sellCursor, lastPokerSell, cursor + 1, pokersList, counter, dp);
			}
		}
		for(PokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(original);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				return cursor == 0;
			}else {
				pokersList.set(cursor, pokers);

				String key = serialKey(cursor, sell, cursor + 1, pokersList);
				Long score = dp.get(key);
				if(score != null) {
					counter.addAndGet(score);
				}else {
					Boolean suc = deduce(cursor, sell, cursor + 1, pokersList, counter, dp);
					if(cursor != 0) {
						pokersList.set(cursor, original);
						return suc;
					}
					if(Math.abs(counter.get()) > DEDUCE_LIMIT) {
						pokersList.set(cursor, original);
						return counter.get() > DEDUCE_LIMIT;
					}
					if(suc != null) {
						score = (long)(suc ? 1 : -1);
						counter.addAndGet(score);
					}
				}
				pokersList.set(cursor, original);
			}
		}
		return null;
	}

	private String serialKey(int sellCursor, PokerSell lastPokerSell, int cursor, List<List<Poker>> pokersList) {
		return sellCursor + "v" + (lastPokerSell == null ? "n" : serialPokers(lastPokerSell.getSellPokers())) + "v" + cursor + "v" + serialPokersList(pokersList);
	}

	private static String serialPokers(List<Poker> pokers){
		if(pokers == null || pokers.size() == 0) {
			return "n";
		}
		StringBuilder builder = new StringBuilder();
		if(pokers != null && pokers.size() > 0) {
			for(int index = 0; index < pokers.size(); index ++) {
				builder.append(pokers.get(index).getLevel().getLevel() + (index == pokers.size() - 1 ? "" : "_"));
			}
		}
		return builder.toString();
	}

	private static String serialPokersList(List<List<Poker>> pokersList){
		StringBuilder builder = new StringBuilder();
		for(int index = 0; index < pokersList.size(); index ++) {
			List<Poker> pokers = pokersList.get(index);
			builder.append(serialPokers(pokers) + (index == pokersList.size() - 1 ? "" : "m"));
		}
		return builder.toString();
	}

	@Override
	public boolean howToChooseLandlord(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		int leftScore = PokerHelper.parsePokerColligationScore(leftPokers);
		int rightScore = PokerHelper.parsePokerColligationScore(rightPokers);
		int myScore = PokerHelper.parsePokerColligationScore(myPokers);
		return myScore >= (leftScore + rightScore)/2;
	}

}
