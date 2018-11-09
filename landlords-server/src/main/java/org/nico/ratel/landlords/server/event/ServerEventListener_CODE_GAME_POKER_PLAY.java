package org.nico.ratel.landlords.server.event;

import java.util.List;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.helper.TimeHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_GAME_POKER_PLAY implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());
		Character[] options = Noson.convert(data, Character[].class);
		int[] indexes = PokerHelper.getIndexes(options, clientSide.getPokers());
		if(PokerHelper.checkPokerIndex(indexes, clientSide.getPokers())){
			boolean sellFlag = false;
			List<Poker> currentPokers = PokerHelper.getPoker(indexes, clientSide.getPokers());
			PokerSell currentPokerShell = PokerHelper.checkPokerType(currentPokers);
			if(room.getLastSellClient() != -1 && room.getLastSellClient() != clientSide.getId() && room.getLastPokerShell() != null){
				PokerSell lastPokerShell = room.getLastPokerShell();
				if(lastPokerShell.getSellType() != currentPokerShell.getSellType() && currentPokerShell.getSellType() != SellType.BOMB && currentPokerShell.getSellType() != SellType.KING_BOMB) {
					String result = MapHelper.newInstance()
										.put("playType", currentPokerShell.getSellType())
										.put("preType", lastPokerShell.getSellType())
										.put("pokers", clientSide.getPokers())
										.json();
					ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_MISMATCH, result);
				}else if(lastPokerShell.getScore() >= currentPokerShell.getScore()) {
					String result = MapHelper.newInstance()
							.put("playScore", currentPokerShell.getScore())
							.put("preScore", lastPokerShell.getScore())
							.put("pokers", clientSide.getPokers())
							.json();
					ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_LESS, result);
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
				String result = MapHelper.newInstance()
						.put("currentClientId", clientSide.getId())
						.put("currentClientNickname", clientSide.getNickname())
						.put("pokers", currentPokers)
						.json();
				for(ClientSide client: room.getClientSideList()) {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_POKERS, result);
				}
				
				TimeHelper.sleep(500);
				
				if(clientSide.getPokers().isEmpty()) {
					result = MapHelper.newInstance()
										.put("winnerNickname", clientSide.getNickname())
										.json();
					for(ClientSide client: room.getClientSideList()) {
						ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_OVER, result);
					}
				}else {
					ClientSide next = clientSide.getNext();
					result = MapHelper.newInstance()
							.put("pokers", next.getPokers())
							.json();
					
					ChannelUtils.pushToClient(next.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY, result);
				}
			}
		}else{
			if(options.length > 0 && options[0] == 'p') {
				ClientSide turnClient = clientSide.getNext();
				for(ClientSide client: room.getClientSideList()) {
					String result = MapHelper.newInstance()
							.put("currentClientId", clientSide.getId())
							.put("currentClientNickname", clientSide.getNickname())
							.put("turnClientId", turnClient.getId())
							.put("turnClientNickname", turnClient.getNickname())
							.put("pokers", client.getPokers())
							.json();
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_DONT_SELL, result);
				}
			}else {
				String result = MapHelper.newInstance()
						.put("pokers", clientSide.getPokers())
						.json();
				ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY, result);
			}
		}
	}

}
