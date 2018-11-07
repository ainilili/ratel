package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.entity.ClientTransferData;
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
				pushToServer(channel, ServerEventCode.CODE_PLAY_ROUND, new int[] {0});
			}else if(line.equalsIgnoreCase("EXIT")){
				pushToServer(channel, ServerEventCode.CODE_PLAYER_EXIT, null);
			}else {
				String[] options = line.split(" ");
				int[] indexes = new int[options.length];
				boolean access = true;
				for(int index = 0; index < options.length; index ++){
					String option = options[index];
					int result = OptionsUtils.getOptions(option);
					if(result < 1){
						access = false;
						break;
					}
					indexes[index] = result;
				}
				if(access){
					pushToServer(channel, ServerEventCode.CODE_PLAY_ROUND, indexes);
				}else{
					SimplePrinter.println("Invalid input");
					call(channel, clientTransferData);
				}
			}
		}

	}

}
