package org.nico.ratel.landlords.server.event;

import java.util.List;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.robot.RobotEventListener;

public class ServerEventListener_CODE_GAME_POKER_PLAY implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		if(room != null) {
			if(room.getCurrentSellClient() == clientSide.getId()) {
				Character[] options = Noson.convert(data, Character[].class);
				int[] indexes = PokerHelper.getIndexes(options, clientSide.getPokers());
				if(PokerHelper.checkPokerIndex(indexes, clientSide.getPokers())){
					boolean sellFlag = true;
					
					List<Poker> currentPokers = PokerHelper.getPoker(indexes, clientSide.getPokers());
					PokerSell currentPokerShell = PokerHelper.checkPokerType(currentPokers);
					if(currentPokerShell.getSellType() != SellType.ILLEGAL) {
						if(room.getLastSellClient() != clientSide.getId() && room.getLastPokerShell() != null){
							PokerSell lastPokerShell = room.getLastPokerShell();
							
							if((lastPokerShell.getSellType() != currentPokerShell.getSellType() || lastPokerShell.getSellPokers().size() != currentPokerShell.getSellPokers().size()) && currentPokerShell.getSellType() != SellType.BOMB && currentPokerShell.getSellType() != SellType.KING_BOMB) {
								String result = MapHelper.newInstance()
													.put("playType", currentPokerShell.getSellType())
													.put("playCount", currentPokerShell.getSellPokers().size())
													.put("preType", lastPokerShell.getSellType())
													.put("preCount", lastPokerShell.getSellPokers().size())
													.json();
								sellFlag = false;
								ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_MISMATCH, result);
							}else if(lastPokerShell.getScore() >= currentPokerShell.getScore()) {
								String result = MapHelper.newInstance()
										.put("playScore", currentPokerShell.getScore())
										.put("preScore", lastPokerShell.getScore())
										.json();
								sellFlag = false;
								ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_LESS, result);
							}
						}
					}else {
						sellFlag = false;
						ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_INVALID, null);
					}
					
					if(sellFlag) {
						ClientSide next = clientSide.getNext();
						
						room.setLastSellClient(clientSide.getId());
						room.setLastPokerShell(currentPokerShell);
						room.setCurrentSellClient(next.getId());
						
						clientSide.getPokers().removeAll(currentPokers);
						String result = MapHelper.newInstance()
								.put("clientId", clientSide.getId())
								.put("clientNickname", clientSide.getNickname())
								.put("clientType", clientSide.getType())
								.put("pokers", currentPokers)
								.put("sellClinetNickname", next.getNickname())
								.json();
						for(ClientSide client: room.getClientSideList()) {
							if(client.getRole() == ClientRole.PLAYER) {
								ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_POKERS, result);
							}
						}
						
						if(clientSide.getPokers().isEmpty()) {
							result = MapHelper.newInstance()
												.put("winnerNickname", clientSide.getNickname())
												.put("winnerType", clientSide.getType())
												.json();
							
							for(ClientSide client: room.getClientSideList()) {
								if(client.getRole() == ClientRole.PLAYER) {
									ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_OVER, result);
								}
							}
							ServerEventListener.get(ServerEventCode.CODE_CLIENT_EXIT).call(clientSide, data);
						}else {
							if(next.getRole() == ClientRole.PLAYER) {
								ServerEventListener.get(ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT).call(next, result);
							}else {
								RobotEventListener.get(ClientEventCode.CODE_GAME_POKER_PLAY).call(next, data);
							}
						}
					}
				}else{
					ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_INVALID, null);
				}
			}else {
				ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_ORDER_ERROR, null);
			}
		}else {
//			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_ROOM_PLAY_FAIL_BY_INEXIST, null);
		}
	}

}
