package org.nico.ratel.landlords.client.event;

import java.util.ArrayList;
import java.util.List;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_PLAY_ROUND extends ClientEventListener<String>{

	@Override
	public void call(Channel channel, ClientTransferData<String> clientTransferData) {
		SimplePrinter.println(clientTransferData.getData());
		SimplePrinter.println("Please enter the number you want：");
		String line = SimpleWriter.write();

		if(line == null){
			SimplePrinter.println("Invalid input");
			call(channel, clientTransferData);
		}else{
			if(line.equalsIgnoreCase("PASS")) {
				pushToServer(channel, ServerEventCode.CODE_PLAY_ROUND, new Character[] {'p'});
			}else if(line.equalsIgnoreCase("EXIT")){
				pushToServer(channel, ServerEventCode.CODE_PLAYER_EXIT, null);
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
					pushToServer(channel, ServerEventCode.CODE_PLAY_ROUND, options.toArray(new Character[] {}));
				}else{
					SimplePrinter.println("Invalid input");
					call(channel, clientTransferData);
				}
			}
		}

	}

}
