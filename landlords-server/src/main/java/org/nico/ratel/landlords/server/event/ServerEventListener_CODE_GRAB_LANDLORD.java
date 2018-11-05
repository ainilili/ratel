package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.helper.PokerHelper;
import org.nico.ratel.landlords.server.event.helper.RoomHelper;
import org.nico.ratel.landlords.server.event.helper.TimeHelper;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_GRAB_LANDLORD implements ServerEventListener<Boolean>{

	@Override
	public void call(Channel channel, ServerTransferData<Boolean> serverTransferData) {
		ClientSide clientSide = ServerContains.CLIENT_SIDE_MAP.get(serverTransferData.getClientId());

		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());

		boolean isY = serverTransferData.getData();
		if(isY){
			System.out.println(PokerHelper.unfoldPoker(room.getLandlordPokers(), false));
			System.out.println(PokerHelper.unfoldPoker(room.getLandlordPokers(), false));
			clientSide.getPokers().addAll(room.getLandlordPokers());
			PokerHelper.sortPoker(clientSide.getPokers());
			System.out.println(PokerHelper.unfoldPoker(room.getLandlordPokers(), false));
			
			
			room.setLandlordId(clientSide.getId());
			for(ClientSide client: room.getClientSideList()){
				ChannelUtils.pushToClient(client.getChannel(), null, null, clientSide.getNickname() + " grabed landlord, he got three pokers:\r\n" + PokerHelper.unfoldPoker(room.getLandlordPokers(), false));
			}

			TimeHelper.sleep(500);

			for(ClientSide client: room.getClientSideList()){
				if(client.getId() != clientSide.getId()){
					ChannelUtils.pushToClient(client.getChannel(), null, null, "Please wait for " + clientSide.getNickname() + " to come out\r\n" + PokerHelper.unfoldPoker(client.getPokers(), true));
				}
			}

			TimeHelper.sleep(200);

			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_PLAY_ROUND, PokerHelper.unfoldPoker(clientSide.getPokers(), true));
		}else{
			if(clientSide.getNext().getId() == room.getLandlordId()){
				for(ClientSide client: room.getClientSideList()){
					ChannelUtils.pushToClient(client.getChannel(), null, null, "No one takes the landowner. Reissue the license");
				}
				RoomHelper.restartingGame(room);
			}else{
				for(ClientSide client: room.getClientSideList()){
					if(client.getId() != clientSide.getId()){
						ChannelUtils.pushToClient(client.getChannel(), null, null, clientSide.getNickname() + " don't grab");
					}
				}

				TimeHelper.sleep(500);

				ClientSide currentClient = clientSide.getNext();
				for(ClientSide client: room.getClientSideList()){
					ChannelUtils.pushToClient(client.getChannel(), null, null, "Turn " + currentClient.getNickname() + " confirm");
				}

				TimeHelper.sleep(500);


				ChannelUtils.pushToClient(currentClient.getChannel(), ClientEventCode.CODE_GRAB_LANDLORD, null);
			}
		}
	}





}
