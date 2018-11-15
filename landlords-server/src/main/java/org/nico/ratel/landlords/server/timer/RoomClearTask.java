package org.nico.ratel.landlords.server.timer;

import java.util.Map;
import java.util.TimerTask;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
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
	private static long strtingStatusInterval = 1000 * 15;
	
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
				System.out.println(room.getId() + "->" + diff);
				if(diff > interval){
						ClientSide currentPlayer = room.getClientSideMap().get(room.getCurrentSellClient());
						//kick this client
						ChannelUtils.pushToClient(currentPlayer.getChannel(), ClientEventCode.CODE_CLIENT_KICK, null);
						
						room.getClientSideMap().remove(currentPlayer.getId());
						//add ai
						currentPlayer.setId(- ServerContains.getClientId());
						currentPlayer.setRole(ClientRole.ROBOT);
						
						//set robot difficulty -> simple
						room.setDifficultyCoefficient(1);
						room.getClientSideMap().put(currentPlayer.getId(), currentPlayer);
						room.setCurrentSellClient(currentPlayer.getId());
						
						ServerContains.CLIENT_SIDE_MAP.put(currentPlayer.getId(), currentPlayer);
						if(room.getLandlordId() == -1){
							RobotEventListener.get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(currentPlayer, null);
						}else{
							RobotEventListener.get(ClientEventCode.CODE_GAME_POKER_PLAY).call(currentPlayer, null);
						}
				}
			}
		}
	}

}
