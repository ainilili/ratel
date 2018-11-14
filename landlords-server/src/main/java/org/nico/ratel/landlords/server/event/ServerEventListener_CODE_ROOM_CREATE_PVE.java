package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.enums.RoomType;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_ROOM_CREATE_PVE implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		
		Room room = new Room(ServerContains.getServerId());
		room.setType(RoomType.PVE);
		room.setStatus(RoomStatus.BLANK);
		room.setRoomOwner(clientSide.getNickname());
		room.getClientSideMap().put(clientSide.getId(), clientSide);
		room.getClientSideList().add(clientSide);
		
		clientSide.setRoomId(room.getId());
		ServerContains.ROOM_MAP.put(room.getId(), room);
		
		ClientSide preClient = clientSide;
		//Add robots
		for(int index = 1; index < 3; index ++) {
			ClientSide robot = new ClientSide(- ServerContains.getClientId(), ClientStatus.PLAYING, null);
			robot.setNickname("robot_" + index);
			robot.setRole(ClientRole.ROBOT);
			preClient.setNext(robot);
			robot.setPre(preClient);
			robot.setRoomId(room.getId());
			room.getClientSideMap().put(robot.getId(), robot);
			room.getClientSideList().add(robot);
			
			preClient = robot;
		}
		preClient.setNext(clientSide);
		clientSide.setPre(preClient);
		
		ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, String.valueOf(room.getId()));
	}

	



}
