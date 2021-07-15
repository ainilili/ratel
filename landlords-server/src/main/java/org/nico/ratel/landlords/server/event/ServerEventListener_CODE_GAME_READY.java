package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.*;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;

import java.util.concurrent.ConcurrentSkipListMap;

public class ServerEventListener_CODE_GAME_READY implements ServerEventListener {
	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		if (room == null) {
			return;
		}
		SimplePrinter.serverLog("房间状态：" + room.getStatus());
		SimplePrinter.serverLog("玩家状态：" + clientSide.getStatus());
		if (room.getStatus() == RoomStatus.STARTING) {
			return;
		}
		if (clientSide.getStatus() == ClientStatus.PLAYING || clientSide.getStatus() == ClientStatus.TO_CHOOSE || clientSide.getStatus() == ClientStatus.CALL_LANDLORD) {
			return;
		}
		clientSide.setStatus(clientSide.getStatus() == ClientStatus.READY ? ClientStatus.NO_READY : ClientStatus.READY);
		String result = MapHelper.newInstance()
				.put("clientNickName", clientSide.getNickname())
				.put("status", clientSide.getStatus())
				.put("clientId", clientSide.getId())
				.json();
		boolean allReady = true;
		ConcurrentSkipListMap<Integer, ClientSide> roomClientMap = (ConcurrentSkipListMap<Integer, ClientSide>) room.getClientSideMap();
		if (roomClientMap.size() < 3) {
			allReady = false;
		} else {
			for (ClientSide client : room.getClientSideList()) {
				if (client.getRole() == ClientRole.PLAYER && client.getStatus() != ClientStatus.READY) {
					allReady = false;
					break;
				}
			}
		}

		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() == ClientRole.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_READY, result);
			}
		}

		if (allReady) {
			ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, data);
		}
	}
}
