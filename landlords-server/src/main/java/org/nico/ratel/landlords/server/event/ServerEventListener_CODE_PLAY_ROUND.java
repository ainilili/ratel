package org.nico.ratel.landlords.server.event;

import java.util.List;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.helper.PokerHelper;
import org.nico.ratel.landlords.server.event.helper.TimeHelper;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_PLAY_ROUND implements ServerEventListener<int[]>{

	@Override
	public void call(Channel channel, ServerTransferData<int[]> serverTransferData) {
		ClientSide clientSide = ServerContains.CLIENT_SIDE_MAP.get(serverTransferData.getClientId());
		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());
		int[] indexes = serverTransferData.getData();
		
		if(PokerHelper.checkPokerIndex(indexes, clientSide.getPokers())){
			boolean sellFlag = false;
			List<Poker> currentPokers = PokerHelper.getPoker(indexes, clientSide.getPokers());
			PokerSell currentPokerShell = PokerHelper.checkPokerType(currentPokers);
			if(room.getLastSellClient() != -1 && room.getLastSellClient() != clientSide.getId() && room.getLastPokerShell() != null){
				PokerSell lastPokerShell = room.getLastPokerShell();
				if(lastPokerShell.getSellType() != currentPokerShell.getSellType() && currentPokerShell.getSellType() != SellType.BOMB && currentPokerShell.getSellType() != SellType.KING_BOMB) {
					ChannelUtils.pushToClient(channel, ClientEventCode.CODE_PLAY_ROUND, PokerHelper.unfoldPoker(clientSide.getPokers(), true), "Your sell type is " + currentPokerShell.getSellType().getMsg() + " ,but last sell type is " + lastPokerShell.getSellType().getMsg());
				}else if(lastPokerShell.getScore() >= currentPokerShell.getScore()) {
					ChannelUtils.pushToClient(channel, ClientEventCode.CODE_PLAY_ROUND, PokerHelper.unfoldPoker(clientSide.getPokers(), true), "It's not as big as the other side");
				}else {
					sellFlag = true;
				}
			}else{
				sellFlag = true;
			}
			if(sellFlag) {
				room.setLastSellClient(clientSide.getId());
				room.setLastPokerShell(currentPokerShell);
				clientSide.getPokers().removeAll(currentPokers);
				
				for(ClientSide client: room.getClientSideList()) {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_POKERS, PokerHelper.unfoldPoker(currentPokers, false), clientSide.getNickname() + " sell， turn " + clientSide.getNext().getNickname());
				}
				
				TimeHelper.sleep(500);
				
				if(clientSide.getPokers().isEmpty()) {
					for(ClientSide client: room.getClientSideList()) {
						ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_OVER, null, clientSide.getNickname() + " win");
					}
				}else {
					ClientSide next = clientSide.getNext();
					ChannelUtils.pushToClient(next.getChannel(), ClientEventCode.CODE_PLAY_ROUND, PokerHelper.unfoldPoker(next.getPokers(), true));
				}
			}
		}else{
			if(indexes.length > 0 && indexes[0] == 0) {
				for(ClientSide client: room.getClientSideList()) {
					ChannelUtils.pushToClient(client.getChannel(), null, null, clientSide.getNickname() + " don't sell， turn " + clientSide.getNext().getNickname());
				}
				TimeHelper.sleep(500);
				
				ClientSide next = clientSide.getNext();
				ChannelUtils.pushToClient(next.getChannel(), ClientEventCode.CODE_PLAY_ROUND, PokerHelper.unfoldPoker(next.getPokers(), true));
			}else {
				ChannelUtils.pushToClient(channel, ClientEventCode.CODE_PLAY_ROUND, PokerHelper.unfoldPoker(clientSide.getPokers(), true), "The draw number is invalid");
			}
		}
	}

}
