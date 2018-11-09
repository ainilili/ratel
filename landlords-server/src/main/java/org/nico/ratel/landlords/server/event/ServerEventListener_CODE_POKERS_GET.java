package org.nico.ratel.landlords.server.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_POKERS_GET implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		List<Map<String, Object>> roomList = new ArrayList<>(ServerContains.ROOM_MAP.size());
		for(Entry<Integer, Room> entry: ServerContains.ROOM_MAP.entrySet()) {
			Room room = entry.getValue();
			roomList.add(MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("roomOwner", room.getRoomOwner())
					.put("roomClientCount", room.getClientSideList().size())
					.map());
		}
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_SHOW_ROOMS, Noson.reversal(roomList));
	}

}
