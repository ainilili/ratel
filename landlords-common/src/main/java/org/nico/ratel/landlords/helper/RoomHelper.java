package org.nico.ratel.landlords.server.event.helper;

import java.util.LinkedList;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientType;
import org.nico.ratel.landlords.enums.RoomStatus;

/** 
 * 
 * @author nico
 */

public class RoomHelper {

	public static void restartingGame(Room room){
		LinkedList<ClientSide> roomClientList = room.getClientSideList();
		
		// Push start game messages
		room.setStatus(RoomStatus.STARTING);
		for(ClientSide client: roomClientList) {
			client.setType(ClientType.PEASANT);
			ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_ROOM_STARTING, Noson.reversal(room));
		}
		
		TimeHelper.sleep(500);
		
		// Send the points of poker
		List<List<Poker>> pokersList = PokerHelper.distributePoker();
		int cursor = 0;
		for(ClientSide client: roomClientList){
			client.setPokers(pokersList.get(cursor ++));
			ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_POKERS, PokerHelper.unfoldPoker(client.getPokers(), false));
		}
		room.setLandlordPokers(pokersList.get(3));
		
		TimeHelper.sleep(500);
		
		// Push information about the robber
		int startGrabIndex = (int)(Math.random() * 3);
		ClientSide startGrabClient = roomClientList.get(startGrabIndex);
		room.setLandlordId(startGrabClient.getId());
		
		for(ClientSide client: roomClientList){
			if(client.getId() == startGrabClient.getId()){
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GRAB_LANDLORD, null);
			}else{
				ChannelUtils.pushToClient(client.getChannel(), null, "Wait for " + startGrabClient.getNickname() + " verify local owner");
			}
		}
		
	}
	
}
