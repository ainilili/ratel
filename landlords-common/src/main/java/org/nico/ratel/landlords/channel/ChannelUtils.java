package org.nico.ratel.landlords.channel;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class ChannelUtils {

	public static void pushToClient(Channel channel, ClientEventCode code, String data) {
		pushToClient(channel, code, data, null);
	}
	
	public static void pushToClient(Channel channel, ClientEventCode code, String data, String info) {
		if(channel != null) {
			ClientTransferData.ClientTransferDataProtoc.Builder clientTransferData = ClientTransferData.ClientTransferDataProtoc.newBuilder();
			if(code != null) {
				clientTransferData.setCode(code.toString());
			}
			if(data != null) {
				clientTransferData.setData(data);
			}
			if(info != null) {
				clientTransferData.setInfo(info);
			}
			channel.writeAndFlush(clientTransferData);
		}
	}
	
	public static ChannelFuture pushToServer(Channel channel, ServerEventCode code, String data) {
		ServerTransferData.ServerTransferDataProtoc.Builder serverTransferData = ServerTransferData.ServerTransferDataProtoc.newBuilder();
		if(code != null) {
			serverTransferData.setCode(code.toString());		
		}
		if(data != null) {
			serverTransferData.setData(data);
		}
		return channel.writeAndFlush(serverTransferData);
	}
	
}
