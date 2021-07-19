package org.nico.ratel.landlords.client.event;

import static org.nico.ratel.landlords.client.event.ClientEventListener_CODE_CLIENT_NICKNAME_SET.NICKNAME_MAX_LENGTH;
import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.FormatPrinter;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_ROOMS extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		List<Map<String, Object>> roomList = Noson.convert(data, new NoType<List<Map<String, Object>>>() {});
		if (roomList != null && !roomList.isEmpty()) {
			// "COUNT" begins after NICKNAME_MAX_LENGTH characters. The dash means that the string is left-justified.
			String format = "#\t%s\t|\t%-" + NICKNAME_MAX_LENGTH + "s\t|\t%-6s\t|\t%-6s\t#\n";
			FormatPrinter.printNotice(format, "ID", "OWNER", "COUNT", "TYPE");
			for (Map<String, Object> room : roomList) {
				FormatPrinter.printNotice(format, room.get("roomId"), room.get("roomOwner"), room.get("roomClientCount"), room.get("roomType"));
			}
			SimplePrinter.printNotice("");
			get(ClientEventCode.CODE_SHOW_OPTIONS_PVP).call(channel, data);
		} else {
			SimplePrinter.printNotice("No available room. Please create a room!");
			get(ClientEventCode.CODE_SHOW_OPTIONS_PVP).call(channel, data);
		}
	}
}
