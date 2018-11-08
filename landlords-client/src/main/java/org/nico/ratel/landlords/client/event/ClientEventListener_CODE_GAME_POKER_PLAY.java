package org.nico.ratel.landlords.client.event;

import java.util.ArrayList;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.println(data);
		SimplePrinter.println("Please enter the number you want：");
		String line = SimpleWriter.write();

		if(line == null){
			SimplePrinter.println("Invalid input");
			call(channel, data);
		}else{
			if(line.equalsIgnoreCase("PASS")) {
				pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY, Noson.reversal(new Character[] {'p'}));
			}else if(line.equalsIgnoreCase("EXIT")){
				pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT, null);
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
					SimplePrinter.println("Invalid input");
					call(channel, data);
				}
			}
		}
		
	}

}
