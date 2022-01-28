package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.client.entity.User;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS_PVP extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("PVP: ");
		SimplePrinter.printNotice("1. Create Room");
		SimplePrinter.printNotice("2. Room List");
		SimplePrinter.printNotice("3. Join Room");
		SimplePrinter.printNotice("4. Spectate Game");
		SimplePrinter.printNotice("Please select an option above (enter [back|b] to return to options list)");
		String line = SimpleWriter.write(User.INSTANCE.getNickname(), "pvp");
		if (line == null) {
			SimplePrinter.printNotice("Invalid options, please choose again：");
			call(channel, data);
			return;
		}

		if (line.equalsIgnoreCase("BACK") || line.equalsIgnoreCase("b")) {
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
			return;
		}

		int choose = OptionsUtils.getOptions(line);
		switch (choose) {
			case 1:
				pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE, null);
				break;
			case 2:
				pushToServer(channel, ServerEventCode.CODE_GET_ROOMS, null);
				break;
			case 3:
				handleJoinRoom(channel, data);
				break;
			case 4:
				handleJoinRoom(channel, data, true);
				break;
			default:
				SimplePrinter.printNotice("Invalid option, please choose again：");
				call(channel, data);
		}
	}

	private void parseInvalid(Channel channel, String data) {
		SimplePrinter.printNotice("Invalid options, please choose again：");
		call(channel, data);
	}

	private void handleJoinRoom(Channel channel, String data) {
		handleJoinRoom(channel, data, false);
	}

	private void handleJoinRoom(Channel channel, String data, Boolean watchMode) {
		String notice = String.format("Please enter the room id you want to %s (enter [back|b] return options list)", watchMode ? "spectate" : "join");

		SimplePrinter.printNotice(notice);
		String line = SimpleWriter.write(User.INSTANCE.getNickname(), "roomid");
		if (line == null) {
			parseInvalid(channel, data);
			return;
		}

		if (line.equalsIgnoreCase("BACK") || line.equalsIgnoreCase("b")) {
			call(channel, data);
			return;
		}

		int option = OptionsUtils.getOptions(line);
		if (option < 1) {
			parseInvalid(channel, data);
			return;
		}

		pushToServer(channel, watchMode? ServerEventCode.CODE_GAME_WATCH : ServerEventCode.CODE_ROOM_JOIN, String.valueOf(option));
	}
}
