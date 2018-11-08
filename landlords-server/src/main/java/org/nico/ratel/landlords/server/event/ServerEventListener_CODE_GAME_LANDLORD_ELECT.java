package org.nico.ratel.landlords.server.event;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.helper.TimeHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_GAME_LANDLORD_ELECT implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());

		boolean isY = Boolean.valueOf(data);
		if(isY){
			clientSide.getPokers().addAll(room.getLandlordPokers());
			PokerHelper.sortPoker(clientSide.getPokers());
			
			room.setLandlordId(clientSide.getId());
			for(ClientSide client: room.getClientSideList()){
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_CONFIRM, clientSide.getNickname());
			}
			
			for(ClientSide client: room.getClientSideList()){
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_POKERS_LANDLORD, Noson.reversal(room.getLandlordPokers()));
			}
			
			//PokerHelper.unfoldPoker(room.getLandlordPokers(), false)
			TimeHelper.sleep(500);

			for(ClientSide client: room.getClientSideList()){
				if(client.getId() != clientSide.getId()){
					ChannelUtils.pushToClient(client.getChannel(), null, null, "Please wait for " + clientSide.getNickname() + " to come out");
				}
			}

			TimeHelper.sleep(200);

			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY, PokerHelper.unfoldPoker(clientSide.getPokers(), true));
		}else{
			if(clientSide.getNext().getId() == room.getLandlordId()){
				for(ClientSide client: room.getClientSideList()){
					ChannelUtils.pushToClient(client.getChannel(), null, null, "No one takes the landowner. Reissue the license");
				}
				
				ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, String.valueOf(room.getId()));
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


				ChannelUtils.pushToClient(currentClient.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_ELECT, null);
			}
		}
	}





}
