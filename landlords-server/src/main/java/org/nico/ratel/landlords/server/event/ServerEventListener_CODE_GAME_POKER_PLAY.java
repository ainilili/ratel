package org.nico.ratel.landlords.server.event;

import java.util.ArrayList;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.*;
import org.nico.ratel.landlords.features.Features;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.robot.RobotEventListener;
import org.nico.ratel.landlords.utils.LastCardsUtils;

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

		clientSide.addRound();

		if (currentPokerSell.getSellType() == SellType.BOMB || currentPokerSell.getSellType() == SellType.KING_BOMB) {
			// 炸弹积分翻倍
			room.increaseRate();
		}

		room.setLastSellClient(clientSide.getId());
		room.setLastPokerShell(currentPokerSell);
		room.setCurrentSellClient(next.getId());

		List<List<Poker>> lastPokerList = new ArrayList<>();
		for(int i = 0; i < room.getClientSideList().size(); i++){
			if(room.getClientSideList().get(i).getId() != clientSide.getId()){
				lastPokerList.add(room.getClientSideList().get(i).getPokers());
			}
		}
		String lastPokers = LastCardsUtils.getLastCards(lastPokerList);
		lastPokerList = new ArrayList<>();
		clientSide.getPokers().removeAll(currentPokers);
		MapHelper mapHelper = MapHelper.newInstance()
				.put("clientId", clientSide.getId())
				.put("clientNickname", clientSide.getNickname())
				.put("clientType", clientSide.getType())
				.put("pokers", currentPokers)
				.put("lastSellClientId", clientSide.getId())
				.put("lastSellPokers", currentPokers)
				.put("lastPokers",lastPokers);

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

		if (!clientSide.getPokers().isEmpty()) {
			if (next.getRole() == ClientRole.ROBOT) {
				RobotEventListener.get(ClientEventCode.CODE_GAME_POKER_PLAY).call(next, data);
			} else {
				ServerEventListener.get(ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT).call(next, result);
			}
			return;
		}

		gameOver(clientSide, room);
//        ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, data);
	}

	private void setRoomClientScore(Room room, ClientType winnerType) {
		int landLordScore = room.getScore() * 2;
		int peasantScore = room.getScore();
		// 输的一方分数为负
		if (winnerType == ClientType.LANDLORD) {
			peasantScore = -peasantScore;
		} else {
			landLordScore = -landLordScore;
		}
		for (ClientSide client : room.getClientSideList()) {
			if (client.getType() == ClientType.LANDLORD) {
				client.addScore(landLordScore);
			} else {
				client.addScore(peasantScore);
			}
		}
	}

	private void gameOver(ClientSide winner, Room room) {
		ClientType winnerType = winner.getType();
		if (isSpring(winner, room)) {
			room.increaseRate();
		}

		setRoomClientScore(room, winnerType);

		ArrayList<Object> clientScores = new ArrayList<>();
		for (ClientSide client : room.getClientSideList()) {
			MapHelper score = MapHelper.newInstance()
					.put("clientId", client.getId())
					.put("nickName", client.getNickname())
					.put("score", client.getScore())
					.put("scoreInc", client.getScoreInc())
					.put("pokers", client.getPokers());
			clientScores.add(score.map());
		}

		SimplePrinter.serverLog(clientScores.toString());
		String result = MapHelper.newInstance()
				.put("winnerNickname", winner.getNickname())
				.put("winnerType", winner.getType())
				.put("scores", clientScores)
				.json();

		boolean supportReady = true;
		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() == ClientRole.ROBOT || ! Features.supported(client.getVersion(), Features.READY)) {
				supportReady = false;
				break;
			}
		}
		if (supportReady){
			room.setStatus(RoomStatus.WAIT);
			room.initScoreRate();
			for (ClientSide client : room.getClientSideList()) {
				client.setStatus(ClientStatus.NO_READY);
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_OVER, result);
			}
		}else{
			ServerEventListener.get(ServerEventCode.CODE_CLIENT_EXIT).call(winner, "");
		}
		notifyWatcherGameOver(room, result);
	}

	private boolean isSpring(ClientSide winner, Room room) {
		boolean isSpring = true;
		for (ClientSide client: room.getClientSideList()) {
			if (client.getType() == winner.getType()) {
				continue;
			}
			if (client.getType() == ClientType.PEASANT && client.getRound() > 0) {
				isSpring = false;
			}
			if (client.getType() == ClientType.LANDLORD && client.getRound() > 1) {
				isSpring = false;
			}
		}
		return isSpring;
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
