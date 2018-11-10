package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_GAME_LANDLORD_ELECT implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());

		boolean isY = Boolean.valueOf(data);
		if(isY){
			clientSide.getPokers().addAll(room.getLandlordPokers());
			PokerHelper.sortPoker(clientSide.getPokers());
			
			int currentClientId = clientSide.getId();
			room.setLandlordId(currentClientId);
			room.setLastSellClient(currentClientId);
			room.setCurrentSellClient(currentClientId);
			
			for(ClientSide client: room.getClientSideList()){
				String result = MapHelper.newInstance()
						.put("roomId", room.getId())
						.put("roomOwner", room.getRoomOwner())
						.put("roomClientCount", room.getClientSideList().size())
						.put("landlordNickname", clientSide.getNickname())
						.put("landlordId", clientSide.getId())
						.put("pokers", client.getPokers())
						.put("additionalPokers", room.getLandlordPokers())
						.json();
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_CONFIRM, result);
			}
		}else{
			if(clientSide.getNext().getId() == room.getLandlordId()){
				for(ClientSide client: room.getClientSideList()){
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_CYCLE, null);
				}
				ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, null);
			}else{
				ClientSide turnClientSide = clientSide.getNext();
				String result = MapHelper.newInstance()
						.put("roomId", room.getId())
						.put("roomOwner", room.getRoomOwner())
						.put("roomClientCount", room.getClientSideList().size())
						.put("nextClientNickname", turnClientSide.getNickname())
						.put("nextClientId", turnClientSide.getId())
						.json();
				ChannelUtils.pushToClient(turnClientSide.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_ELECT, result);
			}
		}
	}
}
