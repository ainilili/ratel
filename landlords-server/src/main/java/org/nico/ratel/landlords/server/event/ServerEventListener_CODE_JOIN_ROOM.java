package org.nico.ratel.landlords.server.event;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.helper.RoomHelper;

public class ServerEventListener_CODE_JOIN_ROOM implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.ROOM_MAP.get(Integer.valueOf(data));

		if(room == null) {
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_SHOW_OPTIONS, null, "Room inexistence !");
		}else if(room.getClientSideList().size() == 3) {
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_SHOW_OPTIONS, null, "The room is full !");
		}else {
			clientSide.setRoomId(room.getId());
			
			ConcurrentSkipListMap<Integer, ClientSide> roomClientMap = (ConcurrentSkipListMap<Integer, ClientSide>) room.getClientSideMap();
			LinkedList<ClientSide> roomClientList = room.getClientSideList();

			if(roomClientList.size() > 0){
				ClientSide pre = roomClientList.getLast();
				pre.setNext(clientSide);
				clientSide.setPre(pre);
			}
			
			roomClientList.add(clientSide);
			roomClientMap.put(clientSide.getId(), clientSide);

			if(roomClientMap.size() == 3) {
				clientSide.setNext(roomClientList.getFirst());
				roomClientList.getFirst().setPre(clientSide);
				
				RoomHelper.restartingGame(room);
			}else {
				room.setStatus(RoomStatus.WAIT);
				ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_JOIN_ROOM_SUCCESS, Noson.reversal(room));
				for(ClientSide client: roomClientMap.values()) {
					if(client.getId() != clientSide.getId()) {
						ChannelUtils.pushToClient(client.getChannel(), null, null, clientSide.getNickname() + " join room !");
					}
				}
			}
		}
	}





}
