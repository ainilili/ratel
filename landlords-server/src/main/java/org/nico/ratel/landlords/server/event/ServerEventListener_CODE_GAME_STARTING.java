package org.nico.ratel.landlords.server.event;

import java.util.LinkedList;
import java.util.List;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.*;
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

		// Record the first speaker
		room.setFirstSellClient(startGrabClient.getId());

		for(ClientSide client: roomClientList) {
			client.setType(ClientType.PEASANT);

			String result = MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("roomOwner", room.getRoomOwner())
					.put("roomClientCount", room.getClientSideList().size())
					.put("nextClientNickname", startGrabClient.getNickname())
					.put("nextClientId", startGrabClient.getId())
					.put("pokers", client.getPokers())
					// this key-value use to client order to show
					.put("clientOrderList", roomClientList)
					.json();

			if(client.getRole() == ClientRole.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_STARTING, result);
			}else {
				if(startGrabClient.getId() == client.getId()) {
					RobotEventListener.get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(client, result);
				}
			}

		}

		notifyWatcherGameStart(room);
	}


	/**
	 * 通知房间内的观战人员游戏开始
	 *
	 * @param room	房间
	 */
	private void notifyWatcherGameStart(Room room) {
		for (ClientSide clientSide : room.getWatcherList()) {
			String result = MapHelper.newInstance()
					.put("player1", room.getClientSideList().getFirst().getNickname())
					.put("pokers1", room.getClientSideList().getFirst().getPokers())
					.put("player2", room.getClientSideList().getFirst().getNext().getNickname())
					.put("pokers2", room.getClientSideList().getFirst().getNext().getPokers())
					.put("player3", room.getClientSideList().getLast().getNickname())
					.put("pokers3", room.getClientSideList().getLast().getPokers())
					.json();

			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_STARTING, result);
		}
	}

}
