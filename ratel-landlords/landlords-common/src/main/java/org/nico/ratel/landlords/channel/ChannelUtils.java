package org.nico.ratel.landlords.channel;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;

import io.netty.channel.Channel;

public class ChannelUtils {

	public static <T> void pushToClient(Channel channel, ClientEventCode code, T datas) {
		pushToClient(channel, code, datas, null);
	}
	
	public static <T> void pushToClient(Channel channel, ClientEventCode code, T datas, String msg) {
		ClientTransferData<T> clientTransferData = new ClientTransferData<T>(code, datas, msg);
		channel.writeAndFlush(clientTransferData);
	}
	
	public static <T> void pushToServer(Channel channel, int clientId, int roomId, ServerEventCode code, T datas) {
		ServerTransferData<T> serverTransferData = new ServerTransferData<T>(clientId, roomId, code, datas);
		channel.writeAndFlush(serverTransferData);
	}
	
	public static <T> void pushToServer(Channel channel, int clientId, ServerEventCode code, T datas) {
		pushToServer(channel, clientId, -1, code, datas);
	}
	
	public static <T> void pushToServer(Channel channel, ServerEventCode code, T datas) {
		pushToServer(channel, -1, -1, code, datas);
	}
	
}
