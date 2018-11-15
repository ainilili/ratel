package org.nico.ratel.landlords.server.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_GAME_POKER_PLAY_REDIRECT implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		
		List<Map<String, Object>> clientInfos = new ArrayList<Map<String,Object>>(3);
		for(ClientSide client: room.getClientSideList()){
			if(clientSide.getId() != client.getId()){
				clientInfos.add(MapHelper.newInstance()
						.put("clientId", client.getId())
						.put("clientNickname", client.getNickname())
						.put("type", client.getType())
						.put("surplus", client.getPokers().size())
						.put("position", clientSide.getPre().getId() == client.getId() ? "UP" : "DOWN")
						.map());
			}
		}
		
		String result = MapHelper.newInstance()
				.put("pokers", clientSide.getPokers())
				.put("clientInfos", clientInfos)
				.put("sellClientId", room.getCurrentSellClient())
				.put("sellClinetNickname", ServerContains.CLIENT_SIDE_MAP.get(room.getCurrentSellClient()).getNickname())
				.json();
		
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_REDIRECT, result);
	}

}
