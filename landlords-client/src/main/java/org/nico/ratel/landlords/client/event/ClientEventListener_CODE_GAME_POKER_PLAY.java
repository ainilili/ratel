package org.nico.ratel.landlords.client.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		
		SimplePrinter.printNotice("It's your turn to play, your pokers are as follows: ");
		List<Poker> pokers = Noson.convert(map.get("pokers"), new NoType<List<Poker>>() {});
		SimplePrinter.printPokers(pokers);
		
		
		SimplePrinter.printNotice("Please enter the card you came up with (enter [EXIT] to exit current room, enter [PASS] to jump current round)");
		String line = SimpleWriter.write("card");

		if(line == null){
			SimplePrinter.printNotice("Invalid enter");
			call(channel, data);
		}else{
			if(line.equalsIgnoreCase("PASS")) {
				pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_PASS);
			}else if(line.equalsIgnoreCase("EXIT")){
				pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT);
			}else {
				String[] strs = line.split(" ");
				List<Character> options = new ArrayList<>();
				boolean access = true;
				for(int index = 0; index < strs.length; index ++){
					String str = strs[index];
					for(char c: str.toCharArray()) {
						if(c == ' ' || c == '\t') {
						}else {
							if(! PokerLevel.aliasContains(c)) {
								access = false;
								break;
							}else {
								options.add(c);
							}
						}
					}
				}
				if(access){
					pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY, Noson.reversal(options.toArray(new Character[] {})));
				}else{
					SimplePrinter.printNotice("Invalid enter");
					
					if(lastPokers != null) {
						SimplePrinter.printNotice(lastSellClientNickname + "[" + lastSellClientType + "] played:");
						SimplePrinter.printPokers(lastPokers);
					}
					
					call(channel, data);
				}
			}
		}
		
	}

}
