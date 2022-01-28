package org.nico.ratel.landlords.client.event;

import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.client.entity.User;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientEventListener_CODE_GAME_POKER_PLAY extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);

		SimplePrinter.printNotice("It's your turn to play, your cards are as follows: ");
		List<Poker> pokers = Noson.convert(map.get("pokers"), new NoType<List<Poker>>() {
		});
		SimplePrinter.printPokers(pokers);
		SimplePrinter.printNotice("Last cards are");
		SimplePrinter.printNotice(map.containsKey("lastPokers")?map.get("lastPokers").toString():"");

		SimplePrinter.printNotice("Please enter the combination you came up with (enter [exit|e] to exit current room, enter [pass|p] to jump current round, enter [view|v] to show all valid combinations.)");
		String line = SimpleWriter.write(User.INSTANCE.getNickname(), "combination");

		if (line == null) {
			SimplePrinter.printNotice("Invalid enter");
			call(channel, data);
		} else {
			if (line.equalsIgnoreCase("pass") || line.equalsIgnoreCase("p")) {
				pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_PASS);
			} else if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("e")) {
				pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT);
			} else if (line.equalsIgnoreCase("view") || line.equalsIgnoreCase("v")) {
				if (!map.containsKey("lastSellPokers") || !map.containsKey("lastSellClientId")) {
					SimplePrinter.printNotice("Current server version unsupport this feature, need more than v1.2.4.");
					call(channel, data);
					return;
				}
				Object lastSellPokersObj = map.get("lastSellPokers");
				if (lastSellPokersObj == null || Integer.valueOf(SimpleClient.id).equals(map.get("lastSellClientId"))) {
					SimplePrinter.printNotice("Up to you !");
					call(channel, data);
					return;
				} else {
					List<Poker> lastSellPokers = Noson.convert(lastSellPokersObj, new NoType<List<Poker>>() {});
					List<PokerSell> sells = PokerHelper.validSells(PokerHelper.checkPokerType(lastSellPokers), pokers);
					if (sells.size() == 0) {
						SimplePrinter.printNotice("It is a pity that, there is no winning combination...");
						call(channel, data);
						return;
					}
					for (int i = 0; i < sells.size(); i++) {
						SimplePrinter.printNotice(i + 1 + ". " + PokerHelper.textOnlyNoType(sells.get(i).getSellPokers()));
					}
					while (true) {
						SimplePrinter.printNotice("You can enter index to choose anyone.(enter [back|b] to go back.)");
						line = SimpleWriter.write(User.INSTANCE.getNickname(), "choose");
						if (line.equalsIgnoreCase("back") || line.equalsIgnoreCase("b")) {
							call(channel, data);
							return;
						} else {
							try {
								int choose = Integer.valueOf(line);
								if (choose < 1 || choose > sells.size()) {
									SimplePrinter.printNotice("The input number must be in the range of 1 to " + sells.size() + ".");
								} else {
									List<Poker> choosePokers = sells.get(choose - 1).getSellPokers();
									List<Character> options = new ArrayList<>();
									for (Poker poker : choosePokers) {
										options.add(poker.getLevel().getAlias()[0]);
									}
									pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY, Noson.reversal(options.toArray(new Character[]{})));
									break;
								}
							} catch (NumberFormatException e) {
								SimplePrinter.printNotice("Please input a number.");
							}
						}
					}
				}

//				PokerHelper.validSells(lastPokerSell, pokers);
			} else {
				String[] strs = line.split(" ");
				List<Character> options = new ArrayList<>();
				boolean access = true;
				for (int index = 0; index < strs.length; index++) {
					String str = strs[index];
					for (char c : str.toCharArray()) {
						if (c == ' ' || c == '\t') {
						} else {
							if (!PokerLevel.aliasContains(c)) {
								access = false;
								break;
							} else {
								options.add(c);
							}
						}
					}
				}
				if (access) {
					pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY, Noson.reversal(options.toArray(new Character[]{})));
				} else {
					SimplePrinter.printNotice("Invalid enter");

					if (lastPokers != null) {
						SimplePrinter.printNotice(lastSellClientNickname + "[" + lastSellClientType + "] played:");
						SimplePrinter.printPokers(lastPokers);
					}

					call(channel, data);
				}
			}
		}

	}

}
