package org.nico.ratel.landlords.client.event;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_ROOMS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		List<Map<String, Object>> roomList = Noson.convert(data, new NoType<List<Map<String, Object>>>() {});
		if(roomList != null && ! roomList.isEmpty()){
			SimplePrinter.printNotice("#\tID\t|\tOWNER\t|\tCOUNT\t#");
			for(Map<String, Object> room: roomList) {
				SimplePrinter.printNotice("#\t" + room.get("roomId") + "\t|\t" + room.get("roomOwner") + "\t|\t" + room.get("roomClientCount") + "\t#");
			}
			SimplePrinter.printNotice("");
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
		}else {
			SimplePrinter.printNotice("No available room, please create a room ！");
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
		}
	}



}
