package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.helper.PokerHelper;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_PLAY_ROUND implements ServerEventListener<int[]>{

	@Override
	public void call(Channel channel, ServerTransferData<int[]> serverTransferData) {
		ClientSide clientSide = ServerContains.CLIENT_SIDE_MAP.get(serverTransferData.getClientId());
		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());
		int[] indexes = serverTransferData.getData();
		
		if(PokerHelper.checkPoker(indexes, clientSide.getPokers())){
			if(room.getLastSellClient() != clientSide.getId() && room.getLastSellPokers() != null){
				// Compare the brand
			}else{
				// Casual play
			}
		}else{
			ChannelUtils.pushToClient(channel, ClientEventCode.CODE_PLAY_ROUND, PokerHelper.unfoldPoker(clientSide.getPokers(), true), "The draw number is invalid");
		}
		
	}

}
