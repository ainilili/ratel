package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.enums.RoomType;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_ROOM_CREATE_PVE implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		
		int difficultyCoefficient = Integer.valueOf(data);
		if(RobotDecisionMakers.contains(difficultyCoefficient)) {

			Room room = new Room(ServerContains.getServerId());
			room.setType(RoomType.PVE);
			room.setStatus(RoomStatus.BLANK);
			room.setRoomOwner(clientSide.getNickname());
			room.getClientSideMap().put(clientSide.getId(), clientSide);
			room.getClientSideList().add(clientSide);
			room.setCurrentSellClient(clientSide.getId());
			room.setCreateTime(System.currentTimeMillis());
			room.setDifficultyCoefficient(difficultyCoefficient);
			
			clientSide.setRoomId(room.getId());
			ServerContains.addRoom(room);
			
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
				ServerContains.CLIENT_SIDE_MAP.put(robot.getId(), robot);
			}
			preClient.setNext(clientSide);
			clientSide.setPre(preClient);
			
			ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, String.valueOf(room.getId()));
		}else {
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_PVE_DIFFICULTY_NOT_SUPPORT, null);
		}
		
	}

	



}
