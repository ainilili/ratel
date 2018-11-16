package org.nico.ratel.landlords.server.event;

import java.util.LinkedList;
import java.util.List;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.ClientType;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.robot.RobotEventListener;

public class ServerEventListener_CODE_GAME_STARTING implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.getRoom(clientSide.getRoomId());

		LinkedList<ClientSide> roomClientList = room.getClientSideList();

		// Send the points of poker
		List<List<Poker>> pokersList = PokerHelper.distributePoker();
		int cursor = 0;
		for(ClientSide client: roomClientList){
			client.setPokers(pokersList.get(cursor ++));
		}
		room.setLandlordPokers(pokersList.get(3));

		// Push information about the robber
		int startGrabIndex = (int)(Math.random() * 3);
		ClientSide startGrabClient = roomClientList.get(startGrabIndex);
		room.setCurrentSellClient(startGrabClient.getId());
		
		// Push start game messages
		room.setStatus(RoomStatus.STARTING);


		for(ClientSide client: roomClientList) {
			client.setType(ClientType.PEASANT);

			String result = MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("roomOwner", room.getRoomOwner())
					.put("roomClientCount", room.getClientSideList().size())
					.put("nextClientNickname", startGrabClient.getNickname())
					.put("nextClientId", startGrabClient.getId())
					.put("pokers", client.getPokers())
					.json();

			if(client.getRole() == ClientRole.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_STARTING, result);
			}else {
				if(startGrabClient.getId() == client.getId()) {
					RobotEventListener.get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(client, result);
				}
			}

		}


	}

}
