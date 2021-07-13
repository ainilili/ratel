package org.nico.ratel.landlords.server.event;

import java.util.List;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.*;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.robot.RobotEventListener;

public class ServerEventListener_CODE_GAME_POKER_PLAY implements ServerEventListener {

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		if (room == null) {
			return;
		}
		if (room.getCurrentSellClient() != clientSide.getId()) {
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_ORDER_ERROR, null);
			return;
		}
		Character[] options = Noson.convert(data, Character[].class);
		int[] indexes = PokerHelper.getIndexes(options, clientSide.getPokers());
		if (!PokerHelper.checkPokerIndex(indexes, clientSide.getPokers())) {
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_INVALID, null);
			return;
		}

		boolean sellFlag = true;
		List<Poker> currentPokers = PokerHelper.getPoker(indexes, clientSide.getPokers());
		PokerSell currentPokerSell = PokerHelper.checkPokerType(currentPokers);
		if (currentPokerSell.getSellType() == SellType.ILLEGAL) {
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_INVALID, null);
			return;
		}
		if (room.getLastSellClient() != clientSide.getId() && room.getLastPokerShell() != null) {
			PokerSell lastPokerSell = room.getLastPokerShell();

			if ((lastPokerSell.getSellType() != currentPokerSell.getSellType() || lastPokerSell.getSellPokers().size() != currentPokerSell.getSellPokers().size()) && currentPokerSell.getSellType() != SellType.BOMB && currentPokerSell.getSellType() != SellType.KING_BOMB) {
				String result = MapHelper.newInstance()
						.put("playType", currentPokerSell.getSellType())
						.put("playCount", currentPokerSell.getSellPokers().size())
						.put("preType", lastPokerSell.getSellType())
						.put("preCount", lastPokerSell.getSellPokers().size())
						.json();
				ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_MISMATCH, result);
				return;
			}
			if (lastPokerSell.getScore() >= currentPokerSell.getScore()) {
				String result = MapHelper.newInstance()
						.put("playScore", currentPokerSell.getScore())
						.put("preScore", lastPokerSell.getScore())
						.json();
				ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_LESS, result);
				return;
			}
		}

		ClientSide next = clientSide.getNext();

		room.setLastSellClient(clientSide.getId());
		room.setLastPokerShell(currentPokerSell);
		room.setCurrentSellClient(next.getId());

		clientSide.getPokers().removeAll(currentPokers);
		MapHelper mapHelper = MapHelper.newInstance()
				.put("clientId", clientSide.getId())
				.put("clientNickname", clientSide.getNickname())
				.put("clientType", clientSide.getType())
				.put("pokers", currentPokers)
				.put("lastSellClientId", clientSide.getId())
				.put("lastSellPokers", currentPokers);

		if (!clientSide.getPokers().isEmpty()) {
			mapHelper.put("sellClientNickname", next.getNickname());
		}

		String result = mapHelper.json();

		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() == ClientRole.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_POKERS, result);
			}
		}

		notifyWatcherPlayPoker(room, result);

		if (clientSide.getPokers().isEmpty()) {
			result = MapHelper.newInstance()
								.put("winnerNickname", clientSide.getNickname())
								.put("winnerType", clientSide.getType())
								.json();

			for(ClientSide client: room.getClientSideList()) {
				if(client.getRole() == ClientRole.PLAYER) {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_OVER, result);
				}
			}

			notifyWatcherGameOver(room, result);

			ServerEventListener.get(ServerEventCode.CODE_CLIENT_EXIT).call(clientSide, data);
		} else {
			if(next.getRole() == ClientRole.PLAYER) {
				ServerEventListener.get(ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT).call(next, result);
			}else {
				RobotEventListener.get(ClientEventCode.CODE_GAME_POKER_PLAY).call(next, data);
			}
		}
	}

	/**
	 * 通知观战者出牌信息
	 *
	 * @param room	房间
	 * @param result	出牌信息
	 */
	private void notifyWatcherPlayPoker(Room room, String result) {
		for (ClientSide watcher : room.getWatcherList()) {
			ChannelUtils.pushToClient(watcher.getChannel(), ClientEventCode.CODE_SHOW_POKERS, result);
		}
	}

	/**
	 * 通知观战者游戏结束
	 *
	 * @param room	房间
	 * @param result	出牌信息
	 */
	private void notifyWatcherGameOver(Room room, String  result) {
		for (ClientSide watcher : room.getWatcherList()) {
			ChannelUtils.pushToClient(watcher.getChannel(), ClientEventCode.CODE_GAME_OVER, result);
		}
	}
}
