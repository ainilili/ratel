package org.nico.ratel.landlords.server.timer;

import java.util.Map;
import java.util.TimerTask;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.ServerEventListener;
import org.nico.ratel.landlords.server.robot.RobotEventListener;

/** 
 * 
 * @author nico
 */

public class RoomClearTask extends TimerTask{

	//The room wait time of after create is 60s
	private static long waitingStatusInterval = 1000 * 60;

	//The room starting destroy time is 300s
	private static long strtingStatusInterval = 1000 * 60;

	@Override
	public void run() {

		Map<Integer, Room> rooms = ServerContains.getRoomMap();
		if(rooms != null && ! rooms.isEmpty()){
			long now = System.currentTimeMillis();
			for(Room room: rooms.values()){
				long interval = 0;
				if(room.getStatus() != RoomStatus.STARTING){
					interval = waitingStatusInterval;
				}else{
					interval = strtingStatusInterval;
				}
				long diff = now - room.getLastFlushTime();
				
				if(diff > interval){
					boolean allRobots = true;
					for(ClientSide client: room.getClientSideList()) {
						if(client.getId() != room.getCurrentSellClient() && client.getRole() == ClientRole.PLAYER) {
							allRobots = false;
							break;
						}
					}
					
					ClientSide currentPlayer = room.getClientSideMap().get(room.getCurrentSellClient());
					
					if(allRobots) {
						ServerEventListener.get(ServerEventCode.CODE_CLIENT_EXIT).call(currentPlayer, null);
					}else {
						//kick this client
						ChannelUtils.pushToClient(currentPlayer.getChannel(), ClientEventCode.CODE_CLIENT_KICK, null);

						//client current player
						room.getClientSideMap().remove(currentPlayer.getId());
						room.getClientSideList().remove(currentPlayer);

						ClientSide robot = new ClientSide(- ServerContains.getClientId(), ClientStatus.PLAYING, null);
						robot.setNickname(currentPlayer.getNickname());
						robot.setRole(ClientRole.ROBOT);
						robot.setRoomId(room.getId());
						robot.setNext(currentPlayer.getNext());
						robot.setPre(currentPlayer.getPre());
						robot.getNext().setPre(robot);
						robot.getPre().setNext(robot);
						robot.setPokers(currentPlayer.getPokers());
						robot.setType(currentPlayer.getType());

						room.getClientSideMap().put(robot.getId(), robot);
						room.getClientSideList().add(robot);
						room.setCurrentSellClient(currentPlayer.getId());

						//set robot difficulty -> simple
						room.setDifficultyCoefficient(1);

						ServerContains.CLIENT_SIDE_MAP.put(robot.getId(), robot);
						
						//init client
						currentPlayer.init();
						if(room.getLandlordId() == -1){
							RobotEventListener.get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(robot, null);
						}else{
							RobotEventListener.get(ClientEventCode.CODE_GAME_POKER_PLAY).call(robot, null);
						}
					}
				}
			}
		}
	}

}
